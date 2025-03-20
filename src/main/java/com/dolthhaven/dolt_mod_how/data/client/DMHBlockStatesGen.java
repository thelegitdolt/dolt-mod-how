package com.dolthhaven.dolt_mod_how.data.client;

import com.bobmowzie.mowziesmobs.server.block.RakedSandBlock;
import com.davigj.blasted_barrens.core.registry.BBBlocks;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;

public class DMHBlockStatesGen extends BlueprintBlockStateProvider {
    public DMHBlockStatesGen(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), DoltModHow.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {
        customPot(POTTED_ARID_SPROUTS, POTTED_MYCELIUM_SPROUTS, POTTED_BEACHGRASS,
                POTTED_TALL_BEACHGRASS, POTTED_STRAWBERRIES, POTTED_WHITE_STRAWBERRIES, POTTED_ONION, POTTED_TOMATOES, POTTED_CABBAGE);

        rakedSand(ASHEN_RAKED_SAND, BBBlocks.ASHEN_SAND);
        rakedSand(ARID_RAKED_SAND, AtmosphericBlocks.ARID_SAND);
        rakedSand(RED_ARID_RAKED_SAND, AtmosphericBlocks.RED_ARID_SAND);
    }


    @SafeVarargs
    private void customPot(RegistryObject<? extends Block>... pots) {
        for (RegistryObject<? extends Block> pot : pots) {
            ResourceLocation potTexture = DoltModHow.rl("block/" + name(pot.get()));
            this.simpleBlock(pot.get(), models()
                    .singleTexture(name(pot.get()), new ResourceLocation("block/flower_pot_cross"), "plant", potTexture).renderType("cutout"));
        }
    }

    private void rakedSand(RegistryObject<? extends Block> sand, RegistryObject<? extends Block> nonRaked) {
        String cubeTop = "block/cube_top";
        this.getVariantBuilder(sand.get()).forAllStates(state -> {
            Function<String, ConfiguredModel.Builder<?>> sandModelFunction = sandType -> {
                String name = name(sand.get()) + (sandType.isEmpty() ? "" : "_" + sandType.replace("_", ""));
                return ConfiguredModel.builder().modelFile(this.models().withExistingParent("block/" + name, cubeTop)
                        .texture("side", blockTexture(nonRaked.get()))
                        .texture("top", mapPath(loc(sand), str -> "block/%s%s".formatted(name(sand.get()),
                                sandType.isEmpty() ? "" :  "_" + sandType.replace("_", "")))));
            };

            Function<String, Integer> getFacingValue = str -> switch (str) {
                case "north_east" ->  270;
                case "north_west" ->  180;
                case "south_east" ->  0;
                case "south_west" ->  90;
                default -> throw new IllegalStateException("Unexpected value: " + str);
            };

            String shape = state.getValue(RakedSandBlock.SHAPE).getName();
            String[] shapeName = shape.split("_");

            if (Objects.equals(shapeName[0], "north") && Objects.equals(shapeName[1], "south")) {
                return sandModelFunction.apply("").build();
            }
            else if (Objects.equals(shapeName[0], "east") && Objects.equals(shapeName[1], "west")) {
                return sandModelFunction.apply("").rotationY(90).build();
            }
            else {
                int rotationAmount = getFacingValue.apply(shape);
                return sandModelFunction.apply(shape).rotationY(rotationAmount).build();
            }
        });

        this.blockItem(sand.get());
    }

    private ResourceLocation mapPath(ResourceLocation location, Function<String, String> mapper) {
        return new ResourceLocation(location.getNamespace(), mapper.apply(location.getPath()));
    }

    private ResourceLocation loc(Block block) {
        return block.builtInRegistryHolder().key().location();
    }

    private ResourceLocation loc(Supplier<? extends Block> block) {
        return loc(block.get());
    }
}
