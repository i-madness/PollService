package net.imadness.controllers;

import net.imadness.entities.extended.ResultHolder;
import net.imadness.services.dal.PollService;
import net.imadness.services.dal.RespondentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PollController {

    @Autowired
    PollService pollService;
    @Autowired
    RespondentService respondentService;

    @RequestMapping("/poll/{id}")
    public String preparePollView(@PathVariable Long id, ModelMap modelMap) {
        modelMap.addAttribute("poll",pollService.getPollById(id));
        return "poll";
    }

    @RequestMapping("/poll/{id}/save")
    public String saveResults(@PathVariable Long id, @RequestBody ResultHolder resultHolder) {
        pollService.updatePoll(resultHolder.getPoll());
        respondentService.updateRespondent(resultHolder.getRespondent());

        return "redirect:/";
    }
}
