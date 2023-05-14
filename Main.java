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
	private Graphics2D graphics;
	private BufferStrategy bufferStrategy;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
        // make a new JFrame with the window title 'Recursive Tree', and assign to 'frame' variable //
        frame = new JFrame("Recursive Tree");
        // make a new Canvas, and assign to 'canvas' variable //
        canvas = new Canvas();
	// set the canvas window size to 1920 x 1080 pixels //
        canvas.setSize(1920, 1080);
        // adds the canvas variable to the frame variable //
        frame.add(canvas);
        frame.pack();
        // sets the location of the start of the frame to a null relative //
        frame.setLocationRelativeTo(null);
       		// this exits the program when the frame is closed //
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// sets the frame to visible
		frame.setVisible(true);
		// creates buffer strategy
		canvas.createBufferStrategy(2);
		// assigns buffer strategy to 'bufferStrategy' variable //
		bufferStrategy = canvas.getBufferStrategy();
		// sets graphics to 2D, and assigns to variable 'graphics' //
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		
		// sets antialiasing to on //
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
		// instantiates new thread in variable 'update' //
		update = new Thread(() ->  {
			while(true) {
                		// set the color of window //
                		graphics.setColor(Color.BLACK);
                		// make the window, with dimensions of 1920x1080 pixels //
                		graphics.fillRect(0, 0, 1920, 1080);
                		// set the color of the graphics 'pencil' to cyan //
                		graphics.setColor(Color.CYAN);
               			// get the graphics 'pencil' to draw with arguments for length and angle, using the middle of the frame (960 and 840) as the start of the recursive drawing //
                		drawStick(170, 0, 960, 840, 10, 30);
                		// make the window and the 'pencil' drawings visible //
				bufferStrategy.show();
			}
		});
		update.start();
	}
    
    	// recursive method that draws a single line //
	private void drawStick(int length, int angle, int x, int y, int lengthStep, int angleStep) {
		int xSize = (int)(Math.cos(Math.toRadians(angle - 90)) * length);
		int ySize = (int)(Math.sin(Math.toRadians(angle - 90)) * length);
		
		graphics.drawLine(x, y, x + xSize, y + ySize);
		
		// exit condition of recursive loop requires length of a line to be below 1 //
		if(length >= 1) {
			drawStick(length - lengthStep, angle - angleStep, x + xSize, y + ySize, lengthStep, angleStep);
			drawStick(length - lengthStep, angle + angleStep, x + xSize, y + ySize, lengthStep, angleStep);
		}
	}
}
