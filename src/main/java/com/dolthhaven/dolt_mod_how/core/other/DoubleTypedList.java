package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DoubleTypedList<A, B> extends ArrayList<Object> {
    private final Class<A> aClass;
    private final Class<B> bClass;

    public DoubleTypedList(Class<A> aClass, Class<B> bClass) {
        super();
        this.aClass = aClass;
        this.bClass = bClass;
    }


    public static void main(String[] args) {
        DoubleTypedList<RegistryObject<Block>, Block> list = DoubleTypedList
                .of(Blocks.DRAGON_EGG, Blocks.ACACIA_DOOR, DMHBlocks.GLOWSHROOM_COLONY);
        List<Block> block = list.mapB(RegistryObject::get);
    }

    public List<B> mapB(Function<? super A, B> mapper) {
        List<B> bList = new ArrayList<>();
        B b;
        for (Object object : this) {
            b = object.getClass() == aClass ? (B) object : mapper.apply((A) object);
            bList.add(b);
        }
        return bList;
    }

    public List<A> mapA(Function<? super B, A> mapper) {
        List<A> aList = new ArrayList<>();
        A a;
        for (Object object : this) {
            a = object.getClass() == aClass ? (A) object : mapper.apply((B) object);
            aList.add(a);
        }
        return aList;
    }

    public void forEach(Consumer<A> aAction, Consumer<B> bAction) {
         for (Object object : this) {
             if (object.getClass() == aClass) {
                 aAction.accept((A) object);
             }
             else if (object.getClass() == bClass) {
                 bAction.accept((B) object);
             }
         }
    }

    public static <A, B> DoubleTypedList<A, B> of(Object... objects) {
        Class<A> aClass = null;
        Class<B> bClass = null;
        for (Object object : objects) {
            if (aClass == null) {
                aClass = (Class<A>) object.getClass();
            }
            else if (bClass == null) {
                bClass = (Class<B>) object.getClass();
            }
            else {
                throw new IllegalArgumentException("Attempted to place objects of more than 2 types in DoubleTypedList");
            }
        }

        DoubleTypedList<A, B> list = new DoubleTypedList<>(aClass, bClass);
        list.addAll(Arrays.asList(objects));
        return list;
    }
}
