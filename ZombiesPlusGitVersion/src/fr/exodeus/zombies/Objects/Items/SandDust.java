package fr.exodeus.zombies.Objects.Items;

import fr.exodeus.zombies.Common.MainZombies;
import fr.exodeus.zombies.Common.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SandDust {
	public static Item sand_dust;

	public static Integer maxStackSize = 64;
	public static CreativeTabs creativeTab = MainZombies.tabZombies;

	public static void init() {
		sand_dust = new Item().setUnlocalizedName("sand_dust").setMaxStackSize(maxStackSize)
				.setCreativeTab(creativeTab);

	}

	public static void register() {
		GameRegistry.registerItem(sand_dust, sand_dust.getUnlocalizedName().substring(5));
	}

	public static void registeerRednders() {
		registerRender(sand_dust);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(
				Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
