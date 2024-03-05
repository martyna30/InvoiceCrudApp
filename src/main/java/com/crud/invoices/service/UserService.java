package com.crud.invoices.service;

import com.crud.invoices.domain.AppUser;
import com.crud.invoices.domain.ConfirmationToken;
import com.crud.invoices.domain.MyUserDetails;
import com.crud.invoices.exception.EmailNotFoundException;
import com.crud.invoices.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ConfirmationTokenService confirmationTokenService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with such name doesn't exist")));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.get().getRole()));
        return new MyUserDetails(appUser.get().getUsername(), appUser.get().getPassword(), authorities);
    }


    public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
        Optional<AppUser> users = userRepository.findByEmail(email);
        return users.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with such email doesn't exist"));
    }


    public UserDetails saveUser(AppUser appUser) {
        Optional<AppUser>userIn =userRepository.findByUsername(appUser.getUsername());
        if(userIn.isPresent()) {
            appUser.setId(userIn.get().getId());
            userRepository.save(userIn.get());
        }else {
            userRepository.save(appUser);
        }
        return new MyUserDetails(appUser);
    }



    public String signUpUser(AppUser user) {
        Optional<AppUser> users = userRepository.findAppUserByUsernameIgnoreCase(user.getUsername());
        if(users.isPresent()) {
            Optional<ConfirmationToken> confirmationTokeWithConfirmed = confirmationTokenService.getConfirmationTokenForUser(users.get());
            if (confirmationTokeWithConfirmed.isPresent()) {
                throw new IllegalStateException("email already taken and confirmed");
            } else if (!confirmationTokeWithConfirmed.isPresent()) {
                String token = UUID.randomUUID().toString();

                ConfirmationToken confirmationToken = new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        users.get()
                );
                confirmationTokenService.saveConfirmationToken(confirmationToken);
                return token;
            }
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void deleteUser(Long userId) {
       userRepository.deleteById(userId);
    }

    public Optional<AppUser> getUser(Long userId) {
        Optional<AppUser> user = userRepository.findById(userId);
        return Optional.ofNullable(user.orElseThrow(() -> new RuntimeException("User doesn't exist")));
    }

    public Optional<AppUser> getAppUserByUsername(String username) {
        Optional<AppUser> user = userRepository.findAppUserByUsernameIgnoreCase(username);
        return Optional.ofNullable(user.orElseThrow(()-> new RuntimeException(("User doesn't exist"))));
    }
    /*public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }*/

}




