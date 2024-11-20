package game;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AreaGame {
    private final HashMap<Coordinates,Card> area;

    private final ArrayList<Coordinates> checkCoor;


    // constructeur de la zone de jeu
    public AreaGame() {
        this.area= new HashMap<Coordinates, Card>();
        area.put(new Coordinates(50,200),StarterCard.firstCard());
        this.checkCoor = new ArrayList<Coordinates>();

    }

    //renvoi la carte de depart de la zone de jeu
    public Card stCard(){

        return area.get(new Coordinates(50,200));
    }
    //renvoi un boolean pour pouvoir verifier a l'endroit où on clique si il y a une carte
   public boolean isCard(Coordinates coordinates){
        Objects.requireNonNull(coordinates);
       for(Coordinates c : area.keySet()) {
           if (c.x() <= coordinates.x() && coordinates.x() <= c.x() + Card.width()
                   && c.y() <= coordinates.y() && coordinates.y() <= c.y() + Card.height()) {
               checkCoor.add(c);
               return true;
           }
       }return false;
    }

// permet de pouvoir se déplace dans le l'aire de jeu
    public void moveCard(String line) {
        Objects.requireNonNull(line);
        int change;
        if(line.equals("U") || line.equals("L")){
            change=-10;
        }else{ change=10;}
        var c = Map.copyOf(area);
        for (Coordinates co : c.keySet()) {
            if(line.equals("U") || line.equals("D")){
                area.put(new Coordinates(co.x(), co.y()+change), area.get(co));
            }else{
                area.put(new Coordinates(co.x()+change, co.y()), area.get(co));
            }
            area.remove(co);
        }
    }
 /* fonction permettant de recupere la taille de la liste des coordonnee ( on l'utilisera pour le system
     de point par recouvrement de coin )*/
    public int sizelist(){
        return checkCoor.size();
    }

    // renvoi un boolean pour determiner si on a clique sur le coin en haut a gauche de la carte
    public boolean isTopLeft(Coordinates coordinates){
        Objects.requireNonNull(coordinates);
        return lastCoor().x()<=coordinates.x() && coordinates.x()<=lastCoor().x()+(Corner.size())
        && lastCoor().y()<=coordinates.y() && coordinates.y()<=lastCoor().y()+(Corner.size());
    }
 // renvoi un boolean pour determiner si on a clique sur le coin en haut a droite de la carte
    public boolean isTopRight(Coordinates coordinates){
        Objects.requireNonNull(coordinates);
        return lastCoor().x()+(Card.width()-Corner.size())<=coordinates.x() && coordinates.x()<=lastCoor().x()+Card.width()
                && lastCoor().y()<=coordinates.y() && coordinates.y()<=lastCoor().y()+(Corner.size());
    }
    // renvoi un boolean pour determiner si on a clique sur le coin en bas a gauche de la carte
    public boolean isBottomLeft(Coordinates coordinates){
        Objects.requireNonNull(coordinates);
        return lastCoor().x()<=coordinates.x() && coordinates.x()<=lastCoor().x()+(Corner.size())
                && lastCoor().y()+(Card.height()-Corner.size())<=coordinates.y() && coordinates.y()<=lastCoor().y()+Card.height();
    }
    // renvoi un boolean pour determiner si on a clique sur le coin en bas a droite de la carte
    public boolean isBottomRight(Coordinates coordinates){
        Objects.requireNonNull(coordinates);
        return lastCoor().x()+(Card.width()-Corner.size())<=coordinates.x() && coordinates.x()<=lastCoor().x()+Card.width()
                && lastCoor().y()+(Card.height()-Corner.size())<=coordinates.y() && coordinates.y()<= lastCoor().y()+Card.height();
    }

    //dessine la zone de jeu
    public void zone(Graphics2D g){
        Objects.requireNonNull(g);
        GameView.drawArea(g,area);
    }

    //recupere la derniere coordonnee de la liste
    public Coordinates lastCoor(){
        return checkCoor.getLast();
    }

    // recupere les coordonnees de la carte où on a cliqué
    public Card clickCard(){
        return area.get(lastCoor());
    }

    // ajoute une carte sur la zone beige
    public void add(Card card,Coordinates c){
        Objects.requireNonNull(card);
        Objects.requireNonNull(c);
        area.put(c, card);
    }
}
