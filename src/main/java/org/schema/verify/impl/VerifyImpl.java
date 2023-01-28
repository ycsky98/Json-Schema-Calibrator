package org.schema.verify.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.schema.json.*;
import org.schema.json.base.Schema;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author yangcong
 *
 * 校验器实现部分
 */
public class VerifyImpl {

    private ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

    private Schema schema;

    private Object data;

    /**
     * 报错信息
     */
    private String errorMessage;

    /**
     * 当前层级的key
     */
    private String key;

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
            StringSchema stringSchema = (StringSchema) schema;

            if (!(data instanceof String)){
                throw new RuntimeException("Key => " + this.key + " 不是StringSchema类型");
            }
            String str = data.toString();
            //正则不为空进行校验
            if (Objects.nonNull(stringSchema.getRegex())){
                //不通过返回false
                if (!str.matches(stringSchema.getRegex())){
                    this.errorMessage = "String 类型 [" + str + "] 不符合匹配规则";
                    throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
                }
            }
            if ((Objects.nonNull(stringSchema.getMaxLength()) && stringSchema.getMaxLength() < str.length()) ||
                    (Objects.nonNull(stringSchema.getMinLength()) && stringSchema.getMinLength() > str.length())
            ){
                this.errorMessage = "String 类型 [" + str + "] 长度不符合规则";
                throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
            }

            return true;
        } else if (schema instanceof BoolSchema) {//如果描述是boolean类型
            BoolSchema boolSchema = (BoolSchema) schema;

            if (!(data instanceof Boolean))
                throw new RuntimeException("Key => " + this.key + " " + "非Boolean类型");
            return true;
        } else if (schema instanceof NumberSchema) {
            NumberSchema numberSchema = (NumberSchema) schema;

            //如果不是数字
            if (!data.toString().matches("\\d+") && !data.toString().matches("\\d+\\.\\d+") && //正数
                    !data.toString().matches("-\\d+") && !data.toString().matches("-\\d+\\.\\d+") //负数
            ) {
                this.errorMessage = "数据类型有误,非NumberSchema类型";
                throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
            }

            //如果有枚举值(进行校验)
            if (Objects.nonNull(numberSchema.getEnumVal())){
                //不包含抛错
                if(Arrays.asList(numberSchema.getEnumVal()).stream().filter(val -> {
                    return val.compareTo(new BigDecimal(data.toString())) == 0;
                }).toList().size()  == 0){
                    this.errorMessage = "不在枚举范围";
                    throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
                }
            }
            //最大值不为空
            if (Objects.nonNull(numberSchema.getMax())){
                if (new BigDecimal(data.toString()).compareTo(new BigDecimal(numberSchema.getMax().toString()))  == 1){
                    this.errorMessage = "超过最大范围" + numberSchema.getMax();
                    throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
                }
            }
            //最小值不为空
            if (Objects.nonNull(numberSchema.getMin())){
                if (new BigDecimal(data.toString()).compareTo(new BigDecimal(numberSchema.getMin().toString()))  == -1){
                    this.errorMessage = "小于最小范围" + numberSchema.getMin();
                    throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
                }
            }

            return true;
        } else if (schema instanceof ObjectSchema) {//如果是复杂的object类型
            ObjectSchema objectSchema = (ObjectSchema) schema;

            Map<String, Schema> schemaMap = objectSchema.getObj();

            //拿到必传的keys
            List<String> requireKeys = objectSchema.getKeys();

            //用于检测哪些必传key没有传
            Map<String, Boolean> hasKey = new HashMap<>();
            //循环初始化
            for (String key : requireKeys){
                hasKey.put(key, false);
            }

            Object parseData = null;
            for (Map.Entry<String, Schema> entry:
                 schemaMap.entrySet()) {

                //将当前层级数据解析出来
                parseData = read(data.toString()).get(entry.getKey());

                //如果是当前对象的必传属性( 如果是必传key, 并且取到的值不为空 )
                if (requireKeys.contains(entry.getKey()) && Objects.nonNull(parseData)){
                    hasKey.put(entry.getKey(), true);
                }

                //防止空指针异常
                if (Objects.nonNull(parseData)){
                    this.key = entry.getKey();
                    //如果是Object或Arr类型(要做json转换)
                    if (entry.getValue().getClass().equals(ObjectSchema.class) || entry.getValue().getClass().equals(ArraySchema.class)){
                        //拿到key值(递归继续查找)(如果是错的就返回)
                        if(!verifySchema(this.objectMapper.writeValueAsString(parseData), entry.getValue())){
                            throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
                        }
                    }else {
                        //拿到key值(递归继续查找)(如果是错的就返回)
                        if(!verifySchema(parseData, entry.getValue())){
                            throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
                        }
                    }

                }

            }
            //检测必传key是否都传了
            for (Map.Entry<String, Boolean> h:
                 hasKey.entrySet()) {
                if (!h.getValue()){
                    throw new RuntimeException("key " + h.getKey() + " is require");
                }
            }
            //以上校验正常返回true
            return true;
        } else if (schema instanceof ArraySchema) {
            ArraySchema arraySchema = (ArraySchema) schema;

            List arr = objectMapper.readValue(data.toString(), List.class);

            if (arraySchema.getMinLength() != null && arraySchema.getMaxLength() != null && (arraySchema.getMinLength() > arr.size() || arr.size() > arraySchema.getMaxLength())) {
                this.errorMessage = "数组长度不匹配";
                throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
            }

            for (Object obj:
                 arr) {
                //如果数组里面的个别元素值为空就直接进行递归校验
                if (Objects.isNull(obj)){
                    throw new RuntimeException("Key => " + this.key + " " + "数组有元素为空, 请检查");
                }
                //要区分定义为String类型,并且数组内不是String类型的情况
                if (arraySchema.getSchema().getClass().equals(StringSchema.class) && !obj.getClass().equals(StringSchema.class)){
                    this.errorMessage = "数组内有非StringSchema类型";
                    throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
                }

                //如果不通过返回false
                if(!verifySchema(this.objectMapper.writeValueAsString(obj), arraySchema.getSchema())){
                    throw new RuntimeException("Key => " + this.key + " " + this.errorMessage);
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
