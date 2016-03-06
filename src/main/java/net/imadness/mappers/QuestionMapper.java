package net.imadness.mappers;

import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface QuestionMapper {

    @Select("SELECT * FROM question")
    @Results(value = {
            @Result(property = "id",      column = "question_id"),
            @Result(property = "name",    column = "name"),
            @Result(property = "poll",    column = "poll",    javaType = Poll.class,
                    one  = @One(select = "net.imadness.mappers.PollMapper.getPollById")),
            @Result(property = "options", column = "options", javaType = List.class,
                    many = @Many(select = "net.imadness.mappers.OptionMapper.getOptionsForQuestion"))
    })
    public List<Question> getAllQuestions();

    @Select("SELECT * FROM question WHERE id=#{id}")
    @Results(value = {
            @Result(property = "id",      column = "id"),
            @Result(property = "name",    column = "name"),
            @Result(property = "poll",    column = "poll",    javaType = Poll.class,
                    one  = @One(select = "net.imadness.mappers.PollMapper.getPollById")),
            @Result(property = "options", column = "options", javaType = List.class,
                    many = @Many(select = "net.imadness.mappers.OptionMapper.getOptionsForQuestion"))
    })
    public Question getQuestionById(@Param("id") Long id);

    @Update("UPDATE question SET name=#{question.name} WHERE id=#{question.id}")
    public void updateQuestion(@Param("question") Question question);

    @Insert("INSERT INTO question (name) VALUES(#{question.name})")
    @Options(useGeneratedKeys=true, keyProperty = "question.id")
    public void addQuestion(@Param("question") Question question);

    @Delete("DELETE FROM question WHERE id=#{id};")
    public void deleteQuestion(@Param("id") Long id);

}
