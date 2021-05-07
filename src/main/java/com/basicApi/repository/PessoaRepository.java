package com.basicApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basicApi.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Pessoa findByNomeIgnoreCase(String nome);

}
