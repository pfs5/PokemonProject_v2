package hr.pfs.graphics;

import static hr.pfs.main.GlobalSettings.*;

public class MapPixel {
	
	private int color;
	private int data;
	
	public MapPixel (int bottom, int top, int data) {
		if (top == cTRANSPARENT)
			this.color = bottom;
		else 
			this.color = top & 0x00ffffff;
		
		this.data = data;
	}
	
	public int getBottom() {
		return color;
	}
	
	public int getTop() {
		//ff xx xx xx  = botom layer
		//00 xx xx xx  = top layer
		
		if ((color & 0xff000000) == 0) {
			return color | 0xff000000;
		}
		else
			return 0;
	}

	public int getData() {
		return data;
	}
}
