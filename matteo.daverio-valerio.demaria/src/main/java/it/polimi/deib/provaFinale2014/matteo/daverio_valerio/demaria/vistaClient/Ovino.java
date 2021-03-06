package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;


/**
 * classe per gestire le immagini degli ovini
 * 
 * @author Matteo Daverio
 *
 */
public class Ovino extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int xSize=34;
	private int ySize=24;
	private Image image;
	private static final Logger LOG=Logger.getLogger(Ovino.class.getName());
	
	/**
	 * costruttore immagine ovino
	 * 
	 * @param path
	 * @author Matteo Daverio
	 */
	public Ovino(String path) {
		image=Toolkit.getDefaultToolkit().createImage(path);
		loadImage(image);
		this.setSize(xSize, ySize);
		setOpaque(false);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	
	/**
	 * carica l'immagine
	 * 
	 * @param image
	 * @author Matteo Daverio
	 */
	private void loadImage(Image image) {
		try{
			MediaTracker tracker=new MediaTracker(this);
			tracker.addImage(image, 0);
			tracker.waitForID(0);
		} catch(InterruptedException e) {
			LOG.log(Level.SEVERE,"Caricamento immagine errato", e);
		}
	}
	
	@Override
	/**
	 * override del paintComponent
	 * 
	 * @author Matteo Daverio
	 */
	protected void paintComponent(Graphics g) {
		setOpaque(false);
		g.drawImage(image, 0, 0, null);
		super.paintComponent(g);
	}
	
}
