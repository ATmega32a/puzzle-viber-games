package ru.gamechatbot.puzzlevibergames;

import java.util.*;
/**
 *
 *
 !!!---Игра "Пятнашки"---!!!

 В_начале_рандомно_номера_в_кнопки_записать();
 Цикл (){
 Если (отсортировался_массив){
 Вывести("Это победа!")
 Запустить_новую_игру()
 Выйти_из_цикла;
 } Иначе {
 Если (нажата_кнопка){
 Если (нажата_та_кнопка_у_которой_есть_пустая_соседняя_на_прямой_линии){ # не по диагонали!
 Поменять_местами_нажатую_кнопку_с_пустой_кнопкой();
 Обновить_массив(); # записать в массив новые значения();
 }
 }
 }
 }


 Индексы массива
 0  1  2  3
 4  5  6  7
 8  9  10 11
 12 13 14 15
 *
 *
 **/

public class TestPSVMTagGame {
    private List<String> array = new ArrayList<>(16);
    private final List<String> sortedArray = Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","0");

    private void gameProcess(){
        clearArray();
        shuffleArray();
        System.out.println();
        System.out.println();
        printArray();
        System.out.println();
        int index;
        Scanner scanner = new Scanner(System.in);
        while (true){
            if(isSortedArray()){
                print("Это победа!");
                runNewGame();
                break;
            }else {
                System.out.println();
                System.out.print("Делай ход: ");
                String value = scanner.next();
                if (value.equals("0")) continue;
                System.out.println();
                index = array.indexOf(value);
                int indexEmptyCell;
                if((indexEmptyCell = indexOfTheAdjacentCellIfItIsEmpty(index))!=16){
                    swap(index, indexEmptyCell);
                    updateArray();
                }
            }
            printArray();
        }
    }

    private void updateArray() {
    }

    private void swap(int indexPressedCell, int indexEmptyCell) {
        String value = array.get(indexPressedCell);
        array.set(indexEmptyCell, value);
        array.set(indexPressedCell, "0");
    }

    private void clearArray() {
        array.clear();
    }

    private boolean isSortedArray() {
        for (int i = 0; i < 15; i++) {
            if (!array.get(i).equals(sortedArray.get(i))){
                return false;
            }
        }
        return true;
    }

    private void shuffleArray() {
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

    }

    private int indexOfTheAdjacentCellIfItIsEmpty(int inboundIndex) {

        if(inboundIndex == 0){
            if(array.get(1).equals("0")) return 1;
            if (array.get(4).equals("0")) return 4;
        }
        if(inboundIndex == 3){
            if(array.get(2).equals("0")) return 2;
            if (array.get(7).equals("0")) return 7;
        }
        if(inboundIndex == 12){
            if(array.get(8).equals("0")) return 8;
            if (array.get(13).equals("0")) return 13;
        }
        if(inboundIndex == 15){
            if(array.get(11).equals("0")) return 11;
            if (array.get(14).equals("0")) return 14;
        }
        if(inboundIndex == 1 || inboundIndex == 2){
            if(array.get(inboundIndex + 1).equals("0")) return inboundIndex + 1;
            if(array.get(inboundIndex - 1).equals("0")) return inboundIndex - 1;
            if(array.get(inboundIndex + 4).equals("0")) return inboundIndex + 4;
        }
        if(inboundIndex == 7 || inboundIndex == 11){
            if(array.get(inboundIndex - 1).equals("0")) return inboundIndex - 1;
            if(array.get(inboundIndex - 4).equals("0")) return inboundIndex - 4;
            if(array.get(inboundIndex + 4).equals("0")) return inboundIndex + 4;
        }
        if(inboundIndex == 13 || inboundIndex == 14){
            if(array.get(inboundIndex - 1).equals("0")) return inboundIndex - 1;
            if(array.get(inboundIndex + 1).equals("0")) return inboundIndex + 1;
            if(array.get(inboundIndex - 4).equals("0")) return inboundIndex - 4;
        }
        if(inboundIndex == 4 || inboundIndex == 8){
            if(array.get(inboundIndex + 1).equals("0")) return inboundIndex + 1;
            if(array.get(inboundIndex + 4).equals("0")) return inboundIndex + 4;
            if(array.get(inboundIndex - 4).equals("0")) return inboundIndex - 4;
        }
        if (inboundIndex == 5 || inboundIndex == 6 || inboundIndex == 9 || inboundIndex == 10){
            if(array.get(inboundIndex - 1).equals("0")) return inboundIndex - 1;
            if(array.get(inboundIndex + 1).equals("0")) return inboundIndex + 1;
            if(array.get(inboundIndex - 4).equals("0")) return inboundIndex - 4;
            if(array.get(inboundIndex + 4).equals("0")) return inboundIndex + 4;
        }
        return 16;
    }

    private void runNewGame() {
        clearArray();
//        startTagGame(response);
    }

    private void print(String s) {
        System.out.println(s);
    }

    public void printArray(){
        for (int i = 0; i < array.size(); i++) {
            if (i == 3 || i == 7 || i == 11){
                System.out.println(array.get(i) + "   ");
            }else{
                System.out.print(array.get(i) + "   ");
            }
        }
    }

    public static void main(String[] args) {
        TestPSVMTagGame tagGame = new TestPSVMTagGame();
        tagGame.gameProcess();
    }
}

