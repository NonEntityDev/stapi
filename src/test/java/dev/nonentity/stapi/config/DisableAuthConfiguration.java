package dev.nonentity.stapi.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ConditionalOnExpression("'${spring.profiles.active:default}'.contains('test')")
public class DisableAuthConfiguration {

  @Bean
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/v1/**").permitAll()
            .anyRequest().permitAll()
    ).csrf(AbstractHttpConfigurer::disable);
    return httpSecurity.build();
  }

}
