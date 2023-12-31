package com.littledot.UnderTheTree.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "message_comment")
@Entity
@Getter
@Setter
public class MessageComment {

    @Id
    private Long id;

    @Column(nullable = false, length = 512)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_email")
    private UserAccount user;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static MessageComment of(String content, Message message) {
        MessageComment messageComment = new MessageComment();
        messageComment.setContent(content);
        messageComment.setMessage(message);
        return messageComment;
    }
}
