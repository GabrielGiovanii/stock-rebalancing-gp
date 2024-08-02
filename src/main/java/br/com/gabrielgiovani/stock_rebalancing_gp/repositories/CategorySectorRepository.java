package br.com.gabrielgiovani.stock_rebalancing_gp.repositories;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.CategorySector;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.pk.CategorySectorPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategorySectorRepository extends JpaRepository<CategorySector, CategorySectorPK> {

    @Query(value = """
            SELECT cs.* FROM category_sector cs
            INNER JOIN category c ON cs.category_id = c.id
            INNER JOIN portfolio p ON c.portfolio_id = p.id
            INNER JOIN app_user u ON p.user_id = u.id
            WHERE u.username = :username
            """, nativeQuery = true)
    List<CategorySector> findAllByUsername(@Param("username") String username);

    @Query(value = """
        SELECT cs.* FROM category_sector cs
        INNER JOIN category c ON cs.category_id = c.id
        INNER JOIN portfolio p ON c.portfolio_id = p.id
        INNER JOIN app_user u ON p.user_id = u.id
        WHERE u.username = :username
        AND cs.category_id = :categoryId
        AND cs.sector_id = :sectorId
        """, nativeQuery = true)
    Optional<CategorySector> findAllByUsernameAndId(@Param("username") String username,
                                                    @Param("categoryId") Integer categoryId,
                                                    @Param("sectorId") Integer sectorId);
}