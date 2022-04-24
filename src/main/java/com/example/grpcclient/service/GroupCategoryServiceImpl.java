package com.example.grpcclient.service;

import com.example.grpcclient.entity.GroupCategory;
import com.example.grpcclient.exception.AppException;
import com.example.grpcclient.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupCategoryServiceImpl implements GroupCategoryService {
    GroupRepository groupRepository;
    CategoryService categoryService;

    @Override
    public GroupCategory findGroupCategoryById(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new AppException("not found"));
    }
}
