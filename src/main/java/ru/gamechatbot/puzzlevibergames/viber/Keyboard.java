package ru.gamechatbot.puzzlevibergames.viber;
import com.viber.bot.message.MessageKeyboard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Keyboard extends ArrayList<Button> {

    static class KeyboardBuilder extends ArrayList<Button>{

        public KeyboardBuilder addButton(Button button){
            this.add(button);
            return this;
        }

        public Keyboard build(){
            Keyboard keyboard = new Keyboard();
            keyboard.addAll(this);
            return keyboard;
        }
    }

    public MessageKeyboard getMessageKeyboard(){
        Map<String, Object> keyboardDelegate = new HashMap<>();
        keyboardDelegate.put("Type", "keyboard");
        keyboardDelegate.put("InputFieldState", "hidden");
        keyboardDelegate.put("Buttons", this);
        return new MessageKeyboard(keyboardDelegate);
    }
}
