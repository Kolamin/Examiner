package ru.anton.services;

import org.springframework.stereotype.Service;
import ru.anton.models.Question;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class QuestionDataService {

    private static int QUESTION_COUNT;

    private static final String QUESTION_URL = "https://raw.githubusercontent.com/Kolamin/Questions/main/Thermal_power_plants.txt";


    List<Question> allQuestion = new ArrayList<>();

    public List<Question> getAllQuestion() {
        return allQuestion;
    }



    @PostConstruct
    public void fetchQuestionData() throws IOException, InterruptedException {

        List<Question> newQuestion = new ArrayList<>();


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(QUESTION_URL))
                .build();
        HttpResponse<String> httpResponse =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader stringReader = new StringReader(httpResponse.body());

        StringBuilder fileContent;
        try (BufferedReader br = new BufferedReader(stringReader)) {


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

