package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.PINE_NUTS_CRATE;
import static com.dolthhaven.dolt_mod_how.core.registry.DMHItems.GLOWSHROOM_COLONY;

public class DoltModHowDataUtil {
    public static final Object2FloatMap<EntityType<?>> COMPOSTABLE_ENTITIES = new Object2FloatOpenHashMap<>();

    public static void registerData() {
        registerCompostable();
        registerFluidInteractions();
    }

    public static void registerFluidInteractions() {
        if (BlockSubRegistryHelper.areModsLoaded(Util.Constants.OREGANIZED, Util.Constants.ALEXS_CAVES)) {
            Fluid lead = Util.getFluidOrWater(Util.Constants.MOLTEN_LEAD);
            Fluid purpleSoda = Util.getFluidOrWater(Util.Constants.PURPLE_SODA);
            Fluid acid = Util.getFluidOrWater(Util.Constants.ACID);

            Block blackCandy = Util.getPotentialBlock(Util.Constants.BLACK_ROCK_CANDY);
            Block galena = Util.getPotentialBlock(Util.Constants.GALENA);

            registerMoltenLeadInteraction(lead, purpleSoda, blackCandy);
            registerMoltenLeadInteraction(lead, acid, galena);
        }
    }

    private static void registerMoltenLeadInteraction(Fluid lead, Fluid fluid, Block block) {
        if (fluid != null && block != null) {
            FluidInteractionRegistry.addInteraction(lead.getFluidType(),
                new FluidInteractionRegistry.InteractionInformation(
                        (level, pos, relativePos, fluidState) -> level.getFluidState(relativePos).is(fluid) && fluidState.isSource(),
                        (fluidState) -> block.defaultBlockState()));
        }
    }

    private static void registerCompostable() {
        DataUtil.registerCompostable(GLOWSHROOM_COLONY.get(), 1.0f);
        DataUtil.registerCompostable(PINE_NUTS_CRATE.get(), 1.0f);

        COMPOSTABLE_ENTITIES.defaultReturnValue(-1.0f);

        if (ModList.get().isLoaded(Util.Constants.NEAPOLITAN)) {
            EntityType<?> bananaPeel = ForgeRegistries.ENTITY_TYPES.getValue(Util.Constants.BANANA_PEEL);

            COMPOSTABLE_ENTITIES.put(bananaPeel, 0.5f);
        }
    }
}
