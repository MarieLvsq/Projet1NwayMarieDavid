package binaire;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;


public class NoeudBinaire {
    public final static int TAILLE_MAX_NOEUD_OCTET = StagiaireBinaire.TAILLE_STAGIAIRE_OCTET + 3 * 4;

    //	private List<StagiaireBinaire> cleList;
    private StagiaireBinaire cle;
    private StagiaireBinaire cleAjout;
    private int filsGauche;
    private int filsDroit;
    private int filsDoublon;
    private int index;


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

    public NoeudBinaire() {

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
            if(raf.getFilePointer() < raf.length()) {
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
            } else {
                System.out.println("End of file reached.");
            }
        } catch (EOFException e) {
            // handle the EOFException
            System.err.println("Unexpected end of file encountered: " + e.getMessage());
            e.printStackTrace();



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

                    ecrireNoeud(noeudStagiaire,raf);

                } else {
                    raf.seek((long) this.filsGauche * TAILLE_MAX_NOEUD_OCTET);
                    NoeudBinaire filsGauche = lireNoeud(raf);
                    filsGauche.ajouterNoeud(noeudStagiaire, raf);

                }
            } else if (comparaison < 0) {
                if (this.filsDroit == -1) {
                    this.filsDroit = (int) (raf.length() / TAILLE_MAX_NOEUD_OCTET);
                    raf.seek(raf.getFilePointer() - 8); // ici on remonte de 4 octets seulement
                    raf.writeInt(this.filsDroit); // on donne la valeur au fils droit
                    raf.seek(raf.length());
                    ecrireNoeud( noeudStagiaire,raf);
                } else {
                    raf.seek((long) this.filsDroit * TAILLE_MAX_NOEUD_OCTET);
                    NoeudBinaire filsDroit = lireNoeud(raf);
                    filsDroit.ajouterNoeud(noeudStagiaire, raf);
                }
            } else {
                if (this.filsDoublon == -1) {
                    this.filsDoublon = (int) (raf.length() / TAILLE_MAX_NOEUD_OCTET);
                    raf.seek(raf.getFilePointer() - 4); // ici on remonte de 12 octets
                    raf.writeInt(this.filsDoublon); // on donne la valeur au fils doublon
                    raf.seek(raf.length());
                    ecrireNoeud(noeudStagiaire,raf);
                } else {
                    raf.seek((long) this.filsDoublon * TAILLE_MAX_NOEUD_OCTET);
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
                raf.seek((long) this.filsGauche * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire filsGauche = lireNoeud(raf);
                filsGauche.affichageInfixeNoeudB(raf, cleList);
            }

            // if (!cleList.contains(this.cle)) {
            // System.out.println(this.cle.toString());
            cleList.add(this.cle);
            // }
            if (this.filsDoublon != -1) {
                raf.seek((long) this.filsDoublon * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire filsDoublon = lireNoeud(raf);
                filsDoublon.affichageInfixeNoeudB(raf, cleList);
            }

            if (this.filsDroit != -1) {
                raf.seek((long) this.filsDroit * TAILLE_MAX_NOEUD_OCTET);
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
        if (stagiaireRecherche.getNom().compareToIgnoreCase(this.cle.getNom()) == 0) {
            // Add the current node's value to the result list
            listeResultats.add(this.cle);
            // If there is a duplicate node on the right, continue searching there
            if (this.filsDoublon != -1) {
                raf.seek((long) this.filsDoublon * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire noeudSuivant = lireNoeud(raf);
                noeudSuivant.rechercheNoeud(listeResultats, stagiaireRecherche, raf);
            }
            // If the search key is less than the current node's key, continue searching in
            // the left subtree
        } else if (stagiaireRecherche.getNom().compareToIgnoreCase(this.cle.getNom()) < 0) {
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
    public void supprimerNoeud(NoeudBinaire noeudASupprimer, int indexDuParent, RandomAccessFile raf)
            throws IOException {
        int racine = 0; // index of the root node in the file
        NoeudBinaire parent = null; // index of the parent node
        int index = (int) ((raf.getFilePointer() - TAILLE_MAX_NOEUD_OCTET) / TAILLE_MAX_NOEUD_OCTET);
//------------------------Search for the node to be deleted__________________________________________
        boolean found = false; // boolean is added to avoid "found the name" being printed 5 times, but it doesnt work. i leave it for now
        if (this.cle.getNom().compareToIgnoreCase(noeudASupprimer.cle.getNom()) > 0) {
            // call supprimerNoeud on the left child
            if (this.filsGauche != -1) {
                raf.seek((long) this.filsGauche * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire noeudFilsGauche = lireNoeud(raf);
                noeudFilsGauche.supprimerNoeud(noeudASupprimer, this.filsGauche, raf);
                System.out.println("found the name on left");
                found = true;
                return;
            } else {
                System.out.println("No left child found");
                return;
            }
        } else if (this.cle.getNom().compareToIgnoreCase(noeudASupprimer.cle.getNom()) < 0) {
            // call supprimerNoeud on the right child
            if (this.filsDroit != -1) {
                raf.seek((long) this.filsDroit * TAILLE_MAX_NOEUD_OCTET);
                NoeudBinaire noeudFilsDroit = lireNoeud(raf);
                noeudFilsDroit.supprimerNoeud(noeudASupprimer, this.filsDroit, raf);
                System.out.println("found the name on right");
                found = true;
                return;
            } else {
                System.out.println("No right child found");
                return;
            }
        }

//-------------------------------Sorting ----------------------------------------------
        // if the node to be deleted is the left child of its parent (one child)
        if (this.filsGauche == noeudASupprimer.index) {
            raf.seek((long) indexDuParent * TAILLE_MAX_NOEUD_OCTET);
            // read the parent node from the file
            parent = lireNoeud(raf);
            // if the deleted node has left child, ternary operator // A ? B : C
            // If A == true -> B, If A == false -> C
            parent.filsGauche = noeudASupprimer.filsGauche != -1 ? noeudASupprimer.filsGauche
                    : noeudASupprimer.filsDroit;
        } else if (this.filsDroit == noeudASupprimer.index) {
            raf.seek((long) indexDuParent * TAILLE_MAX_NOEUD_OCTET);
            parent = lireNoeud(raf);
            parent.filsDroit = noeudASupprimer.filsGauche != -1 ? noeudASupprimer.filsGauche
                    : noeudASupprimer.filsDroit;
//---------------------------------Case 1,2,3----------------------------------------------------
            // Basic case 1: Node is a leaf node, just delete it by setting its key to null
            if (noeudASupprimer.filsGauche == -1 && noeudASupprimer.filsDroit == -1) {
                noeudASupprimer.cle = null;//node deleted

            } else {
                // Node has one or two children, find the successor node and replace with its child
                NoeudBinaire successeur = getSuccesseur(noeudASupprimer.getCle(), raf);

                // TO REPLACE, Check if the node to delete is the root node (LACROIX)
                if (indexDuParent == -1) {
                    // case 2: The node to delete is the root node
                    if (successeur == null) {
                        // No successor, just delete the root node
                        raf.setLength(0); // Empty the file
                    } else {
                        // Replace root node with its successor
                        raf.seek((long) successeur.index * TAILLE_MAX_NOEUD_OCTET);
                        NoeudBinaire noeudSuccesseur = lireNoeud(raf);
                        noeudSuccesseur.filsGauche = noeudASupprimer.filsGauche;
                        noeudSuccesseur.filsDroit = noeudASupprimer.filsDroit;
                        raf.seek(0);
                        noeudSuccesseur.ecrireNoeud(noeudASupprimer,raf);

                    }

                } else {        // case 3
                    // The node to delete is not the root node
                    raf.seek((long) indexDuParent * TAILLE_MAX_NOEUD_OCTET);
                    parent = lireNoeud(raf);
                    if (parent.filsGauche == noeudASupprimer.index) {
                        parent.filsGauche = successeur != null ? successeur.index : -1;
                    } else {
                        parent.filsDroit = successeur != null ? successeur.index : -1;
                    }
                    if (successeur != null) {
                        raf.seek((long) successeur.index * TAILLE_MAX_NOEUD_OCTET);
                        NoeudBinaire noeudSuccesseur = lireNoeud(raf);
                        noeudSuccesseur.filsGauche = noeudASupprimer.filsGauche;
                        noeudSuccesseur.filsDroit = noeudASupprimer.filsDroit;
                        raf.seek((long) successeur.index * TAILLE_MAX_NOEUD_OCTET);
                        noeudSuccesseur.ecrireNoeud(noeudASupprimer,raf);
                    }
                }
            }

        }
    }
    private NoeudBinaire getSuccesseur(StagiaireBinaire stagiaireSupprimer, RandomAccessFile raf) throws IOException {

        raf.seek((long) this.filsDroit * TAILLE_MAX_NOEUD_OCTET);
        NoeudBinaire noeudTemporaire = lireNoeud(raf);
        while (noeudTemporaire.filsGauche != 0) {
            raf.seek((long) noeudTemporaire.filsGauche * TAILLE_MAX_NOEUD_OCTET);
            noeudTemporaire = lireNoeud(raf);
        }
        return noeudTemporaire;
    }
//    private int trouverIndexDuParent(NoeudBinaire node, RandomAccessFile raf) throws IOException {
//        int indexDuParent = -1;
//        int racine;
//        if (racine = -1) {
//            if (racine.filsGauche == node || racine.filsDroit == node) {
//                indexDuParent = racine.getPosition(raf);
//            } else {
//                if (compareNom(node.getCle(), racine.getCle()) < 0) {
//                    indexDuParent = trouverIndexDuParent(node, raf);
//                } else {
//                    indexDuParent = trouverIndexDuParent(node, raf);
//                }
//            }
//        }
//        return indexDuParent;
//    }

    private int comparerNom(StagiaireBinaire s1, StagiaireBinaire s2) {
        return s1.getNom().compareToIgnoreCase(s2.getNom());
    }



    public String getNomLong() {
        String nomLong = this.cle.getNom();
        for (int i = this.cle.getNom().length(); i < StagiaireBinaire.TAILLE_NOM; i++) {
            nomLong += " ";
        }
        return nomLong;
    }

    public String getPrenomLong() {
        String prenomLong = this.cle.getPrenom();
        for (int i = this.cle.getPrenom().length(); i < StagiaireBinaire.TAILLE_PRENOM; i++) {
            prenomLong += " ";
        }
        return prenomLong;
    }

    public String getDepartementLong() {
        String departementLong = this.cle.getDepartement();
        for (int i = this.cle.getDepartement().length(); i < StagiaireBinaire.TAILLE_DEPARTEMENT; i++) {
            departementLong += " ";
        }
        return departementLong;
    }

    public String getPromoLong() {
        String promoLong = this.cle.getPromo();
        for (int i = this.cle.getPromo().length(); i < StagiaireBinaire.TAILLE_PROMO; i++) {
            promoLong += " ";
        }
        return promoLong;
    }

    public String getAnneeLong() {
        String anneeLong = this.cle.getAnnee();
        for (int i = this.cle.getAnnee().length(); i < StagiaireBinaire.TAILLE_ANNEE; i++) {
            anneeLong += " ";
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

//    @Override
//    public String toString() {
//        return cle.toString() + filsGauche + " " + filsDroit;
//    }

}
