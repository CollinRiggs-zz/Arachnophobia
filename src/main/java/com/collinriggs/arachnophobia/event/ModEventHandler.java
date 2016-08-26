package com.collinriggs.arachnophobia.event;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.collinriggs.arachnophobia.Arachnophobia;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEventHandler {
	
	private Block block;
	
	@SubscribeEvent
	public void onLivingSpawn(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof EntitySpider && Arachnophobia.cancelSpiders) {
			entity.setSilent(true);
			event.setCanceled(true);
		}
		if (entity instanceof EntityCaveSpider && Arachnophobia.cancelCaveSpiders) {
			entity.setSilent(true);
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BreakEvent event) {
		block = event.getWorld().getBlockState(event.getPos()).getBlock();
	}
	
	@SubscribeEvent
	public void getBlockDrops(HarvestDropsEvent event) {
		if (Arachnophobia.woolDropsString) {
			if (event.getHarvester() instanceof EntityPlayer) {
				EntityPlayer player = event.getHarvester();
				ItemStack itemInHand = player.getHeldItemMainhand();
				if ((itemInHand == null || itemInHand.getItem() != Items.SHEARS) && block == Blocks.WOOL) {
					List<ItemStack> drops = event.getDrops();
					drops.clear();
					drops.add(new ItemStack(Items.STRING, 4));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void getEntityDrops(LivingDropsEvent event) {
		if (Arachnophobia.zombiesDropEyes) {
			Entity entity = event.getEntityLiving();
			List<EntityItem> drops = event.getDrops();
			BlockPos ePos = entity.getPosition();
			if (entity instanceof EntityZombie) {
				int rand = ThreadLocalRandom.current().nextInt(0, 5 + 1);
				if (rand > 2) {
					rand = 0;
				}
				drops.add(new EntityItem(entity.getEntityWorld(), ePos.getX(), ePos.getY(), ePos.getZ(), new ItemStack(Items.SPIDER_EYE, rand)));
			}
		}
	}
	
}