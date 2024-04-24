package miu.edu.auction.service.security;

import lombok.RequiredArgsConstructor;
import miu.edu.auction.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with email: " + username + " Not Found"));
        //user.setProducts(new ArrayList<>());
        //user.setPassword("");
        return user;
    }
}
