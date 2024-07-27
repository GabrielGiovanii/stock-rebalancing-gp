package br.com.gabrielgiovani.stock_rebalancing_gp.repositories;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = """
            SELECT c.* FROM category c
            INNER JOIN portfolio p ON c.portfolio_id = p.id
            INNER JOIN app_user u ON p.user_id = u.id
            WHERE u.username = :username
            """, nativeQuery = true)
    List<Category> findAllByUsername(@Param("username") String username);

    @Query(value = """
            SELECT c.* FROM category c
            INNER JOIN portfolio p ON c.portfolio_id = p.id
            INNER JOIN app_user u ON p.user_id = u.id
            WHERE u.username = :username
            AND c.id = :id
            """, nativeQuery = true)
    Optional<Category> findByUsernameAndId(@Param("username") String username, @Param("id") Integer id);
}