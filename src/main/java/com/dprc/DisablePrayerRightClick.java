package com.dprc;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import java.awt.event.MouseEvent;
import net.runelite.api.Client;
import net.runelite.api.events.ClientTick;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.MouseListener;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;


@Slf4j
@PluginDescriptor(
	name = "Disable Prayer Right Click"
)
public class DisablePrayerRightClick extends Plugin
{
	@Inject
	private Client client;

	@Inject
	ClientThread clientThread;

	@Inject
	private MouseManager mouseManager;

	private boolean isPrayerPanelOpen;

	private final MouseListener mouseListener = new MouseListener()
	{
		@Override
		public MouseEvent mousePressed(MouseEvent e)
		{
			Widget prayerPanel = client.getWidget(InterfaceID.PRAYERBOOK,0);

			if(prayerPanel != null)
			{
				if (e.getButton() == MouseEvent.BUTTON3)
				{
					if(isPrayerPanelOpen && prayerPanel.getBounds().contains(e.getX(), e.getY()))
					{
						e.consume();
					}
				}
			}

			return e;
		}

		@Override
		public MouseEvent mouseReleased(MouseEvent e) { return e; }
		@Override
		public MouseEvent mouseClicked(MouseEvent e) { return e; }
		@Override
		public MouseEvent mouseEntered(MouseEvent e) { return e; }
		@Override
		public MouseEvent mouseExited(MouseEvent e) { return e; }
		@Override
		public MouseEvent mouseDragged(MouseEvent e) { return e; }
		@Override
		public MouseEvent mouseMoved(MouseEvent e) { return e; }
	};

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		Widget prayerPanel = client.getWidget(InterfaceID.PRAYERBOOK,0);

		if(prayerPanel != null)
		{
			clientThread.invoke(() ->
			{
				isPrayerPanelOpen = !prayerPanel.isHidden();
			});
		}
	}

	@Override
	protected void startUp() throws Exception
	{
		mouseManager.registerMouseListener(mouseListener);
	}

	@Override
	protected void shutDown() throws Exception
	{
		mouseManager.unregisterMouseListener(mouseListener);
	}
}
