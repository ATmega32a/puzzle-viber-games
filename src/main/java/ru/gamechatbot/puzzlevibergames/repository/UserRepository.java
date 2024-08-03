package ru.gamechatbot.puzzlevibergames.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gamechatbot.puzzlevibergames.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {


    Optional<User> findByUserId(String userId);
    User findByUserName(String username);

}
