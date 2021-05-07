package com.basicApi.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basicApi.entities.Pessoa;
import com.basicApi.repository.PessoaRepository;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoaRecource {
	
	@Autowired
	private PessoaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> buscarTodos() {
		List<Pessoa> list = repository.findAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{nome}")
	public ResponseEntity<Pessoa> buscarPorNome(@PathVariable String nome ) {
		Pessoa pessoa = repository.findByNomeIgnoreCase(nome);
		return ResponseEntity.ok(pessoa);
	}

	@PostMapping
	public Pessoa Incluir (@RequestBody Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	@PutMapping
	public Pessoa Alterar (@RequestBody Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	@DeleteMapping(value = "/{id}")
	public void Excluir (@PathVariable Long id) {
		 repository.deleteById(id);
	}
}
