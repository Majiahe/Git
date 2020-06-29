package cn.qnm.modules.system.dao;

import cn.qnm.modules.system.entity.Menu;
import cn.qnm.modules.system.entity.vo.ShowMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

//功能描述:选项 Mapper 接口
public interface MenuDaos extends BaseMapper<Menu> {

    List<ShowMenu> selectShowMenuByUser(Map<String,Object> map);

    List<Menu> getMenus(Map map);
}
