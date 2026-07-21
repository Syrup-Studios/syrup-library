package net.syrupstudios.syruplibrary.config.value;

import net.syrupstudios.syruplibrary.config.ConfigSpec;
import net.syrupstudios.syruplibrary.config.RestartRequirement;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/** Typed string configuration value with optional validation. */
public final class StringConfigValue extends ConfigValue<String> {
    private final Predicate<String> validator;
    private final String validationMessage;

    public StringConfigValue(ConfigSpec spec, String key, String path, String defaultValue, List<String> description,
                             RestartRequirement restartRequirement, Predicate<String> validator,
                             String validationMessage) {
        super(spec, key, path, String.class, defaultValue, description, restartRequirement);
        this.validator = Objects.requireNonNull(validator, "validator");
        this.validationMessage = Objects.requireNonNull(validationMessage, "validationMessage");
        if (!validator.test(defaultValue)) {
            throw new IllegalArgumentException("Default value fails validation for " + path);
        }
    }

    public boolean isValid(String value) { return validator.test(value); }

    public String validationMessage() { return validationMessage; }
}
