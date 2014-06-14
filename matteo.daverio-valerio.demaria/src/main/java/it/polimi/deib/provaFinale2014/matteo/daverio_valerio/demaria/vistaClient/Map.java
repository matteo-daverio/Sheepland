package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.List;

public class Map extends JFrame {

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
	ImageIcon killSheepImage = new ImageIcon("./img/abbattimento.png");
	ImageIcon marrySheepImage = new ImageIcon("./img/accoppiamento.png");

	ImageIcon player1Image = new ImageIcon("./img/giocatore1.png");
	ImageIcon player2Image = new ImageIcon("./img/giocatore2.png");
	ImageIcon player3Image = new ImageIcon("./img/giocatore3.png");
	ImageIcon player4Image = new ImageIcon("./img/giocatore4.png");

	ImageIcon recintoImage = new ImageIcon("./img/recinto.png");

	// immagini animali
	ImageIcon pecoraNeraImage = new ImageIcon("./img/pecoraNera.png");
	ImageIcon pecoraImage = new ImageIcon("./img/pecora.png");
	ImageIcon montoneImage = new ImageIcon("./img/montone.png");
	ImageIcon agnelloImage = new ImageIcon("./img/agnello.png");
	ImageIcon lupoImage = new ImageIcon("./img/lupo.png");

	Point puntoAttuale = new Point();

	File file = new File("./img/Game_Board_Nascosta.png");
	BufferedImage image;
	BufferedImage mappaNascosta;

	// contatori presenti a schermo
	JLabel contatoreRecintiRimanenti = new JLabel();
	JLabel contatoreTessereGrano = new JLabel();
	JLabel costoGrano = new JLabel();
	JLabel contatoreTessereForesta = new JLabel();
	JLabel costoForesta = new JLabel();
	JLabel contatoreTesserePrateria = new JLabel();
	JLabel costoPrateria = new JLabel();
	JLabel contatoreTessereRoccia = new JLabel();
	JLabel costoRoccia = new JLabel();
	JLabel contatoreTessereAcqua = new JLabel();
	JLabel costoAcqua = new JLabel();
	JLabel contatoreTessereSabbia = new JLabel();
	JLabel costoSabbia = new JLabel();
	JLabel[] contatoreDenaroPlayer = new JLabel[4];

	// label del nome del giocatore
	JLabel[] nomePlayer = new JLabel[4];
	JLabel immagineGiocatore1 = new JLabel();
	JLabel immagineGiocatore2 = new JLabel();
	JLabel immagineGiocatore3 = new JLabel();
	JLabel immagineGiocatore4 = new JLabel();

	// Frame
	JFrame map = new JFrame();
	JLabel tessereTerreno;
	Container c;
	SignificatoColori significatoColori = new SignificatoColori();
	MappaturaPosizioni mappaturaPosizione = new MappaturaPosizioni();
	PosizioniLupo posizioniLupo = new PosizioniLupo();
	PosizioniPecore posizioniPecore = new PosizioniPecore();
	PosizioniMontoni posizioniMontoni = new PosizioniMontoni();
	PosizioniAgnelli posizioniAgnelli = new PosizioniAgnelli();

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
	JLabel bottoneAbbattiPecora = new JLabel();
	JLabel bottoneAccoppiaPecore = new JLabel();

	// pedine
	// JLabel player1 = new JLabel();
	JLabel[] player = new JLabel[4];

	// pecora nera
	Ovino pecoraNera;

	// ovini e loro contatori
	Ovino[] pecora = new Ovino[19];
	JLabel[] contatorePecore = new JLabel[19];
	Ovino[] montone = new Ovino[19];
	JLabel[] contatoreMontoni = new JLabel[19];
	Ovino[] agnello = new Ovino[19];
	JLabel[] contatoreAgnelli = new JLabel[19];
	Posizione posizioneAttuale = new Posizione("Strada", 0);

	// lupo
	JLabel lupo = new JLabel();

	// variabili macchina a stati
	int numeroGiocatori;
	boolean richiestaPosizionamentoPastore = false;
	boolean movimentoPastore = false;
	boolean movimentoPecora = false;
	boolean acquistaTessere = false;
	boolean abbattimentoPecora = false;
	boolean accoppiaPecore = false;
	boolean mioTurno = false;

	// variabili di ritorno
	int posizioneInizialePastore = -20;
	int posizioneCorretta = -1;

	// oggetto di comunicazione con il controllore
	ControllorePartitaClient controllorePartita;

