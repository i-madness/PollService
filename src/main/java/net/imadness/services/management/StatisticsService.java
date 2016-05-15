package net.imadness.services.management;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.entities.Respondent;
import net.imadness.services.dal.RespondentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
     * Формирует статистику по опросу в формате "вопрос: количество правильно ответивших людей"
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
     * Для выбранного опроса возвращает ограниченный список участников с учётом пагинации
     * @param question опрос, для которого необходимо сформировать статистику
     * @param offset "отступ" от верхней записи в результате запроса
     * @return JSON со списком участников
     */
    public List<Respondent> getRespondentsOf(Question question, int offset) {
        ArrayList<Respondent> respondents = new ArrayList<>();
        respondentService.getPageableRespondentsForPoll(question.getPoll(), offset);
        return respondents;
    }

    /**
     * Предоставляет клиенту ответ сервера в виде .csv-файла со статистикой
     * @return ответ сервера с указанными заголовками для скачивания файла клиентом
     */
    public ResponseEntity<byte[]> packStatistics(Poll poll) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String[]> lines = getPollStatistics(poll);
        for (String[] line : lines)
        // используем ; как разделитель (в формате MS Excel)
            stringBuilder.append("\"").append(line[0]).append("\";").append(line[1]).append("\n");
        byte[] output = stringBuilder.toString().getBytes();

        HttpHeaders responseHeaders = new HttpHeaders();
        // удалить все спецсимволы и пробелы
        String fileName = TransliterationService.transliterate(poll.getName().replaceAll("\\p{Cc}", "").replaceAll(" ","_")) + ".csv";
        responseHeaders.set("charset", "utf-8");
        responseHeaders.setContentType(MediaType.valueOf("text/html"));
        responseHeaders.setContentLength(output.length);
        responseHeaders.set("Content-disposition", "attachment; filename="+fileName);

        return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
    }

}
