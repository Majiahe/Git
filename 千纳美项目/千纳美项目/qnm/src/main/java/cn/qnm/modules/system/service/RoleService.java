package cn.qnm.modules.system.service;

import cn.qnm.modules.system.entity.Role;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

//功能描述:用户角色 服务接口

public interface RoleService extends IService<Role> {

    //根据角色名称统计记录
    int getRoleNameCount(String name);

    //保存角色信息
    Role saveRole(Role role);

    //更新角色信息
    void updateRole(Role role);

    //根据角色Id获取角色信息
    Role getRoleById(Long id);

    //删除角色信息
    void deleteRole(Role role);

    //获取所有角色列表
    List<Role> selectAll();


}
