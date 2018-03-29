package com.example.android.quizapp;

import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;

abstract class AbstractQuestion {
    private String question;

    AbstractQuestion(String question) {
        this.question = question;
    }
    String getQuestion() {
        return question;
    }

    abstract boolean isCorrect();
    abstract boolean isAnswered();

    abstract void updateAnswer();

    abstract void setProperColorAfterAnswer();
}

abstract class ChoiceQuestion extends AbstractQuestion {
    ArrayList<Choice> choices;

    ChoiceQuestion(String question, ArrayList<Choice> choices) {
        super(question);
        this.choices = choices;
    }

    ArrayList<Choice> getChoices() {
        return choices;
    }

    boolean isAnswered() {
        for (Choice choice : this.choices) {
            if (choice.isChosen()) {
                return true;
            }
        }
        return false;
    }

    @Override
    void setProperColorAfterAnswer() {
        for (Choice choice : this.choices) {
            choice.setProperColorAfterAnswer();
        }
    }
}


class MultipleChoiceQuestion extends ChoiceQuestion {
    MultipleChoiceQuestion(String question, ArrayList<Choice> choices) {
        super(question, choices);
    }

    @Override
    boolean isCorrect() {
        for (Choice choice : this.choices) {
            if (choice.isChosen() != choice.isCorrect()) {
                return false;
            }
        }
        return true;
    }

    @Override
    void updateAnswer() {
        for (Choice choice : this.choices) {
            choice.updateAnswer();
        }
    }
}

class SingleChoiceQuestion extends ChoiceQuestion {
    private RadioGroup radioGroupRef;
    private int answeredChoiceID;
    SingleChoiceQuestion(String question, ArrayList<Choice> choices) {
        super(question, choices);
    }

    @Override
    boolean isCorrect() {
        for (Choice choice : this.choices) {
            if (choice.isChosen() && choice.isCorrect()) {
                return true;
            }
        }
        return false;
    }

    @Override
    void updateAnswer() {
        for (Choice choice : this.choices) {
            choice.updateAnswer();
            if (choice.isChosen()) {
                answeredChoiceID = choice.getResId();
            }
        }
    }

    void setRadioGroupRef(RadioGroup radioGroupRef) {
        this.radioGroupRef = radioGroupRef;
        this.radioGroupRef.check(answeredChoiceID);
    }
}

class TextEntryQuestion extends AbstractQuestion {
    private String answer;
    private String respond;
    private EditText editTextRef;

    TextEntryQuestion(String question, String answer) {
        super(question);
        this.answer = answer.toLowerCase();
        this.respond = "";
    }

    String getAnswer() {
        return answer;
    }

    void setRespond(String respond) {
        this.respond = respond.toLowerCase();
    }

    void setEditTextRef(EditText editText) {
        editTextRef = editText;
        editText.setText(respond);
    }

    @Override
    void updateAnswer() {
        respond = editTextRef.getText().toString().trim();
    }

    @Override
    void setProperColorAfterAnswer() {
        int colorId = QuestionsLayout.getAnswerColorAsInt(isCorrect(), isAnswered());
        editTextRef.setBackgroundColor(colorId);
    }

    @Override
    boolean isCorrect() {
        return this.answer.equals(this.respond);
    }

    @Override
    boolean isAnswered() {
        return this.respond.length() > 0;
    }
}


