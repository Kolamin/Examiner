package ru.anton.services;

import org.springframework.stereotype.Service;
import ru.anton.models.Question;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class QuestionDataService {

    private static int QUESTION_COUNT;

    List<Question> allQuestion = new ArrayList<>();

    public List<Question> getAllQuestion() {
        return allQuestion;
    }



    @PostConstruct
    public void fetchQuestionData() throws IOException {

        List<Question> newQuestion = new ArrayList<>();

        QuestionDataService obj = new QuestionDataService();

        InputStream inputStream = obj.getClass()
                .getClassLoader()
                .getResourceAsStream("./data/Thermal_power_plants.txt");


        StringBuilder fileContent;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {


            fileContent = new StringBuilder();
            String st;
            while ((st = br.readLine()) != null) {
                fileContent.append(st)
                        .append("\n");
            }
        }

        String[] split = fileContent.toString()
                .split("Вопрос \\d+");

        String[] result = Arrays.copyOfRange(split, 1, split.length);

        for (String s : result) {
            String[] strings = s.split("\\n");
            Question question = new Question();
            question.setId(++QUESTION_COUNT);
            question.setQuestion(strings[1]);
            question.setTestOptions(Arrays.copyOfRange(strings, 2, strings.length));

            newQuestion.add(question);
        }

        this.allQuestion = newQuestion;
    }
}

