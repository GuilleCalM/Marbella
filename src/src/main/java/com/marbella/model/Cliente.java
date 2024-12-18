package com.marbella.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
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
@Table(name="cliente",uniqueConstraints=
@UniqueConstraint(columnNames={"dni_cli"}))
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codCliGenerator")
	@SequenceGenerator(name = "codCliGenerator", sequenceName = "cliente_seq", initialValue = 2001, allocationSize = 1)
	@Column(name="cod_cli")
	private int codCli;
	
	@Column(name = "nombre_cli", nullable = false, length = 80)
	@NotEmpty(message = "- Debe ingresar su nombre")
    private String nombreCli;

    @Column(name = "apellido_cli", nullable = false, length = 80)
    @NotEmpty(message = "- Debe ingresar su apellido")
    private String apellidosCli;

    @Column(name = "direccion_cli", nullable = false, length = 150)
    @NotEmpty(message = "- Debe ingresar su dirección")
    private String direccionCli;

    @Column(name = "telef_cli", nullable = false, length = 20)
    @NotEmpty(message = "- Debe ingresar un número telefónico")
    private String telefCli;

    @Column(name = "dni_cli", nullable = false, length = 8, unique = true)
    @NotEmpty(message = "- Debe ingresar su dni")
	@Size(min = 8, max = 8, message = "- El DNI debe tener 8 numeros")
    private String dniCli;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cod_usu", referencedColumnName = "cod_usu")
    @Valid
    private Usuario codUsu;

    @OneToMany(mappedBy = "codCli")
	private List<Pedido> pedido;
}