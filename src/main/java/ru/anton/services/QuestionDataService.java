package ru.anton.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.anton.models.Question;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

        Reader reader = new FileReader("./data/Thermal_power_plants.csv");

        Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader()
                .parse(reader);

        StringBuilder columnOne = new StringBuilder();
        for (CSVRecord record : records) {
            columnOne.append(record.get(0))
                    .append("\n");
        }

        String[] split = columnOne.toString()
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

