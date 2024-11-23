package com.marbella.interfaces;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IImagen {
	String subirImagen(MultipartFile file, String categoria) throws IOException;
	Resource verImagen(String filename, String categoria) throws MalformedURLException;
	Resource verImagen(String filename) throws MalformedURLException;
}