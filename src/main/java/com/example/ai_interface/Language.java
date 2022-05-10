package com.example.ai_interface;

import java.util.ArrayList;

public class Language {
    private String language;
    private ArrayList<String> letters = new ArrayList<>();
    private ArrayList<String> words = new ArrayList<>();
    public Language(String name){
        language = name;
    }
    public void putLetters(String letter){
        letters.add(letter);
    }
    public void putWords(String word) {
        words.add(word);
    }
    public String getLanguage() {
        return language;
    }
    public ArrayList<String> getLetters() {
        return letters;
    }
    public ArrayList<String> getWords(){
        return words;
    }
}
