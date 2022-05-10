package com.example.ai_interface;

import java.io.IOException;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private Text answerArea;
    @FXML
    private AnchorPane answerField;
    @FXML
    private AnchorPane changingField;
    @FXML
    private Button defineButton;
    @FXML
    private Button noButton;
    @FXML
    private Button noUpdate;
    @FXML
    private Text question;
    @FXML
    private Text helpFieldHeader;
    @FXML
    private TextField helpFieldInputLang;
    @FXML
    private TextArea textField;
    @FXML
    private AnchorPane helpField;
    @FXML
    private Button yesButton;
    @FXML
    private Button yesUpdate;
    private Questions[] startQuestions = new Questions[] {
            new Questions("В тексте используются буквы кириллцы?", "Русский"),
            new Questions("В тексте используются буквы латиницы?", "Английский")
    };
    private Questions[] latinQuestions = new Questions[] {
            new Questions("В тексте много слов с заглавной буквы?", "Немецкий"),
            new Questions("В тексте много диактрики (букв с крыжечками) и апострофов?", "Французский"),
            new Questions("В тексте есть артикли la/le? Есть характерные слова dello/della?", "Итальянский"),
            new Questions("Встречаются ли в тексте перевернутые ¡восклицательные! и знаки ¿вопроса?", "Испанский")
    };
    private Questions[] cyrilliсQuestions = new Questions[] {
            new Questions("Это русский?", "Русский"),
            new Questions("В тексте есть как будто слепленные буквы (Љ или Њ)?", "Сербскохорватский"),
            new Questions("Выглядит как русский, но есть буква ў?", "Белорусский")
    };
    public String questionSearch(String text, Questions[] currentQuestions) {
        for(int i = 0 ; i < currentQuestions.length; i++){
            if(Objects.equals(currentQuestions[i].getQuestion(), text)) {
                return currentQuestions[i].getAnswer();
            }
        }
        return "Не определен";
    }
    private int nowQuestion = 0;
    private String currentAnswer;

    @FXML
    void initialize() {
        DataBase dataBase = new DataBase("C:\\Users\\User\\IdeaProjects\\Ai_interface\\src\\main\\java\\com\\example\\ai_interface\\dataBases\\GlobalLettersBase.txt");
        User first = new User();
        Server server = new Server(dataBase, first);
        changingField.setVisible(false);
        answerField.setVisible(false);
        helpField.setVisible(false);

        defineButton.setOnAction(e -> {
            String text = textField.getText().trim();
            if ( !text.isEmpty() ) {
                first.setInitialText(text);
                first.setInitialWords();
            }
            initialQuestions(server);
        });
    }
    private void initialQuestions(Server server) {
        changingField.setVisible(true);
        currentAnswer = startQuestions[nowQuestion].getAnswer();

        yesButton.setOnAction(e -> {
            String tmpAnswer = questionSearch(question.getText(), startQuestions);
            if(Objects.equals(tmpAnswer, "Русский")){
               cyrillicQuestionsSearch(server);
            }
            if(Objects.equals(tmpAnswer, "Английский")){
                latinQuestionsSearch(server);
            }
        });

        noButton.setOnAction(e -> {
            currentAnswer = startQuestions[nowQuestion].getAnswer();
            if(nowQuestion + 1 == startQuestions.length) {
                changingField.setVisible(false);
                answerField.setVisible(true);
                String tmpAnswer = Server.checkingLanguageDataBase();
                answerArea.setText(tmpAnswer);

                yesUpdate.setOnAction(t -> {
                    helpField.setVisible(true);
                    helpFieldInputLang.setVisible(false);
                    helpFieldHeader.setText("База данных пополнена!");
                    try {
                        Server.getDataBase().update(Server.getUser(), tmpAnswer);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                noUpdate.setOnAction(t -> {
                    helpField.setVisible(true);
                    helpFieldInputLang.setVisible(true);
                    helpFieldHeader.setText("Помогите! Какой это язык?");

                    helpFieldInputLang.setOnKeyPressed(event -> {
                        if (event.getCode().equals(KeyCode.ENTER)) {
                            String text = helpFieldInputLang.getText().trim();
                            if ( !text.isEmpty() ) {
                                try {
                                    Server.getDataBase().update(Server.getUser(), text);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                helpFieldInputLang.setVisible(false);
                                helpFieldHeader.setText("База данных пополнена!");
                                answerArea.setText(text);
                            }
                        }
                    });
                });
            }else {
                nowQuestion++;
                currentAnswer = startQuestions[nowQuestion].getAnswer();
                question.setText(startQuestions[nowQuestion].getQuestion());
            }
        });

    }
    private void latinQuestionsSearch(Server server) {
        nowQuestion = 0;
        currentAnswer = latinQuestions[nowQuestion].getAnswer();
        question.setText(latinQuestions[nowQuestion].getQuestion());

        yesButton.setOnAction(e -> {
            String tmpAnswer = questionSearch(question.getText(), latinQuestions);
            if(!Objects.equals(tmpAnswer, "Не определен")){
                changingField.setVisible(false);
                answerField.setVisible(true);
                answerArea.setText(tmpAnswer);
            }

        });

        noButton.setOnAction(e -> {
            currentAnswer = latinQuestions[nowQuestion].getAnswer();
            if(nowQuestion + 1 == latinQuestions.length) {
                String tmpAnswer = Server.latinBasedSearch();
                changingField.setVisible(false);
                answerField.setVisible(true);
                answerArea.setText(tmpAnswer);
                yesUpdate.setOnAction(t -> {
                    helpField.setVisible(true);
                    helpFieldInputLang.setVisible(false);
                    helpFieldHeader.setText("База данных пополнена!");
                    try {
                        Server.getDataBase().update(Server.getUser(), tmpAnswer);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                noUpdate.setOnAction(t -> {
                    helpField.setVisible(true);
                    helpFieldInputLang.setVisible(true);
                    helpFieldHeader.setText("Помогите! Какой это язык?");

                    helpFieldInputLang.setOnKeyPressed(event -> {
                        if (event.getCode().equals(KeyCode.ENTER)) {
                            String text = helpFieldInputLang.getText().trim();
                            if ( !text.isEmpty() ) {
                                try {
                                    Server.getDataBase().update(Server.getUser(), text);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                helpFieldInputLang.setVisible(false);
                                helpFieldHeader.setText("База данных пополнена!");
                                answerArea.setText(text);
                            }
                        }
                    });
                });
            }else {
                nowQuestion++;
                currentAnswer = latinQuestions[nowQuestion].getAnswer();
                question.setText(latinQuestions[nowQuestion].getQuestion());
            }
        });
    }
    private void cyrillicQuestionsSearch(Server server) {
        nowQuestion = 0;
        currentAnswer = cyrilliсQuestions[nowQuestion].getAnswer();
        question.setText(cyrilliсQuestions[nowQuestion].getQuestion());

        yesButton.setOnAction(e -> {
            String tmpAnswer = questionSearch(question.getText(), cyrilliсQuestions);
            if(!Objects.equals(tmpAnswer, "Не определен")){
                changingField.setVisible(false);
                answerField.setVisible(true);
                answerArea.setText(tmpAnswer);
            }

        });

        noButton.setOnAction(e -> {
            currentAnswer = cyrilliсQuestions[nowQuestion].getAnswer();
            if(nowQuestion + 1 == cyrilliсQuestions.length) {
                String tmpAnswer = Server.cyrillicBasedSearch();
                changingField.setVisible(false);
                answerField.setVisible(true);
                answerArea.setText(tmpAnswer);
                yesUpdate.setOnAction(t -> {
                    helpField.setVisible(true);
                    helpFieldInputLang.setVisible(false);
                    helpFieldHeader.setText("База данных пополнена!");
                    try {
                        Server.getDataBase().update(Server.getUser(), tmpAnswer);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                noUpdate.setOnAction(t -> {
                    helpField.setVisible(true);
                    helpFieldInputLang.setVisible(true);
                    helpFieldHeader.setText("Помогите! Какой это язык?");

                    helpFieldInputLang.setOnKeyPressed(event -> {
                        if (event.getCode().equals(KeyCode.ENTER)) {
                            String text = helpFieldInputLang.getText().trim();
                            if ( !text.isEmpty() ) {
                                try {
                                    Server.getDataBase().update(Server.getUser(), text);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                helpFieldInputLang.setVisible(false);
                                helpFieldHeader.setText("База данных пополнена!");
                                answerArea.setText(text);
                            }
                        }
                    });
                });
            }else {
                nowQuestion++;
                currentAnswer = cyrilliсQuestions[nowQuestion].getAnswer();
                question.setText(cyrilliсQuestions[nowQuestion].getQuestion());
            }
        });
    }

}
