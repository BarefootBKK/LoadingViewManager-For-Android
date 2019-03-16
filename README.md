# LoadingViewManager-For-Android

这是一个能快速搭建加载动画的封装类，无需任何布局文件，并搭配多种加载动画

## 准备工作

#### 在build.gradle(app)中加入依赖

  ```
  implementation 'com.wang.avi:library:2.1.3'
  implementation 'com.squareup.assertj:assertj-android-cardview-v7:1.2.0'
  ```
  
#### 引入LoadingViewManager

  下载本项目的```LoadingViewManager.java```文件，将其复制到自己的安卓项目中
  
  
## 快速开始

#### 构建动画

  ```
  LoadingViewManager.with(Activity或Fragment).setHintText("加载提示").build();
  ```
  
#### 关闭动画

  ```
  LoadingViewManager.dismiss();
  ```


## 更多用法

#### 修改动画样式

  ```
  LoadingViewManager.with(activity)
                        .setAnimationStyle("BallClipRotatePulseIndicator")  // 修改动画样式，传入样式名称
                        .setHintText("正在加载")
                        .build();
  ```
  
#### 显示加载边框

  ```
  LoadingViewManager.with(activity)
                        .setShowInnerRectangle(true)  // 显示矩形框
                        .setHintText("正在加载")
                        .build();
  ```
    
    
#### 概览

  ```
  LoadingViewManager.with(this)                 // Activity或Fragment
                .setHintText("加载中")          // 提示信息
                .setAnimationStyle("BallClipRotatePulseIndicator")  // 设置加载动画样式
                .setShowInnerRectangle(true)    // 是否显示矩形框
                .setTouchOutsideToDismiss(true) // 是否点击动画外部消失
                .setOutsideAlpha(0.5f)          // 设置外部背景透明度
                .setInnerRectangleAlpha(0.8f)   // 设置矩形框透明度
                .setLoadingContentMargins(20, 40, 20, 50)   // 设置动画与矩形框的距离，间接也设置了矩形框大小
                .setMinAnimTime(2000)           // 设置动画的最短时长
                .setAnimationSize(400, 400)     // 设置动画大小
                .setHintTextSize(50)            // 设置提示文本的大小
                .setInnerRectangleColor("#000") // 设置矩形框颜色
                .setOnTouchOutsideListener(new View.OnClickListener() { // 可定义点击动画外部分的响应
                    @Override
                    public void onClick(View v) {
                        
                    }
                })
                .setOnAnimatingListener(new LoadingViewManager.OnAnimatingListener() {  // 动画加载中的监听器
                    @Override
                    public void onAnimating() {
                        // 加载中
                    }

                    @Override
                    public void onDismiss() {
                        // 动画完成（消失）后
                    }
                })
                .build();   // 开始构建
  ```

### 高级

#### 动画加载中，更新文本信息或动画样式
  
  ```
  LoadingViewManager.updateHintText("更新提示");
  
  LoadingViewManager.updateAnimation("动画样式名称");
  ```
  
#### 强制关闭动画

  ```
  LoadingViewManager.dismiss(true); // 参数：isForcedDismiss 是否强制关闭
  ```

#### 参数默认值

```
minAnimTime = 1000

maxAnimTime = 600000

setLoadingContentMargins(50, 50, 50, 50, 20)

setAnimationSize(0.1429)

textSize = 14

setHintTextMaxWidth(0.3333)
```

### 特别感谢

  本项目基于 [AVLoadingIndicatorView](https://github.com/hanzhanbing/AVLoadingIndicatorView) 项目二次开发，感谢原作者提供的炫酷加载动画样式框架
