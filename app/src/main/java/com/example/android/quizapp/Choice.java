package com.example.android.quizapp;

import java.util.HashMap;

public class Choice {
    private static HashMap<String, Choice> resIdMap;

    private String resId;
    private String choiceText;
    private boolean isChosen;
    private boolean isCorrect;

    Choice(String choiceText, boolean isCorrect) {
        this.choiceText = choiceText;
        this.isCorrect = isCorrect;
    }

    String getChoiceText() {
        return choiceText;
    }

    boolean isCorrect() {
        return isCorrect;
    }

    void setId(String resId) {
        this.resId = resId;
        resIdMap.put(this.resId, this);
    }

    public static HashMap<String, Choice> getResIdMap() {
        return resIdMap;
    }

    boolean isChosen() {
        return isChosen;
    }

    void setChosen(boolean chosen) {
        isChosen = chosen;
    }
}
