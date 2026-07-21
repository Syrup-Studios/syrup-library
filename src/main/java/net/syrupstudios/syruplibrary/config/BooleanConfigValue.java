package net.syrupstudios.syruplibrary.config;

import java.util.List;

/** Typed boolean configuration value. */
public final class BooleanConfigValue extends ConfigValue<Boolean> {
    BooleanConfigValue(ConfigSpec spec, String key, String path, boolean defaultValue,
                       List<String> description, RestartRequirement restartRequirement) {
        super(spec, key, path, Boolean.class, defaultValue, description, restartRequirement);
    }
}
