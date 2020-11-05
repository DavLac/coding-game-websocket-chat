package fr.dla.chat.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<MessageEntity> messageEntities;

    public UserEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
