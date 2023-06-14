package com.xindong.accounting.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xindong.accounting.dataobject.RoleMenuDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Date: 2022/8/26/026 下午 12:48
 * @Author: taoyu
 * @Description:
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenuDO> {

    @Delete("delete from t_role_menu where role_id = #{roleId} ")
    int deleteByRoleId(@Param("roleId") Integer roleId);

    @Select("select menu_id from t_role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);
}
