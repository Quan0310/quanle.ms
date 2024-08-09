package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.models.ERole;
import com.example.demo.models.Role;
import com.example.demo.repositories.RoleRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Checking roles...");
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            System.out.println("Roles not found, creating roles...");
            roleRepository.save(new Role(ERole.ROLE_USER));
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
            System.out.println("Roles created.");
        } else {
            System.out.println("Roles already exist.");
        }
    }
}