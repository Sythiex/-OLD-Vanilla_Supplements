package com.sythiex.vanillasupplements.items;

import com.sythiex.vanillasupplements.VanillaSupplements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;

public class ItemEmeraldHoe extends ItemHoe
{
	public ItemEmeraldHoe(ToolMaterial p_i45343_1_)
	{
		super(p_i45343_1_);
		setUnlocalizedName("emeraldHoe");
		setTextureName(VanillaSupplements.MODID + ":emeraldHoe");
		setCreativeTab(CreativeTabs.tabTools);
	}
}