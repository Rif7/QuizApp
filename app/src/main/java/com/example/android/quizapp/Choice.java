package com.example.android.quizapp;

import android.widget.CompoundButton;

public class Choice {
    private CompoundButton compoundButtonRef;
    private int resId;
    private String choiceText;
    private boolean isChosen;
    private boolean isCorrect;

    Choice(String choiceText, boolean isCorrect) {
        this.choiceText = choiceText;
        this.isCorrect = isCorrect;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resID) {
        this.resId = resID;
    }

    void setCompoundButtonRef(CompoundButton compoundButton) {
        this.compoundButtonRef = compoundButton;
    }

    void updateAnswer() {
        isChosen = compoundButtonRef.isChecked();
    }

    void setProperColorAfterAnswer() {
        int colorId = QuestionsLayout.getAnswerColorAsInt(isCorrect, isChosen);
        compoundButtonRef.setBackgroundColor(colorId);
    }

    String getChoiceText() {
        return choiceText;
    }

    boolean isCorrect() {
        return isCorrect;
    }

    boolean isChosen() {
        return isChosen;
    }

    void setChosen(boolean chosen) {
        isChosen = chosen;
    }
}
