# Crystallization

English | [简体中文](README.zh-CN.md)

Crystallization is a Fabric mod for Minecraft Java Edition 1.16 through 26.2.
While enabled, right-clicking a dry waterloggable block with regular ice
waterlogs the target and consumes one ice block.

## Usage

- The feature is disabled on first launch.
- Press `Alt + F` in game to toggle it. Change the primary key under
  Options > Controls > Key Binds > Crystallization.
- With the matching Mod Menu version installed, use the configuration screen
  to toggle the feature or open the key-binding screen.
- Use vanilla ice on a dry waterloggable block such as a stair, slab, fence,
  or trapdoor.

The mod and the matching Fabric API must be installed on both the client and
server for multiplayer. Mod Menu is optional.

## Version support

Minecraft changes mappings, Fabric networking, GUI APIs, Java requirements,
and (since 26.1) its obfuscation model across this range. The project therefore
builds one JAR per compatibility family instead of declaring one unsafe
universal JAR.

| Target | Minecraft range | Runtime Java |
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

Each output contains precise Minecraft, Java, Fabric Loader, and optional Mod
Menu metadata so Fabric Loader rejects the wrong JAR before the game starts.

## Building

Development builds require JDK 25 or newer. Build and collect every target in
`build/libs` with:

```text
./gradlew build
```

On Windows, use `gradlew.bat build`. To build only one target:

```text
./gradlew --configure-on-demand :mc1_20_6:build
```

Its output is written to `versions/mc1_20_6/build/libs`. GitHub Actions builds
the same 15-target matrix in parallel.

Cloud-synced workspaces such as OneDrive can temporarily lock build outputs.
Move only the generated files outside the synced directory when needed:

```text
gradlew.bat -Pbuild_root=C:\temp\crystallization-build collectJars
```

The collected JARs are then written to
`C:\temp\crystallization-build\root\libs`; source locations are unchanged.

Shared configuration and Mod Menu integration live in `src/common`; API-era
adapters live in `src/compat`; lightweight Gradle targets live in `versions`.
The dependency and metadata matrix has a single source of truth in
`build.gradle`.

## License

This project is available under the CC0 license.
