package org.schema.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.schema.json.base.Schema;
import org.schema.verify.impl.VerifyImpl;

/**
 * @author yangcong
 *
 * 校验器
 */
public abstract class Verify {

    public static boolean verify(Object data, Schema schema) {
        VerifyImpl verify = new VerifyImpl(data, schema);
        try {
            return verify.verifySchema(data, schema);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON解析出错, 请检查格式\n" + e.getMessage());
        }
    }
}
