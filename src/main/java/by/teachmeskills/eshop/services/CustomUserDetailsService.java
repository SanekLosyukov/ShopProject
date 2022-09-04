package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.UserRepository;
import by.teachmeskills.eshop.utils.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            if (Objects.equals(username, "admin")) {
                Set<GrantedAuthority> roles = new HashSet<>();
                roles.add(new SimpleGrantedAuthority(UserRoleEnum.ADMIN.name()));
                userDetails = new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), roles);
            } else {
                Set<GrantedAuthority> roles = new HashSet<>();
                roles.add(new SimpleGrantedAuthority(UserRoleEnum.USER.name()));
                userDetails = new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), roles);
            }
        } else {
            throw new UsernameNotFoundException("User wasn't found");
        }
        return userDetails;
    }
}


