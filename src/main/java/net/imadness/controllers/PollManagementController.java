package net.imadness.controllers;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.services.dal.OptionService;
import net.imadness.services.dal.PollService;
import net.imadness.services.dal.QuestionService;
import net.imadness.services.management.AdminPreferencesService;
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
    @Autowired
    private AdminPreferencesService adminPreferencesService;

    /**
     * Выполняет авторизацию по полученным данным и предоставляет данные по имеющимся опросам
     * @param login имя администратора
     * @param password пароль администратора
     * @return адрес страницы настроек или перенаправление на главную страницу (в зависимости от успешности авторизации)
     */
    @RequestMapping(value = "/manage" /*method = RequestMethod.POST*/)
    public String preparePollManagementView(ModelMap model/*, @RequestParam("login") String login, @RequestParam("password") String password*/) {
        //if(adminPreferencesService.preferencesEqual(login,password)){
            List<Poll> pollList = pollService.getAllPollsWithoutFetch();
            model.addAttribute("polls",pollList);
            return "manage";
        //}
        //else return "redirect:/";
    }

    /**
     *
     * @param newLogin новое имя администратора
     * @param newPassword новый пароль администратора
     * @return перенаправление на страницу настроек
     */
    @RequestMapping(value = "/manage/saveprefs", method = RequestMethod.POST)
    public String saveAdminPreferences(@RequestParam("login") String newLogin, @RequestParam("password") String newPassword) {
        adminPreferencesService.setPreferences(newLogin,newPassword);
        return "redirect:/manage";
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

    @RequestMapping(value = "/manage/save/{pollId}/question")
    public String saveQuestion(@PathVariable Long pollId, @RequestBody Question question) {
        Poll containingPoll = pollService.getPollById(pollId);
        questionService.addQuestion(question,containingPoll);
        return "redirect:/manage";
    }

    @RequestMapping("/manage/getpoll/{id}")
    @ResponseBody
    public Poll getPoll(@PathVariable Long id) {
        return pollService.getPollById(id);
    }
    @RequestMapping("/manage/getquestion/{id}")
    @ResponseBody
    public Question getQuestion(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    // Mapping для запросов на удаление
    @RequestMapping("/manage/deletepoll/{id}")
    public String deletePoll(@PathVariable Long id/*, @RequestParam String login, @RequestParam String password*/) {
        //if(adminPreferencesService.preferencesEqual(login,password)) {
            pollService.deletePoll(id);
            return "redirect:/manage";
        //}
        //return "redirect:/";
    }
    @RequestMapping("/manage/deletequestion/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/manage";
    }
    @RequestMapping("/manage/deleteoption/{id}")
    public String deleteOption(@PathVariable Long id) {
        optionService.deleteOption(id);
        return "redirect:/manage";
    }

}
