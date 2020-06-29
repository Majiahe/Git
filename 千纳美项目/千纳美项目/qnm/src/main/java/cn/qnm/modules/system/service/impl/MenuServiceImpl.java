package cn.qnm.modules.system.service.impl;

import cn.qnm.modules.system.dao.MenuDaos;
import cn.qnm.modules.system.entity.Menu;
import cn.qnm.modules.system.entity.vo.ShowMenu;
import cn.qnm.modules.system.service.MenuService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuDaos, Menu> implements MenuService {

    @Override
    public List<ShowMenu> getShowMenuByUser(Long id) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("userId",id);
        map.put("parentId",null);
        return baseMapper.selectShowMenuByUser(map);
    }

    @Override
    public List<Menu> selectAllMenus(Map<String, Object> map) {
        return baseMapper.getMenus(map);
    }

    @Override
    public int getCountByName(String name) {
        EntityWrapper<Menu> wrapper = new EntityWrapper();
        wrapper.eq("del_flag",false);
        wrapper.eq("name",name);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public int getCountByPermission(String permission) {
        EntityWrapper<Menu> wrapper = new EntityWrapper();
        wrapper.eq("del_flag",false);
        wrapper.eq("permission",permission);
        return baseMapper.selectCount(wrapper);
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(Menu menu) {
        insertOrUpdate(menu);
    }
}
