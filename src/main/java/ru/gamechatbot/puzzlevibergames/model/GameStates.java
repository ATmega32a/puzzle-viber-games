package ru.gamechatbot.puzzlevibergames.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gamechatbot.puzzlevibergames.games.Game;
import ru.gamechatbot.puzzlevibergames.games.TagGame;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Component
//@Scope("prototype")
@Entity
public class GameStates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String owner;

    @OneToOne
    private TagGame tagGame;



//    @Nullable
//    @Embedded
//    private TicTacToe TicTacToe;

    public GameStates(String owner, Game game) {
        this.owner = owner;
        if (game instanceof TagGame)
            tagGame = (TagGame) game;
//        if (game instanceof TicTacToe)
//            TicTacToe = (TicTacToe) game;
    }

    @Override
    public String toString() {
        return "GameStates{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", TagGame=" + tagGame +
//                ", TicTacToe=" + TicTacToe +
                '}';
    }
}
