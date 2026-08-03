//package com.nns.blog.script;
//
//import com.nns.blog.entities.User;
//import com.nns.blog.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.List;
//
//@Configuration
//@RequiredArgsConstructor
//public class PasswordMigrationRunner {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Bean
//    CommandLineRunner migratePasswords() {
//        return args -> {
//            List<User> users = userRepository.findAll();
//
//            for (User user : users) {
//                String password = user.getPassword();
//
//                //skip already encrypted passwords
//                if (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$")) {
//                    continue;
//                }
//                user.setPassword(passwordEncoder.encode(password));
//            }
//            userRepository.saveAll(users);
//            System.out.println("Password migration completed.");
//        };
//    }
//}
