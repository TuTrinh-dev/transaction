package com.example.grpcclient.service;

import com.example.grpcclient.dto.UserDTO;
import com.example.grpcclient.dto.UserResp;
import com.example.grpcclient.entity.Role;
import com.example.grpcclient.entity.User;
import com.example.grpcclient.exception.AppException;
import com.example.grpcclient.repository.RoleRepository;
import com.example.grpcclient.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new AppException("Username not found"));
    }

    @Override
    @Cacheable(value = "userList")
    public List<UserResp> getAllUser() {
        List<UserResp> userResp = userRepository.findAll().stream()
                .map(user -> {
                    UserResp userResp1 = modelMapper.map(user, UserResp.class);
                    userResp1.setRole(user.getRoles().stream().findFirst().get().getName());
                    return userResp1;
                })
                .collect(Collectors.toList());
        return userResp;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "userList", allEntries = true),}, put = {
            @CachePut(value = "user", key = "#userDTO.id")})
    public UserResp addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new AppException("User Role not set."));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Collections.singleton(role));
        user = userRepository.save(user);
        return modelMapper.map(user, UserResp.class);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "userList", allEntries = true),
            @CacheEvict(value = "user", key = "#userDTO.id"),})
    public UserResp updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new AppException("User not found"));
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user = userRepository.save(user);
        return modelMapper.map(user, UserResp.class);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "userList", allEntries = true),
            @CacheEvict(value = "user", key = "#userId"),})
    public void deleteUserById(int userId) {
        User user = userRepository.findById((long) userId)
                .orElseThrow(() -> new AppException("User not found"));
        userRepository.deleteById(user.getId());
    }

    @Override
    @Cacheable(value = "user", key = "#userId")
    public UserResp getUserById(int userId) {
        User user = userRepository.findById((long) userId).orElseThrow(() -> new AppException("User not found"));
        UserResp userResp = modelMapper.map(user, UserResp.class);
        userResp.setRole(user.getRoles().stream().findFirst().get().getName());
        return userResp;
    }

}
