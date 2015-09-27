package com.sythiex.vanilla_supplements.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class ItemFoodVS extends ItemFood
{
	public boolean messagePlayed = false;
	
	public ItemFoodVS(int healAmount, float saturationMod) 
	{
		super(healAmount, saturationMod, false);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (player.canEat(false))
        {
        	if(!messagePlayed && player.getDisplayName() == "Zodiac17")
        	{
        		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("Zodiac17: 'OOOOOOHHHHHHHH, THIS BERRY IS SOOO DELICIOUS!!! THE FEELING OF IT SQUIRTING ITS JUICES INTO MY MOUTH IS EXQUISITE!!! AND TO THINK I HAVE BEEN MISSING OUT ON THIS EXPERIENCE ALL THESE YEARS BECAUSE I REFUSED TO PLAY WITH NATURA!!! OH, HOW I HAVE WRONGED YOU, BUSHES OF DANGLING JUICE-FILLED BALLS!!!'"));
        		messagePlayed = true;
        	}
            player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }
        return itemStack;
    }
}
