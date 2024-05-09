package com.estudos.br.parkapi.repository;

import com.estudos.br.parkapi.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


}
