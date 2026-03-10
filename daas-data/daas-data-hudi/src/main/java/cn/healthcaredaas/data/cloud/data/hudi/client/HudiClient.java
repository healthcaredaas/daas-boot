package cn.healthcaredaas.data.cloud.data.hudi.client;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import org.apache.avro.Schema;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hudi.client.HoodieJavaWriteClient;
import org.apache.hudi.client.common.HoodieJavaEngineContext;
import org.apache.hudi.common.fs.FSUtils;
import org.apache.hudi.common.model.HoodieAvroPayload;
import org.apache.hudi.common.model.HoodieKey;
import org.apache.hudi.common.model.HoodieRecord;
import org.apache.hudi.common.model.HoodieTableType;
import org.apache.hudi.common.table.HoodieTableMetaClient;
import org.apache.hudi.config.HoodieIndexConfig;
import org.apache.hudi.config.HoodieWriteConfig;
import org.apache.hudi.index.HoodieIndex;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**

 * @ClassName： HudiClient.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/29 16:50
 * @Modify：
 */
public class HudiClient {

    private HoodieJavaWriteClient<HoodieAvroPayload> client;

    private String table;
    private Schema avroSchema;
    private String primaryKey;
    private List<String> partitions;

    public void init(String hdfsPath, String table, String schema, String primaryKey, List<String> partitions,
                     HoodieTableType tableType) {
        String tablePath = StrUtil.join("/", hdfsPath, table);
        Configuration hadoopConf = new Configuration();
        Path path = new Path(tablePath);
        FileSystem fs = FSUtils.getFs(tablePath, hadoopConf);

        try {
            if (!fs.exists(path)) {
                // 初始化Hoodie Table 创建Hoodie表的tablePath，写入初始化元数据信息
                HoodieTableMetaClient.withPropertyBuilder()
                        .setTableType(tableType.name())
                        .setTableName(table)
                        .setPayloadClassName(HoodieAvroPayload.class.getName())
                        .initTable(hadoopConf, tablePath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.table = table;
        this.avroSchema = new Schema.Parser().parse(schema);
        this.primaryKey = primaryKey;
        this.partitions = partitions;

        // 创建write client conf
        HoodieWriteConfig huDiWriteConf = HoodieWriteConfig.newBuilder()
                .withSchema(schema)
                // 数据插入更新并行度
                .withParallelism(2, 2)
                // 数据删除并行度
                .withDeleteParallelism(2)
                .withPath(tablePath)
                .forTable(table)
                //表索引类型，BLOOM
                .withIndexConfig(HoodieIndexConfig.newBuilder()
                        .withIndexType(HoodieIndex.IndexType.BLOOM)
                        .build())
                .build();

        this.client = new HoodieJavaWriteClient<>(new HoodieJavaEngineContext(hadoopConf), huDiWriteConf);
    }

    public void close() {
        this.client.close();
    }

    public void insert(JSONObject object) {
        insert(Collections.singletonList(object));
    }

    public void insert(List<JSONObject> objects) {
        String instantTime = client.startCommit();
        List<HoodieRecord<HoodieAvroPayload>> hoodieRecords = objects.stream()
                .map(object -> AvroConverter.convert(primaryKey, partitions, avroSchema, object))
                .collect(Collectors.toList());
        client.insert(hoodieRecords, instantTime);
    }

    public void upsert(JSONObject object) {
        upsert(Collections.singletonList(object));
    }

    public void upsert(List<JSONObject> objects) {
        String instantTime = client.startCommit();
        List<HoodieRecord<HoodieAvroPayload>> hoodieRecords = objects.stream()
                .map(object -> AvroConverter.convert(primaryKey, partitions, avroSchema, object))
                .collect(Collectors.toList());
        client.upsert(hoodieRecords, instantTime);
    }


    public void delete(String primaryKey) {
        delete(Collections.singletonList(primaryKey));
    }

    public void delete(List<String> primaryKeys) {
        String instantTime = client.startCommit();
        List<HoodieKey> deleteKeys = primaryKeys.stream().map(key -> new HoodieKey(key, table)).collect(Collectors.toList());
        client.delete(deleteKeys, instantTime);
    }
}
