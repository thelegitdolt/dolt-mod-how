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
        block(WAX_BLOCK);
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
}
