package com.endchants.enchantment.effect.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class LuminaryEffect {
    public static void apply(ServerLevel serverLevel, BlockPos blockPos, Player player) {

        if (Math.random() < .1) {

            Direction dir = Direction.fromYRot(player.getYHeadRot());
            if (tryPlaceWallTorch(serverLevel, blockPos.relative(dir.getCounterClockWise()),
                    dir.getClockWise())
                    || tryPlaceWallTorch(serverLevel, blockPos.relative(dir.getClockWise()),
                            dir.getCounterClockWise())) {
            }

        }

    }

    public static boolean tryPlaceWallTorch(ServerLevel level, BlockPos basePos, Direction facingDirection) {
        // Wall torches cannot be placed on UP or DOWN faces
        if (facingDirection.getAxis() == Direction.Axis.Y) {
            return false;
        }

        // The position where the wall torch block would occupy
        // (which is adjacent to the base block in the direction of the wall)
        BlockPos torchPos = basePos.relative(facingDirection);
        // Wall torch's FACING property points *away* from the wall it's attached to.
        // For example, if placed on the North side of a block, it faces South.
        BlockState wallTorchState = Blocks.WALL_TORCH.defaultBlockState()
                .setValue(WallTorchBlock.FACING, facingDirection);

        if (wallTorchState.canSurvive(level, torchPos)) {
            return level.setBlock(torchPos, wallTorchState, 3);

        }
        return false;
    }
}
