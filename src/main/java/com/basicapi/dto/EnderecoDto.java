package com.basicapi.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.basicapi.entities.Endereco;

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
	
	private Long id;
	private Long cep;
	private String logradouro;
	private String bairro;
	private String localidade;
	private String uf;

}
