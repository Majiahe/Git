package cn.qnm.modules.system.controller;

import cn.qnm.base.BaseController;
import cn.qnm.modules.system.entity.User;
import cn.qnm.util.RestResponse;
import cn.qnm.modules.system.entity.Menu;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//功能描述:选项 前端控制器

@RestController
@RequestMapping("/api/system/menu")
public class MenuControllers extends BaseController {

    /**
     * 功能描述：获取全部选项列表
     */
    @RequestMapping("treelist")
    public RestResponse treelist(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("parentId",null);
        map.put("isShow",false);
        return RestResponse.success().setData(menuService.selectAllMenus(map));
    }




    /**
     * 功能描述：编辑选项
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping("edit")
    public RestResponse edit(Menu menu){
        if(null==menu.getId()){
            return RestResponse.failure("选项ID不能为空");
        }

        if (StringUtils.isBlank(menu.getName())) {
            return RestResponse.failure("选项名称不能为空");
        }

        Menu oldMenu = menuService.selectById(menu.getId());

        if(!oldMenu.getName().equals(menu.getName())){
            if(menuService.getCountByName(menu.getName())>0){
                return RestResponse.failure("选项名称已存在");
            }

        }

        if (StringUtils.isNotBlank(menu.getPermission())) {
            if(!oldMenu.getPermission().equals(menu.getPermission())) {
                if (menuService.getCountByPermission(menu.getPermission()) > 0) {
                    return RestResponse.failure("权限标识已经存在");
                }
            }
        }


        menuService.saveOrUpdateMenu(menu);
        return RestResponse.success();

    }

    /**
     * 功能描述：删除选项
     */
    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("delete")
     public RestResponse delete(@RequestParam(value = "id",required = true)Long id){
        if(null==id){
            return RestResponse.failure("选项ID不能为空");
        }
        Menu menu = menuService.selectById(id);
        menu.setDelFlag(true);
        menuService.saveOrUpdateMenu(menu);

        return RestResponse.success();
     }

    @Override
    public User getCurrentUser() {
        return super.getCurrentUser();
    }
}
