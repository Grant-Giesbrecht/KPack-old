import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// import java.lang.Object.*;
import javax.imageio.*;
import java.io.*;

class Wt1{

	private	JFrame frame;
	JMenuBar mb;

	public Wt1(){
	
		/*=================================================*\
		===================  PREPATE WINDOW =================
		\*=================================================*/
	
		frame = new JFrame("Window Test 1"); //Title of window
		frame.setSize(300, 250); //X, Y
		frame.setLayout(new GridLayout(3, 1));
	
		frame.addWindowListener(new WindowAdapter(){ //?? What is an adapter? What is a window Listener?
			public void windowClosing(WindowEvent windowEvent){ //?? Window Closing I believe is called when the window is closed.
				System.exit(0);
			}
		});
		
		/*=================================================*\
		====================  CREATE MENUS ==================
		\*=================================================*/

		//Create menu bar
		mb = new JMenuBar();
		
		//Create menus (go in menu bar)
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu subMenu = new JMenu("Options");

		//| CREATE MENU ITEMS
		
		//| | CREATE ITEMS FOR 'FILE' MENU

		//| | | CREATE 'CONTROL 1'
		JMenuItem item1 = new JMenuItem("Control 1");
		item1.setMnemonic(KeyEvent.VK_N);
		item1.setActionCommand("Ctrl1");
		
		//| | | | CREATE ICON FOR 'CONTROL 1'
		try{
			ImageIcon FOSLogo = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("FOS.gif")));
			item1.setIcon(FOSLogo);
		}catch(IOException e){
			System.out.println("EXCEPTION!!!");
		}
		
		//| | | CREATE 'CONTROL 2'
		JMenuItem item2 = new JMenuItem("Control 2");
		item2.setActionCommand("Ctrl2");
		
		//| | | CREATE RADIO BUTTON
		final JRadioButtonMenuItem itemRB = new JRadioButtonMenuItem("Radio Button", false);
		itemRB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
            
				if(e.getStateChange() == 1){
					System.out.println("RB Selected");
				}else{
					System.out.println("RB Deselected");
				}
			}
		});
		
		//| | | CREATE CHECK BUTTON 1
		final JCheckBoxMenuItem itemCB1 = new JCheckBoxMenuItem("Check Button 1", false);
		itemCB1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1){
					System.out.println("CB1 Selected");
				}else{
					System.out.println("CB1 Deselected");
				}
			}
		});
		
		//| | | CREATE CHECK BUTTON 2
		final JCheckBoxMenuItem itemCB2 = new JCheckBoxMenuItem("Check Button 2", true);
		itemCB2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1){
					System.out.println("CB2 Selected");
				}else{
					System.out.println("CB2 Deselected");
				}
			}
		});
		
		//| | CREATE ITEMS FOR 'EDIT' MENU
		
		//| | | CREATE 'CONTROL 3'
		JMenuItem item3 = new JMenuItem("Control 3");
		item3.setActionCommand("Ctrl3");

		//| | | CREATE 'CONTROL 4'		
		JMenuItem item4 = new JMenuItem("Control 4");
		item4.setActionCommand("Ctrl4");				
		
		//| | CREATE ITEMS FOR 'OPTIONS' MENU
		
		//| | | CREATE 'CONTROL 5'
		JMenuItem item5 = new JMenuItem("Control 5");
		item5.setActionCommand("Ctrl5");

		//| | | CREATE 'CONTROL 6'		
		JMenuItem item6 = new JMenuItem("Control 6");
		item6.setActionCommand("Ctrl6");				
		//-

		//Create menu item listener		
		MenuItemListener mil = new MenuItemListener();
		
		//Add menu item listener to all menu items
		item1.addActionListener(mil);
		item2.addActionListener(mil);
		item3.addActionListener(mil);
		item4.addActionListener(mil);
		item5.addActionListener(mil);
		item6.addActionListener(mil);
				
		//| ADD ITEMS TO MENUS
		
		//| | ADD ITEMS TO FILE MENU
		fileMenu.add(item1);
		fileMenu.add(item2);
		fileMenu.addSeparator();
		fileMenu.add(itemRB);
		fileMenu.addSeparator();
		fileMenu.add(itemCB1);
		fileMenu.add(itemCB2);
		fileMenu.addSeparator();
		fileMenu.add(subMenu);
		
		//| | ADD ITEMS TO EDIT MENU 
		editMenu.add(item3);
		editMenu.add(item4);
		
		//| | ADD ITEMS TO OPTIONS MENU
		subMenu.add(item5);
		subMenu.add(item6);
		
		//Add menus to menu bar
		mb.add(fileMenu);
		mb.add(editMenu);
						
		//Add menu bar to window
		frame.setJMenuBar(mb);
							
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		Wt1 window = new Wt1();
	}
	
	class MenuItemListener implements ActionListener { //?? What is 'implements', what is 'actionPerformed'
		public void actionPerformed(ActionEvent e) {		   
			System.out.println(e.getActionCommand());
		}				
	}    

}
