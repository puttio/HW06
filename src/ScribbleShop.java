import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class ScribbleShop extends JFrame{
	public static ScribbleShop program;
	protected JMenuBar menuBar;
	protected JMenu fileMenu, aboutMenu;
	protected JMenuItem newMenuItem, closeMenuItem, closeAllMenuItem, exitMenuItem, helpMenuItem, authorMenuItem;
	protected static JDesktopPane desktop;
	protected static ScribblerInternalFrame frame1;
	static int openFrameCount = 0;
	public ScribbleShop(){
		super("ScribbleShop - Putti O. 5431270021");
		initGUI();
	}
	

	public void initGUI(){
		setupMenu();
		desktop = new JDesktopPane();
		setContentPane(desktop);
	}

	
	public static void createFrame(){
		frame1 = new ScribblerInternalFrame();
		frame1.setSize(500,600);
		frame1.setVisible(true);
		desktop.add(frame1);
		try{
			frame1.setSelected(true);
		} catch (Exception e){}
		
	}
	

	public void setupMenu(){
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		newMenuItem.addActionListener(new MenuListener());
		
		
		fileMenu.addSeparator();
		
		closeMenuItem = new JMenuItem("Close");
		closeAllMenuItem = new JMenuItem("Close All");
		fileMenu.add(closeMenuItem);
		fileMenu.add(closeAllMenuItem);
		closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		closeAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.CTRL_MASK));
		closeMenuItem.addActionListener(new MenuListener());
		closeAllMenuItem.addActionListener(new MenuListener());
		
		
		fileMenu.addSeparator();
		
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		exitMenuItem.addActionListener(new MenuListener());
		
		
		aboutMenu = new JMenu("About");
		menuBar.add(aboutMenu);
		
		helpMenuItem = new JMenuItem("Help");
		authorMenuItem = new JMenuItem("Author");
		aboutMenu.add(helpMenuItem);
		aboutMenu.add(authorMenuItem);
		helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		helpMenuItem.addActionListener(new MenuListener());
		authorMenuItem.addActionListener(new MenuListener());
		
		
		setJMenuBar(menuBar);
	}
	

	static void createAndShowGUI(){
		program = new ScribbleShop();
		program.setPreferredSize(new Dimension(1000,800));
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		program.pack();
		// Position the program at the center of screen
		program.setLocationRelativeTo(null);
		program.setVisible(true);
	}

	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	

	public class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			AbstractButton source = (AbstractButton) e.getSource();
			if(source.getText().equals("Author")){
				showAuthorDialog();
			}else if(source.getText().equals("Help")){
				showHelpDialog();
			}else if(source.getText().equals("New")){
				ScribbleShop.createFrame();

			}else if(source.getText().equals("Close")){

				if(ScribbleShop.openFrameCount!=0){
					try {
						ScribbleShop.desktop.getSelectedFrame().setClosed(true);
						ScribbleShop.openFrameCount--;

						if(ScribbleShop.openFrameCount != 1) ScribbleShop.desktop.selectFrame(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}


			}else if(source.getText().equals("Close All")){
				for(int i = 0; i < ScribbleShop.openFrameCount; i++){
					try {

						ScribbleShop.desktop.getSelectedFrame().setClosed(true);
						if(i > 0) ScribbleShop.desktop.selectFrame(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				ScribbleShop.openFrameCount = 0;
			}else if(source.getText().equals("Exit")){
				System.exit(0);
			}else{}
		}

		private void showHelpDialog() {
			Image img = null;
			final JDialog helpDialog = new JDialog();
			helpDialog.setLayout(new BorderLayout());
			URL imgURL = getClass().getResource("/dogswing.jpg");
			try {
				img = ImageIO.read(imgURL);
			} catch (IOException e) {
				e.printStackTrace();
			}

			JLabel imgLabel = new JLabel(new ImageIcon(img));

			helpDialog.getContentPane().add(imgLabel, BorderLayout.CENTER);
			helpDialog.setModal(true);
			helpDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			helpDialog.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					helpDialog.dispose();
				}
			});
			helpDialog.setSize(700,526);
			helpDialog.setLocationRelativeTo(null);
			helpDialog.pack();
			helpDialog.setVisible(true);

		}

		public void showAuthorDialog(){
			final JDialog dialog = new JDialog();
			dialog.setLayout(new BorderLayout());
			dialog.getContentPane().add(new MyAvatar(), BorderLayout.CENTER);
			JButton OKButton = new JButton("OK! OK! Stop bragging!");
			dialog.getContentPane().add(OKButton,BorderLayout.SOUTH);
			OKButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dialog.dispose();
				}
			});
			dialog.setModal(true);
			dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			dialog.setSize(700,750);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		}
	}
}

