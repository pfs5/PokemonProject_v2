package hr.pfs.main;

import java.util.ArrayList;
import hr.pfs.graphics.Character;;

public class GlobalSettings {
	//Game settings
	public static final int BASE = 30;
	
	//Screen settings
	public static final String gTITLE = "Game";
	public static final double gRATIO = 9./16.;
	public static final int gWIDTH = 500;
	public static final int gHEIGHT = (int) (gWIDTH * gRATIO);
	public static final int gSCALE = 2;
	
	//Timing settings
	public static final int UPS = 100;
	public static final int DEFAULT_SPEED = 1;				
	public static final int MAX_SPEED = 3;
	
	//Position settings
	public static final int xOFFSET_INIT = 5*BASE;
	public static final int yOFFSET_INIT = 3*BASE;
	
	//Colors
	public static final int cTRANSPARENT = 0xffd2a3c5;
	
	// # Characters #
	//Character size
	public static final int cSIZE_X = 30;
	public static final int cSIZE_Y = 40;

	//Main character location
	public static final int xMAIN = 8*BASE;
	public static final int yMAIN = 4*BASE-(cSIZE_Y-BASE);
	
	//Character list creator
	public static ArrayList<Character> getCharacters (String mapName) {
		ArrayList<Character> characterList = new ArrayList<Character>();
		
		if (mapName.equals("map001")) {
			String move001 = "DNDDRRUUNNUULLD";
			Character character001 = new Character("map001","character001", 10*BASE, 3*BASE-(cSIZE_Y-BASE), "D", move001);
			characterList.add(character001);
		}
		
		return characterList;
	}
	
}