package com.lcampoverde.administrativo.core;

import com.lcampoverde.administrativo.cliente.model.User;
import com.lcampoverde.administrativo.cliente.repository.UserRepository;
import com.lcampoverde.administrativo.core.service.CustomUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest {

    @TestConfiguration
    static class CustomUserDetailsServiceTestContextConfiguration {
        @Bean
        public CustomUserDetailsService customUserDetailsService() {
            return new CustomUserDetailsService();
        }
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    private static String userNameDefault = "default_user";

    @Before
    public void setUp() {
        User user = User.builder()
                .userName(userNameDefault)
                .id(1L)
                .enabled(Boolean.TRUE)
                .userGroups(new HashSet<>())
                .roles(new HashSet<>())
                .build();
        Mockito.when(userRepository.findByUserNameOrEmail(user.getUserName(), user.getUserName()))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByIdIn(Arrays.asList(user.getId()))).thenReturn(Arrays.asList(user));
        Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.existsByUserName(user.getUserName())).thenReturn(Boolean.TRUE);
        Mockito.when(userRepository.existsByUserName(user.getEmail())).thenReturn(Boolean.TRUE);
    }

    @Test
    public void loadUserByUsername() {
        UserDetails user = customUserDetailsService.loadUserByUsername(userNameDefault);
        assertThat(user.getUsername()).isEqualTo(userNameDefault);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserNameNotFound() {
        String name = "defaultuser";
        customUserDetailsService.loadUserByUsername(name);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserNameDisabled() {
        User user = User.builder()
                .enabled(Boolean.FALSE)
                .userGroups(new HashSet<>())
                .roles(new HashSet<>())
                .build();
        Mockito.when(userRepository.findByUserNameOrEmail(userNameDefault, userNameDefault))
                .thenReturn(Optional.of(user));
        customUserDetailsService.loadUserByUsername(userNameDefault);
    }

    @Test
    public void loadUserById() {
        UserDetails user = customUserDetailsService.loadUserById(1L);
        assertThat(user.getUsername()).isEqualTo(userNameDefault);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByIdNotFound() {
        customUserDetailsService.loadUserById(2L);
    }
}