# LoadingViewManager-For-Android

**这是一个能快速创建【加载动画】的封装类，无需任何布局文件，并搭配多种加载动画**

<img src="https://img-blog.csdnimg.cn/20190317032900886.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hCS19NeVN1bW1lckNU,size_16,color_FFFFFF,t_70" width="200px" /><img src="https://img-blog.csdnimg.cn/20190317032948915.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hCS19NeVN1bW1lckNU,size_16,color_FFFFFF,t_70" width="200px" /><img src="https://img-blog.csdnimg.cn/20190317033015178.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hCS19NeVN1bW1lckNU,size_16,color_FFFFFF,t_70" width="200px" /><img src="https://raw.githubusercontent.com/hanzhanbing/AVLoadingIndicatorView/master/screenshots/avi.gif" width="225px" />

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

### 概览

  ```
  LoadingViewManager.with(this)                 // Activity或Fragment
                .setHintText("加载中")          // 提示信息
                .setAnimationStyle("BallClipRotatePulseIndicator")  // 修改动画样式，传入样式名称
                .setShowInnerRectangle(true)    // 是否显示矩形框
                .setTouchOutsideToDismiss(true) // 是否点击动画外部消失
                .setOutsideAlpha(0.5f)          // 设置外部背景透明度
                .setInnerRectangleAlpha(0.8f)   // 设置矩形框透明度
                .setLoadingContentMargins(20, 40, 20, 50)   // 设置动画与矩形框的距离，间接也设置了矩形框大小
                .setMinAnimTime(2000)           // 设置动画的最短时长
                .setAnimationSize(400, 400)     // 设置动画样式大小
                .setHintTextSize(50)            // 设置提示文本的大小
                .setInnerRectangleColor("#000") // 设置矩形框颜色
                .setOnTouchOutsideListener(new View.OnClickListener() { 
                    @Override
                    public void onClick(View v) {
                        // 可自定义点击动画外部分的响应
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

#### 参数默认值示例

```
minAnimTime = 1000

maxAnimTime = 600000

setLoadingContentMargins(50, 50, 50, 50, 20)

setAnimationSize(0.1429)

textSize = 14

setHintTextMaxWidth(0.3333)
```

#### 动画样式与样式名称

<img src="https://raw.githubusercontent.com/hanzhanbing/AVLoadingIndicatorView/master/screenshots/avi.gif" width="250px" />

```
// 第一行
BallPulseIndicator、BallGridPulseIndicator、BallClipRotateIndicator、BallClipRotatePulseIndicator

// 第二行
SquareSpinIndicator、BallClipRotateMultipleIndicator、BallPulseRiseIndicator、BallRotateIndicator

// 第三行
CubeTransitionIndicator、BallZigZagIndicator、BallZigZagDeflectIndicator、BallTrianglePathIndicator

// 第四行
BallScaleIndicator、LineScaleIndicator、LineScalePartyIndicator、BallScaleMultipleIndicator

// 第五行
BallPulseSyncIndicator、BallBeatIndicator、LineScalePulseOutIndicator、LineScalePulseOutRapidIndicator

// 第六行
BallScaleRippleIndicator、BallScaleRippleMultipleIndicator、BallSpinFadeLoaderIndicator、LineSpinFadeLoaderIndicator

// 第七行
TriangleSkewSpinIndicator、PacmanIndicator、BallGridBeatIndicator、SemiCircleSpinIndicator
```

## 感谢

  - [AVLoadingIndicatorView](https://github.com/hanzhanbing/AVLoadingIndicatorView)
