package net.imadness.controllers;

import net.imadness.entities.Poll;
import net.imadness.services.dal.PollService;
import net.imadness.services.dal.QuestionService;
import net.imadness.services.dal.RespondentService;
import net.imadness.services.management.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    QuestionService questionService;
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
    public List showStatsForPoll(@PathVariable("poll_id") Long id) {
        Poll poll = pollService.getPollById(id);
        List<String[]> statistics = statisticsService.getPollStatistics(poll);
        return statistics;
    }

    /**
     * Возвращает клиенту список участников, ответивших на данный вопрос
     */
    @RequestMapping("/manage/stats/questionresp/") //{question_id}
    @ResponseBody
    public List showRespondentsForQuestion(/*@PathVariable("question_id") Long id, @RequestParam int offset*/) {
        return respondentService.getAllRespondents();
        //return statisticsService.getRespondentsOf(questionService.getQuestionById(id), 5);
    }

    /**
     * Возвращает клиенту .csv файл со статистикой
     */
    @RequestMapping(value = "/manage/stats/{poll_id}/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> showDownloadData(@PathVariable("poll_id") Long id) {
        Poll poll = pollService.getPollById(id);
        return statisticsService.packStatistics(poll);
    }
}
