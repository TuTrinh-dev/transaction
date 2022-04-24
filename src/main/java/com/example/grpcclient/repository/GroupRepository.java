package com.example.grpcclient.repository;

import com.example.grpcclient.entity.GroupCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupCategory, Long> {
//    List<GroupCategory> findGroupCategoriesByWallet_Id(int walletId);
}
