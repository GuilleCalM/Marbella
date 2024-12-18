package com.marbella.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.marbella.interfaces.IImagen;

@Service
public class ImagenService implements IImagen{
	@Override
	@Transactional
	public String subirImagen(MultipartFile file, String categoria) throws IOException {
		String uniqueFilename = file.getOriginalFilename();
		String imagePath = "src/main/resources/static/imagenes/" + categoria + "/" + uniqueFilename;
			
			try (InputStream inputStream = file.getInputStream()) {
		        Files.copy(inputStream, Paths.get(imagePath));
		    }
				
		return uniqueFilename;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Resource verImagen(String filename, String categoria) throws MalformedURLException {
	    String imagePath = "static/imagenes/" + categoria + "/" + filename;
	    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(imagePath);
	    
	    if (inputStream == null) {
	        throw new RuntimeException("No se encontró la imagen en la ruta: " + imagePath);
	    }

	    return new InputStreamResource(inputStream);
	}

	@Override
	@Transactional(readOnly = true)
	public Resource verImagen(String filename) throws MalformedURLException {
	    String imagePath = "static/imagenes/" + filename;
	    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(imagePath);
	    
	    if (inputStream == null) {
	        throw new RuntimeException("No se encontró la imagen en la ruta: " + imagePath);
	    }

	    return new InputStreamResource(inputStream);
	}
}