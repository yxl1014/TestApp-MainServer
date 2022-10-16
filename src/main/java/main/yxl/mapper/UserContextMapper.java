package main.yxl.mapper;

import org.apache.ibatis.annotations.*;

/**
 * @author yxl
 * @date: 2022/9/19 下午4:53
 */

@Mapper
public interface UserContextMapper {

    @Select("select data from UserContext where userId = #{userId}")
    byte[] findDataByUserId(@Param("userId") int userId);

    @Delete("delete from UserContext where userId = #{userId}")
    int deleteUserContextByUserId(@Param("userId") int userId);

    @Insert("insert into UserContext(userId,data) values(#{userId},#{data})")
    int insertUserContext(@Param("userId") int userId, @Param("data") byte[] data);

    @Update("update UserContext set data= #{data} where userId = #{userId}")
    int updateUserContext(@Param("userId") int taskId, @Param("data") byte[] data);
}
