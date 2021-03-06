package hr.pfs.game.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import hr.pfs.game.graphics.Screen;
import hr.pfs.game.input.Keyboard;
import hr.pfs.game.main.Global.STATE;

public class Game extends Canvas implements Runnable {

	private final static String title = "New Game";

	//Game state
	public STATE state;
	
	//Game speed
	private int speed = Global.SPEED;

	//Objects
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	
	private Screen screen;
	private Keyboard keyboard;

	//Game image
	private BufferedImage currentScreen = new BufferedImage(Global.scaledWidth, Global.scaledHeight, BufferedImage.TYPE_INT_RGB);
	private int [] pixels = ((DataBufferInt)currentScreen.getRaster().getDataBuffer()).getData();

	public Game () {

		//Set window dimension
		Dimension size = new Dimension (Global.scaledWidth, Global.scaledHeight);
		setPreferredSize(size);

		//Create game objects
		screen = new Screen();
		frame = new JFrame();
		keyboard = new Keyboard();

		frame.addKeyListener(keyboard);
	}

	//Start game thread
	public synchronized void start () {
		running = true;
		state = Global.state;

		thread = new Thread(this, "Display");
		thread.start();
	}

	//Stop game thread
	public synchronized void stop () {
		running = false;
		try {
			thread.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//Thread action
	public void run () {
		
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 30;
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			
			render();
		}
		
		stop(); //TODO maybe remove
	}

	public void update () {
		if (state == STATE.GAME) {
			getKeyGame();
			screen.move();
		}
		else if (state == STATE.BATTLE) {

		}
	}

	public void getKeyGame () {
		//WASD
		if (keyboard.keys[KeyEvent.VK_W])
			screen.setMove("U");

		if (keyboard.keys[KeyEvent.VK_A])
			screen.setMove("L");

		if (keyboard.keys[KeyEvent.VK_S])
			screen.setMove("D");

		if (keyboard.keys[KeyEvent.VK_D]) 
			screen.setMove("R");

		//ARROW KEYS
		if (keyboard.keys[KeyEvent.VK_UP])
			screen.setMove("U");

		if (keyboard.keys[KeyEvent.VK_LEFT])
			screen.setMove("L");

		if (keyboard.keys[KeyEvent.VK_DOWN])
			screen.setMove("D");

		if (keyboard.keys[KeyEvent.VK_RIGHT]) 
			screen.setMove("R");

		//SPEED
		if (keyboard.keys[KeyEvent.VK_SPACE])
			setSpeed();

		if (keyboard.keys[KeyEvent.VK_SPACE] == false)
			resetSpeed();
	}

	public void setSpeed() {
		int k = 2; 
		speed*=k;
	}

	public void resetSpeed() {
		speed = Global.SPEED;
	}

	//Game graphics
	public void render () {

		//Create buffer strategy
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		if (state == STATE.GAME) {
			//Draw screen
//			screen.clear();
			screen.render();

			//Copy image pixels into current screen
			for (int i=0; i<pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
		}

		Graphics g = bs.getDrawGraphics();

		//Display graphics
		{
			//Draw map
			g.drawImage(currentScreen,0,0,getWidth(),getHeight(),null);
		}
		g.dispose();
		bs.show();
	}

	public static void main (String args []) {
		Game game = new Game();

		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.requestFocusInWindow();
		game.frame.requestFocus();
		game.frame.setFocusable(true);
		game.frame.setVisible(true);

		game.start();
	}
}

