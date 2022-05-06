package br.com.ShopGM.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.ShopGM.model.Avaliacao;

@Repository
public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long>{

	//criando um metodo que retorna um list de avaliação (busca todas as avaliações de um determinado restaurante
	public List<Avaliacao> findByEventosId(Long id);
	
}