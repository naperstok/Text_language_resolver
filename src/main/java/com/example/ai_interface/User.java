package com.example.ai_interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class User {
    private ArrayList<String> initialText = new ArrayList<String>();
    private ArrayList<String> words = new ArrayList<>();

    public void setInitialText(String initialText) {
        this.initialText.add(initialText);
    }
    public void setInitialWords() {
        for (int i = 0; i < initialText.size(); i++) {
            StringTokenizer tokenizer = new StringTokenizer(initialText.get(i), " ");
            while (tokenizer.hasMoreTokens())
            {
                String token = tokenizer.nextToken();
                if(!isDigit(token)) {
                    token = clearWord(token);
                    words.add(token);
                }
            }
        }
    }
    public String clearWord(String word){
        List<String> delimiters = Arrays.asList(",", ".", "?", ":", "!");
        for (String delimiter : delimiters) {
            word = word.replace(delimiter, " ");
        }
        String[] output = word.split(" ");
        return output[0];
    }
    public ArrayList<String> getInitialWord() {
        return words;
    }
    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
