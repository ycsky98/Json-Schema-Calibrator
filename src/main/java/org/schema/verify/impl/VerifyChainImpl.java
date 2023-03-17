package org.schema.verify.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.schema.json.base.Schema;
import org.schema.verify.Verify;
import org.schema.verify.verifyschema.impl.ObjectVerifySchema;

/**
 * @author ashone
 * <p>
 * desc
 */
public class VerifyChainImpl extends Verify {
    private final ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

    private final ObjectVerifySchema objectVerifySchema = new ObjectVerifySchema(objectMapper);

    public boolean verifySchema(Object data, Schema schema) throws JsonProcessingException {
        return objectVerifySchema.verifySchema(data, schema);
    }

}
