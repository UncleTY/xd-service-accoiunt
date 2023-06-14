package com.xindong.accounting.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.log.Log;
import com.xindong.accounting.constants.Constants;
import com.xindong.accounting.dataobject.MenuDO;
import com.xindong.accounting.dataobject.UserDO;
import com.xindong.accounting.dataobject.response.UserDTO;
import com.xindong.accounting.dataobject.response.UserPasswordDTO;
import com.xindong.accounting.dataobject.response.menu.MenuDTO;
import com.xindong.accounting.enums.RoleEnum;
import com.xindong.accounting.exception.ServiceException;
import com.xindong.accounting.mapper.RoleMapper;
import com.xindong.accounting.mapper.RoleMenuMapper;
import com.xindong.accounting.mapper.UserMapper;
import com.xindong.accounting.model.entity.Menu;
import com.xindong.accounting.model.entity.User;
import com.xindong.accounting.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2022/8/12/012 下午 4:36
 * @Author: taoyu
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Log LOG = Log.get();

    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Resource
    MenuService menuService;

    @Resource
    UserMapper userMapper;

    @Override
    public UserDTO login(UserDTO userDTO) {
        // 用户密码 md5加密
        userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
        User one = getUserInfo(userDTO);
        if (one != null){
            BeanUtil.copyProperties(one,userDTO,true);
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            String role = one.getRole(); // ROLE_ADMIN
            // 设置用户的菜单列表
            List<MenuDTO> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }

    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        // 用户密码 md5加密
        userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
        User one = getUserInfo(userDTO);
        if (one == null){
            one = new User();
            BeanUtil.copyProperties(userDTO,one,true);
            // 默认一个普通用户的角色
            one.setRole(RoleEnum.ROLE_USER.toString());
            if (one.getNickname() == null) {
                one.setNickname(one.getUsername());
            }
            save(one);//把 copy 完之后的用户数据存到数据库中
        }else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return null;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }

    //封装方法
    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }


    /**
     * 获取当前角色的菜单列表
     * @param roleFlag
     * @return
     */
    private List<MenuDTO> getRoleMenus(String roleFlag) {
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        // 当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        // 查出系统所有的菜单(树形)
        List<Menu> menus = menuService.findMenus("");
        // new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

}
