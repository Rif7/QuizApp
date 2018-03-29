package com.example.android.quizapp;

import android.widget.CompoundButton;

class Choice {
    private CompoundButton compoundButtonRef;
    private final String choiceText;
    private boolean isChosen;
    private final boolean isCorrect;

    Choice(String choiceText, boolean isCorrect) {
        this.choiceText = choiceText;
        this.isCorrect = isCorrect;
    }

    void setCompoundButtonRef(CompoundButton compoundButton) {
        this.compoundButtonRef = compoundButton;
    }

    void updateAnswer() {
        isChosen = compoundButtonRef.isChecked();
    }

    void changeQuestionLayoutAfterAnswer() {
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
