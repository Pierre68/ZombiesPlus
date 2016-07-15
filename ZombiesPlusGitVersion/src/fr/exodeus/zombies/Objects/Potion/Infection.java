package fr.exodeus.zombies.Objects.Potion;

import java.util.HashMap;
import java.util.Random;

import fr.exodeus.zombies.Common.MainZombies;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Infection extends Potion {
	public static Potion infection;

	public Infection(int par1, boolean par2, int par3) {
		super(par2, par3);
	}

	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	public static void init() {
		infection = (new Infection(32, true, 0)).setIconIndex(0, 0).setPotionName("potion.infection");

		REGISTRY.register(50, new ResourceLocation("zombies", "infection"), infection);
	}

	public static ResourceLocation textureResource = new ResourceLocation("zombies", "textures/gui/status_Gui.png");

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasStatusIcon() {
		Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);
		return true;
	}

	public static void effects(EntityPlayer player) {
		Random r = new Random();
		int lvl = player.getActivePotionEffect(infection).getAmplifier() + 1;

		switch (r.nextInt(20000 / lvl)) {

		case 0:
			player.attackEntityFrom(DamageSource.generic, 1);
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(15), 40 + r.nextInt(100) * lvl, 0)); // blindness
			break;
		case 1:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(9), 300 + r.nextInt(200) * lvl, 0)); // confusion
			break;
		case 2:
			player.playSound(SoundEvents.AMBIENT_CAVE, 1f, 1f);
			break;
		case 3:
			player.playSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT, 1f, 1f);
			break;
		case 4:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 300 + r.nextInt(500), r.nextInt(2)));
			break;
		case 5:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 300 + r.nextInt(500), r.nextInt(2))); // dig
																													// slow
																													// down
			break;
		case 6:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(17), 300 + r.nextInt(500), r.nextInt(2))); // hunger
			break;
		case 7:
			player.addPotionEffect(
					new PotionEffect(Potion.getPotionById(18), 300 + r.nextInt(500), r.nextInt(2) + lvl)); // weakness
			break;

		}

	}

	public static void playerGetHurtByInfected(LivingHurtEvent event) {

		if (event.getAmount() >= event.getEntityLiving().getHealth())
			return; // not longer useful

		EntityPlayer player = (EntityPlayer) event.getEntityLiving();

		// -----------------

		Random r = new Random();
		if (r.nextInt(15) != 0)
			return; // infect player if equal 0

		// ------------------

		if (event.getEntityLiving().isPotionActive(Infection.infection)) {

			int lvl = event.getEntityLiving().getActivePotionEffect(Infection.infection).getAmplifier();

			// Infection.infectPlayer(player, lvl + 1); MARCHE PAS
			infectPlayer(player, lvl + 1);

		} else {

			// Infection.infectPlayer(player, 0); MARCHE PAS
			infectPlayer(player, 0);
		}
	}

	public static void infectPlayer(EntityLivingBase playerIn, Integer lvl) {

		EntityPlayer player = (EntityPlayer) playerIn;

		if (player.getHealth() == 0)
			return;
		
		if (lvl != 0)
			player.addChatComponentMessage(new TextComponentString("�4Your infection is getting worse"));
		if (lvl == 0){
			player.addChatComponentMessage(new TextComponentString("�4You have been infected !"));
			player.addChatComponentMessage(new TextComponentString("�7Take an Antibiotic as fast as possible"));
		}

		playerIn.addPotionEffect(new PotionEffect(Infection.infection, 10000 * (lvl + 1), lvl));

	}

}
