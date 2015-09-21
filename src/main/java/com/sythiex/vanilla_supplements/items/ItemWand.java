package com.sythiex.vanilla_supplements.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWand extends Item
{
	private int mode = 0;
	
	public ItemWand()
	{
		
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float sideX, float sideY, float sideZ)
    {
		System.out.println("onItemUse");
		if(!player.canPlayerEdit(x, y, z, side, itemStack) || world.isRemote)
        {
            return false;
        }
		else
		{
			
			return false;
		}
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
		System.out.println("onItemRightClick");
		
        return itemStack;
    }
}