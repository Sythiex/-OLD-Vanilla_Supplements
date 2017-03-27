package com.sythiex.vanillasupplements.items;

import com.sythiex.vanillasupplements.VanillaSupplements;

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