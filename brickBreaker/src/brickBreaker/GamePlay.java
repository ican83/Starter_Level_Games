package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;


public class GamePlay extends JPanel implements KeyListener, ActionListener{
	
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballPosX = 200;
	private int ballPosY = 320;
	private int ballXDir = -1;
	private int ballYDir = -2;
	
	private MapGenerator map;
	
	public GamePlay() {
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 892, 592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 28 ));
		g.drawString(""+score, 792, 30);
		
		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 892, 3);
		g.fillRect(891, 0, 3, 592);
		
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//the ball
		g.setColor(Color.red);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		if(totalBricks ==0) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.orange);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("YOU WIN!!      Score : ", 300, 30);
			
			g.setColor(Color.red);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Please Enter to Restart", 300, 60);
		}
		
		if(ballPosY > 570) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("GAME OVER!      Score : ", 300, 30);
			
			
			
			g.setColor(Color.red);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Please Enter to Restart", 300, 60);
		}
		
		g.dispose();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			
			if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYDir = -ballYDir;
				
			}
			
			
			A: for(int i = 0; i < map.map.length; i++) {
				for(int j = 0; j < map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickHeight + 80;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rec = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRec = new Rectangle(ballPosX,ballPosY,20,20);
						Rectangle brickRec = rec;
						
						if(ballRec.intersects(brickRec)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if((ballPosX + 19 <= brickRec.x)||(ballPosX + 1 >= brickRec.x + brickRec.width)) {
								ballXDir = -ballXDir;
								
							} else {
								ballYDir = -ballYDir;
							}
							
							break A;
							
						}
						
						
						
						
					}
				}
			}
			
			ballPosX += ballXDir;
			ballPosY += ballYDir;
			
			if(ballPosX < 0) {
				ballXDir = -ballXDir;
			}
			
			if(ballPosY < 0) {
				ballYDir = -ballYDir;
			}
			
			if(ballPosX > 870) {
				ballXDir = -ballXDir;
			}
		}
		
		repaint();
		
	}

	
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			if(playerX >= 800) {
				playerX = 800;
			} else {
				moveRight();
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			
			if(playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballXDir = -1;
				ballYDir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint();
			}
			
		}
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
		
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	

	
}
