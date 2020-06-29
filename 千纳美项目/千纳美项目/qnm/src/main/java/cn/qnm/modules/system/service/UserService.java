package cn.qnm.modules.system.service;

        import cn.qnm.modules.system.entity.Role;
        import cn.qnm.modules.system.entity.User;
        import com.baomidou.mybatisplus.service.IService;

        import java.util.Set;

//功能描述:用户信息 服务类

public interface UserService extends IService<User> {

    User findUserByLoginName(String name);

    User findUserById(Long id);

    int userCount(String param);

    User saveUser(User user);

    //保存用户和角色的关系
    void saveUserRoles(Long id, Set<Role> roleSet);

    //更新用户信息
    User updateUser(User user);

    //删除用户和角色关系
    void  dropUserRolesByUserId(Long id);

    //删除单条记录
    void deleteUser(User user);
}


