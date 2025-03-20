package com.dolthhaven.dolt_mod_how.core.network;

import com.dolthhaven.dolt_mod_how.core.registry.DMHParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CRustScrapePacket {
    private final BlockPos pos;

    public S2CRustScrapePacket(BlockPos pos) {
        this.pos = pos;
    }

    public S2CRustScrapePacket(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        LocalPlayer player = Minecraft.getInstance().player;
        ParticleUtils.spawnParticlesOnBlockFaces(player.getCommandSenderWorld(), this.pos, DMHParticles.RUST_SCRAPE.get(), UniformInt.of(3, 5));
        contextSupplier.get().setPacketHandled(true);
    }
}
