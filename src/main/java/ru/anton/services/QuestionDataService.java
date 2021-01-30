package ru.anton.services;

import org.springframework.stereotype.Service;
import ru.anton.models.Question;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

        File file = new File("./data/Thermal_power_plants.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        StringBuilder fileContent = new StringBuilder();
        String st;
        while ((st = br.readLine()) != null) {
            fileContent.append(st).append("\n");
        }

        String[] split = fileContent.toString()
                .split("Вопрос \\d+");

        String[] result = Arrays.copyOfRange(split, 1, split.length);

        for (String s : result) {
            String[] strings = s.split("\\n");
            Question question = new Question();
            question.setId(++QUESTION_COUNT);
            question.setQuestion(strings[1]);
            question.setTestOptions( Arrays.copyOfRange(strings, 2, strings.length));

            newQuestion.add(question);
        }


        for (Question question : newQuestion) {
            System.out.println("================");
            System.out.println("Вопрос " + question.getId());
            System.out.println(question.getQuestion());
            String[] testOptions = question.getTestOptions();
            for (String testOption : testOptions) {
                System.out.println("  *"+testOption);
            }
        }

        this.allQuestion = newQuestion;
    }
}

