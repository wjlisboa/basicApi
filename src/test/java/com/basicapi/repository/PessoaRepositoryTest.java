package com.basicapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.basicapi.entities.Pessoa;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PessoaRepositoryTest {

	@Autowired
	private PessoaRepository repository;
	 
    @Test
    void inserirPessoa() {
    	Integer countBefore = repository.findAll().size();
        Pessoa pessoa = Pessoa.builder().nome("Welington 2").build();
        repository.save(pessoa);
        Integer countAfter = repository.findAll().size();
        assertEquals(countBefore+1, countAfter);
        
        Pessoa pessoaInserted = repository.findById(pessoa.getId()).get();
        
        assertEquals(pessoa, pessoaInserted);
    }
    
    
    @Test
    void alterarPessoa() {
    	Optional<Pessoa> pessoaOptionalBefore = repository.findById(1l);

    	if (pessoaOptionalBefore.isPresent()) {
    		Pessoa pessoaBefore = pessoaOptionalBefore.get();
    		String oldName = pessoaBefore.getNome();
    		Pessoa pessoa = Pessoa.builder().id(pessoaBefore.getId()).nome(pessoaBefore.getNome()+" Alterado").build();
		    
    		repository.save(pessoa);
		    
    		Pessoa pessoaAfter = repository.findById(pessoa.getId()).get();
	        
		    assertEquals(pessoaBefore.getId(), pessoaAfter.getId());
		    assertNotEquals(oldName, pessoaAfter.getNome());
    	}
    }
   
    @Test
    void buscarPorNome() {
        Pessoa pessoa = Pessoa.builder().nome("Wel").build();
        List<Pessoa> pessoas = repository.findByNomeContainingIgnoreCase(pessoa.getNome());
        
        if (!pessoas.isEmpty()) {
        	 assertTrue(pessoas.stream()
         			.anyMatch(x -> x.getNome().contains(pessoa.getNome())));
        }
       
    }
}
