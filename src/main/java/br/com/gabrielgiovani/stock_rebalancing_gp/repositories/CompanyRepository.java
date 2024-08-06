package br.com.gabrielgiovani.stock_rebalancing_gp.repositories;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query(value = """
            SELECT c.* FROM company c
            INNER JOIN sector s ON c.sector_id = s.id
            INNER JOIN category_sector cs ON s.id = cs.sector_id
            INNER JOIN category cat ON cat.id = cs.category_id
            INNER JOIN portfolio p ON cat.portfolio_id = p.id
            INNER JOIN app_user u ON p.user_id = u.id
            WHERE u.username = :username
            """, nativeQuery = true)
    List<Company> findAllByUsername(@Param("username") String username);

    @Query(value = """
            SELECT c.* FROM company c
            INNER JOIN sector s ON c.sector_id = s.id
            INNER JOIN category_sector cs ON s.id = cs.sector_id
            INNER JOIN category cat ON cat.id = cs.category_id
            INNER JOIN portfolio p ON cat.portfolio_id = p.id
            INNER JOIN app_user u ON p.user_id = u.id
            WHERE u.username = :username
            AND c.id = :id
            """, nativeQuery = true)
    Optional<Company> findByUsernameAndId(@Param("username") String username, @Param("id") Integer id);
}