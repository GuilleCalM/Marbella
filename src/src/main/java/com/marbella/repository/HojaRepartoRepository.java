package com.marbella.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.marbella.model.HojaReparto;

public interface HojaRepartoRepository extends JpaRepository<HojaReparto, Integer> {
    HojaReparto findByFechaReparto (Date fechaReparto);
    @Query("SELECT hr FROM Pedido p INNER JOIN HojaReparto hr WHERE p.codEst.codEst = 3")
    Page<HojaReparto> findAllByCodEst(Pageable pageable);
    Page<HojaReparto> findAllByOrderByCodHojDesc(Pageable pageable);
}