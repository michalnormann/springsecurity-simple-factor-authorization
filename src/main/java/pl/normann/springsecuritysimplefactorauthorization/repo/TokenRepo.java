package pl.normann.springsecuritysimplefactorauthorization.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.normann.springsecuritysimplefactorauthorization.model.Token;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    Token findByValue(String value);
}
