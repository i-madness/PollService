package net.imadness.services.dal;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.entities.Respondent;
import net.imadness.mappers.OptionMapper;
import net.imadness.mappers.PollMapper;
import net.imadness.mappers.QuestionMapper;
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
    private QuestionMapper questionMapper;
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private PollRespondentMapper pollRespondentMapper;

    /**
     * Обновляет/добавляет данные всех сущностей, связанных с данным экземпляром poll
     * @param poll объект Poll, для которого нужно обновить зависимости
     */
    private void updateAllDependencies(Poll poll) {
        for (Question question : poll.getQuestions()) {
            if (question.getId() != null)
                questionMapper.updateQuestion(question);
            else questionMapper.addQuestion(question,poll);
            for (Option option : question.getOptions()) {
                if(option.getId() != null)
                    optionMapper.updateOption(option);
                else optionMapper.addOption(option);
            }
        }
    }

    /**
     * Добавляет новый элемент poll в БД
     * @param poll добавляемый объект
     */
    public void addPoll(Poll poll) {
        pollMapper.addPoll(poll);
    }

    /**
     * Добавляет новый элемент poll в БД и обновляет все зависимые от него сущности
     * @param poll добавляемый объект
     */
    public void addPollFullStructure(Poll poll) {
        pollMapper.addPoll(poll);
        updateAllDependencies(poll);
    }

    /**
     * Получает все экземпляры Poll, имеющиеся в БД, не затрагивая зависимые от них объекты
     * @return коллекция из всех экземпляров Poll
     */
    public List<Poll> getAllPollsWithoutFetch() {
        return pollMapper.getAllPolls();
    }

    /**
     * Получает экземпляр сущности Poll по идентификатору, не затрагивая зависимые от неё объекты
     * @param id идентификатор экземпляра сущности Poll
     * @return полученный экземпляр Poll
     */
    public Poll getPollByIdWithoutFetch(long id) {
        return pollMapper.getPollById(id);
    }

    /**
     * Получает экземпляр сущности Poll по идентификатору
     * @param id идентификатор экземпляра сущности Poll
     * @return полученный экземпляр Poll
     */
    public Poll getPollById(long id) {
        Poll result = pollMapper.getPollById(id);
        if (result != null) {
            result.setRespondents(pollRespondentMapper.getRespondentsForPoll(id));
            result.setQuestions(questionMapper.getQuestionsForPoll(result));
            for (Question question : result.getQuestions()) {
                //question.setPoll(result);
                question.setOptions(optionMapper.getOptionsForQuestion(question));
            }
        }
        return result;
    }

    /**
     * Получает все экземпляры Poll, имеющиеся в БД
     * @return коллекция из всех экземпляров Poll
     */
    public List<Poll> getAllPolls() {
        List<Poll> result = pollMapper.getAllPolls();
        if (result != null && result.size()>0) {
            for (Poll poll : result) {
                poll.setRespondents(pollRespondentMapper.getRespondentsForPoll(poll.getId()));
                poll.setQuestions(questionMapper.getQuestionsForPoll(poll));
                for (Question question : poll.getQuestions()) {
                    //question.setPoll(poll);
                    question.setOptions(optionMapper.getOptionsForQuestion(question));
                }
            }
        }
        return result;
    }

    /**
     * Обновляет данные у уже существующего экземпляра сущности Poll
     * @param poll необходимый для обновления экземпляр
     */
    public void updatePoll(Poll poll) {
        pollMapper.updatePoll(poll);

    }

    /**
     * Обновляет данные у уже существующего экземпляра сущности Poll, а также все зависимые от неё объекты
     * @param poll
     */
    public void updatePollFullStructure(Poll poll) {
        pollMapper.updatePoll(poll);
        updateAllDependencies(poll);
    }

    /**
     * Удаляет экземпляр сущности Poll из БД
     * @param id идентификатор удаляемой сущности
     */
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
