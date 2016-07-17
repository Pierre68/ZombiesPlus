package fr.exodeus.zombies.ServerSide;

import java.io.File;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
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

public class ServerEvents {

	// ======================================================

	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) {

		if (event.side == Side.SERVER) {
			MainZombies.proxy.serverTick(event.player);
		}
	}

	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent event) {
		PlayerContainer.addPlayer(event.player);

	}

	@SubscribeEvent
	public void onLogout(PlayerLoggedOutEvent event) {
		PlayerContainer.removePlayer(event.player);

	}

	@SubscribeEvent
	public void playerDeath(LivingDeathEvent event) {

		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		PlayerContainer.removePlayer((EntityPlayer) event.getEntityLiving());

	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		PlayerContainer.addPlayer(event.player);
	}

	// ==========================================================

	@SubscribeEvent
	public void playerRegeneration(LivingHealEvent event) {
		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		if (PlayerContainer.getPlayer((EntityPlayer) event.getEntityLiving()).getThirstStats().thirstLevel <= 3) {
			if (event.getAmount() <= 1)
				event.setCanceled(true);
		}
	}

	// ==========================================================

	@SubscribeEvent
	public void onAttack(AttackEntityEvent attack) {
		PlayerContainer player = PlayerContainer.getPlayer(attack.getEntityPlayer());
		if ((player != null)) {
			player.getThirstStats().addExhaustion(0.4f);
		}
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent hurt) {
		if (hurt.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) hurt.getEntityLiving();
			PlayerContainer.getPlayer(player).getThirstStats().addExhaustion(0.5f);
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		if (player != null) {
			PlayerContainer player2 = PlayerContainer.getPlayer(event.getPlayer());
			if ((player2 != null)) {
				player2.getThirstStats().addExhaustion(0.05f);
			}
		}
	}

}
