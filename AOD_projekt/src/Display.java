import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {
	
	private JFrame frame;
	private Canvas canvas;
	private int width = 300, height = 300;
	
	public Display() {
		
		Dimension dimension = new Dimension(width, height);
		
		frame = new JFrame("title");
		frame.setMinimumSize(dimension);
		frame.setMaximumSize(dimension);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(dimension);
		canvas.setMinimumSize(dimension);
		canvas.setMaximumSize(dimension);
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
		
	}

	public JFrame getFrame() {
		return frame;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}