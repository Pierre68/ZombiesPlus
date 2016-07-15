package fr.exodeus.zombies.Objects.Items.Effects;

import fr.exodeus.zombies.Common.MainZombies;
import fr.exodeus.zombies.Common.Reference;
import fr.exodeus.zombies.Objects.Potion.Infection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemAntibiotic extends Item {

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player,
			EnumHand hand) {

		--itemStackIn.stackSize;

		if (player.isPotionActive(Infection.infection))
			if (player.worldObj.isRemote)
				player.addChatMessage(new TextComponentString("§cInfection healed"));

		player.setHealth(player.getHealth() + 1);
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 400, 1)); // regeneration
		player.removePotionEffect(Potion.getPotionById(17)); // hunger
		player.removePotionEffect(Potion.getPotionById(9)); // confusion
		player.removePotionEffect(Potion.getPotionById(15)); // blindness
		player.removePotionEffect(Potion.getPotionById(9)); // poison
		player.removePotionEffect(Potion.getPotionById(4)); // dig slow down
		player.removePotionEffect(Potion.getPotionById(16)); // night vision

		player.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, 1F, 1F);

		player.removePotionEffect(Infection.infection);

		return super.onItemRightClick(itemStackIn, worldIn, player, hand);
	}
}
