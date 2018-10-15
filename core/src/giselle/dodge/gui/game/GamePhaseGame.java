package giselle.dodge.gui.game;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import giselle.dodge.entity.Entity;
import giselle.dodge.entity.EntityBullet;
import giselle.dodge.entity.EntityPlayer;
import giselle.dodge.gui.Alignment;
import giselle.dodge.utils.LimitTimer;
import giselle.dodge.utils.RectangleUtils;

public class GamePhaseGame extends GamePhase
{
	private LimitTimer bulletTimer = null;
	private Alignment[] spawnDirections = null;

	public GamePhaseGame()
	{
		this.bulletTimer = new LimitTimer(1.0F);

		this.spawnDirections = new Alignment[]{Alignment.middleLeft, Alignment.topCenter, Alignment.middleRight, Alignment.bottomCenter};
	}

	private Vector2 getWorlRandomPosition(Random rand, Vector2 worldSize, Vector2 entitySize)
	{
		float x = rand.nextInt((int) (worldSize.x - entitySize.x));
		float y = rand.nextInt((int) (worldSize.y - entitySize.y));

		return new Vector2(x, y);
	}

	private Vector2 getSpawnPosition(Random rand, Vector2 worldSize, Vector2 entitySize)
	{
		Vector2 position = this.getWorlRandomPosition(rand, worldSize, entitySize);
		int ai = rand.nextInt(this.spawnDirections.length);
		Alignment alignment = this.spawnDirections[ai];

		if (alignment.getX() != 0)
		{
			position.x = (worldSize.x - entitySize.x) * (alignment.getX() + 1) / 2;
		}

		if (alignment.getY() != 0)
		{
			position.y = (worldSize.y - entitySize.y) * (alignment.getY() + 1) / 2;
		}

		return position;
	}

	@Override
	public void update(GameScreen gameScreen)
	{
		Random rand = new Random();

		EntityPlayer player = gameScreen.getPlayer();
		Rectangle playerBounds = player.getBounds();

		World world = gameScreen.getWorld();
		List<Entity> entities = world.getEntities();
		Vector2 worldSize = world.getSize();

		LimitTimer levelTimer = gameScreen.getLevelTimer();
		levelTimer.update();

		if (levelTimer.isOver() == true)
		{
			int nextLevel = gameScreen.getLevel() + 1;
			gameScreen.setLevel(nextLevel);
		}

		this.bulletTimer.update();

		if (this.bulletTimer.isOver() == true)
		{
			this.bulletTimer.resetElapsed();
			
			int bulletCount =  gameScreen.getLevel();

			for (int i = 0; i < bulletCount; i++)
			{
				EntityBullet bullet = new EntityBullet(world);
				Vector2 bulletSize = bullet.getSize();
				Vector2 bulletPosition = bullet.setPosition(this.getSpawnPosition(rand, worldSize, bulletSize));

				Vector2 target = this.getWorlRandomPosition(rand, worldSize, bulletSize);
				double radian = Math.atan2(target.y - bulletPosition.y, target.x - bulletPosition.x);

				Vector2 vector2 = new Vector2(0, 256).setAngleRad((float) radian);

				bullet.setVelocity(vector2);
				bullet.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1.0F));
				entities.add(bullet);
			}

		}

		for (int i = 0; i < entities.size(); i++)
		{
			Entity entity = entities.get(i);

			if (entity == player)
			{
				continue;
			}

			if (RectangleUtils.intersectWith(playerBounds, entity.getBounds()) == true)
			{
				gameScreen.setPhase(new GamePhaseOver());
			}

		}

	}

}
