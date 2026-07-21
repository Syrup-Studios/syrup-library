# Syrup Library

Syrup Library is a Fabric library mod for reusable, typed configuration in Syrup Studios mods. Version 0.1.0 intentionally contains configuration infrastructure only: it does not add commands, GUIs, permissions, networking, or gameplay features.

The current Stonecutter target is Fabric for Minecraft 1.20.1 on Java 17. The project layout is retained so additional Minecraft versions and loader targets can be introduced later without coupling those targets to the configuration core.

## Features

- Typed boolean, integer, long, double, string, string-list, and enum values
- Nested sections, schema defaults, descriptions, numeric ranges, and restart metadata
- Full JSON5 parsing with documented first-run file generation
- Multiple independent config files below Fabric's config directory
- Structured information, warnings, errors, original values, and fallbacks
- Atomic whole-snapshot publication and synchronized reloads
- Previous-snapshot retention after document-level reload failures
- Separate configured, effective, and startup views for restart-required settings

## Build

```bash
./gradlew clean build
```

Stonecutter writes the Fabric 1.20.1 artifacts below `versions/1.20.1-fabric/build/libs/`. The remapped mod JAR embeds the JSON5 parser it needs at runtime.

## Consumer example

```java
public final class ExampleConfig {
    public static final ConfigSpec SPEC = ConfigSpec.builder("example_mod")
            .header("Example Mod configuration")
            .build();

    public static final BooleanConfigValue ENABLED =
            SPEC.booleanValue("enabled", true, "Whether the feature is enabled.");

    public static final ConfigSection LIMITS =
            SPEC.section("limits", "Limits applied by Example Mod.");

    public static final IntConfigValue MAX_ITEMS =
            LIMITS.intValue("max_items", 10, 0, 100, "Maximum retained items.");

    public static final BooleanConfigValue NAMESPACED_COMMANDS =
            SPEC.booleanValue(
                    "namespaced_commands",
                    false,
                    "Registers commands below /example.",
                    RestartRequirement.REQUIRED
            );
}
```

Register during the consuming mod's initialization:

```java
RegisteredConfig config = SyrupConfigManager.getInstance().register(ExampleConfig.SPEC);
ConfigLoadResult initial = config.initialResult();
```

Registration creates `config/example_mod.json5` when absent and performs the initial load. A consuming mod may expose its own reload command using `config.reload()`; Syrup Library intentionally provides no global command.


## Reload and fallback semantics

Field-level problems do not reject a valid document. Missing or invalid values use defaults, bounded numbers are clamped, unknown fields are ignored, and diagnostics describe the chosen effective value. Invalid JSON5, I/O failures, and a non-object document root reject the entire load.

On initial-load failure, complete schema defaults are active. On later failure, the last successfully published snapshot remains active. A successful reload publishes every value at once, so callers never observe a partially loaded state.

`ConfigValue.get()` returns the value effective in the running process. For a restart-required setting, `configuredValue()` exposes a successfully reloaded edit while `get()` and `startupValue()` continue returning the startup value. `RegisteredConfig.snapshot()` is available when a caller must read several values from one exact snapshot.

## Validation policy

| Input condition | Behavior | Result |
| --- | --- | --- |
| Missing field | Schema default | Information |
| Unknown field | Ignored | Information |
| Wrong type | Schema default | Warning |
| Number outside bounds | Clamp to nearest bound | Warning |
| Invalid string predicate | Schema default | Warning |
| Invalid enum name | Schema default | Warning |
| Invalid complete document | Retain prior snapshot | Error / unsuccessful load |

Enums are written as lowercase Java constant names using `Locale.ROOT`, never as ordinals. Config IDs are restricted to lowercase safe filename characters, and schema keys are restricted to safe unquoted JSON5 identifiers.
