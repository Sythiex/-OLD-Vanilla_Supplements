package com.sythiex.vanillasupplements.items;

import com.sythiex.vanillasupplements.VanillaSupplements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class ItemEmeraldAxe extends ItemAxe
{
	public ItemEmeraldAxe(ToolMaterial material)
	{
		super(material);
		setUnlocalizedName("emeraldAxe");
		setTextureName(VanillaSupplements.MODID + ":emeraldAxe");
		setCreativeTab(CreativeTabs.tabTools);
	}

}