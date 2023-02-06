package org.schema.json.base;

//检查的类型
public enum CheckType {

    ARRAY, SIZE_BOUND,//数组

    ENUM_VAL,
    MIN,
    MAX,
    NUMBER,//数字

    ATTR,
    REQUIRE, OBJECT,//对象

    REGEX,
    STRING_BOUND, STRING//字符串

    ,
    BOOLEAN//布尔
}
