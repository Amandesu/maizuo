# 仿卖座网
仿`Android`版的卖座网，整个卖座`APP`主要是由`Android`页面+`ReactNative`页面组成.作者努力正在更新中.

## 开发环境
`Android 5.0+`
`Android Studio3.0+`
`Gradle 4.1`
`ReactNative@0.59.5`

如果要进行`ReactNative`页面开发很简单,`git clone`[ReactNative]("https://github.com/Amandesu/maizuoRN")，然后`npm run start` 然后启动`Android`然后点击首页右上角白色的`rn`

## `ReactNative`与`Android`通信
主要通过`BridgeModule`,更新中...

方法名 | 说明 |    
|------|---|  
`closeWindow` | 关闭当前页面 |
`getDataFromIntent` | 打开`RN`页面的时候传入参数 | 
`openLink` | 从`RN`页面跳转至安卓页面 | 
`closeWindow` | 关闭当前页面 |
`getDataFromIntent` | 打开`RN`页面的时候传入参数 | 
`openLink` | 从`RN`页面跳转至安卓页面 | 


## 页面结构

一个目录代表一个活动,下面是已经开发完成页面,其中城市页面和影城详情页为`ReactNative`页面

### 首页
下面有三个`fragment`
1. movie(已完成)
2. cinima(已完成)
3. user(已完成)

<img src="https://github.com/Amandesu/maizuo/raw/master/assets/movie.jpg"  width="200" height="420"/> <img src="https://github.com/Amandesu/maizuo/raw/master/assets/cinema.jpg"  width="200" height="420"/> <img src="https://github.com/Amandesu/maizuo/raw/master/assets/user.jpg"  width="200" height="420"/>



### 城市页面

<img src="https://github.com/Amandesu/maizuo/raw/master/assets/city_rn.jpg"  width="300" height="620"/>

### 电影详情页面

<img src="https://github.com/Amandesu/maizuo/raw/master/assets/film_detail.jpg" width="300" height="620"/>

### 影城详情页面

<img src="https://github.com/Amandesu/maizuo/raw/master/assets/cinema_detail_rn.jpg"  width="300" height="620"/>

### 某个电影的影城列表页面

<img src="https://github.com/Amandesu/maizuo/raw/master/assets/cinemas.jpg"  width="300" height="620"/>

### 登录页面

<img src="https://github.com/Amandesu/maizuo/raw/master/assets/login.jpg"  width="300" height="620"/>

## `git`和下载地址
为了便于管理`ReactNative`的项目我抽离出来了

[sdk下载地址](https://github.com/Amandesu/maizuo/blob/master/app/release/app-release.apk)项目地址
[android](https://github.com/Amandesu/maizuo)项目地址
[ReactNative](https://github.com/Amandesu/maizuoRN)项目地址.

如果大家有所帮助，可以给个[star]("https://github.com/Amandesu/maizuoRN"). 作者将更有动力更新和优化这个项目,不胜感激!







