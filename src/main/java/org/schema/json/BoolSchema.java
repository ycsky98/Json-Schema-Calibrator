package org.schema.json;

import org.schema.json.base.Schema;

/**
 * @author yangcong
 *
 * Bool描述器
 */
public class BoolSchema extends Schema {

    /**
     * 对当前字段的描述
     */
    private String desc;

    /**
     * 描述
     *
     * @param desc
     * @return
     */
    public BoolSchema desc(String desc){
        this.desc = desc;
        return this;
    }


    public String getDesc() {
        return desc;
    }

}
