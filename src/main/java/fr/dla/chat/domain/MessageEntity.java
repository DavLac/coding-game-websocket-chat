package fr.dla.chat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Message")
@Entity
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    private Instant timestamp;

    private String roomId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId", nullable = false)
    private UserEntity user;

    public MessageEntity(String content, Instant timestamp, String roomId, UserEntity user) {
        this.content = content;
        this.timestamp = timestamp;
        this.roomId = roomId;
        this.user = user;
    }
}
