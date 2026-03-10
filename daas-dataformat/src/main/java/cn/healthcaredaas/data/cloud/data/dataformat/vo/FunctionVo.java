package cn.healthcaredaas.data.cloud.data.dataformat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 * @ClassName： FunctionVo.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/8 10:57
 * @Modify：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionVo {

    private String value;
    private String label;
    private String desc;
}
