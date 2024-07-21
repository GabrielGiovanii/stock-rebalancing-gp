package br.com.gabrielgiovani.stock_rebalancing_gp.repositories;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);
}