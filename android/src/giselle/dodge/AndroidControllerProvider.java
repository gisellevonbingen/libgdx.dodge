package giselle.dodge;

import java.util.List;

import giselle.dodge.input.Controller;
import giselle.dodge.input.ControllerDrag;
import giselle.dodge.input.ControllerProvider;
import giselle.dodge.input.ControllerTocuhpad;

public class AndroidControllerProvider extends ControllerProvider
{
	public AndroidControllerProvider()
	{

	}

	@Override
	public void provide(List<Controller> controllers)
	{
		controllers.add(new ControllerTocuhpad());
		controllers.add(new ControllerDrag());
	}

}
