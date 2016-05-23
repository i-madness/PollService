package net.imadness.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.entities.Respondent;
import net.imadness.entities.extended.ResultHolder;
import net.imadness.services.dal.OptionService;
import net.imadness.services.dal.PollService;
import net.imadness.services.dal.QuestionService;
import net.imadness.services.dal.RespondentService;
import net.imadness.services.management.ParticipationService;
import net.imadness.services.management.PollViewRequestFilter;
import net.imadness.util.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для прохождения опросов, сохранения их результатов и предоставления их пользователю
 * через соответствующие представления.
 */
@Controller
public class PollController {

    @Autowired
    private PollService pollService;
    @Autowired
    private RespondentService respondentService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private PollViewRequestFilter validator;
    @Autowired
    private ParticipationService participationService;

    /**
     * Страница-реферрер, содержащая скрипт перенаправления на страницу опроса
     */
    @RequestMapping("/poll/r/{id}")
    public String pollPageReferrer(@PathVariable Long id, ModelMap modelMap) {
        modelMap.addAttribute("pollid", id);
        return "ref";
    }

    /**
     * Страница для прохождения опроса с конкретным ID
     * Если посетитель страницы уже проходил данный опрос, ему будут показаны его старые результаты
     * @param id ID опроса
     */
    @RequestMapping("/poll/{id}")
    public String preparePollView(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request) {
        if (!validator.checkRequest(request))
            throw new BadRequestException();
        Cookie[] cookies = request.getCookies();
        String userId = null, participatedCookie = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user"))
                userId = cookie.getValue();
            if (cookie.getName().equals("p" + id.toString())) {
                participatedCookie = cookie.getValue();
            }
        }
        if (userId != null && participatedCookie != null) {
            Long respId = Long.parseLong(userId);
            // показать пользователю его ответы
            for (Respondent respondent : respondentService.getAllRespondentsForPoll(id)) {
                if (respondent.getId().equals(respId)) {
                    modelMap.addAttribute("questions", respondentService.getQuestionsForRespondent(respondent));
                    int rightCount = 0;
                    for (Option option : respondent.getAnswers())
                        if (option.getRight())
                            rightCount++;
                    modelMap.addAttribute("rightCnt", rightCount);
                }
            }
        }
        Poll poll = pollService.getPollById(id);
        modelMap.addAttribute("poll", poll);
        modelMap.addAttribute("id", id);
        try {
            modelMap.addAttribute("pollJson", new ObjectMapper().writeValueAsString(poll));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "poll";
    }

    /**
     * Сохраняет результаты, полученные на странице опроса и предоставляет их пользователю
     * @param id ID опроса
     * @param resultHolder объект, содержащий в себе данные об опросе и ответы
     */
    @RequestMapping(value = "/poll/{id}/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> saveResults(@PathVariable Long id, @RequestBody ResultHolder resultHolder, HttpServletResponse response) {
        try {
            savePollResults(id,resultHolder);
            response.addCookie(new Cookie("p"+id,"true"));
            response.addCookie(new Cookie("user", resultHolder.getRespondent().getId().toString()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Принимает и сохраняет результаты опроса, пройденного на стороннем ресурсе
     * @return ответ сервера с указанием статуса
     */
    @Deprecated
    @ResponseBody
    @RequestMapping(value = "/poll/{id}/receiveResultsFromEmbed", method = RequestMethod.POST)
    public ResponseEntity<String> receiveResults(@PathVariable Long id, @RequestBody ResultHolder resultHolder, HttpServletResponse response) {
        try {
            savePollResults(id,resultHolder);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Передаёт правильные ответы клиенту для сравнения с его результатами
     * @return список из ID правильных ответов
     */
    @ResponseBody
    @RequestMapping(value = "/poll/{id}/getAnswers", method = RequestMethod.GET)
    public List getAnswersForPoll(@PathVariable Long id) {
        ArrayList<Long> answers = new ArrayList<>();
        Poll poll = pollService.getPollById(id);
        for (Question question : poll.getQuestions())
            for (Option option : question.getOptions())
                if (option.getRight())
                    answers.add(option.getId());
        return answers;
    }

    @ResponseBody
    @RequestMapping(value = "/poll/{id}/getVotes", method = RequestMethod.GET)
    public List getPollVotes(@PathVariable Long id) {
        return questionService.getVotesForPoll(id);
    }

    /**
     * Общий метод сохранения результатов опроса для определённого пользователя
     * @param id id опроса
     * @param resultHolder объект, содержащий в себе данные об опросе и ответы
     * @throws Exception исключение, перехватываемое вышестоящими методами контроллера
     */
    private void savePollResults(Long id, ResultHolder resultHolder) throws Exception {
        Poll completedPoll = pollService.getPollById(id);
        Respondent respondent = resultHolder.getRespondent();
        List<Long> answers = resultHolder.getOptions();
        // проверка на существование записи об опрошенном с такими же name и email - на уровне сервиса
        respondentService.addRespondent(respondent);
        // для m-to-n связей:
        for (Long optionId : answers)
            optionService.insertRespondentOption(respondent.getId(),optionId);
        pollService.insertPollRespondent(id,respondent.getId());
    }
}