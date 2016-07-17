package fr.exodeus.zombies.Objects.Items.Food;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Donut extends ItemFood{

	public Donut() {
		super(4, 5.2F, false);
		setUnlocalizedName("donut");
		setMaxStackSize(maxStackSize);
		setCreativeTab(creativeTab);
	}
	
	public static ItemFood donut;

	public static Integer maxStackSize = 8;
	public static CreativeTabs creativeTab = MainZombies.tabZombies;

	public static void init() {
		donut = new Donut();
	}

	public static void register() {
		GameRegistry.registerItem(donut, donut.getUnlocalizedName().substring(5));
	}

	public static void registeerRednders() {
		registerRender(donut);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(
				Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

}
