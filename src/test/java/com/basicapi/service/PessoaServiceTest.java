package com.basicapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.basicapi.entities.Pessoa;
import com.basicapi.repository.PessoaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PessoaServiceTest {

	@Autowired
	private PessoaService service;
	
	@MockBean
	private PessoaRepository repository;
	
	@Test
    @DisplayName("Deve consultar uma pessoa passando o nome e validar o retorno")
    void deve_retornarPessoasbuscarPorNome() {
        Pessoa pessoa = Pessoa.builder().nome("Wel").build();
        List<Pessoa> pessoaList = List.of(pessoa);
        
        when(repository.findByNomeContainingIgnoreCase(pessoa.getNome())).thenReturn(pessoaList);
        
        List<Pessoa> pessoas = service.buscarPorNome(pessoa.getNome());
        
        assertTrue(pessoas.size() == 1);
        
        if (!pessoas.isEmpty()) {
        	 assertTrue(pessoas.stream()
         			.anyMatch(x -> x.getNome().contains(pessoa.getNome())));
        }
    }
	
    @Test
    @DisplayName("Deve incluir uma pessoa e validar se a inclusão foi correta")
    void deve_RetornarSucesso_QuandoInserirPessoa() throws Exception {
        Pessoa pessoa = Pessoa.builder().nome("Welington 2").build();
        Pessoa pessoaEsperada = Pessoa.builder().id(2l).nome(pessoa.getNome()).build();
        
        when(repository.save(pessoa)).thenReturn(pessoaEsperada);

        Pessoa pessoaInserida = service.incluir(pessoa);
        
        assertEquals(pessoaEsperada, pessoaInserida);
        verify(repository, times(1)).save(pessoa);
    }
    
    @Test
    @DisplayName("Deve alterar uma pessoa e validar se a alteração foi correta")
    void deve_RetornarSucesso_QuandoAtualizarPessoa() throws Exception {
		 
		Pessoa pessoa = Pessoa.builder().id(2l).nome("Welington 2").build();
		
        Pessoa pessoaEsperada = Pessoa.builder().id(2l).nome(pessoa.getNome()+" Alterado").build();
        
        when(repository.save(pessoa)).thenReturn(pessoaEsperada);
        
		Pessoa pessoaAlterada = service.alterar(pessoa);  
	 
	    assertEquals(pessoa.getId(), pessoaAlterada.getId());
	    assertNotEquals(pessoa.getNome(), pessoaAlterada.getNome());
	    verify(repository, times(1)).save(pessoa);
    }
   
	
	@Test
    @DisplayName("Deve Excluir uma pessoa e validar se a exclusão foi com Sucesso")
    void deve_RetornarSucesso_QuandoExcluirPessoa() throws Exception {
		
		Pessoa pessoa = Pessoa.builder().id(2l).nome("Welington 2").build();
		
		doNothing().when(repository).deleteById(pessoa.getId());;
        
		String sucesso = service.excluir(pessoa.getId());  
	 
		assertNotNull(sucesso);
		assertTrue(sucesso.toUpperCase().contains("SUCESSO"));

	    verify(repository, times(1)).deleteById(pessoa.getId());
        
    }
	
}
