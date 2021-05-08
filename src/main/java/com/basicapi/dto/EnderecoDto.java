package com.basicapi.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EnderecoDto  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cep;
	private String logradouro;
	private String bairro;
	private String localidade;
	private String uf;

}
