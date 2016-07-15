package fr.exodeus.zombies.Common;

import java.io.File;

import fr.exodeus.zombies.Common.Saver.PlayerContainer;
import fr.exodeus.zombies.Common.Saver.Network.Capabilities.Capabilities;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieButcher;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieCivil;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieCrawler;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieHerobrine;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieMiner;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieNazi;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombiePolice;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombiePrisoner;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieSoldier;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieSurgeon;
import fr.exodeus.zombies.Objects.Potion.Bonebreak;
import fr.exodeus.zombies.Objects.Potion.Infection;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ZombiesEvent {

	@SubscribeEvent
	public void onMonsterSpawn(EntityJoinWorldEvent event) {

		if (event.getEntity() instanceof EntityZombie) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntitySkeleton) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntityCreeper) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntitySpider) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntityEnderman) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntitySlime) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntityWitch) {
			event.setCanceled(true);
		}

	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event) {

		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		if (event.getEntityLiving().isPotionActive(Infection.infection)) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			Infection.effects(player);

		}
		if (event.getEntityLiving().isPotionActive(Bonebreak.bonebreak)) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			Bonebreak.effects(player);

		}

		for (Potion p : Reference.potionTypes) {
			if (event.getEntityLiving().isPotionActive(p))

				if (event.getEntityLiving().getActivePotionEffect(p).getDuration() == 0) {

					event.getEntityLiving().removePotionEffect(p);
					// event.getEntityLiving().removePotionEffectClient(p);
				}
		}
	}

	@SubscribeEvent
	public void onEntityGetHurt(LivingHurtEvent event) {

		if (event.getEntityLiving() instanceof EntityPlayer) {

			if (event.getSource().getEntity() instanceof ZombieHerobrine) {
				event.getEntityLiving().setFire(1);

			} else if (event.getSource().getEntity() instanceof ZombieCivil
					|| event.getSource().getEntity() instanceof ZombieButcher
					|| event.getSource().getEntity() instanceof ZombiePrisoner
					|| event.getSource().getEntity() instanceof ZombieSurgeon
					|| event.getSource().getEntity() instanceof ZombieMiner
					|| event.getSource().getEntity() instanceof ZombieNazi
					|| event.getSource().getEntity() instanceof ZombiePolice
					|| event.getSource().getEntity() instanceof ZombieSoldier
					|| event.getSource().getEntity() instanceof ZombieCrawler) {

				Infection.playerGetHurtByInfected(event);

			} else if (event.getSource() == DamageSource.fall) {

				Bonebreak.playerFall(event);

			}
		}
	}
	
	//======================================================

	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) {
		if (event.side == Side.SERVER) {
			MainZombies.proxy.serverTick(event.player);
		} else if (event.side == Side.CLIENT) {
			// MainZombies.proxy.serverTick(event.player);
		}
	}

	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent event) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			PlayerContainer.addPlayer(event.player);
		}
	}

	@SubscribeEvent
	public void onLogout(PlayerLoggedOutEvent event) {
		PlayerContainer.ALL_PLAYERS.remove(event.player);
	}

	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			if (event.getOriginal().hasCapability(MainZombies.ZOMBIESPLUS_CAP, null)) {
				Capabilities cap = event.getOriginal().getCapability(MainZombies.ZOMBIESPLUS_CAP, null);
				Capabilities newCap = event.getEntityPlayer().getCapability(MainZombies.ZOMBIESPLUS_CAP, null);
				//-------------------------------------
				newCap.setStats(cap.thirstLevel);
				
			}
		}
	}
	
	@SubscribeEvent
	public void onAttachCapability(AttachCapabilitiesEvent.Entity event)
	{
	    if(event.getEntity() instanceof EntityPlayer)
	    {
	        event.addCapability(new ResourceLocation(MainZombies.ZOMBIESPLUS_CAP + ":ZOMBIESPLUS_CAP"), new Capabilities((EntityPlayer) event.getEntity()));
	    }
	}

	@SubscribeEvent
	public void playerDeath(LivingDeathEvent event) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			if (!(event.getEntityLiving() instanceof EntityPlayer))
				return;
			PlayerContainer.getPlayer((EntityPlayer) event.getEntityLiving()).getStats().onDeath();
		}
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) 
	{
	    if(!event.player.worldObj.isRemote)
	    {
	        event.player.getCapability(MainZombies.ZOMBIESPLUS_CAP, null).sync();
	    }
	}
	
	//==========================================================

	@SubscribeEvent
	public void playerRegeneration(LivingHealEvent event) {
		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		if (PlayerContainer.getPlayer((EntityPlayer) event.getEntityLiving()).getStats().thirst.thirstLevel <= 3) {
			if (event.getAmount() <= 1)
				event.setCanceled(true);
		}
	}

	// ==========================================================

	@SubscribeEvent
	public void onAttack(AttackEntityEvent attack) {
		PlayerContainer player = PlayerContainer.getPlayer(attack.getEntityPlayer());
		if ((player != null) && (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)) {
			player.addExhaustion(0.4f);
		}
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent hurt) {
		if (hurt.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) hurt.getEntityLiving();
			PlayerContainer.getPlayer(player).addExhaustion(0.5f);
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		if (player != null) {
			PlayerContainer player2 = PlayerContainer.getPlayer(event.getPlayer());
			if ((player2 != null) && (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)) {
				player2.addExhaustion(0.05f);
			}
		}
	}

}
