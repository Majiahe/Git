package cn.qnm.modules.system.service.impl;

import cn.qnm.modules.system.dao.LogDaos;
import cn.qnm.modules.system.entity.Log;
import cn.qnm.modules.system.service.LogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//功能描述:系统日志 服务实现类
@Service
@Transactional(rollbackFor = Exception.class)
public class LogServiceImpl extends ServiceImpl<LogDaos, Log> implements LogService {

}
