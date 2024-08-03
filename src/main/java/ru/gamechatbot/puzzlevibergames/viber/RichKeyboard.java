package ru.gamechatbot.puzzlevibergames.viber;

import com.viber.bot.message.RichMediaObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RichKeyboard extends ArrayList<Button>{

    static class RichKeyboardBuilder extends ArrayList<Button>{

        public RichKeyboardBuilder addButton(Button button){
            this.add(button);
            return this;
        }

        public RichKeyboard build(){
            RichKeyboard keyboard = new RichKeyboard();
            keyboard.addAll(this);
            return keyboard;
        }
    }

    public RichMediaObject getMessageKeyboard(){
        Map<String, Object> richKeyboardDelegate = new HashMap<>();
        richKeyboardDelegate.put("Type", "keyboard"); //rich
        richKeyboardDelegate.put("Buttons", this);
        return new RichMediaObject(richKeyboardDelegate);
    }
}


