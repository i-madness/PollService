package net.imadness.controllers;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.entities.Respondent;
import net.imadness.entities.extended.ResultHolder;
import net.imadness.services.dal.OptionService;
import net.imadness.services.dal.PollService;
import net.imadness.services.dal.RespondentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для прохождения опросов, сохранения их результатов и предоставления их пользователю
 * через соответствующие представления.
 */
@Controller
public class PollController {

    @Autowired
    PollService pollService;
    @Autowired
    RespondentService respondentService;
    @Autowired
    OptionService optionService;

    /**
     * Страница для прохождения опроса с конкретным ID
     * @param id ID опроса
     */
    @RequestMapping("/poll/{id}")
    public String preparePollView(@PathVariable Long id, ModelMap modelMap) {
        Poll poll = pollService.getPollById(id);
        modelMap.addAttribute("poll",poll);
        modelMap.addAttribute("id",id);
        return "poll";
    }

    /**
     * Сохраняет результаты, полученные на странице опроса и предоставляет их пользователю
     * @param id ID опроса
     * @param resultHolder объект, содержащий в себе данные об опросе и ответы
     */
    @RequestMapping(value = "/poll/{id}/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public String saveResults(@PathVariable Long id, @RequestBody ResultHolder resultHolder, ModelMap modelMap) {
        Poll completedPoll = pollService.getPollById(id);
        Respondent respondent = resultHolder.getRespondent();
        List<Long> answers = resultHolder.getOptions();
        // проверка на существование записи об опрошенном с такими же name и email - на уровне сервиса
        respondentService.addRespondent(respondent);
        // для m-to-n связей:
        for (Long optionId : answers)
            optionService.insertRespondentOption(respondent.getId(),optionId);
        pollService.insertPollRespondent(id,respondent.getId());
        return "redirect:/";
    }

    /**
     * Передаёт правильные ответы клиенту для сравнения с его результатами
     * @return список из ID правильных ответов
     */
    @ResponseBody
    @RequestMapping(value = "/poll/{id}/getAnswers", method = RequestMethod.GET)
    public List getAnswersForPoll(@PathVariable Long id) {
        ArrayList<Long> answers = new ArrayList<Long>();
        Poll poll = pollService.getPollById(id);
        for (Question question : poll.getQuestions())
            for (Option option : question.getOptions())
                if (option.getRight())
                    answers.add(option.getId());
        return answers;
    }

}