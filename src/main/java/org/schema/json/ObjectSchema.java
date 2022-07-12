package org.schema.json;

import org.schema.json.base.Schema;

import java.util.*;

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
     * keys
     */
    private List<String> keys = new ArrayList<>();

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

    /**
     * 必须包含的字段
     *
     * @param keys
     *
     * @return
     */
    public ObjectSchema require(String ... keys){
        this.keys.addAll(Arrays.asList(keys));
        return this;
    }

    public Map<String, Schema> getObj() {
        return obj;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getKeys() {
        return keys;
    }
}
