package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.block.WardenzolaFluid;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import oshi.util.tuples.Quartet;
import umpaz.brewinandchewin.common.fluid.AlcoholFluidType;

import static umpaz.brewinandchewin.common.registry.BnCFluids.KOMBUCHA_FLUID_PROPERTIES;

public class DMHFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, DoltModHow.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, DoltModHow.MOD_ID);


    public static final RegistryObject<FluidType> TEQUILA_FLUID_TYPE = FLUID_TYPES.register("tequila_type", () -> new AlcoholFluidType(0x50cded));
    public static final RegistryObject<FlowingFluid> TEQUILA = FLUIDS.register("tequila", () -> new ForgeFlowingFluid.Source(DMHFluids.TEQUILA_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_TEQUILA = FLUIDS.register("flowing_tequila", () -> new ForgeFlowingFluid.Flowing(DMHFluids.TEQUILA_PROPERTIES));

    public static final ForgeFlowingFluid.Properties TEQUILA_PROPERTIES = new ForgeFlowingFluid.Properties(TEQUILA_FLUID_TYPE, TEQUILA, FLOWING_TEQUILA);


    public static final RegistryObject<FluidType> WARDENZOLA_FLUID_TYPE = FLUID_TYPES.register("wardenzola_fluid_type", WardenzolaFluid::new);
    public static final RegistryObject<FlowingFluid> WARDENZOLA = FLUIDS.register("wardenzola", () ->
            new ForgeFlowingFluid.Source(DMHFluids.WARDENZOLA_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_WARDENZOLA = FLUIDS.register("flowing_wardenzola", () ->
            new ForgeFlowingFluid.Flowing(DMHFluids.WARDENZOLA_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties WARDENZOLA_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties
            (WARDENZOLA_FLUID_TYPE, WARDENZOLA, FLOWING_WARDENZOLA);

//    public static Quartet<RegistryObject<FluidType>, RegistryObject<FlowingFluid>, RegistryObject<FlowingFluid>, ForgeFlowingFluid.Properties>
//        registerFluid(String name, int tint) {
//        RegistryObject<FluidType> type = FLUID_TYPES.register(name + "_type", () -> new AlcoholFluidType(tint));
//        final ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(type, source, flowing);
//        RegistryObject<FlowingFluid> source = FLUIDS.register(name, () -> new ForgeFlowingFluid.Source(properties));
//        RegistryObject<FlowingFluid>  flowing = FLUIDS.register("flowing_" + name, () -> new ForgeFlowingFluid.Flowing(properties));
//
//        return new Quartet<>(type, source, flowing, properties);
//    }
}
