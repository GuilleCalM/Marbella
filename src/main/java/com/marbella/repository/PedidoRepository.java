package com.marbella.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.marbella.model.Cliente;
import com.marbella.model.HojaReparto;
import com.marbella.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p WHERE p.codEst.codEst = 3")
    List<Pedido> findPedidoByCodEst();
    @Query("SELECT p FROM Pedido p WHERE p.codEst.codEst = 3 AND p.fechaPedido = :hoy")
    List<Pedido> findPedidoByCodEstAndFechaPedido(@Param("hoy") Date hoy);
    List<Pedido> getPedidoByCodCliOrderByCodPedDesc (Cliente codCli);
    List<Pedido> getPedidoByCodHoj (HojaReparto codHoj);
    Page<Pedido> getPedidoByCodCliOrderByCodPedDesc (Cliente codCli, Pageable pageable);
    Page<Pedido> findAllByOrderByCodPedDesc(Pageable pageable);
}