package com.process.auth.core.security.service;

import com.process.auth.app.sys.mapper.PsAppUserMapper;
import com.process.auth.core.security.domain.AbstractAppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.UnaryOperator;

/**
 * @author Danfeng
 * @since 2018/11/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PsUserDetailsService implements UserDetailsService {

    private final PsAppUserMapper psAppUserMapper;

    private UnaryOperator<UserDetails> unaryOperator = (userDetails -> userDetails);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AbstractAppUser entityByName = psAppUserMapper.getEntityByName(username);
        if (entityByName != null) {
            return entityByName;
        }
        throw new UsernameNotFoundException("user not find: " + username);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("password");
        System.out.println("password== " + password);
    }

}
