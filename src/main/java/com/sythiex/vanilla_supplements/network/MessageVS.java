package com.sythiex.vanilla_supplements.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageVS implements IMessage
{
	private boolean toSend;
	
	public MessageVS(){}
	
	public MessageVS(boolean toSend)
	{
		this.toSend = toSend;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(toSend);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		toSend = buf.readBoolean();
	}
}