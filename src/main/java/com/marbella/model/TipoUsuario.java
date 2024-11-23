package com.marbella.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tipo_usuario")
public class TipoUsuario {	
	@Id
	@Column(name = "cod_tipo_usu")
    private int codTipoUsu;
	
    @Column(name = "desc_tipo_usu", nullable = false, length = 15)
    private String descTipoUsu;  

}
