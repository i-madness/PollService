package net.imadness.entities.extended;

import net.imadness.entities.Poll;

import java.util.List;

/**
 * Created by Валерий on 08.03.2016.
 */
public class ResponseHolder {
    private Float percent;
    private List<Long> wrongAnswers;
    private List<Long> rightAnswers;
    private Poll completedPoll;

    public ResponseHolder() {}

    public ResponseHolder(Float percent, List<Long> wrongAnswers, List<Long> rightAnswers, Poll completedPoll) {
        this.percent = percent;
        this.wrongAnswers = wrongAnswers;
        this.rightAnswers = rightAnswers;
        this.completedPoll = completedPoll;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public List<Long> getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(List<Long> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public List<Long> getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(List<Long> rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public Poll getCompletedPoll() {
        return completedPoll;
    }

    public void setCompletedPoll(Poll completedPoll) {
        this.completedPoll = completedPoll;
    }
}
