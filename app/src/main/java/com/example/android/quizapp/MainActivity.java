package com.example.android.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout questionListView = findViewById(R.id.questionsListView);
        questionListView.removeAllViewsInLayout();

        TextView introText = new TextView(this, null, R.attr.introStyleRef);
        questionListView.addView(introText);

        LinearLayout textAnswerQuestion = new LinearLayout(this, null, R.attr.questionAndAnswerLayoutRef);
        TextView questionText = new TextView(this, null, R.attr.questionStyleRef);
        questionText.setText("Question 1");
        EditText textAnswer = new EditText(this, null, R.attr.textAnswerStyleRef);

        textAnswerQuestion.addView(questionText);
        textAnswerQuestion.addView(textAnswer);

        questionListView.addView(textAnswerQuestion);
    }

//    TextView createIntroText(String text) {
//
//    }
}
