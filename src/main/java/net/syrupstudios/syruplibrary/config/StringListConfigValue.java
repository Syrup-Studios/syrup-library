package net.syrupstudios.syruplibrary.config;

import java.util.List;

/** Typed immutable list-of-strings configuration value. */
public final class StringListConfigValue extends ConfigValue<List<String>> {
    StringListConfigValue(ConfigSpec spec, String key, String path, List<String> defaultValue,
                          List<String> description, RestartRequirement restartRequirement) {
        super(spec, key, path, List.class, List.copyOf(defaultValue), description, restartRequirement);
    }

    @Override
    List<String> copy(List<String> value) {
        return List.copyOf(value);
    }
}
