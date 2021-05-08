package com.basicapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.basicapi.entities.Pessoa;
import com.basicapi.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository repository;
	
	public List<Pessoa> buscarTodos() {
		return repository.findAll();
	}

	public List<Pessoa> buscarPorNome(String nome) {
		return repository.findByNomeContainingIgnoreCase(nome);
	}

	public Optional<Pessoa> buscarPorId(Long id) {
		return repository.findById(id);
	}

	public Pessoa incluir(Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	public Pessoa alterar(@RequestBody Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	public String excluir(Long id) {
		 repository.deleteById(id);
		 return "Pessoa excluída com sucesso";
	}

}
