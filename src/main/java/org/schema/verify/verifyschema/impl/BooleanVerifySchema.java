package org.schema.verify.verifyschema.impl;

import org.schema.json.BoolSchema;
import org.schema.json.base.CheckType;
import org.schema.json.base.Schema;
import org.schema.verify.verifyschema.VerifySchema;

/**
 * @author ashone
 * <p>
 * desc
 */
public class BooleanVerifySchema extends VerifySchema {

    public static Class<BoolSchema> handlerSchema() {
        return BoolSchema.class;

    }

    @Override
    public void verifySchema(Object data, Schema schema, String key) {

        if (!(data instanceof Boolean))
            hasSpecifiedException(schema, CheckType.BOOLEAN);
        throw new RuntimeException("Key => " + key + " " + "非Boolean类型");


    }
}