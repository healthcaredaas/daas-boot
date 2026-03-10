package cn.healthcaredaas.data.cloud.id.dao;

import cn.healthcaredaas.data.cloud.id.model.BaseId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseIdDao extends BaseMapper<BaseId> {

}
