package ru.gamechatbot.puzzlevibergames.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gamechatbot.puzzlevibergames.model.GameStates;

import java.util.Optional;

public interface GameStatesRepository extends CrudRepository<GameStates, Long> {
    Optional<GameStates> findByOwner(String owner);
    Optional<GameStates> findGameStatesByOwner(String owner);
}
