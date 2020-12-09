<p align="center"><img src="./erupt-web/src/main/resources/public/erupt.svg" height="150" alt="logo"/></p>

---

# Erupt Framework 🚀 

[![Gitee star](https://gitee.com/erupt/erupt-site/badge/star.svg?theme=dark)](https://gitee.com/erupt/erupt)
[![GitHub stars](https://img.shields.io/github/stars/erupts/erupt?style=social)](https://github.com/erupts/erupt)
[![GitHub forks](https://img.shields.io/github/forks/erupts/erupt?style=social)](https://github.com/erupts/erupt)

官方网站：https://erupt.xyz

国内仓库：https://gitee.com/erupt/erupt  

国外仓库：https://github.com/erupts/erupt

QQ交流群：821389129

## 首页 | Home
https://www.erupt.xyz

## 简介 | Intro
Erupt Framework仅需要简单的注解配置，可快速开发企业级Admin管理后台。  
拥有简洁美观的后台页面，支持23类数据录入组件，支持多数据源，严密的安全策略。  
**我不是代码生成器**，代码生成器并非后台开发的最优解。


## 特性 | Features
+ **易于上手**：仅需了解@Erupt与@EruptField两个注解即可上手开发。
+ **代码简洁**：前端零代码，后端template、controller、service、dao都不需要，仅需一个实体类即可。
+ **敏捷开发**：仅单个`.java`文件即可实现后台管理功能，专注业务逻辑与核心功能的研发。
+ **快速迭代**：需求变更仅需修改或添加注解配置即可，迭代速度比需求讨论速度还快。
+ **高安全性**：可靠的安全机制，登录白名单，菜单权限验证，请求头检查，注解项检查，为你的数据保驾护航。
+ **自动建表**：依托于JPA可自动帮你完成数据库建表相关工作。
+ **无侵入性**：完全不影响Spring Boot与JPA功能的使用。
+ **多数据源**：MySQL、Oracle、SQL Server、PostgreSQL、H2，甚至支持MongoDB。
+ **多种组件**：支持滑动输入、时间选择、开关、图片上传、代码编辑器、自动完成、树、多选框、地图等23类组件
+ **丰富展示**：普通文本、二维码、链接、图片、HTML、代码段、iframe、swf等
+ **代码生成**：erupt代码已经足够简洁，代码生成器可进一步提升开发效率。
+ **扩展性强**：支持自定义数据源实现、自定义页面、自定义模板等。
+ **界面美观**：每个交互都精心设计，产品思维打磨，只为了更好的操作体验。

## 代码示例
``` java
@Erupt(name = "简单的例子")
@Table(name = "simple_table")
@Entity
public class Simple extends BaseModel {

    @EruptField(
            views = @View(title = "文本"),
            edit = @Edit(title = "文本")
    )
    private String input;
    
    @EruptField(
            views = @View(title = "数值"),
            edit = @Edit(title = "数值")
    )
    private Integer number;

    @EruptField(
            views = @View(title = "布尔"),
            edit = @Edit(title = "布尔")
    )
    private Boolean bool;

    @EruptField(
            views = @View(title = "时间"),
            edit = @Edit(title = "时间")
    )
    private Date date;

}
```
#### 运行效果
![result](./img/simple.gif)

## 演示截图 | Screenshot
<table>
    <tr>
        <td><img src="./img/login.png"/></td>
        <td><img src="./img/home.png"/></td>
    </tr>
    <tr>
        <td><img src="./img/role.png"/></td>
        <td><img src="./img/log.png"/></td>
    </tr>
    <tr>
        <td><img src="./img/code.png"/></td>
        <td><img src="./img/job.png"/></td>
    </tr>
    <tr>
        <td><img src="./img/tpl.png"/></td>
        <td><img src="./img/complex.png"/></td>
    </tr>
    <tr>
        <td><img src="./img/goods.png"/></td>
        <td><img src="./img/chart.png"/></td>
    </tr>
    <tr>
        <td><img src="./img/component.png"/></td>
        <td><img src="./img/component-edit.png"/></td>
    </tr>
    <tr>
        <td><img src="./img/bi.png"/></td>
        <td><img src="./img/bi2.png"/></td>
    </tr>
</table>


### 模块说明 | Module
```lua
erupt
├── erupt-annotation -- 核心注解模块
├── erupt-core -- 核心功能包
├── erupt-data -- 数据相关包
     ├── erupt-jpa -- 关系型数据库erupt实现
     └── erupt-mongodb -- mongodb数据库erupt实现
├── erupt-job -- 定时任务功能
├── erupt-security -- 接口数据安全模块
├── erupt-upms -- 用户权限管理
└── erupt-web -- 前端页面

erupt-pro //暂未开放
├── erupt-bi -- 配置化灵活报表工具，仅需后台配置加sql语句，支持sql与js混编
├── erupt-tpl -- 支持在erupt中自定义页面，自定义图表，自定义模板等功能，模板引擎支持freemarker/thymeleaf/原生H5
└── erupt-generator -- 代码生成器，通过简单配置，生成erupt代码段
```

## 为什么要做Erupt 
+ 开发管理后台是项目中必不可少的工作，但往往管理后台的开发周期要占到总工作量的50%
+ 开发后台数据管理系统，管理系统页面单一，接口众多，很多后端小伙伴被迫开始接触的前端代码，
+ 本来要需要一个月甚至几个月的工作任务，能否在几小时之内完成呢？
+ 代码生成器的本质还是生成繁琐的后台代码，一旦修改后期生成的代码很难合并，虽然减轻了一部分工作，但仍需更好的解决方式。

## 技术体系 | Technology system
#### 后端
Java 8、 ScriptEngine、 Annotation、 JDBC、 Reflect
、 Spring Boot、 JPA、 Hibernate、 Quartz、 Gson
、 Lombok、 Freemarker、 Thymeleaf ...

#### 前端
JavaScript、 H5、 MVVM、 Router、 Angular CLI
、 Angular、 NG-ZORRO、 NG-ALAIN、 G2Plot
、 RxJS、 TypeScript、 Less、 ...


## 在线演示 | Demo
演示地址：https://www.erupt.xyz/demo  
账号密码：`guest / guest`

支持主流 4 款现代浏览器，以及 Internet Explorer 11+。

<table>
    <tr>
        <td width="20%" align="center"><img src="https://cdn.jsdelivr.net/gh/alrra/browser-logos/src/edge/edge_48x48.png" alt="IE / Edge" width="24px" height="24px" /> <br> Edge / IE </td>
        <td width="15%" align="center"><img src="https://cdn.jsdelivr.net/gh/alrra/browser-logos/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" /><br> Firefox </td>
        <td width="15%" align="center"><img src="https://cdn.jsdelivr.net/gh/alrra/browser-logos/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" /> <br> Chrome </td>
        <td width="15%" align="center"><img src="https://cdn.jsdelivr.net/gh/alrra/browser-logos/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" /> <br> Safari </td>
        <td width="15%" align="center"><img src="https://cdn.jsdelivr.net/gh/alrra/browser-logos/src/opera/opera_48x48.png" alt="Opera" width="24px" height="24px" /> <br> Opera </td>
        <td width="20%" align="center"><img src="https://cdn.jsdelivr.net/gh/alrra/browser-logos/src/electron/electron_48x48.png" alt="Electron" width="24px" height="24px" /> <br> Electron </td>
    </tr>
    <tr>
        <td align="center">Edge 16 / IE 11+</td>
        <td align="center">522</td>
        <td align="center">57</td>
        <td align="center">11</td>
        <td align="center">44</td>
        <td align="center">Chromium 57</td>
    </tr>
</table>

## 使用文档 | Documentation
https://www.yuque.com/yuepeng/erupt

## 开源推荐 | Recommend
[`zeta-api`：通过XML配置快速创建api接口与文档，安全且高效，快速开发必备！](https://github.com/erupts/zeta-api)

## 捐赠 | Donate


**作者**：YuePeng / erupts@126.com