package pl.normann.springsecuritysimplefactorauthorization.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.normann.springsecuritysimplefactorauthorization.model.AppUser;
import pl.normann.springsecuritysimplefactorauthorization.model.Token;
import pl.normann.springsecuritysimplefactorauthorization.repo.AppUserRepo;
import pl.normann.springsecuritysimplefactorauthorization.repo.TokenRepo;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
public class UserService {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;
    private TokenRepo tokenRepo;
    private MailService mailService;

    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, TokenRepo tokenRepo, MailService mailService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public void addUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole("ROLE_USER");
        appUserRepo.save(appUser);
        sendToken(appUser);
    }

    private void sendToken(AppUser appUser) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setAppUser(appUser);
        tokenRepo.save(token);
        String utl = "http://localhost:8080/token?value=" + tokenValue;
        try {
            mailService.sendMail(appUser.getEmail(), "Potwierdzenie rejestracji","Cześć " + appUser.getUsername()+ " ,\nKliknij w poniższy link, aby potwierdzić rejestrację.\n" + utl , false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
