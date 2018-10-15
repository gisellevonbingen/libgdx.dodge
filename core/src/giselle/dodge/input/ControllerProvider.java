package giselle.dodge.input;

import java.util.List;

public abstract class ControllerProvider
{
	public ControllerProvider()
	{

	}

	public abstract void provide(List<Controller> controllers);

}
