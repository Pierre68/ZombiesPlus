package fr.exodeus.zombies.Objects.Items.Food;

import fr.exodeus.zombies.Common.MainZombies;
import fr.exodeus.zombies.Common.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CookedFlesh extends ItemFood{
	public CookedFlesh() {
		super(4, 3.0F, true);
		setUnlocalizedName("cooked_flesh");
		setMaxStackSize(maxStackSize);
		setCreativeTab(creativeTab);
	}
	
	public static ItemFood cooked_flesh;

	public static Integer maxStackSize = 64;
	public static CreativeTabs creativeTab = MainZombies.tabZombies;

	public static void init() {
		cooked_flesh = new CookedFlesh();

	}

	public static void register() {
		GameRegistry.registerItem(cooked_flesh, cooked_flesh.getUnlocalizedName().substring(5));
	}

	public static void registeerRednders() {
		registerRender(cooked_flesh);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(
				Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
