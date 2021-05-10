package com.basicapi.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PessoaDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Código da pessoa")
	private Long id;
	
	@ApiModelProperty(value = "Nome da pessoa")
	private String nome;
	
	@ApiModelProperty(value = "CEP da pessoa pra pesquisa de Endereço")
	private Long cep;
	
	@ApiModelProperty(value = "Endereço da pessoa")
	private EnderecoDto endereco;
}
