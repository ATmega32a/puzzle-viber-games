package ru.gamechatbot.puzzlevibergames.viber;

import java.util.*;
import java.util.stream.IntStream;

public class ViberCalendar {

    private final Calendar calendar = new GregorianCalendar(2021, Calendar.JANUARY, 1);
    //    private final Calendar calendar = new GregorianCalendar();
    final List<String> weekDays = Arrays.asList("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс");

    int first_day_month;
    int last_day_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    int day = 1;
    int index = 0;
    int index2;

    {
//        try {
//            calendar.setTime(
//                    new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
//                            .parse(String.valueOf(calendar.getTime())));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        DayOfWeek dayOfWeek = LocalDate.parse(String.valueOf(calendar.getTime()), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")).getDayOfWeek();
        first_day_month = calendar.get(Calendar.DAY_OF_WEEK) - 2;
//        first_day_month = dayOfWeek.getValue();
        System.out.println(first_day_month);
    }


    public List<String> seq_num_month(int m, int y){
        List<String> num_days = new ArrayList<>();
        IntStream.range(0, 43).forEach(i -> {
            num_days.add("s" + i);
        });
        if (first_day_month == 6 || first_day_month == 5  &&  last_day_month == 31){
            IntStream.range(1, first_day_month).forEach(i -> {
                num_days.remove(num_days.get(index));
                num_days.add(index, "");
                index += 6;
            });
            IntStream.range(0, 7 - first_day_month).forEach(i -> {
                num_days.remove(num_days.get(index));
                num_days.add(index, String.valueOf(day));
                day += 1;
                index += 6;
            });
            IntStream.range(0, 6).forEach(ind -> {
                IntStream.range(0, 7).forEach(i -> {
                    num_days.remove(num_days.get(ind));
                    num_days.add(ind, String.valueOf(day));
                    day += 1;
                    if (day > last_day_month + 1) {
                        num_days.remove(num_days.get(i));
                        num_days.add(i, "");
                        index += 6;
                    }
                    index += 1;
                });
            });
        }else {
            weekDays.forEach(w_day -> {
                num_days.add(index, w_day);
                index += 6;
            });
            index2 = 1;
            IntStream.range(0, first_day_month).forEach(i -> {
                num_days.remove(num_days.get(index2));
                num_days.add(index2, "");
                index2 += 6;
            });
            IntStream.range(0, 7 - first_day_month).forEach(i ->{
                num_days.remove(num_days.get(index2));
                num_days.add(index2, String.valueOf(day));
                day += 1;
                index2 += 6;
            });
            IntStream.range(2, 6).forEach(i -> {
                index2 = i;
                IntStream.range(0, 7).forEach(j -> {
                    num_days.remove(num_days.get(index2));  //    какая-то проблема тут!
                    num_days.add(index2, String.valueOf(day));  //  какая-то проблема тут!
                    day += 1;
                    if (day > last_day_month + 1){
                        num_days.remove(num_days.get(index2));
                        num_days.add(index2, "");
                    }
                    index2 += 6;
                });
                index2 += 1;
            });
        }
        return num_days;
    }
}
