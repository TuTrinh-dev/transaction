package com.example.grpcclient.controller;

import com.example.grpcclient.dto.ApiResponse;
import com.example.grpcclient.dto.CategoryDTO;
import com.example.grpcclient.repository.CategoryRepository;
import com.example.grpcclient.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class CategoryController {
    CategoryService categoryService;
    CategoryRepository categoryRepository;

    @PostMapping(path = "/category")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        if (categoryRepository.existsCategoriesByNameAndGroupCategory_Id(categoryDTO.getName(), categoryDTO.getGroupId())) {
            return new ResponseEntity(new ApiResponse(400, "Category is already exist!"),
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(categoryService.addCategory(categoryDTO));
    }

    @GetMapping(path = "/category/{walletId}")
    public ResponseEntity<List<CategoryDTO>> getAllByWalletId(@PathVariable long walletId) {
        return ResponseEntity.ok().body(categoryService.getAllCategory(walletId));
    }
}
