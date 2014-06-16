package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.LOGGER;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;



public class Ovino extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int xSize=34;
	private int ySize=24;
	private Image image;
	
	public Ovino(String path) {
		image=Toolkit.getDefaultToolkit().createImage(path);
		loadImage(image);
		this.setSize(xSize, ySize);
		setOpaque(false);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	
	private void loadImage(Image image) {
		try{
			MediaTracker tracker=new MediaTracker(this);
			tracker.addImage(image, 0);
			tracker.waitForID(0);
		} catch(InterruptedException e) {
			LOGGER.log("Caricamento immagine errato", e);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		setOpaque(false);
		g.drawImage(image, 0, 0, null);
		super.paintComponent(g);
	}
	
}
