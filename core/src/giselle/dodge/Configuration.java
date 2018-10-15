package giselle.dodge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Configuration
{
	private Preferences preferences;

	public String controller;

	public Configuration()
	{
		this.preferences = Gdx.app.getPreferences("giselle.dodge.configuration");
		this.controller = this.preferences.getString("controller");
	}

	public void save()
	{
		this.preferences.putString("controller", this.controller);

		this.preferences.flush();
	}

}
