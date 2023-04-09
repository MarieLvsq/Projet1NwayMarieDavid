package binaire;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


public class ArbreBinaire {
	private NoeudBinaire racine;
	private RandomAccessFile raf;
	private int previousIndex = -1;

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

	public void ajouterStagiaire(StagiaireBinaire noeudStagiaire) {
		// base case : s'il n'y a pas de racine
		try {
			if (raf.length() == 0) {
				racine = new NoeudBinaire(noeudStagiaire);
				racine.ecrireNoeud(racine, raf);
				raf.seek(0);
			} else {
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
		// create a list
		List<StagiaireBinaire> cleList = new ArrayList<>();
		try {
			if (raf.length() == 0) {
				//System.out.println("l'arbre est vide");
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
	public void supprimerStagiaire(String nom, RandomAccessFile raf) throws IOException {
		List<StagiaireBinaire> noeudsASupprimer =  new ArrayList<>();
		NoeudBinaire previousNoeud = null;
		long previousNodeIndex = -1; // if there is no previous node,
		raf.seek(0);

		try {
			if (raf.length() == 0) {
				System.out.println("The tree is empty");
			} else {
				NoeudBinaire noeudCourant = racine.lireNoeud(raf);
				while((noeudCourant != null) || (!(noeudCourant.getCle().getNom().equals(nom)))) {
					previousNodeIndex = (previousNoeud == null) ? -1 : raf.getFilePointer() -  raf.readLong();
					previousNoeud = noeudCourant;
					if (noeudCourant.getCle().getNom().compareTo(nom)<0){
						raf.seek(noeudCourant.getFilsGauche());
						noeudCourant = noeudCourant.lireNoeud(raf);
						if (raf.getFilePointer() < raf.length()) {
							noeudCourant = noeudCourant.lireNoeud(raf);
						} else {
							System.out.println("End of file reached1.");
							break;
						}
					} else {
						raf.seek(noeudCourant.getFilsDroit());
						if (raf.getFilePointer() < raf.length()) {
							noeudCourant = noeudCourant.lireNoeud(raf);
						} else {
							System.out.println("End of file reached2.");
							break;
						}
					}
				}
				if (noeudCourant==null){
					System.out.println("Le nom n'est pas dans l'arbre");
				} else {
					System.out.println("J'ai trouvé le stagiaire à supprimer  "+ nom);
					racine.supprimerNoeud(noeudCourant, 0, raf);
					System.out.println("Le stagiaire: " +nom+ " a été supprimé avec succès.");
				}
			}
		} catch (EOFException e) {
			System.out.println("End of file reached3.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	//	public void supprimerStagiaire(String nom, RandomAccessFile raf) throws IOException {
//	//	List<NoeudBinaire> noeuds = new ArrayList<>();
//		List<StagiaireBinaire> noeudsASupprimer =  new ArrayList<>();
//		NoeudBinaire previousNoeud = null;
//		long previousNodeIndex = -1; // if there is no previous node,
//		raf.seek(0);
//
//		try {
//			if (raf.length() == 0) {
//				System.out.println("The tree is empty");
//			} else {
//				//raf.seek(0);
//				NoeudBinaire noeudCourant = racine.lireNoeud(raf);
//				while((noeudCourant != null) || (!(noeudCourant.getCle().getNom().equals(nom)))){
//					//record the id of the previous noeudCourrant
//				//readLong() read the long value stored at the current position, which represents the pointer to the previous NoeudBinaire object in the file.
//				previousNodeIndex = (previousNoeud == null) ? -1 : raf.getFilePointer() -  raf.readLong();
//					previousNoeud = noeudCourant;
//					if (noeudCourant.getCle().getNom().compareTo(nom)<0){
//						raf.seek(noeudCourant.getFilsGauche());
//						noeudCourant = noeudCourant.lireNoeud(raf);
//						if (raf.getFilePointer() < raf.length()) {
//							noeudCourant = noeudCourant.lireNoeud(raf);
//						} else {
//							System.out.println("End of file reached.");
//							break;
//					} else {
//						//complete the case where you need to go right
//							raf.seek(noeudCourant.getFilsDroit());
//							if (raf.getFilePointer() < raf.length()) {
//							noeudCourant = noeudCourant.lireNoeud(raf);
//							} else {
//								System.out.println("End of file reached.");
//								break;
//							}
//					}
//					}
//					if (noeudCourant==null){
//						System.out.println("Le nom n'est pas dans l'arbre");
//					}else{
//						System.out.println("J'ai trouvé le stagiaire à supprimer");
//						racine.supprimerNoeud(noeudCourant, 0, raf);
//					}
//				}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	// Search for the NoeudBinaire object that corresponds to the given nom
//				List<NoeudBinaire> noeuds = new ArrayList<>();
//				List<StagiaireBinaire> noeudsASupprimer =  new ArrayList<>();;
//				racine.rechercheNoeud(noeudsASupprimer, new StagiaireBinaire(nom, "", "", "", ""), raf);
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
