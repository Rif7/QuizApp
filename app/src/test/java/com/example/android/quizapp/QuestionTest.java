package com.example.android.quizapp;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class QuestionTest {
    private static final QuestionManager questionManager = new QuestionManager();

    @Test
    public void textEntryQuestionTest() throws Exception {
        TextEntryQuestion textEntryQuestion = questionManager.createTextQuestion("Question1", "Answer1");
        assertFalse(textEntryQuestion.isAnswered());
        assertFalse(textEntryQuestion.isCorrect());
        textEntryQuestion.setRespond("Answer1");
        assertTrue(textEntryQuestion.isAnswered());
        assertTrue(textEntryQuestion.isCorrect());

        textEntryQuestion = questionManager.createTextQuestion("Question2", "Answer2");
        textEntryQuestion.setRespond("Answer1");
        assertTrue(textEntryQuestion.isAnswered());
        assertFalse(textEntryQuestion.isCorrect());
    }

    @Test
    public void singleChoiceQuestionTest() throws Exception {
        String question = "Question2";
        String answer = "Answer1";
        String[] choices = {"Answer2", "Answer3", "Answer4"};

        SingleChoiceQuestion singleChoiceQuestion = questionManager.createSingleChoiceQuestion(question, answer, choices);
        assertFalse(singleChoiceQuestion.isAnswered());
        assertFalse(singleChoiceQuestion.isCorrect());
        ArrayList<Choice> choicesList = singleChoiceQuestion.getChoices();
        for (Choice choice : choicesList) {
            if (choice.getChoiceText().equals(choices[0])) {
                choice.setChosen(true);
                assertTrue(singleChoiceQuestion.isAnswered());
                assertFalse(singleChoiceQuestion.isCorrect());
                choice.setChosen(false);
                assertFalse(singleChoiceQuestion.isAnswered());
                assertFalse(singleChoiceQuestion.isCorrect());
            }
            if (choice.getChoiceText().equals(answer)) {
                choice.setChosen(true);
                assertTrue(singleChoiceQuestion.isAnswered());
                assertTrue(singleChoiceQuestion.isCorrect());
                choice.setChosen(false);
                assertFalse(singleChoiceQuestion.isAnswered());
                assertFalse(singleChoiceQuestion.isCorrect());
            }

        }
    }

    @Test
    public void multipleChoiceQuestionTest() throws Exception {
        String question = "Question2";
        String[] answers = {"Answer1", "Answer3"};
        String[] choices = {"Answer2", "Answer4"};

        MultipleChoiceQuestion multipleChoiceQuestion = questionManager.createMultipleChoiceQuestion(question, answers, choices);
        assertFalse(multipleChoiceQuestion.isAnswered());
        assertFalse(multipleChoiceQuestion.isCorrect());
        ArrayList<Choice> choicesList = multipleChoiceQuestion.getChoices();
        for (Choice choice : choicesList) {
            if (choice.getChoiceText().equals(answers[0])) {
                choice.setChosen(true);
            }
        }
        assertTrue(multipleChoiceQuestion.isAnswered());
        assertFalse(multipleChoiceQuestion.isCorrect());
        for (Choice choice : choicesList) {
            if (choice.getChoiceText().equals(answers[1])) {
                choice.setChosen(true);
            }
        }
        assertTrue(multipleChoiceQuestion.isAnswered());
        assertTrue(multipleChoiceQuestion.isCorrect());
        for (Choice choice : choicesList) {
            if (choice.getChoiceText().equals(choices[0])) {
                choice.setChosen(true);
            }
        }
        assertTrue(multipleChoiceQuestion.isAnswered());
        assertFalse(multipleChoiceQuestion.isCorrect());
    }

}