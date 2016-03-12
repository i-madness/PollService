package net.imadness.controllers;

import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.services.dal.OptionService;
import net.imadness.services.dal.PollService;
import net.imadness.services.dal.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер, отвечающий за авторизацию администратора, управление опросами с соответствующей страницы.
 * Предоставляет администратору все необходимые CRUD-операции через REST-интерфейс.
 */
@Controller
public class PollManagementController {

    @Autowired
    private PollService pollService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private OptionService optionService;

    /**
     * Помещает список опросов на страницу управления опросами
     */
    @RequestMapping(value = "/manage")
    public String preparePollManagementView(ModelMap model) {
        List<Poll> pollList = pollService.getAllPollsWithoutFetch();
        model.addAttribute("polls",pollList);
        return "manage";
    }

    /**
     * Сохраняет объект Poll, полученный от клиента
     * @param poll json объект Poll
     * @return перенаправление на страницу настроек
     */
    @RequestMapping(value = "/manage/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public String savePoll(@RequestBody Poll poll) {
        if (poll.getId()==null)
            pollService.addPollFullStructure(poll);
        else pollService.updatePollFullStructure(poll);
        return "redirect:/manage";
    }

    /**
     * В сохраняет вопрос для конкретно заданного опроса
     */
    @RequestMapping(value = "/manage/save/{pollId}/question")
    public String saveQuestion(@PathVariable Long pollId, @RequestBody Question question) {
        Poll containingPoll = pollService.getPollById(pollId);
        questionService.addQuestion(question,containingPoll);
        return "redirect:/manage";
    }

    /**
     * Маппинг запроса на получение опроса
     */
    @RequestMapping("/manage/getpoll/{id}")
    @ResponseBody
    public Poll getPoll(@PathVariable Long id) {
        return pollService.getPollById(id);
    }

    /**
     * Маппинг запроса на удаление опроса
     */
    @RequestMapping("/manage/deletepoll/{id}")
    public String deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);
        return "redirect:/manage";
    }

    /**
     * Маппинг запроса на удаление вопроса
     */
    @RequestMapping("/manage/deletequestion/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/manage";
    }

    /**
     * Маппинг запроса на удаление варианта ответа
     */
    @RequestMapping("/manage/deleteoption/{id}")
    public String deleteOption(@PathVariable Long id) {
        optionService.deleteOption(id);
        return "redirect:/manage";
    }

}
