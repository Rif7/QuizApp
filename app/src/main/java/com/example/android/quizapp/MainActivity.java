package com.example.android.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    QuestionsLayout questionsLayout;
    static Boolean gradingButtonWasClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (QuestionManager.getInstance().getQuestionList().size() == 0) {
            try {
                QuestionManager.getInstance().parseQuestionFromRawTxtFile(getApplicationContext());
            } catch (Exception e) {
                Log.e(e.toString(), e.getMessage());
            }
        }
        LinearLayout questionListLinearLayout = findViewById(R.id.questionsListView);
        questionsLayout = new QuestionsLayout(this, questionListLinearLayout);
        questionsLayout.createIntroTextLayout("Welcome in quiz about World, Cities and Culture." +
                " There is " + QuestionManager.getInstance().getQuestionList().size() + " questions." +
                " Good Luck!");
        questionsLayout.createAllQuestionsLayout();

        // to avoid automatic displaying keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // to restore background colors after rotate
        if (gradingButtonWasClicked) {
            for (AbstractQuestion question : QuestionManager.getInstance().getQuestionList()) {
                question.setProperColorAfterAnswer();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Save all question states
        for (AbstractQuestion question : QuestionManager.getInstance().getQuestionList()) {
            question.updateAnswer();
        }

    }

    public void clickGradingButton(View view) {
        int total = 0;
        int correct = 0;
        for (AbstractQuestion question : QuestionManager.getInstance().getQuestionList()) {
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
        gradingButtonWasClicked = true;
    }

}
