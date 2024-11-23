package com.marbella.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="hoja_reparto")
public class HojaReparto {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codHojGenerator")
	@SequenceGenerator(name = "codHojGenerator", sequenceName = "HojaRep_seq", initialValue = 6001, allocationSize = 1)
	@Column(name="cod_hoj")
	private int codHoj;
	
	@Column(name="fecha_reparto",nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaReparto;
	
	@Column(name="cantidad_ped",columnDefinition = "int default 0")
	private int cantidadPed;
}