import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class ScribbleShopExtreme extends ScribbleShop{
	protected static ScribbleShopExtreme program;
	protected JMenu editMenu;
	protected JMenuItem undoMenuItem;

	public ScribbleShopExtreme(){
		super();
		System.out.println("ScribbleShopExtreme RUN");
	} 

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	static void createAndShowGUI(){
		program = new ScribbleShopExtreme();
		program.setPreferredSize(new Dimension(1000,800));
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		program.pack();
		// Position the program at the center of screen
		program.setLocationRelativeTo(null);
		program.setVisible(true);

	}


	public void setupMenu(){
		super.setupMenu();

		editMenu = new JMenu("Edit");
		menuBar.add(editMenu);

		undoMenuItem = new JMenuItem("Undo");
		editMenu.add(undoMenuItem);
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
		undoMenuItem.addActionListener(new MenuListener2());

	}

	public class MenuListener2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			undo();
		}

	}



	public void undo(){
		
		// if stack size is 0, create a white image
		if(frame1.stackOfImages.size() == 0){
			Image img0 = createImage(500,500);
			Graphics2D g2d0 = (Graphics2D) img0.getGraphics();
			g2d0.setColor(Color.WHITE);
			g2d0.fillRect(0,0,500,500);
			frame1.stackOfImages.push(img0);
		}

			Image imgH = frame1.stackOfImages.pop();
			System.out.println("stack size: "+frame1.stackOfImages.size());
			frame1.drawingPanel.g2d.drawImage(imgH, 0, 0, null);
		
		frame1.drawingPanel.repaint();
	}

}