	public Map(ControllorePartitaClient controllorePartita) {
		this.controllorePartita = controllorePartita;
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
		map.setSize(formWidth + 6, formHeight + 26);
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
		tessereTerreno = Disegno.disegnaImmagine(terrainCardImage,
				(formHeight * terrainCardImage.getIconWidth())
						/ terrainCardImage.getIconHeight(), formHeight);
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

		bottoneAbbattiPecora = Disegno.disegnaImmagine(killSheepImage,
				killSheepImage.getIconWidth(), killSheepImage.getIconHeight());
		bottoneAbbattiPecora.setLocation(5 * formWidth / 7,
				90 * formHeight / 100);

		// disegna bottone accoppia pecore

		bottoneAccoppiaPecore = Disegno
				.disegnaImmagine(marrySheepImage,
						marrySheepImage.getIconWidth(),
						marrySheepImage.getIconHeight());
		bottoneAccoppiaPecore.setLocation(57 * formWidth / 70,
				90 * formHeight / 100);

		// disegna icona giocatori

		immagineGiocatore1 = Disegno.disegnaImmagine(player1Image,
				player1Image.getIconWidth(), player1Image.getIconHeight());
		immagineGiocatore1.setLocation(formWidth * 183 / 200,
				formHeight * 5 / 100);

		immagineGiocatore2 = Disegno.disegnaImmagine(player2Image,
				player2Image.getIconWidth(), player2Image.getIconHeight());
		immagineGiocatore2.setLocation(formWidth * 183 / 200,
				formHeight * 20 / 100);
		immagineGiocatore3 = Disegno.disegnaImmagine(player3Image,
				player3Image.getIconWidth(), player3Image.getIconHeight());
		immagineGiocatore3.setLocation(formWidth * 183 / 200,
				formHeight * 35 / 100);
		immagineGiocatore4 = Disegno.disegnaImmagine(player4Image,
				player4Image.getIconWidth(), player4Image.getIconHeight());
		immagineGiocatore4.setLocation(formWidth * 183 / 200,
				formHeight * 50 / 100);

		// disegna contatore recinti rimanenti
		JLabel immagineRecintiRimanenti = Disegno.disegnaImmagine(
				enclosureNumberImage, enclosureNumberImage.getIconWidth(),
				enclosureNumberImage.getIconHeight());
		immagineRecintiRimanenti.setLocation(formWidth / 7, 0);

		// disegna pecora nera
		pecoraNera = new Ovino("./img/pecoraNera.png");
		pecoraNera.setLocation(
				(int) mappaturaPosizione.getLocalizzazione(
						new Posizione("Regione", 0)).getX()
						* formWidth / 1452, (int) mappaturaPosizione
						.getLocalizzazione(new Posizione("Regione", 0)).getY()
						* formHeight / 1292);

		// disegna lupo
		lupo = Disegno.disegnaImmagine(lupoImage, lupoImage.getIconWidth(),
				lupoImage.getIconHeight());
		lupo.setLocation(
				(int) posizioniLupo.getLocalizzazione(
						new Posizione("Regione", 0)).getX()
						* formWidth / 1452, (int) posizioniLupo
						.getLocalizzazione(new Posizione("Regione", 0)).getY()
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
						String.valueOf(Costanti.NUMERO_RECINTI_NORMALI),
						15,
						10,
						(int) (immagineRecintiRimanenti.getX() + immagineRecintiRimanenti
								.getSize().getWidth() * 4 / 9),
						(int) immagineRecintiRimanenti.getSize().getHeight() * 7 / 12,
						Color.RED);

		costoGrano = Disegno.creaContatore("0", 15, 10, (int) (tessereTerreno
				.getSize().getWidth() * 31 / 40), (int) tessereTerreno
				.getSize().getHeight() * 7 / 55, Color.RED);
		contatoreTessereGrano = Disegno.creaContatore("0", 15, 10,
				(int) (tessereTerreno.getSize().getWidth() * 15 / 100),
				(int) tessereTerreno.getSize().getHeight() * 3 / 100,
				Color.WHITE);
		costoSabbia = Disegno.creaContatore("0", 15, 10, (int) (tessereTerreno
				.getSize().getWidth() * 31 / 40), (int) tessereTerreno
				.getSize().getHeight() * 16 / 55, Color.RED);
		contatoreTessereSabbia = Disegno.creaContatore("0", 15, 10,
				(int) (tessereTerreno.getSize().getWidth() * 15 / 100),
				(int) tessereTerreno.getSize().getHeight() * 20 / 100,
				Color.WHITE);
		costoForesta = Disegno.creaContatore("0", 15, 10, (int) (tessereTerreno
				.getSize().getWidth() * 31 / 40), (int) tessereTerreno
				.getSize().getHeight() * 26 / 57, Color.RED);
		contatoreTessereForesta = Disegno.creaContatore("0", 15, 10,
				(int) (tessereTerreno.getSize().getWidth() * 15 / 100),
				(int) tessereTerreno.getSize().getHeight() * 73 / 200,
				Color.WHITE);
		costoAcqua = Disegno.creaContatore("0", 15, 10, (int) (tessereTerreno
				.getSize().getWidth() * 31 / 40), (int) tessereTerreno
				.getSize().getHeight() * 34 / 54, Color.RED);
		contatoreTessereAcqua = Disegno.creaContatore("0", 15, 10,
				(int) (tessereTerreno.getSize().getWidth() * 15 / 100),
				(int) tessereTerreno.getSize().getHeight() * 53 / 100,
				Color.WHITE);
		costoPrateria = Disegno
				.creaContatore("0", 15, 10, (int) (tessereTerreno.getSize()
						.getWidth() * 31 / 40), (int) tessereTerreno.getSize()
						.getHeight() * 45 / 57, Color.RED);
		contatoreTesserePrateria = Disegno.creaContatore("0", 15, 10,
				(int) (tessereTerreno.getSize().getWidth() * 15 / 100),
				(int) tessereTerreno.getSize().getHeight() * 70 / 100,
				Color.WHITE);
		costoRoccia = Disegno.creaContatore("0", 15, 10, (int) (tessereTerreno
				.getSize().getWidth() * 31 / 40), (int) tessereTerreno
				.getSize().getHeight() * 96 / 100, Color.RED);
		contatoreTessereRoccia = Disegno.creaContatore("0", 15, 10,
				(int) (tessereTerreno.getSize().getWidth() * 15 / 100),
				(int) tessereTerreno.getSize().getHeight() * 173 / 200,
				Color.WHITE);

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
		c.add(bottoneAbbattiPecora, 0);
		c.add(bottoneAccoppiaPecore, 0);
		c.add(immagineRecintiRimanenti, 0);
		c.add(contatoreRecintiRimanenti, 0);
		c.add(contatoreTessereGrano, 0);
		c.add(contatoreTessereAcqua, 0);
		c.add(contatoreTessereSabbia, 0);
		c.add(contatoreTessereRoccia, 0);
		c.add(contatoreTessereForesta, 0);
		c.add(contatoreTesserePrateria, 0);
		c.add(costoGrano, 0);
		c.add(costoAcqua, 0);
		c.add(costoSabbia, 0);
		c.add(costoRoccia, 0);
		c.add(costoForesta, 0);
		c.add(costoPrateria, 0);
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
		c.add(pecoraNera, 0);
		c.add(lupo, 0);

		// avvio metodo handleDrag per pecora nera
		handleDragPecoraNera(pecoraNera);

		c.setVisible(true);

	}

