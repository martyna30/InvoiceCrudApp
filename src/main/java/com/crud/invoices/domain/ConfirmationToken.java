package com.crud.invoices.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedQuery(name = "ConfirmationToken.findByUserId",
        query = "FROM ConfirmationToken WHERE appUser.id = :USER_ID"
)

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CONFIRMATION_TOKEN")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOKEN_ID", unique = true)
    private Long id;
    @Column(name = "TOKEN")
    private String token;

    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private AppUser appUser;


    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,
                             AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }
}
