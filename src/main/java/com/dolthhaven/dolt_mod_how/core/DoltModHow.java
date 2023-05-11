package com.dolthhaven.dolt_mod_how.core;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DoltModHow.MOD_ID)
public class DoltModHow
{
    public static final String MOD_ID = "dolt_mod_how";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DoltModHow() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
