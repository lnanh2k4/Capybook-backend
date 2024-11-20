package fa24.swp391.se1802.group3.capybook.configs;

import fa24.swp391.se1802.group3.capybook.daos.AccountDAO;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
public class ApplicationInitConfig {


    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Bean
    ApplicationRunner applicationRunner(AccountDAO accountDAO) {

        return args -> {
            if (accountDAO.findByUsername("admin") == null) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setUsername("admin");
                accountDTO.setPassword(passwordEncoder.encode("admin"));
                accountDTO.setRole(0);
                accountDAO.addAccount(accountDTO);
                log.warn("admin user has been created with default password is admin. Please change password!");
            }
        };
    }
}
