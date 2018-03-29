package com.example.android.quizapp;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionsLayout implements Serializable {
    private static int correctColorInt;
    private static int wrongColorInt;
    private static int notAnsweredColorInt;
    private static int oddQuestionColor;
    private static int evenQuestionColor;
    private Context mainActivityContext;
    LinearLayout questionsListView;

    QuestionsLayout(Context mainActivityContext, LinearLayout questionsListView) {
        this.mainActivityContext = mainActivityContext;
        this.questionsListView = questionsListView;
        this.questionsListView.removeAllViewsInLayout();
        correctColorInt = ContextCompat.getColor(this.mainActivityContext, R.color.correct);
        wrongColorInt = ContextCompat.getColor(this.mainActivityContext, R.color.wrong);
        notAnsweredColorInt = ContextCompat.getColor(this.mainActivityContext, R.color.notAnswered);
        oddQuestionColor = ContextCompat.getColor(this.mainActivityContext, R.color.questionAnswer1);
        evenQuestionColor = ContextCompat.getColor(this.mainActivityContext, R.color.questionAnswer2);

    }

    public static int getAnswerColorAsInt(Boolean isCorrect, Boolean isChosen) {
        if (isCorrect) {
            if (isChosen) {
                return correctColorInt;
            } else {
                return notAnsweredColorInt;
            }
        } else {
            if (isChosen) {
                return wrongColorInt;
            } else {
                return Color.TRANSPARENT; // to indicate no color change
            }
        }
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
        if (this.questionsListView.getChildCount() % 2 == 0) {
            textAnswerQuestion.setBackgroundColor(oddQuestionColor);
        } else {
            textAnswerQuestion.setBackgroundColor(evenQuestionColor);
        }
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
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        radioGroup.setLayoutParams(layoutParams);
        singleChoiceQuestion.setRadioGroupRef(radioGroup);
        int checkedChoiceIResId = 0;
        for (Choice choice : choiceArrayList) {
            RadioButton radioButton = new RadioButton(mainActivityContext, null, R.attr.radioButtonStyleRef);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            radioButton.setText(choice.getChoiceText());
            choice.setCompoundButtonRef(radioButton);
            if (choice.isChosen()) {
                checkedChoiceIResId = View.generateViewId();
                radioButton.setId(checkedChoiceIResId);
                radioGroup.check(choice.getResId());
            }
            radioGroup.addView(radioButton);
        }
        if (checkedChoiceIResId != 0) {
            radioGroup.check(checkedChoiceIResId);
        }
        questionBaseLayout.addView(radioGroup);
        return questionBaseLayout;
    }

    private LinearLayout createMultipleChoiceQuestionLayout(MultipleChoiceQuestion multipleChoiceQuestion) {
        LinearLayout questionBaseLayout = createQuestionBaseLayout(multipleChoiceQuestion.getQuestion());
        ArrayList<Choice> choiceArrayList = multipleChoiceQuestion.getChoices();
        for (Choice choice : choiceArrayList) {
            CheckBox checkBox = new CheckBox(mainActivityContext, null, R.attr.checkBoxStyleRef);
            checkBox.setText(choice.getChoiceText());
            choice.setCompoundButtonRef(checkBox);
            checkBox.setChecked(choice.isChosen());
            questionBaseLayout.addView(checkBox);
        }
        return questionBaseLayout;
    }

    void createAllQuestionsLayout() {
        for (AbstractQuestion question : QuestionManager.getInstance().getQuestionList()) {
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
