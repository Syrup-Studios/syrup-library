package net.syrupstudios.syruplibrary.config;

import java.util.List;

/** Typed bounded integer configuration value. */
public final class IntConfigValue extends ConfigValue<Integer> {
    private final int minimum;
    private final int maximum;

    IntConfigValue(ConfigSpec spec, String key, String path, int defaultValue, int minimum, int maximum,
                   List<String> description, RestartRequirement restartRequirement) {
        super(spec, key, path, Integer.class, defaultValue, description, restartRequirement);
        if (minimum > maximum || defaultValue < minimum || defaultValue > maximum) {
            throw new IllegalArgumentException("Invalid range/default for " + path);
        }
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /** Inclusive minimum. */
    public int minimum() { return minimum; }

    /** Inclusive maximum. */
    public int maximum() { return maximum; }
}
