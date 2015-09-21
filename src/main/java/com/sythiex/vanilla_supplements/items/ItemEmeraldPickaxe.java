package com.sythiex.vanilla_supplements.items;

import com.sythiex.vanilla_supplements.VanillaSupplements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class ItemEmeraldPickaxe extends ItemPickaxe
{
	public ItemEmeraldPickaxe(ToolMaterial p_i45347_1_)
	{
		super(p_i45347_1_);
		setUnlocalizedName("emeraldPickaxe");
		setTextureName(VanillaSupplements.MODID + ":emeraldPickaxe");
		setCreativeTab(CreativeTabs.tabTools);
	}
}