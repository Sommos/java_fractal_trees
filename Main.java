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
	private int window_width = 1920;
	private int window_height = 1080;
	private int lineLength;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
        drawFrame();
		// instantiates new thread in variable 'update' //
		update = new Thread(() ->  {
			while(true) {
                		// set the color of window //
                		graphics.setColor(Color.BLACK);
                		// make the window, with dimensions of 1920x1080 pixels //
                		graphics.fillRect(0, 0, window_width, window_height);
                		// set the color of the graphics 'pencil' to cyan //
                		graphics.setColor(Color.CYAN);
				// increment line length by 5 pixels each time //
				lineLength += 2;
               			// get the graphics 'pencil' to draw with arguments for length and angle, using the middle of the frame (960 and 840) as the start of the recursive drawing //
                		drawStick(lineLength, 0, 960, 840, 10, 30);
                		// make the window and the 'pencil' drawings visible //
				bufferStrategy.show();
				try {
					// wait for 50 milliseconds before drawing the tree again //
					Thread.sleep(13);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		// start the thread //
		update.start();
	}

	private void drawFrame() {
		// make a new JFrame with the window title 'Recursive Tree', and assign to 'frame' variable //
        	frame = new JFrame("Recursive Tree");
        	// make a new Canvas, and assign to 'canvas' variable //
        	canvas = new Canvas();
		// set the canvas window size to 1920 x 1080 pixels //
        	canvas.setSize(window_width, window_height);
       	 	// adds, and then packs the canvas variable in to the frame variable //
        	frame.add(canvas);
        	frame.pack();
		// sets the frame to not be resizable //
		frame.setResizable(false);
        	// sets the location of the start of the frame to a null relative //
        	frame.setLocationRelativeTo(null);
       		// this exits the program when the frame is closed //
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// sets the frame to visible
		frame.setVisible(true);
		// creates buffer strategy, and uses quad buffering //
		canvas.createBufferStrategy(4);
		// assigns buffer strategy to 'bufferStrategy' variable //
		bufferStrategy = canvas.getBufferStrategy();
		// sets graphics to 2D, and assigns to variable 'graphics' //
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		// sets antialiasing to on //
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
    
    	// recursive method that draws a single line //
	private void drawStick(int lineLength, int lineAngle, int x, int y, int lengthStep, int lineAngleStep) {
		// calculates the x and y size of the line //
		int xSize = (int) (lineLength * Math.cos(Math.toRadians(lineAngle - 90)));
    		int ySize = (int) (lineLength * Math.sin(Math.toRadians(lineAngle - 90)));
		// set the color of the graphics 'pencil' to a random color //
		graphics.setColor(new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)));
		// draw a line from the start of the line to the end of the line //
		graphics.drawLine(x, y, x + xSize, y + ySize);
		// exit condition of recursive loop requires lineLength of a line to be below 1 //
		if(lineLength >= 1) {
			// recursive loop that draws 2 lines, one to the left and one to the right, with a smaller lineLength and lineAngle //
			drawStick(lineLength - lengthStep, lineAngle - lineAngleStep, x + xSize, y + ySize, lengthStep, lineAngleStep);
			drawStick(lineLength - lengthStep, lineAngle + lineAngleStep, x + xSize, y + ySize, lengthStep, lineAngleStep);
		}
	}
}
