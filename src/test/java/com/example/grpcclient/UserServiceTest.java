package com.example.grpcclient;

import com.example.grpcclient.dto.UserResp;
import com.example.grpcclient.repository.UserRepository;
import com.example.grpcclient.service.UserService;
import com.example.grpcclient.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserService userService = new UserServiceImpl();
    @Mock
    ModelMapper modelMapper;

    @Test
    public void when_save_user_it_should_return_user() {
        List<UserResp> users = new ArrayList<>();
        users.add(new UserResp("test1", "Test 1", "USER"));
        users.add(new UserResp("test2", "Test 2", "USER"));
        users.add(new UserResp("test3", "Test 3", "USER"));
        when(userRepository.findAll().stream().map(user -> {
                    UserResp userResp1 = modelMapper.map(user, UserResp.class);
                    userResp1.setRole(user.getRoles().stream().findFirst().get().getName());
                    return userResp1;
                })
                .collect(Collectors.toList())).thenReturn(users);
        List<UserResp> expected = userService.getAllUser();
        assertEquals(expected, users);
    }
}
