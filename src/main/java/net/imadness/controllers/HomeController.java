package net.imadness.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.imadness.entities.Poll;
import net.imadness.services.dal.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	public String prepareHomePageView(ModelMap model) {
        List<Poll> all = pollService.getAllPollsWithoutFetch();
        List<Poll> polls = pollService.getPollsOnly();
        List<Poll> tests = pollService.getTestsOnly();
        model.addAttribute("pollList", all);
        try {
            model.addAttribute("jsonAllList", new ObjectMapper().writeValueAsString(all));
            model.addAttribute("jsonPollList", new ObjectMapper().writeValueAsString(polls));
            model.addAttribute("jsonTestList", new ObjectMapper().writeValueAsString(tests));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "home";
	}

    /**
     * Возвращает клиенту список из всех имеющихся тестов
     */
    @RequestMapping("/allTestsList")
    @ResponseBody
    public List getTestList() {
        return pollService.getTestsOnly();
    }

    /**
     * Возвращает клиенту список из всех имеющихся опросов (всё, кроме тестов)
     */
    @RequestMapping("/allPollsList")
    @ResponseBody
    public List getPollList() {
        return pollService.getPollsOnly();
    }

    /**
     * Задаёт URL-маппинг для страницы входа в систему для администратора сервиса
     */
    @RequestMapping("/auth")
	public String prepareLoginPageView(ModelMap model) {
        return "auth";
	}

    /**
     * Задаёт URL-маппинг для выхода из системы для администратора сервиса
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

}