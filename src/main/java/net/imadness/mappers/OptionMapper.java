package net.imadness.mappers;

import net.imadness.entities.Option;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OptionMapper {

    @Select("SELECT * FROM option")
    public List<Option> getAllOptions();

    @Select("SELECT * FROM option WHERE id=#{id}")
    public Option getOptionById(@Param("id") Long id);

    @Update("UPDATE option SET content=#{option.content}, isright=#{option.isRight} WHERE id=#{option.id}")
    public void updateOption(@Param("option") Option option);

    @Insert("INSERT INTO option (content,isright) VALUES(#{option.content}, #{option.isRight})")
    @Options(useGeneratedKeys=true, keyProperty = "option.id")
    public void addOption(@Param("option") Option option);

    @Delete("DELETE FROM option WHERE id=#{id}")
    public void deleteOption(@Param("id") Long id);

    @Select("SELECT * FROM option WHERE questionid={questionid} ")
    public List<Option> getOptionsForQuestion(@Param("questionid") Long questionId);

}
