package com.basicapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import com.basicapi.dto.PessoaDto;
import com.basicapi.entities.Pessoa;
import com.basicapi.feignclients.EnderecoFeignClient;

@Component
public class PessoaConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EnderecoFeignClient enderecoFeign;
	
	public PessoaDto entityToDto(Pessoa pessoa) {
		PessoaDto pessoaDto = modelMapper.map(pessoa, PessoaDto.class);
		if (pessoaDto.getCep() != null) {
			try {
				pessoaDto.setEndereco(enderecoFeign.getEndereco(pessoaDto.getCep()).get());
			} catch (Exception e) {
				//Não encontrou CEP ou retornou erro no serviço
				pessoaDto.setEndereco(null);
			}
		}
		return pessoaDto;
	}
	
	public Pessoa dtoToEntity(PessoaDto pessoaDto) throws ParseException {
		return modelMapper.map(pessoaDto, Pessoa.class);
	}
	
	public List<PessoaDto> entityToDto(List<Pessoa> listPessoa) {
		return listPessoa.stream().map(this::entityToDto).collect(Collectors.toList());
	}
	
	public List<Pessoa> dtoToEntity(List<PessoaDto> listPessoasDto) throws ParseException {
		return listPessoasDto.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

}
