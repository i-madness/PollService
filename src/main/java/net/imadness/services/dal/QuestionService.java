package net.imadness.services.dal;

import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.mappers.OptionMapper;
import net.imadness.mappers.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    OptionMapper optionMapper;

    public void addQuestion(Question question, Poll poll) {
        questionMapper.addQuestion(question,poll);
    }

    public Question getQuestionById(long id) {
        return questionMapper.getQuestionById(id);
    }

    public void updateQuestion(Question question) {
        questionMapper.updateQuestion(question);
    }

    public void deleteQuestion(long id) {
        questionMapper.deleteQuestion(id);
    }

    public List<Question> getAllQuestionsWithoutFetch() {
        return questionMapper.getAllQuestions();
    }

    public List<Question> getAllQuestions() {
        List<Question> result = questionMapper.getAllQuestions();
        for (Question question : result) {
            //question.setPoll();
            question.setOptions(optionMapper.getOptionsForQuestion(question));
        }
        return result;
    }
}
