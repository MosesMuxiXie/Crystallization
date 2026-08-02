# Crystallization

[English](README.md) | 简体中文

Crystallization 是一个适用于 Minecraft Java 版 1.16–26.2 的 Fabric 模组。功能启用后，
手持普通冰右键点击尚未含水的方块，即可使目标方块含水，并消耗一个冰块。冰仍是原版中
可正常堆叠的物品（最多 64 个）。

## 使用方法

- 首次启动时，该功能默认为关闭状态。
- 在游戏中按 `Alt + F` 可启用或禁用该功能。主按键可在
  “选项 > 控制 > 按键绑定 > Crystallization”中修改。
- 安装与 Minecraft 版本对应的 Mod Menu 后，可以打开配置界面切换功能，
  也可以直接跳转到按键绑定界面。
- 手持原版冰，对楼梯、台阶、栅栏或活板门等干燥且可含水的方块使用。

多人游戏时，客户端和服务端都必须安装本模组及对应版本的 Fabric API。Mod Menu 为可选依赖。

## 版本兼容

Minecraft 跨版本会改变字节码映射、Fabric 网络 API、GUI、Java 版本以及 26.1 起的无混淆构建方式，
因此本项目会按兼容线产出多个 JAR，而不是伪装成一个“全版本通用 JAR”。请安装文件名中 Minecraft
版本与游戏版本范围匹配的文件。

| 构建目标 | 适用 Minecraft | 玩家运行时 Java |
| --- | --- | --- |
| `mc1_16_5` | 1.16–1.16.5 | 8+ |
| `mc1_17_1` | 1.17–1.17.1 | 16+ |
| `mc1_18_2` | 1.18–1.18.2 | 17+ |
| `mc1_19_2` | 1.19–1.19.2 | 17+ |
| `mc1_19_4` | 1.19.3–1.19.4 | 17+ |
| `mc1_20_1` | 1.20–1.20.1 | 17+ |
| `mc1_20_4` | 1.20.2–1.20.4 | 17+ |
| `mc1_20_6` | 1.20.5–1.20.6 | 21+ |
| `mc1_21_1` | 1.21–1.21.1 | 21+ |
| `mc1_21_4` | 1.21.2–1.21.4 | 21+ |
| `mc1_21_5` | 1.21.5 | 21+ |
| `mc1_21_8` | 1.21.6–1.21.8 | 21+ |
| `mc1_21_11` | 1.21.9–1.21.11 | 21+ |
| `mc26_1_2` | 26.1–26.1.2 | 25+ |
| `mc26_2` | 26.2 | 25+ |

每个 JAR 的 `fabric.mod.json` 都包含自己的 Minecraft、Java、Fabric Loader 和 Mod Menu 范围，
装错版本时 Fabric Loader 会在启动前给出明确提示。

## 构建

开发构建需要 JDK 25 或更新版本。构建全部版本：

```text
./gradlew build
```

Windows 使用 `gradlew.bat build`。所有可发布 JAR 会汇总到 `build/libs`。

只构建一个版本可以显著减少首次下载和重映射时间，例如：

```text
./gradlew --configure-on-demand :mc1_20_6:build
```

该版本的 JAR 位于 `versions/mc1_20_6/build/libs`。GitHub Actions 使用相同的 15 项矩阵并行验证。

如果项目位于 OneDrive 等同步目录，构建输出可能被同步程序短暂锁定。可把中间产物放到同步目录之外：

```text
gradlew.bat -Pbuild_root=C:\temp\crystallization-build collectJars
```

此时汇总 JAR 位于 `C:\temp\crystallization-build\root\libs`；该参数不改变源码位置。

## 源码结构

- `src/common`：所有版本共用的配置存储和 Mod Menu 入口。
- `src/compat`：按 Fabric 网络、GUI、键盘输入与混淆方式拆分的兼容层。
- `versions`：Gradle 的轻量目标项目；不复制业务源码。
- `build.gradle`：Minecraft、Fabric API、Mod Menu、Java 与元数据范围的唯一版本矩阵。

## 许可证

本项目采用 CC0 许可证。
