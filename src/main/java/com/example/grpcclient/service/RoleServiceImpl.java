package com.example.grpcclient.service;

import com.example.grpcclient.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

}
