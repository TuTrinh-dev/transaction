package com.example.grpcclient.repository;

import com.example.grpcclient.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsCategoriesByNameAndGroupCategory_Id(String userName, Long groupId);

    @Query(value = "select *  from category inner join wallet_group_category w on w.group_category_id = category.group_category_id where w.wallet_id = ?1", nativeQuery = true)
    List<Category> getAllByWalletId(Long walletId);
}
