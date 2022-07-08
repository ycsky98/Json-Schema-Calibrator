package org.schema.json;

import org.schema.json.base.Schema;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangcong
 *
 * 对象描述器
 */
public class ObjectSchema extends Schema {

    /**
     * 存储对象属性
     */
    private Map<String, Schema> obj = new HashMap<>();

    /**
     * 描述
     */
    private String desc;

    /**
     * 描述这个对象
     *
     * @param desc
     * @return
     */
    public ObjectSchema desc(String desc){
        this.desc = desc;
        return this;
    }

    /**
     * @param key
     * @param schema
     * @return
     */
    public ObjectSchema attr(String key, Schema schema){
        this.obj.put(key, schema);
        return this;
    }

    public Map<String, Schema> getObj() {
        return obj;
    }

    public String getDesc() {
        return desc;
    }

}
