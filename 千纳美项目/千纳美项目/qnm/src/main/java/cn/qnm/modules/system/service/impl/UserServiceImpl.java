package cn.qnm.modules.system.service.impl;

import cn.qnm.modules.system.service.UserService;
import cn.qnm.util.ToolUtil;
import cn.qnm.modules.system.dao.UserDaos;
import cn.qnm.modules.system.entity.Role;
import cn.qnm.modules.system.entity.User;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service("userService")
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserDaos, User> implements UserService {
    @Override
    public User findUserByLoginName(String name) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("loginName",name);
        User u  = baseMapper.selectUserByMap(map);
        return u;
    }

    @Override
    public User findUserById(Long id) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("id",id);
        return baseMapper.selectUserByMap(map);
    }

    /**
     * 功能描述：统计登录名称
     */
    @Override
    public int userCount(String param) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("login_name",param).or().eq("email",param).or().eq("tel",param);
        int count = baseMapper.selectCount(wrapper);
        return count;
    }

    /**
     * 功能描述：保存用户信息
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public User saveUser(User user) {
        ToolUtil.entryptPassword(user);
        user.setLocked(false);
        baseMapper.insert(user);
        return findUserById(user.getId());
    }

    /**
     * 保存用户和角色关系
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void saveUserRoles(Long id, Set<Role> roleSet) {
        baseMapper.saveUserRoles(id,roleSet);
    }

    /**
     * 功能描述：更新用户信息
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public User updateUser(User user) {
        baseMapper.updateById(user);
        return user;
    }

    /**
     * 功能描述：删除用户和角色关系
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void dropUserRolesByUserId(Long id) {
        baseMapper.dropUserRolesByUserId(id);
    }

    /**
     * 删除用户信息（单条）
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void deleteUser(User user) {
        user.setDelFlag(true);
        user.updateById();
    }
}
