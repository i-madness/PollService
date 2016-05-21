package net.imadness.services.dal;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.entities.Respondent;
import net.imadness.mappers.RespondentMapper;
import net.imadness.mappers.extended.PollRespondentMapper;
import net.imadness.mappers.extended.RespondentOptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private final int LIMIT = 5;

    /**
     * Добавляет запись об участнике опроса в БД
     * @param respondent добавляемый участник
     */
    public void addRespondent(Respondent respondent) {
        if(getRespondentByPersonalData(respondent.getName(),respondent.getIpAddress()) == null)
            respondentMapper.addRespondent(respondent);
    }

    /**
     * Получает объект участника из БД по его ID
     * @param id искомый id участника
     */
    public Respondent getRespondentById(long id) {
        Respondent result = respondentMapper.getRespondentById(id);
        if (result != null)
            result.setPolls(pollRespondentMapper.getPollsForRespondent(id));
        return result;
    }

    /**
     *
     * @param name
     * @param email
     */
    public Respondent getRespondentByPersonalData(String name, String email) {
        return respondentMapper.getRespondentByPersonalData(name,email);
    }

    /**
     * Переписывает существующую запись участника в БД
     * @param respondent новый вариант для переписываемой записи
     */
    public void updateRespondent(Respondent respondent) {
        respondentMapper.updateRespondent(respondent);
    }

    /**
     * Удаляет запись об участнике из БД
     * @param id id удаляемого участника
     */
    public void deleteRespondent(long id) {
        respondentMapper.deleteRespondent(id);
    }

    /**
     * Возвращает все имеющиеся записи об участниках и собирает все данные дочерних объектах
     */
    public List<Respondent> getAllRespondents() {
        List<Respondent> result = respondentMapper.getAllRespondents();
        if (result != null && result.size() > 0) {
            for (Respondent respondent : result) {
                respondent.setPolls(pollRespondentMapper.getPollsForRespondent(respondent.getId()));
                respondent.setAnswers(respondentOptionMapper.getOptionsForRespondent(respondent.getId()));
            }
        }
        return result;
    }

    /**
     * Находит и возвращает всех участников для данного опроса
     * @param poll данный опрос
     */
    public List<Respondent> getAllRespondentsForPoll(Poll poll) {
        return pollRespondentMapper.getRespondentsForPoll(poll.getId());
    }

    /**
     * Возвращает список из ограниченного числа участников для данного вопроса
     * Требуется для постраничного отображения участников
     * @see net.imadness.services.management.StatisticsService [ getRespondentsOf(Poll) ]
     * @param questionId id данного вопроса
     * @param offset "отступ" от верхней записи в результате запроса
     */
    public List<Respondent> getPageableRespondentsForQuestion(Long optionId, Long questionId, int offset) {
        return respondentOptionMapper.getPageableRespondentsForOption(optionId, questionId, LIMIT, offset);
    }

    /**
     * Находит и возвращает всех участников для данного опроса
     * @param pollId ID данного опроса
     */
    public List<Respondent> getAllRespondentsForPoll(Long pollId) {
        List<Respondent> respondents = pollRespondentMapper.getRespondentsForPoll(pollId);
        for (Respondent respondent : respondents) {
            respondent.setAnswers(respondentOptionMapper.getOptionsForRespondent(respondent.getId()));
        }
        return respondents;
    }

    /**
     * Находит и возвращает всех участников, выбравших данный вариант ответа
     * @param option данный вариант ответа
     */
    public List<Respondent> getAllRespondentsForOption(Option option) {
        return respondentOptionMapper.getRespondentsForOption(option.getId());
    }

    /**
     * Находит и возвращает все вопросы, в которых участвовал данный пользователь
     * @param respondent участник искомых вопросов
     * @return список из вопросов
     */
    public List<Question> getQuestionsForRespondent(Respondent respondent) {
        ArrayList<Question> questions = new ArrayList<>();
        for (Option option : respondentOptionMapper.getOptionsForRespondent(respondent.getId()))
            questions.add(option.getQuestion());
        return questions;
    }

}
