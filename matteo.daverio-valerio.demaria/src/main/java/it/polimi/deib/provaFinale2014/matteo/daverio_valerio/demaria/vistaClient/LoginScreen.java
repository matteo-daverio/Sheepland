package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


/**
 * 
 * @author Matteo Daverio
 * 
 */
public class LoginScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9053941280569018349L;

	JFrame f = new JFrame();
	Toolkit t = Toolkit.getDefaultToolkit();

	// dimensione dello schermo
	Dimension screenSize = t.getScreenSize();
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();

	// dimensione form
	int formWidth = 1024;
	int formHeight = 1024;

	// immagine sfondo
	ImageIcon sfondo = new ImageIcon("./img/login.png");

	// campi richiesta username e password
	JTextField username = new JTextField();
	JPasswordField password = new JPasswordField();

	public LoginScreen() {
		if (formHeight > screenHeight) {
			formWidth = formHeight = (int) screenHeight * 90 / 100;
		}
		f.setSize(formWidth, formHeight);
		f.setTitle("Login");
		f.setLayout(new FlowLayout());
		f.setLocation((int) (screenWidth - formWidth) / 2,
				(int) (screenHeight - formHeight) / 2);
		WindowListener i = new MyWindowAdapter();
		f.addWindowListener(i);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		f.setVisible(true);
	}

	public void createAndShowGui() {
		Container c = f.getContentPane();
		JPanel richiesta = richiestaLogin();
		richiesta.setLocation((formWidth - 350) / 2, (formHeight - 60) / 4);
		JButton logButton = new JButton("Login");
		logButton.setSize(100, 40);
		logButton.setLocation((formWidth - 100) / 2, (formHeight - 40) / 2);
		logButton.setActionCommand("Login");
		logButton.addActionListener(new ButtonClickListener());
		c.setLayout(null);
		c.add(creaImmagine());
		c.add(richiesta, 0);
		c.add(logButton, 0);
	}

	public JLabel creaImmagine() {
		JLabel immagine = new JLabel() {
			/**
				 * 
				 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				g.drawImage(sfondo.getImage(), 0, 0, formWidth, formHeight,
						null);
				super.paintComponent(g);
			}
		};
		add(immagine);
		immagine.setOpaque(false);
		immagine.setSize(formWidth, formHeight);
		return immagine;

	}

	public JPanel richiestaLogin() {
		JPanel richiesta = new JPanel(new GridLayout(2, 2));

		Dimension size = new Dimension(350, 60);
		richiesta.setSize(size);
		richiesta.setMaximumSize(size);
		richiesta.setMinimumSize(size);

		JLabel user = new JLabel("Username:", JLabel.CENTER);
		JLabel psw = new JLabel("Password:", JLabel.CENTER);

		richiesta.add(user);
		richiesta.add(username);
		richiesta.add(psw);
		richiesta.add(password);

		return richiesta;
	}

	/**
	 * 
	 * classe adapter per gestire la richiesta di chiusura della finestra
	 * 
	 * @author Matteo Daverio
	 * 
	 */
	private class MyWindowAdapter extends WindowAdapter {
		/**
		 * gestione evento click su chiusura finestra
		 * 
		 * @author Matteo Daverio
		 */
		public void windowClosing(WindowEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "Vuoi uscire?",
					"Esci", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	private class ButtonClickListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();			
			// viene premuto bottone per login
			if (command.equals("Login")) {
				// TODO reazione a conferma login
				String nome = username.getText();
				@SuppressWarnings("deprecation")
				String pass = password.getText();
				// TODO creazione istanza partita
				f.dispose();
			}
		}

	}
}
