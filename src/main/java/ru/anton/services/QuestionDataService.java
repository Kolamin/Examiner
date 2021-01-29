package ru.anton.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuestionDataService {

    public Map<String, String[]> getDataMap() {
        return dataMap;
    }

    private Map<String, String[]> dataMap = new TreeMap<>();

    @PostConstruct
    public void fetchQuestionData() throws IOException {

        Map<String, String[]> newDataMap = new TreeMap<>();

        Reader reader = new FileReader("./data/Thermal_power_plants.csv");

        Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader()
                .parse(reader);

        StringBuilder columnOne = new StringBuilder();
        for (CSVRecord record : records) {
            columnOne.append(record.get(0))
                    .append("\n");
        }


        AtomicInteger numberOfQuestion = new AtomicInteger(1);
        String[] split = columnOne.toString()
                .split("Вопрос \\d+");

        String[] result = Arrays.copyOfRange(split, 1, split.length);

        for (String s : result) {
            String[] strings = s.split("\\n");
            newDataMap.put(strings[1], Arrays.copyOfRange(strings, 2, strings.length));
        }


        newDataMap.forEach((k, v) -> {
            System.out.println("================");
            System.out.println("Вопрос " + numberOfQuestion.getAndIncrement());
            System.out.println(k);
            for (String s : v) {
                System.out.println("  * " + s);
            }

        });

        this.dataMap = newDataMap;
    }
}

