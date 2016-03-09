package net.imadness.controllers;

import net.imadness.entities.Poll;
import net.imadness.services.dal.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Контроллер для главной страницы приложения
 */
@Controller
public class HomeController {

    @Autowired
    private PollService pollService;

    /**
     * Помещает опросы и их описание на главную страницу в виде списка
     */
	@RequestMapping("/")
	public String prepareHomepageView(ModelMap model) {
        List<Poll> polls = pollService.getAllPollsWithoutFetch();
        model.addAttribute("pollList",polls);
        return "home";
	}
}