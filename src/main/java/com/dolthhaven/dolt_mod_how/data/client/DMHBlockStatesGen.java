package com.dolthhaven.dolt_mod_how.data.client;

import com.bobmowzie.mowziesmobs.server.block.RakedSandBlock;
import com.davigj.blasted_barrens.core.registry.BBBlocks;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.CabinetBlock;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

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
        leafPileBlock(ACBlockRegistry.ANCIENT_LEAVES, ANCIENT_LEAF_PILE);

        chiseledBookshelfBlock(CHISELED_PEWEN_BOOKSHELF);
        chiseledBookshelfBlock(CHISELED_THORNWOOD_BOOKSHELF);
        cabinetBlock(PEWEN_CABINET.get());
        cabinetBlock(THORNWOOD_CABINET.get());
        stupidWoodworksBlocks("pewen", ACBlockRegistry.PEWEN_PLANKS, PEWEN_BOARDS, PEWEN_LADDER, PEWEN_BOOKSHELF, PEWEN_BEEHIVE, PEWEN_CHEST, TRAPPED_PEWEN_CHEST);
        stupidWoodworksBlocks("thornwood", ACBlockRegistry.THORNWOOD_PLANKS, THORNWOOD_BOARDS, THORNWOOD_LADDER, THORNWOOD_BOOKSHELF, THORNWOOD_BEEHIVE, THORNWOOD_CHEST, TRAPPED_THORNWOOD_CHEST);

        block(ZINC_BRICKS);
        block(WAX_BLOCK);
        block(CHISELED_ZINC_BRICKS);
        wallBlock(ZINC_BRICKS.get(), ZINC_BRICK_WALL.get());
        stairsBlock(ZINC_BRICKS.get(), ZINC_BRICK_STAIRS.get());
        slabBlock(ZINC_BRICKS.get(), ZINC_BRICK_SLAB.get());
        directionalBlock(MULCH_BAG);
    }


    @SafeVarargs
    private void customPot(RegistryObject<? extends Block>... pots) {
        for (RegistryObject<? extends Block> pot : pots) {
            ResourceLocation potTexture = DoltModHow.rl("block/" + name(pot.get()));
            this.simpleBlock(pot.get(), models()
                    .singleTexture(name(pot.get()), new ResourceLocation("block/flower_pot_cross"), "plant", potTexture).renderType("cutout"));
        }
    }

    public void cabinetBlock(Block block) {
        this.horizontalBlock(block, (state) -> {
            String suffix = state.getValue(CabinetBlock.OPEN) ? "_open" : "";
            return this.models().orientable(name(block) + suffix,
                    after(block,"_side"),
                    after(block, "_front" + suffix),
                    after(block, "_top"));
        });

        this.blockItem(block);
    }

    private void rakedSand(RegistryObject<? extends Block> sand, RegistryObject<? extends Block> nonRaked) {
        String cubeTop = "block/cube_top";
        this.getVariantBuilder(sand.get()).forAllStates(state -> {
            Function<String, ConfiguredModel.Builder<?>> sandModelFunction = sandType -> {
                String name = name(sand.get()) + (sandType.isEmpty() ? "" : "_" + sandType.replace("_", ""));
                return ConfiguredModel.builder().modelFile(this.models().withExistingParent("block/" + name, cubeTop)
                        .texture("side", blockTexture(nonRaked.get()))
                        .texture("top", loc(sand).withPath(str -> "block/%s%s".formatted(name(sand.get()),
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


    private ResourceLocation loc(Block block) {
        return block.builtInRegistryHolder().key().location();
    }

    private ResourceLocation blockTexture(Block block, UnaryOperator<String> mapper) {
        return loc(block).withPath(str -> "block/" + mapper.apply(str));
    }

    private ResourceLocation blockTexture(RegistryObject<? extends Block> block, UnaryOperator<String> mapper) {
        return blockTexture(block.get(), mapper);
    }

    private ResourceLocation after(Block block, String string) {
        return blockTexture(block, str -> str + string);
    }

    private ResourceLocation loc(Supplier<? extends Block> block) {
        return loc(block.get());
    }

    public void stupidWoodworksBlocks(String type, RegistryObject<Block> planks, RegistryObject<Block> boards, RegistryObject<Block> ladder, RegistryObject<Block> bookshelf, RegistryObject<Block> beehive, RegistryObject<? extends Block> chest, RegistryObject<? extends Block> trappedChest) {
        this.boardsBlock(boards);
        this.ladderBlock(ladder);
        this.beehiveBlock(beehive);
        this.stupidBookshelf(planks.get(), type, bookshelf);
        this.stupidChestBlocks(type, planks, chest, trappedChest);
    }

    public void stupidChestBlocks(String type, RegistryObject<Block> planks, RegistryObject<? extends Block> chest, RegistryObject<? extends Block> trappedChest) {
        this.stupidChestBlocks(type, planks.get(), chest, trappedChest);
    }

    // this is copied from blueprint because alex's caves is stupid
    public void stupidChestBlocks(String type, Block planks, RegistryObject<? extends Block> chest, RegistryObject<? extends Block> trappedChest) {
        ModelFile model = this.particle(chest, loc(planks).withPath(str -> "block/%s/%s".formatted(type, str)));
        this.simpleBlock(chest.get(), model);
        this.simpleBlock(trappedChest.get(), model);
        this.simpleBlockItem(chest.get(), new ModelFile.UncheckedModelFile(new ResourceLocation("blueprint", "item/template_chest")));
        this.simpleBlockItem(trappedChest.get(), new ModelFile.UncheckedModelFile(new ResourceLocation("blueprint", "item/template_chest")));
    }

    public void stupidBookshelf(Block planks, String type, RegistryObject<Block> bookshelf) {
        this.simpleBlock(bookshelf.get(), this.models().cubeColumn(name(bookshelf.get()),
                this.blockTexture(bookshelf.get()), loc(planks).withPath(str -> "block/%s/%s".formatted(type, str))));
        this.blockItem(bookshelf);
    }
}
