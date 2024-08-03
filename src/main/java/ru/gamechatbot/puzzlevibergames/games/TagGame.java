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

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

//@Data

@Component
@Scope("prototype")

//@Embeddable
@Entity
public class TagGame implements  Game, Serializable {

    @ElementCollection
    public List<String> array;
    @Transient
    private final List<String> sortedArray = Arrays.asList("1","2","3","4","5","6","7","8",
            "9","10","11","12","13","14","15","");
    @Transient
    private List<String>  colors = Arrays.asList("#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191",
            "#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191");
    @Id
    @GeneratedValue
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Transient
    public void startTagGame(Response response) {
        Map<String, Object> trackingDataDelegate = new HashMap<>();
        trackingDataDelegate.put("tracking_data", "tag_game");
        TrackingData trackingData = new TrackingData(trackingDataDelegate);
        Keyboards keyboards = new Keyboards();
        clearArray();
//        shuffleArray(array);
        shuffleArray();
        colors = Arrays.asList("#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191",
                "#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191", "#F59191");
        colors = setColorForEmptyCell(array);
        Keyboard tagGame = keyboards.tagGameKeyboard(array, colors);
        MessageKeyboard tagGameKeyboard = tagGame.getMessageKeyboard();
        KeyboardMessage keyboardMessage = new KeyboardMessage(tagGameKeyboard, trackingData, 8);
        response.send(keyboardMessage);
    }

    @Transient
    public void tagGameMessageReceiver(Message message, Response response) {
        Map<String, Object> trackingDataDelegate = new HashMap<>();
        trackingDataDelegate.put("tracking_data", "taggame");
        TrackingData trackingData = new TrackingData(trackingDataDelegate);
        Keyboard tagGame;
        MessageKeyboard tagGameKeyboard;

        if(message.getMapRepresentation().get("text").equals("tag-game")){
            tagGame = new Keyboards().tagGameKeyboard(array, colors);
            tagGameKeyboard = tagGame.getMessageKeyboard();
            response.send(new KeyboardMessage(tagGameKeyboard, trackingData, 8));
        }
        if (message.getMapRepresentation().get("text").toString().contains("tag_")) {
            for (Map.Entry<String, Object> entrySet : message.getMapRepresentation().entrySet()) {
                String key = entrySet.getKey();
                if(key.equals("text")){
                    Object value = entrySet.getValue();
                    String indexStr = (String) value;
                    int cellIndex= Integer.parseInt(indexStr.substring(4));
                    gameProcess(response, cellIndex);
                    tagGame = new Keyboards().tagGameKeyboard(array,colors);
                    tagGameKeyboard = tagGame.getMessageKeyboard();
                    response.send(new KeyboardMessage(tagGameKeyboard, trackingData, 8));
                }
            }
        }
    }

    @Transient
    public void gameProcess(Response response, int index){
        int indexEmptyCell;
        if((indexEmptyCell = indexOfTheAdjacentCellIfItIsEmpty(index))!=16){
            swap(index, indexEmptyCell);
        }
        updateAndShowButtons(response);
        if(isSortedArray()){
            response.send("Это победа!");
        }
    }

//    @Transient
    public void swap(int indexPressedCell, int indexEmptyCell) {
        String value = array.get(indexPressedCell);
        array.set(indexEmptyCell, value);
        array.set(indexPressedCell, "");
        colors.set(indexEmptyCell, "#F59191");
        colors.set(indexPressedCell, "#f7f0ff");
    }

    @Transient
    public void clearArray() {
        array.clear();
    }

    @Transient
    public boolean isSortedArray() {
        for (int i = 0; i < 15; i++) {
            if (!array.get(i).equals(sortedArray.get(i))){
                return false;
            }
        }
        return true;
    }

    // Попробовать этот вариант
//    public List<String> shuffleArray(List<String> arr) {
//        Collections.shuffle(arr);
//        return arr;
//    }



    @Transient
    public List<String> shuffleArray() {
        Random random = new Random();
        int number;
        for (int i = 0; i < 16; i++) {
            number = random.nextInt(16);
            if (!array.contains(String.valueOf(number))){
                array.add(String.valueOf(number));
            }else{
                i--;
            }
        }
        array.set(array.indexOf("0"), "");
        return array;
    }

