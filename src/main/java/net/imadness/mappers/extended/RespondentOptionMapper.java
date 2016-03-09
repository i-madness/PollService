package net.imadness.mappers.extended;

import net.imadness.entities.Option;
import net.imadness.entities.Respondent;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Расширенный DAO-интерфейс, необходимый для работы связи "многие ко многим" в таблицах option и respondent
 * @see Option
 * @see Respondent
 */
public interface RespondentOptionMapper {

    @Select("SELECT * FROM option o WHERE o.id = ANY(" +
            "SELECT optionid FROM respondent_option WHERE respondentid=#{respondentId})")
    public List<Option> getOptionsForRespondent(@Param("respondentId") Long respondentId);

    @Select("SELECT * FROM respondent r WHERE r.id = ANY(" +
            "SELECT respondentid FROM respondent_option WHERE optionid=#{optionId})")
    public List<Respondent> getRespondentsForOption(@Param("optionId") Long optionId);

    @Insert("INSERT INTO respondent_option(respondentid,optionid) VALUES(#{respondentId},#{optionId})")
    public void insertRespondentOption(@Param("respondentId") Long respondentId, @Param("optionId") Long optionId);

}
