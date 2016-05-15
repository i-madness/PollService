package net.imadness.mappers.extended;

import net.imadness.entities.Poll;
import net.imadness.entities.Respondent;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Расширенный DAO-интерфейс, необходимый для работы связи "многие ко многим" в таблицах poll и respondent
 * @see Poll
 * @see Respondent
 */
public interface PollRespondentMapper {

    @Select("SELECT * FROM poll p WHERE p.id = ANY(" +
            "SELECT pollid FROM poll_respondent WHERE respondentid=#{respondentId})")
    public List<Poll> getPollsForRespondent(@Param("respondentId") Long respondentId);

    @Select("SELECT * FROM respondent r WHERE r.id = ANY(" +
            "SELECT respondentid FROM poll_respondent WHERE pollid=#{pollId})")
    public List<Respondent> getRespondentsForPoll(@Param("pollId") Long pollId);


    @Select("SELECT * FROM respondent r WHERE r.id = ANY(" +
            "SELECT respondentid FROM poll_respondent WHERE pollid=#{pollId})" +
            "LIMIT #{limit} OFFSET #{offset}")
    public List<Respondent> getPageableRespondentsForPoll(@Param("pollId") Long pollId, @Param("limit") int limit, @Param("offset") int offset);

    @Insert("INSERT INTO poll_respondent(pollid,respondentid) VALUES(#{pollId},#{respondentId})")
    public void insertPollRespondent(@Param("pollId") Long pollId, @Param("respondentId") Long respondentId);

}
