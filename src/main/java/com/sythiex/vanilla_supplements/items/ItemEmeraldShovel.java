package com.sythiex.vanilla_supplements.items;

import com.sythiex.vanilla_supplements.VanillaSupplements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class ItemEmeraldShovel extends ItemSpade
{
	public ItemEmeraldShovel(ToolMaterial p_i45353_1_)
	{
		super(p_i45353_1_);
		setUnlocalizedName("emeraldShovel");
		setTextureName(VanillaSupplements.MODID + ":emeraldShovel");
		setCreativeTab(CreativeTabs.tabTools);
	}
}