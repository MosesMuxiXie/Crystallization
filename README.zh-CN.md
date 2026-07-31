# Crystallization

[English](README.md) | [简体中文](README.zh-CN.md)

Crystallization 是一个适用于 Minecraft Java 版 1.21.11 的 Fabric 模组。功能启用后，
手持普通冰右键点击尚未含水的方块，即可使目标方块含水，并消耗一个冰块。冰仍是原版中
可正常堆叠的物品（最多 64 个）。

## 使用方法

- 首次启动时，该功能默认为关闭状态。
- 在游戏中按 `Alt + F` 可启用或禁用该功能。主按键可在
  “选项 > 控制 > 按键绑定 > Crystallization”中修改。
- 安装 Mod Menu 后，可以打开 Crystallization 的配置界面来启用或禁用该功能，
  也可以直接跳转到按键绑定界面。
- 手持原版冰，对楼梯、台阶、栅栏或活板门等干燥且可含水的方块使用。

多人游戏时，客户端和服务端都必须安装本模组。Mod Menu 为可选依赖。

## 构建

运行 `./gradlew build`（Windows 上运行 `gradlew.bat build`）。构建完成的 JAR 文件
将生成在 `build/libs` 目录中。

## 许可证

此模板采用 CC0 许可证。你可以自由学习其内容，并将其用于自己的项目。
