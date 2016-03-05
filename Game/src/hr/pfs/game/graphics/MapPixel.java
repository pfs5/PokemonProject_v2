package hr.pfs.game.graphics;

public class MapPixel {

	private int bottom;
	private int top;
	private int data;
	
	public MapPixel (int bottom, int top, int data) {
		this.bottom = bottom;
		this.top = top;
		this.data = data;
		
		//Check transparency
		if (top == Color.topTransparent)
			this.top = 0;
	}

	//Getters and setters
	public int getBottom() {
		return bottom;
	}

	public int getTop() {
		return top;
	}

	public int getData() {
		return data;
	}
}
