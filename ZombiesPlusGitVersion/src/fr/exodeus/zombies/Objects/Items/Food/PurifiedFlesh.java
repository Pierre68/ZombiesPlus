package fr.exodeus.zombies.Objects.Items.Food;

import fr.exodeus.zombies.Common.MainZombies;
import fr.exodeus.zombies.Common.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PurifiedFlesh extends ItemFood{
	public PurifiedFlesh() {
		super(2, 1.5F, true);
		setUnlocalizedName("purified_flesh");
		setMaxStackSize(maxStackSize);
		setCreativeTab(creativeTab);
	}
	
	public static ItemFood purified_flesh;

	public static Integer maxStackSize = 64;
	public static CreativeTabs creativeTab = MainZombies.tabZombies;

	public static void init() {
		purified_flesh = new PurifiedFlesh();

	}

	public static void register() {
		GameRegistry.registerItem(purified_flesh, purified_flesh.getUnlocalizedName().substring(5));
	}

	public static void registeerRednders() {
		registerRender(purified_flesh);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(
				Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
