package com.littledot.UnderTheTree.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;


@Table(name = "user_account")
@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE user_account SET deleted_at NOW() where email=?")
@Where(clause = "deleted_at is NULL")
public class UserAccount {

    @Id
    private String email;
    private String nickname;
    private String password;
    private int chance;
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, updatable = false, name = "registered_at")
    private LocalDateTime registeredAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = LocalDateTime.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = LocalDateTime.from(Instant.now());
    }

    public static UserAccount of(String email, String password) {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(email);
        userAccount.setPassword(password);
        userAccount.setChance(5);
        return userAccount;
    }

}