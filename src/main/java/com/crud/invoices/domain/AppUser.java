package com.crud.invoices.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class AppUser {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean locked = false;
    private boolean enabled = false;
    //Seller seller;
    private List<ConfirmationToken> confirmationTokens = new ArrayList<>();

    //@Transient
    //private Collection<? extends GrantedAuthority> authorities;

    //Collection<SimpleGrantedAuthority> authorities;
    public AppUser(String username, String email, String password, String role,
                   boolean locked, boolean enabled,
                List<ConfirmationToken> confirmationTokens) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = locked;
        this.enabled = enabled;
        this.confirmationTokens = confirmationTokens;

    }

    /*public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }*/

    public AppUser(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;

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
            cascade = {CascadeType.ALL})
            //@JoinColumn(name = "APP_USER_ID")
    public List<ConfirmationToken> getConfirmationTokens() {
        return confirmationTokens;
    }

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SELLER_ID")
    public Seller getSeller() {
        return seller;
    }*/

    @Column(name = "ROLE")
    public String getRole() {
        return role;
    }


    @Column(name = "LOCKED")
    public boolean isLocked() {
        return locked;
    }

    @Column(name = "ENABLED")
    public boolean isEnabled() {
        return enabled;
    }


    public void setRole(String role) {
        this.role = role;
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
