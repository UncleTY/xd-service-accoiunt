package com.xindong.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xindong.account.dto.UserPasswordDTO;
import com.xindong.account.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @Date: 2022/8/12/012 下午 4:31
 * @Author: taoyu
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update t_user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);
}
