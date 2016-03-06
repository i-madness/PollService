package net.imadness.services;

import net.imadness.entities.Poll;
import net.imadness.entities.Respondent;
import net.imadness.mappers.PollMapper;
import net.imadness.mappers.extended.PollRespondentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PollService {
    @Autowired
    private PollMapper pollMapper;
    @Autowired
    private PollRespondentMapper pollRespondentMapper;

    public void addPoll(Poll poll) {
        pollMapper.addPoll(poll);
    }

    public Poll getPollById(long id) {
        Poll result = pollMapper.getPollById(id);
        if (result != null)
            result.setRespondents(pollRespondentMapper.getRespondentsForPoll(id));
        return result;
    }

    public List<Poll> getAllPolls() {
        List<Poll> result = pollMapper.getAllPolls();
        if (result != null && result.size()>0) {
            for (Poll poll : result)
                poll.setRespondents(pollRespondentMapper.getRespondentsForPoll(poll.getId()));
        }
        return result;
    }

    public void updatePoll(Poll poll) {
        pollMapper.updatePoll(poll);
    }

    public void deletePoll(long id) {
        pollMapper.deletePoll(id);
    }

    public List<Poll> getPollsByRespondent(Respondent respondent) {
        return pollRespondentMapper.getPollsForRespondent(respondent.getId());
    }

    public void insertPollRespondent(Long pollId, Long respondentId) {
        pollRespondentMapper.insertPollRespondent(pollId,respondentId);
    }
}
