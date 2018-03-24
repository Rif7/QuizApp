package com.example.android.quizapp;

import java.util.ArrayList;


class QuestionManager {
    TextEntryQuestion getTextQuestion(String question, String answer) {
        return new TextEntryQuestion(question, answer);
    }

    SingleChoiceQuestion getSingleChoiceQuestion(String question, String answer, String[] choices) {
        ArrayList<Choice> choicesList = new ArrayList<>();
        choicesList.add(new Choice(answer, true));
        for (String choice : choices) {
            choicesList.add(new Choice(choice, false));
        }
        return new SingleChoiceQuestion(question, choicesList);
    }

    MultipleChoiceQuestion getMultipleChoiceQuestion(String question, String[] answers, String[] choices) {
        ArrayList<Choice> choicesList = new ArrayList<>();
        for (String choice : answers) {
            choicesList.add(new Choice(choice, true));
        }
        for (String choice : choices) {
            choicesList.add(new Choice(choice, false));
        }
        return new MultipleChoiceQuestion(question, choicesList);
    }


}
