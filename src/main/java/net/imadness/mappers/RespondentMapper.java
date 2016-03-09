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
    public List<Respondent> getAllRespondents();

    @Select("SELECT * FROM respondent WHERE id=#{id}")
    public Respondent getRespondentById(@Param("id") Long id);

    @Select("SELECT * FROM respondent WHERE name=#{name} AND email=#{email}")
    public Respondent getRespondentByPersonalData(@Param("name") String name, @Param("email") String email);

    @Update("UPDATE respondent SET name=#{respondent.name}, email=#{respondent.email} WHERE id=#{respondent.id}")
    public void updateRespondent(@Param("respondent") Respondent respondent);

    @Insert("INSERT INTO respondent (name,email) VALUES(#{respondent.name},#{respondent.email})")
    @Options(useGeneratedKeys=true, keyProperty = "respondent.id")
    public void addRespondent(@Param("respondent") Respondent respondent);

    @Delete("DELETE FROM respondent WHERE id=#{id}")
    public void deleteRespondent(@Param("id") Long id);

}