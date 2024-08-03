package ru.gamechatbot.puzzlevibergames.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gamechatbot.puzzlevibergames.games.TagGame;

public interface GameRepository extends CrudRepository<TagGame, Long> {

}
