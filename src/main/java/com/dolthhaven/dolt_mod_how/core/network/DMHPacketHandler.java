package com.dolthhaven.dolt_mod_how.core.network;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class DMHPacketHandler {
    public static SimpleChannel CHANNEL;
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        CHANNEL = NetworkRegistry.ChannelBuilder.named(DoltModHow.rl("channel"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
    }
}
