package net.syrupstudios.syruplibrary.config;

import java.util.List;

/** Typed bounded double configuration value. */
public final class DoubleConfigValue extends ConfigValue<Double> {
    private final double minimum;
    private final double maximum;

    DoubleConfigValue(ConfigSpec spec, String key, String path, double defaultValue, double minimum, double maximum,
                      List<String> description, RestartRequirement restartRequirement) {
        super(spec, key, path, Double.class, defaultValue, description, restartRequirement);
        if (!Double.isFinite(defaultValue) || !Double.isFinite(minimum) || !Double.isFinite(maximum)
                || minimum > maximum || defaultValue < minimum || defaultValue > maximum) {
            throw new IllegalArgumentException("Invalid range/default for " + path);
        }
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /** Inclusive minimum. */
    public double minimum() { return minimum; }

    /** Inclusive maximum. */
    public double maximum() { return maximum; }
}
