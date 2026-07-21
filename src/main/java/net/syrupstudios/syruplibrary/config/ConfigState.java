package net.syrupstudios.syruplibrary.config;

record ConfigState(ConfigSnapshot configured, ConfigSnapshot effective, ConfigSnapshot startup) {
}
