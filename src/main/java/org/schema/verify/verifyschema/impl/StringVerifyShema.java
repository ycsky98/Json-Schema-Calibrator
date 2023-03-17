package org.schema.verify.verifyschema.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.schema.json.StringSchema;
import org.schema.json.base.CheckType;
import org.schema.json.base.Schema;
import org.schema.verify.verifyschema.VerifySchema;

import java.util.Objects;

/**
 * @author ashone
 * <p>
 * desc
 */
public class StringVerifyShema extends VerifySchema {
    String errorMessage;

    public static Class<StringSchema> handlerSchema() {
        return StringSchema.class;
    }

    @Override
    public void verifySchema(Object data, Schema schema, String key) throws JsonProcessingException {
        StringSchema stringSchema = (StringSchema) schema;

        if (!(data instanceof String)) {
            hasSpecifiedException(stringSchema, CheckType.STRING);
            throw new RuntimeException("Key => " + key + " 不是StringSchema类型");
        }
        String str = data.toString();
        //正则不为空进行校验
        if (Objects.nonNull(stringSchema.getRegex())) {
            //不通过返回false
            if (!str.matches(stringSchema.getRegex())) {
                hasSpecifiedException(stringSchema, CheckType.REGEX);

                this.errorMessage = "String 类型 [" + str + "] 不符合匹配规则";
                throw new RuntimeException("Key => " + key + " " + this.errorMessage);
            }
        }
        if ((Objects.nonNull(stringSchema.getMaxLength()) && stringSchema.getMaxLength() < str.length()) ||
                (Objects.nonNull(stringSchema.getMinLength()) && stringSchema.getMinLength() > str.length())
        ) {
            hasSpecifiedException(stringSchema, CheckType.STRING_BOUND);

            this.errorMessage = "String 类型 [" + str + "] 长度不符合规则";
            throw new RuntimeException("Key => " + key + " " + this.errorMessage);
        }

    }
}
