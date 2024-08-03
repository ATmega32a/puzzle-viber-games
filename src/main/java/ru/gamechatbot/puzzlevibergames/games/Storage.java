package ru.gamechatbot.puzzlevibergames.games;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//@Data
@Data
@NoArgsConstructor
@Component
public class Storage {
    public ConcurrentMap<String, Game> storage = new ConcurrentHashMap<>();
}