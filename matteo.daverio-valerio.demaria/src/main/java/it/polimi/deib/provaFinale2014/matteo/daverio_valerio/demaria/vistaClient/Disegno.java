package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Disegno {
	public static JLabel disegnaImmagine(final ImageIcon imageToDraw,
			final int width, final int height) {
		JLabel immagine = new JLabel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				g.drawImage(imageToDraw.getImage(), 0, 0, width, height, null);
				super.paintComponent(g);
			}

		};
		immagine.setOpaque(false);
		immagine.setSize(width, height);
		return immagine;
	}
	
	
	
	

	public static BufferedImage getScaledImage(BufferedImage src, int w, int h) {
		int finalw = w;
		int finalh = h;
		double factor = 1.0d;
		if (src.getWidth() > src.getHeight()) {
			factor = ((double) src.getHeight() / (double) src.getWidth());
			finalh = (int) (finalw * factor);
		} else {
			factor = ((double) src.getWidth() / (double) src.getHeight());
			finalw = (int) (finalh * factor);
		}

		BufferedImage resizedImg = new BufferedImage(finalw, finalh,
				BufferedImage.TRANSLUCENT);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(src, 0, 0, finalw, finalh, null);
		g2.dispose();
		return resizedImg;
	}

	public static JLabel creaContatore(String value, int width, int height,
			int posX, int posY, Color color) {

		JLabel contatore = new JLabel(value);
		contatore.setForeground(color);
		contatore.setSize(width, height);
		contatore.setLocation(posX, posY);

		return contatore;
	}
}
