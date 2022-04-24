package com.example.grpcclient.service;

import com.example.grpcclient.dto.CategoryDTO;
import com.example.grpcclient.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    ModelMapper modelMapper;

//    @Autowired
//    GroupCategoryService groupCategoryService;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
//        GroupCategory groupCategory = groupCategoryService.findGroupCategoryById(categoryDTO.getGroupId());
//        Category category = new Category();
//        category.setName(categoryDTO.getName());
//        category.setGroupCategory(groupCategory);
//        category = categoryRepository.save(category);
//        return modelMapper.map(category, CategoryDTO.class);
        return null;
    }

    @Override
    public List<CategoryDTO> getAllCategory(Long walletId) {
        return categoryRepository.getAllByWalletId(walletId).stream().map(e -> modelMapper.map(e, CategoryDTO.class)).collect(Collectors.toList());
    }
}
