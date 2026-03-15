package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.DataPackCommand;
import net.minecraft.server.commands.ReloadCommand;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mixin(DataPackCommand.class)
public abstract class DataPackCommandMixin {
    @WrapOperation(method = "register", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;requires(Ljava/util/function/Predicate;)Lcom/mojang/brigadier/builder/ArgumentBuilder;"))
    private static <S> ArgumentBuilder<S, ?> resortCommands(LiteralArgumentBuilder<S> instance, Predicate<CommandSourceStack> predicate, Operation<ArgumentBuilder<S, ?>> original) {
        ArgumentBuilder<S, ?> value = original.call(instance, predicate);
        value = value.then((ArgumentBuilder<S, ?>) Commands.literal("sort").executes(DataPackCommandMixin::resortCommands));
        return value;
    }

    @Unique
    private static int resortCommands(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        PackRepository packrepository = source.getServer().getPackRepository();

        List<Pack> newList = new ArrayList<>();
        for (Pack pack : packrepository.getSelectedPacks()) {
            if (pack.getId().toLowerCase().contains("openloader")) {
                newList.add(pack);
            } else {
                newList.add(0, pack);
            }
        }

        source.sendSuccess(() -> Component.literal("Among us"), true);
        ReloadCommand.reloadPacks(newList.stream().map(Pack::getId).collect(Collectors.toList()), source);

        return newList.size();
    }
}
