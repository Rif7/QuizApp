package com.example.android.quizapp;


import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionsLayout {
    Context mainActivityContext;
    LinearLayout questionsListView;
    QuestionManager questionManager;


    QuestionsLayout(Context mainActivityContext, LinearLayout questionsListView, QuestionManager questionManager) {
        this.mainActivityContext = mainActivityContext;
        this.questionsListView = questionsListView;
        this.questionManager = questionManager;
        this.questionsListView.removeAllViewsInLayout();
    }

    void createIntroTextLayout(String text) {
        TextView introText = new TextView(mainActivityContext, null, R.attr.introStyleRef);
        introText.setText(text);
        questionsListView.addView(introText);
    }

    private LinearLayout createQuestionBaseLayout(String question) {
        LinearLayout textAnswerQuestion = new LinearLayout(mainActivityContext, null, R.attr.questionAndAnswerLayoutRef);
        TextView questionText = new TextView(mainActivityContext, null, R.attr.questionStyleRef);
        questionText.setText(question);
        textAnswerQuestion.addView(questionText);
        return textAnswerQuestion;
    }

    private LinearLayout createTextAnswerQuestionLayout(TextEntryQuestion textEntryQuestion) {
        LinearLayout questionBaseLayout = createQuestionBaseLayout(textEntryQuestion.getQuestion());
        EditText textAnswer = new EditText(mainActivityContext, null, R.attr.textAnswerStyleRef);
        textEntryQuestion.setEditTextRef(textAnswer);
        questionBaseLayout.addView(textAnswer);
        return questionBaseLayout;
    }

    private LinearLayout createSingleChoiceQuestionLayout(SingleChoiceQuestion singleChoiceQuestion) {
        LinearLayout questionBaseLayout = createQuestionBaseLayout(singleChoiceQuestion.getQuestion());
        ArrayList<Choice> choiceArrayList = singleChoiceQuestion.getChoices();
        RadioGroup radioGroup = new RadioGroup(mainActivityContext);
        for (Choice choice : choiceArrayList) {
            RadioButton radioButton = new RadioButton(mainActivityContext, null, R.attr.radioButtonStyle);
            radioButton.setText(choice.getChoiceText());
            choice.setCompoundButtonRef(radioButton);
            radioGroup.addView(radioButton);
        }
        questionBaseLayout.addView(radioGroup);
        return questionBaseLayout;
    }

    private LinearLayout createMultipleChoiceQuestionLayout(MultipleChoiceQuestion multipleChoiceQuestion) {
        LinearLayout questionBaseLayout = createQuestionBaseLayout(multipleChoiceQuestion.getQuestion());
        ArrayList<Choice> choiceArrayList = multipleChoiceQuestion.getChoices();
        for (Choice choice : choiceArrayList) {
            CheckBox checkBox = new CheckBox(mainActivityContext, null, R.attr.checkboxStyle);
            checkBox.setText(choice.getChoiceText());
            choice.setCompoundButtonRef(checkBox);
            questionBaseLayout.addView(checkBox);
        }
        return questionBaseLayout;
    }

    void createAllQuestionsLayout() {
        for (AbstractQuestion question : questionManager.getQuestionList()) {
            LinearLayout questionView = null;
            if (question instanceof TextEntryQuestion) {
                questionView = createTextAnswerQuestionLayout((TextEntryQuestion) question);
            } else if (question instanceof SingleChoiceQuestion) {
                questionView = createSingleChoiceQuestionLayout((SingleChoiceQuestion) question);
            } else if (question instanceof MultipleChoiceQuestion) {
                questionView = createMultipleChoiceQuestionLayout((MultipleChoiceQuestion) question);
            }
            questionsListView.addView(questionView);
        }
    }

}
