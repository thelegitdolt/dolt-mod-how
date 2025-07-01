package com.dolthhaven.dolt_mod_how.common.block;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

public class WardenzolaFluid extends FluidType {
    public static final ResourceLocation WARDENZOLA_FLOWING_TEXTURE = DoltModHow
            .rl("block/wardenzola_flowing");
    public static final ResourceLocation WARDENZOLA_STILL_TEXTURE = DoltModHow
            .rl("block/wardenzola_still");

    public WardenzolaFluid() {
        super(Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            public ResourceLocation getStillTexture() {
                return WARDENZOLA_STILL_TEXTURE;
            }


            public ResourceLocation getFlowingTexture() {
                return WARDENZOLA_FLOWING_TEXTURE;
            }
        });
    }
}

