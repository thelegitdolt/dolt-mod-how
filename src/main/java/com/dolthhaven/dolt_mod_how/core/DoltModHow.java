package com.dolthhaven.dolt_mod_how.core;

import com.dolthhaven.dolt_mod_how.core.compat.DoltModHowCommonSetup;
import com.dolthhaven.dolt_mod_how.core.data.DMHRecipes;
import com.dolthhaven.dolt_mod_how.core.data.tag.DoltModHowBlockTags;
import com.dolthhaven.dolt_mod_how.core.data.tag.DoltModHowLootTable;
import com.dolthhaven.dolt_mod_how.core.registry.DMHEnchants;
import com.dolthhaven.dolt_mod_how.core.registry.DMHRecipeSerializer;
import com.mojang.logging.LogUtils;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DoltModHow.MOD_ID)
public class DoltModHow {
    public static final String MOD_ID = "dolt_mod_how";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);


    public DoltModHow() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::dataSetup);
        bus.addListener(this::commonSetup);

        DMHEnchants.ENCHANTMENTS.register(bus);
        DMHRecipeSerializer.RECIPE_SERIALIZERS.register(bus);
        REGISTRY_HELPER.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }
    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper EFH = event.getExistingFileHelper();
        boolean includeServer = event.includeServer();
        DoltModHowBlockTags taggies = new DoltModHowBlockTags(generator, EFH);
        generator.addProvider(includeServer, taggies);
        generator.addProvider(includeServer, new DoltModHowLootTable(generator));
        generator.addProvider(includeServer, new DMHRecipes(generator));
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(DoltModHowCommonSetup::commonSetup);
    }
}
