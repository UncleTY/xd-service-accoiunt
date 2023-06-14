package com.xindong.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xindong.account.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Date: 2022/8/25/025 下午 4:48
 * @Author: taoyu
 * @Description:
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from t_role where flag = #{flag}")
    Integer selectByFlag(String role);
}
