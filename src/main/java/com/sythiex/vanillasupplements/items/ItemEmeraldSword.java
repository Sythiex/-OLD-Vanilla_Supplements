package com.sythiex.vanillasupplements.items;

import com.sythiex.vanillasupplements.VanillaSupplements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class ItemEmeraldSword extends ItemSword
{
	public ItemEmeraldSword(ToolMaterial p_i45356_1_)
	{
		super(p_i45356_1_);
		setUnlocalizedName("emeraldSword");
		setTextureName(VanillaSupplements.MODID + ":emeraldSword");
		setCreativeTab(CreativeTabs.tabCombat);
	}
}