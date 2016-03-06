package net.imadness.mappers;

import net.imadness.entities.Poll;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PollMapper {

    @Select("SELECT * FROM poll")
    public List<Poll> getAllPolls();

    @Select("SELECT * FROM poll WHERE id=#{id}")
    public Poll getPollById(@Param("id") Long id);

    @Update("UPDATE poll SET content=#{poll.name}, description=#{description} WHERE id=#{poll.id}")
    public void updatePoll(@Param("poll") Poll poll);

    @Insert("INSERT INTO poll (name,description) VALUES(#{poll.name}, #{poll.description})")
    @Options(useGeneratedKeys=true, keyProperty = "poll.id")
    public void addPoll(@Param("poll") Poll poll);

    @Delete("DELETE FROM poll WHERE id=#{id}")
    public void deletePoll(@Param("id") Long id);

}
