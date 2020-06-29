package cn.qnm.modules.system.dao;

import cn.qnm.modules.system.entity.Role;
import cn.qnm.modules.system.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Set;

//功能描述:用户信息 Mapper 接口

public interface UserDaos extends BaseMapper<User> {
    User selectUserByMap(Map<String,Object> map);

    //保存用户和角色关系
    void  saveUserRoles(@Param("userId") Long id,@Param("roleIds") Set<Role> roles);

    void dropUserRolesByUserId(@Param("userId") Long userId);
}
