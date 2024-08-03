package ru.gamechatbot.puzzlevibergames.viber;
import java.util.HashMap;

public class Button extends HashMap<String, Object>{

    //    private List<Map<String, Object>> buttons = new ArrayList<>();
    private final String actionBody;
    private final String actionType;
    private final String text;
    private final int textSize;
    private final String textColor;
    private final String bgColor;
    private final String silent;
    private final int columns;
    private final int rows;

    public Button(ButtonBuilder buttonBuilder) {
        super();
        this.text = buttonBuilder.text;
        this.textSize = buttonBuilder.textSize;
        this.textColor = buttonBuilder.textColor;
        this.actionBody = buttonBuilder.actionBody;
        this.actionType = buttonBuilder.actionType;
        this.bgColor = buttonBuilder.bgColor;
        this.silent = buttonBuilder.silent;
        this.columns = buttonBuilder.columns;
        this.rows = buttonBuilder.rows;
    }

    public static class ButtonBuilder {
        private final String actionBody;
        private final String actionType;
        public String textColor = "#FFFFFF";
        private String text;
        private int textSize = 20;
        private String bgColor = "#000000";
        private String silent = "False";
        private int columns = 6;
        private int rows = 1;
        public ButtonBuilder(String actionBody, String actionType) {
            this.actionBody = actionBody;
            this.actionType = actionType;
        }

        public ButtonBuilder text(String text){
            this.text = text;
            return this;
        }

        public ButtonBuilder textSize(int textSize){
            this.textSize = textSize;
            return this;
        }

        public ButtonBuilder textColor(String textColor){
            this.textColor = textColor;
            return this;
        }

        public ButtonBuilder bgColor(String bgColor){
            this.bgColor = bgColor;
            return this;
        }

        public ButtonBuilder silent(String silent){
            this.silent = silent;
            return this;
        }

        public ButtonBuilder columns(int columns){
            this.columns = columns;
            return this;
        }

        public ButtonBuilder rows(int rows){
            this.rows = rows;
            return this;
        }

        public Button build(){
            String kbText = "<font color='" + textColor + "' size='" + textSize + "'>" + text + "</font>";
            Button button = new Button(this);
            button.put("Columns", columns);
            button.put("Rows", rows);
            button.put("Text", kbText);
            button.put("ActionBody", actionBody);
            button.put("ActionType", actionType);
            button.put("BgColor", bgColor);
            button.put("Silent", silent);
            return button;
        }
    }

    @Override
    public String toString() {
        return "Button{" +
                "actionBody='" + actionBody + '\'' +
                ", text='" + text + '\'' +
                ", textSize=" + textSize +
                ", textColor='" + textColor + '\'' +
                ", bgColor='" + bgColor + '\'' +
                ", silent='" + silent + '\'' +
                ", columns=" + columns +
                ", rows=" + rows +
                '}';
    }

}
