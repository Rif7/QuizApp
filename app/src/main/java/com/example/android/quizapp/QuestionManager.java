package com.example.android.quizapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

class QuestionManager {
    private static QuestionManager instance;
    private ArrayList<AbstractQuestion> questionList;

    protected QuestionManager() {
        questionList = new ArrayList<>();
    }

    public static QuestionManager getInstance() {
        if (instance == null) {
            instance = new QuestionManager();
        }
        return instance;
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

    AbstractQuestion addProperQuestion(String question, ArrayList<String> answers, ArrayList<String> choices) {
        Log.i("addProperQuestion", question);
        if (answers.size() == 1) {
            if (choices.size() == 0) {
                return createTextQuestion(question, answers.get(0));
            } else {
                return createSingleChoiceQuestion(question, answers.get(0), choices.toArray(new String[0]));
            }
        } else {
            return createMultipleChoiceQuestion(question, answers.toArray(new String[0]), choices.toArray(new String[0]));
        }
    }

    void parseQuestionFromRawTxtFile(Context context) throws IOException {
        char CORRECT_ANS_MARK = '+';

        Resources resources = context.getResources();
        InputStream questionFile = resources.openRawResource(R.raw.questions);
        BufferedReader reader = new BufferedReader(new InputStreamReader(questionFile));
        String question = reader.readLine();
        while (true) {
            Log.i("addAnswer", question);
            ArrayList<String> answers = new ArrayList<>();
            ArrayList<String> choices = new ArrayList<>();
            String choice = reader.readLine();
            while (choice.length() != 0) {
                Log.i("two", choice);
                if (choice.charAt(0) == CORRECT_ANS_MARK) {
                    answers.add(choice.substring(1, choice.length()));
                } else {
                    choices.add(choice);
                }
                choice = reader.readLine();
                if (choice == null) {
                    return;
                }
            }
            addProperQuestion(question, answers, choices);
            question = reader.readLine();
            if (question == null) {
                return;
            }
        }
    }


}
