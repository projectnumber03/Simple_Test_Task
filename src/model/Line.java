package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Pattern;

public class Line {
    private int numberLength;
    private int dateLength;
    private int nameLength;
    private ArrayList<String> number;
    private ArrayList<String> date;
    private ArrayList<String> name;
    private int height;
    private int pageWidth;

    public Line(int numberLength, int dateLength, int nameLength, String number, String date, String name) {
        ArrayList<Integer> maxLength = new ArrayList<>();
        maxLength.add(number.length());
        maxLength.add(date.length());
        maxLength.add(name.length());
        this.numberLength = numberLength;
        this.dateLength = dateLength;
        this.nameLength = nameLength;
        this.number = splitString(number, numberLength);
        this.date = splitString(date, dateLength);
        this.name = splitString(name, nameLength);
        this.height = (int)Math.ceil((double)Collections.max(maxLength) / nameLength);
    }

    public Line(int pageWidth) {
        this.pageWidth = pageWidth;
        this.height = 1;
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<String> splitString(String str, int length){
        ArrayList<String> splitFinal = new ArrayList<>();
        //Разбивка строки по словам
        Pattern pattern = Pattern.compile("((?<=\\W)|(?=\\W))", Pattern.UNICODE_CHARACTER_CLASS);
        String[] splitWords = pattern.split(str);
        //Разбивка строки по длине
        for (String splitWord : splitWords){
            if(splitWord.length() > length){
                String[] splitLengths = splitWord.split("(?<=\\G.{" + length + "})");
                for (String splitLength : splitLengths){
                    splitFinal.add(splitLength);
                }
            }else splitFinal.add(splitWord);
        }
        //Присоединение символьных разделителей
        for (int i = 1; i < splitFinal.size() - 1; i++){
            if(splitFinal.get(i).matches("\\W")){
                if(splitFinal.get(i - 1).trim().length() < length){
                    splitFinal.set(i - 1, splitFinal.get(i - 1) + splitFinal.get(i));
                }else {
                    splitFinal.set(i + 1, splitFinal.get(i) + splitFinal.get(i + 1));
                }
                splitFinal.remove(i);
            }
        }
        return splitFinal;
    }

    public void print(){
        Collections.reverse(date);
        Collections.reverse(name);
        Stack<String> dateStack = new Stack<>();
        Stack<String> nameStack = new Stack<>();
        dateStack.addAll(date);
        nameStack.addAll(name);
        for (int i = 0; i < height; i++){
            System.out.print("| ");
            try{
                while (number.get(i).length() < numberLength){
                    number.set(i, number.get(i) + " ");
                }
                System.out.print(number.get(i));
            }catch (IndexOutOfBoundsException iobe){
                for (int j = 0; j < numberLength; j++){
                    System.out.print(" ");
                }
            }
            System.out.print(" | ");
            String dateLine = "";
            try{
                while ((dateLine + dateStack.peek()).length() < dateLength){
                    dateLine += dateStack.pop();
                }
                while (dateLine.length() < dateLength){
                    dateLine += " ";
                }
                System.out.print(dateLine);
            }catch (EmptyStackException ese){
                System.out.print(dateLine);
                for (int j = dateLine.length(); j < dateLength; j++){
                    System.out.print(" ");
                }
            }
            System.out.print(" | ");
            String nameLine = "";
            try{
                while ((nameLine + nameStack.peek()).trim().length() <= nameLength){
                    nameLine += nameStack.pop();
                }
                System.out.print(nameLine.trim());
                for (int j = nameLine.trim().length(); j < nameLength; j++){
                    System.out.print(" ");
                }
            }catch (EmptyStackException ese){
                System.out.print(nameLine);
                for (int j = nameLine.trim().length(); j < nameLength; j++){
                    System.out.print(" ");
                }
            }
            System.out.print(" |\n");
        }
    }
}
