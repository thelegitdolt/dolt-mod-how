package com.dolthhaven.dolt_mod_how.data;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;

public class DMHBlockStatesGen extends BlueprintBlockStateProvider {
    public DMHBlockStatesGen(GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), DoltModHow.MOD_ID, e.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {
        customPot(POTTED_ARID_SPROUTS, POTTED_MYCELIUM_SPROUTS, POTTED_BEACHGRASS,
                POTTED_TALL_BEACHGRASS, POTTED_STRAWBERRIES, POTTED_WHITE_STRAWBERRIES, POTTED_ONION, POTTED_TOMATOES, POTTED_CABBAGE);
    }


    @SafeVarargs
    private void customPot(RegistryObject<? extends Block>... pots) {
        for (RegistryObject<? extends Block> pot : pots) {
            ResourceLocation potTexture = DoltModHow.rl("block/" + name(pot.get()));
            this.simpleBlock(pot.get(), models()
                    .singleTexture(name(pot.get()), new ResourceLocation("block/flower_pot_cross"), "plant", potTexture).renderType("cutout"));
        }
    }
}
