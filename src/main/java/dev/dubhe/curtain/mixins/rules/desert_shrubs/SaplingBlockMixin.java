package dev.dubhe.curtain.mixins.rules.desert_shrubs;

import dev.dubhe.curtain.CurtainRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlockMixin {
    @Inject(method = "advanceTree", at = @At(value = "INVOKE", shift = At.Shift.BEFORE,
            target = "Lnet/minecraft/world/level/block/grower/AbstractTreeGrower;growTree(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Ljava/util/Random;)Z"),
            cancellable = true)
    private void onGenerate(ServerLevel pLevel, BlockPos pPos, BlockState pState, Random pRand, CallbackInfo ci) {
        if (CurtainRules.desertShrubs && pLevel.getBiome(pPos).is(BiomeTags.HAS_DESERT_PYRAMID) && !nearWater(pLevel, pPos)) {
            pLevel.setBlock(pPos, Blocks.DEAD_BUSH.defaultBlockState(), Block.UPDATE_ALL);
            ci.cancel();
        }
    }

    private static boolean nearWater(LevelAccessor level, @NotNull BlockPos pos) {
        for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-4, -4, -4), pos.offset(4, 1, 4))) {
            if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                return true;
            }
        }

        return false;
    }
}
