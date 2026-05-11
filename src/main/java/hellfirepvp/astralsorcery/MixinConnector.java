package hellfirepvp.astralsorcery;

import org.spongepowered.asm.mixin.MixinEnvironment;

/**
 * Connector for Mixin functionality in NeoForge
 * Initializes mixin configuration on mod startup
 */
public class MixinConnector {

    public MixinConnector() {
        AstralSorcery.log.info("Initializing AstralSorcery Mixins...");
        // Mixins are automatically initialized by NeoForge
        // This class exists as a connector for potential future mixin-related setup
    }
}
