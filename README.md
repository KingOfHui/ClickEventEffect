### 本项目为给View添加点击效果Demo，Lib可作为依赖库引用
* 简单使用：ClickEventsManager.buildDefault(); //可以放在Application中初始化
* 构建默认的点击效果方式，包含所有的设置点击事件的View，不包含ViewGroup，ViewGroup使用ClickEventXXX(如：ClickEventLinearLayout)
		
* ClickEventsFactory.initClickEventEffects(activity);
* 给需要设置点击效果的activity进行设置即可

### 此项目目前只进行点击加深颜色的效果，还可以扩展其他的效果，如水波纹，缩放等