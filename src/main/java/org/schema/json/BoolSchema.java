package org.schema.json;

import org.schema.json.base.CheckType;
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

    {
        currentCheckType = CheckType.BOOLEAN;
    }

    @Override
    public BoolSchema error(RuntimeException hintException) {
        getErrorMap().put(currentCheckType, hintException);
        return this;
    }

    /**
     * 描述
     *
     * @param desc
     * @return
     */
    public BoolSchema desc(String desc) {
        this.desc = desc;
        return this;
    }


    public String getDesc() {
        return desc;
    }

}
