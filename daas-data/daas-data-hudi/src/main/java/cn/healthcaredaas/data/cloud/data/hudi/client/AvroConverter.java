package cn.healthcaredaas.data.cloud.data.hudi.client;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hudi.common.model.HoodieAvroPayload;
import org.apache.hudi.common.model.HoodieAvroRecord;
import org.apache.hudi.common.model.HoodieKey;
import org.apache.hudi.common.model.HoodieRecord;
import org.apache.hudi.common.util.Option;

import java.util.List;

/**

 * @ClassName： AvroConverter.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/29 17:35
 * @Modify：
 */
public class AvroConverter {

    public static HoodieRecord<HoodieAvroPayload> convert(String primaryKey,
                                                          List<String> partitions,
                                                          Schema avroSchema,
                                                          JSONObject object) {
        GenericRecord record = new GenericData.Record(avroSchema);
        object.forEach(record::put);
        String id = object.getString(primaryKey);
        String partitionPath = "";
        if (CollectionUtil.isNotEmpty(partitions)) {
            partitionPath = StrUtil.join("/", partitions);
        }
        HoodieKey key = new HoodieKey(id, partitionPath);
        return new HoodieAvroRecord<>(key, new HoodieAvroPayload(Option.of(record)));
    }
}
