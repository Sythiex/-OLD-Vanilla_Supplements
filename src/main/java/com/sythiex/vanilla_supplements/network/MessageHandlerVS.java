package com.sythiex.vanilla_supplements.network;

import com.sythiex.vanilla_supplements.VanillaSupplements;
import com.sythiex.vanilla_supplements.items.ItemWand;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class MessageHandlerVS implements IMessageHandler<MessageVS, IMessage>
{
	@Override
	public IMessage onMessage(MessageVS message, MessageContext ctx)
	{
		EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
		ItemStack itemStack = serverPlayer.inventory.getCurrentItem();
		if(itemStack.getItem() == VanillaSupplements.wand)
		{
			int mode = itemStack.stackTagCompound.getInteger("mode");
			++mode;
			if(mode > ItemWand.numberOfModes)
				mode = 1;
			itemStack.stackTagCompound.setInteger("mode", mode);
			serverPlayer.addChatMessage(new ChatComponentText("Mode: " + ItemWand.getMode(mode)));
		}
		return null;
	}
}