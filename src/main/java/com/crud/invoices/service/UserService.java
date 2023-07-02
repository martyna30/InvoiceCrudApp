package com.crud.invoices.service;

import com.crud.invoices.domain.AppUser;
import com.crud.invoices.domain.ConfirmationToken;
import com.crud.invoices.exception.EmailNotFoundException;
import com.crud.invoices.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return userRepository.findByUsername(username).orElseThrow(() ->
            new UsernameNotFoundException("User with such name doesn't exist"));
        //Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            //authorities.add(new SimpleGrantedAuthority(user.get().getAppUserRole().name()));
            //return new AppUser(user.get().getUsername(),
                   // user.get().getPassword(), authorities);
        }


    public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->
                new EmailNotFoundException("User with such email doesn't exist"));
        }

    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
    }

    public String signUpUser(AppUser user) {
        Optional<AppUser> users = userRepository.findByEmail(user.getEmail());
        if(users.isPresent()) {
            Optional<ConfirmationToken> confirmation = confirmationTokenService.getConfirmationTokenForUser(user);
            if (confirmation.isPresent()) {
                if (confirmation.get().getConfirmedAt() != null) {
                    throw new IllegalStateException("email already taken and confirmed");
                }
            }
            else {
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
    /*public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }*/

}
    /*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User with such name doesn't exist");
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.get().getRole()));
            return new MyUserDetails(user.get().getUsername(),
                    user.get().getPassword(), authorities);
        }
    }
    public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
        Optional<User> users = userRepository.findByEmail(email);
        return users.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with such email doesn't exist"));
    }


    public String signUpUser(User user) {
        Optional<User> users = userRepository.findByEmail(user.getEmail());
        if(users.isPresent()) {
            Optional<ConfirmationToken> confirmation = confirmationTokenService.getConfirmationTokenForUser(user);
            if (confirmation.isPresent()) {
                if (confirmation.get().getConfirmedAt() != null) {
                    throw new IllegalStateException("email already taken and confirmed");
                }
            }
            else {
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


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return Optional.ofNullable(user.orElseThrow(() -> new RuntimeException("User doesn't exist")));
    }

    public UserDetails saveUser(User user) {
        userRepository.save(user);
        return new MyUserDetails(user);
    }




    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }*/



