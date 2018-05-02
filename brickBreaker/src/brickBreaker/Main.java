package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frm = new JFrame();
		GamePlay gamePlay = new GamePlay();
		frm.setBounds(10,10,900,600);
		frm.setTitle("Brick Breaker");
		frm.setResizable(false);
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.add(gamePlay);
		
	}

}
