package ru.gamechatbot.puzzlevibergames.games;

import com.viber.bot.Response;
import com.viber.bot.message.KeyboardMessage;
import com.viber.bot.message.Message;
import com.viber.bot.message.MessageKeyboard;
import com.viber.bot.message.TrackingData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.gamechatbot.puzzlevibergames.viber.Keyboard;
import ru.gamechatbot.puzzlevibergames.viber.Keyboards;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.*;



//@Data
@Component
@Scope("prototype")
@Embeddable
public class TicTacToe implements Game, Serializable {
//public class TicTacToe extends Game implements Serializable {

    public TicTacToe() {
    }


    @ElementCollection
    private List<String> array = new ArrayList<>(9);
    @Transient
    private final List<String> emptyArray = Arrays.asList("","","","","","","","","");

    @ElementCollection
    private final List<String> defaultBgColors =
            Arrays.asList("#F0FFFF","#F0FFFF","#F0FFFF","#F0FFFF","#F0FFFF","#F0FFFF","#F0FFFF","#F0FFFF", "#F0FFFF");
    @Transient
    private int[] winIndexes = new int[4];
    public void startTicTacToeKeyboard(Response response) {
        Map<String, Object> trackingDataDelegate = new HashMap<>();
        trackingDataDelegate.put("tracking_data", "tictactoe");
        TrackingData trackingData = new TrackingData(trackingDataDelegate);
        Keyboards keyboards = new Keyboards();
        Keyboard ticTacToe = keyboards.ticTacToeKeyboard(emptyArray, defaultBgColors);
        MessageKeyboard ticTacToeKeyboard = ticTacToe.getMessageKeyboard();
        KeyboardMessage keyboardMessage = new KeyboardMessage(ticTacToeKeyboard, trackingData, 8);
        response.send(keyboardMessage);
    }
    @Transient
    public void ticTacToeMessageReceiver(Message message, Response response) {
        String coordinate = "";
        Map<String, Object> trackingDataDelegate = new HashMap<>();
        trackingDataDelegate.put("tracking_data", "tictactoe");
        TrackingData trackingData = new TrackingData(trackingDataDelegate);
        Keyboard ticTacToe;
        MessageKeyboard ticTacToeKeyboard;

        if(message.getMapRepresentation().get("text").equals("tictactoe-game")){
            array = Arrays.asList("","","","","","","","","");
            ticTacToe = new Keyboards().ticTacToeKeyboard(emptyArray, defaultBgColors);
            ticTacToeKeyboard = ticTacToe.getMessageKeyboard();
            response.send(new KeyboardMessage(ticTacToeKeyboard, trackingData, 8));
        }
        if (message.getMapRepresentation().get("text").toString().contains("empty_cell")) {
            for (Map.Entry<String, Object> entrySet : message.getMapRepresentation().entrySet()) {
                String key = entrySet.getKey();
                if(key.equals("text")){
                    Object value = entrySet.getValue();
                    coordinate = (String) value;
                    coordinate = coordinate.substring(11);

                    ticTacToe = new Keyboards().ticTacToeKeyboard(setSign(coordinate, response), defaultBgColors);
                    ticTacToeKeyboard = ticTacToe.getMessageKeyboard();
                    response.send(new KeyboardMessage(ticTacToeKeyboard, trackingData, 8));
                }
            }
        }
    }
    @Transient
    private List<String> setSign(String coordinate, Response response) {
        List<String> emptyArray = Arrays.asList("","","","","","","","","");
        if(array.isEmpty()) {
            array = emptyArray;
        }
        switch (coordinate){
            case "0": {
                array.set(0, "X");
                computerStep(response);
            }
            break;
            case "1": {
                array.set(1, "X");
                computerStep(response);
            }
            break;
            case "2": {
                array.set(2, "X");
                computerStep(response);
            }
            break;
            case "3": {
                array.set(3, "X");
                computerStep(response);
            }
            break;
            case "4": {
                array.set(4, "X");
                computerStep(response);
            }
            break;
            case "5": {
                array.set(5, "X");
                computerStep(response);
            }
            break;
            case "6": {
                array.set(6, "X");
                computerStep(response);
            }
            break;
            case "7": {
                array.set(7, "X");
                computerStep(response);
            }
            break;
            case "8": {
                array.set(8, "X");
                computerStep(response);
            }
            break;
        }
        return array;
    }
    @Transient
    private boolean logicGame(String s) {

        if (array.get(0).equals(s) && array.get(1).equals(s) && array.get(2).equals(s)){
            saveWinIndexes(0, 1, 2);
            return true;
        }
        if (array.get(3).equals(s) && array.get(4).equals(s) && array.get(5).equals(s)){
            saveWinIndexes(3, 4, 5);
            return true;
        }
        if (array.get(6).equals(s) && array.get(7).equals(s) && array.get(8).equals(s)){
            saveWinIndexes( 6, 7, 8);
            return true;
        }

        if (array.get(0).equals(s) && array.get(3).equals(s) && array.get(6).equals(s)){
            saveWinIndexes(0, 3, 6);
            return true;
        }
        if (array.get(1).equals(s) && array.get(4).equals(s) && array.get(7).equals(s)){
            saveWinIndexes(1, 4, 7);
            return true;
        }
        if (array.get(2).equals(s) && array.get(5).equals(s) && array.get(8).equals(s)){
            saveWinIndexes(2, 5, 8);
            return true;
        }

        if (array.get(0).equals(s) && array.get(4).equals(s) && array.get(8).equals(s)){
            saveWinIndexes( 0, 4, 8);
            return true;
        }
        if (array.get(2).equals(s) && array.get(4).equals(s) && array.get(6).equals(s)){
            saveWinIndexes(2, 4, 6);
            return true;
        }
        return false;
    }
    @Transient
    private boolean isWinner(){
        return logicGame("X");
    }
    @Transient
    private boolean isLoser(){
        return logicGame("0");
    }
    @Transient
    private boolean isDraw(){
        return (isFullTable() && !isWinner() && !isLoser());
    }
    @Transient
    private boolean isFullTable(){
        boolean isFull = false;
        int notEmptyCellCounter = 0;
        for (String s : array) {
            if (s.equals("0") || s.equals("X")) {
                notEmptyCellCounter++;
            }
        }
        if(notEmptyCellCounter == 9){
            isFull = true;
        }
        return isFull;
    }
    @Transient
    private void computerStep(Response response) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            if (!array.get(i).equals("0") && !array.get(i).equals("X")){
                indexes.add(i);
            }
        }
        if(indexes.size() > 0){
            Random random = new Random();
            int index = random.nextInt(indexes.size());
            array.set(indexes.get(index), "0");
        }
        outcomeGame(response);
    }
    @Transient
    private void outcomeGame(Response response) {

        if (isWinner()){
            response.send("Поздравляю, Ты выиграл!");
            finalSnapShotGame(response);
            array = Arrays.asList("","","","","","","","","");
            return;
        }

        if (isLoser()){
            response.send("Ты проиграл!");
            finalSnapShotGame(response);
            array = Arrays.asList("","","","","","","","","");
            return;
        }

        if (isDraw()){
            response.send("Это ничья!");
            array = Arrays.asList("","","","","","","","","");
        }

    }
    @Transient
    private void finalSnapShotGame(Response response) {
        String winColor = "#FF1493";
        List<String> colorsForWinIndexes = new ArrayList<>(defaultBgColors);
        for (int i = 0; i < colorsForWinIndexes.size(); i++) {
            if (winIndexes[0] == i || winIndexes[1] == i || winIndexes[2] == i){
                colorsForWinIndexes.set(i, winColor);
            }
        }
        Map<String, Object> trackingDataDelegate = new HashMap<>();
        trackingDataDelegate.put("tracking_data", "tictactoe");
        TrackingData trackingData = new TrackingData(trackingDataDelegate);
        Keyboard ticTacToe = new Keyboards().ticTacToeKeyboard(array, colorsForWinIndexes);
        MessageKeyboard ticTacToeKeyboard = ticTacToe.getMessageKeyboard();
        response.send(new KeyboardMessage(ticTacToeKeyboard, trackingData, 8));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Transient
    private void saveWinIndexes(int i, int i2, int i3) {
        winIndexes[0] = i;
        winIndexes[1] = i2;
        winIndexes[2] = i3;
    }
    @Transient

    public void clearArray(){
        array = Arrays.asList("","","","","","","","","");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicTacToe ticTacToe = (TicTacToe) o;
        return Objects.equals(array, ticTacToe.array) && Objects.equals(emptyArray, ticTacToe.emptyArray) && Objects.equals(defaultBgColors, ticTacToe.defaultBgColors) && Arrays.equals(winIndexes, ticTacToe.winIndexes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(array, emptyArray, defaultBgColors);
        result = 31 * result + Arrays.hashCode(winIndexes);
        return result;
    }

    public List<String> getArray() {
        return array;
    }

    public void setArray(List<String> array) {
        this.array = array;
    }

    public List<String> getEmptyArray() {
        return emptyArray;
    }

    public List<String> getDefaultBgColors() {
        return defaultBgColors;
    }

    public int[] getWinIndexes() {
        return winIndexes;
    }

    public void setWinIndexes(int[] winIndexes) {
        this.winIndexes = winIndexes;
    }
}
