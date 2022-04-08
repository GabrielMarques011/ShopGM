package br.com.ShopGM.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.ShopGM.model.Categorias;
import br.com.ShopGM.model.Eventos;

public interface EventosRepository extends PagingAndSortingRepository<Eventos, Long>{

	
	
}