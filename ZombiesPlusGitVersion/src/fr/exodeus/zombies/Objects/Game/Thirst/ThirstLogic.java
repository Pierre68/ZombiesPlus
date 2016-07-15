package fr.exodeus.zombies.Objects.Game.Thirst;

import fr.exodeus.zombies.Common.MainZombies;
import fr.exodeus.zombies.Common.Reference;
import fr.exodeus.zombies.Common.Saver.Network.NetworkHandler;
import fr.exodeus.zombies.Common.Saver.Network.PacketUpdateClient;
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

	public float thirstLevel;
	public float thirstSaturation;
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
				thirstSaturation = Math.max(thirstSaturation - 1f, 0);
			} else {
				thirstLevel = Math.max(thirstLevel - 1, 0);
			}
		}

		this.computeExhaustion(player);

		this.writeData();

		NetworkHandler.networkWrapper.sendTo(new PacketUpdateClient(this), (EntityPlayerMP) player);//passer les info au client

		player.addChatComponentMessage(
				new TextComponentString(thirstExhaustion + " / " + thirstSaturation + " / " + thirstLevel));

	}

	public void onDeath() {
		this.thirstLevel = Reference.MAX_THIRST_LEVEL;
		this.thirstSaturation = Reference.MAX_THIRST_SATURATION;
		this.writeData();
	}

	public void writeData() {
		if (player != null) {
			NBTTagCompound oldNBT = player.getEntityData();
			NBTTagCompound nbt = oldNBT.getCompoundTag("ZombiesPlus");
			

			nbt.setFloat("level", thirstLevel);
			nbt.setFloat("saturation", thirstSaturation);

			nbt.setBoolean("poisoned", isPoisoned);
			
			if (!oldNBT.hasKey("ZombiesPlus")) {
				oldNBT.setTag("ZombiesPlus", nbt);
			}//pour passer les info au client
			
			//======A Utiliser
			
			player.getCapability(MainZombies.ZOMBIESPLUS_CAP, null).setStats((int) thirstLevel);
			
			

		}
	}

	public void readData() {
		if (player != null) {
			/*NBTTagCompound oldnbt = player.getEntityData();
			NBTTagCompound nbt = oldnbt.getCompoundTag("ZombiesPlus");
			if (nbt.hasKey("level")) {
				thirstLevel = nbt.getFloat("level");
				thirstSaturation = nbt.getFloat("saturation");

				isPoisoned = nbt.getBoolean("poisoned");
			}*/
			
			this.thirstLevel = player.getCapability(MainZombies.ZOMBIESPLUS_CAP, null).thirstLevel;
			
		}
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

		// marche pas pck update plus apres 1ere mort

		return 70;

	}

	private boolean isNight() {

		return !player.worldObj.isDaytime();
	}
	
	
	
	
}
