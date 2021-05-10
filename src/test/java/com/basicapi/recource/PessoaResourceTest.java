package com.basicapi.recource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.basicapi.converter.PessoaConverter;
import com.basicapi.dto.PessoaDto;
import com.basicapi.entities.Pessoa;
import com.basicapi.feignclients.EnderecoFeignClient;
import com.basicapi.resources.PessoaResource;
import com.basicapi.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PessoaResource.class)
class PessoaResourceTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaService service;
    
    @MockBean
	private PessoaConverter converter;
    
    @MockBean
    private EnderecoFeignClient enderecoFeign;

    @Autowired
    private ObjectMapper objectMapper;
   
    @Test
    void deveRetornarSucesso_QuandoBuscarTodos() throws Exception {
    	Pessoa pessoa = Pessoa.builder().nome("Welington").build();
        List<Pessoa> pessoaList = List.of(pessoa);
        
        PessoaDto pessoaDto =  PessoaDto.builder().nome(pessoa.getNome()).build();
        List<PessoaDto> pessoaDtoList = List.of(pessoaDto);
        
        when(service.buscarTodos()).thenReturn(pessoaList);
        when(converter.entityToDto(pessoaList)).thenReturn(pessoaDtoList);
        
        this.mockMvc.perform(get("/api/pessoa"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(pessoa.getNome())));
        
        verify(service, times(1)).buscarTodos();
    }
    
    @Test
    void deveRetornar_404_QuandoPesquisarPessoa() throws Exception {
        mockMvc.perform(get("/api/pessoa/2")).andExpect(status().isNotFound());
        verify(service, times(1)).buscarPorNome("2");
    }
    
    @Test
    void deveRetornarSucesso_QuandoInserirPessoa() throws Exception {
		
    	Pessoa pessoa = Pessoa.builder().nome("Welington").build();
        
        PessoaDto pessoaDto =  PessoaDto.builder().nome(pessoa.getNome()).build();
       
        when(enderecoFeign.getEndereco(1l)).thenReturn(Optional.empty());
        when(converter.dtoToEntity(pessoaDto)).thenReturn(pessoa);
        when(service.incluir(pessoa)).thenReturn(pessoa);
        when(converter.entityToDto(pessoa)).thenReturn(pessoaDto);
        
        this.mockMvc.perform(post("/api/pessoa")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(objectMapper.writeValueAsString(pessoaDto))
        		)
        		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(pessoa.getNome())));
        
        verify(service, times(1)).incluir(pessoa);
    }
    
    @Test
    void deveRetornarSucesso_QuandoAtualizarPessoa() throws Exception {
		
    	Pessoa pessoa = Pessoa.builder().nome("Welington").build();
        
        PessoaDto pessoaDto =  PessoaDto.builder().id(1l).nome(pessoa.getNome()).build();
       
        when(enderecoFeign.getEndereco(1l)).thenReturn(Optional.empty());
        when(service.buscarPorId(1l)).thenReturn(Optional.of(pessoa));
        when(converter.dtoToEntity(pessoaDto)).thenReturn(pessoa);
        when(service.alterar(pessoa)).thenReturn(pessoa);
        when(converter.entityToDto(pessoa)).thenReturn(pessoaDto);
        
        
        this.mockMvc.perform(put("/api/pessoa")
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(objectMapper.writeValueAsString(pessoaDto))
        		)
        		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(pessoa.getNome())));
        
        verify(service, times(1)).alterar(pessoa);
    }
    
    @Test
    void deveRetornarSucesso_QuandoExcluirPessoa() throws Exception {
		
    	Pessoa pessoa = Pessoa.builder().id(1l).nome("Welington").build();
        
        when(service.buscarPorId(1l)).thenReturn(Optional.of(pessoa));
        when(service.excluir(1l)).thenReturn("Sucesso");
        
        this.mockMvc.perform(delete("/api/pessoa/"+1)
        		)
        		.andExpect(status().isOk())
                .andExpect(content().string(containsString("Sucesso")));
        
        verify(service, times(1)).excluir(1l);
    }
}
