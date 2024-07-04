package br.com.gabrielgiovani.stock_rebalancing_gp.repositories;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
}
