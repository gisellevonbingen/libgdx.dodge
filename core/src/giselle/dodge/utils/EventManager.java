package giselle.dodge.utils;

import java.util.ArrayList;
import java.util.List;

public class EventManager<T extends EventArgs>
{
	private final EventHandlerCollection<T> handlers;

	public EventManager()
	{
		this.handlers = new EventHandlerCollection<>();
	}

	public EventHandlerCollection<T> getHandlers()
	{
		return this.handlers;
	}

	public void post(T e)
	{
		for (IEventHandler<T> handler : new ArrayList<>(this.handlers.handlers))
		{
			handler.onPost(e);
		}

	}

	public static class EventHandlerCollection<T extends EventArgs>
	{
		private List<IEventHandler<T>> handlers;

		public EventHandlerCollection()
		{
			this.handlers = new ArrayList<>();
		}

		public void add(IEventHandler<T> handler)
		{
			this.handlers.add(handler);
		}

		public void remove(IEventHandler<T> handler)
		{
			this.handlers.remove(handler);
		}

		public boolean conatins(IEventHandler<T> handler)
		{
			return this.handlers.contains(handler);
		}

	}

}
