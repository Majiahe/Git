package cn.qnm.modules.system.dao;

import cn.qnm.modules.system.entity.Menu;
import cn.qnm.modules.system.entity.Role;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

//功能描述:用户角色 Mapper 接口
public interface RoleDaos extends BaseMapper<Role> {

    //保存角色和选项关系
    void saveRoleMenus(@Param("roleId") Long id,@Param("menus") Set<Menu> menus);

    //删除角色和选项关系
    void dropRoleMenus(@Param("roleId") Long roleId);

    //根据角色Id获取角色信息
    Role selectRoleById(@Param("id") Long id);

    //删除角色和用户的关系
    void dropRoleUsers(@Param("roleId") Long roleId);
}
