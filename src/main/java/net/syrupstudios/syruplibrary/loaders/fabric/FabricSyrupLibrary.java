package net.syrupstudios.syruplibrary.loaders.fabric;

//? if fabric {
import net.fabricmc.api.ModInitializer;
import net.syrupstudios.syruplibrary.SyrupLibrary;

/** Fabric entrypoint for Syrup Library. */
public final class FabricSyrupLibrary implements ModInitializer {
    @Override
    public void onInitialize() {
        SyrupLibrary.initialize();
    }
}
//?}
