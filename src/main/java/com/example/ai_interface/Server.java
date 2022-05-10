package com.example.ai_interface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Server {

    private static DataBase dataBase;
    private static User user;
    private static String decision;
    public Server(DataBase dataBase, User user) {
        Server.dataBase = dataBase;
        Server.user = user;
    }
    public static void setDataBase(DataBase dataBase){
        Server.dataBase = dataBase;
    }
    public static String checkingLanguageDataBase() {
        ArrayList<String> words = user.getInitialWord();
        ArrayList<Language> lang = dataBase.getLagnuages();

        for(int i = 0; i < words.size(); i++){
            for(int j = 0; j < words.get(i).length(); j++) {
                char tmp_letter = words.get(i).charAt(j);

                for(int k = 0; k <  lang.size(); k++){
                    Language current = lang.get(k);

                    for(int t = 0; t < current.getLetters().size(); t++){
                        char bd_letter = lang.get(k).getLetters().get(t).charAt(0);
                        if(Character.toLowerCase(tmp_letter) == bd_letter) {
                            decision = current.getLanguage();
                            if(Objects.equals(decision, "Русский")) {
                                cyrillicBasedSearch();
                            }
                            if(Objects.equals(decision, "Английский")){
                                latinBasedSearch();
                            }
                            return decision;
                        }

                    }
                }
            }
        }
        return "Не определен";
    }
    static String latinBasedSearch() {
        decision = "Английский";
        ArrayList<String> ClientWords = user.getInitialWord();
        setDataBase(new DataBase("C:\\Users\\User\\IdeaProjects\\Ai_interface\\src\\main\\java\\com\\example\\ai_interface\\dataBases\\LatinLettersBase.txt"));
        ArrayList<Language> lang = dataBase.getLagnuages();

            for (int i = 0; i < ClientWords.size(); i++) {
                for (int k = 0; k < lang.size(); k++) {
                    String tmp_word = ClientWords.get(i);
                    Language current = lang.get(k);
                    for (int t = 0; t < current.getWords().size(); t++) {
                        String tmp_bd_word = current.getWords().get(t);
                        if (Objects.equals(tmp_word.toLowerCase(), tmp_bd_word.toLowerCase())) {
                            decision = current.getLanguage();
                            return decision;
                        }
                    }
                }
            }

            for (int i = 0; i < ClientWords.size(); i++) {
                for (int j = 0; j < ClientWords.get(i).length(); j++) {
                    char tmp_letter = ClientWords.get(i).charAt(j);

                    for (int k = 0; k < lang.size(); k++) {
                        Language current = lang.get(k);

                        for (int t = 0; t < current.getLetters().size(); t++) {
                            char bd_letter = lang.get(k).getLetters().get(t).charAt(0);
                            if (Character.toLowerCase(tmp_letter) == bd_letter) {
                                decision = current.getLanguage();
                                return decision;
                            }
                        }
                    }
                }
            }
        return decision;
    }
    public static DataBase getDataBase(){
        return dataBase;
    }
    public static User getUser(){
        return user;
    }
    public static String cyrillicBasedSearch(){
        ArrayList<String> ClientWords = user.getInitialWord();
        setDataBase(new DataBase("C:\\Users\\User\\IdeaProjects\\Ai_interface\\src\\main\\java\\com\\example\\ai_interface\\dataBases\\CyrillicLettersBase.txt"));
        ArrayList<Language> lang = dataBase.getLagnuages();

            for (int i = 0; i < ClientWords.size(); i++) {
                for (int k = 0; k < lang.size(); k++) {
                    String tmp_word = ClientWords.get(i);
                    Language current = lang.get(k);
                    for (int t = 0; t < current.getWords().size(); t++) {
                        String tmp_bd_word = current.getWords().get(t);
                        if (Objects.equals(tmp_word.toLowerCase(), tmp_bd_word.toLowerCase())) {
                            decision = current.getLanguage();
                        }
                    }
                }
            }

            for (int i = 0; i < ClientWords.size(); i++) {
                for (int j = 0; j < ClientWords.get(i).length(); j++) {
                    char tmp_letter = ClientWords.get(i).charAt(j);

                    for (int k = 0; k < lang.size(); k++) {
                        Language current = lang.get(k);

                        for (int t = 0; t < current.getLetters().size(); t++) {
                            char bd_letter = lang.get(k).getLetters().get(t).charAt(0);
                            if (Character.toLowerCase(tmp_letter) == bd_letter) {
                                decision = current.getLanguage();
                                return decision;
                            }
                        }
                    }
                }
            }
        return decision;
    }
}
