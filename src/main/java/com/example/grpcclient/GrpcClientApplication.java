package com.example.grpcclient;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class GrpcClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
//        roleRepository.save(new Role(1, "ADMIN"));
//        roleRepository.save(new Role(2, "USER"));
//        User user = new User();
//        user.setUsername("trinhlt");
//        user.setPassword(passwordEncoder.encode("123456"));
//        user.setEmail("trinh@gmail.com");
//        Role role = roleRepository.findByName("ADMIN").orElseThrow();
//        user.setRoles(Collections.singleton(role));
//        userRepository.save(user);
//        System.out.println(user);
//
//        List<GroupCategory> groupCategoryCategories = new ArrayList<>();
//        groupCategoryCategories.add(new GroupCategory("Required Expense"));
//        groupCategoryCategories.add(new GroupCategory("Up & Comers"));
//        groupCategoryCategories.add(new GroupCategory("Fun & Relax"));
//        groupCategoryCategories.add(new GroupCategory("Investing & Debt Payments"));
//        groupCategoryCategories.add(new GroupCategory("Income"));
//        groupRepository.saveAll(groupCategoryCategories);
    }

}
