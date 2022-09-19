package main.yxl.mapper;

import org.apache.ibatis.annotations.*;

/**
 * @author yxl
 * @date: 2022/9/19 下午4:52
 */

@Mapper
public interface PublicContextMapper {
    @Select("select data from PublicContext where taskId = #{taskId}")
    byte[] findDataByTaskId(@Param("taskId") int taskId);

    @Delete("delete from PublicContext where taskId = #{taskId}")
    int deletePublicContextByTaskId(@Param("taskId") int taskId);

    @Insert("insert into PublicContext(taskId,data) values(#{taskId},#{data})")
    int insertPublicContext(@Param("taskId") int taskId, @Param("data") byte[] data);
}
