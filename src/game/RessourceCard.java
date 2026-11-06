package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RessourceCard implements Card {

    private final String face;
    private final Corner topleft;
    private final Corner topright;
    private final Corner bottomleft;
    private final Corner bottomright;
    private final String kingdom;
    private final Score score;

    // constructeur de la carte ressource
    public RessourceCard(String face, Corner topleft, Corner topright, Corner bottomleft, Corner bottomright, String kingdom, Score score) {
        this.face = face;
        this.topleft = topleft;
        this.topright = topright;
        this.bottomleft = bottomleft;
        this.bottomright = bottomright;
        this.kingdom = kingdom;
        this.score = score;
    }

    // accesseur
    @Override
    public boolean isRessource() {
        return true;
    }

    //initialise la ressource card
    static public  RessourceCard newCard(String line) {
        Objects.requireNonNull(line);
        String[] parts = line.split(" ");
        return new RessourceCard(parts[1],
                new Corner(parts[2],"TL"),
                new Corner(parts[3],"TR"),
                new Corner(parts[4],"BL"),
                new Corner(parts[5],"BR"),
                parts[7],
                new Score(parts[9]));
    }

    // oblige de l'ecrire car on l'a mis dans l'interface card
    @Override
    public boolean canPlaceGoldCard(String ressource, int count) {
        return false;
    }

    // switch entre le recto et le verso
    static public RessourceCard switchVerso(Card c){
        Objects.requireNonNull(c);
        return new RessourceCard("Verso",
                    new Corner("Empty","TL"),
                    new Corner("Empty","TR"),
                    new Corner("Empty","BL"),
                    new Corner("Empty","BR"),
                    c.kingdom(),
                    new Score("None")
                    );
    }

    // renvoi un boolean pour connaitre si les carte est verso
    @Override
    public boolean isVerso() {
        return face.equals("Verso");
    }

    // accesseur
    @Override
    public Corner bottomleft() {
        return bottomleft;
    }

    // accesseur
    @Override
    public Corner bottomright() {
        return bottomright;
    }

    // accesseur
    @Override
    public Corner topright() {
        return topright;
    }

    // accesseur
    @Override
    public Corner topleft() {
        return topleft;
    }

    // accesseur
    @Override
    public String kingdom() {
        return kingdom;
    }

    // cahnge le score
    @Override
    public Card changeScore(int a, HashMap<String,Integer> count) {
        Objects.requireNonNull(count);
        return new RessourceCard(face,topleft,topright,bottomleft,bottomright,kingdom,score.couvrement(a,count));
    }

    // renvoi false car la carte n'est pas une gold
    @Override
    public boolean isGold() {
        return false;
    }

    // compteur pour la ressource animal
    @Override
    public int countAnimal() {
        var count= 0;
        if(topleft.animal()){
            count ++;
        }
        if(topright.animal()){
            count ++;
        }
        if(bottomright.animal()){
            count ++;
        }
        if(bottomleft.animal()){
            count ++;
        }
        if(isVerso() && kingdom.equals("Animal")){
            count++;
        }
        return count;
    }

    // compteur pour la ressource insect
    @Override
    public int countInsect() {
        var count= 0;
        if(topleft.insect()){
            count ++;
        }
        if(topright.insect()){
            count ++;
        }
        if(bottomright.insect()){
            count ++;
        }
        if(bottomleft.insect()){
            count ++;
        }
        if(isVerso() && kingdom.equals("Insect")){
            count++;
        }
        return count;
    }

    // compteur pour la ressource champignon
    @Override
    public int countFungi() {
        var count= 0;
        if(topleft.fungi()){
            count ++;
        }
        if(topright.fungi()){
            count ++;
        }
        if(bottomright.fungi()){
            count ++;
        }
        if(bottomleft.fungi()){
            count ++;
        }
        if(isVerso() && kingdom.equals("Fungi")){
            count++;
        }
        return count;
    }

    // compteur pour la ressource enrier
    @Override
    public int countInkwell() {
        var count= 0;
        if(topleft.inkwell()){
            count ++;
        }
        if(topright.inkwell()){
            count ++;
        }
        if(bottomright.inkwell()){
            count ++;
        }
        if(bottomleft.inkwell()){
            count ++;
        }
        return count;
    }

    // compteur pour la ressource plume
    @Override
    public int countQuill() {
        var count= 0;
        if(topleft.quill()){
            count ++;
        }
        if(topright.quill()){
            count ++;
        }
        if(bottomright.quill()){
            count ++;
        }
        if(bottomleft.quill()){
            count ++;
        }
        return count;
    }

    // compteur pour la ressource manuscript
    @Override
    public int countManuscript() {
        var count= 0;
        if(topleft.manuscript()){
            count ++;
        }
        if(topright.manuscript()){
            count ++;
        }
        if(bottomright.manuscript()){
            count ++;
        }
        if(bottomleft.manuscript()){
            count ++;
        }
        return count;
    }

    // compteur pour la ressource plante
    @Override
    public int countPlant() {
        var count= 0;
        if(topleft.plant()){
            count ++;
        }
        if(topright.plant()){
            count ++;
        }
        if(bottomright.plant()){
            count ++;
        }
        if(bottomleft.plant()){
            count ++;
        }
        if(isVerso() && kingdom.equals("Plant")){
            count++;
        }
        return count;
    }

    // accesseur
    @Override
    public Score score() {
        return score;
    }

    @Override
    public ArrayList<String> cost() {
        return null;
    }

    @Override
    public String toString() {
        return "RessourceCard{" +
                "face='" + face + '\'' +
                ", topleft=" + topleft +
                ", topright=" + topright +
                ", bottomleft=" + bottomleft +
                ", bottomright=" + bottomright +
                ", kingdom=" + kingdom +
                ", score=" + score +
                '}';
    }
}


