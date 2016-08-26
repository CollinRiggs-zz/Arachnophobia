package com.collinriggs.arachnophobia;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.collinriggs.arachnophobia.event.ModEventHandler;

@Mod(modid = Ref.modid, name = Ref.modname, version = Ref.version)
public class Arachnophobia {
	
	public static boolean addSeedsToGrass, woolDropsString, zombiesDropEyes,
	cancelSpiders, cancelCaveSpiders, removeSpiderDungeons = true;
	
	@Instance
	public static Arachnophobia instance = new Arachnophobia();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		String cat = Configuration.CATEGORY_GENERAL;
        config.load();
        	addSeedsToGrass = config.getBoolean("addSeedsToGrass", cat, true, "Add pumpkin and melon seeds to replace those which are found in mineshafts.");
        	woolDropsString = config.getBoolean("woolDropsString", cat, true, "When broken, wool drops 4 string unless shears are used.");
        	zombiesDropEyes = config.getBoolean("zombiesDropEyes", cat, true, "Zombies have a chance to drop spider eyes on death.");
        	cancelSpiders = config.getBoolean("cancelSpiders", cat, true, "Spiders cannot be and will not spawn.");
        	cancelCaveSpiders = config.getBoolean("cancelCaveSpiders", cat, true, "Cave Spiders cannot be and will not spawn.");
        	removeSpiderDungeons = config.getBoolean("removeSpiderDungeons", cat, true, "Spider dungeons will be replaced with either or zombies or skeletons.");
        config.save();
	}
	
	@EventHandler
	public void postInit(FMLInitializationEvent event) {
		if (removeSpiderDungeons) DungeonHooks.removeDungeonMob("Spider");
		if (addSeedsToGrass) { 
			MinecraftForge.addGrassSeed(new ItemStack(Items.MELON_SEEDS), 2);
			MinecraftForge.addGrassSeed(new ItemStack(Items.PUMPKIN_SEEDS), 2);
		}
	}
	
}
