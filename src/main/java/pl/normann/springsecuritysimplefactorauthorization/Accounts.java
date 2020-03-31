package pl.normann.springsecuritysimplefactorauthorization;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.normann.springsecuritysimplefactorauthorization.model.AppUser;
import pl.normann.springsecuritysimplefactorauthorization.repo.AppUserRepo;

@Configuration
public class Accounts {

    private AppUserRepo appUserRepo;

    public Accounts(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;

        AppUser appUser = new AppUser();
        appUser.setUsername("Admin");
        appUser.setPassword(passwordEncoder.encode("admin"));
        appUser.setRole("ROLE_ADMIN");
        appUser.setEnabled(true);
        appUserRepo.save(appUser);

        AppUser appUser2 = new AppUser();
        appUser2.setUsername("User");
        appUser2.setPassword(passwordEncoder.encode("user"));
        appUser2.setRole("ROLE_USER");
        appUser2.setEnabled(true);
        appUserRepo.save(appUser2);
    }
}
