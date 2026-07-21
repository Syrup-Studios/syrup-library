package net.syrupstudios.syruplibrary.config.value;

import net.syrupstudios.syruplibrary.config.ConfigSpec;
import net.syrupstudios.syruplibrary.config.RestartRequirement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Typed enum value serialized by stable lowercase constant name. */
public final class EnumConfigValue<E extends Enum<E>> extends ConfigValue<E> {
    private final Class<E> enumType;
    private final Map<String, E> serializedValues;

    public EnumConfigValue(ConfigSpec spec, String key, String path, Class<E> enumType, E defaultValue,
                           List<String> description, RestartRequirement restartRequirement) {
        super(spec, key, path, enumType, defaultValue, description, restartRequirement);
        this.enumType = enumType;
        Map<String, E> names = new LinkedHashMap<>();
        for (E constant : enumType.getEnumConstants()) {
            String serialized = serialize(constant);
            if (names.put(serialized, constant) != null) {
                throw new IllegalArgumentException("Duplicate serialized enum name " + serialized);
            }
        }
        this.serializedValues = Map.copyOf(names);
    }

    /** Returns the enum class. */
    public Class<E> enumType() { return enumType; }

    /** Returns the stable JSON5 representation of a constant. */
    public String serialize(E value) { return value.name().toLowerCase(Locale.ROOT); }

    public E parse(String value) { return serializedValues.get(value.toLowerCase(Locale.ROOT)); }
}
