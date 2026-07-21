package net.syrupstudios.syruplibrary.config;

/** Describes whether a setting can affect the running game immediately. */
public enum RestartRequirement {
    /** Reloading changes the effective value immediately. */
    NONE,
    /** Reloading records the configured value, but the startup value remains effective. */
    REQUIRED
}
