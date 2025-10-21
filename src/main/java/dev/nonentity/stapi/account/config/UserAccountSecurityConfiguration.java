package dev.nonentity.stapi.account.config;

import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

public class UserAccountSecurityConfiguration {


  @Bean
  public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, @Qualifier("userAccountPasswordEncoder") PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.with(OAuth2AuthorizationServerConfigurer.authorizationServer(), Customizer.withDefaults()).build();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    JWKSet jwkSet = new JWKSet(this.generateRSAKey());
    return (JWKSelector jwkSelector, SecurityContext securityContext) -> jwkSelector.select(jwkSet);
  }

  private KeyPair generateRSAKeyPair() {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      return keyPairGenerator.generateKeyPair();

    } catch (NoSuchAlgorithmException ex) {
      throw new IllegalArgumentException(ex);

    }
  }

  private RSAKey generateRSAKey() {
    KeyPair keyPair = this.generateRSAKeyPair();
    return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
            .privateKey(keyPair.getPrivate())
            .keyID(UUID.randomUUID().toString())
            .build();
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().build();
  }

}
