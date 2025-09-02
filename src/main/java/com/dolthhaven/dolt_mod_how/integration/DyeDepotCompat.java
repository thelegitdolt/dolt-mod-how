package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DyeDepotCompat {
    public static String getDyeDepotDyeColor(Item item) {
        ResourceLocation loc = DMHUtils.getItemId(item);
        Pattern pattern = Pattern.compile("([a-z]+)_dye$");
        if (loc.getNamespace().equals("dye_depot")) {
            Matcher matcher = pattern.matcher(loc.getPath());
            if (matcher.matches()) {
                String dye = matcher.group(1);

                try {
                    DyeDepotDyes.valueOf(dye.toUpperCase(Locale.ROOT));
                    return dye;
                } catch (IllegalArgumentException e) {
                    return null;
                }
            }
        }
        return null;
    }


    public enum DyeDepotDyes implements StringRepresentable {
        ROSE("rose"),
        MAROON("maroon"),
        GINGER("ginger"),
        TAN("tan"),
        BEIGE("beige"),
        CORAL("coral"),
        OLIVE("olive"),
        FOREST("forest"),
        VERDANT("verdant"),
        AMBER("amber"),
        TEAL("teal"),
        MINT("mint"),
        AQUA("aqua"),
        SLATE("slate"),
        NAVY("navy"),
        INDIGO("indigo");

        final String name;
        DyeDepotDyes(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
