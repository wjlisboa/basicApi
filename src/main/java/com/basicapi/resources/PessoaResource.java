package com.basicapi.resources;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basicapi.dto.PessoaDto;
import com.basicapi.entities.Pessoa;
import com.basicapi.service.PessoaService;

@RestController
@RequestMapping(value = "/api/pessoa")
public class PessoaResource {
	
	@Autowired
	private PessoaService service;
	
	 @Autowired
	 private ModelMapper modelMapper;
	 
	@GetMapping
	public ResponseEntity<List<Pessoa>> buscarTodos() {
		return ResponseEntity.ok(service.buscarTodos());
	}
	
	@GetMapping(value = "/{nome}")
	public ResponseEntity<Pessoa> buscarPorNome(@PathVariable String nome ) {
		return ResponseEntity.ok(service.buscarPorNome(nome));
	}

	@PostMapping
	public PessoaDto incluir(@RequestBody PessoaDto pessoaDto) {
		Pessoa pessoa = convertToEntity(pessoaDto);
		return convertToDto(service.incluir(pessoa));
	}
	
	@PutMapping
	public PessoaDto alterar(@RequestBody PessoaDto pessoaDto) {
		Pessoa pessoa = convertToEntity(pessoaDto);
		return convertToDto(service.alterar(pessoa));
	}
	
	@DeleteMapping(value = "/{id}")
	public void excluir(@PathVariable Long id) {
		service.excluir(id);
	}
	
	private PessoaDto convertToDto(Pessoa pessoa) {
		return modelMapper.map(pessoa, PessoaDto.class);
	}
	
	private Pessoa convertToEntity(PessoaDto pessoaDto) throws ParseException {
		return modelMapper.map(pessoaDto, Pessoa.class);
	}
}
