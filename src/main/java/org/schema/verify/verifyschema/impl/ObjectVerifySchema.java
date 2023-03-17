package org.schema.verify.verifyschema.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.schema.json.ArraySchema;
import org.schema.json.ObjectSchema;
import org.schema.json.base.CheckType;
import org.schema.json.base.Schema;
import org.schema.verify.verifyschema.VerifySchema;

import java.util.*;

/**
 * @author ashone
 * <p>
 * desc
 */
public class ObjectVerifySchema extends VerifySchema {

    protected final Queue<KV> queue = new LinkedList<>();
    protected ObjectMapper objectMapper;
    Map<Class<? extends Schema>, VerifySchema> verifySchemaMap = new HashMap<>();
    private String key;


    public ObjectVerifySchema(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        verifySchemaMap.put(NumberVerifySchema.handlerSchema(), new NumberVerifySchema());
        verifySchemaMap.put(BooleanVerifySchema.handlerSchema(), new BooleanVerifySchema());
        verifySchemaMap.put(ObjectVerifySchema.handlerSchema(), this);
        verifySchemaMap.put(StringVerifyShema.handlerSchema(), new StringVerifyShema());
        verifySchemaMap.put(ArrayVerifySchema.handlerSchema(), new ArrayVerifySchema(this));

    }

    public static Class<ObjectSchema> handlerSchema() {
        return ObjectSchema.class;
    }

    public boolean verifySchema(Object data, Schema schema) throws JsonProcessingException {
        KV kv = new KV();
        kv.setData(data);
        kv.setSchema(schema);
        this.queue.offer(kv);
        //如果队列不为空,继续校验
        while (!this.queue.isEmpty()) {
            kv = this.queue.poll();
            data = kv.getData();
            schema = kv.getSchema();
            VerifySchema verifySchema = this.verifySchemaMap.get(schema.getClass());

            verifySchema.verifySchema(data, schema, this.key);

        }
        return true;
    }

    public Map<String, Object> read(String json) throws JsonProcessingException {
        //检测是否为json字符串

        if (!json.startsWith("{") || !json.endsWith("}")) {
            throw new RuntimeException("json Error");
        }

        Map<String, Object> map = objectMapper.readValue(json, Map.class);
        return map;
    }

    @Override
    public void verifySchema(Object data, Schema schema, String key) throws JsonProcessingException {
        ObjectSchema objectSchema = (ObjectSchema) schema;

        Map<String, Schema> schemaMap = objectSchema.getObj();

        //拿到必传的keys
        List<String> requireKeys = objectSchema.getKeys();

        //用于检测哪些必传key没有传
        Map<String, Boolean> hasKey = new HashMap<>();
        //循环初始化
        for (String tempKey : requireKeys) {
            hasKey.put(tempKey, false);
        }

        Object parseData = null;
        for (Map.Entry<String, Schema> entry :
                schemaMap.entrySet()) {

            //将当前层级数据解析出来
            parseData = read(data.toString()).get(entry.getKey());

            //如果是当前对象的必传属性( 如果是必传key, 并且取到的值不为空 )
            if (requireKeys.contains(entry.getKey()) && Objects.nonNull(parseData)) {
                hasKey.put(entry.getKey(), true);
            }

            //防止空指针异常
            if (Objects.nonNull(parseData)) {
                this.key = entry.getKey();
                //如果是Object或Arr类型(要做json转换)
                if (entry.getValue().getClass().equals(ObjectSchema.class) || entry.getValue().getClass().equals(ArraySchema.class)) {
                    //拿到key值(加入队列)(如果是错的就返回)
                    KV kv = new KV();
                    kv.setData(this.objectMapper.writeValueAsString(parseData));
                    kv.setSchema(entry.getValue());
                    this.queue.offer(kv);
                } else {
                    //拿到key值(加入队列)(如果是错的就返回)
                    KV kv = new KV();
                    kv.setData(parseData);
                    kv.setSchema(entry.getValue());
                    this.queue.offer(kv);
                }

            }

        }
        //检测必传key是否都传了
        for (Map.Entry<String, Boolean> h :
                hasKey.entrySet()) {
            if (!h.getValue()) {
                hasSpecifiedException(schema, CheckType.REQUIRE);
                throw new RuntimeException("key " + h.getKey() + " is require");
            }
        }
    }


}
