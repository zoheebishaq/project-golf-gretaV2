package com.example.projectgolfgreta.service;

import com.example.projectgolfgreta.dao.securite.UserRepository;
import com.example.projectgolfgreta.domain.Group;
import com.example.projectgolfgreta.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public JpaUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        log.info("Recherche utilisateur: "+username);
        if(user == null){
            throw new UsernameNotFoundException("Utilisateur introuvable : |"+username+"|");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();

        for(Group grp: user.getGroups()){
            log.info("{username: "+username+"| grp: "+grp.getRole());
            authorities.add(new SimpleGrantedAuthority(grp.getRole()));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        log.info("Test pwd : "+encoder.encode("passe"));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                authorities);
    }

}
