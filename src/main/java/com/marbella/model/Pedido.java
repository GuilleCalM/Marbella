package com.marbella.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pedido")
public class Pedido {
	@Id
	@Column(name="cod_ped")
	@SequenceGenerator(name = "codPedGenerator", sequenceName = "pedido_seq", initialValue = 10001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codPedGenerator")
	private int codPed;
	
	@Column(name = "fecha_pedido", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaPedido;
	
	@Column(name = "monto_total")
	private double montoTotal;
	
	@ManyToOne
    @JoinColumn(name = "cod_hoj")
	private HojaReparto codHoj;

	@ManyToOne
    @JoinColumn(name = "cod_cli")
	private Cliente codCli;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cod_est")
	private EstadoPedido codEst;
	
	@OneToOne(mappedBy = "codPed")
	private Boleta boleta;

	@OneToMany(targetEntity = DetallePedido.class, mappedBy = "codPed",fetch = FetchType.EAGER)
	private List<DetallePedido> detallePedido;
}