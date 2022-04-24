package com.example.grpcclient.controller;

import com.example.grpcclient.dto.*;
import com.example.grpcclient.entity.RefreshToken;
import com.example.grpcclient.entity.User;
import com.example.grpcclient.exception.TokenRefreshException;
import com.example.grpcclient.repository.RoleRepository;
import com.example.grpcclient.repository.UserRepository;
import com.example.grpcclient.security.CustomUserDetail;
import com.example.grpcclient.security.JwtTokenProvider;
import com.example.grpcclient.security.TokenRefreshRequest;
import com.example.grpcclient.service.RefreshTokenService;
import com.example.grpcclient.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/api")
@AllArgsConstructor
public class UserController {
    AuthenticationManager authenticationManager;
    JwtTokenProvider jwtTokenProvider;
    UserRepository userRepository;
    RoleRepository roleRepository;
    RefreshTokenService refreshTokenService;
    UserService userService;


//    public UserController() {
//        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
//        this.bucket = Bucket4j.builder()
//                .addLimit(limit)
//                .build();
//    }

    @GetMapping(path = "/auth/logout")
    ResponseEntity<String> logout(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        return ResponseEntity.ok().body("200 OK");
    }

    @GetMapping(path = "/auth/test")
    ResponseEntity<String> testUser(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok("valid");
    }

    @PostMapping(path = "/auth/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity(new ApiResponse(400, "Email is does not existed!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findUserByEmail(request.getEmail()).get();
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        CustomUserDetail customUserDetail = (CustomUserDetail) authenticate.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(customUserDetail);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(customUserDetail.getUser().getId());
        return ResponseEntity.ok(new AuthResponse(jwt, refreshToken.getToken()));
    }

    @PostMapping("/auth/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    CustomUserDetail customUserDetail = new CustomUserDetail(user);
                    String token = jwtTokenProvider.generateToken(customUserDetail);
                    return ResponseEntity.ok(new AuthResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping(path = "/auth/register")
    public @ResponseBody
    ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "Email is already existed!"));
        }
        if (!roleRepository.existsRoleByName(userDTO.getRole())) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "Role not found"));
        }
        UserResp res = userService.addUser(userDTO);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping(path = "/user")
    public @ResponseBody
    ResponseEntity<List<UserResp>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping(path = "/user/{userId}")
    public @ResponseBody
    ResponseEntity<UserResp> getUserById(@PathVariable int userId) {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @PutMapping(path = "/user")
    public @ResponseBody
    ResponseEntity<UserResp> updateUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userService.updateUser(userDTO));
    }

    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping(path = "/user")
//    public ResponseEntity<User> findUserByUsername(@RequestParam String username) {
//        return userService.findByUsername(username);
//    }
}
