package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JLabel;


public class Pedina extends JLabel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4562936774742543034L;

	private int xSize=27;
	private int ySize=27;
	private Image image;
	
	public Pedina(String path) {
		image=Toolkit.getDefaultToolkit().createImage(path);
		loadImage(image);
		this.setSize(xSize, ySize);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	
	private void loadImage(Image image) {
		try{
			MediaTracker tracker=new MediaTracker(this);
			tracker.addImage(image, 0);
			tracker.waitForID(0);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		setOpaque(false);
		g.drawImage(image, 0, 0, null);
		super.paintComponent(g);
	}
	
}
