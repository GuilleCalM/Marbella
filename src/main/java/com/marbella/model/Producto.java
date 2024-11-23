package com.marbella.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="producto")
public class Producto {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="codProGenerator")
	@SequenceGenerator(name = "codProGenerator", sequenceName = "producto_seq", initialValue = 7064, allocationSize = 1)
	@Column(name="cod_pro")
    private int codPro;
    
    @Column(name = "nombre_pro", nullable = false, length = 80)
    @NotEmpty(message = "- Debe ingresar el nombre del producto")
    private String nombrePro;
    
    @ManyToOne
    @JoinColumn(name = "cod_marca")
    private Marca marca;
    
    @Column(name = "descripcion_pro", nullable = false, length = 255)
    @NotEmpty(message = "- Debe ingresar la descripción del producto")
    private String descripcionPro;
    
    @Column(name = "precio_pro", nullable = false)
    @NotNull(message = "- Debe ingresar el precio unitario del producto")
    @Positive(message = "- El precio debe ser un número positivo")
    private double precioPro;
    
    @ManyToOne
    @JoinColumn(name = "id_cat")
    private Categoria categoria;
    
    @Column(name = "stock", nullable = false)
    @NotNull(message = "- Debe ingresar el stock inicial del producto")
    @Min(value = 0, message = "- El stock debe ser un número no negativo")
    private int stock;
    
    @Column(name = "img_pro")
    private String imgPro;
}
