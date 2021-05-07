package com.basicApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basicApi.entities.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}

