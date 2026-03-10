package cn.healthcaredaas.data.cloud.id.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.core.exception.BizException;
import cn.healthcaredaas.data.cloud.id.dao.BaseIdDao;
import cn.healthcaredaas.data.cloud.id.model.BaseId;
import cn.healthcaredaas.data.cloud.id.service.IBaseIdService;
import cn.healthcaredaas.data.cloud.data.mybatisplus.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;

@Service
public class BaseIdServiceImpl extends BaseServiceImpl<BaseIdDao, BaseId> implements IBaseIdService {

    @Resource
    private RedissonClient redisson;

    @Autowired
    private BaseIdDao baseIdDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String nextId(String bizCode) {
        String nextId = null;
        LambdaQueryWrapper<BaseId> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseId::getBizCode, bizCode);
        BaseId baseId = baseIdDao.selectOne(queryWrapper);
        if (baseId == null) {
            throw new BizException(String.format("[%s]对应的业务序号发生器不存在！", bizCode));
        }
        if (StrUtil.isNotBlank(baseId.getPrefix())) {
            nextId = baseId.getPrefix() + baseId.getNextValue();
        } else {
            nextId = String.valueOf(baseId.getNextValue());
        }
        baseId.setNextValue(baseId.getNextValue() + baseId.getStep());
        baseIdDao.updateById(baseId);
        return nextId;

    }
}
