package com.marbella.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marbella.model.Boleta;
import com.marbella.model.Pedido;

public interface BoletaRepository extends JpaRepository<Boleta, Integer> {
    List<Boleta> getBoletaByCodPed (Pedido codPed);
}
