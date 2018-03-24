package com.example.android.quizapp;

import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

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

}

class SingleChoiceQuestion extends ChoiceQuestion {
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
}


class TextEntryQuestion extends AbstractQuestion {
    private String answer;
    private String respond;

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

    @Override
    boolean isCorrect() {
        return this.answer.equals(this.respond);
    }

    @Override
    boolean isAnswered() {
        return !this.respond.equals("");
    }
}

