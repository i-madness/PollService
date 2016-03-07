package net.imadness.mappers;

import net.imadness.entities.Poll;
import net.imadness.entities.Respondent;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * DAO-интерфейс для доступа к сущности Respondent в БД
 */
public interface RespondentMapper {

    @Select("SELECT * FROM respondent")
    @Results(value = {
            @Result(property = "id",      column = "poll_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "name",    column = "name"),
            @Result(property = "email",   column = "email"),
            @Result(property = "ipAddress", column = "ip_address"),
            @Result(property = "polls",     javaType = Poll.class,
                    many  = @Many(select = "net.imadness.mappers.extended.PollRespondentMapper.getPollsByRespondent")),
            //@Result(property = "options", column = "options", javaType = List.class,
            //        many = @Many(select = "net.imadness.mappers.OptionMapper.getOptionsOfRespondent"))
    })
    public List<Respondent> getAllRespondents();

    @Select("SELECT * FROM respondent WHERE id=#{id}")
    public Respondent getRespondentById(@Param("id") Long id);

    @Update("UPDATE respondent SET name=#{respondent.name}, email=#{respondent.email}, ipaddress=#{respondent.ipAddress}" +
            "WHERE id=#{respondent.id}")
    public void updateRespondent(@Param("respondent") Respondent respondent);

    @Insert("INSERT INTO respondent (name,email,ipaddress) VALUES(#{respondent.name},#{respondent.email},#{respondent.ipAddress})")
    @Options(useGeneratedKeys=true, keyProperty = "respondent.id")
    public void addRespondent(@Param("respondent") Respondent respondent);

    @Delete("DELETE FROM respondent WHERE id=#{id}")
    public void deleteRespondent(@Param("id") Long id);

}
