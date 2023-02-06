package org.schema.json;

import org.schema.json.base.CheckType;
import org.schema.json.base.Schema;

/**
 * @author yangcong
 *
 * String类型Schema
 */
public class StringSchema extends Schema {

    /**
     * 对当前字段的描述
     */
    private String desc;
    /**
     * 正则表达式
     */
    private String regex;
    /**
     * 最大长度
     */
    private Integer maxLength = null;
    /**
     * 最小长度
     */
    private Integer minLength = null;

    {
        currentCheckType = CheckType.STRING;
    }

    @Override
    public StringSchema error(RuntimeException hintException) {
        getErrorMap().put(currentCheckType, hintException);

        return this;
    }

    /**
     * 描述
     *
     * @param desc
     * @return
     */
    public StringSchema desc(String desc) {
        this.desc = desc;
        return this;
    }

    /**
     * 正则表达式
     *
     * @param regex
     * @return
     */
    public StringSchema regex(String regex) {
        currentCheckType = CheckType.REGEX;
        this.regex = regex;
        return this;
    }

    /**
     * 最大长度
     *
     * @param maxLength
     * @return
     */
    public StringSchema maxLength(int maxLength) {
        this.currentCheckType = CheckType.STRING_BOUND;
        this.maxLength = maxLength;
        return this;
    }

    /**
     * 最小长度
     *
     * @param minLength
     * @return
     */
    public StringSchema minLength(int minLength) {
        this.currentCheckType = CheckType.STRING_BOUND;
        this.minLength = minLength;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public String getRegex() {
        return regex;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public Integer getMinLength() {
        return minLength;
    }

}
