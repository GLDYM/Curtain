package dev.dubhe.curtain.mixins.Rule_fillUpdates;

import dev.dubhe.curtain.CurtainRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SetBlockCommand.class)
public class SetBlockCommandMixin {
    @Redirect(
            method = "setBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;blockUpdated(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V"
            )
    )
    private static void conditionalUpdating(ServerLevel serverWorld, BlockPos blockPos, Block block) {
        if (CurtainRules.fillUpdates) {
            serverWorld.blockUpdated(blockPos, block);
        }
    }
}
