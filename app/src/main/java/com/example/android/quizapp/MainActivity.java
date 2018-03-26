package com.example.android.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    QuestionManager questionManager;
    QuestionsLayout questionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout questionListLinearLayout = findViewById(R.id.questionsListView);
        questionManager = new QuestionManager();
        questionsLayout = new QuestionsLayout(this, questionListLinearLayout, questionManager);

        questionManager.createTextQuestion("Question1", "answer1");
        questionManager.createSingleChoiceQuestion("Question2", "Answer1", new String[]{"Answer2", "Answer3", "Answer4"});
        questionManager.createMultipleChoiceQuestion("Question3", new String[]{"One Answer"}, new String[]{"Choice 2", "Choice 3", "Choice 4"});
        questionManager.createMultipleChoiceQuestion("Question4", new String[]{"One Answer"}, new String[]{"Choice 2", "Choice 3", "Choice 4"});

        questionsLayout.createIntroTextLayout("AAAA\nBBBB\nCCCC");
        questionsLayout.createAllQuestionsLayout();
    }

    public void clickGradingButton(View view) {
        int total = 0;
        int correct = 0;
        for (AbstractQuestion question : questionManager.getQuestionList()) {
            total++;
            question.updateAnswer();
            if (question.isCorrect()) {
                correct++;
            }
        }
        Toast toast = Toast.makeText(getApplicationContext(),
                "You have result" + correct + "/" + total, Toast.LENGTH_SHORT);
        toast.show();
    }

}
