package com.sythiex.vanilla_supplements.items;

import com.sythiex.vanilla_supplements.VanillaSupplements;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemVS extends Item
{
	public ItemVS()
	{
		
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float sideX, float sideY, float sideZ)
    {
        if (!player.canPlayerEdit(x, y, z, side, itemStack))
        {
            return false;
        }
        else
        {
            if (itemStack.getItem() == VanillaSupplements.fertilizer)
            {
                if (ItemDye.applyBonemeal(itemStack, world, x, y, z, player))
                {
                    if (!world.isRemote)
                    {
                        world.playAuxSFX(2005, x, y, z, 0);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}