package com.example.android.quizapp;

import android.widget.EditText;

import java.util.ArrayList;

abstract class AbstractQuestion {
    private final String question;

    AbstractQuestion(String question) {
        this.question = question;
    }

    String getQuestion() {
        return question;
    }

    abstract boolean isCorrect();

    abstract boolean isAnswered();

    abstract void updateAnswer();

    abstract void changeQuestionLayoutAfterAnswer();
}

abstract class ChoiceQuestion extends AbstractQuestion {
    final ArrayList<Choice> choices;

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
    void changeQuestionLayoutAfterAnswer() {
        for (Choice choice : this.choices) {
            choice.changeQuestionLayoutAfterAnswer();
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
        }
    }

}

class TextEntryQuestion extends AbstractQuestion {
    private final String correctAnswer;
    private String respond;
    private EditText editTextRef;

    TextEntryQuestion(String question, String answer) {
        super(question);
        this.correctAnswer = answer.toLowerCase();
        this.respond = "";
    }

    String getCorrectAnswer() {
        return correctAnswer;
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
    void changeQuestionLayoutAfterAnswer() {
        int colorId = QuestionsLayout.getAnswerColorAsInt(isCorrect(), true);
        editTextRef.setTextColor(colorId);
        String msg = correctAnswer;
        if (!isCorrect()) {
            msg = "correct: " + correctAnswer;
        }
        editTextRef.setText(msg);
    }

    @Override
    boolean isCorrect() {
        return this.correctAnswer.equals(this.respond);
    }

    @Override
    boolean isAnswered() {
        return this.respond.length() > 0;
    }
}


