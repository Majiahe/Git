package cn.qnm.base;

import cn.qnm.realm.AuthRealm;
import org.apache.shiro.SecurityUtils;

//功能描述：用户信息.

public class MySysUser {

    public static String icon() {
        return ShiroUser().getIcon();
    }

    public static Long id() {
        return ShiroUser().getId();
    }

    public static String loginName() {
        return ShiroUser().getloginName();
    }

    public static String nickName(){
        return ShiroUser().getNickName();
    }

    public static AuthRealm.ShiroUser ShiroUser() {
        return (AuthRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal(); //return ShiroUser user
    }
}