    @Transient
    public int indexOfTheAdjacentCellIfItIsEmpty(int inboundIndex) {

        if(inboundIndex == 0){
            if(array.get(1).equals("")) return 1;
            if (array.get(4).equals("")) return 4;
        }
        if(inboundIndex == 3){
            if(array.get(2).equals("")) return 2;
            if (array.get(7).equals("")) return 7;
        }
        if(inboundIndex == 12){
            if(array.get(8).equals("")) return 8;
            if (array.get(13).equals("")) return 13;
        }
        if(inboundIndex == 15){
            if(array.get(11).equals("")) return 11;
            if (array.get(14).equals("")) return 14;
        }
        if(inboundIndex == 1 || inboundIndex == 2){
            if(array.get(inboundIndex + 1).equals("")) return inboundIndex + 1;
            if(array.get(inboundIndex - 1).equals("")) return inboundIndex - 1;
            if(array.get(inboundIndex + 4).equals("")) return inboundIndex + 4;
        }
        if(inboundIndex == 7 || inboundIndex == 11){
            if(array.get(inboundIndex - 1).equals("")) return inboundIndex - 1;
            if(array.get(inboundIndex - 4).equals("")) return inboundIndex - 4;
            if(array.get(inboundIndex + 4).equals("")) return inboundIndex + 4;
        }
        if(inboundIndex == 13 || inboundIndex == 14){
            if(array.get(inboundIndex - 1).equals("")) return inboundIndex - 1;
            if(array.get(inboundIndex + 1).equals("")) return inboundIndex + 1;
            if(array.get(inboundIndex - 4).equals("")) return inboundIndex - 4;
        }
        if(inboundIndex == 4 || inboundIndex == 8){
            if(array.get(inboundIndex + 1).equals("")) return inboundIndex + 1;
            if(array.get(inboundIndex + 4).equals("")) return inboundIndex + 4;
            if(array.get(inboundIndex - 4).equals("")) return inboundIndex - 4;
        }
        if (inboundIndex == 5 || inboundIndex == 6 || inboundIndex == 9 || inboundIndex == 10){
            if(array.get(inboundIndex - 1).equals("")) return inboundIndex - 1;
            if(array.get(inboundIndex + 1).equals("")) return inboundIndex + 1;
            if(array.get(inboundIndex - 4).equals("")) return inboundIndex - 4;
            if(array.get(inboundIndex + 4).equals("")) return inboundIndex + 4;
        }
        return 16;
    }

    @Transient
    public void runNewGame(Response response) {
        clearArray();
        startTagGame(response);
    }

//    @Transient
    public void updateAndShowButtons(Response response){
        setValueButtons(array, colors, response);
    }

    @Transient
    public void setValueButtons(List<String> array, List<String> colors, Response response) {
        Map<String, Object> trackingDataDelegate = new HashMap<>();
        trackingDataDelegate.put("tracking_data", "taggame");
        TrackingData trackingData = new TrackingData(trackingDataDelegate);
        Keyboard tagGame;
        MessageKeyboard tagGameKeyboard;

        tagGame = new Keyboards().tagGameKeyboard(array,colors);
        tagGameKeyboard = tagGame.getMessageKeyboard();

        response.send(new KeyboardMessage(tagGameKeyboard, trackingData, 8));

    }
    @Transient
//    @ElementCollection
    private List<String> setColorForEmptyCell(List<String> array){
        int indexColorEmptyCell = 0;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).equals("")){
                indexColorEmptyCell = i;
                break;
            }
        }
        colors.set(indexColorEmptyCell, "#f7f0ff");
        return colors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagGame tagGame = (TagGame) o;
        return Objects.equals(array, tagGame.array) && Objects.equals(sortedArray, tagGame.sortedArray) && Objects.equals(colors, tagGame.colors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(array, sortedArray, colors);
    }

//    @ElementCollection
//    @Transient

    public List<String> getArray() {
        return array;
    }

    public void setArray(List<String> array) {
        this.array = array;
    }
@Transient
    public List<String> getSortedArray() {
        return sortedArray;
    }

    @Transient
    public List<String> getColors() {
        return colors;
    }
    @Transient
    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public TagGame(List<String> array, List<String> colors) {

        this.array = array;
        this.colors = colors;
    }

    public TagGame(List<String> array) {
        this.array = array;
    }

    public TagGame() {
            this.array = new ArrayList<>(16);
    }


}
