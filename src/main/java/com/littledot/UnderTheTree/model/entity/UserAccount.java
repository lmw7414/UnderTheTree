package com.littledot.UnderTheTree.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Table(name = "user_account")
@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE user_account SET deleted_at = NOW() where email=?")
@Where(clause = "deleted_at is NULL")
public class UserAccount {

    @Id
    private String email;

    @Column(nullable = false, name = "nickname")
    private String nickname;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(columnDefinition = "int default 5")
    private int chance;

    @ToString.Exclude
    @OrderBy("registeredAt DESC")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> messages = new LinkedHashSet<>();

    @ToString.Exclude
    @JoinTable(
            name = "user_interests",  //연결테이블 이름
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    @ManyToMany
    private Set<Interest> interests = new LinkedHashSet<>();

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