	public Color calcoloColore(int x, int y) {

		Color colore = new Color(mappaNascosta.getRGB(x, y));

		return colore;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * MOVIMENTO ELEMENTI A SCHERMO CON DRAG AND DROP
	 */

	/**
	 * metodo per consentire il drag and drop alle pecore
	 * 
	 * @param immagine
	 * @author Matteo Daverio
	 */
	public void handleDragPecore(final JPanel immagine) {
		final JPanel p = immagine;

		immagine.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				// alla pressione del mouse, memorizza la posizione attuale
				puntoAttuale.setLocation(p.getX(), p.getY());
				posizioneAttuale = significatoColori
						.getPosizione(calcoloColore(p.getX(), p.getY()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Color color = calcoloColore(p.getX(), p.getY());
				Posizione posizione = significatoColori.getPosizione(color);
				// se posso muovere e sposto su una regione
				if (movimentoPecora && posizione.getTipo().equals("Regione")) {
					// memorizzo il punto in cui vorrei mettere la pecora
					Point punto = posizioniPecore.getLocalizzazione(posizione);

					// se il punto di arrivo è diverso dal punto di partenza e
					// non esistono agnelli nel punto di arrivo
					if (!punto.equals(puntoAttuale)
							&& pecora[posizione.getPosizione()] == null) {
						pecora[posizione.getPosizione()] = new Ovino(
								"./img/pecora.png");
						pecora[posizione.getPosizione()].setLocation(
								(int) punto.getX() * formWidth / 1452,
								(int) punto.getY() * formHeight / 1292);
						contatorePecore[posizione.getPosizione()] = new JLabel();
						contatorePecore[posizione.getPosizione()] = Disegno
								.creaContatore(
										"1",
										15,
										10,
										(int) (pecora[posizione.getPosizione()]
												.getLocation().getX() + pecora[posizione
												.getPosizione()].getSize()
												.getWidth() * 25 / 100),
										(int) (pecora[posizione.getPosizione()]
												.getLocation().getY() + pecora[posizione
												.getPosizione()].getSize()
												.getHeight() * 25 / 100),
										Color.WHITE);
						contatorePecore[posizione.getPosizione()]
								.setBorder(BorderFactory.createEmptyBorder(-2,
										4, 0, 0));
						pecora[posizione.getPosizione()]
								.add(contatorePecore[posizione.getPosizione()]);
						c.add(pecora[posizione.getPosizione()], 0);
						handleDragPecore(pecora[posizione.getPosizione()]);
						pecora[posizione.getPosizione()].validate();
						pecora[posizione.getPosizione()].repaint();
						// decrementa pecora iniziale
						pecora[posizioneAttuale.getPosizione()]
								.setLocation(puntoAttuale);
						pecora[posizioneAttuale.getPosizione()].repaint();
						decrementaContatorePecora(posizioneAttuale
								.getPosizione());
						// punto di partenza diverso da punto di arrivo, ma
						// pecora gia presente
					} else if (!punto.equals(puntoAttuale)) {
						incrementaContatore(contatorePecore[posizione
								.getPosizione()]);
						contatorePecore[posizione.getPosizione()].repaint();
						pecora[posizioneAttuale.getPosizione()]
								.setLocation(puntoAttuale);
						pecora[posizioneAttuale.getPosizione()].repaint();
						decrementaContatorePecora(posizioneAttuale
								.getPosizione());
					} else {
						// punto di partenza uguale a quello di arrivo
						p.setLocation(puntoAttuale);
						p.repaint();
					}
				}
				movimentoPecora = false;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				p.setLocation(puntoAttuale);
				p.repaint();
			}

		});
		immagine.addMouseMotionListener(new MouseMotionAdapter() {
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

	/**
	 * metodo per consentire il drag and drop ai montoni
	 * 
	 * @param immagine
	 * @author Matteo Daverio
	 */
	public void handleDragMontoni(final JPanel immagine) {
		final JPanel p = immagine;

		immagine.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				// alla pressione del mouse, memorizza la posizione attuale
				puntoAttuale.setLocation(p.getX(), p.getY());
				posizioneAttuale = significatoColori
						.getPosizione(calcoloColore(p.getX(), p.getY()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Color color = calcoloColore(p.getX(), p.getY());
				Posizione posizione = significatoColori.getPosizione(color);
				// se posso muovere e sposto su una regione
				if (movimentoPecora && posizione.getTipo().equals("Regione")) {
					// memorizzo il punto in cui vorrei mettere la pecora
					Point punto = posizioniMontoni.getLocalizzazione(posizione);

					// se il punto di arrivo è diverso dal punto di partenza e
					// non esistono agnelli nel punto di arrivo
					if (!punto.equals(puntoAttuale)
							&& montone[posizione.getPosizione()] == null) {
						montone[posizione.getPosizione()] = new Ovino(
								"./img/montone.png");

						montone[posizione.getPosizione()].setLocation(
								(int) punto.getX() * formWidth / 1452,
								(int) punto.getY() * formHeight / 1292);
						contatoreMontoni[posizione.getPosizione()] = new JLabel();
						contatoreMontoni[posizione.getPosizione()] = Disegno
								.creaContatore(
										"1",
										15,
										10,
										(int) (montone[posizione.getPosizione()]
												.getLocation().getX() + montone[posizione
												.getPosizione()].getSize()
												.getWidth() * 25 / 100),
										(int) (montone[posizione.getPosizione()]
												.getLocation().getY() + montone[posizione
												.getPosizione()].getSize()
												.getHeight() * 25 / 100),
										Color.WHITE);
						contatoreMontoni[posizione.getPosizione()]
								.setBorder(BorderFactory.createEmptyBorder(-2,
										4, 0, 0));
						montone[posizione.getPosizione()]
								.add(contatoreMontoni[posizione.getPosizione()]);
						c.add(montone[posizione.getPosizione()], 0);
						handleDragMontoni(montone[posizione.getPosizione()]);
						montone[posizione.getPosizione()].validate();
						montone[posizione.getPosizione()].repaint();
						// decrementa pecora iniziale
						montone[posizioneAttuale.getPosizione()].setLocation(
								(int) puntoAttuale.getX(),
								(int) puntoAttuale.getY());
						montone[posizioneAttuale.getPosizione()].revalidate();
						montone[posizioneAttuale.getPosizione()].repaint();
						decrementaContatoreMontone(posizioneAttuale
								.getPosizione());

						// punto di partenza diverso da punto di arrivo, ma
						// pecora gia presente
					} else if (!punto.equals(puntoAttuale)) {
						incrementaContatore(contatoreMontoni[posizione
								.getPosizione()]);
						contatoreMontoni[posizione.getPosizione()].revalidate();
						contatoreMontoni[posizione.getPosizione()].repaint();
						montone[posizioneAttuale.getPosizione()].setLocation(
								(int) puntoAttuale.getX(),
								(int) puntoAttuale.getY());
						montone[posizioneAttuale.getPosizione()].revalidate();
						montone[posizioneAttuale.getPosizione()].repaint();
						decrementaContatoreMontone(posizioneAttuale
								.getPosizione());
					} else {
						p.setLocation((int) puntoAttuale.getX(),
								(int) puntoAttuale.getY());
						p.revalidate();
						p.repaint();
					}
				}
				movimentoPecora = false;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				p.setLocation(puntoAttuale);
				p.repaint();
			}

		});
		// mouse drag
		immagine.addMouseMotionListener(new MouseMotionAdapter() {
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

	/**
	 * metodo per consentire il drag and drop agli agnelli
	 * 
	 * @param immagine
	 * @author Matteo Daverio
	 */
	public void handleDragAgnelli(final JPanel immagine) {
		final JPanel p = immagine;

		immagine.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				// alla pressione del mouse, memorizza la posizione attuale
				puntoAttuale.setLocation(p.getX(), p.getY());
				posizioneAttuale = significatoColori
						.getPosizione(calcoloColore(p.getX(), p.getY()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Color color = calcoloColore(p.getX(), p.getY());
				Posizione posizione = significatoColori.getPosizione(color);
				// se posso muovere e sposto su una regione
				if (movimentoPecora && posizione.getTipo().equals("Regione")) {
					// memorizzo il punto in cui vorrei mettere la pecora
					Point punto = posizioniAgnelli.getLocalizzazione(posizione);

					// se il punto di arrivo è diverso dal punto di partenza e
					// non esistono agnelli nel punto di arrivo
					if (!punto.equals(puntoAttuale)
							&& agnello[posizione.getPosizione()] == null) {
						agnello[posizione.getPosizione()] = new Ovino(
								"./img/agnello.png");

						agnello[posizione.getPosizione()].setLocation(
								(int) punto.getX() * formWidth / 1452,
								(int) punto.getY() * formHeight / 1292);
						contatoreAgnelli[posizione.getPosizione()] = new JLabel();
						contatoreAgnelli[posizione.getPosizione()] = Disegno
								.creaContatore(
										"1",
										15,
										10,
										(int) (agnello[posizione.getPosizione()]
												.getLocation().getX() + agnello[posizione
												.getPosizione()].getSize()
												.getWidth() * 25 / 100),
										(int) (agnello[posizione.getPosizione()]
												.getLocation().getY() + agnello[posizione
												.getPosizione()].getSize()
												.getHeight() * 25 / 100),
										Color.WHITE);
						contatoreAgnelli[posizione.getPosizione()]
								.setBorder(BorderFactory.createEmptyBorder(-2,
										4, 0, 0));
						agnello[posizione.getPosizione()]
								.add(contatoreAgnelli[posizione.getPosizione()]);
						c.add(agnello[posizione.getPosizione()], 0);
						handleDragAgnelli(agnello[posizione.getPosizione()]);
						agnello[posizione.getPosizione()].validate();
						agnello[posizione.getPosizione()].repaint();
						// decrementa il contatore della pecora iniziale
						agnello[posizioneAttuale.getPosizione()].setLocation(
								(int) puntoAttuale.getX(),
								(int) puntoAttuale.getY());
						agnello[posizioneAttuale.getPosizione()].revalidate();
						agnello[posizioneAttuale.getPosizione()].repaint();
						decrementaContatoreAgnello(posizioneAttuale
								.getPosizione());

						// punto di partenza diverso da punto di arrivo, ma
						// pecora gia presente
					} else if (!punto.equals(puntoAttuale)) {
						incrementaContatore(contatoreAgnelli[posizione
								.getPosizione()]);
						contatoreAgnelli[posizione.getPosizione()].revalidate();
						contatoreAgnelli[posizione.getPosizione()].repaint();
						agnello[posizioneAttuale.getPosizione()].setLocation(
								(int) puntoAttuale.getX(),
								(int) puntoAttuale.getY());
						agnello[posizioneAttuale.getPosizione()].revalidate();
						agnello[posizioneAttuale.getPosizione()].repaint();
						decrementaContatoreAgnello(posizioneAttuale
								.getPosizione());
					} else {
						// punto di partenza uguale a quello di arrivo
						p.setLocation((int) puntoAttuale.getX(),
								(int) puntoAttuale.getY());
						p.revalidate();
						p.repaint();
					}
				}
				movimentoPecora = false;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				p.setLocation(puntoAttuale);
				p.repaint();
			}

		});
		// mouse drag
		immagine.addMouseMotionListener(new MouseMotionAdapter() {
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

	/**
	 * da alla pedina del pastore la possibilità di essere mossa con il Drag and
	 * Drop
	 * 
	 * @param pedina
	 * @author Matteo Daverio
	 */
	public void handleDragPedine(final JLabel pedina) {
		final JLabel p = pedina;

		pedina.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				puntoAttuale.setLocation(p.getX(), p.getY());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Posizione posizione = significatoColori
						.getPosizione(calcoloColore(p.getX(), p.getY()));
				if (movimentoPastore /* && !puntoAttuale.equals(p.getLocation()) */
						&& posizione.getTipo().equals("Strada")) {
					// TODO controllare se la mossa è eseguibile e, se si,
					// mandare un aggiornamento della mossa
					JLabel recinto = new JLabel();
					recinto = Disegno.disegnaImmagine(recintoImage,
							recintoImage.getIconWidth(),
							recintoImage.getIconHeight());
					recinto.setLocation(puntoAttuale);
					c.add(recinto, 0);
					if (Integer.valueOf(contatoreRecintiRimanenti.getText()) > 0) {
						decrementaContatore(contatoreRecintiRimanenti);
					}
					recinto.validate();
					recinto.repaint();
					contatoreRecintiRimanenti.revalidate();
					contatoreRecintiRimanenti.repaint();
					puntoAttuale.setLocation(
							(int) mappaturaPosizione.getLocalizzazione(
									posizione).getX()
									* formWidth / 1452,
							(int) mappaturaPosizione.getLocalizzazione(
									posizione).getY()
									* formHeight / 1292);
					p.setLocation(puntoAttuale);
					p.repaint();
				} else {
					if (!posizione.getTipo().equals("Strada")) {
						p.setLocation(puntoAttuale);
						p.repaint();
					}
				}
				movimentoPastore = false;
			}
		});
		// listener per il drag
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

	/**
	 * da alla pecora nera la capacità di essere mossa con Drag and Drop
	 * 
	 * @param pecoraNera
	 * @author Matteo Daverio
	 */
	public void handleDragPecoraNera(final JPanel pecoraNera) {
		final JPanel p = pecoraNera;

		pecoraNera.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				puntoAttuale.setLocation(p.getX(), p.getY());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Posizione posizione = significatoColori
						.getPosizione(calcoloColore(p.getX(), p.getY()));
				if (movimentoPecora /* && !puntoAttuale.equals(p.getLocation()) */
						&& posizione.getTipo().equals("Regione")) {
					// TODO controllare se fattibile, e se si, mandare
					// aggiornamento mossa
					puntoAttuale.setLocation(
							(int) mappaturaPosizione.getLocalizzazione(
									posizione).getX()
									* formWidth / 1452,
							(int) mappaturaPosizione.getLocalizzazione(
									posizione).getY()
									* formHeight / 1292);
					p.setLocation(puntoAttuale);
					p.repaint();
				} else {
					if (!posizione.getTipo().equals("Regione")) {
						p.setLocation(puntoAttuale);
						p.repaint();
					}
				}
				movimentoPecora = false;
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
					// TODO invio di posizione.getPosizione() al controllore
					// passare la posizione e il turno del pastore
					if (player[0] == null) {
						posizionaPastore(posizione, player[0],
								"./img/pedina1.png");
					} else if (player[1] == null) {
						if (numeroGiocatori == 2) {
							posizionaPastore(posizione, player[1],
									"./img/pedina1.png");
						} else {
							posizionaPastore(posizione, player[1],
									"./img/pedina2.png");
						}
					} else if (player[2] == null) {
						if (numeroGiocatori == 2) {
							posizionaPastore(posizione, player[2],
									"./img/pedina2.png");
						} else {
							posizionaPastore(posizione, player[2],
									"./img/pedina3.png");
						}
					} else if (player[3] == null) {
						if (numeroGiocatori == 2) {
							posizionaPastore(posizione, player[3],
									"./img/pedina2.png");
						} else {
							posizionaPastore(posizione, player[3],
									"./img/pedina4.png");
						}
					}
				}
			} else if (controlloArea(e, bottoneMovimentoPersonaggio) && mioTurno) {
				aggiornaVariabili(1);
				JOptionPane.showMessageDialog(null, "muovi pastore",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if (controlloArea(e, bottoneMovimentoPecora) && mioTurno) {
				aggiornaVariabili(2);
				JOptionPane.showMessageDialog(null, "muovi pecora",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if (controlloArea(e, bottoneAcquistoTessere) && mioTurno) {
				aggiornaVariabili(3);
				JOptionPane.showMessageDialog(null, "acquista tessera",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if (controlloArea(e, bottoneAbbattiPecora) && mioTurno) {
				aggiornaVariabili(4);
				JOptionPane.showMessageDialog(null, "abbatti pecore",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if (controlloArea(e, bottoneAccoppiaPecore) && mioTurno) {
				aggiornaVariabili(5);
				JOptionPane.showMessageDialog(null, "accoppia pecore",
						"Posizione", JOptionPane.INFORMATION_MESSAGE);
			} else if(abbattimentoPecora && mioTurno) {
				// TODO abbattimento pecora
				// TODO non va messa qui, deve essere presa al click su una pecora
			} else if(accoppiaPecore && mioTurno) {
				// TODO accoppia pecore
			} else if(acquistaTessere && mioTurno){
				// TODO acquisto tessere
				// TODO bisogna calcolare che il click sia su uno dei riquadri tessera
				// passo e.getX() e e.getY() e ottengo la stringa del tipo di terreno
				// TODO passare calcolaBottone(e.getX(),e.getY());
				TipoTerreno s=calcolaBottone(e.getX(),e.getY());
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

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * metodi di supporto
	 */

	/**
	 * incrementa il numero presente nella JLabel
	 * 
	 * @param label
	 * @author Matteo Daverio
	 */
	private void incrementaContatore(JLabel label) {
		label.setText(String.valueOf(Integer.valueOf(label.getText()) + 1));
	}

	/**
	 * decrementa il numero presente nella JLabel
	 * 
	 * @param label
	 * @author Matteo Daverio
	 */
	private void decrementaContatore(JLabel label) {
		label.setText(String.valueOf(Integer.valueOf(label.getText()) + 1));
	}

	/**
	 * decrementa il contatore delle pecore
	 * 
	 * @param terreno
	 * @author Matteo Daverio
	 */
	public void decrementaContatorePecora(int terreno) {
		int numeroPecore = Integer.valueOf(contatorePecore[terreno].getText());
		if (numeroPecore > 1) {
			numeroPecore--;
			contatorePecore[terreno].setText(String.valueOf(numeroPecore));
			contatorePecore[terreno].repaint();
		} else {
			contatorePecore[terreno].removeAll();
			remove(pecora[terreno]);
			pecora[terreno].removeAll();
			pecora[terreno].setVisible(false);
			pecora[terreno] = null;
			repaint();
		}
	}

	/**
	 * decrementa il contatore dei montoni
	 * 
	 * @param terreno
	 * @author Matteo Daverio
	 */
	public void decrementaContatoreMontone(int terreno) {
		int numeroMontoni = Integer
				.valueOf(contatoreMontoni[terreno].getText());
		if (numeroMontoni > 1) {
			numeroMontoni--;
			contatoreMontoni[terreno].setText(String.valueOf(numeroMontoni));
			contatoreMontoni[terreno].repaint();
		} else {
			contatoreMontoni[terreno].removeAll();
			remove(montone[terreno]);
			montone[terreno].removeAll();
			montone[terreno].setVisible(false);
			montone[terreno] = null;
			repaint();
		}
	}

	/**
	 * decrementa il contatore dei agnello
	 * 
	 * @param terreno
	 * @author Matteo Daverio
	 */
	public void decrementaContatoreAgnello(int terreno) {
		int numeroAgnelli = Integer
				.valueOf(contatoreAgnelli[terreno].getText());
		if (numeroAgnelli > 1) {
			numeroAgnelli--;
			contatoreAgnelli[terreno].setText(String.valueOf(numeroAgnelli));
			contatoreAgnelli[terreno].repaint();
		} else {
			contatoreAgnelli[terreno].removeAll();
			remove(agnello[terreno]);
			agnello[terreno].removeAll();
			agnello[terreno].setVisible(false);
			agnello[terreno] = null;
			repaint();
		}
	}

	public void aggiornaVariabili(int tasto) {
		movimentoPastore = false;
		movimentoPecora = false;
		acquistaTessere = false;
		abbattimentoPecora = false;
		accoppiaPecore = false;
		if (tasto == 1) {
			movimentoPastore = true;
		} else if (tasto == 2) {
			movimentoPecora = true;
		} else if (tasto == 3) {
			acquistaTessere = true;
		} else if (tasto == 4) {
			abbattimentoPecora = true;
		} else if (tasto == 5) {
			accoppiaPecore = true;
		}
	}

	public void inserisciAnimale(Ovino animale, JLabel contatore, Point punto,
			int tipoAnimale) {

		animale.setLocation((int) punto.getX() * formWidth / 1452,
				(int) punto.getY() * formHeight / 1292);
		contatore = Disegno.creaContatore("1", 15, 10,
				(int) (animale.getLocation().getX() + animale.getSize()
						.getWidth() * 25 / 100), (int) (animale.getLocation()
						.getY() + animale.getSize().getHeight() * 25 / 100),
				Color.WHITE);
		contatore.setBorder(BorderFactory.createEmptyBorder(-2, 4, 0, 0));
		animale.add(contatore);
		c.add(animale, 0);

		handleDragAgnelli(animale);
		animale.validate();
		contatore.validate();
		animale.repaint();
		contatore.repaint();

	}

	private void posizionaPastore(Posizione posizione, JLabel pedina,
			String path) {
		posizioneInizialePastore = posizione.getPosizione();
		// TODO passare al server posizioneInizialePastore, se true andare
		// avanti a posizionare
		pedina = new Pedina(path);
		pedina.setLocation((int) mappaturaPosizione
				.getLocalizzazione(posizione).getX() * formWidth / 1452,
				(int) mappaturaPosizione.getLocalizzazione(posizione).getY()
						* formHeight / 1292);
		pedina.removeAll();
		pedina.revalidate();
		pedina.repaint();
		richiestaPosizionamentoPastore = false;

	}

	/**
	 * restituisce il tipo di terreno su cui l'utente ha cliccato, se il click
	 * non è avvenuto su una tessera terreno, come errore ritorna Sheepsburg
	 * 
	 * @param x
	 * @param y
	 * @return TipoTerreno corrispondente al click, o Sheepsburg se click errato
	 * @author Matteo Daverio
	 */
	private TipoTerreno calcolaBottone(int x, int y) {
		TipoTerreno posizione;

		if (x > 4 * formWidth / 1452 && x < 80 * formWidth / 1452) {
			if (y > 5 * formHeight / 1292 && y < 83 * formHeight / 1292) {
				posizione = TipoTerreno.GRANO;
			} else if (y > 95 * formHeight / 1292
					&& y < 170 * formHeight / 1292) {
				posizione = TipoTerreno.SABBIA;
			} else if (y > 185 * formHeight / 1292
					&& y < 260 * formHeight / 1292) {
				posizione = TipoTerreno.FORESTA;
			} else if (y > 273 * formHeight / 1292
					&& y < 350 * formHeight / 1292) {
				posizione = TipoTerreno.ACQUA;
			} else if (y > 362 * formHeight / 1292
					&& y < 440 * formHeight / 1292) {
				posizione = TipoTerreno.PRATERIA;
			} else if (y > 452 * formHeight / 1292
					&& y < 528 * formHeight / 1292) {
				posizione = TipoTerreno.ROCCIA;
			} else {
				// come tipo terreno sheepsburg rappresenta l'errore nel click
				posizione = TipoTerreno.SHEEPSBURG;
			}
		} else {
			// sheepsburg rappresenta l'errore nel click
			posizione = TipoTerreno.SHEEPSBURG;
		}

		return posizione;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * metodi di interazione con il server
	 */

	/**
	 * comunica al pastore l'inizio del suo turno
	 * 
	 * @author Matteo Daverio
	 */
	public void iniziaTurno() {
		// mostra a schermo un messaggio che indica l'inizio del turno primo
		// metodo da attivare, per
		// far capire all'utente che può giocare
		mioTurno = true;
		JOptionPane.showMessageDialog(null, "Ora è il tuo turno", "Posizione",
				JOptionPane.INFORMATION_MESSAGE);
	}

	//
	/**
	 * il controllore lo chiama per effettuare la mossa della pecora nera a
	 * inizio turno
	 * 
	 * @param nuova
	 *            posizione pecoraNera
	 * @author Matteo Daverio
	 */
	public void muoviPecoraNera(int posizione) {
		pecoraNera.setLocation(
				(int) mappaturaPosizione.getLocalizzazione(
						new Posizione("Regione", posizione)).getX()
						* formWidth / 1452, (int) mappaturaPosizione
						.getLocalizzazione(new Posizione("Regione", posizione))
						.getY()
						* formHeight / 1292);
		pecoraNera.removeAll();
		pecoraNera.revalidate();
		pecoraNera.repaint();
	}

	/**
	 * il controllore lo chiama per mostrare a schermo il posizionamento
	 * iniziale o i movimenti successivi dei pastori avversari
	 * 
	 * @param turnoGiocatore
	 *            (turno del pastore avversario appena posizionato o mosso
	 * @param posizione
	 *            (nuova posizione del pastore)
	 * @author Matteo Daverio
	 */
	public void muoviPastoreAvversario(int turnoGiocatore, int posizione) {
		JLabel recinto = new JLabel();
		recinto = Disegno.disegnaImmagine(recintoImage,
				recintoImage.getIconWidth(), recintoImage.getIconHeight());
		recinto.setLocation(player[turnoGiocatore - 1].getLocation());
		c.add(recinto, 0);
		if (Integer.valueOf(contatoreRecintiRimanenti.getText()) > 0) {
			decrementaContatore(contatoreRecintiRimanenti);
		}
		contatoreRecintiRimanenti.revalidate();
		recinto.validate();
		contatoreRecintiRimanenti.repaint();
		recinto.repaint();
		player[turnoGiocatore - 1].setLocation(
				(int) mappaturaPosizione.getLocalizzazione(
						new Posizione("Strada", posizione)).getX()
						* formWidth / 1452,
				(int) mappaturaPosizione.getLocalizzazione(
						new Posizione("Strada", posizione)).getY()
						* formHeight / 1292);
		player[turnoGiocatore - 1].setVisible(true);
		player[turnoGiocatore - 1].removeAll();
		player[turnoGiocatore - 1].revalidate();
		player[turnoGiocatore - 1].repaint();
	}

	/**
	 * metodo iniziale con cui le pecore vengono settate a una posizione
	 * 
	 * @param pecore
	 *            posizionate a inizio partita
	 * @author Matteo Daverio
	 */
	public void inizializzaPecore(List<Pecora> ovini) {

		for (Pecora capra : ovini) {

			if (capra.getTipoPecora() == Costanti.TIPO_PECORA_AGNELLO) {
				Point punto = posizioniAgnelli.getLocalizzazione(new Posizione(
						"Regione", capra.getPosizione()));
				if (agnello[capra.getPosizione()] == null) {
					agnello[capra.getPosizione()] = new Ovino(
							"./img/agnello.png");
					agnello[capra.getPosizione()].setLocation(
							(int) punto.getX() * formWidth / 1452,
							(int) punto.getY() * formHeight / 1292);
					contatoreAgnelli[capra.getPosizione()] = Disegno
							.creaContatore(
									"1",
									15,
									10,
									(int) (agnello[capra.getPosizione()]
											.getLocation().getX() + agnello[capra
											.getPosizione()].getSize()
											.getWidth() * 25 / 100),
									(int) (agnello[capra.getPosizione()]
											.getLocation().getY() + agnello[capra
											.getPosizione()].getSize()
											.getHeight() * 25 / 100),
									Color.WHITE);
					contatoreAgnelli[capra.getPosizione()]
							.setBorder(BorderFactory.createEmptyBorder(-2, 4,
									0, 0));
					agnello[capra.getPosizione()].add(contatoreAgnelli[capra
							.getPosizione()]);
					c.add(agnello[capra.getPosizione()], 0);

					handleDragAgnelli(agnello[capra.getPosizione()]);
					agnello[capra.getPosizione()].validate();
					contatoreAgnelli[capra.getPosizione()].validate();
					agnello[capra.getPosizione()].repaint();
					contatoreAgnelli[capra.getPosizione()].repaint();
				} else {
					incrementaContatore(contatoreAgnelli[capra.getPosizione()]);
					contatoreAgnelli[capra.getPosizione()].revalidate();
					contatoreAgnelli[capra.getPosizione()].repaint();
				}
			} else if (capra.getTipoPecora() == Costanti.TIPO_PECORA_PECORA) {
				Point punto = posizioniPecore.getLocalizzazione(new Posizione(
						"Regione", capra.getPosizione()));
				if (pecora[capra.getPosizione()] == null) {
					pecora[capra.getPosizione()] = new Ovino("./img/pecora.png");
					pecora[capra.getPosizione()].setLocation((int) punto.getX()
							* formWidth / 1452, (int) punto.getY() * formHeight
							/ 1292);
					contatorePecore[capra.getPosizione()] = Disegno
							.creaContatore(
									"1",
									15,
									10,
									(int) (pecora[capra.getPosizione()]
											.getLocation().getX() + pecora[capra
											.getPosizione()].getSize()
											.getWidth() * 25 / 100),
									(int) (pecora[capra.getPosizione()]
											.getLocation().getY() + pecora[capra
											.getPosizione()].getSize()
											.getHeight() * 25 / 100),
									Color.WHITE);
					contatorePecore[capra.getPosizione()]
							.setBorder(BorderFactory.createEmptyBorder(-2, 4,
									0, 0));
					pecora[capra.getPosizione()].add(contatorePecore[capra
							.getPosizione()]);
					c.add(pecora[capra.getPosizione()], 0);

					handleDragPecore(pecora[capra.getPosizione()]);
					pecora[capra.getPosizione()].validate();
					contatorePecore[capra.getPosizione()].validate();
					pecora[capra.getPosizione()].repaint();
					contatorePecore[capra.getPosizione()].repaint();
				} else {
					incrementaContatore(contatorePecore[capra.getPosizione()]);
					contatorePecore[capra.getPosizione()].revalidate();
					contatorePecore[capra.getPosizione()].repaint();
				}
			} else {
				Point punto = posizioniMontoni.getLocalizzazione(new Posizione(
						"Regione", capra.getPosizione()));
				if (montone[capra.getPosizione()] == null) {
					montone[capra.getPosizione()] = new Ovino(
							"./img/montone.png");
					montone[capra.getPosizione()].setLocation(
							(int) punto.getX() * formWidth / 1452,
							(int) punto.getY() * formHeight / 1292);
					contatoreMontoni[capra.getPosizione()] = Disegno
							.creaContatore(
									"1",
									15,
									10,
									(int) (montone[capra.getPosizione()]
											.getLocation().getX() + montone[capra
											.getPosizione()].getSize()
											.getWidth() * 25 / 100),
									(int) (montone[capra.getPosizione()]
											.getLocation().getY() + montone[capra
											.getPosizione()].getSize()
											.getHeight() * 25 / 100),
									Color.WHITE);
					contatoreMontoni[capra.getPosizione()]
							.setBorder(BorderFactory.createEmptyBorder(-2, 4,
									0, 0));
					montone[capra.getPosizione()].add(contatoreMontoni[capra
							.getPosizione()]);
					c.add(montone[capra.getPosizione()], 0);

					handleDragMontoni(montone[capra.getPosizione()]);
					montone[capra.getPosizione()].validate();
					contatoreMontoni[capra.getPosizione()].validate();
					montone[capra.getPosizione()].repaint();
					contatoreMontoni[capra.getPosizione()].repaint();
				} else {
					incrementaContatore(contatoreMontoni[capra.getPosizione()]);
					contatoreMontoni[capra.getPosizione()].revalidate();
					contatoreMontoni[capra.getPosizione()].repaint();
				}
			}
		}
	}

	// metodo che riceve la nuova posizione del lupo
	public void muoviLupo(int posizione) {
		// TODO spostare il lupo nella nuova posizione
	}

	// metodo dal nome molto figo per abbattere la pecora o per darla in pasto a
	// un lupo
	public void annientaPecora(int numeroPecora, int tipoPecora) {
		// TODO diminuire il contatore di pecora, se il contatore va a 0,
		// togliere la sua icona
	}

	// metodo per inserire un nuovo agnello dopo l'accoppiamento
	public void aggiungiPecora(int posizione) {
		// TODO creare o incrementare di uno il contatore degli agnelli
	}

	public void numeroGiocatori(int numeroGiocatori) {
		this.numeroGiocatori = numeroGiocatori;
		if (numeroGiocatori < 4) {
			nomePlayer[3].setVisible(false);
			contatoreDenaroPlayer[3].setVisible(false);
			immagineGiocatore4.setVisible(false);
			if (numeroGiocatori < 3) {
				nomePlayer[2].setVisible(false);
				contatoreDenaroPlayer[2].setVisible(false);
				immagineGiocatore3.setVisible(false);
			}
		}
	}

	/**
	 * inserisce il nome di giocatori, riceve il nome del giocatore e il suo
	 * turno di gioco
	 * 
	 * @param nome
	 * @param turno
	 * @author Matteo Daverio
	 */
	public void aggiornaNome(String nome, int turno) {
		// metodo da usare all'avvio del gioco come primissima cosa
		nomePlayer[turno].setText(nome);
	}

	/**
	 * imposta il denaro del giocatore, riceve il denaro e il turno di gioco
	 * 
	 * @param denaro
	 * @param turno
	 * @author Matteo Daverio
	 */
	public void impostaDenaro(int denaro, int turno) {
		// impostare il denaro iniziale, subito dopo aver impostato i nomi
		contatoreDenaroPlayer[turno].setText(String.valueOf(denaro));
	}

	/**
	 * imposta i recinti totali, da fare a avvio gioco
	 * 
	 * @param numeroRecinti
	 * @author Matteo Daverio
	 */
	public void totaleRecinti(int numeroRecinti) {
		contatoreRecintiRimanenti.setText(String.valueOf(numeroRecinti));
	}

	/**
	 * riceve la tessera iniziale del giocatore riceve un oggetto di tipo
	 * terreno e incrementa di uno il contatore dei terreni sulla tessera
	 * 
	 * @param terreno
	 *            terreno iniziale del giocatore
	 * @author Matteo Daverio
	 */
	public void tesseraIniziale(TipoTerreno terreno) {
		if (terreno.equals("GRANO")) {
			incrementaContatore(contatoreTessereGrano);
		} else if (terreno.equals("FORESTA")) {
			incrementaContatore(contatoreTessereForesta);
		} else if (terreno.equals("PRATERIA")) {
			incrementaContatore(contatoreTesserePrateria);
		} else if (terreno.equals("ROCCIA")) {
			incrementaContatore(contatoreTessereRoccia);
		} else if (terreno.equals("ACQUA")) {
			incrementaContatore(contatoreTessereAcqua);
		} else if (terreno.equals("SABBIA")) {
			incrementaContatore(contatoreTessereSabbia);
		} else {
			// errore in ricezione della tessera iniziale
			JOptionPane.showMessageDialog(null,
					"errore ricezione tessera iniziale", "Errore",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * comunicazione di fine turno
	 * 
	 * @author Matteo Daverio
	 */
	public void fineTurno() {
		// comunicazione di fine turno tramite una JOptionPane
		mioTurno = false;
		JOptionPane.showMessageDialog(null, "fine turno", "Posizione",
				JOptionPane.INFORMATION_MESSAGE);
		// resetta il valore delle variabili di stato dei pulsanti
		aggiornaVariabili(0);
	}

}
