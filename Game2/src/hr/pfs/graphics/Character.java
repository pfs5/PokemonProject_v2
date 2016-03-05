package hr.pfs.graphics;

import static hr.pfs.main.GlobalSettings.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Character {
	//Info
	private String mapName;
	private String charName;
	//Position on map
	private int x;
	private int y;

	//Movement
	private String moveRoutine;
	private int moveIndex;

	private boolean moving = true;
	private boolean turning = false;
	private int tick = 0;

	//Special main character variables
	private int mainX;
	private int mainY;

	//State
	private String direction;
	private int step;
	private int nextStep;

	//Image
	private int width;
	private int height;
	private int[] pixels0;
	private int[] pixels1;
	private int[] pixels2;

	//Regular character
	public Character (String mapName, String charName, int initX, int initY, String direction, String moveRoutine) {
		this.mapName = mapName;
		this.charName = charName;

		this.x = initX;
		this.y = initY;

		this.moveRoutine = moveRoutine;
		moveIndex = 0;

		this.direction = direction;
		step = 0;
		nextStep = 1;

		//Load images
		String path = "/characters/"+mapName+"/"+charName+""+direction;
		loadImage(path);
	}

	//Main character
	public Character() {
		mainX = xMAIN;
		mainY = yMAIN;

		direction = "D";
		step = 0;

		//Load images
		String path = "/characters/main_character/testCharacter"+direction;
		loadImage(path);
	}

	private void loadImage(String path) {
		BufferedImage image0;
		BufferedImage image1;
		BufferedImage image2;

		try {
			image0 = ImageIO.read(getClass().getResource(path+"0.png"));
			image1 = ImageIO.read(getClass().getResource(path+"1.png"));
			image2 = ImageIO.read(getClass().getResource(path+"2.png"));

			width = image0.getWidth();
			height = image0.getHeight();

			pixels0 = new int [width*height];
			pixels1 = new int [width*height];
			pixels2 = new int [width*height];

			image0.getRGB(0,0,width,height,pixels0,0,width);
			image1.getRGB(0,0,width,height,pixels1,0,width);
			image2.getRGB(0,0,width,height,pixels2,0,width);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void update () {
		if (moving || turning) {
			if (moving) {
				if (direction.equals("U"))
					y--;
				if (direction.equals("D"))
					y++;
				if (direction.equals("L"))
					x--;
				if (direction.equals("R"))
					x++;
			}

			tick++;

			//Check terrain obstacle
			//			checkTerrain();

			//Switch images when half way done
			if (tick == BASE / 2) {
				resetStep();
			}

			//Check end of block move
			if (tick >= BASE) {
				tick = 0;
				moving = false;
				turning = false;

				//Next step
				moveIndex++;
				moveIndex%=moveRoutine.length();
				String newDirection = moveRoutine.charAt(moveIndex) + "";
				if (newDirection.equals(direction) || direction.equals("N")) {
					moving = true;
				}
				else
					turning = true;

				direction = newDirection;
				setStep();

				//Load images
				if (!direction.equals("N")) {
					String path = "/characters/"+mapName+"/"+charName+""+direction;
					loadImage(path);
				}
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getMainX() {
		return mainX;
	}

	public int getMainY() {
		return mainY;
	}

	public String getDirection() {
		return direction;
	}

	public int getStep() {
		return step;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		switch (step) {
		case 0: return pixels0;
		case 1: return pixels1;
		case 2: return pixels2;
		default: return pixels0;
		}
	}

	public void setDirection(String direction) {
		//If the direction has changed, load new images
		if (!this.direction.equals(direction)) {
			String path = "/characters/main_character/testCharacter"+direction;
			loadImage(path);
		}
		this.direction = direction;
	}

	public void setStep() {
		this.step = nextStep;
	}

	public void resetStep() {
		this.step = 0;

		if (nextStep == 1)
			nextStep = 2;
		else
			nextStep = 1;
	}

}
