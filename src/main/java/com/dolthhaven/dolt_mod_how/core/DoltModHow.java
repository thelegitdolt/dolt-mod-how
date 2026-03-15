package com.dolthhaven.dolt_mod_how.core;

import com.dolthhaven.dolt_mod_how.client.other.DMHClientCompat;
import com.dolthhaven.dolt_mod_how.core.network.DMHPacketHandler;
import com.dolthhaven.dolt_mod_how.core.other.DMHTrackedData;
import com.dolthhaven.dolt_mod_how.core.other.DoltModHowDataUtil;
import com.dolthhaven.dolt_mod_how.core.other.dispensers.DoltModHowDispensers;
import com.dolthhaven.dolt_mod_how.core.registry.*;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.data.DMHAdvancements;
import com.dolthhaven.dolt_mod_how.data.DMHRecipes;
import com.dolthhaven.dolt_mod_how.data.DoltModHowLootTables;
import com.dolthhaven.dolt_mod_how.data.client.DMHBlockStatesGen;
import com.dolthhaven.dolt_mod_how.data.client.DMHItemModelsGen;
import com.dolthhaven.dolt_mod_how.data.tag.DMHBlockTags;
import com.dolthhaven.dolt_mod_how.data.tag.DMHEntityTags;
import com.dolthhaven.dolt_mod_how.data.tag.DMHItemTags;
import com.dolthhaven.dolt_mod_how.data.tag.DMHMobEffectTags;
import com.dolthhaven.dolt_mod_how.integration.DoltModHowFishBarrelSetup;
import com.mojang.logging.LogUtils;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(DoltModHow.MOD_ID)
public class DoltModHow {
    public static final String MOD_ID = "dolt_mod_how";
    public static final String GIT_URL = "https://github.com/thelegitdolt/dolt-mod-how";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);


    public DoltModHow() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext context = ModLoadingContext.get();
        context.registerConfig(ModConfig.Type.COMMON, DMHConfig.COMMON_SPEC);
        context.registerConfig(ModConfig.Type.CLIENT, DMHConfig.CLIENT_SPEC);

        bus.addListener(this::dataSetup);
        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);

        DMHEnchants.ENCHANTMENTS.register(bus);
        DMHRecipeSerializer.RECIPE_SERIALIZERS.register(bus);
        DMHParticles.PARTICLES.register(bus);
        DMHLoot.LOOT_CONDITIONS.register(bus);
        DMHLoot.LOOT_MODIFIERS.register(bus);
        DMHFluids.FLUID_TYPES.register(bus);
        DMHFluids.FLUIDS.register(bus);

        DMHMobEffects.MOB_EFFECTS.register(bus);
        DMHMobEffects.POTIONS.register(bus);

        REGISTRY_HELPER.register(bus);

        DMHTrackedData.registerTrackedData();
        
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> DMHItems::setUpTabEditors);

        MinecraftForge.EVENT_BUS.register(this);

        ForgeMod.enableMilkFluid();
    }

    private void dataSetup(GatherDataEvent event) {
        if (!DMHUtils.Constants.runData()) {
            return;
        }

        DataGenerator dataGen = event.getGenerator();
        PackOutput packOutput = dataGen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean includeServer = event.includeServer();

        DMHBlockTags taggies = new DMHBlockTags(packOutput, provider, helper);
        dataGen.addProvider(includeServer, taggies);
        dataGen.addProvider(includeServer, new DMHItemTags(event, taggies));
        dataGen.addProvider(includeServer, DMHAdvancements.create(event));
        dataGen.addProvider(includeServer, new DMHEntityTags(event));
        dataGen.addProvider(includeServer, new DMHMobEffectTags(event));
        dataGen.addProvider(includeServer, new DoltModHowLootTables(packOutput));
        dataGen.addProvider(includeServer, new DMHRecipes(packOutput));

        boolean includeClient = event.includeClient();
        dataGen.addProvider(includeClient, new DMHBlockStatesGen(event));
        dataGen.addProvider(includeClient, new DMHItemModelsGen(event));
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DoltModHowDispensers.registerDispenseBehavior();
            DoltModHowDataUtil.registerData();
            DMHPacketHandler.register();
        });

        if (ModList.get().isLoaded("fish_in_planks")) {
            event.enqueueWork(DoltModHowFishBarrelSetup::commonSetup);
        }
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(DMHClientCompat::doCompat);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
