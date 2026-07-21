package net.syrupstudios.syruplibrary.config;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/** Typed string configuration value with optional validation. */
public final class StringConfigValue extends ConfigValue<String> {
    private final Predicate<String> validator;
    private final String validationMessage;

    StringConfigValue(ConfigSpec spec, String key, String path, String defaultValue, List<String> description,
                      RestartRequirement restartRequirement, Predicate<String> validator, String validationMessage) {
        super(spec, key, path, String.class, defaultValue, description, restartRequirement);
        this.validator = Objects.requireNonNull(validator, "validator");
        this.validationMessage = Objects.requireNonNull(validationMessage, "validationMessage");
        if (!validator.test(defaultValue)) {
            throw new IllegalArgumentException("Default value fails validation for " + path);
        }
    }

    boolean isValid(String value) { return validator.test(value); }

    String validationMessage() { return validationMessage; }
}
