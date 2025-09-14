package com.bidcom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SistemabidcomApplication {

	public static void main(String[] args) {
            
                String rawPassword = "1234";
                String hashedPassword = "$2a$10$Qo8rounTA2FABvFYyi9preTXmedRmNgLDvZXGr0zymdxX8c11ULQa";

                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                boolean match = passwordEncoder.matches(rawPassword, hashedPassword);
                System.out.println("¿Coincide '1234' con el hash guardado?: " + match);
		SpringApplication.run(SistemabidcomApplication.class, args);
                
                
	}



}
