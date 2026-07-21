package net.syrupstudios.syruplibrary;

import net.fabricmc.api.ModInitializer;
import net.syrupstudios.syruplibrary.config.SyrupConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Fabric entrypoint for Syrup Library. */
public final class SyrupLibrary implements ModInitializer {
    public static final String MOD_ID = "syrup_library";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        SyrupConfigManager.getInstance();
        LOGGER.info("Syrup Library initialized");
    }
}
