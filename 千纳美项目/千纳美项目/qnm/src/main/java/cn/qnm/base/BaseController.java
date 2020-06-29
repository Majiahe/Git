package cn.qnm.base;

import cn.qnm.realm.AuthRealm;
        import cn.qnm.modules.system.entity.User;
        import cn.qnm.modules.system.service.LogService;
        import cn.qnm.modules.system.service.MenuService;
        import cn.qnm.modules.system.service.RoleService;
        import cn.qnm.modules.system.service.UserService;
        import org.apache.shiro.SecurityUtils;
        import org.springframework.beans.factory.annotation.Autowired;

//功能描述：基础Controller类
public class BaseController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected RoleService roleService;

    @Autowired
    protected LogService logService;

    /**
     * 功能描述：获取当前用户信息
     */
    public User getCurrentUser(){

        AuthRealm.ShiroUser shiroUser = (AuthRealm.ShiroUser)SecurityUtils.getSubject().getPrincipal();
        if(null==shiroUser){
            return null ;
        }

        return userService.selectById(shiroUser.getId());
    }
}










