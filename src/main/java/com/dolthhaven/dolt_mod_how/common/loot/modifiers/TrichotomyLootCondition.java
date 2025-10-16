package com.dolthhaven.dolt_mod_how.common.loot.modifiers;

import com.dolthhaven.dolt_mod_how.core.registry.DMHLoot;
import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Function;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record TrichotomyLootCondition(List<Block> blocks, List<TagKey<Block>> blockTags, List<Block> exclude) implements LootItemCondition {
    public static final MapCodec<TrichotomyLootCondition> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ForgeRegistries.BLOCKS.getCodec().listOf().fieldOf("blocks").forGetter(TrichotomyLootCondition::blocks),
            TagKey.codec(Registries.BLOCK).listOf().fieldOf("blockTags").forGetter(TrichotomyLootCondition::blockTags),
            ForgeRegistries.BLOCKS.getCodec().listOf().fieldOf("exclude").forGetter(TrichotomyLootCondition::exclude)
    ).apply(inst, TrichotomyLootCondition::new));

    @Override
    public LootItemConditionType getType() {
        return DMHLoot.TRICHOTOMY.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        BlockState state = lootContext.getParamOrNull(LootContextParams.BLOCK_STATE);
        if (state != null) {
            Block block = state.getBlock();
            if (exclude().contains(block)) {
                return false;
            }
            else {
                for (TagKey<Block> blockTag : blockTags()) {
                    if (state.is(blockTag)) {
                        return true;
                    }
                }

                if (blocks().contains(state.getBlock())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<TrichotomyLootCondition> {

        @Override
        public void serialize(JsonObject object, TrichotomyLootCondition condition, JsonSerializationContext context) {
            serializeArray(object, "blocks", condition.blocks, ForgeRegistries.BLOCKS::getKey);
            serializeArray(object, "blockTags", condition.blockTags, TagKey::location);
            serializeArray(object, "exclude", condition.exclude, ForgeRegistries.BLOCKS::getKey);
            object.add("blocks", context.serialize(condition.blocks));
        }

        private static <V> void serializeArray(JsonObject jsonObject, String name, Iterable<V> contents, Function<V, ResourceLocation> stringFunction) {
            JsonArray array = new JsonArray();
            for (V object : contents) {
                array.add(stringFunction.apply(object).toString());
            }
            jsonObject.add(name, array);
        }

        private static <V> List<V> deserializeArray(JsonObject object, JsonDeserializationContext context, String name, Function<ResourceLocation, V> parser) {
            JsonArray array = GsonHelper.getAsJsonArray(object, name, null);
            ImmutableList.Builder<V> list = ImmutableList.builder();
            if (array != null) {
                for (JsonElement element : array) {
                    ResourceLocation loc = new ResourceLocation(GsonHelper.convertToString(element, "element"));
                    list.add(parser.apply(loc));
                }
            }
            return list.build();
        }

        @Override
        public TrichotomyLootCondition deserialize(JsonObject object, JsonDeserializationContext context) {
            List<Block> blocks = deserializeArray(object, context, "blocks", a ->
                    BuiltInRegistries.BLOCK.getOptional(a).orElseThrow(() -> new JsonSyntaxException("Unknown block id '" + a + "'")));

            List<TagKey<Block>> blockTags = deserializeArray(object, context, "blockTags", a ->
                    TagKey.create(Registries.BLOCK, a));

            List<Block> exclude = deserializeArray(object, context, "exclude", a ->
                    BuiltInRegistries.BLOCK.getOptional(a).orElseThrow(() -> new JsonSyntaxException("Unknown block id '" + a + "'")));

            return new TrichotomyLootCondition(blocks, blockTags, exclude);
        }
    }
}
