package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	private JFrame loginFrame = new JFrame();
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private static final Logger LOG=Logger.getLogger(LoginScreen.class.getName());

	// dimensione dello schermo
	private Dimension screenSize = toolkit.getScreenSize();
	private double screenWidth = screenSize.getWidth();
	private double screenHeight = screenSize.getHeight();

	// dimensione form
	private int formWidth = 1024;
	private int formHeight = 1024;

	// immagine sfondo
	private ImageIcon sfondo = new ImageIcon("./img/login.png");

	// campi richiesta username e password
	private JTextField username = new JTextField();
	private JPasswordField password = new JPasswordField();

	// controllore partita
	private ControllorePartitaClient controllorePartita;
	private GuiImpl guiImpl;

	// controllo di login valido
	private boolean loginValido = false;

	/**
	 * costruttore finestra login con inizializzazione frame
	 * 
	 * @author Matteo Daverio
	 */
	public LoginScreen(ControllorePartitaClient controllorePartita,
			GuiImpl guiImpl) {
		this.controllorePartita = controllorePartita;
		this.guiImpl = guiImpl;
		loginFrame.setResizable(false);
		if (formHeight > screenHeight) {
			formWidth = formHeight = (int) screenHeight * 90 / 100;
		}
		loginFrame.setSize(formWidth, formHeight);
		loginFrame.setTitle("Login");
		loginFrame.setLayout(new FlowLayout());
		loginFrame.setLocation((int) (screenWidth - formWidth) / 2,
				(int) (screenHeight - formHeight - 40) / 2);
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
		logButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		logButton.setLocation((formWidth - 150) / 2, (formHeight - 60) / 2);
		logButton.setActionCommand("Login");
		logButton.addActionListener(new ButtonClickListener());
		container.setLayout(null);
		container.add(Disegno.disegnaImmagine(sfondo, formWidth, formHeight));
		container.add(requestPanel, 0);
		container.add(logButton, 0);
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

				// creazione finestra partita

				try {
					loginValido = controllorePartita.logIn(nome, pass);
					if (loginValido) {
						Map mappa;
						mappa = new Map(controllorePartita);
						guiImpl.creaMappa(mappa);
						mappa.creaMappa();
						// chiusura finestra login
						loginFrame.dispose();
					} else {
						username.setText("");
						password.setText("");
						JOptionPane.showMessageDialog(null, "Password Errata",
								"Errore", JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null,
							"Errore di connessione", "Errore",
							JOptionPane.ERROR_MESSAGE);
					LOG.log(Level.SEVERE,"errore di connessione", e);
				}

			}
		}

		/*
		 * JOptionPane.showMessageDialog(null, "fine turno", "Posizione",
		 * JOptionPane.INFORMATION_MESSAGE);
		 */
	}
}
