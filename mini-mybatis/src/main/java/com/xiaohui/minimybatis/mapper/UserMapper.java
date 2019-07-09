package com.xiaohui.minimybatis.mapper;

import com.xiaohui.minimybatis.annotation.Insert;
import com.xiaohui.minimybatis.annotation.Select;
import com.xiaohui.minimybatis.annotation.Table;
import com.xiaohui.minimybatis.entity.User;

import java.util.List;

/**
 * @Author: xiaohui
 * @Description:
 */
@Table("user")
public interface UserMapper {

    @Select("select * from %s where id = %s")
    User selectByPrimiryKey(Integer userId);

    @Select("select * from %s")
    List<User> queryAllUser();

    @Insert("insert into %s values (%s)")
    int insert(User user);

}
