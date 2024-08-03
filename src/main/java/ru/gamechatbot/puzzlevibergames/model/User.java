package ru.gamechatbot.puzzlevibergames.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(unique = true)
    @Column
    private String userId;
    @Column
    private String userName;
    @Column
    private String avatar;
//
//    @ManyToMany
//    @JoinTable(
//            name = "user_subscriptions",
//            joinColumns = { @JoinColumn(name = "channel_id") },
//            inverseJoinColumns = { @JoinColumn(name = "subscriber_id") }
//    )
//    private Set<User> subscribers = new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(
//            name = "user_subscriptions",
//            joinColumns = { @JoinColumn(name = "subscriber_id") },
//            inverseJoinColumns = { @JoinColumn(name = "channel_id") }
//    )
//    private Set<User> subscriptions = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}