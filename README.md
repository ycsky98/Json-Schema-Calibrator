# Json-Schema-Calibrator

<p align="center">
<a href="https://github.com/ycsky98/Json-Schema-Calibrator/releases"><img alt="Github Releases" src="https://img.shields.io/github/v/release/ycsky98/Json-Schema-Calibrator?include_prereleases&style=flat-square" /></a>
<a href="https://github.com/ycsky98/Json-Schema-Calibrator/stargazers"><img alt="GitHub Stargazers" src="https://img.shields.io/github/stars/ycsky98/Json-Schema-Calibrator.svg?style=flat-square&label=Stars&logo=github" /></a>
<a href="https://github.com/ycsky98/Json-Schema-Calibrator/issues"><img src="https://img.shields.io/github/issues/ycsky98/Json-Schema-Calibrator?color=blue&style=flat-square"/></a>
<a href="https://github.com/ycsky98/Json-Schema-Calibrator/commits"><img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/ycsky98/Json-Schema-Calibrator.svg?style=flat-square" /></a>
<br />
</p>

# 基于Java的Json描述器
  项目采用的jdk版本为17


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

<p>

## New Version
<p>

#### 1.新增自定义异常
<p>

#### 2.修改了存储方式

---
    1.如何自定义异常
        JSON.number().min(10)
        .error(new RuntimeException("最小长度不能为10"));
- 剩余情况如上而定
