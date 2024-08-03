package ru.gamechatbot.puzzlevibergames.scheduler;

import com.google.common.util.concurrent.Futures;
import com.viber.bot.message.KeyboardMessage;
import com.viber.bot.message.MessageKeyboard;
import com.viber.bot.message.TrackingData;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.gamechatbot.puzzlevibergames.viber.Keyboard;
import ru.gamechatbot.puzzlevibergames.viber.Keyboards;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class Scheduler{
    final String cron_str =  "*/10 * * * * *";
    @Scheduled(cron = cron_str) // Формат:  секунда, минута, час, день, месяц, день недели
    public void reportCurrentData() {
        Map<String, Object> trackingDataDelegate = new HashMap<>();
        trackingDataDelegate.put("tracking_data", "game-menu");
        TrackingData trackingData = new TrackingData(trackingDataDelegate);
        Keyboard startButton = new Keyboards().startGameMemuButton();
        MessageKeyboard startKeyboard = startButton.getMessageKeyboard();
        Futures.immediateFuture(
                            Optional.of(new KeyboardMessage(startKeyboard, trackingData, 8)));
    }
//        System.out.println("Scheduler working: " + new Date());
}