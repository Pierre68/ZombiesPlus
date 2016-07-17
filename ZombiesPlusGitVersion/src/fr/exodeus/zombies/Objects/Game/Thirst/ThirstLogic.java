package fr.exodeus.zombies.Objects.Game.Thirst;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.TempCategory;

public class ThirstLogic {

	public EntityPlayer player;

	public int thirstLevel;
	public int thirstSaturation;
	public DamageSource thirstSource = new DamageThirst();

	public boolean isPoisoned;

	public float thirstExhaustion = 0;

	// =======================================

	public ThirstLogic(EntityPlayer player) {
		this.thirstLevel = Reference.MAX_THIRST_LEVEL;
		this.thirstSaturation = Reference.MAX_THIRST_SATURATION;
		this.player = player;
		this.thirstSource = new DamageThirst();

		readData();
	}

	public void onTick() {

		if (thirstExhaustion > 5f) {
			thirstExhaustion = 0f;
			if (thirstSaturation > 0f) {
				thirstSaturation = Math.max(thirstSaturation - 1, 0);
			} else {
				thirstLevel = Math.max(thirstLevel - 1, 0);
			}
		}

		this.computeExhaustion(player);

		this.writeData();

		player.addChatComponentMessage(
				new TextComponentString(thirstExhaustion + " / " + thirstSaturation + " / " + thirstLevel));

	}

	public void onDeath() {
		this.thirstLevel = Reference.MAX_THIRST_LEVEL;
		this.thirstSaturation = Reference.MAX_THIRST_SATURATION;
		this.writeData();
	}

	public void writeData() {

	}

	public void readData() {

	}

	public static class DamageThirst extends DamageSource {
		public DamageThirst() {
			super("thirst");
			setDamageBypassesArmor();
			setDamageIsAbsolute();
		}

		@Override
		public TextComponentString getDeathMessage(EntityLivingBase entity) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				return new TextComponentString(player.getDisplayName() + "'s body is now made up of 0% water!");
			}
			return (TextComponentString) super.getDeathMessage(entity);
		}
	}

	public void addExhaustion(float f) {
		thirstExhaustion = thirstExhaustion + f;
	}

	public void computeExhaustion(EntityPlayer player) {

		int movement = player.isRiding() ? 0 : movementSpeed();
		float exhaustAmplifier = isNight() ? 0.9f : 1;
		float multiplier = getCurrentBiomeMultiplier();
		if (player.isInsideOfMaterial(Material.WATER)) {
			if (movement > 0) {
				addExhaustion(0.03f * movement * 0.003F * exhaustAmplifier);
			}
		} else if (player.isInWater()) {
			if (movement > 0) {
				addExhaustion(0.03f * movement * 0.003F * exhaustAmplifier);
			}
		} else if (player.onGround) {

			if (movement > 0) {
				if (player.isSprinting()) {
					addExhaustion(0.1f * movement * 0.018F * multiplier * exhaustAmplifier);
				} else {

					addExhaustion(0.1f * movement * 0.018F * multiplier * exhaustAmplifier);
				}
			}
		} else if (!player.onGround && !player.isRiding()) {
			if (player.isSprinting()) {
				addExhaustion((0.03f * 2) * multiplier * exhaustAmplifier);
			} else {
				addExhaustion(0.03f * multiplier * exhaustAmplifier);
			}
		}
	}

	private float getCurrentBiomeMultiplier() {

		TempCategory cat = player.worldObj.getBiomeGenForCoords(player.getPosition()).getTempCategory();

		if (cat == TempCategory.COLD)
			return 1.5f;
		if (cat == TempCategory.MEDIUM)
			return 1f;
		if (cat == TempCategory.WARM)
			return 2f;
		if (cat == TempCategory.OCEAN)
			return 1.25f;

		return 1;

	}

	private int movementSpeed() {

		return 70; // deal with it later

	}

	private boolean isNight() {

		return !player.worldObj.isDaytime();
	}

}
