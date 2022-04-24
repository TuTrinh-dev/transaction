package com.example.grpcclient.service;

import com.example.grpcclient.dto.UserDTO;
import com.example.grpcclient.dto.UserResp;
import com.example.grpcclient.entity.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    List<UserResp> getAllUser();

    UserResp addUser(UserDTO userDTO);

    UserResp updateUser(UserDTO userDTO);

    void deleteUserById(int userId);

    UserResp getUserById(int userId);
}
