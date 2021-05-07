package com.basicapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basicapi.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Pessoa findByNomeIgnoreCase(String nome);

}
