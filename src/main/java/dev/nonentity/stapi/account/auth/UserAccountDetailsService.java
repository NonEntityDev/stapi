package dev.nonentity.stapi.account.auth;

import dev.nonentity.stapi.account.domain.UserAccount;
import dev.nonentity.stapi.account.repository.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAccountDetailsService implements UserDetailsService {

  private final UserAccountRepository userAccountRepository;

  public UserAccountDetailsService(UserAccountRepository userAccountRepository) {
    this.userAccountRepository = userAccountRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserAccount userAccount = this.userAccountRepository.findByAlias(username)
            .orElseThrow(() -> new UsernameNotFoundException("User account not found."));
    return User.builder()
            .username(userAccount.getAlias())
            .password(userAccount.getSecret())
            .roles("userAccount")
            .build();
  }

}
