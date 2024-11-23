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
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="usuario",uniqueConstraints=
@UniqueConstraint(columnNames={"nombre_usu"}))
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codUsuGenerator")
    @SequenceGenerator(name = "codUsuGenerator", sequenceName = "usuario_seq", initialValue = 1004, allocationSize = 1)
	@Column(name = "cod_usu")
	private int codUsu;
	
	@Column(name = "nombre_usu", nullable=false, length=15, unique = true)
	@NotEmpty(message = "- Debes especificar el nombre de usuario")
	@Size(min = 1, max = 15, message = "- El nombre de usuario debe medir entre 1 y 15 caracteres")
	private String nombreUsu;
	
	@Column(name = "contrasena_usu", nullable=false, length=255)
	@NotEmpty(message = "- Debes especificar una contraseña")
	private String contrasenaUsu;
	
	@ManyToOne
    @JoinColumn(name = "cod_tipo_usu")
    private TipoUsuario codTipoUsu;

	@Transient
	private String contrasenaUsu2;
}
