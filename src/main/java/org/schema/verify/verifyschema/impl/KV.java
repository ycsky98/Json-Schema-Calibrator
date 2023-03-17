package org.schema.verify.verifyschema.impl;

import org.schema.json.base.Schema;

/**
 * @author ashone
 * <p>
 * desc
 */
public class KV {

    private Object data;

    private Schema schema;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KV kv)) return false;

        if (!getData().equals(kv.getData())) return false;
        return getSchema().equals(kv.getSchema());
    }

    @Override
    public int hashCode() {
        int result = getData().hashCode();
        result = 31 * result + getSchema().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "KV{" +
                "data=" + data +
                ", schema=" + schema +
                '}';
    }
}