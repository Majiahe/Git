package cn.qnm.modules.system.controller;

import cn.qnm.base.BaseController;
import cn.qnm.util.LayerData;
import cn.qnm.util.RestResponse;
import cn.qnm.modules.system.entity.Log;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

//功能描述:日志 前端控制器

@RestController
@RequestMapping("api/system/log")
public class LogControllers extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogControllers.class);

    @PostMapping(value = "list")
            public LayerData<Log> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       ServletRequest request){

                Map map = WebUtils.getParametersStartingWith(request,"s_");
                LayerData<Log> layerData = new LayerData<>();
                EntityWrapper<Log> wrapper = new EntityWrapper<>();

                if(!map.isEmpty()){
                    String keys = (String) map.get("type");
                    if(StringUtils.isNotBlank(keys)){
                        wrapper.eq("type",keys);
                    }

            String title = (String) map.get("title");
            if(StringUtils.isNotBlank(title)){
                wrapper.like("title",title);
            }

            String username = (String) map.get("username");
            if(StringUtils.isNotBlank(username)){
                wrapper.eq("username",username);
            }

            String httpMethod = (String)map.get("method");
            if(StringUtils.isNotBlank(httpMethod)){
                wrapper.eq("http_method",httpMethod);
            }
        }

        Page<Log> logPage;
        logPage = logService.selectPage(new Page<>(page,limit),wrapper);
        layerData.setCount(logPage.getTotal());
        layerData.setData(logPage.getRecords());
        return layerData;

    }

    @RequiresPermissions("sys:log:delete")
    @PostMapping("delete")
    public RestResponse delete(@RequestParam("ids[]")List<Long> ids){
        if(ids==null||ids.size()==0){
            RestResponse.failure("请选择要删除的记录");
        }
        logService.deleteBatchIds(ids);
        return RestResponse.success();
    }

}
