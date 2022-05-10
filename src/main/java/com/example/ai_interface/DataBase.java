package com.example.ai_interface;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;

public class DataBase {
    private String filepath;
    private ArrayList<Language> lagnuages = new ArrayList<>();
    public DataBase(String filepath) {
        this.filepath = filepath;
        createBaseLetters();
    }
    public void createBaseLetters() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            String buffer;
            int counter = 1;
            while ((buffer = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(buffer, " ");
                switch(counter) {
                    case 1:{
                        String token = tokenizer.nextToken();
                        lagnuages.add(new Language(token));
                        break;
                    }
                    case 2: {
                        while (tokenizer.hasMoreTokens())
                        {
                            String token = tokenizer.nextToken();
                            lagnuages.get(lagnuages.size() - 1).putLetters(token);
                        }
                        break;
                    }
                    case 3: {
                        while (tokenizer.hasMoreTokens())
                        {
                            String tokenWord = tokenizer.nextToken();
                            lagnuages.get(lagnuages.size() - 1).putWords(tokenWord);
                        }
                        counter = 0;
                        break;
                    }
                }
                counter++;

            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public void update(User user, String decision) throws IOException {
        for (int i = 0; i < lagnuages.size(); ++i) {
            Language currentLanguage = lagnuages.get(i);
            if(Objects.equals(currentLanguage.getLanguage().toLowerCase(), decision.toLowerCase())) {
                for(int j = 0; j < user.getInitialWord().size(); j++) {
                    String tmp_user = user.getInitialWord().get(j);
                    boolean flag = false;
                    for(int k = 0; k < currentLanguage.getWords().size(); k++){
                        String tmp_base = currentLanguage.getWords().get(k);
                        if(Objects.equals(tmp_user.toLowerCase(), tmp_base.toLowerCase())) {
                            flag = true;
                        }
                    }
                    if(!flag)
                        lagnuages.get(i).putWords(tmp_user);
                }
            }
        }
        updateLanguageDataBase();
    }
    public void updateLanguageDataBase() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath));

        for (int i = 0; i < lagnuages.size(); ++i) {
            Language currentLanguage = lagnuages.get(i);
            bufferedWriter.write(currentLanguage.getLanguage() + "\n");
            bufferedWriter.flush();
            for(int j = 0; j < currentLanguage.getLetters().size(); j++) {
                bufferedWriter.write(currentLanguage.getLetters().get(j) + " ");
                bufferedWriter.flush();
            }
            bufferedWriter.write("\n");
            for(int k = 0; k < currentLanguage.getWords().size(); k++) {
                bufferedWriter.write(currentLanguage.getWords().get(k) + " ");
                bufferedWriter.flush();
            }
            bufferedWriter.write("\n");
        }
    }
    public ArrayList<Language> getLagnuages() {
        return lagnuages;
    }

}