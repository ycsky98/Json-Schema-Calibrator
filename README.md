# Json-Schema-Calibrator
# 基于Java的Json描述器

### 1.组件介绍

---
    ArraySchema是一个用于数组的描述器,例如[{"name":"张三"}],
    这样一个json数组，
    那么使用的描述方式就是
    JSON.array().item(
        JSON.object()
            .attr("name", JSON.string())
    );

    来满足你对这段JSON的描述
---
    BooleanSchema是一个用于描述Boolean类型的描述器,
    例如要描述一个boolean类型
    
    JSON.bool();
---
    NumberSchema是一个用于描述数字类型的描述器,例如描述一个Number类型

    JSON.number();
---
    StringSchema是一个用于描述字符串类型的描述器,例如描述一个String类型

    JSON.string();
---
    ObjectSchema是一个用于描述嵌套类型过多的一个描述器,例如要描述一个对象类型
    {"name":"张三", "age": 18, "isBoy": true}

    JSON.object()
                 .attr("name", JSON.string())
                 .attr("age", JSON.number())
                 .attr("isBoy", JSON.bool())

- 如何进行Schema的描述校验
- 通过Verify.verify(json, schema), 即可进行json的校验
