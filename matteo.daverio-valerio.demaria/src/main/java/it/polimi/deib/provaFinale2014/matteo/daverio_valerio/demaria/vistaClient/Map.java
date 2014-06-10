package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Map extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3256210950304048274L;

	// Immagini presenti a schermo
	ImageIcon mappa = new ImageIcon("./img/Game_Board.jpg");
	ImageIcon terrainCardImage = new ImageIcon("./img/tessereTerreno.jpeg");
	ImageIcon moveCharacterImage = new ImageIcon("./img/iconaPersonaggio.png");
	ImageIcon moveSheepImage = new ImageIcon("./img/iconaPecora.png");
	ImageIcon buyTerrainCard = new ImageIcon("./img/acquistoTessere.png");
	ImageIcon enclosureNumberImage = new ImageIcon("./img/numeroRecinti.png");
	ImageIcon killSheepImage=new ImageIcon("./img/abbattimento.png");
	ImageIcon marrySheepImage = new ImageIcon("./img/accoppiamento.png");
	ImageIcon endTurnImage = new ImageIcon("./img/fineTurno.png");
	ImageIcon marketImage = new ImageIcon("./img/market.png");
	
	ImageIcon player1Image = new ImageIcon("./img/giocatore1.png");
	ImageIcon player2Image = new ImageIcon("./img/giocatore2.png");
	ImageIcon player3Image = new ImageIcon("./img/giocatore3.png");
	ImageIcon player4Image = new ImageIcon("./img/giocatore4.png");

	ImageIcon pedina1 = new ImageIcon("./img/pedina1.png");
	ImageIcon pedina2 = new ImageIcon("./img/pedina2.png");
	ImageIcon pedina3 = new ImageIcon("./img/pedina3.png");
	ImageIcon pedina4 = new ImageIcon("./img/pedina4.png");
	
	ImageIcon recintoImage=new ImageIcon("./img/recinto.png");
	
	// immagini animali
	ImageIcon pecoraNeraImage = new ImageIcon("./img/pecoraNera.png");
	// TODO da inserire l'immagine
	ImageIcon pecoraImage = new ImageIcon();
	ImageIcon montoneImage = new ImageIcon();
	ImageIcon agnelloImage = new ImageIcon();
	ImageIcon lupoImage=new ImageIcon("./img/lupo.png");

	Point puntoAttuale = new Point();

	File file = new File("./img/Game_Board_Nascosta.png");
	BufferedImage image;
	BufferedImage mappaNascosta;

	// contatori presenti a schermo
	JLabel contatoreRecintiRimanenti = new JLabel();
	JLabel contatoreTessereGrano = new JLabel();
	JLabel contatoreTessereForesta = new JLabel();
	JLabel contatoreTesserePrateria = new JLabel();
	JLabel contatoreTessereRoccia = new JLabel();
	JLabel contatoreTessereAcqua = new JLabel();
	JLabel contatoreTessereSabbia = new JLabel();
	JLabel[] contatoreDenaroPlayer = new JLabel[4];
	

	// label del nome del giocatore
	JLabel[] nomePlayer = new JLabel[4];

	// Frame
	JFrame map = new JFrame();
	Container c;
	SignificatoColori significatoColori = new SignificatoColori();
	MappaturaPosizioni mappaturaPosizione = new MappaturaPosizioni();
	PosizioniLupo posizioniLupo=new PosizioniLupo();
	PosizioniPecore posizioniPecore=new PosizioniPecore();
	PosizioniMontoni posizioniMontoni=new PosizioniMontoni();
	PosizioniAgnelli posizioniAgnelli=new PosizioniAgnelli();
	

	// Dimensioni schermo
	Toolkit t = Toolkit.getDefaultToolkit();
	Dimension screenSize = t.getScreenSize();
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	int formWidth = 1452;
	int formHeight = 1292;

	// bottoni
	JLabel bottoneMovimentoPersonaggio = new JLabel();
	JLabel bottoneMovimentoPecora = new JLabel();
	JLabel bottoneAcquistoTessere = new JLabel();
	JLabel bottoneAbbattiPecora=new JLabel();
	JLabel bottoneAccoppiaPecore=new JLabel();
	JLabel bottoneMarket=new JLabel();
	JLabel bottoneEndTurn = new JLabel();

	// pedine
	// JLabel player1 = new JLabel();
	JLabel[] player = new JLabel[4];

	// pecora nera
	JLabel pecoraNera=new JLabel();
	
	// lupo 
	JLabel lupo = new JLabel();

	// variabili macchina a stati
	boolean richiestaPosizionamentoPastore = false;
	boolean movimentoPastore = false;
	boolean movimentoPecora = false;
	boolean acquistaTessere = false;
	boolean abbattimentoPecora = false;
	boolean accoppiaPecore=false;
	boolean market = false;
	
	// oggetto di comunicazione con il controllore
	ControllorePartitaClient controllorePartita;

	public Map(ControllorePartitaClient controllorePartita) {
		this.controllorePartita=controllorePartita;
		map.setResizable(false);
		int altezzaIdeale = (int) screenHeight * 90 / 100;
		int larghezzaIdeale = altezzaIdeale * formWidth / formHeight;
		if (altezzaIdeale < formHeight) {
			formWidth = larghezzaIdeale;
			formHeight = altezzaIdeale;
		}
		try {
			image = ImageIO.read(file);
			mappaNascosta = Disegno.getScaledImage(image, larghezzaIdeale,
					altezzaIdeale);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void creaMappa() {
		map.setSize(formWidth+6, formHeight+26);
		map.setLocation((int) (screenWidth - formWidth) / 2,
				(int) (screenHeight - formHeight - 67) / 2);
		map.setTitle("Sheepland");
		map.setVisible(true);
		c = map.getContentPane();
		c.setLocation(0, 0);
		c.setLayout(null);
		WindowListener i = new MyWindowAdapter();
		map.addWindowListener(i);
		c.addMouseListener(new MouseClickListener());
		map.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// disegna mappa di gioco
		JLabel mappaVisibile = Disegno.disegnaImmagine(mappa, formWidth,
				formHeight);
		mappaVisibile.setLocation(0, 0);

		// disegna zona tessere terreno
		JLabel tessereTerreno = Disegno.disegnaImmagine(terrainCardImage,
				(formHeight / terrainCardImage.getIconHeight())
						* terrainCardImage.getIconWidth(), formHeight);
		tessereTerreno.setLocation(0, 0);

		// disegna bottone movimento personaggio
		bottoneMovimentoPersonaggio = Disegno.disegnaImmagine(
				moveCharacterImage, moveCharacterImage.getIconWidth(),
				moveCharacterImage.getIconHeight());
		bottoneMovimentoPersonaggio.setLocation(5 * formWidth / 7,
				4 * formHeight / 5);

		// disegna bottone movimento pecora
		bottoneMovimentoPecora = Disegno.disegnaImmagine(moveSheepImage,
				moveSheepImage.getIconWidth(), moveSheepImage.getIconHeight());
		bottoneMovimentoPecora.setLocation(57 * formWidth / 70,
				4 * formHeight / 5);

		// disegna bottone acquisto tessere

		bottoneAcquistoTessere = Disegno.disegnaImmagine(buyTerrainCard,
				buyTerrainCard.getIconWidth(), buyTerrainCard.getIconHeight());
		bottoneAcquistoTessere.setLocation(64 * formWidth / 70,
				4 * formHeight / 5);

		// disegna bottone abbatti pecora
		
		bottoneAbbattiPecora =Disegno.disegnaImmagine(killSheepImage,
				killSheepImage.getIconWidth(), killSheepImage.getIconHeight());
		bottoneAbbattiPecora.setLocation(5 * formWidth / 7,
				90 * formHeight / 100);
		
		// disegna bottone accoppia pecore
		
		bottoneAccoppiaPecore =Disegno.disegnaImmagine(marrySheepImage,
				marrySheepImage.getIconWidth(), marrySheepImage.getIconHeight());
		bottoneAccoppiaPecore.setLocation(57 * formWidth / 70,
				90 * formHeight / 100);
		
		// disegna bottone market
		
		bottoneMarket =Disegno.disegnaImmagine(marketImage,
				marketImage.getIconWidth(), marketImage.getIconHeight());
		bottoneMarket.setLocation(64 * formWidth / 70,
				90 * formHeight / 100);
		
		// disegna bottone end turn
		
		bottoneEndTurn =Disegno.disegnaImmagine(endTurnImage,
				endTurnImage.getIconWidth(), endTurnImage.getIconHeight());
		bottoneEndTurn.setLocation(64 * formWidth / 70,
				70 * formHeight / 100);
		
		// disegna icona giocatori
		
		JLabel immagineGiocatore1 = Disegno.disegnaImmagine(player1Image,
				player1Image.getIconWidth(), player1Image.getIconHeight());
		immagineGiocatore1.setLocation(formWidth * 183 / 200,
				formHeight * 5 / 100);

		JLabel immagineGiocatore2 = Disegno.disegnaImmagine(player2Image,
				player2Image.getIconWidth(), player2Image.getIconHeight());
		immagineGiocatore2.setLocation(formWidth * 183 / 200,
				formHeight * 20 / 100);
		JLabel immagineGiocatore3 = Disegno.disegnaImmagine(player3Image,
				player3Image.getIconWidth(), player3Image.getIconHeight());
		immagineGiocatore3.setLocation(formWidth * 183 / 200,
				formHeight * 35 / 100);
		JLabel immagineGiocatore4 = Disegno.disegnaImmagine(player4Image,
				player4Image.getIconWidth(), player4Image.getIconHeight());
		immagineGiocatore4.setLocation(formWidth * 183 / 200,
				formHeight * 50 / 100);

		// disegna contatore recinti rimanenti
		JLabel immagineRecintiRimanenti = Disegno.disegnaImmagine(
				enclosureNumberImage, enclosureNumberImage.getIconWidth(),
				enclosureNumberImage.getIconHeight());
		immagineRecintiRimanenti.setLocation(formWidth / 7, 0);

		// disegna pedine giocatori
		player[0] = Disegno.disegnaImmagine(pedina1, pedina1.getIconWidth(),
				pedina1.getIconHeight());
		player[0].setLocation(0, 0);
		player[0].setVisible(false);
		player[1] = Disegno.disegnaImmagine(pedina2, pedina2.getIconWidth(),
				pedina2.getIconHeight());
		player[1].setLocation(0, 0);
		player[1].setVisible(false);
		player[2] = Disegno.disegnaImmagine(pedina3, pedina3.getIconWidth(),
				pedina3.getIconHeight());
		player[2].setLocation(0, 0);
		player[2].setVisible(false);
		player[3] = Disegno.disegnaImmagine(pedina4, pedina4.getIconWidth(),
				pedina4.getIconHeight());
		player[3].setLocation(0, 0);
		player[3].setVisible(false);
		
		// disegna pecora nera
		pecoraNera = Disegno.disegnaImmagine(pecoraNeraImage, pecoraNeraImage.getIconWidth(),
				pecoraNeraImage.getIconHeight());
		pecoraNera.setLocation((int) mappaturaPosizione
				.getLocalizzazione(new Posizione("Regione",0)).getX()
				* formWidth / 1452, (int) mappaturaPosizione
				.getLocalizzazione(new Posizione("Regione",0)).getY()
				* formHeight / 1292);
		
		// disegna lupo
		lupo=Disegno.disegnaImmagine(lupoImage, lupoImage.getIconWidth(),
				lupoImage.getIconHeight());
		lupo.setLocation((int) posizioniLupo
				.getLocalizzazione(new Posizione("Regione",0)).getX()
				* formWidth / 1452, (int) posizioniLupo
				.getLocalizzazione(new Posizione("Regione",0)).getY()
				* formHeight / 1292);
		
		
		// inserisci nome giocatore
		nomePlayer[0] = Disegno.creaContatore("Player1", 50, 15,
				(int) (immagineGiocatore1.getX() + immagineGiocatore1.getSize()
						.getWidth() * 20 / 100),
				(int) (immagineGiocatore1.getY() + immagineGiocatore1.getSize()
						.getHeight() * 77 / 100), Color.WHITE);
		nomePlayer[1] = Disegno.creaContatore("Player2", 50, 15,
				(int) (immagineGiocatore2.getX() + immagineGiocatore2.getSize()
						.getWidth() * 29 / 100),
				(int) (immagineGiocatore2.getY() + immagineGiocatore2.getSize()
						.getHeight() * 77 / 100), Color.WHITE);
		nomePlayer[2] = Disegno.creaContatore("Player3", 50, 15,
				(int) (immagineGiocatore3.getX() + immagineGiocatore3.getSize()
						.getWidth() * 29 / 100),
				(int) (immagineGiocatore3.getY() + immagineGiocatore3.getSize()
						.getHeight() * 77 / 100), Color.WHITE);
		nomePlayer[3] = Disegno.creaContatore("Player4", 50, 15,
				(int) (immagineGiocatore4.getX() + immagineGiocatore4.getSize()
						.getWidth() * 29 / 100),
				(int) (immagineGiocatore4.getY() + immagineGiocatore4.getSize()
						.getHeight() * 77 / 100), Color.WHITE);

		// inserisci contatori numerici
		contatoreRecintiRimanenti = Disegno
				.creaContatore(
						"0",
						15,
						10,
						(int) (immagineRecintiRimanenti.getX() + immagineRecintiRimanenti
								.getSize().getWidth() * 4 / 9),
						(int) immagineRecintiRimanenti.getSize().getHeight() * 7 / 12,
						Color.RED);

		contatoreTessereGrano = Disegno.creaContatore("0", 15, 10,
				(int) (tessereTerreno.getSize().getWidth() * 31 / 40),
				(int) tessereTerreno.getSize().getHeight() * 7 / 55, Color.RED);
		contatoreTessereSabbia = Disegno
				.creaContatore("0", 15, 10, (int) (tessereTerreno.getSize()
						.getWidth() * 31 / 40), (int) tessereTerreno.getSize()
						.getHeight() * 16 / 55, Color.RED);
		contatoreTessereForesta = Disegno
				.creaContatore("0", 15, 10, (int) (tessereTerreno.getSize()
						.getWidth() * 31 / 40), (int) tessereTerreno.getSize()
						.getHeight() * 26 / 57, Color.RED);
		contatoreTessereAcqua = Disegno
				.creaContatore("0", 15, 10, (int) (tessereTerreno.getSize()
						.getWidth() * 31 / 40), (int) tessereTerreno.getSize()
						.getHeight() * 34 / 54, Color.RED);
		contatoreTesserePrateria = Disegno
				.creaContatore("0", 15, 10, (int) (tessereTerreno.getSize()
						.getWidth() * 31 / 40), (int) tessereTerreno.getSize()
						.getHeight() * 45 / 57, Color.RED);
		contatoreTessereRoccia = Disegno.creaContatore("0", 15, 10,
				(int) (tessereTerreno.getSize().getWidth() * 31 / 40),
				(int) tessereTerreno.getSize().getHeight() * 96 / 100,
				Color.RED);

		// contatori denaro
		contatoreDenaroPlayer[0] = Disegno
				.creaContatore("0", 15, 10, (int) (immagineGiocatore1
						.getLocation().getX() + immagineGiocatore1.getSize()
						.getWidth() * 30 / 100), (int) (immagineGiocatore1
						.getLocation().getY() + immagineGiocatore1.getSize()
						.getHeight() * 20 / 100), Color.RED);
		contatoreDenaroPlayer[1] = Disegno
				.creaContatore("0", 15, 10, (int) (immagineGiocatore2
						.getLocation().getX() + immagineGiocatore2.getSize()
						.getWidth() * 39 / 100), (int) (immagineGiocatore2
						.getLocation().getY() + immagineGiocatore2.getSize()
						.getHeight() * 20 / 100), Color.BLUE);
		contatoreDenaroPlayer[2] = Disegno
				.creaContatore("0", 15, 10, (int) (immagineGiocatore3
						.getLocation().getX() + immagineGiocatore3.getSize()
						.getWidth() * 39 / 100), (int) (immagineGiocatore3
						.getLocation().getY() + immagineGiocatore3.getSize()
						.getHeight() * 20 / 100), Color.GREEN);
		contatoreDenaroPlayer[3] = Disegno
				.creaContatore("0", 15, 10, (int) (immagineGiocatore4
						.getLocation().getX() + immagineGiocatore4.getSize()
						.getWidth() * 39 / 100), (int) (immagineGiocatore4
						.getLocation().getY() + immagineGiocatore4.getSize()
						.getHeight() * 20 / 100), Color.YELLOW);


		// aggiunta elementi al container
		c.add(mappaVisibile, 0);
		c.add(tessereTerreno, 0);
		c.add(bottoneMovimentoPersonaggio, 0);
		c.add(bottoneMovimentoPecora, 0);
		c.add(bottoneAcquistoTessere, 0);
		c.add(bottoneAbbattiPecora,0);
		c.add(bottoneAccoppiaPecore,0);
		c.add(bottoneMarket,0);
		c.add(bottoneEndTurn,0);
		c.add(immagineRecintiRimanenti, 0);
		c.add(contatoreRecintiRimanenti, 0);
		c.add(contatoreTessereGrano, 0);
		c.add(contatoreTessereAcqua, 0);
		c.add(contatoreTessereSabbia, 0);
		c.add(contatoreTessereRoccia, 0);
		c.add(contatoreTessereForesta, 0);
		c.add(contatoreTesserePrateria, 0);
		c.add(immagineGiocatore1, 0);
		c.add(immagineGiocatore2, 0);
		c.add(immagineGiocatore3, 0);
		c.add(immagineGiocatore4, 0);
		c.add(contatoreDenaroPlayer[0], 0);
		c.add(contatoreDenaroPlayer[1], 0);
		c.add(contatoreDenaroPlayer[2], 0);
		c.add(contatoreDenaroPlayer[3], 0);
		c.add(nomePlayer[0], 0);
		c.add(nomePlayer[1], 0);
		c.add(nomePlayer[2], 0);
		c.add(nomePlayer[3], 0);
		c.add(pecoraNera,0);
		c.add(lupo,0);
		
		
		c.add(player[0], 0);
		c.add(player[1], 0);
		c.add(player[2],0);
		c.add(player[3],0);

		// avvio metodi handleDrag per pedine
		handleDragPedine(player[0]);
		handleDragPedine(player[1]);
		handleDragPedine(player[2]);
		handleDragPedine(player[3]);
		
		// avvio metodo handleDrag per pecora nera
		handleDragPecoraNera(pecoraNera);
		
		c.setVisible(true);

	}

	public Color calcoloColore(int x, int y) {

		Color colore = new Color(mappaNascosta.getRGB(x, y));

		return colore;
	}

	public void posizionePastore() {
		richiestaPosizionamentoPastore = true;
	}

	// movimento pedine

	public void handleDragPedine(final JLabel pedina) {
		final JLabel p = pedina;

		pedina.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				puntoAttuale.setLocation(p.getX(), p.getY());
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				Posizione posizione = significatoColori.getPosizione(calcoloColore(p.getX(), p.getY()));
				if(movimentoPastore /*&& !puntoAttuale.equals(p.getLocation())*/ && posizione.getTipo().equals("Strada")) {
					// TODO controllare se la mossa è eseguibile e, se si, mandare un aggiornamento della mossa
					JLabel recinto=new JLabel();
					recinto=Disegno.disegnaImmagine(recintoImage, recintoImage.getIconWidth(), recintoImage.getIconHeight());
					recinto.setLocation(puntoAttuale);
					c.add(recinto,0);
					recinto.validate();
					recinto.repaint();
					puntoAttuale.setLocation((int) mappaturaPosizione
							.getLocalizzazione(posizione).getX()
							* formWidth / 1452, (int) mappaturaPosizione
							.getLocalizzazione(posizione).getY()
							* formHeight / 1292);
					p.setLocation(puntoAttuale);
					p.repaint();
				} else {
					if(!posizione.getTipo().equals("Strada")) {
						p.setLocation(puntoAttuale);
						p.repaint();
					}
				}
				movimentoPastore=false;
			}
		});
		pedina.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				if (movimentoPastore) {
					me.translatePoint(me.getComponent().getLocation().x, me
							.getComponent().getLocation().y);
					p.setLocation(me.getX(), me.getY());
					p.repaint();
				}
			}

		});
	}
	
	
	// movimento pecora nera
	
	public void handleDragPecoraNera(final JLabel pecoraNera) {
		final JLabel p = pecoraNera;

		pecoraNera.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				puntoAttuale.setLocation(p.getX(), p.getY());
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				Posizione posizione = significatoColori.getPosizione(calcoloColore(p.getX(), p.getY()));
				if(movimentoPecora /*&& !puntoAttuale.equals(p.getLocation())*/ && posizione.getTipo().equals("Regione")) {
					// TODO controllare se fattibile, e se si, mandare aggiornamento mossa
					puntoAttuale.setLocation((int) mappaturaPosizione
							.getLocalizzazione(posizione).getX()
							* formWidth / 1452, (int) mappaturaPosizione
							.getLocalizzazione(posizione).getY()
							* formHeight / 1292);
					p.setLocation(puntoAttuale);
					p.repaint();
				} else {
					if(!posizione.getTipo().equals("Regione")) {
						p.setLocation(puntoAttuale);
						p.repaint();
					}
				}
				movimentoPecora=false;
			}
		});
		pecoraNera.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				if (movimentoPecora) {
					me.translatePoint(me.getComponent().getLocation().x, me
							.getComponent().getLocation().y);
					p.setLocation(me.getX(), me.getY());
					p.repaint();
				}
			}

		});
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * CLASSI DI LISTENER
	 */

	/**
	 * listener per eventi legati alla finestra
	 * 
	 * @author Matteo Daverio
	 * 
	 */
	private class MyWindowAdapter extends WindowAdapter {

		/**
		 * chiusura finestra
		 * 
		 * @author Matteo Daverio
		 */
		public void windowClosing(WindowEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "Vuoi uscire?",
					"Esci", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION)
				System.exit(0);
		}
	}

	/**
	 * listener eventi legati al click del mouse
	 * 
	 * @author Matteo Daverio
	 * 
	 */
	private class MouseClickListener implements MouseListener {

		/**
		 * click del mouse
		 * 
		 * @author Matteo Daverio
		 */
		public void mouseClicked(MouseEvent e) {

			Posizione posizione = null;

			try {

				posizione = significatoColori.getPosizione(calcoloColore(
						e.getX(), e.getY()));
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (richiestaPosizionamentoPastore) {
				if (posizione.getTipo() == "Strada") {
					// TODO invio di posizione.getPosizione() al controllore passare la posizione e il turno del pastore
					if (player[0].getX() == 0) {
						player[0].setLocation((int) mappaturaPosizione
								.getLocalizzazione(posizione).getX()
								* formWidth / 1452, (int) mappaturaPosizione
								.getLocalizzazione(posizione).getY()
								* formHeight / 1292);
						player[0].setVisible(true);
						player[0].removeAll();
						player[0].revalidate();
						player[0].repaint();
						richiestaPosizionamentoPastore = false;
					} else if (player[1].getX() == 0) {
						player[1].setLocation((int) mappaturaPosizione
								.getLocalizzazione(posizione).getX()
								* formWidth / 1452, (int) mappaturaPosizione
								.getLocalizzazione(posizione).getY()
								* formHeight / 1292);
						player[1].setVisible(true);
						player[1].removeAll();
						player[1].revalidate();
						player[1].repaint();
						richiestaPosizionamentoPastore = false;
					} else if (player[2].getX() == 0) {
						player[2].setLocation((int) mappaturaPosizione
								.getLocalizzazione(posizione).getX()
								* formWidth / 1452, (int) mappaturaPosizione
								.getLocalizzazione(posizione).getY()
								* formHeight / 1292);
						player[2].setVisible(true);
						player[2].removeAll();
						player[2].revalidate();
						player[2].repaint();
						richiestaPosizionamentoPastore = false;
					} else if (player[3].getX() == 0) {
						player[3].setLocation((int) mappaturaPosizione
								.getLocalizzazione(posizione).getX()
								* formWidth / 1452, (int) mappaturaPosizione
								.getLocalizzazione(posizione).getY()
								* formHeight / 1292);
						player[3].setVisible(true);
						player[3].removeAll();
						player[3].revalidate();
						player[3].repaint();
						richiestaPosizionamentoPastore = false;
					}
				}
			} else if (controlloArea(e, bottoneMovimentoPersonaggio)) {
				// TODO Muovi il pastore, bisogna azzerare gli altri stati
				movimentoPastore = true;
				movimentoPecora = false;
				acquistaTessere = false;
				abbattimentoPecora = false;
				accoppiaPecore=false;
				market=false;
				JOptionPane.showMessageDialog(null, "muovi pastore",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if (controlloArea(e, bottoneMovimentoPecora)) {
				// TODO Muovi la pecora, bisogna azzerare gli altri stati
				movimentoPastore =false;
				movimentoPecora = true;
				acquistaTessere = false;
				abbattimentoPecora = false;
				accoppiaPecore=false;
				market=false;
				JOptionPane.showMessageDialog(null, "muovi pecora",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if (controlloArea(e, bottoneAcquistoTessere)) {
				// TODO Acquista tessera, bisogna azzerare gli altri stati
				movimentoPastore = false;
				movimentoPecora = false;
				acquistaTessere = true;
				abbattimentoPecora = false;
				accoppiaPecore=false;
				market=false;
				JOptionPane.showMessageDialog(null, "acquista tessera",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if (controlloArea(e,bottoneAbbattiPecora)) {
				// TODO Abbatti pecora, bisogna azzerare gli altri stati
				movimentoPastore = false;
				movimentoPecora = false;
				acquistaTessere = false;
				abbattimentoPecora = true;
				accoppiaPecore=false;
				market=false;
				JOptionPane.showMessageDialog(null, "abbatti pecore",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if (controlloArea(e,bottoneAccoppiaPecore)) {
				// TODO Accoppia pecore, bisogna azzerare gli altri stati
				movimentoPastore = false;
				movimentoPecora = false;
				acquistaTessere = false;
				abbattimentoPecora = false;
				accoppiaPecore=true;
				market=false;
				JOptionPane.showMessageDialog(null, "accoppia pecore",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if (controlloArea(e,bottoneMarket)) {
				// TODO Effettua il market
				movimentoPastore = false;
				movimentoPecora = false;
				acquistaTessere = false;
				abbattimentoPecora = false;
				accoppiaPecore=false;
				market=true;
				JOptionPane.showMessageDialog(null, "vendi tessera",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if (controlloArea(e,bottoneEndTurn)) {
				// TODO esegui end turn
				JOptionPane.showMessageDialog(null, "fine turno",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			}

		}

		/**
		 * controlla se avvenuto il click del bottone
		 * 
		 * @param e
		 * @param button
		 * @return true se ho cliccato sul bottone
		 * 
		 * @author Matteo Daverio
		 */
		private boolean controlloArea(MouseEvent e, JLabel button) {
			return e.getX() > button.getX()
					&& e.getY() > button.getY()
					&& e.getX() < (button.getX() + button.getSize().getWidth())
					&& e.getY() < (button.getY() + button.getSize().getHeight());
		}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

	}
	
	
	
	
	
	// metodi di interazione con il server
	
	
	/**
	 * il controllore chiede il posizionamento di un pastore
	 * 
	 * @author Matteo Daverio
	 */
	public void posizionaPastore () {
		richiestaPosizionamentoPastore=true;
		JOptionPane.showMessageDialog(null, "Posiziona il tuo pastore", "Posiziona pastore",
				  JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * comunica al pastore l'inizio del suo turno
	 * 
	 * @author Matteo Daverio
	 */
	public void iniziaTurno () {
		// mostra a schermo un messaggio che indica l'inizio del turno primo metodo da attivare, per
		// far capire all'utente che può giocare
		JOptionPane.showMessageDialog(null, "Ora è il tuo turno",
				"Posizione", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// 
	/**
	 * il controllore lo chiama per effettuare la mossa della pecora nera a inizio turno
	 * 
	 * @param nuova posizione pecoraNera
	 * @author Matteo Daverio
	 */
	public void muoviPecoraNera(int posizione) {
		pecoraNera.setLocation((int) mappaturaPosizione
				.getLocalizzazione(new Posizione("Regione",posizione)).getX()
				* formWidth / 1452, (int) mappaturaPosizione
				.getLocalizzazione(new Posizione("Regione",posizione)).getY()
				* formHeight / 1292);
		pecoraNera.removeAll();
		pecoraNera.revalidate();
		pecoraNera.repaint();
	}
	
	
	/**
	 * il controllore lo chiama per mostrare a schermo 
	 * il posizionamento iniziale o i movimenti successivi dei pastori avversari
	 * 
	 * @param turnoGiocatore(turno del pastore avversario appena posizionato o mosso
	 * @param posizione(nuova posizione del pastore)
	 * @author Matteo Daverio
	 */
	public void muoviPastoreAvversario(int turnoGiocatore, int posizione) {
		JLabel recinto=new JLabel();
		recinto=Disegno.disegnaImmagine(recintoImage, recintoImage.getIconWidth(), recintoImage.getIconHeight());
		recinto.setLocation(player[turnoGiocatore-1].getLocation());
		c.add(recinto,0);
		recinto.validate();
		recinto.repaint();
		player[turnoGiocatore-1].setLocation((int) mappaturaPosizione
				.getLocalizzazione(new Posizione("Strada",posizione)).getX()
				* formWidth / 1452, (int) mappaturaPosizione
				.getLocalizzazione(new Posizione("Strada",posizione)).getY()
				* formHeight / 1292);
		player[turnoGiocatore-1].setVisible(true);
		player[turnoGiocatore-1].removeAll();
		player[turnoGiocatore-1].revalidate();
		player[turnoGiocatore-1].repaint();
	}
	
	/**
	 * metodo iniziale con cui le pecore vengono settate a una posizione
	 * 
	 * @param pecore posizionate a inizio partita
	 * @author Matteo Daverio
	 */
	public void inizializzaPecore(ArrayList<Pecora> arrayList) {
		// TODO riceve array di pecore, scandisce l'array e le posiziona al loro posto
	}
	
	// metodo con cui viene aggiornata la posizione della pecora numeroPecora 
	// che viene messa in posizione posizione
	public void muoviPecora(int numeroPecora, int tipoPecora, int posizione) {
		// TODO trovare l'elemento numeroPecora e modificarne la posizione con hashmap pecore, 
		// sfruttare il metodo annientaPecora per togliere la pecora dalla posizione di partenza
	}
	
	// metodo che riceve la nuova posizione del lupo
	public void muoviLupo(int posizione) {
		// TODO spostare il lupo nella nuova posizione
	}
	
	// metodo dal nome molto figo per abbattere la pecora o per darla in pasto a un lupo
	public void annientaPecora(int numeroPecora, int tipoPecora) {
		// TODO diminuire il contatore di pecora, se il contatore va a 0, togliere la sua icona
	}
	
	// metodo per inserire un nuovo agnello dopo l'accoppiamento
	public void aggiungiPecora(int posizione) {
		// TODO creare o incrementare di uno il contatore degli agnelli
	}
	

	
	// inserisce il nome di giocatori, riceve il nome del giocatore e il suo turno di gioco
	public void aggiornaNome(String nome, int turno) {
		// metodo da usare all'avvio del gioco come primissima cosa
		nomePlayer[turno-1].setText(nome);
	}
	
	// imposta il denaro del giocatore, riceve il denaro e il turno di gioco
	public void impostaDenaro(int denaro, int turno) {
		// impostare il denaro iniziale, subito dopo aver impostato i nomi
		contatoreDenaroPlayer[turno-1].setText(String.valueOf(denaro));
	}
	
	// imposta i recinti totali, da fare a avvio gioco
	public void totaleRecinti(int numeroRecinti) {
		contatoreRecintiRimanenti.setText(String.valueOf(numeroRecinti));
	}
	
	// passa la tessera iniziale al giocatore
	public void tesseraIniziale(/* oggetto di tipo enumerazione terreni */ ) {
		// TODO riceve un oggetto di tipo terreno e incrementa di uno il contatore dei terreni sulla tessera
	}
	
	// comunicazione di fine turno
	public void fineTurno() {
		JOptionPane.showMessageDialog(null, "fine turno",
				"Posizione", JOptionPane.INFORMATION_MESSAGE);
	}



	
}
