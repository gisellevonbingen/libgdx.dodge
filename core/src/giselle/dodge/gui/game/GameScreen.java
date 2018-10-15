package giselle.dodge.gui.game;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import giselle.dodge.Dodge;
import giselle.dodge.entity.Entity;
import giselle.dodge.entity.EntityPlayer;
import giselle.dodge.gui.Alignment;
import giselle.dodge.gui.Button;
import giselle.dodge.gui.CloseConfirmLayer;
import giselle.dodge.gui.Screen;
import giselle.dodge.gui.main.MainScreen;
import giselle.dodge.input.Controller;
import giselle.dodge.render.EntityRenderer;
import giselle.dodge.render.RenderEngine;
import giselle.dodge.render.StringRenderCache;
import giselle.dodge.render.TextureManager;
import giselle.dodge.utils.EventArgs;
import giselle.dodge.utils.IEventHandler;
import giselle.dodge.utils.LimitTimer;

public class GameScreen extends Screen
{
	private GamePhase phase = null;

	private World world = null;
	private EntityPlayer player = null;

	private EntityRenderer entityRenderer = null;

	private ToastMessage toastMessage = null;
	private Button restartButton = null;
	private Button closeButton = null;

	private int level = 0;
	private LimitTimer levelTimer = null;
	private LimitTimer liveTimer = null;

	public GameScreen()
	{
		this.world = new World();
		this.player = new EntityPlayer(this.world);

		this.entityRenderer = new EntityRenderer(this.world);

		Dodge game = Dodge.instance();
		TextureManager textureManager = game.getTextureManager();
		RenderEngine renderEngine = game.getRenderEngine();
		Viewport viewport = renderEngine.getViewport();
		ControlCollection children = this.getChildren();

		this.restartButton = new Button();
		this.restartButton.setText("다시시작");
		this.restartButton.setSize(400, 200);
		this.restartButton.setVisible(false);
		children.add(this.restartButton);

		this.closeButton = new Button();
		this.closeButton.setSize(150, 150);
		this.closeButton.setLocation(viewport.getWorldWidth() - this.closeButton.getWidth() - 50, viewport.getWorldHeight() - this.closeButton.getHeight() - 50);
		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(new TextureRegion(textureManager.get("close.png")));
		this.closeButton.setUpDrawable(textureRegionDrawable.tint(Color.BLACK));
		this.closeButton.setDownDrawable(textureRegionDrawable.tint(Color.RED));
		children.add(this.closeButton);

		this.levelTimer = new LimitTimer(1.0F);
		this.liveTimer = new LimitTimer();
	}

	private void resetGame()
	{
		List<Entity> entities = this.world.getEntities();
		entities.clear();
		entities.add(this.player);

		Vector2 worldSize = this.world.getSize();
		Vector2 playerSize = this.player.getSize();
		this.player.setPosition(Alignment.middleCenter.align(worldSize, playerSize, true));

		this.setPhase(new GamePhaseReady());
		this.setLevel(0);
		this.liveTimer.resetElapsed();
	}

	@Override
	public void onOpen()
	{
		super.onOpen();

		this.resetGame();
	}

	@Override
	protected void onSizeChanged(EventArgs e)
	{
		super.onSizeChanged(e);

		Dodge game = Dodge.instance();
		Rectangle renderBounds = game.getRenderEngine().getRenderBounds();

		Vector2 size = new Vector2(renderBounds.width, renderBounds.height);
		this.world.setSize(size);

		Vector2 restartButtonSize = this.restartButton.getSize();
		this.restartButton.setX(renderBounds.x + (renderBounds.width - restartButtonSize.x) / 2);
		this.restartButton.setY(renderBounds.y + (renderBounds.height - restartButtonSize.y) / 2 - 100);
	}

	@Override
	public boolean onHandleInput()
	{
		if (super.onHandleInput() == false)
		{
			return false;
		}

		Controller controller = Dodge.instance().getInputManager().getSelectedController();
		controller.handleInput();

		if (this.phase != null)
		{
			this.phase.update(this);
		}

		if (this.toastMessage != null)
		{
			this.toastMessage.update();
		}

		boolean isAlive = (this.phase instanceof GamePhaseOver) == false;

		if (isAlive == true)
		{
			this.world.update();

			if ((this.phase instanceof GamePhaseReady) == false)
			{
				this.liveTimer.update();
			}

			this.player.accept(controller);
		}
		else
		{
			if (controller.isRestart() == true || (this.restartButton.isVisible() == true && this.restartButton.isClicked() == true))
			{
				this.resetGame();
			}

		}

		if (this.closeButton.isClicked() == true)
		{
			if (isAlive == true)
			{
				CloseConfirmLayer layer = new CloseConfirmLayer();
				layer.setText("정말로 메인화면으로 이동하시겠습니까?");
				layer.getDoneEvent().add(new IEventHandler<EventArgs>()
				{
					@Override
					public void onPost(EventArgs e)
					{
						Dodge.instance().setCurrentScreen(new MainScreen());
					}
				});
				this.getLayers().add(layer);
			}
			else
			{
				Dodge.instance().setCurrentScreen(new MainScreen());
			}

		}

		return true;
	}

	@Override
	protected void renderMiddle()
	{
		super.renderMiddle();

		this.entityRenderer.render();

		{
			RenderEngine renderEngine = Dodge.instance().getRenderEngine();

			StringRenderCache src = new StringRenderCache();
			src.setText("Live Time : " + String.format("%.1f", this.liveTimer.getElapsedTime()));
			src.setFont(40);

			src.renderBounds(renderEngine.getSpriteBatch(), renderEngine.getRenderBounds(), Alignment.bottomLeft);
		}

		if (this.toastMessage != null)
		{
			LimitTimer timer = this.toastMessage.getTimer();

			if (timer.isOver() != true)
			{
				this.toastMessage.render();
			}
			else
			{
				this.toastMessage = null;
			}

		}

		Controller controller = Dodge.instance().getInputManager().getSelectedController();
		controller.render();

	}

	public int getLevel()
	{
		return this.level;
	}

	public void setLevel(int level)
	{
		this.level = level;

		this.getLevelTimer().resetElapsed();
	}

	public LimitTimer getLevelTimer()
	{
		return this.levelTimer;
	}

	public World getWorld()
	{
		return this.world;
	}

	public EntityPlayer getPlayer()
	{
		return this.player;
	}

	public GamePhase getPhase()
	{
		return this.phase;
	}

	public void setPhase(GamePhase phase)
	{
		this.phase = phase;

		this.restartButton.setVisible(phase instanceof GamePhaseOver);
	}

	public ToastMessage getToastMessage()
	{
		return this.toastMessage;
	}

	public void setToastMessage(ToastMessage toastMessage)
	{
		this.toastMessage = toastMessage;
	}

}
