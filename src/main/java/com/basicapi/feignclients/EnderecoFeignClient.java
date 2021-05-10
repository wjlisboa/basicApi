package com.basicapi.feignclients;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.basicapi.dto.EnderecoDto;

@Component
@FeignClient(name = "endereco", url = "https://viacep.com.br")
public interface EnderecoFeignClient {

	@GetMapping(value = "/ws/{cep}/json")
	Optional<EnderecoDto> getEndereco(@PathVariable Long cep);
	
}
