package net.imadness.controllers;

import net.imadness.entities.Poll;
import net.imadness.services.dal.PollService;
import net.imadness.services.dal.RespondentService;
import net.imadness.services.management.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Контроллер, отвечающий за предоставление администратору детальной статистики по опросам
 * и их участникам через соответствующие представления.
 */
@Controller
public class StatsController {

    @Autowired
    StatisticsService statisticsService;
    @Autowired
    PollService pollService;
    @Autowired
    RespondentService respondentService;

    /**
     * Помещает на страницу статистики список всех опросов
     */
    @RequestMapping("/manage/stats")
    public String prepareStatsView(ModelMap modelMap) {
        List<Poll> polls = pollService.getAllPollsWithoutFetch();
        modelMap.addAttribute("polls",polls);
        return "stats";
    }

    /**
     * Возвращает клиенту статистику в формате json
     */
    @RequestMapping("/manage/stats/{poll_id}")
    @ResponseBody
    public List getStatsForPoll(@PathVariable("poll_id") Long id) {
        Poll poll = pollService.getPollById(id);
        List<String[]> statistics = statisticsService.getPollStatistics(poll);
        return statistics;
    }

    /**
     * Возвращает клиенту .csv файл со статистикой
     */
    @RequestMapping(value = "/manage/stats/{poll_id}/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDownloadData(@PathVariable("poll_id") Long id) {
        Poll poll = pollService.getPollById(id);
        return statisticsService.packStatistics(poll);
    }
}
