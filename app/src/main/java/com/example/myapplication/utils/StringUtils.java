package com.example.myapplication.utils;

import android.content.Intent;

import com.example.myapplication.model.Colors;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Quiz;

import java.util.ArrayList;

public class StringUtils {
    public static Quiz parseQuestions(String attributes) {
        String strTimeOut = attributes.substring(attributes.lastIndexOf("{") + 1, attributes.lastIndexOf("}"));
        int timeOut = Integer.parseInt(strTimeOut);
        String questionsAttributes = attributes.substring(0, attributes.lastIndexOf("{"));

        ArrayList<Question> questions = new ArrayList<>();
        Question[] questionsArray;
        int indexBraketOpen = -1;
        int indexBraketClose = -1;

        while (true) {
            indexBraketOpen = questionsAttributes.indexOf("[", indexBraketOpen + 1);
            indexBraketClose = questionsAttributes.indexOf("]", indexBraketClose + 1);
            if (indexBraketOpen == -1) {
                break;
            }
            String questionAtt = questionsAttributes.substring(indexBraketOpen + 1, indexBraketClose);
            Question question;
            String questionText = "";
            boolean questionAnswer = false, questionIsCheat = false;
            String colorStr = "";
            Colors color;
            int indexBracerOpen = -1;
            int indexBracerClose = -1;
            int attributeIndex;

            for (int i = 0; i < 4; i++) {
                indexBracerOpen = questionAtt.indexOf("{", indexBracerOpen + 1);
                indexBracerClose = questionAtt.indexOf("}", indexBracerClose + 1);
                switch (i) {
                    case 0:
                        questionText = questionAtt.substring(indexBracerOpen + 1, indexBracerClose);
                        break;
                    case 1:
                        questionAnswer = Boolean.parseBoolean(questionAtt.substring(indexBracerOpen + 1, indexBracerClose));
                        break;
                    case 2:
                        questionIsCheat = Boolean.parseBoolean(questionAtt.substring(indexBracerOpen + 1, indexBracerClose));
                        break;
                    case 3:
                        colorStr = questionAtt.substring(indexBracerOpen + 1, indexBracerClose);
                        break;
                    default:
                        break;
                }
            }

            color = Colors.valueOf(colorStr.toUpperCase());
            question = new Question(questionText, questionAnswer, questionIsCheat, color);
            questions.add(question);

        }



        //questionsArray = (Question[]) questions.toArray();
        return new Quiz(questions,timeOut);
    }
}
