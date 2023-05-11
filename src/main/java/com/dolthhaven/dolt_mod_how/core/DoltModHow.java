package com.dolthhaven.dolt_mod_how.core;

import com.dolthhaven.dolt_mod_how.core.data.tag.DoltModHowBlockTags;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
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
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::dataSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper EFH = event.getExistingFileHelper();
        boolean includeServer = event.includeServer();

        DoltModHowBlockTags taggies = new DoltModHowBlockTags(generator, EFH);
        generator.addProvider(includeServer, taggies);
    }
}
