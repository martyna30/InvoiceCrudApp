package com.crud.invoices.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Table
public class AppUser implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    //private String role;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private boolean locked = false;
    private boolean enabled = false;
    private List<ConfirmationToken> confirmationTokens = new ArrayList<>();
   // @Transient
    //private Collection<? extends GrantedAuthority> authorities;


    public AppUser(String username, String email, String password,
              AppUserRole appUserRole, boolean locked, boolean enabled,
                List<ConfirmationToken> confirmationTokens) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.locked = locked;
        this.enabled = enabled;
        this.confirmationTokens = confirmationTokens;
    }

    public AppUser(String username, String password, String email, AppUserRole appUserRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    public AppUser(String username, String password, AppUserRole appUserRole) {
        this.username = username;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column
    public String getUsername() {
        return username;
    }
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            targetEntity = ConfirmationToken.class,//prawa strona relacji 1:n
            mappedBy = "appUser",//obiekt po lewej 1 :n
            cascade = {CascadeType.ALL}
    )
    public List<ConfirmationToken> getConfirmationTokens() {
        return confirmationTokens;
    }



    @Column(name = "ROLE")
    public AppUserRole getAppUserRole() {
        return appUserRole;
    }


    @Column(name = "LOCKED")
    public boolean isLocked() {
        return locked;
    }

    @Column(name = "ENABLED")
    public boolean isEnabled() {
        return enabled;
    }


    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return Arrays.stream(appUserRole.name().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    public void setAppUserRole(AppUserRole appUserRole) {
        this.appUserRole = appUserRole;
    }

    public void setConfirmationTokens(List<ConfirmationToken> confirmationTokens) {
        this.confirmationTokens = confirmationTokens;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}
