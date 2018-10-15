package giselle.dodge.utils;

public interface IEventHandler<T extends EventArgs>
{
	public void onPost(T e);
}
