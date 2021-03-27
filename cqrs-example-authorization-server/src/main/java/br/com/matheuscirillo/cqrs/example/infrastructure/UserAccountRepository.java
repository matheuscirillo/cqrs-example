package br.com.matheuscirillo.cqrs.example.infrastructure;

import br.com.matheuscirillo.cqrs.example.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    UserAccount findByUsername(String username);

}
