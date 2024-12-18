package com.marbella.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private int codPro;
    private String nombrePro;
    private String descripcionPro;
    private double precioPro;
    private String nombreMarca;
    private String nombreCategoria;
    private int stock;
}

