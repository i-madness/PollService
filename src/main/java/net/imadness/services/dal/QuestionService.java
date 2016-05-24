package net.imadness.services.dal;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.mappers.OptionMapper;
import net.imadness.mappers.QuestionMapper;
import net.imadness.mappers.extended.RespondentOptionMapper;
import org.apache.ibatis.type.IntegerTypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private RespondentOptionMapper respondentOptionMapper;

    /**
     * Добавляет объект новый вопрос в БД
     * @param question добавляемый объект
     * @param poll опрос, содержащий данный вопрос
     */
    public void addQuestion(Question question, Poll poll) {
        questionMapper.addQuestion(question,poll);
    }

    /**
     * Получает объект вопроса по его id
     * @param id id вопроса
     * @return объект Question
     */
    public Question getQuestionById(long id) {
        return questionMapper.getQuestionById(id);
    }

    /**
     * Переписывает существующую запись о вопросе в БД
     * @param question новая версия записи
     */
    public void updateQuestion(Question question) {
        questionMapper.updateQuestion(question);
    }

    /**
     * Удаляет вопрос из БД
     * @param id id удаляемого вопроса
     */
    public void deleteQuestion(long id) {
        questionMapper.deleteQuestion(id);
    }

    /**
     * Извлекает все записи о вопросах из БД, не затрагивая при этом зависимые таблицы
     * @return список из вопросов
     */
    public List<Question> getAllQuestionsWithoutFetch() {
        return questionMapper.getAllQuestions();
    }

    public List<Question> getAllQuestions() {
        List<Question> result = questionMapper.getAllQuestions();
        for (Question question : result) {
            question.setOptions(optionMapper.getOptionsForQuestion(question));
        }
        return result;
    }

    /**
     * Извлекает все записи о вопросах для данного опроса / теста из БД
     * @param pollId опрос / тест
     * @return список из вопросов
     */
    public List<Question> getQuestionsForPoll(Long pollId) {
        Poll poll = new Poll();
        poll.setId(pollId);
        List<Question> result = questionMapper.getQuestionsForPoll(poll);
        for (Question question : result) {
            question.setOptions(optionMapper.getOptionsForQuestion(question));
        }
        return result;
    }

    /**
     * Для данного опроса создаёт список из голосов за каждый вариант ответа
     * @param pollId данный опрос
     * @return список значений Integer
     */
    public List<Integer> getVotesForPoll(Long pollId) {
        List<Question> questions = this.getQuestionsForPoll(pollId);
        List<Integer> voteList = new ArrayList<>();
        for (Option option : questions.get(0).getOptions())
            voteList.add(respondentOptionMapper.getVoteCount(option.getId()));
        return voteList;
    }
}
