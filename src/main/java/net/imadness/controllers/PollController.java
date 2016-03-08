package net.imadness.controllers;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.entities.extended.ResponseHolder;
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
    @ResponseBody
    @RequestMapping(value = "/poll/{id}/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseHolder saveResults(@PathVariable Long id, @RequestBody ResultHolder resultHolder, ModelMap modelMap) {
        Poll completedPoll = pollService.getPollById(id);
        List<Long> answerIds = resultHolder.getOptions();
        List<Long> rightAnswers = new ArrayList<Long>();
        List<Long> wrongAnswers = new ArrayList<Long>();
        Float percent = (float)rightAnswers.size()/(float)answerIds.size();
        for (Long answerId : answerIds) {
            if(optionService.getOptionById(answerId).getRight())
                rightAnswers.add(answerId);
            else wrongAnswers.add(answerId);
        }
        modelMap.addAttribute("poll",completedPoll);
        modelMap.addAttribute("rightOpts",rightAnswers);
        modelMap.addAttribute("wrongOpts",wrongAnswers);
        //respondentService.updateRespondent(resultHolder.getRespondent());
        ResponseHolder responseHolder = new ResponseHolder(percent,wrongAnswers,rightAnswers,completedPoll);
        return responseHolder;
    }

    // временный метод
    @ResponseBody
    @RequestMapping("/getholder/")
    public ResultHolder getHolder() {
        List<Option> options = optionService.getAllOptions();
        List<Long> ids = new ArrayList<Long>();
        for (Option o : options)
            ids.add(o.getId());
        ResultHolder resultHolder = new ResultHolder(respondentService.getAllRespondents().get(0),ids);
        return resultHolder;
    }

}
