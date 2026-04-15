package com.dolthhaven.dolt_mod_how.core.util;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.integration.DMHACCompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class DMHUtils {
    public static final int NOT_TOOLBOX = 0;
    public static final int SUCCESSFUL_SETTING = 1;
    public static final int UNSUCCESSFUL_SETTING = 2;

    public static void addToInvOrDrop(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, true);
        }
    }


    public static void printItem(Item item) {
        ResourceLocation res = ForgeRegistries.ITEMS.getKey(item);
        if (res == ForgeRegistries.ITEMS.getDefaultKey() || res == null) {
            DoltModHow.LOGGER.info("DoltModHow.Util.printItem used, but no Item was found!!");
        }
        else {
            DoltModHow.LOGGER.info(res.toString());
        }
    }

    public static @Nullable Item getPotentialItem(ResourceLocation loc) {
        Item item = ForgeRegistries.ITEMS.getValue(loc);
        if (item == Items.AIR) {
            return null;
        }
        return item;
    }

    public static @Nullable Item getPotentialItem(String path, String name) {
        return getPotentialItem(new ResourceLocation(path, name));
    }


    public static @Nullable Block getPotentialBlock(ResourceLocation loc) {
        Block block = ForgeRegistries.BLOCKS.getValue(loc);
        return block == Blocks.AIR ? null : block;
    }

    public static @Nullable Block getPotentialBlock(String path, String name) {
       return getPotentialBlock(new ResourceLocation(path, name));
    }

    public static @NotNull Fluid getFluidOrWater(String path, String name) {
        return getFluidOrWater(new ResourceLocation(path, name));
    }

    public static @NotNull Fluid getFluidOrWater(ResourceLocation location) {
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(location);
        return fluid != null ? fluid: Fluids.WATER;
    }

    public static ResourceLocation getFluidID(Fluid fluid) {
        return ForgeRegistries.FLUIDS.getKey(fluid);
    }

    public static ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    public static ResourceLocation getBlockId(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public static ResourceLocation entityId(EntityType<?> type) {
        return ForgeRegistries.ENTITY_TYPES.getKey(type);
    }


    public static class Constants {
        public static final String ALEXS_CAVES = "alexscaves";
        public static final String AMENDMENTS = "amendments";
        public static final String ATMOSPHERIC = "atmospheric";
        public static final String BOP = "biomesoplenty";
        public static final String BLASTED_BARRENS = "blasted_barrens";
        public static final String BREWING_AND_CHEWING = "brewinandchewin";
        public static final String BUZZIER_BEES = "buzzier_bees";
        public static final String CAVERNS_AND_CHASMS = "caverns_and_chasms";
        public static final String CAVE_DELIGHT = "cavedelight";
        public static final String CREATE = "create";
        public static final String DOOM_AND_GLOOM = "doom_and_gloom";
        public static final String DUNGEONS_DELIGHT = "dungeonsdelight";
        public static final String DYE_DEPOT = "dye_depot";
        public static final String ENVIRONMENTAL = "environmental";
        public static final String FARMERS_DELIGHT = "farmersdelight";
        public static final String FTGU = "ftgu";
        public static final String MOWZIES_MOBS = "mowziesmobs";
        public static final String MY_NETHERS_DELIGHT = "mynethersdelight";
        public static final String NEAPOLITAN = "neapolitan";
        public static final String JNE = "netherexp";
        public static final String OREGANIZED = "oreganized";
        public static final String QUARK = "quark";
        public static final String RESONANCE = "resonance";
        public static final String SAVAGE_AND_RAVAGE = "savage_and_ravage";
        public static final String UPGRADE_AQUATIC = "upgrade_aquatic";
        public static final String SPAWN = "spawn";
        public static final String SPECIES = "species";
        public static final String WINDSWEPT = "windswept";
        public static final String WOODWORKS = "woodworks";

        public static final ResourceLocation ALPHACENE_GRASS_BLOCK = new ResourceLocation(SPECIES, "alphacene_grass_block");

        public static final ResourceLocation STURDY_STONE = new ResourceLocation(QUARK, "sturdy_stone");

        public static final ResourceLocation FOUR_LEAF_CLOVER = new ResourceLocation(BUZZIER_BEES, "four_leaf_clover");

        public static final ResourceLocation BULLET_PEPPER = new ResourceLocation(MY_NETHERS_DELIGHT, "bullet_pepper");

        public static final ResourceLocation GOLDEN_LAVA_BUCKET = new ResourceLocation(CAVERNS_AND_CHASMS, "golden_lava_bucket");

        public static final ResourceLocation STRAWBERRY_PIPS = new ResourceLocation(NEAPOLITAN, "strawberry_pips");
        public static final ResourceLocation BANANA_PEEL = new ResourceLocation(NEAPOLITAN, "banana_peel");

        public static final ResourceLocation SAND_RAKE = new ResourceLocation(MOWZIES_MOBS, "sand_rake");
        public static final ResourceLocation RAKED_SAND = new ResourceLocation(MOWZIES_MOBS, "raked_sand");
        public static final ResourceLocation RED_RAKED_SAND = new ResourceLocation(MOWZIES_MOBS, "red_raked_sand");
        public static final ResourceLocation ARID_SAND = new ResourceLocation(ATMOSPHERIC, "arid_sand");
        public static final ResourceLocation RED_ARID_SAND = new ResourceLocation(ATMOSPHERIC, "red_arid_sand");
        public static final ResourceLocation ASHEN_SAND = new ResourceLocation(BLASTED_BARRENS, "ashen_sand");
        public static final ResourceLocation GINGERBREAD_COOKIE = new ResourceLocation(WINDSWEPT, "gingerbread_cookie");

        public static final ResourceLocation ACID = new ResourceLocation(ALEXS_CAVES, "acid");
        public static final ResourceLocation PURPLE_SODA = new ResourceLocation(ALEXS_CAVES, "purple_soda");

        public static final ResourceLocation ZIRCONIA = new ResourceLocation(CAVERNS_AND_CHASMS, "zirconia");

        public static final ResourceLocation MOLTEN_LEAD = new ResourceLocation(OREGANIZED, "molten_lead");
        public static final ResourceLocation DESOLATE_DAGGER = new ResourceLocation(ALEXS_CAVES, "desolate_dagger");
        public static final ResourceLocation DINOSAUR_CHOP = new ResourceLocation(ALEXS_CAVES, "dinosaur_chop");
        public static final ResourceLocation COOKED_DINOSAUR_CHOPS = new ResourceLocation(ALEXS_CAVES, "cooked_dinosaur_chop");

        public static final ResourceLocation ZINC_BLOCK = new ResourceLocation(CREATE, "zinc_block");

        public static final ResourceLocation MOWZIES_MOBS_TAB = new ResourceLocation(MOWZIES_MOBS, "mowziesmobs_tab");
        public static final ResourceLocation CREATE_BUILDING_TAB = new ResourceLocation(CREATE, "palettes");

        public static final ResourceLocation DUNGEONS_DELIGHT_TAB = new ResourceLocation(DUNGEONS_DELIGHT,"dungeonsdelight_tab");
        public static final ResourceLocation WARDENZOLA = new ResourceLocation(DUNGEONS_DELIGHT,"wardenzola");

        public static final ResourceLocation PATHFINDER_QUILL = new ResourceLocation(QUARK,"pathfinders_quill");
        public static final ResourceLocation STICKBUG = new ResourceLocation(SPAWN,"stickbug");


        public static final ResourceLocation FALLEN_LEAVES = new ResourceLocation(SPAWN,"fallen_leaves");

        public static final ResourceLocation ALPHACENE_MOSS_CARPET = new ResourceLocation(SPECIES,"alphacene_moss_carpet");



        public static boolean runData() {
            for (String str : new String[]{ALEXS_CAVES, ATMOSPHERIC, BLASTED_BARRENS, NEAPOLITAN, UPGRADE_AQUATIC, ENVIRONMENTAL, MOWZIES_MOBS, DOOM_AND_GLOOM, WOODWORKS, BREWING_AND_CHEWING}) {
                if (!ModList.get().isLoaded(str)) {
                    return false;
                }
            }
            return true;
        }
    }

    @Nullable
    public static LivingEntity getClosestEntityTo(Entity entity, Predicate<LivingEntity> entityPredicate) {
        Vec3 pos = entity.position();
        List<LivingEntity> effectiveEntities = entity.level().getEntitiesOfClass(LivingEntity.class,
                new AABB(pos, pos.add(1, 1, 1)).inflate(6, 6, 6), entityPredicate);
        return entity.level().getNearestEntity(effectiveEntities, TargetingConditions.DEFAULT,null,  pos.x, pos.y, pos.z);
    }

    public static boolean alexCavesLoaded() {
        return ModList.get().isLoaded(Constants.ALEXS_CAVES);
    }

    public static boolean oreganized() {
        return ModList.get().isLoaded(Constants.OREGANIZED);
    }

    public static boolean cavernsChasmsLoaded() {
        return ModList.get().isLoaded(Constants.CAVERNS_AND_CHASMS);
    }

    public static boolean unreadableCode(Entity killer, Entity victim, DamageSource source) {
        if (!alexCavesLoaded()) return true;
        if (DMHACCompat.firstEntityIsTremorzillaAndSecondIsNucleeper(killer, victim)) {
            return DMHACCompat.isNuclearBeamDamageSource(source);
        }

        return true;
    }

}
