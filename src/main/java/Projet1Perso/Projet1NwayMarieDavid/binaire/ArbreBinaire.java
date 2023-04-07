package Projet1Perso.Projet1NwayMarieDavid.binaire;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import Projet1Perso.Projet1NwayMarieDavid.binaire.NoeudBinaire;
import Projet1Perso.Projet1NwayMarieDavid.binaire.StagiaireBinaire;

public class ArbreBinaire {
	private NoeudBinaire racine;
	private RandomAccessFile raf;

	public ArbreBinaire(NoeudBinaire racine, RandomAccessFile raf) {
		super();
		this.racine = new NoeudBinaire(null);
		try {
			this.raf = new RandomAccessFile("Stagiaires.DON", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArbreBinaire() {
		this.racine = new NoeudBinaire(null);
		try {
			raf = new RandomAccessFile("Stagiaires.bin", "rw");
		} catch (FileNotFoundException e) {
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
	
	
// need ajouterStagiaire on NoeudBinaire object that you want to add to the tree
//  adding the noeudStagiaire object directly to the root node is not how bts works
	public void ajouterNoeud(StagiaireBinaire noeudStagiaire) {
		// base case : s'il n'y a pas de racine
		try {
			if (raf.length() == 0) {
				racine = new NoeudBinaire(noeudStagiaire);
				raf.seek(0);
				racine.ecrireNoeud(racine, raf);
			}else {
				// lire le premier noeud du fichier binaire et le stocker dans la racine
				raf.seek(0);
				racine = racine.lireNoeud(raf);

				racine.ajouterNoeud(new NoeudBinaire(noeudStagiaire), raf);
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	// return a list
	public List<StagiaireBinaire> affichageInfixeNoeud() {
	    List<StagiaireBinaire> cleList = new ArrayList<>();
	    try {
	        if (raf.length() == 0) {
	            System.out.println("L'arbre est vide.");
	        } else {
	            raf.seek(0);
	            racine = racine.lireNoeud(raf);
	            racine.affichageInfixeNoeudB(raf, cleList);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return cleList;
	}

	  //MODIFICATION AVEC METHODE RECHERCHE AVANCEE DEBUT 
	
	
	//Recherche lancée depuis la classe arbre. Args list qui contiendra les resultats
    // correspondant : stagiaire recherché, raf pour lire le fichier
	public List<StagiaireBinaire> rechercheStagiaire(String nom) {
		List<StagiaireBinaire> listeResultats = new ArrayList<>();
		StagiaireBinaire stagiaireRecherche = new StagiaireBinaire(nom,"","","","");

		try {
			if  (raf.length() == 0) {
				System.out.println("l'arbre est vide");
		}else {
			raf.seek(0);
			racine = racine.lireNoeud(raf);
			racine.rechercheNoeud(listeResultats, stagiaireRecherche, raf);
		}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listeResultats;
	}

    //Recherche lancée depuis la classe arbre. Args list qui contiendra les resultats correspondant : stagiaire recherché, raf pour lire le fichier
    public void rechercheAvancee(List<StagiaireBinaire> rechercheAvancee ,StagiaireBinaire stagiaireToFind) throws IOException {
        List<StagiaireBinaire> ordreAlpha = new ArrayList<>();

        if  (raf.length() == 0) {
			System.out.println("l'arbre est vide");
	}else {
		raf.seek(0);
		racine = racine.lireNoeud(raf);
            NoeudBinaire premierNoeud = lireNoeud(raf);
            premierNoeud.affichageInfixeNoeudB(raf, ordreAlpha);
            for(StagiaireBinaire stagiaire : ordreAlpha) {
                stagiaire.rechercheAvancee(rechercheAvancee, stagiaireToFind);
            }
            //retour visuel, fichier vide
            System.out.println("Aucun stagiaire ne correspond aux critères.");
        }
    }
    //MODIFICATION AVEC METHODE RECHERCHE AVANCEE FIN
	
	public NoeudBinaire getRacine() {
		return racine;
	}

	public void setRacine(NoeudBinaire racine) {
		this.racine = racine;
	}

	public RandomAccessFile getRaf() {
		return raf;
	}

	public void setRaf(RandomAccessFile raf) {
		this.raf = raf;
	}
}
