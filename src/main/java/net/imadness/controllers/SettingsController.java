package net.imadness.controllers;

import net.imadness.services.management.SettingsService;
import net.imadness.util.exceptions.SettingsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    /**
     * Помещает текущие администраторские настройки на страницу настроек
     */
    @RequestMapping("/manage/settings")
    public String getSettings(ModelMap modelMap, @RequestParam(required = false) String event) {
        if (event != null && event.equals("error"))
            throw new SettingsException();
        else if (event != null && event.equals("applied"))
            modelMap.addAttribute("success", true);
        modelMap.addAttribute("settings", settingsService.getSettings());
        modelMap.addAttribute("settingsPath", settingsService.getPath());
        return "prefs";
    }

    /**
     * Принимает от клиента объект с новыми администраторскими настройками
     */
    @RequestMapping(value = "/manage/settings/setNew", method = RequestMethod.POST, headers = "Accept=application/json")
    public String applySettings(@RequestBody Object newSettings) {
        try {

            //settingsService.setSettings(newSettings);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/manage/settings?event=error";
        }
        return "redirect:/manage/settings?event=applied";
    }

    /*@RequestMapping(value = "/postset", method = RequestMethod.POST)
    @ResponseBody
    public Settings postSettings(@RequestBody Settings settings) {
        return settings;
    }*/

}
