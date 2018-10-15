package giselle.dodge.desktop;

import java.util.List;

import giselle.dodge.input.Controller;
import giselle.dodge.input.ControllerDrag;
import giselle.dodge.input.ControllerKeyboard;
import giselle.dodge.input.ControllerProvider;
import giselle.dodge.input.ControllerTocuhpad;

public class DesktopControllerProvider extends ControllerProvider
{
	@Override
	public void provide(List<Controller> controllers)
	{
		controllers.add(new ControllerKeyboard());
		controllers.add(new ControllerTocuhpad());
		controllers.add(new ControllerDrag());
	}

}
