package net.imadness.entities.extended;

import net.imadness.entities.Poll;
import net.imadness.entities.Respondent;
import org.apache.ibatis.annotations.Options;

public class ResultHolder {
    private Poll poll;
    private Respondent respondent;
    private Options options;

    public ResultHolder(Poll poll, Respondent respondent, Options options) {
        this.poll = poll;
        this.respondent = respondent;
        this.options = options;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Respondent getRespondent() {
        return respondent;
    }

    public void setRespondent(Respondent respondent) {
        this.respondent = respondent;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
}
