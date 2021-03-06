package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;

/**
 * classe per gestire la visualizzazione del messaggio
 * 
 * @author Matteo Daverio
 *
 */
public class Messaggio extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int xSize=1000;
	private int ySize=30;
	
	/**
	 * costruttore del messaggio
	 * 
	 * @param messaggio
	 * @author Matteo Daverio
	 */
	public Messaggio(String messaggio) {
		this.setSize(xSize, ySize);
		this.setFont(new Font("mio font",Font.BOLD,25));
		this.setText(messaggio);
		setOpaque(false);
		setVisible(true);
	}
	
	@Override
	/**
	 * override del paintComponent
	 * 
	 * @author Matteo Daverio
	 */
	protected void paintComponent(Graphics g) {
		setOpaque(false);
		super.paintComponent(g);
	}
	
}
