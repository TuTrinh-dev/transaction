package com.example.grpcclient.service;

import com.example.grpcclient.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> getAllCategory(Long walletId);
}
