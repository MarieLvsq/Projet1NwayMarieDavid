package Projet1Perso.Projet1NwayMarieDavid.program;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import Projet1Perso.Projet1NwayMarieDavid.binaire.ArbreBinaire;
import Projet1Perso.Projet1NwayMarieDavid.binaire.NoeudBinaire;
import Projet1Perso.Projet1NwayMarieDavid.binaire.StagiaireBinaire;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) throws IOException {

		// StagiaireBinaire newstagiaire = new StagiaireBinaire("Nom", "Prenom", "DE",
		// "PROMO", "ANNEE");
//		newstagiaire.setNom("John");
//		newstagiaire.setPrenom("Wane");
//		newstagiaire.setDepartement("91");
//		newstagiaire.setPromo("AI343");
//		newstagiaire.setAnnee("2021");
		List<StagiaireBinaire> stagiaires = StagiaireBinaire.readStagiairesFromResource("STAGIAIRES.DON");
////		System.out.println("taille liste noeud " + stagiaires.size());
//
//	//NoeudBinaire nb = new NoeudBinaire(newstagiaire);
		String filename = "Stagiaires.bin";
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(filename, "rw");
		  ArbreBinaire arbre = new ArbreBinaire();
//		    for (StagiaireBinaire stagiaire : stagiaires) {
//		        arbre.ajouterNoeud(stagiaire);
//		    }
		    
		    List<StagiaireBinaire> cleList = arbre.affichageInfixeNoeud();
		    for (StagiaireBinaire cle : cleList) {
		        System.out.println(cle);
		    }
		    
			
			List<StagiaireBinaire> resultat = arbre.rechercheStagiaire("CHAVENEAU");
			if (resultat.isEmpty()) {
				System.out.println("Le nom n'existe pas.");
			} else {
				for (StagiaireBinaire s : resultat) {
					if (s.getNom().replace("*", "").equals("CHAVENEAU")) {
						if (s.getNom().replace("*", "").equals("CHAVENEAU")) {
							System.out.println("Stagiaire trouvé: ");
							System.out.println("Nom: " + s.getNom().replace("*", ""));
							System.out.println("Prénom: " + s.getPrenom().replace("*", ""));
							System.out.println("Département: " + s.getDepartement().replace("*", ""));
							System.out.println("Promo : " + s.getPromo().replace("*", ""));
							System.out.println("Annee : " + s.getAnnee().replace("*", ""));
						}

					}
				}
			}
			raf.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	}
	
}


//			//
//			long lastObjectPointer = raf.length();
//			if (lastObjectPointer >= NoeudBinaire.TAILLE_MAX_NOEUD_OCTET) {
//				raf.seek(lastObjectPointer - NoeudBinaire.TAILLE_MAX_NOEUD_OCTET);
//				lastObjectPointer = raf.readLong();
//			}
//
//			raf.seek(lastObjectPointer);
//			nb.ecrireNoeud(nb, raf);
//			System.out.println("Noeud ajouté avec succès!");
//
//			
//			// raf.writeLong(lastObjectPointer);
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
// NoeudBinaire root = new NoeudBinaire( new StagiaireBinaire("LENON", "Doe", "91", "CDA22","2000"));
//    root.ecrireNoeud(root, raf);
//    NoeudBinaire node = new NoeudBinaire( new StagiaireBinaire("ALAN", "Pierre", "91", "CDA22","2000"));
//    root.ecrireNoeud(root, raf);
//    
//    NoeudBinaire node2 = new NoeudBinaire( new StagiaireBinaire("MATINEZ", "Javier", "91", "CDA22","2000"));
//    root.ecrireNoeud(root, raf);
//    root.ajouterNoeud(node2, raf);
//    root.lireNoeud(raf);
//} catch (IOException e) {
//	System.out.println("rien marche");
//    e.printStackTrace();
//}
