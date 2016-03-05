package hr.pfs.graphics;

import static hr.pfs.main.GlobalSettings.*;

import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSeparatorUI;


public class Screen {

	public int[] pixels;
	public Map currentMap;
	public MapPixel[] currentMapPixels;

	private int xOffset;
	private int yOffset;

	//Movement
	private String direction = "N";
	private boolean moving = false;
	private boolean turning = false;
	private boolean movable = true;
	private int tick = 0;

	//Main character
	private Character mainCharacter;
	private int[] mainCharacterPixels;

	//Other characters
	private ArrayList<Character> characterList;

	public Screen() {
		pixels = new int[gWIDTH*gHEIGHT];

		//Initial offset
		xOffset = xOFFSET_INIT;
		yOffset = yOFFSET_INIT;

		//Initialize map
		currentMap = new Map("map001");
		currentMapPixels = currentMap.getPixels();
		characterList = currentMap.getCharacterList();

		//Initialize main character
		mainCharacter = new Character();
		mainCharacterPixels = mainCharacter.getPixels();
	}

	public void clear() {
		for (int y=0; y<gHEIGHT; y++)
			for (int x=0; x<gWIDTH; x++)
				pixels[y*gWIDTH+x] = 0x000000;
	}

	public void render() {
		for (int y=0; y<gHEIGHT; y++) {
			int mapY = y + yOffset;
			for (int x=0; x<gWIDTH; x++) {
				int mapX = x + xOffset;

				//Check max edge
				if (mapX<0 || mapX>currentMap.getMapWidth() || mapY<0 || mapY>currentMap.getMapHeight())
					break;

				int pixelIndex = y*gWIDTH + x;
				int mapIndex = mapY*currentMap.getMapWidth() + mapX;

				// ## Draw bottom map layer ##
				if (currentMapPixels[mapIndex].getTop() == 0)
					pixels[pixelIndex] = currentMapPixels[mapIndex].getBottom();


				// ## Draw characters ##
				//Draw main character
				if (x>=mainCharacter.getMainX() && x<mainCharacter.getMainX()+cSIZE_X && y>=mainCharacter.getMainY() && y<mainCharacter.getMainY()+cSIZE_Y) {
					int charX = x % mainCharacter.getMainX();
					int charY = y % mainCharacter.getMainY();
					int charIndex = charY*mainCharacter.getWidth() + charX;
					if (mainCharacterPixels[charIndex]!=cTRANSPARENT)
						pixels[pixelIndex] = mainCharacterPixels[charIndex];
				}

				//Draw other characters
				for (Character current : characterList) {
					if (mapX>=current.getX() && mapX<current.getX()+current.getWidth() && mapY>=current.getY() && mapY<current.getY()+current.getHeight()) {
						int charX = mapX % current.getX();
						int charY = mapY % current.getY();
						int charIndex = charY*mainCharacter.getWidth() + charX;
						int [] characterPixels = current.getPixels();
						if (characterPixels[charIndex]!=cTRANSPARENT)
							pixels[pixelIndex] = characterPixels[charIndex];
					}
				}

				// ## Draw top map layer ##
				if (currentMapPixels[mapIndex].getTop() != 0)
					pixels[pixelIndex] = currentMapPixels[mapIndex].getTop();
			}
		}
	}

	public void update() {
		//Update characters
		for (Character current : characterList) 
			current.update();
		
		if (moving || turning) {
			if (moving) {
				if (direction.equals("U"))
					yOffset--;
				if (direction.equals("D"))
					yOffset++;
				if (direction.equals("L"))
					xOffset--;
				if (direction.equals("R"))
					xOffset++;
			}

			tick++;

			//Check terrain obstacle
			checkTerrain();

			//Switch images when half way done
			if (tick == BASE / 2) {
				mainCharacter.resetStep();
				mainCharacterPixels = mainCharacter.getPixels();
			}

			//Check end of block move
			if (tick >= BASE) {
				tick = 0;
				moving = false;
				turning = false;
				movable = true;
			}
		}
		
	}

	private void checkTerrain() {
		//Top left pixel on map
		int x = xMAIN+xOffset;
		int y = yMAIN+(cSIZE_Y-BASE)+yOffset;

		//Edge pixels
		int indexTL = y*currentMap.getMapWidth() + x;
		int indexBL = (y+BASE-1)*currentMap.getMapWidth() + x;
		int indexTR = y*currentMap.getMapWidth() + x + BASE - 1;
		int indexBR = (y+BASE-1)*currentMap.getMapWidth() + x + BASE - 1;

		//Left
		if (direction.equals("L"))
			if (currentMapPixels[indexTL].getData()!=0xffffffff || currentMapPixels[indexBL].getData()!=0xffffffff)
				xOffset++;
		//Right
		if (direction.equals("R"))
			if (currentMapPixels[indexTR].getData()!=0xffffffff || currentMapPixels[indexBR].getData()!=0xffffffff)
				xOffset--;
		//Up
		if (direction.equals("U"))
			if (currentMapPixels[indexTL].getData()!=0xffffffff || currentMapPixels[indexTR].getData()!=0xffffffff)
				yOffset++;
		//Down
		if (direction.equals("D"))
			if (currentMapPixels[indexBL].getData()!=0xffffffff || currentMapPixels[indexBR].getData()!=0xffffffff)
				yOffset--;
	}

	public void setMove(String direction) {
		if (movable) {
			if (this.direction.equals(direction)) {
				moving = true;
			}
			else
				turning = true;

			this.direction = direction;
			mainCharacter.setDirection(direction);
			mainCharacter.setStep();
			mainCharacterPixels = mainCharacter.getPixels();
			movable = false;
		}
	}

}