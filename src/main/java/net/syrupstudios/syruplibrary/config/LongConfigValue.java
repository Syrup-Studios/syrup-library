package net.syrupstudios.syruplibrary.config;

import java.util.List;

/** Typed bounded long configuration value. */
public final class LongConfigValue extends ConfigValue<Long> {
    private final long minimum;
    private final long maximum;

    LongConfigValue(ConfigSpec spec, String key, String path, long defaultValue, long minimum, long maximum,
                    List<String> description, RestartRequirement restartRequirement) {
        super(spec, key, path, Long.class, defaultValue, description, restartRequirement);
        if (minimum > maximum || defaultValue < minimum || defaultValue > maximum) {
            throw new IllegalArgumentException("Invalid range/default for " + path);
        }
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /** Inclusive minimum. */
    public long minimum() { return minimum; }

    /** Inclusive maximum. */
    public long maximum() { return maximum; }
}
