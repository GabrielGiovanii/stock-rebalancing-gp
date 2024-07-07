package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Wallet;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WalletService implements CRUDService<Wallet> {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Override
    public List<Wallet> findByFilters(Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<Wallet> findById(Integer id) {
        return walletRepository.findById(id);
    }

    @Override
    public void saveOrUpdate(Wallet entity) {
        walletRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        walletRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Wallet> entities) {
        walletRepository.saveAll(entities);
    }
}
