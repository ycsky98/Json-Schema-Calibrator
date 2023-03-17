package org.schema.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.schema.json.base.Schema;
import org.schema.verify.impl.VerifyChainImpl;

/**
 * @author yangcong
 * <p>
 * 校验器
 */
public abstract class Verify {

    /**
     * 校验数据
     *
     * @param data
     * @param schema
     * @return
     */
    public static boolean verify(Object data, Schema schema) {
//        VerifyImpl verify = new VerifyImpl(data, schema);
        VerifyChainImpl verify = new VerifyChainImpl();

        try {
            return verify.verifySchema(data, schema);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON解析出错, 请检查格式\n" + e.getMessage());
        }
    }
}
