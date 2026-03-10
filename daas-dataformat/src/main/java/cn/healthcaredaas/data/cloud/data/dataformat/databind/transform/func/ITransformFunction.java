package cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func;

/**
 * *******description*******
 * 数据转换函数
 * *************************
 *
 * @author chenpan
 * @date 2020/1/6 12:18
 */
public interface ITransformFunction {

    boolean isRenderParams();

    boolean withoutData();

    String transform(Object params);
}
