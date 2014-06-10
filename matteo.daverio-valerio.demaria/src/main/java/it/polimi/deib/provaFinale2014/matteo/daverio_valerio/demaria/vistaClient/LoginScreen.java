package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * classe che gestisce la schermata di login
 * 
 * @author Matteo Daverio
 * 
 */
public class LoginScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9053941280569018349L;

	JFrame loginFrame = new JFrame();
	Toolkit toolkit = Toolkit.getDefaultToolkit();

	// dimensione dello schermo
	Dimension screenSize = toolkit.getScreenSize();
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

	/**
	 * costruttore finestra login con inizializzazione frame
	 * 
	 * @author Matteo Daverio
	 */
	public LoginScreen() {
		loginFrame.setResizable(false);
		if (formHeight > screenHeight) {
			formWidth = formHeight = (int) screenHeight * 90 / 100;
		}
		loginFrame.setSize(formWidth, formHeight);
		loginFrame.setTitle("Login");
		loginFrame.setLayout(new FlowLayout());
		loginFrame.setLocation((int) (screenWidth - formWidth) / 2,
				(int) (screenHeight - formHeight-40) / 2);
		WindowListener windowListener = new MyWindowAdapter();
		loginFrame.addWindowListener(windowListener);
		loginFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		loginFrame.setVisible(true);
	}

	/**
	 * crea e mostra la GUI della finestra di login
	 * 
	 * @author Matteo Daverio
	 */
	public void createAndShowGui() {
		Container container = loginFrame.getContentPane();
		JPanel requestPanel = richiestaLogin();
		requestPanel.setLocation((formWidth - 350) / 2, (formHeight - 60) / 4);
		JButton logButton = new JButton("Login");
		logButton.setSize(150, 60);
		logButton.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
		logButton.setLocation((formWidth - 150) / 2, (formHeight - 60) / 2);
		logButton.setActionCommand("Login");
		logButton.addActionListener(new ButtonClickListener());
		container.setLayout(null);
		container.add(creaImmagine());
		container.add(requestPanel, 0);
		container.add(logButton, 0);
	}

	/**
	 * Disegna l'immagine di sfondo
	 * 
	 * @return JLabel contenente lo sfondo
	 * @author Matteo Daverio
	 */
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
		immagine.setOpaque(false);
		immagine.setSize(formWidth, formHeight);
		return immagine;

	}

	/**
	 * crea un pannello con componenti per inserire username e password
	 * 
	 * @return JPanel contenente le componenti per il login
	 * @author Matteo Daverio
	 */
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

	/**
	 * classe per la gestione del click di pulsanti
	 * 
	 * @author Matteo Daverio
	 *
	 */
	private class ButtonClickListener implements ActionListener {

		/**
		 * gestione evento click su bottoni
		 */
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();			
			// viene premuto bottone per login
			if (command.equals("Login")) {
				// lettura dati inseriti dall'utente
				String nome = username.getText();
				String pass = new String(password.getPassword());
				
				//TODO controllo se il login è valido
				
				// creazione finestra partita
				
				// TODO apertura finestra di gioco
				
				// chiusura finestra login
				loginFrame.dispose();
			}
		}

	}
}
