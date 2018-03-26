package com.example.android.quizapp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class QuestionManagerTest {
    static private QuestionManager questionManager = new QuestionManager();

    @Test
    public void getTextQuestion() throws Exception {
        String question = "Question1";
        String answer = "Answer1".toLowerCase();

        AbstractQuestion questionObject = questionManager.createTextQuestion(question, answer);
        assertEquals(question, questionObject.getQuestion());
        TextEntryQuestion textEntryQuestion = (TextEntryQuestion) questionObject;
        assertEquals(answer, textEntryQuestion.getAnswer());
    }

    @Test
    public void getSingleChoiceQuestion() throws Exception {
        String question = "Question2";
        String answer = "Answer1";
        String[] choices = {"Answer2", "Answer3", "Answer4"};

        AbstractQuestion questionObject = questionManager.createSingleChoiceQuestion(question, answer, choices);
        assertEquals(question, questionObject.getQuestion());
        SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) questionObject;
        ArrayList<Choice> choiceArrayList = singleChoiceQuestion.getChoices();
        for (Choice choice : choiceArrayList) {
            if (choice.getChoiceText().equals(answer)) {
                assertTrue(choice.isCorrect());
            } else if (Arrays.asList(choices).contains(choice.getChoiceText())) {
                assertFalse(choice.isCorrect());
            } else {
                fail("Unexpected choice");
            }
        }
        assertEquals(1 + choices.length, choiceArrayList.size());
    }

    private void testMultipleChoice(String question, String[] answers, String choices[]) {
        AbstractQuestion questionObject = questionManager.createMultipleChoiceQuestion(question, answers, choices);
        assertEquals(question, questionObject.getQuestion());
        MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) questionObject;
        ArrayList<Choice> choiceArrayList = multipleChoiceQuestion.getChoices();
        for (Choice choice : choiceArrayList) {
            if (Arrays.asList(answers).contains(choice.getChoiceText())) {
                assertTrue(choice.isCorrect());
            } else if (Arrays.asList(choices).contains(choice.getChoiceText())) {
                assertFalse(choice.isCorrect());
            } else {
                fail("Unexpected choice");
            }
        }
        assertEquals(answers.length + choices.length, choiceArrayList.size());
    }

    @Test
    public void getMultipleChoiceQuestion() throws Exception {
        testMultipleChoice("Question1", new String[]{"One Answer"}, new String[]{"Choice 2", "Choice 3", "Choice 4"});
        testMultipleChoice("Question2", new String[]{}, new String[]{"No correct answer", "No correct answer 2", "No correct answer 3"});
        testMultipleChoice("Question3", new String[]{"All correct answer1", "All correct answer2"}, new String[]{});
    }

    @Test
    public void getQuestionList() throws Exception {
        questionManager = new QuestionManager();

        questionManager.createTextQuestion("Q1", "A1");
        questionManager.createSingleChoiceQuestion("Q2", "A1", new String[]{"A2"});
        questionManager.createMultipleChoiceQuestion("Q3", new String[]{"A1"}, new String[]{"A2"});

        ArrayList<AbstractQuestion> questionList = questionManager.getQuestionList();
        int counter = 0;
        for (AbstractQuestion question : questionList) {
            counter++;
            assertEquals(question.getQuestion(), "Q" + counter);
        }
    }

}