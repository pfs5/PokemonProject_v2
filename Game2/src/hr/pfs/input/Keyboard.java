package hr.pfs.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{

	public boolean[] keys = new boolean[256]; 
	private boolean pause;
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		keys[key] = true;
		
		if (key == KeyEvent.VK_P) 
			pause = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		keys[key] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public boolean isPause() {
		if (pause == true) {
			pause = false;
			return true;
		}
		else
			return false;
	}
}
