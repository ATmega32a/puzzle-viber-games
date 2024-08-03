package ru.gamechatbot.puzzlevibergames;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.google.common.util.concurrent.Futures;
import com.viber.bot.Request;
import com.viber.bot.Response;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;
import com.viber.bot.message.*;
import com.viber.bot.profile.BotProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.gamechatbot.puzzlevibergames.games.Game;
import ru.gamechatbot.puzzlevibergames.games.Storage;
import ru.gamechatbot.puzzlevibergames.games.TagGame;
import ru.gamechatbot.puzzlevibergames.games.TicTacToe;
import ru.gamechatbot.puzzlevibergames.model.GameStates;
import ru.gamechatbot.puzzlevibergames.service.GameService;
import ru.gamechatbot.puzzlevibergames.service.GameStatesService;
import ru.gamechatbot.puzzlevibergames.service.UserService;
import ru.gamechatbot.puzzlevibergames.viber.Keyboard;
import ru.gamechatbot.puzzlevibergames.viber.Keyboards;
import ru.gamechatbot.puzzlevibergames.viber.RichKeyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@RestController
@SpringBootApplication

public class PuzzleViberGamesApplication implements ApplicationListener<ApplicationReadyEvent> {

    private ViberBot bot;
    private ViberSignatureValidator signatureValidator;
    private Storage storage;
    private UserService userService;

    @Autowired
    TagGame tagGame;

    @Autowired
    private GameStates gameStates;

    @Autowired
    GameService gameService;

    @Autowired
    GameStatesService gameStatesService;

