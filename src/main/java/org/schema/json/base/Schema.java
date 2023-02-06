package org.schema.json.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangcong
 *
 * Schema 基类规范
 */
public abstract class Schema {
    //自定义错误的映射
    private final Map<CheckType, RuntimeException> errorMap = new HashMap<>();
    //用来控制当前操作的检查类型
    protected CheckType currentCheckType;

    public abstract Schema error(RuntimeException hintException);

    public Map<CheckType, RuntimeException> getErrorMap() {
        return errorMap;
    }

    public CheckType getCurrentCheckType() {
        return currentCheckType;
    }
}
