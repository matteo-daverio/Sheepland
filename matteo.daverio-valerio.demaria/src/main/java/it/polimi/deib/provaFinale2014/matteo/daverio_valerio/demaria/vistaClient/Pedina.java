package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;

/**
 * classe per gestire l'immagine della pedina
 * 
 * @author Matteo Daverio
 *
 */
public class Pedina extends JLabel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4562936774742543034L;

	private int xSize=27;
	private int ySize=27;
	private Image image;
	private static final Logger LOG=Logger.getLogger(Pedina.class.getName());
	
	/**
	 * costruttore
	 * 
	 * @param path
	 * @author Matteo Daverio
	 */
	public Pedina(String path) {
		image=Toolkit.getDefaultToolkit().createImage(path);
		loadImage(image);
		this.setSize(xSize, ySize);
		setOpaque(false);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	
	/**
	 * load image
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
	 * override paintComponent
	 * 
	 * @author Matteo Daverio
	 */
	protected void paintComponent(Graphics g) {
		setOpaque(false);
		g.drawImage(image, 0, 0, null);
		super.paintComponent(g);
	}
	
}
