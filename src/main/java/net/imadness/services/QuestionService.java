package net.imadness.services;

import net.imadness.entities.Question;
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

    public void addOption(Question question) {
        questionMapper.addQuestion(question);
    }

    public Question getOptionById(long id) {
        return questionMapper.getQuestionById(id);
    }

    public void updateOption(Question question) {
        questionMapper.updateQuestion(question);
    }

    public void deleteOption(long id) {
        questionMapper.deleteQuestion(id);
    }

    public List<Question> getAllOptions() {
        return questionMapper.getAllQuestions();
    }
}
