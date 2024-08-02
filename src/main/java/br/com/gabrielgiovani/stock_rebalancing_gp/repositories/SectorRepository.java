package br.com.gabrielgiovani.stock_rebalancing_gp.repositories;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector, Integer> {

    @Query(value = """
            SELECT s.* FROM sector s
            INNER JOIN category_sector cs ON s.id = cs.sector_id
            INNER JOIN category c ON c.id = cs.category_id
            INNER JOIN portfolio p ON c.portfolio_id = p.id
            INNER JOIN app_user u ON p.user_id = u.id
            WHERE u.username = :username
            """, nativeQuery = true)
    List<Sector> findAllByUsername(@Param("username") String username);

    @Query(value = """
            SELECT s.* FROM sector s
            INNER JOIN category_sector cs ON s.id = cs.sector_id
            INNER JOIN category c ON c.id = cs.category_id
            INNER JOIN portfolio p ON c.portfolio_id = p.id
            INNER JOIN app_user u ON p.user_id = u.id
            WHERE u.username = :username
            AND s.id = :id
            """, nativeQuery = true)
    Optional<Sector> findByUsernameAndId(@Param("username") String username, @Param("id") Integer id);
}