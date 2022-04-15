package br.com.ShopGM.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.ShopGM.model.Login;

public interface LoginRepository extends PagingAndSortingRepository<Login, Long> {

}
