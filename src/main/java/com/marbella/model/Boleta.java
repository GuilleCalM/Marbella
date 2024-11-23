package com.marbella.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="boleta")
public class Boleta {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codBolGenerator")
    @SequenceGenerator(name = "codBolGenerator", sequenceName = "boleta_seq", initialValue = 9001, allocationSize = 1)
	@Column(name = "cod_bol")
	private int codBol;
	
	@Column(name="fecha_boleta",nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaBoleta;
	
	@Column(name = "igv_final", nullable=false)
	private double igvFinal;
	
	@Column(name = "monto_final", nullable=false)
	private double montoFinal;
	
	@OneToOne
	@JoinColumn(name="cod_ped",referencedColumnName = "cod_ped")
	private Pedido codPed;
}