    @Autowired
    public void setBot(ViberBot bot) {
        this.bot = bot;
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public PuzzleViberGamesApplication() {

    }

    @Autowired
    public void setSignatureValidator(ViberSignatureValidator signatureValidator) {
        this.signatureValidator = signatureValidator;
    }

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Value("${application.spring-viber-bot.webhook-url}")
    private String webhookUrl;

    private Game game(Class<? extends Game> gameClass){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(gameClass);
        return context.getBean(gameClass);
    }

    ConcurrentMap<String, Map<String, List<String>>> stateGame = new ConcurrentHashMap<>();


    public static void main(String[] args) {
        SpringApplication.run(PuzzleViberGamesApplication.class, args);
    }

    @Override
    public void onApplicationEvent(@Nonnull ApplicationReadyEvent appReadyEvent) {

        try {
            bot.setWebhook(webhookUrl).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        bot.onConversationStarted(
                e -> {
                    String viberId = e.getUser().getId();
                    String name = e.getUser().getName();
                    String avatar = e.getUser().getAvatar();
                    userService.saveUser(name, viberId, avatar);
                    Map<String, Object> trackingDataDelegate = new HashMap<>();
                    trackingDataDelegate.put("tracking_data", "game-menu");
                    TrackingData trackingData = new TrackingData(trackingDataDelegate);
                    Keyboard startButton = new Keyboards().startGameMemuButton();
                    MessageKeyboard startKeyboard = startButton.getMessageKeyboard();
                    return Futures.immediateFuture(
                            Optional.of(new KeyboardMessage(startKeyboard, trackingData, 8)));
                }
                );


        bot.onMessageReceived(
                (event, message, response) -> {
                    String viberId = event.getSender().getId();
                    String text = message.getMapRepresentation().get("text").toString();
//                    Game game = storage.getStorage().get(viberId);
//                    Game game = stateGame.get(viberId).get("tagGame");

                    System.out.println("Game: " + game);
                    if (text.equals("tictactoe-game")){
                        TicTacToe ticTacToeGame = (TicTacToe) game;
                        ticTacToeGame.clearArray();

                    }
                    if (text.startsWith("empty_cell")){
                        TicTacToe ticTacToeGame  = (TicTacToe) game;
                        try {
                            ticTacToeGame.ticTacToeMessageReceiver(message, response);
                        }catch (NullPointerException npe){
//                            startMenu(response);
//                            Optional<TicTacToe> newTicTacToe= Optional.of(
//                                    gameStatesService.findFirstByOwner(viberId).get().getTicTacToe());

//                            newTicTacToe.get().setArray()array.get();
//                            newTicTacToe.get().ticTacToeMessageReceiver(message, response);

                        }

                    } if (text.startsWith("tag_")){


                        try {
//                            System.out.println("tagGame.getArray() = " + tagGame.getArray());
//                            System.out.println("tagGame.array = " + tagGame.array);
//                            gameStates.setOwner(viberId);
//                            gameStates.setTagGame(tagGame);
//                            gameService.save(tagGame);
//                            gameStatesService.saveStateGame(gameStates);
                            stateGame.put(viberId, tagGame.getArray());
                            System.out.println("stateTagGame.get(viberId) = " + stateGame.get(viberId));
                            tagGame.tagGameMessageReceiver(message, response);
                        } catch (Exception exception){
//                            Optional<GameStates> gameStatesByOwner = gameStatesService.findGameStatesByOwner(viberId);
//
//                            if (gameStatesByOwner.isPresent()){
//                                System.out.println("gameStatesByOwner.get().getOwner() = " + gameStatesByOwner.get().getOwner());
//                                System.out.println("gameStatesByOwner.get().getTagGame() = " + gameStatesByOwner.get().getTagGame());
//
//                                List<String> array = gameStatesByOwner.get().getTagGame().getArray();
//                                System.out.println("array = " + array);
//                                tagGame.setArray(array);
                                  startMenu(response);
//                                tagGame.tagGameMessageReceiver(message, response);
//                            } else startMenu(response);
                        }

                    }
                });

        bot.onSubscribe(
                (incomingSubscribedEvent, response) -> {
                    response.send("Вы подписаны!");
                    startMenu(response);
                }
        );

        bot.onTextMessage("game-menu", (event, message, response) -> startMenu(response));

        bot.onTextMessage("tictactoe-game", (event, message, response) -> {
            String viberId = event.getSender().getId();
            TicTacToe ticTacToe = (TicTacToe) game(TicTacToe.class);
            ticTacToe.startTicTacToeKeyboard(response);
        });

        bot.onTextMessage("tag-game", (event, message, response) -> {
            String viberId = event.getSender().getId();
//            gameStates.setOwner(viberId);//
//            gameStatesService.saveStateGame(gameStates);
//            gameService.save(tagGame);
            Map<String, List<String>> mapGame = new HashMap<>();
            mapGame.put("tagGame", new ArrayList<>());
            stateGame.put(viberId, mapGame);
            tagGame.startTagGame(response);
        });

        bot.onTextMessage("rich", (event, message, response) -> {
            RichKeyboard menuRich = new Keyboards().calendar();
            RichMediaObject menuKeyboard = menuRich.getMessageKeyboard();
            response.send(new RichMediaMessage(menuKeyboard, "Text", 2));
        });
    }


    private void startMenu(Response response) {
        Map<String, Object> trackingDataDelegate = new HashMap<>();
        trackingDataDelegate.put("tracking_data", "game-menu");
        TrackingData trackingData = new TrackingData(trackingDataDelegate);
        Keyboard menu = new Keyboards().menu();
        MessageKeyboard menuKeyboard = menu.getMessageKeyboard();
        response.send(new KeyboardMessage(menuKeyboard, trackingData, 8));
    }


    @PostMapping(value = "/", produces = "application/json")
    public String incoming(@RequestBody String json,
                           @RequestHeader("X-Viber-Content-Signature") String serverSideSignature)
            throws ExecutionException, InterruptedException, IOException {
        Preconditions.checkState(signatureValidator.isSignatureValid(serverSideSignature, json), "invalid signature");
        @Nullable InputStream response = bot.incoming(Request.fromJsonString(json)).get();
        return response != null ? CharStreams.toString(new InputStreamReader(response, Charsets.UTF_16)) : null;
    }


    @Configuration
    @ComponentScan("ru.gamechatbot.puzzlevibergames")
    public class BotConfiguration {

        @Value("${application.spring-viber-bot.auth-token}")
        private String authToken;

        @Value("${application.spring-viber-bot.name}")
        private String name;

        @Value("${application.spring-viber-bot.avatar:@null}")
        private String avatar;

        @Bean
        ViberBot viberBot() {
            return new ViberBot(new BotProfile(name, avatar), authToken);
        }

        @Bean
        ViberSignatureValidator signatureValidator() {
            return new ViberSignatureValidator(authToken);
        }
    }
}
