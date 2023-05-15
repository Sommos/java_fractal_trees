import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
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
               	// get the graphics 'pencil' to draw with arguments for length and angle, using the middle of the frame (960 and 840) as the start of the recursive drawing //
                drawStick(175, 0, 960, 840, 10, 30);
                // make the window and the 'pencil' drawings visible //
				bufferStrategy.show();
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
        // adds the canvas variable to the frame variable //
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
		// creates buffer strategy
		canvas.createBufferStrategy(2);
		// assigns buffer strategy to 'bufferStrategy' variable //
		bufferStrategy = canvas.getBufferStrategy();
		// sets graphics to 2D, and assigns to variable 'graphics' //
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		// sets antialiasing to on //
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
    
    // recursive method that draws a single line //
	private void drawStick(int lineLength, int lineAngle, int x, int y, int lengthStep, int lineAngleStep) {
		// create a new AffineTransform, and rotate it by the lineAngle - 90 degrees //
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(lineAngle - 90));
		// create a new double array, and transform it by the AffineTransform //
		double[] point = {lineLength, 0};
		transform.transform(point, 0, point, 0, 1);
		// assign the x and y values of the double array to variables //
		int xSize = (int) point[0];
		int ySize = (int) point[1];
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