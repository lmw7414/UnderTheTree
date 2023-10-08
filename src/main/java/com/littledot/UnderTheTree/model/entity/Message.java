package com.littledot.UnderTheTree.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@SQLDelete(sql = "UPDATE message SET deleted_at NOW() where id=?")
@Where(clause = "deleted_at is NULL")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private int likes;

    @OneToMany
    private Set<Interest> interests = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("registeredAt DESC")
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private Set<MessageComment> messageComments = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_email")
    UserAccount user;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "expired_at")
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
