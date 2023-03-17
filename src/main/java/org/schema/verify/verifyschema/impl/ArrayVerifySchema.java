package org.schema.verify.verifyschema.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.schema.json.ArraySchema;
import org.schema.json.StringSchema;
import org.schema.json.base.CheckType;
import org.schema.json.base.Schema;
import org.schema.verify.verifyschema.VerifySchema;

import java.util.List;
import java.util.Objects;

/**
 * @author ashone
 * <p>
 * desc
 */
public class ArrayVerifySchema extends VerifySchema {
    String errorMessage;
    ObjectVerifySchema objectVerifySchema;


    public ArrayVerifySchema(ObjectVerifySchema objectVerifySchema) {
        this.objectVerifySchema = objectVerifySchema;
    }

    public static Class<ArraySchema> handlerSchema() {
        return ArraySchema.class;
    }

    @Override
    public void verifySchema(Object data, Schema schema, String key) throws JsonProcessingException {
        ArraySchema arraySchema = (ArraySchema) schema;

        List arr = objectVerifySchema.objectMapper.readValue(data.toString(), List.class);

        if (arraySchema.getMinLength() != null && arraySchema.getMaxLength() != null && (arraySchema.getMinLength() > arr.size() || arr.size() > arraySchema.getMaxLength())) {
            hasSpecifiedException(arraySchema, CheckType.SIZE_BOUND);

            this.errorMessage = "数组长度不匹配";
            throw new RuntimeException("Key => " + key + " " + this.errorMessage);
        }

        KV kv = new KV();
        for (Object obj :
                arr) {
            //如果数组里面的个别元素值为空就直接进行递归校验
            if (Objects.isNull(obj)) {
                throw new RuntimeException("Key => " + key + " " + "数组有元素为空, 请检查");
            }
            //要区分定义为String类型,并且数组内不是String类型的情况
            if (arraySchema.getSchema().getClass().equals(StringSchema.class) && !obj.getClass().equals(String.class)) {

                this.errorMessage = "数组内有非StringSchema类型";
                throw new RuntimeException("Key => " + key + " " + this.errorMessage);
            }

            //将数据类型和数据纳入队列
            kv = new KV();
            kv.setData(objectVerifySchema.objectMapper.writeValueAsString(obj));
            kv.setSchema(arraySchema.getSchema());
            objectVerifySchema.queue.offer(kv);
        }
    }
}
