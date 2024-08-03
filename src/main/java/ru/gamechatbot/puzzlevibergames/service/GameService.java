package ru.gamechatbot.puzzlevibergames.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gamechatbot.puzzlevibergames.games.TagGame;
import ru.gamechatbot.puzzlevibergames.repository.GameRepository;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;

//    public GameService(GameRepository gameRepository) {
//        this.gameRepository = gameRepository;
//    }

    public void save(TagGame game){
        gameRepository.save(game);
    }
}
