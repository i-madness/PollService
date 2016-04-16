package net.imadness.controllers;

import net.imadness.entities.Poll;
import net.imadness.services.dal.PollService;
import net.imadness.services.management.MailVerificationService;
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
    @Autowired
    private MailVerificationService mailVerificationService;

    /**
     * Помещает опросы и их описание на главную страницу в виде списка
     */
	@RequestMapping("/")
	public String prepareHomepageView(ModelMap model) {
        List<Poll> polls = pollService.getAllPollsWithoutFetch();
        model.addAttribute("pollList",polls);
        return "home";
	}

    @RequestMapping("/m")
    public String send() {
        mailVerificationService.send("valeri-romanov@ya.ru","TEST LTR","TEST SOMETHING");
        // mailVerificationService.sendMail("valeri-romanov@ya.ru","TEST LTR","TEST SOMETHING");
        return "redirect:/";
    }
}