package cn.qnm.modules.system.controller;

import cn.qnm.base.BaseController;
import cn.qnm.base.MySysUser;
import cn.qnm.redis.CacheUtils;
import cn.qnm.util.Constants;
import cn.qnm.util.LayerData;
import cn.qnm.util.RestResponse;
import cn.qnm.util.ToolUtil;
import cn.qnm.modules.system.entity.Role;
import cn.qnm.modules.system.entity.User;
import cn.qnm.modules.system.entity.vo.ShowMenu;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static cn.qnm.util.RestResponse.*;

//功能描述:用户信息 前端控制器

@Controller
@RequestMapping("api/system/user")
public class UserConterollers extends BaseController {

    /**
     * 功能描述：获取用户所拥有的菜单列表
     */
    @RequestMapping("/getUserMenu")
    @ResponseBody
    public RestResponse getUserMenu(){
       Long userId = MySysUser.id();
       List<ShowMenu> list = menuService.getShowMenuByUser(userId);
       return success().setData(list);
    }

    
    /**
     * 功能描述：获取用户列表
     */
    @RequiresPermissions("sys:user:list")
    @RequestMapping("list")
    @ResponseBody
    public LayerData<User> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                ServletRequest request){

        Map map = WebUtils.getParametersStartingWith(request,"s_");
        LayerData<User> userLayerData = new LayerData<>();
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<User>();
        if(!map.isEmpty()){
            String keys = (String)map.get("key");
            if(StringUtils.isNotBlank(keys)){
                userEntityWrapper.like("login_name",keys).or().like("tel", keys).or().like("email", keys);
            }
        }

        Page<User> userPage = userService.selectPage(new Page<>(page,limit),userEntityWrapper);
        userLayerData.setCount(userPage.getTotal());
        userLayerData.setData(userPage.getRecords());
        return userLayerData;
    }

    /**
     * 功能描述：获取所有角色列表
     */
    @GetMapping("getAllRoles")
    @ResponseBody
    public RestResponse getAllRoles(){
        List<Role> roleList = roleService.selectAll();
        return success().setData(roleList);
    }

    /**
     * 功能描述：添加用户信息
     */
    @RequiresPermissions("sys:user:add")
    @PostMapping("add")
    @ResponseBody
    public RestResponse add(@RequestBody User user){
        if (!StringUtils.isBlank(user.getLoginName())) {
            if (user.getRoleLists() == null || user.getRoleLists().size() == 0) {
                return failure("用户角色至少选择一个");
            }

            if (userService.userCount(user.getLoginName()) > 0) {
                return failure("登录名称已经存在");
            }

            if (StringUtils.isNotBlank(user.getEmail())) {
                if (userService.userCount(user.getEmail()) > 0) {
                    return failure("该邮箱已被使用");
                }
            }

            if (StringUtils.isNoneBlank(user.getTel())) {
                if (userService.userCount(user.getTel()) > 0) {
                    return failure("该手机号已被绑定");
                }
            }

            user.setPassword(Constants.DEFAULT_PASSWORD);
            userService.saveUser(user);
            if (user.getId() == null || user.getId() == 0) {
                RestResponse failure = failure("保存用户信息出错");
                return failure;
            }

            //保存用户角色关系
            userService.saveUserRoles(user.getId(), user.getRoleLists());
            return success();
        } else {
            return failure("登录名不能为空");
        }

    }

    /**
     * 功能描述：根据用户id获取角色
     * @param id
     * @return
     */
    @GetMapping("getRolesByUser")
    @ResponseBody
    public RestResponse getRolesByUser(Long id){
        User user = userService.findUserById(id);
        StringBuffer roleIds = new StringBuffer();
        if(user!=null){
            Set<Role> roleSet = user.getRoleLists();
            if (roleSet != null && roleSet.size() > 0) {
                for (Role r : roleSet) {
                    roleIds.append(r.getId().toString()).append(",");
                }
            }
        }

        List<Role> roleList = roleService.selectAll();
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("roleIds",roleIds);
        resultMap.put("roleList",roleList);
        return success().setData(resultMap);

    }

    /**
     * 功能描述：更新用户信息
     */
    @RequiresPermissions("sys:user:edit")
    @PostMapping("edit")
    @ResponseBody
    public RestResponse edit(@RequestBody User user){
        if(user.getId() == 0 || user.getId() == null){
            return failure("用户ID不能为空");
        }

        if(StringUtils.isBlank(user.getLoginName())){
            return failure("登录名不能为空");
        }

        if(user.getRoleLists() == null || user.getRoleLists().size() == 0){
            return  failure("用户角色至少选择一个");
        }

        User oldUser = userService.findUserById(user.getId());
        if(StringUtils.isNotBlank(user.getEmail())){
            if(!user.getEmail().equals(oldUser.getEmail())){
                if(userService.userCount(user.getEmail())>0){
                    return failure("该邮箱已被使用");
                }
            }
        }

        if(StringUtils.isNotBlank(user.getLoginName())){
            if(!user.getLoginName().equals(oldUser.getLoginName())) {
                if (userService.userCount(user.getLoginName()) > 0) {
                    return failure("该登录名已存在");
                }
            }
        }

        if(StringUtils.isNotBlank(user.getTel())){
            if(!user.getTel().equals(oldUser.getTel())) {
                if (userService.userCount(user.getTel()) > 0) {
                    return failure("该手机号已经被绑定");
                }
            }
        }

        userService.updateUser(user);
        //先解除用户跟角色的关系
        userService.dropUserRolesByUserId(user.getId());
        if(user.getId() == null || user.getId() == 0){
            return failure("保存用户信息出错");
        }

        userService.saveUserRoles(user.getId(),user.getRoleLists());
        return success();

    }

    /**
     * 功能描述：删除用户信息（单条记录）
     */
    @RequiresPermissions("sys:user:delete")
    @PostMapping("delete")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "id",required = false)Long id){

        if(id==null|| id==0||id==1){
            return failure("参数错误");
        }

        User user = userService.findUserById(id);

        if(user==null){
            return failure("用户不存在");

        }

        userService.deleteUser(user);
        return success();
    }

    /**
     * 功能描述：批量删除用户数据
     */
    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    public RestResponse deleteSome(@RequestBody List<User> users){
        if(users == null || users.size()==0){
            return failure("请选择需要删除的用户");
        }

        for (User u :users){
            if(u.getId()==1){
                return failure("不能删除超级管理员");
            }else {
                userService.deleteUser(u);
            }
        }
        return success();
    }

    /**
     * 功能描述：修改用户密码
     */
    @RequiresPermissions("sys:user:changePassword")
    @PostMapping("changePassword")
    @ResponseBody
    public RestResponse changePassword(@RequestParam(value = "oldPwd",required = false)String oldPwd,
                                       @RequestParam(value = "newPwd",required = false)String newPwd,
                                       @RequestParam(value = "confirmPwd",required = false)String confirmPwd){

        if(StringUtils.isBlank(oldPwd)){
            return failure("旧密码不能为空");
        }

        if(StringUtils.isBlank(newPwd)){
            return failure("新密码不能为空");
        }

        if(StringUtils.isBlank(confirmPwd)){
            return failure("确认密码不能为空");
        }

        if(!confirmPwd.equals(newPwd)){
            return failure("两次输入密码不一致");
        }

        User user = userService.findUserById(MySysUser.id());

        //输入的旧密码是否正确
        String pw = Objects.requireNonNull(ToolUtil.entryptPassword(oldPwd, user.getSalt())).split(",")[0];
        if(!user.getPassword().equals(pw)){
            return failure("旧密码错误");
        }

        user.setPassword(newPwd);
        ToolUtil.entryptPassword(user);
        userService.updateUser(user);
        return success();

    }

    /**
     * 功能描述：用户个人信息修改
     * @param user
     * @return
     */
    @PostMapping("saveUserInfo")
    @ResponseBody
    public RestResponse saveUserInfo(User user){



        if (StringUtils.isBlank(user.getLoginName())){
            return failure("登录名不能为空");
        }

        User oldUser = userService.findUserById(MySysUser.id());

        if(StringUtils.isNotBlank(user.getEmail())){
            if(!user.getEmail().equals(oldUser.getEmail())){
                if(userService.userCount(user.getEmail())>0){
                    return failure("该邮箱已经使用");
                }
            }
        }

        if(StringUtils.isNotBlank(user.getTel())){
            if(!user.getTel().equals(oldUser.getTel())){
                if(userService.userCount(user.getTel())>0){
                    return failure("改手机号已经使用");
                }
            }
        }

        userService.updateUser(user);
        return success();

    }

    /**
     * 功能描述：清理用户缓存
     * @return
     */
    @GetMapping("clearUserCache")
    @ResponseBody
    public RestResponse clearUserCache(){
        CacheUtils cacheUtils = new CacheUtils();
        cacheUtils.clearUserCache();
        return success("清理缓存成功").setCode(0);
    }



}
