/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2022
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.collision;

import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.shapes.IBooleanFunction;
import net.minecraft.util.shapes.VoxelShape;
import net.minecraft.util.shapes.VoxelShapeSpliterator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: CollisionHelper
 * Created by HellFirePvP
 * Date: 19.12.2020 / 10:00
 */
public class CollisionHelper {

    public static boolean onCollision(VoxelShapeSpliterator iterator, Consumer<? super VoxelShape> action) {
        if (!CollisionManager.needsCustomCollision(iterator.entity)) {
            return false;
        }
        AxisAlignedBB box = CollisionManager.getIteratorBoundingBoxes(iterator, iterator.entity);
        if (box == null) {
            return false;
        }

        VoxelShape floor = VoxelShapes.create(box);
        if (VoxelShapes.compare(floor, VoxelShapes.create(iterator.aabb.grow(1.0E-7D)), IBooleanFunction.AND)) {
            action.accept(floor);
            return true;
        }
        return false;
    }

    @Nullable
    public static Vector3d onEntityCollision(Vector3d allowedMovement, Entity entity) {
        if (!CollisionManager.needsCustomCollision(entity)) {
            return null;
        }
        List<AxisAlignedBB> additionalBoxes = CollisionManager.getAdditionalBoundingBoxes(entity);
        AxisAlignedBB entityBox = entity.getBoundingBox().grow(1.0E-7D);
        for (AxisAlignedBB box : additionalBoxes) {
            double newYMovement = VoxelShapes.create(box).getAllowedOffset(Direction.Axis.Y, entityBox, allowedMovement.y);
            allowedMovement = new Vector3d(allowedMovement.x, newYMovement, allowedMovement.z);
        }

        return allowedMovement;
    }
}
