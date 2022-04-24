package com.example.grpcclient.service;

import com.example.grpcclient.entity.GroupCategory;

public interface GroupCategoryService {
    GroupCategory findGroupCategoryById(Long groupId);
}
