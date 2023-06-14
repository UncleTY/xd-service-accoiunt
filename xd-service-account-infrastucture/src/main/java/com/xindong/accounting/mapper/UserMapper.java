package com.xindong.accounting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xindong.accounting.dataobject.UserDO;
import com.xindong.accounting.model.valueobject.UserPassword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @Date: 2022/8/12/012 下午 4:31
 * @Author: taoyu
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

	@Update("update t_user set password = #{newPassword} where username = #{username} and password = #{password}")
	int updatePassword(UserPassword userPassword);
}
