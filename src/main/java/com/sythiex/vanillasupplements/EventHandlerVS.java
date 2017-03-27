package com.sythiex.vanillasupplements;

import com.sythiex.vanillasupplements.network.ClientProxyVS;
import com.sythiex.vanillasupplements.network.MessageVS;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;

public class EventHandlerVS
{
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(KeyInputEvent event)
	{
		KeyBinding wandMode = ClientProxyVS.wandMode;
		if(wandMode.isPressed())
		{
			VanillaSupplements.network.sendToServer(new MessageVS(true));
		}
	}
}