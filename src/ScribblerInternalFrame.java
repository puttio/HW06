import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class ScribblerInternalFrame extends JInternalFrame {

	// For undo
	Stack<Image> stackOfImages = new Stack<Image>();
	
	// Panels
	JPanel centerMainPanel, southMainPanel;

	// sub-panels for southMainPanel
	JPanel sliderPanel, buttonPanel;

	// preview panel
	PreviewPanel previewPanel = new PreviewPanel();

	// Drawing PAD
	DrawingPanel drawingPanel = new DrawingPanel();

	// r,g,b sliders
	JScrollBar rSlider, gSlider, bSlider;

	// Labels for r,g,b sliders
	JLabel rLabel, gLabel, bLabel;

	// current r,g,b values
	int rValue = 0, gValue = 0, bValue = 0;

	// brush
	Color brushColor = Color.BLACK;
	int brushStroke = 16;

	// buttons
	JButton clearCanvasButton, resetBrushButton;

	// Constants
	final Font MONOSPACED = new Font("Courier", Font.BOLD, 14);
	final int DEFAULT_BRUSH_STROKE = 16;

	// Mouse positions
	int currentX, currentY, oldX, oldY;

	static final int xOffset = 30, yOffset = 30;

	public ScribblerInternalFrame() {
		super("Untitled-" + (ScribbleShop.openFrameCount++), true, true, true,
				true);
		setLocation(xOffset * ScribbleShop.openFrameCount, yOffset
				* ScribbleShop.openFrameCount);
		setSize(500, 600);
		setGUI();
		addListeners();
	}
	
	@SuppressWarnings("serial")
	public class PreviewPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 50, 50);
			g.setColor(brushColor);
			g.fillRect(0, 0, brushStroke, brushStroke);
		}
	}

	protected void setGUI(){
		southMainPanel = new JPanel();
		southMainPanel.setLayout(new BorderLayout());
		add(southMainPanel, BorderLayout.SOUTH);
		centerMainPanel = new JPanel();
		centerMainPanel.setLayout(new BorderLayout());
		add(centerMainPanel, BorderLayout.CENTER);

		// drawing panel
		centerMainPanel.add(drawingPanel);
		centerMainPanel.setPreferredSize(new Dimension(500, 500));

		// preview panel
		southMainPanel.add(previewPanel, BorderLayout.EAST);
		previewPanel.setPreferredSize(new Dimension(50, 50));
	
		
		
		// settings panel

		// r,g,b sliders
		rSlider = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 255);
		gSlider = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 255);
		bSlider = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 255);

		sliderPanel = new JPanel();
		sliderPanel.setLayout(new GridLayout(3, 1, 2, 2));

		JPanel rPanel = new JPanel();
		rPanel.setLayout(new BorderLayout());
		rLabel = new JLabel("R(000)");
		rLabel.setFont(MONOSPACED);
		rPanel.add(rLabel, BorderLayout.WEST);
		rPanel.add(rSlider, BorderLayout.CENTER);

		JPanel gPanel = new JPanel();
		gPanel.setLayout(new BorderLayout());
		gLabel = new JLabel("G(000)");
		gLabel.setFont(MONOSPACED);
		gPanel.add(gLabel, BorderLayout.WEST);
		gPanel.add(gSlider, BorderLayout.CENTER);

		JPanel bPanel = new JPanel();
		bPanel.setLayout(new BorderLayout());
		bLabel = new JLabel("B(000)");
		bLabel.setFont(MONOSPACED);
		bPanel.add(bLabel, BorderLayout.WEST);
		bPanel.add(bSlider, BorderLayout.CENTER);

		sliderPanel.add(rPanel);
		sliderPanel.add(gPanel);
		sliderPanel.add(bPanel);
		southMainPanel.add(sliderPanel, BorderLayout.CENTER);

		// Buttons
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, 5, 2));

		clearCanvasButton = new JButton("Clear Canvas");
		buttonPanel.add(clearCanvasButton);

		resetBrushButton = new JButton("Reset Brush");
		buttonPanel.add(resetBrushButton);

		southMainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	public void addListeners() {
		rSlider.addAdjustmentListener(new SliderHandler());
		gSlider.addAdjustmentListener(new SliderHandler());
		bSlider.addAdjustmentListener(new SliderHandler());
		clearCanvasButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingPanel.clearCanvas();
			}
		});

		resetBrushButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				brushStroke = DEFAULT_BRUSH_STROKE;
				brushColor = Color.black;

				// update previewPanel
				previewPanel.repaint();
				drawingPanel.requestFocusInWindow();
			}
		});
	}

	public class SliderHandler implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			// Display colour value
			String valueString = "";
			int value = ((JScrollBar) e.getSource()).getValue();
			if (value / 10 == 0)
				valueString = "00" + value;
			else if (value / 100 == 0)
				valueString = "0" + value;
			else
				valueString = "" + value;

			if ((JScrollBar) e.getSource() == rSlider) {
				rValue = value;
				rLabel.setText("R(" + valueString + ")");
				rLabel.setFont(MONOSPACED);
			} else if ((JScrollBar) e.getSource() == gSlider) {
				gValue = value;
				gLabel.setText("G(" + valueString + ")");
				gLabel.setFont(MONOSPACED);
			} else {
				bValue = value;
				bLabel.setText("B(" + valueString + ")");
				bLabel.setFont(MONOSPACED);
			}

			brushColor = new Color(rValue, gValue, bValue);

			// update the preview panel
			previewPanel.repaint();
			//

		}
	}

	@SuppressWarnings("serial")
	public class DrawingPanel extends JPanel{
		Image image;
		Graphics2D g2d;
		int currentX, currentY, oldX, oldY;

		
		/*
		ArrayList<Point> listOfPoints = new ArrayList<Point>();
		Queue<ArrayList<Point>> q = new LinkedList<ArrayList<Point>>();
		
		public void startRecording(){
			System.out.println("Start Recording");
			listOfPoints.add(new Point(oldX,oldY));
			System.out.println(listOfPoints.get(0).x+","+listOfPoints.get(0).y);
		}
		
		
		public void stopRecording(){
			
			
			System.out.println("Stop Recording");
			
			q.add(listOfPoints);
			listOfPoints.clear();
			System.out.println("Members in listOfPoints = "+listOfPoints.size());
			System.out.println("Members in queue = "+q.size());
			
	
		}
			
		*/
		
		public void recordState(){
			if(image == null){
				System.out.println("image is null");
			}else{
				Image imgH = createImage(500,500);
				Graphics2D g2dH = (Graphics2D) imgH.getGraphics();
				g2dH.drawImage(image, 0, 0, null);
				stackOfImages.push(imgH);
				System.out.println("current image added to the list("+stackOfImages.size()+")");
			}
		}
		
		public DrawingPanel() {
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					oldX = e.getX();
					oldY = e.getY();
					recordState();

				}
				public void mouseReleased(MouseEvent e){
				}
			});

			addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
					currentX = e.getX();
					currentY = e.getY();
					g2d.setColor(brushColor);
					if (g2d != null)
						g2d.setStroke(new BasicStroke(brushStroke));
					g2d.drawLine(oldX, oldY, currentX, currentY);
					repaint();
					
				//	listOfPoints.add(new Point(currentX,currentY));
					//System.out.println("Point: "+listOfPoints.get(listOfPoints.size()-1).x+","+listOfPoints.get(listOfPoints.size()-1).y);
					
					oldX = currentX;
					oldY = currentY;
				}
			});

			addKeyListener(new KeyAdapter(){
				public void keyTyped(KeyEvent e){
					if(e.getKeyChar() == '+')
					brushStroke++;
					if(e.getKeyChar() == '-')
					brushStroke--;
					previewPanel.repaint();
				}
			});
			setFocusable(true);
            requestFocusInWindow();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (image == null) {
				image = createImage(500, 500);
				g2d = (Graphics2D) image.getGraphics();
			
				clearCanvas();
				
				Image imgH = createImage(500,500);
				Graphics2D g2dH = (Graphics2D) imgH.getGraphics();
				g2dH.drawImage(image, 0, 0, null);
				stackOfImages.push(imgH);
			}
			g.drawImage(image, 0, 0, null);
		}

		public void clearCanvas(){
			System.out.println("clearCanvas called");
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, 500, 500);
			g2d.setColor(Color.BLACK);
			previewPanel.repaint();
			this.repaint();
			drawingPanel.requestFocusInWindow();
		}
	}
}