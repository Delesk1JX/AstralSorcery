/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.item.tool;

import com.google.common.collect.Sets;
import hellfirepvp.astralsorcery.common.item.base.TypeEnchantableItem;
import hellfirepvp.astralsorcery.common.lib.CrystalPropertiesAS;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.item.enchantment.Enchantment;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ItemCrystalAxe
 * Created by HellFirePvP
 * Date: 17.08.2019 / 18:10
 */
public class ItemCrystalAxe extends ItemCrystalTierItem implements TypeEnchantableItem {

    public ItemCrystalAxe() {
        super(ToolType.AXE, new Properties(), Sets.newHashSet(Material.WOOD, Material.PLANTS, Material.TALL_PLANTS, Material.BAMBOO, Material.LEAVES));
    }

    @Override
    public void fillCreativeModeTab(CreativeModeTab group, NonNullList<ItemStack> stacks) {
        if (this.isInGroup(group)) {
            ItemStack stack = new ItemStack(this);
            CrystalPropertiesAS.CREATIVE_CRYSTAL_TOOL_ATTRIBUTES.store(stack);
            stacks.add(stack);
        }
    }

    @Override
    public boolean canEnchantItem(ItemStack stack, net.minecraft.world.item.enchantment.Enchantment type) {
        return type == net.minecraft.world.item.enchantment.Enchantment.BREAKABLE || type == net.minecraft.world.item.enchantment.Enchantment.DIGGER;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        net.minecraft.world.item.enchantment.Enchantment type = enchantment.type;
        return type == net.minecraft.world.item.enchantment.Enchantment.DIGGER || type == net.minecraft.world.item.enchantment.Enchantment.BREAKABLE;
    }

    @Override
    double getAttackDamage() {
        return 11;
    }

    @Override
    double getAttackSpeed() {
        return -3;
    }

    @Override
    protected boolean isToolEfficientAgainst(BlockState state) {
        return state.getMaterial() == Material.LEAVES;
    }

    @Override
    public InteractionResult onItemUse(ItemUseContext context) {
        Level world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = world.getBlockState(blockpos);
        BlockState block = blockstate.getToolModifiedState(world, blockpos, context.getPlayer(), context.getItem(), ToolType.AXE);
        if (block != null) {
            Player playerentity = context.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote()) {
                world.setBlockState(blockpos, block, 11);
                if (playerentity != null) {
                    context.getItem().damageItem(1, playerentity, (stack) ->
                            stack.sendBreakAnimation(context.getHand()));
                }
            }

            return InteractionResult.sidedSuccess(world.isClientSide());
        } else {
            return InteractionResult.CONSUME;
        }
    }
}
