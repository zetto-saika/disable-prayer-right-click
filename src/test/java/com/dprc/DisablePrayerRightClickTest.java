package com.dprc;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DisablePrayerRightClickTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(DisablePrayerRightClick.class);
		RuneLite.main(args);
	}
}