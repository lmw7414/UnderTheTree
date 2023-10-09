package com.littledot.UnderTheTree.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "message")
@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE message SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String imageUrl;

    @ColumnDefault("0")
    private int likes;

    @OneToMany
    @JoinColumn(name = "message_id")
    private Set<Interest> interests = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("registeredAt DESC")
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MessageComment> messageComments = new LinkedHashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_email")
    UserAccount user;

    @Column(nullable = false, updatable = false, name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = LocalDateTime.from(Instant.now());
        this.expiredAt = LocalDateTime.from(Instant.now().plus(5, ChronoUnit.DAYS));
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = LocalDateTime.from(Instant.now());
    }

    public static Message of(String title, String content, UserAccount userAccount) {
        Message message = new Message();
        message.setTitle(title);
        message.setContent(content);
        message.setUser(userAccount);
        return message;
    }
}
