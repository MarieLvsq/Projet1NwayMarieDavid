package program;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import binaire.ArbreBinaire;
import binaire.NoeudBinaire;
import binaire.StagiaireBinaire;

public class main {

	public static void main(String[] args) {

		//Méthode pour SUPPRIMER le fichier - permet d'effacer toute liste qui pourrait préexister sur le fichier .bin
		try {
			StagiaireBinaire.effacerFichier("Stagiaires.bin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Création du fichier .bin
		String filename = "Stagiaires.bin";

		//Lecture et écriture du fichier Stagiaires.bin ("rw")
		try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
			
			//Création d'un arbre binaire nommé "arbre"
			ArbreBinaire arbre = new ArbreBinaire();
			
			//Création d'une liste de stagiaires de la classe StagiaireBinaire issue de la liste du document STAGIAIRES.DON
			List<StagiaireBinaire> stagiaires = StagiaireBinaire.readStagiairesFromResource("STAGIAIRES.DON");

			//Ajout de tous les noms de la liste dans l'arbre binaire via la méthode ajouterStagiaire
			for (StagiaireBinaire stagiaire : stagiaires) {
				arbre.ajouterStagiaire(stagiaire);
			}
			
			//Affichage infixe de l'arbre (permet un affichage des stagiaires par ordre alphabétique)
			List<StagiaireBinaire> cleList = arbre.affichageInfixeNoeud();
		    for (StagiaireBinaire cle : cleList) {
		        System.out.println(cle); // On imprime les infos de chaque stagiaire
		    }
		    //FIN DE L'AFFICHAGE INFIXE
		    
		    //Méthode de recherche d'un stagiaire par son nom de famille - Affiche également les doublons
		    arbre.rechercheStagiaire("NGUYEN");
		    
		    // Si le nom n'est pas présent dans la liste :
		    if (cleList.isEmpty()) {
		    System.out.println("Le nom n'existe pas.");
		    
		    //Si le nom est présent dans la liste :
		    } else {
		    	for (StagiaireBinaire s : cleList) {
		    		//On supprime les " " (chaque variable ayant une taille maximum unique définie en bites.)
		    		// pour ne garder que les charactères du Nom du stagiaire
		    		if (s.getNom().replace(" ", "").equals("NGUYEN")) {
		    		if (s.getNom().replace(" ", "").equals("NGUYEN")) {
		    		
		    		// On affiche les informations du stagiaire (ou des stagiaires s'il y a des doublons)
		    		// On supprime à nouveau les " ", le cas échéant
		    		System.out.println("Stagiaire trouvé: ");
		    		System.out.println("Nom: " + s.getNom().replace(" ", ""));
		    		System.out.println("Prénom: " + s.getPrenom().replace(" ", ""));
		    		System.out.println("Département: " + s.getDepartement().replace(" ", ""));
		    		System.out.println("Promo : " + s.getPromo().replace(" ", ""));
		    		System.out.println("Annee : " + s.getAnnee().replace(" ", ""));
		    						}

		    					}

		    				}
		    			}
		    //FIN DE LA METHODE DE RECHERCHE PAR NOM.
		    
		    //Méthode Recherche Avancée - cherche et affiche un stagiaire quand on appelle un seul paramètre du StagiaireBinaire
		    StagiaireBinaire recherche = new StagiaireBinaire("", "Alix", "", "", "");
		    // Call the "rechercheAvancee" method and pass in the List and search criteria
		    List<StagiaireBinaire> resultList = StagiaireBinaire.rechercheAvancee(stagiaires, recherche);
		    // Loop through the resultList and print out the matching objects
		    for (StagiaireBinaire s : resultList) {
		        System.out.println(s.getNom() + " " + s.getPrenom() + " " + s.getDepartement() + " " + s.getPromo() + " " + s.getAnnee());
		    }
		    // FIN DE LA METHODE DE RECHERCHE AVANCEE.
		    
//			arbre.supprimerStagiaire("ZOUAOUI", raf);

		} catch (EOFException e) {
			// handle the EOFException
			System.err.println("Unexpected end of file encountered: " + e.getMessage());
			e.printStackTrace();
			//raf.close();
	} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
	
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
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
