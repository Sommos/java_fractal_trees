import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Main {
	
	private JFrame frame;
	private Canvas canvas;
	private Thread update;
	private Graphics2D g;
	private BufferStrategy bs;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		frame = new JFrame("Recurcive Tree");
		canvas = new Canvas();
		canvas.setSize(1920, 1080);
		frame.add(canvas);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		update = new Thread(() ->  {
			while(true) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 1920, 1080);
				
				g.setColor(Color.CYAN);
				drawStick(150, 0, 960, 840, 10, 30);
				
				bs.show();
			}
		});
		update.start();
	}
	
	private void drawStick(int length, int angle, int x, int y, int lengthStep, int angleStep) {
		int xSize = (int)(Math.cos(Math.toRadians(angle - 90)) * length);
		int ySize = (int)(Math.sin(Math.toRadians(angle - 90)) * length);
		
		g.drawLine(x, y, x + xSize, y + ySize);
		
		if(length >= 1) {
			drawStick(length - lengthStep, angle - angleStep, x + xSize, y + ySize, lengthStep, angleStep);
			drawStick(length - lengthStep, angle + angleStep, x + xSize, y + ySize, lengthStep, angleStep);
		}
	}
}
