package com.dolthhaven.dolt_mod_how.core.other.dispensers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlintAndSteelExplodeCreepersDispenseBehavior {
    public static void registerFlintAndSteelExplodeCreepers() {
//        if (!DoltCompatConfig.Common.COMMON.DoExplodeCreepers.get())
//            return;

        DispenseItemBehavior flintAndSteel = DispenserBlock.DISPENSER_REGISTRY.get(Items.FLINT_AND_STEEL);

        DispenserBlock.registerBehavior(Items.FLINT_AND_STEEL, new OptionalDispenseItemBehavior() {
            @Override
            public @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                BlockPos affectPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                List<Creeper> creepies = level.getEntitiesOfClass(Creeper.class, new AABB(affectPos), EntitySelector.NO_SPECTATORS);
                if (!creepies.isEmpty()) {
                    for (Creeper creepie : creepies) {
                        stack.hurt(1, level.getRandom(), null);
                        creepie.ignite();
                        level.playSound(null, affectPos.getX(), affectPos.getY(), affectPos.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);

                        this.setSuccess(true);
                        return stack;
                    }
                }

                return flintAndSteel.dispense(source, stack);
            }});

    }
}
