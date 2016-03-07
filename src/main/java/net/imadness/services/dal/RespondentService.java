package net.imadness.services.dal;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Respondent;
import net.imadness.mappers.RespondentMapper;
import net.imadness.mappers.extended.PollRespondentMapper;
import net.imadness.mappers.extended.RespondentOptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RespondentService {

    @Autowired
    private RespondentMapper respondentMapper;
    @Autowired
    private PollRespondentMapper pollRespondentMapper;
    @Autowired
    private RespondentOptionMapper respondentOptionMapper;

    public void addRespondent(Respondent respondent) {
        respondentMapper.addRespondent(respondent);
    }

    public Respondent getRespondentById(long id) {
        Respondent result = respondentMapper.getRespondentById(id);
        if (result != null)
            result.setPolls(pollRespondentMapper.getPollsForRespondent(id));
        return result;
    }

    public void updateOption(Respondent respondent) {
        respondentMapper.updateRespondent(respondent);
    }

    public void deleteRespondent(long id) {
        respondentMapper.deleteRespondent(id);
    }

    public List<Respondent> getAllRespondents() {
        List<Respondent> result = respondentMapper.getAllRespondents();
        if (result != null && result.size()>0) {
            for (Respondent respondent : result) {
                respondent.setPolls(pollRespondentMapper.getPollsForRespondent(respondent.getId()));
                respondent.setAnswers(respondentOptionMapper.getOptionsForRespondent(respondent.getId()));
            }
        }
        return result;
    }

    public List<Respondent> getAllRespondentsForPoll(Poll poll) {
        return pollRespondentMapper.getRespondentsForPoll(poll.getId());
    }

    public List<Respondent> getAllRespondentsForOption(Option option) {
        return respondentOptionMapper.getRespondentsForOption(option.getId());
    }

}
