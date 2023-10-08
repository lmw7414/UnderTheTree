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
    private String content;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private UserAccount user;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static MessageComment of (String content, Message message) {
        MessageComment messageComment = new MessageComment();
        messageComment.setContent(content);
        messageComment.setMessage(message);
        return messageComment;
    }
}
