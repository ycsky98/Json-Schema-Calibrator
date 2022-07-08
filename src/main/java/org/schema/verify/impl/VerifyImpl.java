package org.schema.verify.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.schema.json.*;
import org.schema.json.base.Schema;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yangcong
 *
 * 校验器实现部分
 */
public class VerifyImpl {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Schema schema;

    private Object data;

    /**
     * 报错信息
     */
    private String errorMessage;

    private boolean flag;

    public VerifyImpl(Object data, Schema schema){
        this.schema = schema;
        this.data = data;
    }

    /**
     * 将text转换成json
     * @param json
     * @throws JsonProcessingException
     */
    public Map<String, Object> read(String json) throws JsonProcessingException {
        //检测是否为json字符串
        if (!json.startsWith("{") || !json.endsWith("}")){
            throw new RuntimeException("json Error");
        }
        Map<String, Object> map = objectMapper.readValue(json, Map.class);
        return map;
    }

    /**
     * 校验Schema
     */
    public boolean verifySchema(Object data, Schema schema) throws JsonProcessingException {
        if (schema instanceof StringSchema){//如果是字符串类型
            if (!(data instanceof String)){
                throw new RuntimeException("不是String类型");
            }
            String str = data.toString();
            StringSchema stringSchema = (StringSchema) schema;
            //正则不为空进行校验
            if (Objects.nonNull(stringSchema.getRegex())){
                //不通过返回false
                if (!str.matches(stringSchema.getRegex())){
                    this.errorMessage = "String 类型 [" + str + "] 不符合匹配规则";
                    throw new RuntimeException(this.errorMessage);
                }
            }
            if ((Objects.nonNull(stringSchema.getMaxLength()) && stringSchema.getMaxLength() < str.length()) ||
                    (Objects.nonNull(stringSchema.getMinLength()) && stringSchema.getMinLength() > str.length())
            ){
                this.errorMessage = "String 类型 [" + str + "] 长度不符合规则";
                throw new RuntimeException(this.errorMessage);
            }
            return true;
        } else if (schema instanceof BoolSchema) {//如果描述是boolean类型
            if (!(data instanceof Boolean))
                throw new RuntimeException("非Boolean类型");
            return true;
        } else if (schema instanceof NumberSchema) {
            //如果不是数字
            if (!data.toString().matches("\\d+") && !data.toString().matches("\\d+\\.\\d+")) {
                this.errorMessage = "数据类型有误";
                throw new RuntimeException(this.errorMessage);
            }
            NumberSchema numberSchema = (NumberSchema) schema;
            //如果有枚举值(进行校验)
            if (Objects.nonNull(numberSchema.getEnumVal())){
                //不包含返回false
                if(!Arrays.asList(numberSchema.getEnumVal()).contains(new BigDecimal(data.toString()).intValue())){
                    this.errorMessage = "不在枚举范围";
                    throw new RuntimeException(this.errorMessage);
                }
            }
            //最大值不为空
            if (Objects.nonNull(numberSchema.getMax())){
                if (new BigDecimal(data.toString()).compareTo(new BigDecimal(numberSchema.getMax().toString()))  == 1){
                    this.errorMessage = "超过最大范围" + numberSchema.getMax();
                    throw new RuntimeException(this.errorMessage);
                }
            }
            //最小值不为空
            if (Objects.nonNull(numberSchema.getMin())){
                if (new BigDecimal(data.toString()).compareTo(new BigDecimal(numberSchema.getMax().toString()))  == -1){
                    this.errorMessage = "小于最小范围" + numberSchema.getMin();
                    throw new RuntimeException(this.errorMessage);
                }
            }

            return true;
        } else if (schema instanceof ObjectSchema) {//如果是复杂的object类型
            ObjectSchema objectSchema = (ObjectSchema) schema;
            Map<String, Schema> schemaMap = objectSchema.getObj();
            for (Map.Entry<String, Schema> entry:
                 schemaMap.entrySet()) {
                 //拿到key值(递归继续查找)(如果是错的就返回)
                 if(!verifySchema(read(data.toString()).get(entry.getKey()), entry.getValue())){
                     throw new RuntimeException(this.errorMessage);
                 }
            }
            //以上校验正常返回true
            return true;
        } else if (schema instanceof ArraySchema) {
            ArraySchema arraySchema = (ArraySchema) schema;
            List arr = objectMapper.readValue(data.toString(), List.class);
            for (Object obj:
                 arr) {
                //如果不通过返回false
                if(!verifySchema(this.objectMapper.writeValueAsString(obj), arraySchema.getSchema())){
                    throw new RuntimeException(this.errorMessage);
                }
            }
            return true;
        }
        return false;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
