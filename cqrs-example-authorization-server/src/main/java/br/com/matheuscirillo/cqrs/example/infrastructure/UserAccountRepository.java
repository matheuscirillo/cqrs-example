package br.com.matheuscirillo.cqrs.example.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.matheuscirillo.cqrs.example.domain.entity.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    
    UserAccount findByUsername(String username);
    
}
