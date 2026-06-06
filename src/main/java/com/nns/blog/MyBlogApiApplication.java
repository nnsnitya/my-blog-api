package com.nns.blog;

import com.nns.blog.constants.AppConstants;
import com.nns.blog.entities.Role;
import com.nns.blog.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@EnableCaching
@SpringBootApplication
public class MyBlogApiApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MyBlogApiApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("xyz1234: "+ this.passwordEncoder.encode("xyz1234"));
        try {
            Role role = new Role();
            role.setId(AppConstants.ADMIN_USER);
            role.setName("ROLE_ADMIN");

            Role role1 = new Role();
            role1.setId(AppConstants.NORMAL_USER);
            role1.setName("ROLE_NORMAL");

            List<Role> roles = List.of(role, role1);
            List<Role> result = roleRepository.saveAll(roles);

            result.forEach(r -> {
                System.out.println(r.getName());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
