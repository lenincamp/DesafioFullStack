package com.lcampoverde.administrativo.cliente;

import com.lcampoverde.administrativo.cliente.model.nopersist.UserPrincipal;
import com.lcampoverde.administrativo.cliente.security.JwtTokenProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class JwtTokenProviderTest {
    @TestConfiguration
    static class JwtTokenPoviderTestContextConfiguration {
        @Bean
        public JwtTokenProvider jwtTokenProvider() {
            return new JwtTokenProvider();
        }
    }

    @MockBean
    private Authentication authentication;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQzNzI5ODAzLCJleHAiOjE1NDQzMzQ2MDN9._k7WbtRjqEMEe8X_AqUAs7YyMhaaCIS7i9WbBwcN3iCRRDF4di3SYjKUi_sfW3NetQ8YTzHsK3eh2D0i3TCjFw";

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("jwt.jwtSecret", "jwtSecret");
        System.setProperty("jwt.jwtExpirationInMs", "604800000");
    }

    @Before
    public void setUp() {
        UserPrincipal userPrincipal = UserPrincipal.builder().id(1L).build();
        Mockito.when(authentication.getPrincipal()).thenReturn(userPrincipal);
    }

    @Test
    public void generateToken() {
        String tokenAuth = jwtTokenProvider.generateToken(authentication);
        Long id = jwtTokenProvider.getUserIdFromJWT(tokenAuth);
        boolean validateToken = jwtTokenProvider.validateToken(tokenAuth);
        Assert.assertNotNull(tokenAuth);
        assertThat(id).isEqualTo(1L);
        Assert.assertTrue(validateToken);
    }

    @Test
    public void validateToken() {
        String tokenValid = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQzNzI4ODczLCJleHAiOjE1NDM3NjEzMDV9.QFfjWnxPRatuT_sw9OtzYoCXOtLqjWzINZ-oJJ_zhQCN61fA4zEInktUeoR2tAnYp2vmYcakv44tEjco_YVx_g";
        boolean validateToken = jwtTokenProvider.validateToken(tokenValid);
        Assert.assertFalse(validateToken);
    }

    @Test
    public void getCreatedDateFromToken() {
        Date createdDate = jwtTokenProvider.getCreatedDateFromToken(token);
        Assert.assertNotNull(createdDate);
    }

    @Test
    public void getExpirationDateFromToken() {
        Date createdDate = jwtTokenProvider.getExpirationDateFromToken(token);
        Assert.assertNotNull(createdDate);
    }

    @Test
    public void canTokenBeRefreshed() {
        Boolean validate = jwtTokenProvider.canTokenBeRefreshed(token, null);
        Boolean validateFalse = jwtTokenProvider.canTokenBeRefreshed(token, new Date());
        Assert.assertTrue(validate);
        Assert.assertFalse(validateFalse);

    }

    @Test
    public void refreshToken() {
        Assert.assertNotNull(jwtTokenProvider.refreshToken(token));
        Assert.assertNull(jwtTokenProvider.refreshToken(null));
        Assert.assertNull(jwtTokenProvider.refreshToken("abcd"));
    }
}