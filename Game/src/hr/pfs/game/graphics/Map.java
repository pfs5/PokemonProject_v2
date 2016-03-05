package hr.pfs.game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {

	private int mapWidth;
	private int mapHeight;

	private MapPixel [] pixels;

	public Map (String mapName) {
		//Console output
		System.out.println("Initializing game.");
		System.out.println("Analizing memory.");
		Screen.printMemory();
		System.out.println("Loading maps.");
		
		//Load map
		load(mapName);
		
		System.out.println("##### Initializing maps #####");
		System.out.println("");
		System.out.println("Maps initialized.");
		Screen.printMemory();
	}

	private void load (String mapName) {


		//Images
		BufferedImage BottomImage;
		BufferedImage TopImage;
		BufferedImage DataImage;

		//Paths
		String bottomPath = "/maps/"+mapName+"_bottom.png";
		String topPath = "/maps/"+mapName+"_top.png";
		String dataPath = "/maps/"+mapName+"_data.png";

		try {

			//Load map layers
			BottomImage = ImageIO.read(getClass().getResource(bottomPath)); 
			TopImage = ImageIO.read(getClass().getResource(topPath));
			DataImage = ImageIO.read(getClass().getResource(dataPath));

			mapWidth = BottomImage.getWidth();
			mapHeight = BottomImage.getHeight();

			int[] bottomLayer;
			bottomLayer = new int[mapWidth*mapHeight];
			BottomImage.getRGB(0,0,mapWidth,mapHeight,bottomLayer,0,mapWidth);

			int[] topLayer;
			topLayer = new int[mapWidth*mapHeight];
			TopImage.getRGB(0,0,mapWidth,mapHeight,topLayer,0,mapWidth);

			int[] dataLayer;
			dataLayer= new int[mapWidth*mapHeight];
			DataImage.getRGB(0,0,mapWidth,mapHeight,dataLayer,0,mapWidth);

			pixels = new MapPixel [mapWidth*mapHeight];
			for (int i=0; i<mapWidth*mapHeight; i++) {
				pixels [i] = new MapPixel(bottomLayer[i], topLayer[i], dataLayer[i]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	//Getters and setters
	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public MapPixel [] getPixels () {
		return pixels;
	}

}
