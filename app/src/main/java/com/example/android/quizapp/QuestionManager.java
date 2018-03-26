package com.example.android.quizapp;

import java.util.ArrayList;
import java.util.Collections;

class QuestionManager {
    ArrayList<AbstractQuestion> questionList;

    QuestionManager() {
        questionList = new ArrayList<>();
    }

    ArrayList<AbstractQuestion> getQuestionList() {
        return questionList;
    }

    TextEntryQuestion createTextQuestion(String question, String answer) {
        TextEntryQuestion questionObject = new TextEntryQuestion(question, answer);
        questionList.add(questionObject);
        return questionObject;
    }

    SingleChoiceQuestion createSingleChoiceQuestion(String question, String answer, String[] choices) {
        ArrayList<Choice> choicesList = new ArrayList<>();
        choicesList.add(new Choice(answer, true));
        for (String choice : choices) {
            choicesList.add(new Choice(choice, false));
        }
        Collections.shuffle(choicesList);
        SingleChoiceQuestion questionObject = new SingleChoiceQuestion(question, choicesList);
        questionList.add(questionObject);
        return questionObject;
    }

    MultipleChoiceQuestion createMultipleChoiceQuestion(String question, String[] answers, String[] choices) {
        ArrayList<Choice> choicesList = new ArrayList<>();
        for (String choice : answers) {
            choicesList.add(new Choice(choice, true));
        }
        for (String choice : choices) {
            choicesList.add(new Choice(choice, false));
        }
        Collections.shuffle(choicesList);
        MultipleChoiceQuestion questionObject = new MultipleChoiceQuestion(question, choicesList);
        questionList.add(questionObject);
        return questionObject;
    }

//    int getNumberOfAllQuestions() {
//        return questionList.size();
//    }
//
//    void getNumberOfCorrectAnswers() {
//         int counterCorrectAnswers = 0;
//         for
//    }

}
