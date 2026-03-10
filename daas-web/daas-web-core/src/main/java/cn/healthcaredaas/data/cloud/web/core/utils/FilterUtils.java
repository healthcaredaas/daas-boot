package cn.healthcaredaas.data.cloud.web.core.utils;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

import jakarta.servlet.Filter;

/**
 * <pre>过滤器工具</pre>
 *
 * @ClassName： FilterUtils.java
 * @Author： chenpan
 * @Date：2024/12/16 15:39
 * @Modify：
 */
public class FilterUtils {

    public static FilterRegistrationBean createFilterRegistrationBean(Filter filter, int order, String filterName) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setOrder(order);
        registration.setName(filterName);
        return registration;
    }
}
