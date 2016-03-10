package net.imadness.controllers;

import net.imadness.services.dal.PollService;
import net.imadness.services.dal.RespondentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер, отвечающий за предоставление администратору детальной статистики по опросам
 * и их участникам через соответствующие представления.
 */
@Controller
public class StatsController {

    @Autowired
    PollService pollService;
    @Autowired
    RespondentService respondentService;

    @RequestMapping("/manage/stats")
    public String prepareStatsView() {
        /* TODO:

         * статистика по опросу: сколько людей прошло, [вопрос[i]: вариант[j] - ответило N]
         * статистика по пользователю: какие опросы прошёл, какие неправильные ответы по каждому опросу
        */
        return "stats";
    }



}
