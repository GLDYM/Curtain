package dev.dubhe.curtain.mixins;

import com.mojang.authlib.GameProfile;
import dev.dubhe.curtain.fakes.ServerPlayerInterface;
import dev.dubhe.curtain.helpers.EntityPlayerActionPack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayer_actionPackMixin implements ServerPlayerInterface
{
    @Unique
    public EntityPlayerActionPack actionPack;
    @Override
    public EntityPlayerActionPack getActionPack()
    {
        return actionPack;
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void onServerPlayerEntityContructor(MinecraftServer p_215088_, ServerLevel p_215089_, GameProfile p_215090_, ProfilePublicKey p_215091_, CallbackInfo ci)
    {
        this.actionPack = new EntityPlayerActionPack((ServerPlayer) (Object) this);
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void onTick(CallbackInfo ci)
    {
        actionPack.onUpdate();
    }
}