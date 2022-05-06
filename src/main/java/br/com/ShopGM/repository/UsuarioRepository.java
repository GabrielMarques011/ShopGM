package br.com.ShopGM.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.ShopGM.model.Usuario;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{

	//metodo para buscar usuario no banco de dados
	public Usuario findByEmailAndSenha(String email, String senha);
	
}