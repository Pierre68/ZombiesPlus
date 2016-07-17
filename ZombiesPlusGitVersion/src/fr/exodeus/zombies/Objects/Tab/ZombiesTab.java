package fr.exodeus.zombies.Objects.Tab;

import fr.exodeus.zombies.Objects.Items.Usable.Antibiotic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ZombiesTab extends CreativeTabs{

	public ZombiesTab() {
		super("zombies_tab");
	}
	
	@Override
	public Item getTabIconItem() {
		return Antibiotic.antibiotic;
	}
}
