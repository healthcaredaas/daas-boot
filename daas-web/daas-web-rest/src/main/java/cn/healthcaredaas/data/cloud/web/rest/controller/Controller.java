package cn.healthcaredaas.data.cloud.web.rest.controller;

import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import cn.healthcaredaas.data.cloud.web.core.utils.HttpUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>Controller基础定义</pre>
 *
 * @ClassName： Controller.java
 * @Author： chenpan
 * @Date：2024/11/30 14:56
 * @Modify：
 */
public interface Controller {

    /**
     * 数据实体转换为统一响应实体
     *
     * @param domain 数据实体
     * @param <E>    {@link Object} 子类型
     * @return {@link RestResult} BaseEntity
     */
    default <E extends Object> RestResult<E> result(E domain) {
        RestResult<E> result;
        if (ObjectUtils.isNotEmpty(domain)) {
            return makeResult(RestResult.content(domain));
        } else {
            return emptyResult();
        }
    }

    /**
     * 数据列表转换为统一响应实体
     *
     * @param domains 数据实体 List
     * @param <E>     {@link Object} 子类型
     * @return {@link RestResult} List
     */
    default <E extends Object> RestResult<List<E>> result(List<E> domains) {
        if (CollectionUtils.isNotEmpty(domains)) {
            return makeResult(RestResult.success("查询数据成功！", domains));
        } else {
            return emptyResult();
        }
    }

    /**
     * 数据分页对象转换为统一响应实体
     *
     * @param pages 分页查询结果 {@link IPage}
     * @param <E>   {@link BaseEntity} 子类型
     * @return {@link RestResult} Map
     */
    default <E extends BaseEntity> RestResult<Map<String, Object>> result(IPage<E> pages) {
        if (ObjectUtils.isNotEmpty(pages)) {
            if (CollectionUtils.isNotEmpty(pages.getRecords())) {
                return makeResult(RestResult.success("查询数据成功！", getPageInfoMap(pages)));
            } else {
                return emptyResult();
            }
        } else {
            return makeResult(RestResult.failure("查询数据失败！"));
        }
    }

    /**
     * 数据 Map 转换为统一响应实体
     *
     * @param map 数据 Map
     * @return {@link RestResult} Map
     */
    default RestResult<Map<String, Object>> result(Map<String, Object> map) {
        if (MapUtils.isNotEmpty(map)) {
            return makeResult(RestResult.success("查询数据成功！", map));
        } else {
            return emptyResult();
        }
    }

    /**
     * 数据操作结果转换为统一响应实体
     *
     * @param parameter 数据ID
     * @param <ID>      ID 数据类型
     * @return {@link RestResult} String
     */
    default <ID extends Serializable> RestResult<ID> result(ID parameter) {
        if (ObjectUtils.isNotEmpty(parameter)) {
            return makeResult(RestResult.content(parameter));
        } else {
            return makeResult(RestResult.failure());
        }
    }

    /**
     * 数据查询结果转换为统一响应实体
     *
     * @param object 数据ID
     * @return {@link RestResult} String
     */
    default <D extends Object> RestResult<D> queryResult(D object) {
        if (ObjectUtils.isNotEmpty(object)) {
            return makeResult(RestResult.success("查询数据成功！", object));
        } else {
            return emptyResult();
        }
    }

    /**
     * 数据操作结果转换为统一响应实体
     *
     * @param status 操作状态
     * @return {@link RestResult} String
     */
    default RestResult<String> result(boolean status) {
        if (status) {
            return makeResult(RestResult.success());
        } else {
            return makeResult(RestResult.failure());
        }
    }

    /**
     * Page 对象转换为 Map
     *
     * @param pageInfo 分页查询结果 {@link IPage}
     * @param <E>      {@link BaseEntity} 子类型
     * @return Map
     */
    default <E extends BaseEntity> Map<String, Object> getPageInfoMap(IPage<E> pageInfo) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("data", pageInfo.getRecords());
        result.put("pages", pageInfo.getPages());
        result.put("size", pageInfo.getSize());
        result.put("total", pageInfo.getTotal());
        result.put("current", pageInfo.getCurrent());
        return result;
    }

    default RestResult makeResult(RestResult result) {
        HttpServletRequest request = HttpUtils.getRequest();
        if (ObjectUtils.isNotEmpty(request)) {
            result.path(request.getRequestURI());
        }
        return result;
    }

    default RestResult emptyResult() {
        return makeResult(RestResult.empty("未查询到数据！"));
    }
}
