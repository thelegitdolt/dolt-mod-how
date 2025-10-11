package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHCCOCompat;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;

public class DMHCauldrons {
   public static void register() {
       if (BlockSubRegistryHelper.areModsLoaded(DMHUtils.Constants.CAVERNS_AND_CHASMS, DMHUtils.Constants.OREGANIZED)) {
           DMHCCOCompat.registerCauldrons();
       }
   }
}
