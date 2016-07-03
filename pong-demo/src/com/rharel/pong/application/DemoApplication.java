package com.rharel.pong.application;


import java.util.List;

import com.rharel.pong.ai.AggressiveAI;
import com.rharel.pong.ai.PredictorAI;
import com.rharel.pong.ai.TrackerAI;
import com.rharel.pong.core.Ball;
import com.rharel.pong.core.Game;
import com.rharel.pong.core.HumanPlayer;
import com.rharel.pong.core.Paddle;
import com.rharel.pong.core.Player;
import com.rharel.pong.core.Table;
import com.rharel.pong.geometry.Size;
import com.rharel.pong.input.KeyboardController;
import com.rharel.pong.util.Pair;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;


public class DemoApplication extends Application
{
	private static final String WINDOW_TITLE = "Pong";
	private static final Size WINDOW_SIZE = new Size(500, 500);
	
	private static final Color COLOR_BACKGROUND = Color.BLACK;
	private static final Color COLOR_PADDLE = Color.LIGHTGREEN;
	private static final Color COLOR_BALL = Color.LIGHTGREEN;

	private static final float GAME_PADDLE_WIDTH_RELATIVE = 0.2f;
	private static final float GAME_PADDLE_HEIGHT = 10.0f;
	private static final float GAME_PADDLE_SPEED_RELATIVE = 0.5f;
	private static final float GAME_BALL_RADIUS = 10.0f;
	private static final float GAME_BALL_SPEED_RELATIVE = 1.0f;
	private static final float GAME_BALL_MAX_BOUNCE_ANGLE = (float) Math.toRadians(50);
	private static final float GAME_AI_BRAKING_DISTANCE_RELATIVE = 0.01f;
	private static final float GAME_WINNER_DELAY = 1000;  // in milliseconds
	
	private static final KeyCode KEY_MOVE_LEFT = KeyCode.A;
	private static final KeyCode KEY_MOVE_RIGHT = KeyCode.D;
	private static final KeyCode KEY_TOGGLE_PAUSE = KeyCode.P;
	
	public static void main(final String[] args)
	{
		launch(args);
	}
	@Override
	public void start(
		final Stage stage)
	{
		setupGame();
		setupGUI(stage);
		setupEventListeners();
		enterMainLoop();
	}

