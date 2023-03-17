package org.schema.verify.verifyschema.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.schema.json.NumberSchema;
import org.schema.json.base.CheckType;
import org.schema.json.base.Schema;
import org.schema.verify.verifyschema.VerifySchema;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ashone
 * <p>
 * desc
 */
public class NumberVerifySchema extends VerifySchema {
    String errorMessage;

    public static Class<NumberSchema> handlerSchema() {
        return NumberSchema.class;
    }

    @Override
    public void verifySchema(Object data, Schema schema, String key) throws JsonProcessingException {
        NumberSchema numberSchema = (NumberSchema) schema;

        //如果不是数字
        if (!data.toString().matches("\\d+") && !data.toString().matches("\\d+\\.\\d+") && //正数
                !data.toString().matches("-\\d+") && !data.toString().matches("-\\d+\\.\\d+") //负数
        ) {
            hasSpecifiedException(numberSchema, CheckType.NUMBER);

            this.errorMessage = "数据类型有误,非NumberSchema类型";
            throw new RuntimeException("Key => " + key + " " + this.errorMessage);
        }

        //如果有枚举值(进行校验)
        if (Objects.nonNull(numberSchema.getEnumVal())) {
            //不包含抛错
            if (Arrays.asList(numberSchema.getEnumVal()).stream().filter(val -> {
                return val.compareTo(new BigDecimal(data.toString())) == 0;
            }).collect(Collectors.toList()).size() == 0) {
                hasSpecifiedException(numberSchema, CheckType.ENUM_VAL);

                this.errorMessage = "不在枚举范围";
                throw new RuntimeException("Key => " + key + " " + this.errorMessage);
            }
        }
        //最大值不为空
        if (Objects.nonNull(numberSchema.getMax())) {
            if (new BigDecimal(data.toString()).compareTo(new BigDecimal(numberSchema.getMax().toString())) == 1) {
                hasSpecifiedException(numberSchema, CheckType.MAX);

                this.errorMessage = "超过最大范围" + numberSchema.getMax();
                throw new RuntimeException("Key => " + key + " " + this.errorMessage);
            }
        }
        //最小值不为空
        if (Objects.nonNull(numberSchema.getMin())) {
            if (new BigDecimal(data.toString()).compareTo(new BigDecimal(numberSchema.getMin().toString())) == -1) {
                hasSpecifiedException(numberSchema, CheckType.MIN);

                this.errorMessage = "小于最小范围" + numberSchema.getMin();
                throw new RuntimeException("Key => " + key + " " + this.errorMessage);
            }
        }

    }
}
