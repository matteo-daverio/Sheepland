package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Map extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3256210950304048274L;

	private static final Logger LOG = Logger.getLogger(Map.class.getName());
	List<MosseEnum> mosseDisponibili=new ArrayList<MosseEnum>();

	// per verificare se la mossa è corretta, e nel caso non lo sia, ritornare
	// allo stato di partenza
	// salvo lo stato di partenza in queste variabili

	private String oggettoMosso;
	private JLabel recintoDaTogliere;
	private int posizioneDiPartenza = -1;
	private int posizioneDiArrivo = -1;
	private int mioTurnoDiGioco;

	boolean scegliPastore = false;
	int pastoreScelto;

	private Timer timer;
	private Messaggio messaggio;

	// Immagini presenti a schermo
	private ImageIcon mappa = new ImageIcon("./img/Game_Board.jpg");
	private ImageIcon terrainCardImage = new ImageIcon(
			"./img/tessereTerreno.jpeg");
	private ImageIcon moveCharacterImage = new ImageIcon(
			"./img/iconaPersonaggio.png");
	private ImageIcon moveSheepImage = new ImageIcon("./img/iconaPecora.png");
	private ImageIcon buyTerrainCard = new ImageIcon(
			"./img/acquistoTessere.png");
	private ImageIcon enclosureNumberImage = new ImageIcon(
			"./img/numeroRecinti.png");
	private ImageIcon killSheepImage = new ImageIcon("./img/abbattimento.png");
	private ImageIcon marrySheepImage = new ImageIcon("./img/accoppiamento.png");

	private ImageIcon player1Image = new ImageIcon("./img/giocatore1.png");
	private ImageIcon player2Image = new ImageIcon("./img/giocatore2.png");
	private ImageIcon player3Image = new ImageIcon("./img/giocatore3.png");
	private ImageIcon player4Image = new ImageIcon("./img/giocatore4.png");

	private ImageIcon recintoImage = new ImageIcon("./img/recinto.png");

	// immagini animali
	private ImageIcon lupoImage = new ImageIcon("./img/lupo.png");

	private Point puntoAttuale = new Point();

	private File file = new File("./img/Game_Board_Nascosta.png");
	private BufferedImage image;
	private BufferedImage mappaNascosta;

	// contatori presenti a schermo
	private JLabel contatoreRecintiRimanenti = new JLabel();
	private JLabel contatoreTessereGrano = new JLabel();
	private JLabel costoGrano = new JLabel();
	private JLabel contatoreTessereForesta = new JLabel();
	private JLabel costoForesta = new JLabel();
	private JLabel contatoreTesserePrateria = new JLabel();
	private JLabel costoPrateria = new JLabel();
	private JLabel contatoreTessereRoccia = new JLabel();
	private JLabel costoRoccia = new JLabel();
	private JLabel contatoreTessereAcqua = new JLabel();
	private JLabel costoAcqua = new JLabel();
	private JLabel contatoreTessereSabbia = new JLabel();
	private JLabel costoSabbia = new JLabel();
	private JLabel[] contatoreDenaroPlayer = new JLabel[4];

	// label del nome del giocatore
	private JLabel[] nomePlayer = new JLabel[4];
	private JLabel immagineGiocatore1 = new JLabel();
	private JLabel immagineGiocatore2 = new JLabel();
	private JLabel immagineGiocatore3 = new JLabel();
	private JLabel immagineGiocatore4 = new JLabel();

	// Frame
	private JFrame map = new JFrame();
	private JLabel tessereTerreno;
	private Container c;
	private SignificatoColori significatoColori = new SignificatoColori();
	private MappaturaPosizioni mappaturaPosizione = new MappaturaPosizioni();
	private PosizioniLupo posizioniLupo = new PosizioniLupo();
	private PosizioniPecore posizioniPecore = new PosizioniPecore();
	private PosizioniMontoni posizioniMontoni = new PosizioniMontoni();
	private PosizioniAgnelli posizioniAgnelli = new PosizioniAgnelli();

	// Dimensioni schermo
	private Toolkit t = Toolkit.getDefaultToolkit();
	private Dimension screenSize = t.getScreenSize();
	private double screenWidth = screenSize.getWidth();
	private double screenHeight = screenSize.getHeight();
	private int formWidth = 1452;
	private int formHeight = 1292;
	private int tessereWidth;
	private int tessereHeight;

	// bottoni
	private JLabel bottoneMovimentoPersonaggio = new JLabel();
	private JLabel bottoneMovimentoPecora = new JLabel();
	private JLabel bottoneAcquistoTessere = new JLabel();
	private JLabel bottoneAbbattiPecora = new JLabel();
	private JLabel bottoneAccoppiaPecore = new JLabel();

	// pedine
	// JLabel player1 = new JLabel();
	private JLabel[] player = new JLabel[4];

	// pecora nera
	private Ovino pecoraNera;

	// ovini e loro contatori
	private Ovino[] pecora = new Ovino[19];
	private JLabel[] contatorePecore = new JLabel[19];
	private Ovino[] montone = new Ovino[19];
	private JLabel[] contatoreMontoni = new JLabel[19];
	private Ovino[] agnello = new Ovino[19];
	private JLabel[] contatoreAgnelli = new JLabel[19];
	private Posizione posizioneAttuale = new Posizione("Strada", 0);

	// lupo
	private JLabel lupo = new JLabel();

	// variabili macchina a stati
	private int numeroGiocatori;
	private boolean richiestaPosizionamentoPastore = false;
	private boolean movimentoPastore = false;
	private boolean movimentoPecora = false;
	private boolean acquistaTessere = false;
	private boolean abbattimentoPecora = false;
	private boolean accoppiaPecore = false;
	private boolean mioTurno = false;

	// oggetto di comunicazione con il controllore
	private ControllorePartitaClient controllorePartita;
	private GuiImpl guiImpl;

	/**
	 * costruttore
	 * 
	 * @param controllorePartita
	 * @author Matteo Daverio
	 */
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
			LOG.log(Level.SEVERE, "Caricamento mappa fallito", e);
		}
	}

	/**
	 * crea la mappa
	 * 
	 * @author Matteo Daverio
	 */
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
		tessereWidth = (formHeight * terrainCardImage.getIconWidth())
				/ terrainCardImage.getIconHeight();
		tessereHeight = formHeight;
		tessereTerreno = Disegno.disegnaImmagine(terrainCardImage,
				tessereWidth, tessereHeight);
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
		immagineRecintiRimanenti.setLocation(formWidth * 80 / 100, 0);

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
								.getSize().getWidth() * 35 / 90),
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

	/**
	 * calcola il colore associato a un punto
	 * 
	 * @param x
	 * @param y
	 * @return colore
	 * @author Matteo Daverio
	 */
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
			/**
			 * evento mousePressed
			 * @author Matteo Daverio
			 */
			public void mousePressed(MouseEvent me) {
				// alla pressione del mouse, memorizza la posizione attuale
				puntoAttuale.setLocation(p.getX(), p.getY());
				posizioneAttuale = significatoColori
						.getPosizione(calcoloColore(p.getX() + me.getX(),
								p.getY() + me.getY()));
				oggettoMosso = "pecora";
				posizioneDiPartenza = posizioneAttuale.getPosizione();
			}

			@Override
			/**
			 * evento mouseReleased
			 * @author Matteo
			 */
			public void mouseReleased(MouseEvent e) {
				Color color = calcoloColore(p.getX() + e.getX(),
						p.getY() + e.getY());
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
						posizioneDiArrivo = posizione.getPosizione();
						// punto di partenza diverso da punto di arrivo, ma
						// pecora gia presente
						if (numeroGiocatori == 2) {
							guiImpl.muoviPecora(pastoreScelto,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_PECORA);
						} else {
							guiImpl.muoviPecora(mioTurnoDiGioco - 1,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_PECORA);
						}
					} else if (!punto.equals(puntoAttuale)) {
						incrementaContatore(contatorePecore[posizione
								.getPosizione()]);
						contatorePecore[posizione.getPosizione()].repaint();
						pecora[posizioneAttuale.getPosizione()]
								.setLocation(puntoAttuale);
						pecora[posizioneAttuale.getPosizione()].repaint();
						decrementaContatorePecora(posizioneAttuale
								.getPosizione());
						posizioneDiArrivo = posizione.getPosizione();
						if (numeroGiocatori == 2) {
							guiImpl.muoviPecora(pastoreScelto,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_PECORA);
						} else {
							guiImpl.muoviPecora(mioTurnoDiGioco - 1,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_PECORA);
						}
					} else {
						// punto di partenza uguale a quello di arrivo
						p.setLocation(puntoAttuale);
						p.repaint();
					}
				}
				movimentoPecora = false;
			}

			@Override
			/**
			 * evento mouseClicked
			 * @author Matteo Daverio
			 */
			public void mouseClicked(MouseEvent e) {
				p.setLocation(puntoAttuale);
				p.repaint();
				if (abbattimentoPecora && mioTurno) {
					oggettoMosso = "abbatti";
					posizioneDiPartenza = Costanti.TIPO_PECORA_PECORA;
					posizioneDiArrivo = significatoColori.getPosizione(
							calcoloColore(p.getX(), p.getY())).getPosizione();
					decrementaContatorePecora(significatoColori.getPosizione(
							calcoloColore(p.getX(), p.getY())).getPosizione());
					if (numeroGiocatori == 2) {
						guiImpl.mandaAbbattimento(posizioneDiArrivo,
								posizioneDiPartenza, pastoreScelto);
					} else {
						guiImpl.mandaAbbattimento(posizioneDiArrivo,
								posizioneDiPartenza, mioTurnoDiGioco - 1);
					}
				}
			}

		});
		immagine.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			/**
			 * evento mousedragged
			 * @author Matteo Daverio
			 */
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
			/**
			 * evento mousePressed
			 * @author Matteo Daverio
			 */
			public void mousePressed(MouseEvent me) {
				// alla pressione del mouse, memorizza la posizione attuale
				puntoAttuale.setLocation(p.getX(), p.getY());
				posizioneAttuale = significatoColori
						.getPosizione(calcoloColore(p.getX() + me.getX(),
								p.getY() + me.getY()));
				oggettoMosso = "montone";
				posizioneDiPartenza = posizioneAttuale.getPosizione();
			}

			@Override
			/**
			 * evento mouseReleased
			 * @author Matteo Daverio
			 */
			public void mouseReleased(MouseEvent e) {
				Color color = calcoloColore(p.getX() + e.getX(),
						p.getY() + e.getY());
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
						posizioneDiArrivo = posizione.getPosizione();
						// punto di partenza diverso da punto di arrivo, ma
						// pecora gia presente

						if (numeroGiocatori == 2) {
							guiImpl.muoviPecora(pastoreScelto,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_MONTONE);
						} else {
							guiImpl.muoviPecora(mioTurnoDiGioco - 1,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_MONTONE);
						}
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
						posizioneDiArrivo = posizione.getPosizione();
						if (numeroGiocatori == 2) {
							guiImpl.muoviPecora(pastoreScelto,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_MONTONE);
						} else {
							guiImpl.muoviPecora(mioTurnoDiGioco - 1,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_MONTONE);
						}
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
			/**
			 * evento mouseClicked
			 * @author Matteo
			 */
			public void mouseClicked(MouseEvent e) {
				p.setLocation(puntoAttuale);
				p.repaint();
				if (abbattimentoPecora && mioTurno) {
					oggettoMosso = "abbatti";
					posizioneDiPartenza = Costanti.TIPO_PECORA_MONTONE;
					posizioneDiArrivo = significatoColori.getPosizione(
							calcoloColore(p.getX(), p.getY())).getPosizione();
					decrementaContatoreMontone(significatoColori.getPosizione(
							calcoloColore(p.getX(), p.getY())).getPosizione());
					if (numeroGiocatori == 2) {
						guiImpl.mandaAbbattimento(posizioneDiArrivo,
								posizioneDiPartenza, pastoreScelto);
					} else {
						guiImpl.mandaAbbattimento(posizioneDiArrivo,
								posizioneDiPartenza, mioTurnoDiGioco - 1);
					}
				}
			}

		});
		// mouse drag
		immagine.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			/**
			 * evento mousedragged
			 * @author Matteo
			 */
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
			/**
			 * evento mousePressed
			 * @author Matteo
			 */
			public void mousePressed(MouseEvent me) {
				// alla pressione del mouse, memorizza la posizione attuale
				puntoAttuale.setLocation(p.getX(), p.getY());
				posizioneAttuale = significatoColori
						.getPosizione(calcoloColore(p.getX() + me.getX(),
								p.getY() + me.getY()));
				oggettoMosso = "agnello";
				posizioneDiPartenza = posizioneAttuale.getPosizione();
			}

			@Override
			/**
			 * evento mouseReleased
			 * @author Matteo 
			 */
			public void mouseReleased(MouseEvent e) {
				Color color = calcoloColore(p.getX() + e.getX(),
						p.getY() + e.getY());
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
						posizioneDiArrivo = posizione.getPosizione();
						// punto di partenza diverso da punto di arrivo, ma
						// pecora gia presente

						if (numeroGiocatori == 2) {
							guiImpl.muoviPecora(pastoreScelto,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_AGNELLO);
						} else {
							guiImpl.muoviPecora(mioTurnoDiGioco - 1,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_AGNELLO);
						}
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
						posizioneDiArrivo = posizione.getPosizione();
						if (numeroGiocatori == 2) {
							guiImpl.muoviPecora(pastoreScelto,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_AGNELLO);
						} else {
							guiImpl.muoviPecora(mioTurnoDiGioco - 1,
									posizioneDiPartenza, posizioneDiArrivo,
									Costanti.TIPO_PECORA_AGNELLO);
						}
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
			/**
			 * evento mouseClicked
			 * @author Matteo
			 */
			public void mouseClicked(MouseEvent e) {
				p.setLocation(puntoAttuale);
				p.repaint();
				if (abbattimentoPecora && mioTurno) {
					// creare metodi di rollback
					oggettoMosso = "abbatti";
					posizioneDiPartenza = Costanti.TIPO_PECORA_AGNELLO;
					posizioneDiArrivo = significatoColori.getPosizione(
							calcoloColore(p.getX(), p.getY())).getPosizione();
					decrementaContatoreAgnello(significatoColori.getPosizione(
							calcoloColore(p.getX(), p.getY())).getPosizione());
					if (numeroGiocatori == 2) {
						guiImpl.mandaAbbattimento(posizioneDiArrivo,
								posizioneDiPartenza, pastoreScelto);
					} else {
						guiImpl.mandaAbbattimento(posizioneDiArrivo,
								posizioneDiPartenza, mioTurnoDiGioco - 1);
					}

				}
			}

		});
		// mouse drag
		immagine.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			/**
			 * evento mouseDragged
			 * @author Matteo
			 */
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
			/**
			 * evento mousePressed
			 * @author Matteo
			 */
			public void mousePressed(MouseEvent me) {

				int i;
				puntoAttuale.setLocation(p.getX(), p.getY());
				posizioneAttuale = significatoColori
						.getPosizione(calcoloColore(p.getX() + me.getX(),
								p.getY() + me.getY()));
				oggettoMosso = "pedina";
				for (i = 0; i < player.length; i++) {
					if (player[i].getLocation().equals(puntoAttuale)) {
						posizioneDiPartenza = i;
						break;
					}
				}

			}

			@Override
			/**
			 * evento mouseReleased
			 * @author Matteo
			 */
			public void mouseReleased(MouseEvent e) {
				Posizione posizione = significatoColori
						.getPosizione(calcoloColore(p.getX() + e.getX(),
								p.getY() + e.getY()));
				if (movimentoPastore && posizione.getTipo().equals("Strada")
						&& !posizione.equals(posizioneAttuale)) {
					recintoDaTogliere = new JLabel();
					recintoDaTogliere = Disegno.disegnaImmagine(recintoImage,
							recintoImage.getIconWidth(),
							recintoImage.getIconHeight());
					recintoDaTogliere.setLocation(puntoAttuale);
					c.add(recintoDaTogliere, 0);
					if (Integer.valueOf(contatoreRecintiRimanenti.getText()) > 0) {
						decrementaContatore(contatoreRecintiRimanenti);
					}
					recintoDaTogliere.validate();
					recintoDaTogliere.repaint();
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
					p.revalidate();
					p.repaint();
					controllorePartita.muoviPastore(posizione.getPosizione(),
							posizioneDiPartenza);
				} else {
					if (!posizione.getTipo().equals("Strada")
							|| posizione.equals(posizioneAttuale)) {
						p.setLocation(puntoAttuale);
						p.revalidate();
						p.repaint();
					}
				}
				movimentoPastore = false;

			}

			@Override
			/**
			 * evento mouseClicked
			 * @author Matteo
			 */
			public void mouseClicked(MouseEvent e) {
				if (scegliPastore) {
					Posizione posizionePastoreUsabile = significatoColori
							.getPosizione(calcoloColore(p.getX() + e.getX(),
									p.getY() + e.getY()));

					for (int i = 0; i < 4; i++) {
						if ((int) player[i].getLocation().getX() == (int) mappaturaPosizione
								.getLocalizzazione(posizionePastoreUsabile)
								.getX()
								* formWidth / 1452
								&& (int) player[i].getLocation().getY() == (int) mappaturaPosizione
										.getLocalizzazione(
												posizionePastoreUsabile).getY()
										* formHeight / 1292) {
							pastoreScelto = i;
							scegliPastore = false;
							messaggio = new Messaggio("Pastore scelto");
							messaggio
									.setLocation(new Point(formWidth / 10, 10));
							c.add(messaggio, 0);
							repaint();
							messaggio.repaint();
							timer = new Timer(2000, new TimerTask(messaggio, c));
							timer.start();
							break;
						}
					}
				}
			}

		});
		// listener per il drag
		pedina.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			/**
			 * evento mouseDragged
			 * @author Matteo
			 */
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
			/**
			 * evento mousePressed
			 * @param me
			 * @author Matteo
			 */
			public void mousePressed(MouseEvent me) {
				puntoAttuale.setLocation(p.getX(), p.getY());
				posizioneAttuale = significatoColori
						.getPosizione(calcoloColore(p.getX() + me.getX(),
								p.getY() + me.getX()));
				oggettoMosso = "pecoranera";
				posizioneDiPartenza = posizioneAttuale.getPosizione();
			}

			@Override
			/**
			 * evento mouseReleased
			 * @param e
			 * @author Matteo
			 */
			public void mouseReleased(MouseEvent e) {
				Posizione posizione = significatoColori
						.getPosizione(calcoloColore(p.getX() + e.getX(),
								p.getY() + e.getY()));
				if (movimentoPecora && posizione.getTipo().equals("Regione")) {
					puntoAttuale.setLocation(
							(int) mappaturaPosizione.getLocalizzazione(
									posizione).getX()
									* formWidth / 1452,
							(int) mappaturaPosizione.getLocalizzazione(
									posizione).getY()
									* formHeight / 1292);
					p.setLocation(puntoAttuale);
					p.repaint();
					posizioneDiArrivo = posizione.getPosizione();
					if (numeroGiocatori == 2) {
						guiImpl.mandaMossaPecoraNera(pastoreScelto,
								posizioneDiPartenza, posizioneDiArrivo);
					} else {
						guiImpl.mandaMossaPecoraNera(mioTurnoDiGioco - 1,
								posizioneDiPartenza, posizioneDiArrivo);
					}
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
			/**
			 * evento mouseDragged
			 * @author Matteo
			 */
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
				LOG.log(Level.SEVERE, "Calcolo posizione errato", e1);
			} catch (Exception e1) {
				LOG.log(Level.SEVERE, "Calcolo posizione errato", e1);
			}

			if (richiestaPosizionamentoPastore) {
				if (posizione.getTipo() == "Strada") {
					if (player[0] == null) {
						posizionaPastore(posizione, 0, "./img/pedina1.png");
						handleDragPedine(player[0]);
						oggettoMosso = "posizionamento";
						posizioneDiPartenza = 0;
						controllorePartita.posizioneInserita(posizione
								.getPosizione());

					} else if (player[1] == null) {
						if (numeroGiocatori == 2) {
							posizionaPastore(posizione, 1, "./img/pedina1.png");
						} else {
							posizionaPastore(posizione, 1, "./img/pedina2.png");
						}
						handleDragPedine(player[1]);
						oggettoMosso = "posizionamento";
						posizioneDiPartenza = 1;
						controllorePartita.posizioneInserita(posizione
								.getPosizione());

					} else if (player[2] == null) {
						if (numeroGiocatori == 2) {
							posizionaPastore(posizione, 2, "./img/pedina2.png");
						} else {
							posizionaPastore(posizione, 2, "./img/pedina3.png");
						}
						handleDragPedine(player[2]);
						oggettoMosso = "posizionamento";
						posizioneDiPartenza = 2;
						controllorePartita.posizioneInserita(posizione
								.getPosizione());

					} else if (player[3] == null) {
						if (numeroGiocatori == 2) {
							posizionaPastore(posizione, 3, "./img/pedina2.png");
						} else {
							posizionaPastore(posizione, 3, "./img/pedina4.png");
						}
						handleDragPedine(player[3]);
						oggettoMosso = "posizionamento";
						posizioneDiPartenza = 3;
						controllorePartita.posizioneInserita(posizione
								.getPosizione());

					}
				}
			} else if (controlloArea(e, bottoneMovimentoPersonaggio)
					&& mioTurno && !scegliPastore && mosseDisponibili.contains(MosseEnum.MUOVI_PASTORE)) {
				aggiornaVariabili(1);
				messaggio = new Messaggio("Muovi personaggio");
				messaggio.setLocation(new Point(formWidth / 10, 10));
				c.add(messaggio, 0);
				repaint();
				messaggio.repaint();
				timer = new Timer(2000, new TimerTask(messaggio, c));
				timer.start();
			} else if (controlloArea(e, bottoneMovimentoPecora) && mioTurno
					&& !scegliPastore && mosseDisponibili.contains(MosseEnum.MUOVI_PECORA)) {
				aggiornaVariabili(2);
				messaggio = new Messaggio("Muovi pecora");
				messaggio.setLocation(new Point(formWidth / 10, 10));
				c.add(messaggio, 0);
				repaint();
				messaggio.repaint();
				timer = new Timer(2000, new TimerTask(messaggio, c));
				timer.start();
			} else if (controlloArea(e, bottoneAcquistoTessere) && mioTurno
					&& !scegliPastore && mosseDisponibili.contains(MosseEnum.COMPRA_TESSERA)) {
				aggiornaVariabili(3);
				messaggio = new Messaggio("Acquista tessera");
				messaggio.setLocation(new Point(formWidth / 10, 10));
				c.add(messaggio, 0);
				repaint();
				messaggio.repaint();
				timer = new Timer(2000, new TimerTask(messaggio, c));
				timer.start();
			} else if (controlloArea(e, bottoneAbbattiPecora) && mioTurno
					&& !scegliPastore && mosseDisponibili.contains(MosseEnum.ABBATTI)) {
				aggiornaVariabili(4);
				messaggio = new Messaggio("Abbatti pecore");
				messaggio.setLocation(new Point(formWidth / 10, 10));
				c.add(messaggio, 0);
				repaint();
				messaggio.repaint();
				timer = new Timer(2000, new TimerTask(messaggio, c));
				timer.start();
			} else if (controlloArea(e, bottoneAccoppiaPecore) && mioTurno
					&& !scegliPastore && mosseDisponibili.contains(MosseEnum.ACCOPPIA)) {
				aggiornaVariabili(5);
				messaggio = new Messaggio("Accoppia pecore");
				messaggio.setLocation(new Point(formWidth / 10, 10));
				c.add(messaggio, 0);
				repaint();
				messaggio.repaint();
				timer = new Timer(2000, new TimerTask(messaggio, c));
				timer.start();
			} else if (accoppiaPecore && mioTurno) {
				if (posizione.getTipo().equals("Regione")) {
					oggettoMosso = "accoppia";
					posizioneDiPartenza = posizione.getPosizione();
					aggiungiAgnello(posizione.getPosizione());
					if (numeroGiocatori == 2) {
						controllorePartita.accoppia(posizione.getPosizione(),
								pastoreScelto);
					} else {
						controllorePartita.accoppia(posizione.getPosizione(),
								mioTurnoDiGioco - 1);
					}
				}
				accoppiaPecore = false;
			} else if (acquistaTessere && mioTurno) {
				int terreno = calcolaBottone(e.getX(), e.getY());
				if (terreno > 0 && terreno < 7) {
					acquistaTessera(terreno);
					oggettoMosso = "acquistotessera";
					posizioneDiPartenza = terreno;
					if (numeroGiocatori == 2) {
						controllorePartita
								.compraTessera(terreno, pastoreScelto);
					} else {
						controllorePartita.compraTessera(terreno,
								mioTurnoDiGioco - 1);
					}
				}
				acquistaTessere = false;
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

		/**
		 * mouseEntered
		 */
		public void mouseEntered(MouseEvent e) {
		}

		/**
		 * mouseExited
		 */
		public void mouseExited(MouseEvent e) {
		}

		/**
		 * mousePressed
		 */
		public void mousePressed(MouseEvent e) {
		}

		/**
		 * mouseReleased
		 */
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
		label.setText(String.valueOf(Integer.valueOf(label.getText()) - 1));
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

	/**
	 * aggiorna le variabili della macchina a stati
	 * 
	 * @param tasto
	 * @author Matteo Daverio
	 */
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

	/**
	 * posiziona un pastore
	 * 
	 * @param posizione
	 * @param pedina
	 * @param path
	 * @author Matteo Daverio
	 */
	private void posizionaPastore(Posizione posizione, int pedina, String path) {
		player[pedina] = new Pedina(path);
		player[pedina].setLocation(
				(int) mappaturaPosizione.getLocalizzazione(posizione).getX()
						* formWidth / 1452, (int) mappaturaPosizione
						.getLocalizzazione(posizione).getY()
						* formHeight
						/ 1292);
		c.add(player[pedina], 0);
		player[pedina].removeAll();
		player[pedina].validate();
		player[pedina].revalidate();
		player[pedina].repaint();
		richiestaPosizionamentoPastore = false;

	}

	/**
	 * metodo per l'acquisto della tessera
	 * 
	 * @param terreno
	 * @author Matteo Daverio
	 */
	private void acquistaTessera(int terreno) {
		switch (terreno) {
		case 3:
			incrementaContatore(contatoreTessereGrano);
			incrementaContatore(costoGrano);
			break;
		case 6:
			incrementaContatore(contatoreTessereSabbia);
			incrementaContatore(costoSabbia);
			break;
		case 2:
			incrementaContatore(contatoreTessereForesta);
			incrementaContatore(costoForesta);
			break;
		case 1:
			incrementaContatore(contatoreTessereAcqua);
			incrementaContatore(costoAcqua);
			break;
		case 4:
			incrementaContatore(contatoreTesserePrateria);
			incrementaContatore(costoPrateria);
			break;
		case 5:
			incrementaContatore(contatoreTessereRoccia);
			incrementaContatore(costoRoccia);
			break;
		default:
			break;
		}
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
	private int calcolaBottone(int x, int y) {
		int posizione;
		if (x > 4 * tessereWidth / 97 && x < 80 * tessereWidth / 97) {
			if (y > 5 * tessereHeight / 535 && y < 83 * tessereHeight / 535) {
				posizione = 3;
			} else if (y > 95 * tessereHeight / 535
					&& y < 170 * tessereHeight / 535) {
				posizione = 6;
			} else if (y > 185 * tessereHeight / 535
					&& y < 260 * tessereHeight / 535) {
				posizione = 2;
			} else if (y > 273 * tessereHeight / 535
					&& y < 350 * tessereHeight / 535) {
				posizione = 1;
			} else if (y > 362 * tessereHeight / 535
					&& y < 440 * tessereHeight / 535) {
				posizione = 4;
			} else if (y > 452 * tessereHeight / 535
					&& y < 528 * tessereHeight / 535) {
				posizione = 5;
			} else {
				// come tipo terreno sheepsburg rappresenta l'errore nel click
				posizione = 0;
			}
		} else {
			// sheepsburg rappresenta l'errore nel click
			posizione = 0;
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
	public void iniziaTurno(int turno) {
		// mostra a schermo un messaggio che indica l'inizio del turno primo
		// metodo da attivare, per
		// far capire all'utente che può giocare
		mioTurno = true;
		mioTurnoDiGioco = turno;
		if (numeroGiocatori == 2) {
			scegliPastore = true;
			messaggio = new Messaggio(
					"E' il tuo turno, clicca sul pastore da usare");
		} else {
			messaggio = new Messaggio("E' il tuo turno");
		}
		messaggio.setLocation(new Point(formWidth / 10, 110));
		c.add(messaggio, 0);
		repaint();
		messaggio.repaint();
		timer = new Timer(4000, new TimerTask(messaggio, c));
		timer.start();
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
	 *            (indice del pastore avversario appena posizionato o mosso
	 * @param posizione
	 *            (nuova posizione del pastore)
	 * @author Matteo Daverio
	 */
	public void muoviPastoreAvversario(int turnoGiocatore, int posizione,
			String giocatore) {
		messaggio = new Messaggio("Il giocatore si muove in posizione "
				+ posizione);
		messaggio.setLocation(new Point(formWidth / 10, 10));
		c.add(messaggio, 0);
		repaint();
		messaggio.repaint();
		timer = new Timer(2000, new TimerTask(messaggio, c));
		timer.start();
		muoviPastoreAvversario(turnoGiocatore, posizione);
	}

	/**
	 * muove il pastore dell'avversario
	 * 
	 * @param turnoGiocatore
	 * @param posizione
	 * @author Matteo Daverio
	 */
	public void muoviPastoreAvversario(int turnoGiocatore, int posizione) {
		if (player[turnoGiocatore] != null) {
			JLabel recinto = new JLabel();
			recinto = Disegno.disegnaImmagine(recintoImage,
					recintoImage.getIconWidth(), recintoImage.getIconHeight());
			recinto.setLocation(player[turnoGiocatore].getLocation());
			c.add(recinto, 0);
			if (Integer.valueOf(contatoreRecintiRimanenti.getText()) > 0) {
				decrementaContatore(contatoreRecintiRimanenti);
			}
			contatoreRecintiRimanenti.revalidate();
			recinto.validate();
			contatoreRecintiRimanenti.repaint();
			recinto.repaint();
			player[turnoGiocatore].setLocation((int) mappaturaPosizione
					.getLocalizzazione(new Posizione("Strada", posizione))
					.getX()
					* formWidth / 1452, (int) mappaturaPosizione
					.getLocalizzazione(new Posizione("Strada", posizione))
					.getY()
					* formHeight / 1292);
			player[turnoGiocatore].setVisible(true);
			player[turnoGiocatore].removeAll();
			player[turnoGiocatore].revalidate();
			player[turnoGiocatore].repaint();
		} else {
			if (turnoGiocatore == 0) {
				posizionaPastore(new Posizione("Strada", posizione),
						turnoGiocatore, "./img/pedina1.png");
			} else if (turnoGiocatore == 1) {
				if (numeroGiocatori == 2) {
					posizionaPastore(new Posizione("Strada", posizione),
							turnoGiocatore, "./img/pedina1.png");
				} else {
					posizionaPastore(new Posizione("Strada", posizione),
							turnoGiocatore, "./img/pedina2.png");
				}
			} else if (turnoGiocatore == 2) {
				if (numeroGiocatori == 2) {
					posizionaPastore(new Posizione("Strada", posizione),
							turnoGiocatore, "./img/pedina2.png");
				} else {
					posizionaPastore(new Posizione("Strada", posizione),
							turnoGiocatore, "./img/pedina3.png");
				}
			} else if (turnoGiocatore == 3) {
				if (numeroGiocatori == 2) {
					posizionaPastore(new Posizione("Strada", posizione),
							turnoGiocatore, "./img/pedina2.png");
				} else {
					posizionaPastore(new Posizione("Strada", posizione),
							turnoGiocatore, "./img/pedina4.png");
				}
			}
		}
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
				aggiungiAgnello(capra.getPosizione());
			} else if (capra.getTipoPecora() == Costanti.TIPO_PECORA_PECORA) {
				aggiungiPecora(capra.getPosizione());
			} else {
				aggiungiMontone(capra.getPosizione());
			}
		}
	}

	/**
	 * muove il lupo
	 * 
	 * @param posizione
	 * @author Matteo Daverio
	 */
	public void muoviLupo(int posizione) {
		messaggio = new Messaggio("Il lupo si sta muovendo!");
		messaggio.setLocation(new Point(formWidth / 10, 60));
		c.add(messaggio, 0);
		repaint();
		messaggio.repaint();
		timer = new Timer(2000, new TimerTask(messaggio, c));
		timer.start();
		lupo.setLocation(
				(int) posizioniLupo.getLocalizzazione(
						new Posizione("Regione", posizione)).getX()
						* formWidth / 1452, (int) posizioniLupo
						.getLocalizzazione(new Posizione("Regione", posizione))
						.getY()
						* formHeight / 1292);
		lupo.removeAll();
		lupo.revalidate();
		lupo.repaint();
	}

	/**
	 * muove un ovino verso regioneArrivo
	 * 
	 * @param tipoPecora
	 * @param regionePartenza
	 * @param regioneArrivo
	 * @author Matteo Daverio
	 */
	public void muoviPecora(int tipoPecora, int regionePartenza,
			int regioneArrivo) {
		if (tipoPecora == Costanti.TIPO_PECORA_AGNELLO) {
			aggiungiAgnello(regioneArrivo);
			agnello[regionePartenza].setLocation(
					(int) posizioniAgnelli.getLocalizzazione(
							new Posizione("Regione", regionePartenza)).getX()
							* formWidth / 1452,
					(int) posizioniAgnelli.getLocalizzazione(
							new Posizione("Regione", regionePartenza)).getY()
							* formHeight / 1292);
			agnello[regionePartenza].revalidate();
			agnello[regionePartenza].repaint();
			decrementaContatoreAgnello(regionePartenza);
		} else if (tipoPecora == Costanti.TIPO_PECORA_MONTONE) {
			aggiungiMontone(regioneArrivo);
			montone[regionePartenza].setLocation(
					(int) posizioniMontoni.getLocalizzazione(
							new Posizione("Regione", regionePartenza)).getX()
							* formWidth / 1452,
					(int) posizioniMontoni.getLocalizzazione(
							new Posizione("Regione", regionePartenza)).getY()
							* formHeight / 1292);
			montone[regionePartenza].revalidate();
			montone[regionePartenza].repaint();
			decrementaContatoreMontone(regionePartenza);
		} else if (tipoPecora == Costanti.TIPO_PECORA_PECORA) {
			aggiungiPecora(regioneArrivo);
			pecora[regionePartenza].setLocation(
					(int) posizioniPecore.getLocalizzazione(
							new Posizione("Regione", regionePartenza)).getX()
							* formWidth / 1452,
					(int) posizioniPecore.getLocalizzazione(
							new Posizione("Regione", regionePartenza)).getY()
							* formHeight / 1292);
			pecora[regionePartenza].revalidate();
			pecora[regionePartenza].repaint();
			decrementaContatorePecora(regionePartenza);
		}
	}

	/**
	 * aggiunge un agnello nella posizione inserita
	 * 
	 * @param posizione
	 * @author Matteo Daverio
	 */
	public void aggiungiAgnello(int posizione) {
		if (agnello[posizione] == null) {
			agnello[posizione] = new Ovino("./img/agnello.png");

			agnello[posizione].setLocation((int) posizioniAgnelli
					.getLocalizzazione(new Posizione("Regione", posizione))
					.getX()
					* formWidth / 1452, (int) posizioniAgnelli
					.getLocalizzazione(new Posizione("Regione", posizione))
					.getY()
					* formHeight / 1292);
			contatoreAgnelli[posizione] = new JLabel();
			contatoreAgnelli[posizione] = Disegno
					.creaContatore(
							"1",
							15,
							10,
							(int) (agnello[posizione].getLocation().getX() + agnello[posizione]
									.getSize().getWidth() * 25 / 100),
							(int) (agnello[posizione].getLocation().getY() + agnello[posizione]
									.getSize().getHeight() * 25 / 100),
							Color.WHITE);
			contatoreAgnelli[posizione].setBorder(BorderFactory
					.createEmptyBorder(-2, 4, 0, 0));
			agnello[posizione].add(contatoreAgnelli[posizione]);
			c.add(agnello[posizione], 0);
			handleDragAgnelli(agnello[posizione]);
			agnello[posizione].validate();
			agnello[posizione].repaint();
		} else {
			incrementaContatore(contatoreAgnelli[posizione]);
			contatoreAgnelli[posizione].revalidate();
			contatoreAgnelli[posizione].repaint();
		}
	}

	/**
	 * aggiunge una pecora nella posizione inserita
	 * 
	 * @param posizione
	 * @author Matteo Daverio
	 */
	public void aggiungiPecora(int posizione) {
		if (pecora[posizione] == null) {
			pecora[posizione] = new Ovino("./img/pecora.png");

			pecora[posizione].setLocation((int) posizioniPecore
					.getLocalizzazione(new Posizione("Regione", posizione))
					.getX()
					* formWidth / 1452, (int) posizioniPecore
					.getLocalizzazione(new Posizione("Regione", posizione))
					.getY()
					* formHeight / 1292);
			contatorePecore[posizione] = new JLabel();
			contatorePecore[posizione] = Disegno
					.creaContatore("1", 15, 10, (int) (pecora[posizione]
							.getLocation().getX() + pecora[posizione].getSize()
							.getWidth() * 25 / 100), (int) (pecora[posizione]
							.getLocation().getY() + pecora[posizione].getSize()
							.getHeight() * 25 / 100), Color.WHITE);
			contatorePecore[posizione].setBorder(BorderFactory
					.createEmptyBorder(-2, 4, 0, 0));
			pecora[posizione].add(contatorePecore[posizione]);
			c.add(pecora[posizione], 0);
			handleDragPecore(pecora[posizione]);
			pecora[posizione].validate();
			pecora[posizione].repaint();
		} else {
			incrementaContatore(contatorePecore[posizione]);
			contatorePecore[posizione].revalidate();
			contatorePecore[posizione].repaint();
		}
	}

	/**
	 * aggiunge un montone nella posizione inserita
	 * 
	 * @param posizione
	 * @author Matteo Daverio
	 */
	public void aggiungiMontone(int posizione) {
		if (montone[posizione] == null) {
			montone[posizione] = new Ovino("./img/montone.png");

			montone[posizione].setLocation((int) posizioniMontoni
					.getLocalizzazione(new Posizione("Regione", posizione))
					.getX()
					* formWidth / 1452, (int) posizioniMontoni
					.getLocalizzazione(new Posizione("Regione", posizione))
					.getY()
					* formHeight / 1292);
			contatoreMontoni[posizione] = new JLabel();
			contatoreMontoni[posizione] = Disegno
					.creaContatore(
							"1",
							15,
							10,
							(int) (montone[posizione].getLocation().getX() + montone[posizione]
									.getSize().getWidth() * 25 / 100),
							(int) (montone[posizione].getLocation().getY() + montone[posizione]
									.getSize().getHeight() * 25 / 100),
							Color.WHITE);
			contatoreMontoni[posizione].setBorder(BorderFactory
					.createEmptyBorder(-2, 4, 0, 0));
			montone[posizione].add(contatoreMontoni[posizione]);
			c.add(montone[posizione], 0);
			handleDragMontoni(montone[posizione]);
			montone[posizione].validate();
			montone[posizione].repaint();
		} else {
			incrementaContatore(contatoreMontoni[posizione]);
			contatoreMontoni[posizione].revalidate();
			contatoreMontoni[posizione].repaint();
		}
	}

	/**
	 * imposta la schermata in relazione al numero dei giocatori
	 * 
	 * @param numeroGiocatori
	 * @author Matteo Daverio
	 */
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
		if (turno == 2 && numeroGiocatori == 2) {
			contatoreDenaroPlayer[1].setText(String.valueOf(denaro));
		}
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
		if (terreno.equals(TipoTerreno.GRANO)) {
			incrementaContatore(contatoreTessereGrano);
		} else if (terreno.equals(TipoTerreno.FORESTA)) {
			incrementaContatore(contatoreTessereForesta);
		} else if (terreno.equals(TipoTerreno.PRATERIA)) {
			incrementaContatore(contatoreTesserePrateria);
		} else if (terreno.equals(TipoTerreno.ROCCIA)) {
			incrementaContatore(contatoreTessereRoccia);
		} else if (terreno.equals(TipoTerreno.ACQUA)) {
			incrementaContatore(contatoreTessereAcqua);
		} else if (terreno.equals(TipoTerreno.SABBIA)) {
			incrementaContatore(contatoreTessereSabbia);
		} else {
			// errore in ricezione della tessera iniziale
			JOptionPane.showMessageDialog(null,
					"errore ricezione tessera iniziale", "Errore",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * riceve la richiesta di posizionare il pastore
	 * 
	 * @author Matteo Daverio
	 */
	public void richiestaPosizionamentoPastore() {
		richiestaPosizionamentoPastore = true;
		messaggio = new Messaggio("Posiziona il tuo pastore");
		messaggio.setLocation(new Point(formWidth / 10, 10));
		c.add(messaggio, 0);
		repaint();
		messaggio.repaint();
		timer = new Timer(2000, new TimerTask(messaggio, c));
		timer.start();

	}

	/**
	 * metodo che notifica il cambio di turno
	 * 
	 * @param giocatore
	 * @author Matteo Daverio
	 */
	public void cambioTurno(String giocatore) {
		mioTurno = false;
		messaggio = new Messaggio("Ora è il turno del giocatore " + giocatore);
		messaggio.setLocation(new Point(formWidth / 10, 110));
		c.add(messaggio, 0);
		repaint();
		messaggio.repaint();
		timer = new Timer(2000, new TimerTask(messaggio, c));
		timer.start();
		// resetta il valore delle variabili di stato dei pulsanti
		aggiornaVariabili(0);
	}

	/**
	 * metodo che avvisa l'inizio della fase finale
	 * 
	 * @author Matteo Daverio
	 */
	public void faseFinale() {
		messaggio = new Messaggio(
				"Il gioco entra in fase finale! Ultimo turno di gioco");
		messaggio.setLocation(new Point(formWidth / 10, 110));
		c.add(messaggio, 0);
		repaint();
		messaggio.repaint();
		timer = new Timer(2000, new TimerTask(messaggio, c));
		timer.start();
	}

	/**
	 * metodo che comunica i punteggi finali
	 * 
	 * @param punteggiFinali
	 * @param nomi
	 * @author Matteo Daverio
	 */
	public void punteggiFinali(List<Integer> punteggiFinali, List<String> nomi) {
		for (int i = 0; i < numeroGiocatori; i++) {
			try {
				this.wait(1500);
			} catch (InterruptedException e) {
				LOG.log(Level.SEVERE, "Attesa punteggi finali fallita", e);
			}
			messaggio = new Messaggio("Il giocatore " + nomi.get(i)
					+ " ha ottenuto " + punteggiFinali.get(i) + " punti!");
			messaggio.setLocation(new Point(formWidth / 10, 10 + 100 * i));
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
		}
	}

	/**
	 * metodo che aggiorna i recinti rimanenti
	 * 
	 * @param recintiUsati
	 * @author Matteo Daverio
	 */
	public void aggiornaRecinti(int recintiUsati) {
		int recintiRimanenti = Costanti.NUMERO_RECINTI_NORMALI - recintiUsati;
		if (recintiRimanenti >= 0) {
			contatoreRecintiRimanenti.setText(String.valueOf(recintiRimanenti));
			contatoreRecintiRimanenti.repaint();
		}
	}

	/**
	 * notifica che la mossa è corretta
	 * 
	 * @author Matteo Daverio
	 */
	public void mossaCorretta() {
		oggettoMosso = "";
		posizioneDiPartenza = -1;
		posizioneDiArrivo = -1;
	}

	/**
	 * avvisa che la mossa è sbagliata
	 * 
	 * @author Matteo Daverio
	 */
	public void mossaSbagliata() {
		if (oggettoMosso.equals("pecora")) {
			messaggio = new Messaggio("Movimento pecora errato");
			messaggio.setLocation(new Point(formWidth / 10, 10));
			messaggio.setForeground(Color.RED);
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			muoviPecora(Costanti.TIPO_PECORA_PECORA, posizioneDiArrivo,
					posizioneDiPartenza);
		} else if (oggettoMosso.equals("montone")) {
			messaggio = new Messaggio("Movimento montone errato");
			messaggio.setLocation(new Point(formWidth / 10, 10));
			messaggio.setForeground(Color.RED);
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			muoviPecora(Costanti.TIPO_PECORA_MONTONE, posizioneDiArrivo,
					posizioneDiPartenza);
		} else if (oggettoMosso.equals(agnello)) {
			messaggio = new Messaggio("Movimento agnello errato");
			messaggio.setLocation(new Point(formWidth / 10, 10));
			messaggio.setForeground(Color.RED);
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			muoviPecora(Costanti.TIPO_PECORA_AGNELLO, posizioneDiArrivo,
					posizioneDiPartenza);
		} else if (oggettoMosso.equals("pecoranera")) {
			messaggio = new Messaggio("Movimento pecora nera errato");
			messaggio.setLocation(new Point(formWidth / 10, 10));
			messaggio.setForeground(Color.RED);
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			pecoraNera.setLocation(
					(int) mappaturaPosizione.getLocalizzazione(
							new Posizione("Regione", posizioneDiPartenza))
							.getX()
							* formWidth / 1452,
					(int) mappaturaPosizione.getLocalizzazione(
							new Posizione("Regione", posizioneDiPartenza))
							.getY()
							* formHeight / 1292);
			pecoraNera.revalidate();
			pecoraNera.repaint();
		} else if (oggettoMosso.equals("pedina")) {
			messaggio = new Messaggio("Movimento giocatore errato");
			messaggio.setLocation(new Point(formWidth / 10, 10));
			messaggio.setForeground(Color.RED);
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			player[posizioneDiPartenza].setLocation(
					(int) mappaturaPosizione.getLocalizzazione(
							new Posizione("Strada", posizioneAttuale
									.getPosizione())).getX()
							* formWidth / 1452,
					(int) mappaturaPosizione.getLocalizzazione(
							new Posizione("Strada", posizioneAttuale
									.getPosizione())).getY()
							* formHeight / 1292);
			player[posizioneDiPartenza].revalidate();
			player[posizioneDiPartenza].repaint();
			incrementaContatore(contatoreRecintiRimanenti);
			recintoDaTogliere.setVisible(false);
		} else if (oggettoMosso.equals("acquistotessera")) {
			messaggio = new Messaggio("Acquisto errato");
			messaggio.setLocation(new Point(formWidth / 10, 10));
			messaggio.setForeground(Color.RED);
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			switch (posizioneDiPartenza) {
			case 3:
				decrementaContatore(contatoreTessereGrano);
				decrementaContatore(costoGrano);
				break;
			case 6:
				decrementaContatore(contatoreTessereSabbia);
				decrementaContatore(costoSabbia);
				break;
			case 2:
				decrementaContatore(contatoreTessereForesta);
				decrementaContatore(costoForesta);
				break;
			case 1:
				decrementaContatore(contatoreTessereAcqua);
				decrementaContatore(costoAcqua);
				break;
			case 4:
				decrementaContatore(contatoreTesserePrateria);
				decrementaContatore(costoPrateria);
				break;
			case 5:
				decrementaContatore(contatoreTessereRoccia);
				decrementaContatore(costoRoccia);
				break;
			default:
				break;
			}
		} else if (oggettoMosso.equals("accoppia")) {
			messaggio = new Messaggio("Accoppiamento errato");
			messaggio.setLocation(new Point(formWidth / 10, 10));
			messaggio.setForeground(Color.RED);
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			decrementaContatoreAgnello(posizioneDiPartenza);
		} else if (oggettoMosso.equals("abbatti")) {
			messaggio = new Messaggio("Abbattimento errato");
			messaggio.setLocation(new Point(formWidth / 10, 10));
			messaggio.setForeground(Color.RED);
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			// posizioneDiPartenza - tipo di pecora
			// posizioneDiArrivo - regione su cui è effettuato l'abbattimento
			if (posizioneDiPartenza == Costanti.TIPO_PECORA_AGNELLO) {
				aggiungiAgnello(posizioneDiArrivo);
			} else if (posizioneDiPartenza == Costanti.TIPO_PECORA_MONTONE) {
				aggiungiMontone(posizioneDiArrivo);
			} else if (posizioneDiPartenza == Costanti.TIPO_PECORA_PECORA) {
				aggiungiPecora(posizioneDiArrivo);
			}

		}
		oggettoMosso = "";
		posizioneDiPartenza = -1;
		posizioneDiArrivo = -1;
	}

	/**
	 * notifica il posizionamento corretto
	 * 
	 * @author Matteo Daverio
	 */
	public void posizionamentoCorretto() {
		oggettoMosso = "";
		posizioneDiPartenza = -1;
		posizioneDiArrivo = -1;
	}

	/**
	 * notifica il posizionamento errato
	 * 
	 * @author Matteo Daverio
	 */
	public void posizionamentoSbagliato() {
		messaggio = new Messaggio("Posizionamento errato");
		messaggio.setLocation(new Point(formWidth / 10, 10));
		messaggio.setForeground(Color.RED);
		c.add(messaggio, 0);
		repaint();
		messaggio.repaint();
		timer = new Timer(2000, new TimerTask(messaggio, c));
		timer.start();
		if (oggettoMosso.equals("posizionamento")) {
			player[posizioneDiPartenza].setVisible(false);
		}
		oggettoMosso = "";
		posizioneDiPartenza = -1;
		posizioneDiArrivo = -1;
	}

	/**
	 * notifica l'acquisto di una tessera
	 * 
	 * @param giocatore
	 * @param terreno
	 * @author Matteo Daverio
	 */
	public void notificaAcquistoTessera(String giocatore, TipoTerreno terreno) {
		messaggio = new Messaggio("Il giocatore " + giocatore
				+ " ha acquistato una tessera " + terreno);
		messaggio.setLocation(new Point(formWidth / 10, 10));
		c.add(messaggio, 0);
		repaint();
		messaggio.repaint();
		timer = new Timer(2000, new TimerTask(messaggio, c));
		timer.start();
		switch (terreno) {
		case ACQUA:
			incrementaContatore(costoAcqua);
			break;
		case FORESTA:
			incrementaContatore(costoForesta);
			break;
		case PRATERIA:
			incrementaContatore(costoPrateria);
			break;
		case SABBIA:
			incrementaContatore(costoSabbia);
			break;
		case GRANO:
			incrementaContatore(costoGrano);
			break;
		case ROCCIA:
			incrementaContatore(costoRoccia);
			break;
		default:
			break;

		}
	}

	/**
	 * notifica l'abbattimento di una pecora
	 * 
	 * @param regione
	 * @param giocatore
	 * @param tipoPecora
	 * @author Matteo Daverio
	 */
	public void abbattiPecora(int regione, String giocatore, int tipoPecora) {
		if (tipoPecora == Costanti.TIPO_PECORA_AGNELLO) {
			messaggio = new Messaggio("Agnello abbattuto nella regione "
					+ regione);
			messaggio.setLocation(new Point(formWidth / 10, 10));
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			decrementaContatoreAgnello(regione);
		} else if (tipoPecora == Costanti.TIPO_PECORA_PECORA) {
			messaggio = new Messaggio("Pecora abbattuta nella regione "
					+ regione);
			messaggio.setLocation(new Point(formWidth / 10, 10));
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			decrementaContatorePecora(regione);
		} else if (tipoPecora == Costanti.TIPO_PECORA_MONTONE) {
			messaggio = new Messaggio("Montone abbattuto nella regione "
					+ regione);
			messaggio.setLocation(new Point(formWidth / 10, 10));
			c.add(messaggio, 0);
			repaint();
			messaggio.repaint();
			timer = new Timer(2000, new TimerTask(messaggio, c));
			timer.start();
			decrementaContatoreMontone(regione);
		}
	}

	/**
	 * colloca i pastori avversari
	 * 
	 * @param posizione
	 * @param turno
	 * @author Matteo Daverio
	 */
	public void collocaPastoreAvversario(int posizione, int turno) {
		if (turno == 1) {
			posizionaPastore(new Posizione("Strada", posizione), 0,
					"./img/pedina1.png");
		} else if (turno == 2) {
			if (numeroGiocatori == 2) {
				posizionaPastore(new Posizione("Strada", posizione), 1,
						"./img/pedina1.png");
			} else {
				posizionaPastore(new Posizione("Strada", posizione), 1,
						"./img/pedina2.png");
			}
		} else if (turno == 3) {
			if (numeroGiocatori == 2) {
				posizionaPastore(new Posizione("Strada", posizione), 2,
						"./img/pedina2.png");
			} else {
				posizionaPastore(new Posizione("Strada", posizione), 2,
						"./img/pedina3.png");
			}
		} else if (turno == 4) {
			if (numeroGiocatori == 2) {
				posizionaPastore(new Posizione("Strada", posizione), 3,
						"./img/pedina2.png");
			} else {
				posizionaPastore(new Posizione("Strada", posizione), 3,
						"./img/pedina4.png");
			}
		}
	}

	/**
	 * segnala disconnessione
	 * 
	 * @author Matteo Daverio
	 */
	public void segnalaDisconnessione() {
		JOptionPane.showMessageDialog(null, "Connessione con il server caduta",
				"Disconnessione", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * passa parametro della guiimpl
	 * 
	 * @param guiImpl
	 * @author Matteo Daverio
	 */
	public void impostaGuiImpl(GuiImpl guiImpl) {
		this.guiImpl = guiImpl;
	}

	/**
	 * notifica spostamento pecora nera
	 * 
	 * @param giocatore
	 * @param posizione
	 * @author Matteo Daverio
	 */
	public void spostamentoPecoraNera(String giocatore, int posizione) {
		messaggio = new Messaggio("Il giocatore " + giocatore
				+ " ha spostato la pecora nera in " + posizione);
		messaggio.setLocation(new Point(20, 0));
		this.add(messaggio, 0);
		timer = new Timer(3000, new TimerTask(messaggio, this));
		timer.start();
		pecoraNera.setLocation(
				(int) mappaturaPosizione.getLocalizzazione(
						new Posizione("Regione", posizione)).getX()
						* formWidth / 1452, (int) mappaturaPosizione
						.getLocalizzazione(new Posizione("Regione", posizione))
						.getY()
						* formHeight / 1292);
		pecoraNera.revalidate();
		pecoraNera.repaint();
	}

	/**
	 * classe timerTask
	 * 
	 * @author Matteo
	 * 
	 */
	class TimerTask implements ActionListener {

		private Messaggio messaggio;
		private Container c;

		/**
		 * costruttore
		 * 
		 * @param m
		 * @param c
		 * @author Matteo Daverio
		 */
		public TimerTask(Messaggio m, Container c) {
			this.messaggio = m;
			this.c = c;
		}

		/**
		 * comportamento allo scadere del timer
		 * 
		 * @author Matteo Daverio
		 */
		public void actionPerformed(ActionEvent arg0) {
			c.remove(messaggio);
			c.repaint();
		}

	}

	/**
	 * metodo che elimina tutte le pecore
	 * 
	 * @author Matteo Daverio
	 */
	public void azzeraPecore() {
		for (int i = 0; i <= 18; i++) {
			if (pecora[i] != null) {
				c.remove(pecora[i]);
				pecora[i] = null;
			}
			if (agnello[i] != null) {
				c.remove(agnello[i]);
				agnello[i] = null;
			}
			if (montone[i] != null) {
				c.remove(montone[i]);
				montone[i] = null;
			}
		}
		repaint();
	}

	/**
	 * informa della disconnessione di un client
	 * 
	 * @param nome
	 * @author Matteo Daverio
	 */
	public void disconnessione(String nome) {
		messaggio = new Messaggio("Il giocatore " + nome + " si è disconnesso");
		messaggio.setLocation(new Point(20, 0));
		this.add(messaggio, 0);
		timer = new Timer(3000, new TimerTask(messaggio, this));
		timer.start();
	}

	/**
	 * informa della riconnessione di un client
	 * 
	 * @param nome
	 * @author Matteo Daverio
	 */
	public void riconnessione(String nome) {
		messaggio = new Messaggio("Il giocatore " + nome + " si è riconnesso");
		messaggio.setLocation(new Point(20, 0));
		this.add(messaggio, 0);
		timer = new Timer(3000, new TimerTask(messaggio, this));
		timer.start();
	}

	/**
	 * informa dell'esclusione di un client
	 * 
	 * @param nome
	 * @author Matteo Daverio
	 */
	public void esclusione(String nome) {
		messaggio = new Messaggio("Il giocatore " + nome
				+ " è stato escluso dalla partita");
		messaggio.setLocation(new Point(20, 0));
		this.add(messaggio, 0);
		timer = new Timer(3000, new TimerTask(messaggio, this));
		timer.start();
	}
	
	/**
	 * passo al client la lista di mosse disponibili
	 * 
	 * @param listaMosse
	 * @author Matteo Daverio
	 */
	public void richiestaMosse(List<MosseEnum> listaMosse){
		mosseDisponibili=listaMosse;
	}
}
