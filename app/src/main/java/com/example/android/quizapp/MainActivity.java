package com.example.android.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        try {
            questionManager.parseQuestionFromRawTxtFile(getApplicationContext());
        } catch (Exception e) {
            Log.e(e.toString(), e.getMessage());
        }

        questionsLayout = new QuestionsLayout(this, questionListLinearLayout, questionManager);
        questionsLayout.createIntroTextLayout("Welcome in quiz about World, Cities and Culture");
        questionsLayout.createAllQuestionsLayout();
    }

    public void clickGradingButton(View view) {
        int total = 0;
        int correct = 0;
        for (AbstractQuestion question : questionManager.getQuestionList()) {
            total++;
            question.updateAnswer();
            question.setProperColorAfterAnswer();
            if (question.isCorrect()) {
                correct++;
            }
        }
        Toast toast = Toast.makeText(getApplicationContext(),
                "You have result" + correct + "/" + total, Toast.LENGTH_SHORT);
        toast.show();
    }

}
