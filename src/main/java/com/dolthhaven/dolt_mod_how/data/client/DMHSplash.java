package com.dolthhaven.dolt_mod_how.data.client;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.data.client.splash.WMWSplash;
import com.teamabnormals.blueprint.client.screen.splash.SplashProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class DMHSplash extends SplashProvider {
    protected DMHSplash(GatherDataEvent event) {
        super(DoltModHow.MOD_ID, event.getGenerator().getPackOutput());
    }

    @Override
    protected void registerSplashes() {
        this.add("Dolt Splash How");
        this.add(WMWSplash.INSTANCE);
    }
}
