import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class MainClient extends WindowAdapter implements ActionListener
{
	private JFrame		 mainFrame;
	private JMenuBar	 menuBar;
	private JMenu		 menuFile, menuEdit, menuView, menuHelp, menuLookAndFeel;
	private JMenuItem	 mnuExit, mnuEmbedMessage, mnuEmbedFile, mnuHelp, mnuAbout;
	private JMenuItem	 mnuRetrieveMessage, mnuRetrieveFile, mnuModifyMaster;
	private JRadioButtonMenuItem	 mnuMetalFeel, mnuMotifFeel, mnuWindowsFeel;
	private ButtonGroup lookAndFeelButtonGroup;

	private JLabel lblLogo;

	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	private BackEndHandler back;

	public MainClient()
	{
		mainFrame= new JFrame("Steganograph ");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.addWindowListener(this);

		// Setup the menu bar
		mnuExit= new MyJMenuItem("Exit", 1, 'x');
		mnuEmbedMessage= new MyJMenuItem("Embed Message", 6, 'm');
		mnuEmbedFile= new MyJMenuItem("Embed File", 7, 'i');
		mnuRetrieveMessage= new MyJMenuItem("Retrieve Message", 0, 'r');
		mnuRetrieveFile= new MyJMenuItem("Retrieve File", 2, 't');
		mnuModifyMaster= new MyJMenuItem("Modify Master file settings", 2, 'd');
		mnuHelp= new MyJMenuItem("Help", 0, 'h');
		mnuAbout= new MyJMenuItem("About Steganograph", 0, 'a');

		mnuMetalFeel= new MyJRadioButtonMenuItem("Metal", 0, 'm');
		mnuMotifFeel= new MyJRadioButtonMenuItem("Motif", 2, 't');
		mnuWindowsFeel= new MyJRadioButtonMenuItem("Windows", 0, 'w');

		// Add item listener for Look and feel menu items
		RadioListener radioListener= new RadioListener();

		mnuMetalFeel.addItemListener(radioListener);
		mnuMotifFeel.addItemListener(radioListener);
		mnuWindowsFeel.addItemListener(radioListener);


		lookAndFeelButtonGroup= new ButtonGroup();

		lookAndFeelButtonGroup.add(mnuMetalFeel);
		lookAndFeelButtonGroup.add(mnuMotifFeel);
		lookAndFeelButtonGroup.add(mnuWindowsFeel);

		// Add action listeners for other menu items
		mnuEmbedMessage.addActionListener(this);
		mnuEmbedFile.addActionListener(this);
		mnuRetrieveMessage.addActionListener(this);
		mnuRetrieveFile.addActionListener(this);
		mnuModifyMaster.addActionListener(this);
		mnuExit.addActionListener(this);
		mnuHelp.addActionListener(this);
		mnuAbout.addActionListener(this);

		menuFile= new MyJMenu("File", 0, 'f');
		menuFile.add(mnuEmbedMessage);
		menuFile.add(mnuEmbedFile);
		menuFile.add(mnuRetrieveMessage);
		menuFile.add(mnuRetrieveFile);
		menuFile.add(mnuExit);

		menuEdit= new JMenu("Edit");
		menuEdit.add(mnuModifyMaster);

		menuLookAndFeel= new MyJMenu("Look and Feel...", 0, 'l');

		menuLookAndFeel.add(mnuMetalFeel);
		menuLookAndFeel.add(mnuMotifFeel);
		menuLookAndFeel.add(mnuWindowsFeel);
		menuView= new MyJMenu("View", 0, 'v');
		menuView.add(menuLookAndFeel);

		menuHelp= new MyJMenu("Help", 0, 'h');
		menuHelp.add(mnuHelp);
		menuHelp.add(mnuAbout);

		menuBar= new JMenuBar();
		menuBar.add(menuFile);
		//menuBar.add(menuEdit);
		menuBar.add(menuView);
		menuBar.add(menuHelp);
		mainFrame.setJMenuBar(menuBar);




		lblLogo= new JLabel(new ImageIcon("Images/Logo.jpg"));

		Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.add(lblLogo);
		mainFrame.setSize(d.width, (int) (d.height-(d.height*.03)));
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}

	// Listener methods
	public void actionPerformed(ActionEvent e)
	{
		Object source= e.getSource();

		// Embed message operation
		if(source== mnuEmbedMessage)
		{
			back= new BackEndHandler(this, BackEndHandler.EMBED_MESSAGE);
			back.start();
		}

		// Retrieve message operation
		if(source== mnuRetrieveMessage)
		{
		}

		// Embed file operation
		if(source== mnuEmbedFile)
		{
			back= new BackEndHandler(this, BackEndHandler.EMBED_FILE);
			back.start();
		}

		// Retrieve file operation
		if(source== mnuRetrieveFile)
		{
		}

		// Modify Master file operation
		if(source== mnuModifyMaster)
		{
			back= new BackEndHandler(this, BackEndHandler.EDIT_MASTER);
			back.start();
		}


		if(source== mnuHelp)
			Steganograph.showHelpDialog();

		if(source== mnuAbout)
			Steganograph.showAboutDialog();

		if(source== mnuExit)
		{
			int result= JOptionPane.showConfirmDialog(mainFrame, "Are you sure that you want to close Steganograph.", "Confirm Exit", JOptionPane.YES_NO_OPTION);
			if(result== JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
		}
	}

	public void windowClosing(WindowEvent w)
	{
		
	}

	// Class for lissoning to Look and feel radio menu events
	private class RadioListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			JMenuItem item= (JMenuItem) e.getSource();
			try
			{
				if(item== mnuMetalFeel && mnuMetalFeel.isSelected())
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

				if(item== mnuMotifFeel && mnuMotifFeel.isSelected())
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

				if(item== mnuWindowsFeel && mnuWindowsFeel.isSelected())
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				SwingUtilities.updateComponentTreeUI(mainFrame);
				Steganograph.updateUserInterface();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(mainFrame, "Oops!!\n"+ "Unable to load "+ item.getText()+ " Look and feel.", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	// Main method
	/*public static void main(String args[])
	{
		new MainClient();
	}*/
}