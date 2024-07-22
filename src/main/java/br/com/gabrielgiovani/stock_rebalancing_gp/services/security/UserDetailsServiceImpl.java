package br.com.gabrielgiovani.stock_rebalancing_gp.services.security;

import br.com.gabrielgiovani.stock_rebalancing_gp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByName(username)
                .map(UserAuth::new)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }
}