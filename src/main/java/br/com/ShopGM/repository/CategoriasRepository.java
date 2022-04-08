package br.com.ShopGM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import br.com.ShopGM.model.Categorias;

public interface CategoriasRepository extends PagingAndSortingRepository<Categorias, Long>{
	
	//SELECT * FROM 'categorias' WHERE 'palavrasChaves' LIKE 'f%';
	/* public List<Categorias> findByPalavrasChaveLike(String palavrasChaves); */
	@Query("SELECT c FROM Categorias c WHERE c.palavrasChaves LIKE %:d% ")
	public List<Categorias> buscarPalavrasChave(@Param("d") String palavrasChaves);
	
	//PARA TRAZER AS CATEGORIAS EM ORDEM POR NOME
	public List<Categorias> findAllByOrderByNomeCategoriaAsc();
	
}