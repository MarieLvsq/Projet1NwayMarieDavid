package Projet1Perso.Projet1NwayMarieDavid.binaire;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NoeudBinaire {
	public final static int TAILLE_MAX_NOEUD_OCTET = StagiaireBinaire.TAILLE_STAGIAIRE_OCTET + 3 * 4;

//	private List<StagiaireBinaire> cleList;
	private StagiaireBinaire cle;
	private StagiaireBinaire cleAjout;
	private int filsGauche;
	private int filsDroit;
	private int filsDoublon;

	// this constructor takes a Stagiaire object as its parameter
	// and initializes the cle field of the new Noeud object

	public NoeudBinaire(StagiaireBinaire cle) {
		this.filsDoublon = filsDoublon;
		this.cle = cle;
		this.filsGauche = -1;
		this.filsDroit = -1;

	}

	// the second constructor allows you to create a Noeud object
	public NoeudBinaire(StagiaireBinaire cle, int filsGauche, int filsDroit, int filsDoublon) {
		this.cle = cle;
		this.filsDroit = filsDroit;
		this.filsGauche = filsGauche;
		this.filsDoublon = filsDoublon;
	}

//	RandomAccessFile raf = new RandomAccessFile("src/fichiers/Stagiaires.bin", "rw");
	public void ecrireNoeud(NoeudBinaire cleAjout, RandomAccessFile raf) throws IOException {

		try {
			raf.writeChars(cleAjout.getNomLong());
			raf.writeChars(cleAjout.getPrenomLong());
			raf.writeChars(cleAjout.getDepartementLong());
			raf.writeChars(cleAjout.getPromoLong());
			raf.writeChars(cleAjout.getAnneeLong());
			raf.writeInt(-1);
			raf.writeInt(-1);
			raf.writeInt(-1);

		} catch (IOException e) {
			System.err.println("Erreur en ecrivant: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public NoeudBinaire lireNoeud(RandomAccessFile raf) throws IOException {
		String nom = "";
		String prenom = "";
		String departement = "";
		String promo = "";
		String annee = "";
		int filsGauche = -1;
		int filsDroit = -1;
		int filsDoublon = -1;
		StagiaireBinaire stagiaire = null;
		try {
			for (int j = 0; j < StagiaireBinaire.TAILLE_NOM; j++) {
				nom += raf.readChar();
			}
			for (int j = 0; j < StagiaireBinaire.TAILLE_PRENOM; j++) {
				prenom += raf.readChar();
			}
			for (int j = 0; j < StagiaireBinaire.TAILLE_DEPARTEMENT; j++) {
				departement += raf.readChar();
			}
			for (int j = 0; j < StagiaireBinaire.TAILLE_PROMO; j++) {
				promo += raf.readChar();
			}
			for (int j = 0; j < StagiaireBinaire.TAILLE_ANNEE; j++) {
				annee += raf.readChar();
			}
			filsGauche = raf.readInt();
			filsDroit = raf.readInt();
			filsDoublon = raf.readInt();

			stagiaire = new StagiaireBinaire(nom.trim(), prenom.trim(), departement.trim(), promo.trim(), annee.trim());
		} catch (IOException e) {
			System.err.println("Erreur en lisant: " + e.getMessage());
			e.printStackTrace();
		}

		return new NoeudBinaire(new StagiaireBinaire(nom, prenom, departement, promo, annee), filsGauche, filsDroit,
				filsDoublon);
	}

// ajouter Noeud 

	public void ajouterNoeud(NoeudBinaire noeudStagiaire, RandomAccessFile raf) {
		// NoeudBinaire currentNode = this;
		// NoeudBinaire cleAjout = null;
		try {
			// while (currentNode != null) {
			int comparaison = this.cle.getNom().compareTo(noeudStagiaire.cle.getNom());

			if (comparaison > 0) {
				if (this.filsGauche == -1) { // cas de terminaison
					this.filsGauche = (int) (raf.length() / TAILLE_MAX_NOEUD_OCTET);
					raf.seek(raf.getFilePointer() - 12);// on remonte d'un fils gauche
					raf.writeInt(this.filsGauche); // on donne la valeur au fils gauche
					raf.seek(raf.length());

					ecrireNoeud(noeudStagiaire, raf);

				} else {
					raf.seek(this.filsGauche * TAILLE_MAX_NOEUD_OCTET);
					NoeudBinaire filsGauche = lireNoeud(raf);
					filsGauche.ajouterNoeud(noeudStagiaire, raf);

				}
			} else if (comparaison < 0) {
				if (this.filsDroit == -1) {
					this.filsDroit = (int) (raf.length() / TAILLE_MAX_NOEUD_OCTET);
					raf.seek(raf.getFilePointer() - 8); // ici on remonte de 4 octets seulement
					raf.writeInt(this.filsDroit); // on donne la valeur au fils droit
					raf.seek(raf.length());
					ecrireNoeud(noeudStagiaire, raf);
				} else {
					raf.seek(this.filsDroit * TAILLE_MAX_NOEUD_OCTET);
					NoeudBinaire filsDroit = lireNoeud(raf);
					filsDroit.ajouterNoeud(noeudStagiaire, raf);
				}
			} else if (comparaison == 0) {
				if (this.filsDoublon == -1) {
					this.filsDoublon = (int) (raf.length() / TAILLE_MAX_NOEUD_OCTET);
					raf.seek(raf.getFilePointer() - 4); // ici on remonte de 12 octets
					raf.writeInt(this.filsDoublon); // on donne la valeur au fils doublon
					raf.seek(raf.length());
					ecrireNoeud(noeudStagiaire, raf);
				} else {
					raf.seek(this.filsDoublon * TAILLE_MAX_NOEUD_OCTET);
					NoeudBinaire doublon = lireNoeud(raf);
					doublon.ajouterNoeud(noeudStagiaire, raf);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void affichageInfixeNoeudB(RandomAccessFile raf, List<StagiaireBinaire> cleList) {
		try {
			if (this.filsGauche != -1) {
				raf.seek(this.filsGauche * TAILLE_MAX_NOEUD_OCTET);
				NoeudBinaire filsGauche = lireNoeud(raf);
				filsGauche.affichageInfixeNoeudB(raf, cleList);
			}

			// if (!cleList.contains(this.cle)) {
			// System.out.println(this.cle.toString());
			cleList.add(this.cle);
			// }
			if (this.filsDoublon != -1) {
				raf.seek(this.filsDoublon * TAILLE_MAX_NOEUD_OCTET);
				NoeudBinaire filsDoublon = lireNoeud(raf);
				filsDoublon.affichageInfixeNoeudB(raf, cleList);
			}

			if (this.filsDroit != -1) {
				raf.seek(this.filsDroit * TAILLE_MAX_NOEUD_OCTET);
				NoeudBinaire filsDroit = lireNoeud(raf);
				filsDroit.affichageInfixeNoeudB(raf, cleList);
			}

		} catch (IOException e) {
			System.err.println("Erreur en lisant: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void rechercheNoeud(List<StagiaireBinaire> listeResultats, StagiaireBinaire stagiaireRecherche,
			RandomAccessFile raf) throws IOException {
		// Check if the current node's key matches the search key
		if (stagiaireRecherche.getNom().replace("*","").compareToIgnoreCase(this.cle.getNom().replace("*","")) == 0) {
			// Add the current node's value to the result list
			listeResultats.add(this.cle);
			// If there is a duplicate node on the right, continue searching there
			if (this.filsDoublon != -1) {
				raf.seek(this.filsDoublon * TAILLE_MAX_NOEUD_OCTET);
				NoeudBinaire noeudSuivant = lireNoeud(raf);
				noeudSuivant.rechercheNoeud(listeResultats, stagiaireRecherche, raf);
			}
			// If the search key is less than the current node's key, continue searching in
			// the left subtree
		} else if (stagiaireRecherche.getNom().replace("*","").compareToIgnoreCase(this.cle.getNom()) < 0) {
			// If there is no left child
			if (this.filsGauche == -1) {
				// Otherwise, continue searching in the left subtree
			} else {
				raf.seek((long) this.filsGauche * TAILLE_MAX_NOEUD_OCTET);
				NoeudBinaire noeudFilsGauche = lireNoeud(raf);
				noeudFilsGauche.rechercheNoeud(listeResultats, stagiaireRecherche, raf);
			}
			// If the search key is greater than the current node's key, continue searching
			// in the right
		} else {
			// If there is no right child, the search is unsuccessful
			if (this.filsDroit == -1) { // DROIT
				// Otherwise, continue searching in the right subtree
			} else {

				raf.seek((long) this.filsDroit * TAILLE_MAX_NOEUD_OCTET);
				NoeudBinaire noeudFilsDroit = lireNoeud(raf);
				noeudFilsDroit.rechercheNoeud(listeResultats, stagiaireRecherche, raf);
			}
		}

	}

	public void supprimerNoeud(StagiaireBinaire cle) {
		if (this.cle == null) {
			return;
		}
	}

	public String getNomLong() {
		String nomLong = this.cle.getNom();
		for (int i = this.cle.getNom().length(); i < StagiaireBinaire.TAILLE_NOM; i++) {
			nomLong += "*";
		}
		return nomLong;
	}

	public String getPrenomLong() {
		String prenomLong = this.cle.getPrenom();
		for (int i = this.cle.getPrenom().length(); i < StagiaireBinaire.TAILLE_PRENOM; i++) {
			prenomLong += "*";
		}
		return prenomLong;
	}

	public String getDepartementLong() {
		String departementLong = this.cle.getDepartement();
		for (int i = this.cle.getDepartement().length(); i < StagiaireBinaire.TAILLE_DEPARTEMENT; i++) {
			departementLong += "*";
		}
		return departementLong;
	}

	public String getPromoLong() {
		String promoLong = this.cle.getPromo();
		for (int i = this.cle.getPromo().length(); i < StagiaireBinaire.TAILLE_PROMO; i++) {
			promoLong += "*";
		}
		return promoLong;
	}

	public String getAnneeLong() {
		String anneeLong = this.cle.getAnnee();
		for (int i = this.cle.getAnnee().length(); i < StagiaireBinaire.TAILLE_ANNEE; i++) {
			anneeLong += "*";
		}
		return anneeLong;
	}

	public StagiaireBinaire getCle() {
		return cle;
	}

	public void setCle(StagiaireBinaire cle) {
		this.cle = cle;
	}

	public int getFilsGauche() {
		return filsGauche;
	}

	public void setFilsGauche(int filsGauche) {
		this.filsGauche = filsGauche;
	}

	public int getFilsDroit() {
		return filsDroit;
	}

	public void setFilsDroit(int filsDroit) {
		this.filsDroit = filsDroit;
	}

	@Override
	public String toString() {
		return cle.toString() + filsGauche + " " + filsDroit;
	}

}
