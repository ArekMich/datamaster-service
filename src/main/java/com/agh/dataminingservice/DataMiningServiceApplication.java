package com.agh.dataminingservice;

import com.agh.dataminingservice.model.Role;
import com.agh.dataminingservice.model.RoleName;
import com.agh.dataminingservice.model.User;
import com.agh.dataminingservice.repository.RoleRepository;
import com.agh.dataminingservice.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        DataMiningServiceApplication.class,
        Jsr310JpaConverters.class
})
public class DataMiningServiceApplication {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DataMiningServiceApplication.class, args);
    }

    @Bean
    InitializingBean initializingDatabase(){
        return () -> {

            roleRepository.save(new Role(RoleName.ROLE_USER));
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));

            userRepository.save(
                    new User("Arek Michalik", "arekmich","arkadiusz.michalik@gmail.com", passwordEncoder.encode("arekmich")));
            userRepository.save(
                    new User("Eryk Jarocki", "erykjaro","eryk.jarocki@gmail.com", passwordEncoder.encode("erykjaro")));

        };
    }
}