	private void setupGame()
	{
		final Pair<Paddle> paddles = setupPaddles();
		final Ball ball = setupBall();
		final Table table = new Table(WINDOW_SIZE, paddles, ball);
		final Pair<Player> players = setupPlayers();

		game = new Game(table, players);
	}
	private Pair<Paddle> setupPaddles()
	{
		final Size paddleSize = new Size(
			GAME_PADDLE_WIDTH_RELATIVE * WINDOW_SIZE.width,
			GAME_PADDLE_HEIGHT);
		final float paddleSpeed =
			GAME_PADDLE_SPEED_RELATIVE * WINDOW_SIZE.width;

		final Paddle firstPaddle = new Paddle(paddleSize, paddleSpeed);
		final Paddle secondPaddle = new Paddle(paddleSize, paddleSpeed);
		
		return new Pair<Paddle>(firstPaddle, secondPaddle);
	}
	private Ball setupBall()
	{
		 return new Ball(
			GAME_BALL_RADIUS,
			GAME_BALL_SPEED_RELATIVE * WINDOW_SIZE.height,
			GAME_BALL_MAX_BOUNCE_ANGLE);
	}
	private Pair<Player> setupPlayers()
	{
		final List<String> args = getParameters().getRaw();
		if (args.size() == 2)
		{
			playerTypes = new Pair<String>(args.get(0), args.get(1));
		}
		
		final Player firstPlayer = setupPlayer(
			Player.Position.FIRST, playerTypes.first);
		final Player secondPlayer = setupPlayer(
			Player.Position.SECOND, playerTypes.second);
		
		return new Pair<Player>(firstPlayer, secondPlayer);
	}
	private Player setupPlayer(
		final Player.Position position,
		final String type)
	{
		final float brakingDistance =
			GAME_AI_BRAKING_DISTANCE_RELATIVE * WINDOW_SIZE.width;
		switch (type)
		{
			case PLAYER_TYPE_HUMAN:
			{
				humanPlayer = new HumanPlayer(position);
				return humanPlayer;
			}
			case PLAYER_TYPE_AI_TRACKER:
			{
				return new TrackerAI(position, brakingDistance);
			}
			case PLAYER_TYPE_AI_PREDICTOR:
			{
				return new PredictorAI(position, brakingDistance);
			}
			default:
			{
				return new AggressiveAI(position, brakingDistance);
			}
		}
	}
	private void setupGUI(final Stage stage)
	{
		this.stage = stage;
		
		final Group root = new Group();
		scene = new Scene(root);
		stage.setScene(scene);
		
		final Canvas canvas = new Canvas(WINDOW_SIZE.width, WINDOW_SIZE.height);
		root.getChildren().add(canvas);

		graphicsContext = canvas.getGraphicsContext2D();
		paint(graphicsContext);
		
		updateScoreDisplay();
		
		stage.show();
	}
	private void setupEventListeners()
	{
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) ->
		{
			if (key.getCode() == KeyCode.ESCAPE)
			{
				pause();
				stage.close();
			}
			if (key.getCode() == KEY_TOGGLE_PAUSE)
			{
				togglePause();
			}
			else if(key.getCode() == KEY_MOVE_LEFT)
		    {
				controller.pressLeft();
		    }
			else if (key.getCode() == KEY_MOVE_RIGHT)
			{
				controller.pressRight();
			}
			if (humanPlayer != null)
			{
				humanPlayer.command = controller.getCommand();
			}
		});
		scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) ->
		{
		    if(key.getCode() == KEY_MOVE_LEFT)
		    {
		    	controller.releaseLeft();
		    }
		    else if (key.getCode() == KEY_MOVE_RIGHT)
		    {
		    	controller.releaseRight();
		    }
		    if (humanPlayer != null)
		    {
		    	humanPlayer.command = controller.getCommand();
		    }
		});
	}
	private void enterMainLoop()
	{
	    mainLoop = new AnimationTimer()
	    {
	    	@Override
	    	public void start()
	    	{
	    		previousTime = System.nanoTime();
	    		super.start();
	    	}
	        @Override
			public void handle(final long currentTime)
	        {
	            final float dt =
	            	(currentTime - previousTime) /
	            	1000000000.0f;
	            previousTime = currentTime;

	            final Player.Position winner = game.advance(dt);
	            if (winner != null)
	            {
	            	updateScoreDisplay();
	            	pause();
	            	final Timeline winnerDelay = new Timeline(
	            		new KeyFrame(
	            			Duration.millis(0.5f * GAME_WINNER_DELAY),
	            			actionEvent ->
	            			{
	            				game.setup();
	            				paint(graphicsContext);
            				}),
	            		new KeyFrame(
	            			Duration.millis(GAME_WINNER_DELAY),
	            			actionEvent ->
	            			{
	            				resume();
            				}));
	            	winnerDelay.play();
	            }
	            else
	            {
		            game.updatePlayers();
	            }
	            
	            paint(graphicsContext);
	        }
	        private long previousTime;
	    };
	    resume();
	}
	
	private void paint(
		final GraphicsContext context)
	{
		paintBackground(context);
		paintGameState(context);
	}
	private void paintBackground(
		final GraphicsContext context)
	{
		context.save();

		final Canvas canvas = context.getCanvas();
		context.setFill(COLOR_BACKGROUND);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		context.restore();
	}
	private void paintGameState(
		final GraphicsContext context)
	{
		paintPaddle(game.table.paddles.first, context);
		paintPaddle(game.table.paddles.second, context);
		paintBall(game.table.ball, context);
	}
	private void paintPaddle(
		final Paddle paddle,
		final GraphicsContext context)
	{
		final float x = paddle.position.x;
		final float y = paddle.position.y;
		final float width = paddle.size.width;
		final float height = paddle.size.height;
		final float half_width = 0.5f * width;
		final float half_height = 0.5f * height;
		
		context.save();
		context.setFill(COLOR_PADDLE);
		context.fillRect(
			x - half_width,
			y - half_height,
			width,
			height);
		context.restore();
	}
	private void paintBall(
		final Ball ball,
		final GraphicsContext context)
	{
		final float x = ball.position.x - ball.radius;
		final float y = ball.position.y - ball.radius;
		final float diameter = 2 * ball.radius;
		
		context.save();
		context.setFill(COLOR_BALL);
		context.fillArc(
			x, y,
			diameter, diameter,
			0, 360, ArcType.OPEN);
		context.restore();
	}

	private void updateScoreDisplay()
	{
		final Pair<Integer> score = game.getScore();
    	stage.setTitle(
    		"[" + WINDOW_TITLE + "] - " +
    		"Bottom : Top - " +
			score.second + " : " + score.first);
	}
	private void pause()
	{
		mainLoop.stop();
		isPaused = true;
	}
	private void resume()
	{
		isPaused = false;
		mainLoop.start();
	}
	private void togglePause()
	{
		if (isPaused) { resume(); }
		else { pause(); }
	}
	
	private static final String PLAYER_TYPE_AI_TRACKER = "tracker";
	private static final String PLAYER_TYPE_AI_PREDICTOR = "predictor";
	private static final String PLAYER_TYPE_AI_AGGRESSIVE = "aggressive";
	private static final String PLAYER_TYPE_HUMAN = "human";
	
	private Pair<String> playerTypes = new Pair<String>(
		PLAYER_TYPE_AI_AGGRESSIVE,
		PLAYER_TYPE_HUMAN);
	
	private Game game;
	private HumanPlayer humanPlayer;
	private final KeyboardController controller = new KeyboardController();
	
	private Stage stage;
	private Scene scene;
	private GraphicsContext graphicsContext;
	private AnimationTimer mainLoop;
	private boolean isPaused = true;
}
