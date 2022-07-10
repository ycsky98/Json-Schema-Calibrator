package org.schema.json.base;

/**
 * @author yangcong
 *
 * Schema 基类规范
 */
public abstract class Schema {

    /**
     * 是否为空
     * true必传
     * false非必传
     */
    public Boolean isNULL;

    /**
     *
     * @param flag (true 必传, false 非必传)
     * @return
     */
    public boolean isNULL(boolean flag){
        return this.isNULL;
    }

    /**
     * 是否为必传
     *
     * @return (true 必传, false 非必传)
     */
    public Boolean canNULL() {
        return isNULL;
    }
}
