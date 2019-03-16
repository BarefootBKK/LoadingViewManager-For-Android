# LoadingViewManager-For-Android

这是一个能快速搭建加载动画的封装类，无需任何布局文件，并搭配多种加载动画

## 准备工作

### 在build.gradle(app)中加入依赖

  ```
  implementation 'com.wang.avi:library:2.1.3'
  implementation 'com.squareup.assertj:assertj-android-cardview-v7:1.2.0'
  ```
  
### 引入LoadingViewManager

  下载本项目的```LoadingViewManager.java```文件，将其复制到自己的安卓项目中
  
  
## 快速开始

  ```
  LoadingViewManager.with(Activity或Fragment).setHintText("加载提示").build();
  ```

## 更多用法

#### 修改动画样式

  ```
  LoadingViewManager.with(LoginActivity.this)
                        .setAnimationStyle("BallClipRotatePulseIndicator")  // 修改动画样式，传入样式名称
                        .setHintText("正在加载")
                        .build();
  ```
  
#### 显示加载框

  ```
  LoadingViewManager.with(LoginActivity.this)
                        .setShowInnerRectangle(true)  // 显示矩形框
                        .setHintText("正在加载")
                        .build();
  ```
    
### 概览

