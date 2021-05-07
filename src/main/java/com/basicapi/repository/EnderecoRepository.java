package com.basicapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basicapi.entities.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}

