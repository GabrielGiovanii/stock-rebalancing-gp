package br.com.gabrielgiovani.stock_rebalancing_gp.repositories;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {

    @Query(value = """
            SELECT p.* FROM Portfolio p
                INNER JOIN app_user u
                ON p.user_id = u.id
                WHERE u.username = :username""", nativeQuery = true)
    List<Portfolio> findAllByUsername(@Param("username") String username);

    @Query(value = """
            SELECT p.* FROM Portfolio p
                INNER JOIN app_user u
                ON p.user_id = u.id
                WHERE u.username = :username
                AND p.id = :id""", nativeQuery = true)
    Optional<Portfolio> findByUsernameAndId(@Param("username") String username, @Param("id") Integer id);
}