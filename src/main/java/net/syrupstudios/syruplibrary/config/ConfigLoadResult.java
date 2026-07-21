package net.syrupstudios.syruplibrary.config;

import java.util.List;

/**
 * Result of an initial load or reload.
 *
 * @param successful whether the complete document was accepted and published
 * @param issues immutable diagnostics collected during loading
 * @param cause document-level failure cause, or {@code null}
 */
public record ConfigLoadResult(boolean successful, List<ConfigIssue> issues, Throwable cause) {
    public ConfigLoadResult {
        issues = List.copyOf(issues);
    }

    /** Returns whether at least one warning was reported. */
    public boolean hasWarnings() {
        return issues.stream().anyMatch(issue -> issue.severity() == ConfigIssueSeverity.WARNING);
    }

    /** Returns whether at least one error was reported. */
    public boolean hasErrors() {
        return issues.stream().anyMatch(issue -> issue.severity() == ConfigIssueSeverity.ERROR);
    }
}
