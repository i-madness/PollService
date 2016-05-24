package net.imadness.mappers;

import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * DAO-интерфейс для доступа к сущности Question в БД
 */
public interface QuestionMapper {

    @Select("SELECT * FROM question")
    public List<Question> getAllQuestions();

    @Select("SELECT * FROM question WHERE id=#{id}")
    public Question getQuestionById(@Param("id") Long id);

    @Update("UPDATE question SET name=#{question.name} WHERE id=#{question.id}")
    public void updateQuestion(@Param("question") Question question);

    @Insert("INSERT INTO question (name, pollid) VALUES(#{question.name}, #{poll.id})")
    @Options(useGeneratedKeys=true, keyProperty = "question.id")
    public void addQuestion(@Param("question") Question question, @Param("poll") Poll poll);

    @Delete("DELETE FROM question WHERE id=#{id};")
    public void deleteQuestion(@Param("id") Long id);

    @Select("SELECT * FROM question WHERE pollid=#{poll.id}")
    public List<Question> getQuestionsForPoll(@Param("poll") Poll poll);

    /*@Select("SELECT * FROM question WHERE pollid=#{id}")
    public List<Question> getQuestionsForPoll(@Param("id") Long pollid);*/

}
