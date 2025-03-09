package com.dolthhaven.dolt_mod_how.core.other.dispensers;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class DyeSheepDispenseBehavior {
    private static final HashMap<DyeColor, DispenseItemBehavior> DYE_MAP = new HashMap<>();

    static {
        for (DyeColor dye : DyeColor.values()) {
            DYE_MAP.put(dye, DispenserBlock.DISPENSER_REGISTRY.get(getDye(dye.getName())));
        }
    }

    public static void registerSheepDispensers() {
        for (DyeColor dye : DyeColor.values()) {
            Item dyeItem = getDye(dye.getName());
            if (dyeItem == null) {
                continue;
            }

            DispenserBlock.registerBehavior(dyeItem, dispenserByDye(dye));
        }
    }

    private static DispenseItemBehavior dispenserByDye(DyeColor dye) {
        return new OptionalDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                BlockPos affectPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                List<Sheep> sheeps = level.getEntitiesOfClass(Sheep.class, new AABB(affectPos), (sheepie) -> !sheepie.getColor().equals(dye));
                if (!sheeps.isEmpty()) {
                    for (Sheep sheep : sheeps) {
                        this.setSuccess(true);
                        stack.shrink(1);
                        sheep.setColor(dye);
                        return stack;
                    }
                }
                return DYE_MAP.get(dye).dispense(source, stack);
            }
        };
    }

    private static Item getDye(String dye) {
        Item item = DMHUtils.getPotentialItem("minecraft", dye + "_dye");
        if (item == Items.AIR || item == null) {
            item = DMHUtils.getPotentialItem("dye_depot", dye + "_dye");
            if (item == Items.AIR || item == null) {
                return null;
            } else return item;
        } else return item;
    }
}
