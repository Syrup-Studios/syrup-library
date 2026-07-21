package net.syrupstudios.syruplibrary.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class SchemaNode {
    final String key;
    final String path;
    final List<String> description;
    final Map<String, SchemaNode> children = new LinkedHashMap<>();
    ConfigValue<?> value;

    SchemaNode(String key, String path, List<String> description) {
        this.key = key;
        this.path = path;
        this.description = List.copyOf(description);
    }
}
