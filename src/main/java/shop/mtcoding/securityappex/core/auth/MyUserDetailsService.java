package shop.mtcoding.securityappex.core.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.mtcoding.securityappex.model.User;
import shop.mtcoding.securityappex.model.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // /login + POST + x-www-form-urlencoded + username, password
    // Authentication 객체 만들어짐
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOP = userRepository.findByUsername(username);
        if (userOP.isPresent()) {
            return new MyUserDetails(userOP.get());
        } else {
            return null;
        }
    }
}
