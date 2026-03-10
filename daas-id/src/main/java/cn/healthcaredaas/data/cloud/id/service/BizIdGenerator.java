package cn.healthcaredaas.data.cloud.id.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @author chenpan
 */
@Component
@Slf4j
public class BizIdGenerator {

    @Resource
    private RedissonClient redisson;

    @Autowired
    private IBaseIdService baseIdService;


    public String nextId(String bizCode) {
        String nextId = null;
        RLock lock = redisson.getLock(bizCode);
        lock.lock();
        try {
            nextId = baseIdService.nextId(bizCode);
        } catch (Exception e) {
            log.error("获取ID出错:{}", e.getMessage());
        } finally {
            lock.unlock();
        }
        return nextId;
    }
}