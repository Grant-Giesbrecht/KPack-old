// package com.tutorialspoint.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingMenuDemo {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel; 

   public SwingMenuDemo(){
   	  mainFrame = new JFrame("Java SWING Examples");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));

      headerLabel = new JLabel("",JLabel.CENTER );
      statusLabel = new JLabel("",JLabel.CENTER);        
      statusLabel.setSize(350,100);
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });
      
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);
   }
   
   public static void main(String[] args){
      SwingMenuDemo  swingMenuDemo = new SwingMenuDemo();     
      swingMenuDemo.showMenuDemo();
   }
   
   private void showMenuDemo(){
      //create a menu bar
      JMenuBar menuBar = new JMenuBar();

      //create menus
      JMenu fileMenu = new JMenu("File");
      JMenu editMenu = new JMenu("Edit"); 
      JMenu aboutMenu = new JMenu("About");
      JMenu linkMenu = new JMenu("Links");
     
      //create menu items
      JMenuItem newMenuItem = new JMenuItem("New");
      newMenuItem.setMnemonic(KeyEvent.VK_N);
      newMenuItem.setActionCommand("New");

      JMenuItem openMenuItem = new JMenuItem("Open");
      openMenuItem.setActionCommand("Open");

      JMenuItem saveMenuItem = new JMenuItem("Save");
      saveMenuItem.setActionCommand("Save");

      JMenuItem exitMenuItem = new JMenuItem("Exit");
      exitMenuItem.setActionCommand("Exit");

      JMenuItem cutMenuItem = new JMenuItem("Cut");
      cutMenuItem.setActionCommand("Cut");

      JMenuItem copyMenuItem = new JMenuItem("Copy");
      copyMenuItem.setActionCommand("Copy");

      JMenuItem pasteMenuItem = new JMenuItem("Paste");
      pasteMenuItem.setActionCommand("Paste");

      MenuItemListener menuItemListener = new MenuItemListener();

      newMenuItem.addActionListener(menuItemListener);
      openMenuItem.addActionListener(menuItemListener);
      saveMenuItem.addActionListener(menuItemListener);
      exitMenuItem.addActionListener(menuItemListener);
      cutMenuItem.addActionListener(menuItemListener);
      copyMenuItem.addActionListener(menuItemListener);
      pasteMenuItem.addActionListener(menuItemListener);

      final JCheckBoxMenuItem showWindowMenu = new JCheckBoxMenuItem("Show About", true);
      showWindowMenu.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
            
            if(showWindowMenu.getState()){
               menuBar.add(aboutMenu);
            } else {
               menuBar.remove(aboutMenu);
            }
         }
      });
      final JRadioButtonMenuItem showLinksMenu = new JRadioButtonMenuItem(
         "Show Links", true);
      showLinksMenu.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
            
            if(menuBar.getMenu(3)!= null){
               menuBar.remove(linkMenu);
               mainFrame.repaint();
            } else {                   
               menuBar.add(linkMenu);
               mainFrame.repaint();
            }
         }
      });
      //add menu items to menus
      fileMenu.add(newMenuItem);
      fileMenu.add(openMenuItem);
      fileMenu.add(saveMenuItem);
      fileMenu.addSeparator();
      fileMenu.add(showWindowMenu);
      fileMenu.addSeparator();
      fileMenu.add(showLinksMenu);       
      fileMenu.addSeparator();
      fileMenu.add(exitMenuItem);        
      
      editMenu.add(cutMenuItem);
      editMenu.add(copyMenuItem);
      editMenu.add(pasteMenuItem);

      //add menu to menubar
      menuBar.add(fileMenu);
      menuBar.add(editMenu);
      menuBar.add(aboutMenu);       
      menuBar.add(linkMenu);

      //add menubar to the frame
      mainFrame.setJMenuBar(menuBar);
      mainFrame.setVisible(true);     
   }
   
   class MenuItemListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {            
         statusLabel.setText(e.getActionCommand() + " JMenuItem clicked.");
      }    
   }
}

/*import KGraph.*;

class Test{

public static void main(String[] args){


}


}*/

// package com.tutorialspoint.gui;

/*import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Test {
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;

	public Test(){
		mainFrame = new JFrame("WINDOW");
		mainFrame.setSize(400,400);
		mainFrame.setLayout(new GridLayout(3, 1));

		headerLabel = new JLabel("",JLabel.CENTER );
		statusLabel = new JLabel("",JLabel.CENTER);        
		statusLabel.setSize(350,100);

		JMenuBar menu_bar = new JMenuMBar();		

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
			System.exit(0);
			}        
		});    
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.setJMenuBar(menu_bar);
		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);  

		headerLabel.setText("Pick a Button"); 

		JButton okButton = new JButton("Button 1");
		JButton submitButton = new JButton("Button 2");
		JButton cancelButton = new JButton("Button 3");

		okButton.setActionCommand("OK");
		submitButton.setActionCommand("Submit");
		cancelButton.setActionCommand("Cancel");

		okButton.addActionListener(new ButtonClickListener()); 
		submitButton.addActionListener(new ButtonClickListener()); 
		cancelButton.addActionListener(new ButtonClickListener()); 

		controlPanel.add(okButton);
		controlPanel.add(submitButton);
		controlPanel.add(cancelButton);
		
		mainFrame.setVisible(true); 
	}

	public static void main(String[] args){
		Test swingControlDemo = new Test();  
		//       swingControlDemo.showEventDemo();       
	}


	private class ButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();  

			if( command.equals( "OK" ))  {
				statusLabel.setText("1");
			} else if( command.equals( "Submit" ) )  {
				statusLabel.setText("2"); 
			} else {
				statusLabel.setText("3");
			}  	 	
		}		
	}
}*/