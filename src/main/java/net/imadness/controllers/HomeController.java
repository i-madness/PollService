package net.imadness.controllers;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.entities.Respondent;
import net.imadness.services.OptionService;
import net.imadness.services.PollService;
import net.imadness.services.QuestionService;
import net.imadness.services.RespondentService;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    PollService pollService;
    @Autowired
    QuestionService questionService;
    @Autowired
    OptionService optionService;
    @Autowired
    RespondentService respondentService;

	@RequestMapping("/")
	public String renderHome(ModelMap model) {
        /*Poll poll = new Poll("Опрос","(ужасный опрос)");
        Respondent respondent = new Respondent("Вася Пупкин","yarr@yarr.ya","127.0.0.1");
        Respondent respondent1 = new Respondent("Маша Креведкина","zeppa@mail.sru","6.6.6.6");*/
        Respondent respondent = respondentService.getRespondentById(12);
        //List<Respondent> respondents = respondentService.getAllRespondentsForPoll(poll);
        model.addAttribute("opt",respondent.getPolls());
        return "home";
	}
}
