package org.schema.verify.verifyschema;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.schema.json.ObjectSchema;
import org.schema.json.base.CheckType;
import org.schema.json.base.Schema;
import org.schema.verify.Verify;

/**
 * @author ashone
 *
 * 各类型验证的抽象类
 */
public abstract class VerifySchema {

    public abstract  void verifySchema(Object data,Schema schema ,String key) throws JsonProcessingException;
    public static Class<? extends Schema> handlerSchema(){
        return null;
    }

    //自定义异常抛出
    protected final void hasSpecifiedException(Schema schema, CheckType checkType) {
        RuntimeException runtimeException = schema.getErrorMap().get(checkType);
        if (runtimeException != null) {
            throw runtimeException;
        }

    }
}
