package net.syrupstudios.syruplibrary.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class DefaultJson5Writer {
    private DefaultJson5Writer() {
    }

    static boolean createIfMissing(ConfigSpec spec, Path path) throws IOException {
        if (Files.exists(path)) {
            return false;
        }
        Path parent = path.getParent();
        Files.createDirectories(parent);
        Path temporary = Files.createTempFile(parent, "." + spec.id() + "-", ".tmp");
        try {
            Files.writeString(temporary, render(spec), StandardCharsets.UTF_8);
            try {
                Files.move(temporary, path, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException exception) {
                Files.move(temporary, path);
            }
            return true;
        } catch (FileAlreadyExistsException exception) {
            return false;
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    static String render(ConfigSpec spec) {
        StringBuilder output = new StringBuilder();
        if (!spec.header().isEmpty()) {
            blockComment(output, 0, spec.header());
        }
        output.append("{\n");
        renderChildren(output, spec.root(), 1);
        output.append("}\n");
        return output.toString();
    }

    private static void renderChildren(StringBuilder output, SchemaNode parent, int depth) {
        Iterator<SchemaNode> iterator = parent.children.values().iterator();
        while (iterator.hasNext()) {
            SchemaNode child = iterator.next();
            if (child.value == null) {
                sectionComment(output, depth, child.description);
                indent(output, depth).append(child.key).append(": {\n");
                renderChildren(output, child, depth + 1);
                indent(output, depth).append('}');
            } else {
                valueComment(output, depth, child.value);
                indent(output, depth).append(child.key).append(": ")
                        .append(renderValue(child.value.defaultValue()));
            }
            if (iterator.hasNext()) {
                output.append(',');
            }
            output.append("\n");
            if (iterator.hasNext()) {
                output.append("\n");
            }
        }
    }

    private static void sectionComment(StringBuilder output, int depth, List<String> lines) {
        if (lines.isEmpty()) {
            return;
        }
        if (lines.size() == 1) {
            indent(output, depth).append("// ").append(sanitizeComment(lines.get(0))).append('\n');
        } else {
            blockComment(output, depth, lines);
        }
    }

    private static void valueComment(StringBuilder output, int depth, ConfigValue<?> value) {
        List<String> lines = new ArrayList<>(value.description());
        if (value.restartRequirement() == RestartRequirement.REQUIRED) {
            lines.add("Requires a server restart.");
        }
        String metadata = "Default: " + renderValue(value.defaultValue());
        if (value instanceof IntConfigValue integer) {
            metadata += " | Range: " + integer.minimum() + " ~ " + integer.maximum();
        } else if (value instanceof LongConfigValue longValue) {
            metadata += " | Range: " + longValue.minimum() + " ~ " + longValue.maximum();
        } else if (value instanceof DoubleConfigValue doubleValue) {
            metadata += " | Range: " + doubleValue.minimum() + " ~ " + doubleValue.maximum();
        }
        lines.add(metadata);
        blockComment(output, depth, lines);
    }

    private static void blockComment(StringBuilder output, int depth, List<String> lines) {
        indent(output, depth).append("/*\n");
        for (String line : lines) {
            indent(output, depth).append(" * ").append(sanitizeComment(line)).append('\n');
        }
        indent(output, depth).append(" */\n");
    }

    private static String sanitizeComment(String value) {
        return value.replace("*/", "* /");
    }

    private static String renderValue(Object value) {
        if (value instanceof String string) {
            return quote(string);
        }
        if (value instanceof Enum<?> enumValue) {
            return quote(enumValue.name().toLowerCase(java.util.Locale.ROOT));
        }
        if (value instanceof List<?> list) {
            return list.stream().map(item -> quote((String) item))
                    .collect(java.util.stream.Collectors.joining(", ", "[", "]"));
        }
        return String.valueOf(value);
    }

    private static String quote(String value) {
        StringBuilder escaped = new StringBuilder("\"");
        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            switch (character) {
                case '\\' -> escaped.append("\\\\");
                case '"' -> escaped.append("\\\"");
                case '\n' -> escaped.append("\\n");
                case '\r' -> escaped.append("\\r");
                case '\t' -> escaped.append("\\t");
                case '\b' -> escaped.append("\\b");
                case '\f' -> escaped.append("\\f");
                default -> {
                    if (character < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) character));
                    } else {
                        escaped.append(character);
                    }
                }
            }
        }
        return escaped.append('"').toString();
    }

    private static StringBuilder indent(StringBuilder output, int depth) {
        return output.append("  ".repeat(depth));
    }
}
