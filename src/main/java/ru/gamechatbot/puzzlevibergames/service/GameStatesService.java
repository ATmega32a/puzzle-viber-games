package ru.gamechatbot.puzzlevibergames.service;

import org.springframework.stereotype.Service;
import ru.gamechatbot.puzzlevibergames.model.GameStates;
import ru.gamechatbot.puzzlevibergames.repository.GameStatesRepository;

import java.util.Optional;

@Service
public class GameStatesService {
    private final GameStatesRepository gameStatesRepository;

    public GameStatesService(GameStatesRepository gameStatesRepository) {
        this.gameStatesRepository = gameStatesRepository;

    }

    public void saveStateGame(GameStates gameStates){
        if (gameStatesRepository.findByOwner(gameStates.getOwner()).isEmpty())
            gameStatesRepository.save(gameStates);
    }

    public Optional<GameStates> findFirstByOwner(String owner){
        return gameStatesRepository.findByOwner(owner);
    }

    public Optional<GameStates> findGameStatesByOwner(String owner){
        return gameStatesRepository.findGameStatesByOwner(owner);
    }
}
