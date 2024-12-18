package com.marbella.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
@Table(name="detalle_pedido")
public class DetallePedido implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7178303677778045576L;

	@Id
	@Column(name="cod_det")
	@SequenceGenerator(name = "sequence_coddest", sequenceName = "sequence_coddest", allocationSize = 1, initialValue = 2)
	@GeneratedValue(generator = "sequence_coddest", strategy = GenerationType.SEQUENCE)
	private int codDet;
	
	@Column(name="cantidad",nullable = false)
	private int cantidadDet;
	
	@Column(name="monto_det",nullable = false)
	private double montoDet;

	@ManyToOne(targetEntity = Pedido.class)
    @JoinColumn(name = "cod_ped")
	private Pedido codPed;
	
	@ManyToOne
	@JoinColumn(name="cod_pro")
	private Producto codPro;

	public DetallePedido(int cantidadDet, double montoDet, Producto codPro) {
		this.cantidadDet = cantidadDet;
		this.montoDet = montoDet;
		this.codPro = codPro;
	}
}
