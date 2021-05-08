package com.basicapi.resources;

import java.util.List;
import java.util.Optional;

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

import com.basicapi.converter.PessoaConverter;
import com.basicapi.dto.PessoaDto;
import com.basicapi.entities.Pessoa;
import com.basicapi.feignclients.EnderecoFeignClient;
import com.basicapi.service.PessoaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/pessoa")
public class PessoaResource {
	
	@Autowired
	private PessoaService service;
	
	@Autowired
	private PessoaConverter converter;
	
	@Autowired
	private EnderecoFeignClient enderecoFeign;
	
	@ApiOperation(value = "Retorna uma lista de todas pessoas")
	@ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Retorna a lista de pessoas"),
			    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
			})
	@GetMapping(produces="application/json")
	public ResponseEntity<List<PessoaDto>> buscarTodos() {
		List<Pessoa> list = service.buscarTodos();
		return ResponseEntity.ok(converter.entityToDto(list));
	}
	
	@ApiOperation(value = "Retorna uma lista de pessoas baseado no nome")
	@ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Retorna a lista de pessoas"),
			    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
			})
	@GetMapping(value = "/{nome}", produces="application/json")
	public ResponseEntity<List<PessoaDto>> buscarPorNome(@PathVariable String nome ) {
		List<Pessoa> list = service.buscarPorNome(nome);
		return ResponseEntity.ok(converter.entityToDto(list));
	}

	@ApiOperation(value = "Incluir uma Pessoa")
	@ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Retorna a pessoa incluída"),
			    @ApiResponse(code = 400, message = "CEP inválido ou inexistente"),
			    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
			})
	@PostMapping(produces="application/json", consumes="application/json")
	public ResponseEntity<Object> incluir(@RequestBody PessoaDto pessoaDto) {
		if (pessoaDto.getCep() != null) {
			try {
				enderecoFeign.getEndereco(pessoaDto.getCep()).get();
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
		Pessoa pessoa = converter.dtoToEntity(pessoaDto);
		return ResponseEntity.ok(converter.entityToDto(service.incluir(pessoa)));
	}
	
	@ApiOperation(value = "Alterar uma Pessoa")
	@ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Retorna a pessoa alterada"),
			    @ApiResponse(code = 400, message = "CEP inválido ou inexistente"),
			    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			    @ApiResponse(code = 404, message = "Não existe Pessoa com esse ID"),
			    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
			})
	@PutMapping(produces="application/json", consumes="application/json")
	public ResponseEntity<Object> alterar(@RequestBody PessoaDto pessoaDto) {
		Optional<Pessoa> pessoaOptional = service.buscarPorId(pessoaDto.getId());
		if (!pessoaOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		if (pessoaDto.getCep() != null) {
			try {
				enderecoFeign.getEndereco(pessoaDto.getCep()).get();
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
		Pessoa pessoa = converter.dtoToEntity(pessoaDto);
		return ResponseEntity.ok(converter.entityToDto(service.alterar(pessoa)));
	}

	
	@ApiOperation(value = "Excluir uma Pessoa")
	@ApiResponses(value = {
			    @ApiResponse(code = 200, message = "Exclusão com sucesso"),
			    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			    @ApiResponse(code = 404, message = "Não existe Pessoa com esse ID"),
			    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
			})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> excluir(@PathVariable Long id) {
		Optional<Pessoa> pessoaOptional = service.buscarPorId(id);
		if (!pessoaOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(service.excluir(id));
	}
	
}
