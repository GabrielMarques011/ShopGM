package br.com.ShopGM.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import br.com.ShopGM.model.AdminShop;

public interface AdminRepository extends PagingAndSortingRepository<AdminShop, Long>{

}
