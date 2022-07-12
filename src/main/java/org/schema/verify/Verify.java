package org.schema.verify;

import org.schema.json.base.Schema;
import org.schema.verify.impl.VerifyImpl;

/**
 * @author yangcong
 *
 * 校验器
 */
public class Verify {

    public static boolean verify(Object data, Schema schema) {
        VerifyImpl verify = new VerifyImpl(data, schema);
        try {
            return verify.verifySchema(data, schema);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
