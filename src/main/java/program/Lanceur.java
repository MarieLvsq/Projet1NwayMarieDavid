package program;
//package program;
//
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.util.List;
//import java.io.BufferedOutputStream;
//import java.util.ArrayList;
//import binaire.ArbreBinaire;
//import binaire.NoeudBinaire;
//import binaire.StagiaireBinaire;
//
//public class Lanceur {
//
//	public static void main(String[] args) {
//
//		StagiaireBinaire newstagiaire = new StagiaireBinaire("Nom", "Prenom", "DE", "PROMO", "ANNEE");
////		newstagiaire.setNom("John");
////		newstagiaire.setPrenom("Wane");
////		newstagiaire.setDepartement("91");
////		newstagiaire.setPromo("AI343");
////		newstagiaire.setAnnee("2021");
//
//		// POUR effacer Stagiaires.bin
////		 try {
////			 StagiaireBinaire.effacerFichier("src/main/resources/Stagiaires.bin");
////		    } catch (IOException e) {
////		        System.out.println("An error occurred while clearing the file: " + e.getMessage());
////		    }
////
////	}}
//		// POUR tester stagiaires
//		List<StagiaireBinaire> stagiaires = newstagiaire.readStagiairesFromResource("STAGIAIRES.DON");
//		System.out.println("taille liste noeud " + stagiaires.size());
//
//		// pour tester stagiaire list
//		// for (StagiaireBinaire stagiaire : stagiaires) {
//		// System.out.println(stagiaire.toString());
//
//		// POUR tester noeud list
////		List<NoeudBinaire> noeuds = stagiaireBinaire.readNoeudsFromResource("STAGIAIRES.DON");
////		for (NoeudBinaire noeud : noeuds) {
////			System.out.println(noeud.getCle().toString());
////
//	}
//
////		
//	// Pour tester affichageInfixeNoeud
//	/// Pour tester affichageInfixeNoeud avec la liste
////	String filename = "Stagiaires.bin";
////	ArbreBinaire arbre = new ArbreBinaire();
////	arbre.affichageInfixeNoeud(cleList);
////		
//
//	// for(StagiaireBinaire cle:cleList){
//	//	System.out.println(cle.toString());
////	}
//		// Use this for front
//		// for(StagiaireBinaire stag : stagiaires) {
//		// arbre.ajouterNoeud(stag);
//	//}
//	// Pour eccrire et Lire
////		try {
////			int nbStagiaire = (int) (arbre.getRaf().length() / NoeudBinaire.TAILLE_MAX_NOEUD_OCTET);
////			arbre.getRaf().seek(0);
////			for(int i = 0; i < nbStagiaire; i++) {
////			//	System.out.println(arbre.getRacine().lireNoeud(arbre.getRaf()));
////				
////			}
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////
////		
//	// }
//	// }
//// NoeudBinaire noeud = new NoeudBinaire(newstagiaire);
//
////		try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
////
////		    //long lastObjectPointer = raf.length();
//////		    if (lastObjectPointer >= NoeudBinaire.TAILLE_MAX_NOEUD_OCTET) {
//////		        raf.seek(lastObjectPointer - NoeudBinaire.TAILLE_MAX_NOEUD_OCTET);
//////		        lastObjectPointer = raf.readLong();
//////		    }
////
////		   // raf.seek(lastObjectPointer);
//////		    noeud.ecrireNoeud(noeud, raf);
//////		    System.out.println("Noeud ajouté avec succès!");
//////
//////		    raf.seek(0);
//////		    System.out.println(noeud.lireNoeud(raf));
////		    //raf.writeLong(lastObjectPointer);
////		    
////		} catch (IOException e) {
////		    System.err.println("Error opening/writing/closing file, you are screwed: " + e.getMessage());
////		    e.printStackTrace();
//}