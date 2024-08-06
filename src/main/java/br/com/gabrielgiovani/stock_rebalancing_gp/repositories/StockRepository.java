package br.com.gabrielgiovani.stock_rebalancing_gp.repositories;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query(value = """
            SELECT s.* FROM stock s
            INNER JOIN company c ON s.company_id = c.id
            INNER JOIN sector sec ON c.sector_id = sec.id
            INNER JOIN category_sector cs ON sec.id = cs.sector_id
            INNER JOIN category cat ON cs.category_id = cat.id
            INNER JOIN portfolio p ON cat.portfolio_id = p.id
            INNER JOIN app_user u ON p.user_id = u.id
            WHERE u.username = :username
            """, nativeQuery = true)
    List<Stock> findAllByUsername(@Param("username") String username);

    @Query(value = """
            SELECT s.* FROM stock s
            INNER JOIN company c ON s.company_id = c.id
            INNER JOIN sector sec ON c.sector_id = sec.id
            INNER JOIN category_sector cs ON sec.id = cs.sector_id
            INNER JOIN category cat ON cs.category_id = cat.id
            INNER JOIN portfolio p ON cat.portfolio_id = p.id
            INNER JOIN app_user u ON p.user_id = u.id
            WHERE u.username = :username
            AND s.id = :id
            """, nativeQuery = true)
    Optional<Stock> findByUsernameAndId(@Param("username") String username, @Param("id") Integer id);
}