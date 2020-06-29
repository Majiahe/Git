package cn.qnm.modules.system.service;

import cn.qnm.modules.system.entity.Menu;
import cn.qnm.modules.system.entity.vo.ShowMenu;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

//功能描述:菜单 服务接口

public interface MenuService extends IService<Menu> {

    List<ShowMenu> getShowMenuByUser(Long id);

    List<Menu> selectAllMenus(Map<String,Object> map);

    int getCountByName(String name);

    int getCountByPermission(String permission);

    void saveOrUpdateMenu(Menu menu);
}
