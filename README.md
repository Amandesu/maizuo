# 仿卖座网
仿`Android`版的卖座网，整个卖座`APP`主要是由`Android`页面+`ReactNative`页面组成.作者努力正在更新中, 目前已开发页面如下


## 开发环境
`Android 5.0+`
`Android Studio3.0+`
`Gradle 4.1`
`ReactNative@0.59.5`

## `ReactNative`与`Android`通信
主要通过`BridgeModule`
方法名 | 左右 |    
-|-
`closeWindow` | 关闭当前页面 |
`getDataFromIntent` | 打开`RN`页面的时候传入参数 | 
`openLink` | 从`RN`页面跳转至安卓页面 | 


## 页面结构

一个目录代表一个活动,下面是已经开放完成页面

### 首页
下面有三个`fragment`
1. movie(已完成)
2. cinima(已完成)
3. user(已完成)

### 城市页面

### 电影详情页面

### 影城详情页面`

### 某个电影的影城列表页面

### 登录页面

![登录页面]("https://github.com/Amandesu/maizuo/raw/master/assets/login.jpg")
## `git`地址
为了便于管理`ReactNative`的项目我抽离出来了

[android]("https://github.com/Amandesu/maizuo")项目地址
[ReactNative]("https://github.com/Amandesu/maizuoRN")项目地址.

如果大家有所帮助，可以给个[star]("https://github.com/Amandesu/maizuoRN"). 作者将更有动力更新和优化这个项目,不胜感激!







