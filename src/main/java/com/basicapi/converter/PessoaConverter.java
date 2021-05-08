package com.basicapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import com.basicapi.dto.PessoaDto;
import com.basicapi.dto.PessoaDtoInput;
import com.basicapi.entities.Pessoa;

@Component
public class PessoaConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public PessoaDto entityToDto(Pessoa pessoa) {
		return modelMapper.map(pessoa, PessoaDto.class);
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

	public Pessoa dtoInputToEntity(PessoaDtoInput pessoaDto) {
		return modelMapper.map(pessoaDto, Pessoa.class);
	}
}
