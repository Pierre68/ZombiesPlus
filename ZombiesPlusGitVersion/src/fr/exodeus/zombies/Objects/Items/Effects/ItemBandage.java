package fr.exodeus.zombies.Objects.Items.Effects;

import fr.exodeus.zombies.Common.MainZombies;
import fr.exodeus.zombies.Objects.Potion.Bonebreak;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBandage extends Item {

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player,
			EnumHand hand) {

		player.setHealth(player.getHealth() + 1);
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 200)); // regeneration
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 400, 1)); // resistance

		player.removePotionEffect(Bonebreak.bonebreak);
		player.removePotionEffect(Potion.getPotionById(2)); // move slow

		--itemStackIn.stackSize;

		return super.onItemRightClick(itemStackIn, worldIn, player, hand);
	}

}
