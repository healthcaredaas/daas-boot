package cn.healthcaredaas.data.cloud.core.yaml;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;

/**

 * @ClassName： YamlPropertySourceFactory.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/7/28 16:06
 * @Modify：
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
        List<PropertySource<?>> sources = sourceLoader.load(resource.getResource().getFilename(), resource.getResource());
        return sources.get(0);
    }
}
