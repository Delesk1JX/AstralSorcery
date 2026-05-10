/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.crafting.custom.RecipeDyeableChangeColor;
import hellfirepvp.astralsorcery.common.crafting.serializer.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;

import static hellfirepvp.astralsorcery.common.lib.RecipeSerializersAS.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryRecipeSerializers
 * Created by HellFirePvP
 * Date: 30.06.2019 / 23:32
 */
public class RegistryRecipeSerializers {

    private RegistryRecipeSerializers() {}

    public static void init() {
        WELL_LIQUEFACTION_SERIALIZER = ASRegistries.RECIPE_SERIALIZERS.register("well_liquefaction", WellRecipeSerializer::new);
        LIQUID_INFUSION_SERIALIZER = ASRegistries.RECIPE_SERIALIZERS.register("liquid_infusion", LiquidInfusionSerializer::new);
        BLOCK_TRANSMUTATION_SERIALIZER = ASRegistries.RECIPE_SERIALIZERS.register("block_transmutation", BlockTransmutationSerializer::new);
        ALTAR_RECIPE_SERIALIZER = ASRegistries.RECIPE_SERIALIZERS.register("altar_recipe", SimpleAltarRecipeSerializer::new);
        LIQUID_INTERACTION_SERIALIZER = ASRegistries.RECIPE_SERIALIZERS.register("liquid_interaction", LiquidInteractionSerializer::new);

        CUSTOM_CHANGE_WAND_COLOR_SERIALIZER = ASRegistries.RECIPE_SERIALIZERS.register("change_wand_color", RecipeDyeableChangeColor.IlluminationWandColorSerializer::new);
        CUSTOM_CHANGE_GATEWAY_COLOR_SERIALIZER = ASRegistries.RECIPE_SERIALIZERS.register("change_gateway_color", RecipeDyeableChangeColor.CelestialGatewayColorSerializer::new);
    }
}
