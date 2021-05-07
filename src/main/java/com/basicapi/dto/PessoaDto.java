package com.basicapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PessoaDto {

	private Long id;
	
	private String nome;
	
	private EnderecoDto endereco;
}
