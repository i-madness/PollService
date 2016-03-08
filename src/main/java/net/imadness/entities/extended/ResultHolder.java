package net.imadness.entities.extended;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.imadness.entities.Poll;
import net.imadness.entities.Respondent;

import java.util.List;

/**
 * Класс, необходимый для передачи данных об участнике опроса и его ответах через JSON в виде одного объекта
 */
public class ResultHolder {
    private Respondent respondent;
    private List<Long> optionIds;

    public ResultHolder() {}

    public ResultHolder(Respondent respondent, List<Long> options) {
        //this.poll = poll;
        this.respondent = respondent;
        this.optionIds = options;
    }

    /*public Poll getPoll() {
        return poll;
    }*/

    /*public void setPoll(Poll poll) {
        this.poll = poll;
    }*/

    public Respondent getRespondent() {
        return respondent;
    }

    public void setRespondent(Respondent respondent) {
        this.respondent = respondent;
    }

    public List<Long> getOptions() {
        return optionIds;
    }

    public void setOptions(List<Long> options) {
        this.optionIds = options;
    }
}
