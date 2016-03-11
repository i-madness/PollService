package net.imadness.services.management;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.services.dal.RespondentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Позволяет заносить статистику по опросу в CSV-файлы и предоставлять их администратору
 */
@Service
public class StatisticsService {

    @Autowired
    public RespondentService respondentService;

    /**
     * Формирует по опросу в формате "вопрос: количество правильно ответивших людей"
     * @param poll опрос, для которого необходимо сформировать статистику
     * @return данные в формате, необходимом для записи в файл с помощью CSVWriter
     */
    public List<String[]> getPollStatistics(Poll poll) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        result.add(new String[]{"Всего ответили",String.valueOf(respondentService.getAllRespondentsForPoll(poll).size())});
        for (Question question : poll.getQuestions()) {
            Integer respondentCount = 0;
            for (Option option : question.getOptions()) {
                if(option.getRight()) // считаем количество правильно ответивших пользователей
                    respondentCount += respondentService.getAllRespondentsForOption(option).size();
            }
            result.add(new String[]{question.getName(),respondentCount.toString()});
        }
        return result;
    }

    /**
     * Сохраняет статистику в *.csv-файл
     * @return имя файла
     */
    public ResponseEntity<byte[]> packStatistics(Poll poll) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String[]> lines = getPollStatistics(poll);
        for (String[] line : lines)
        // используем ; как разделитель (в формате MS Excel)
            stringBuilder.append("\"").append(line[0]).append("\";").append(line[1]).append("\n");
        byte[] output = stringBuilder.toString().getBytes();

        HttpHeaders responseHeaders = new HttpHeaders();

        String fileName = poll.getName().replaceAll("\\p{Cc}", "").replaceAll(" ","_") + ".csv"; // удалить все спецсимволы и пробелы
        responseHeaders.set("charset", "utf-8");
        responseHeaders.setContentType(MediaType.valueOf("text/html"));
        responseHeaders.setContentLength(output.length);
        responseHeaders.set("Content-disposition", "attachment; filename="+fileName);

        return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
    }

}
