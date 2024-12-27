package com.dolthhaven.dolt_mod_how.core.mixin.environmental;

import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishOverlay;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Unique
@Mixin(SlabfishOverlay.class)
public class SlabfishOverlayMixin {
//    @Shadow
//    @Mutable
//    @Final
//    private static SlabfishOverlay[] $VALUES;
//
//    @Invoker("<init>")
//    public static SlabfishOverlay newOverlay(String internalName, int internalId, int id, String name) {
//        throw new AssertionError();
//    }
//
//    @Inject(method = "<clinit>", at = @At(value = "FIELD",
//            target = "Lcom/teamabnormals/environmental/common/entity/animal/slabfish/SlabfishOverlay;VALUES:[Lcom/teamabnormals/environmental/common/entity/animal/slabfish/SlabfishOverlay;",
//            shift = At.Shift.AFTER))
//    private static void DD$addCustomColor(CallbackInfo ci) {
//        List<SlabfishOverlay> stuff = new ArrayList<>(Arrays.asList($VALUES));
//
//        stuff.add(newOverlay("rotten_tomatoes", stuff.size(), stuff.size(), "rotten_tomato"));
//        $VALUES = stuff.toArray(new SlabfishOverlay[0]);
//    }
}
