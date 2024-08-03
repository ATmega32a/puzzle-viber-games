package ru.gamechatbot.puzzlevibergames;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.gamechatbot.puzzlevibergames.games.Game;
import ru.gamechatbot.puzzlevibergames.games.TagGame;
import ru.gamechatbot.puzzlevibergames.games.TicTacToe;


//@SpringBootTest
//@ComponentScan("ru.gamechatbot.puzzlevibergames.games")
//@Import(TicTacToeConfiguration.class)
class PuzzleViberGamesApplicationTests {

    private Game game(Class<? extends Game> gameClass){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(gameClass);
        return context.getBean(gameClass);
    }

//    TicTacToe ticTacToe;
//
//    @Autowired
//    public void setTicTacToe(TicTacToe ticTacToe) {
//        this.ticTacToe = ticTacToe;
//    }
//
//
//    public TicTacToe getTicTacToe() {
//        return ticTacToe;
//    }

    @Test
    void contextLoads() {
        Game ticTacToe1 = game(TicTacToe.class);
        Game ticTacToe2 = game(TicTacToe.class);
        Game ticTacToe3 = game(TicTacToe.class);

        Game tagGame1 = game(TagGame.class);
        Game tagGame2 = game(TagGame.class);
        Game tagGame3 = game(TagGame.class);

        System.out.println("ticTacToe1.hashCode() = " + ticTacToe1.hashCode());
        System.out.println("ticTacToe2.hashCode() = " + ticTacToe2.hashCode());
        System.out.println("ticTacToe3.hashCode() = " + ticTacToe3.hashCode());

        System.out.println("tagGame1.hashCode() = " + tagGame1.hashCode());
        System.out.println("tagGame2.hashCode() = " + tagGame2.hashCode());
        System.out.println("tagGame3.hashCode() = " + tagGame3.hashCode());




//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TicTacToe.class);
//        TicTacToe ticTacToe1 = context.getBean("ticTacToe", TicTacToe.class);
//        TicTacToe ticTacToe2 = context.getBean("ticTacToe", TicTacToe.class);
//        TicTacToe ticTacToe3 = context.getBean("ticTacToe", TicTacToe.class);
//        System.out.println("ticTacToe1.hashCode() = " + ticTacToe1.hashCode());
//        System.out.println("ticTacToe2.hashCode() = " + ticTacToe2.hashCode());
//        System.out.println("ticTacToe3.hashCode() = " + ticTacToe3.hashCode());
//        TicTacToe t1 = getTicTacToe();
//        TicTacToe t2 = getTicTacToe();
//        TicTacToe t3 = getTicTacToe();
//        boolean equals = t1.equals(t2);
//        boolean isTrue = t1 == t2;
//        Assert.isTrue(equals);
//        Assert.isTrue(isTrue);
//        System.out.println("equals = " + equals);
//        System.out.println("isTrue = " + isTrue);
//        System.out.println("subscriber.hashCode() = " + t1.hashCode());
//        System.out.println("subscriber.hashCode() = " + t2.hashCode());
//        System.out.println("subscriber.hashCode() = " + t3.hashCode());
//
    }

}
