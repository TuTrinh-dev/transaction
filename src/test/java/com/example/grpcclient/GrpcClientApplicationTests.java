package com.example.grpcclient;

import com.example.grpcclient.dto.UserResp;
import com.example.grpcclient.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GrpcClientApplicationTests {

    @Mock
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    public void getAllUser() {
        List<UserResp> users = userService.getAllUser();
    }
}
