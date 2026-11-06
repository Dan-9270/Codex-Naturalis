package game;
import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameBoard {

    private final List<Score> scoreList;

    private final String player;


    private final ArrayList<Card> hand;

    private Choice choice;

    private final AreaGame area;

    private HashMap<String,Integer> count;


    public Card choice() {
        return choice.card();
    }

    // constructeur du game board
    public GameBoard(String player) {
        this.player = player;
        this.area = new AreaGame();
        scoreList = new ArrayList<>();
        hand = new ArrayList<Card>();
        choice=new Choice(null,3);
        count=new HashMap<>();
        String[] keys = {"A", "P", "F", "S", "I", "M", "Q"};
        for (String key : keys) {
            count.put(key, 0);}
        countressource(area.stCard());
    }

    
    //main de depart
    public void startHand(Stack stack) {
        Objects.requireNonNull(stack);
        Random ran = new Random();
           hand.add(stack.giveRessource());
            hand.add(stack.giveRessource());
            hand.add(stack.giveGold());

    }
    
    //renvoi true si le joueur peut piocher une ressource card

    public boolean giveRessourceCard(Stack stack,ApplicationContext context) {
        Objects.requireNonNull(stack);
        Objects.requireNonNull(context);
        if(hand.size()!=3) {
            hand.add(stack.giveHiddenRessource());
            newHand(context);
            return true;
        }
        return false;
    }
    
    // renvoi true si le joueur peut piocher la gold card caché
    public boolean giveGoldCard(Stack stack,ApplicationContext context) {
        Objects.requireNonNull(stack);
        Objects.requireNonNull(context);
        if(hand.size()!=3) {
            hand.add(stack.giveHiddenGold());
            newHand(context);
            return true;
        }
        return false;
    }

    // renvoi true si le joueur peut piocher une gold card
    public boolean nextGold(Stack stack,ApplicationContext context,int i){
        Objects.requireNonNull(stack);
        Objects.requireNonNull(context);
        if(hand.size()!=3){
            hand.add(stack.givenextgold(i));
            newHand(context);
            return true;
        }
        return false;
    }
    
    //renvoi un boolean si le joueur peut piocher une ressource card

    public boolean nextRessource(Stack stack,ApplicationContext context,int i){
        Objects.requireNonNull(stack);
        Objects.requireNonNull(context);
        if(hand.size()!=3){
            hand.add(stack.givenextRessource(i));
            newHand(context);
            return true;
        }
        return false;
    }
    

    
    // ajoute le score direct d'une carte dans la liste
    private void addScore(Card card) {
       Objects.requireNonNull(card);
        scoreList.add(card.score());
    }
    public ArrayList<Card> hand(){
        return hand;
    }
    // parcour la liste des scores et ajoute le score dans un compteur
    public int totalScore() {
        int total = 0;
        for (var scoring : scoreList) {

            total += scoring.score();
        }

        return total;
    } 
    //le joueur selectionne la carte qui veut poser en fonction des touches
    public void whichCard(KeyboardEvent keyboardEvent){
        Objects.requireNonNull(keyboardEvent);
        if(hand.size()==3){
      if (keyboardEvent.key() == KeyboardEvent.Key.W){
          choice=new Choice(hand.get(0),0);
      }
      if (keyboardEvent.key() == KeyboardEvent.Key.X){
          choice=new Choice(hand.get(1),1);
        }
      if (keyboardEvent.key() == KeyboardEvent.Key.C){
          choice=new Choice(hand.get(2),2);
        }
        }
    }
    //deplace la zone de jeu en fonction du joueur
    public void moveArea(String letter){
        Objects.requireNonNull(letter);
        area.moveCard(letter);
    }
    //permet au joueur de changer entre le recto est verso d'une carte
    public void whichFace(KeyboardEvent keyboardEvent){
        Objects.requireNonNull(keyboardEvent);
        if(choice.card()!=null){
            if (keyboardEvent.key() == KeyboardEvent.Key.A){
                if(choice.card().isRessource()){
                    choice=new Choice(RessourceCard.switchVerso(choice.card()),choice.place());
                }else{
                    choice=new Choice(GoldCard.switchVerso(choice.card()),choice.place());
                }
            }
            if (keyboardEvent.key() == KeyboardEvent.Key.Z){
                choice=new Choice(hand.get(choice.place()),choice.place());
            }
        }

    }
    //affiche la carte selectionne
    public void selectedCard(ApplicationContext context){
        Objects.requireNonNull(context);
        if(choice.card()!=null){
        context.renderFrame(graphics2D -> {
            GameView.drawHand(graphics2D,hand,context.getScreenInfo().width(),context.getScreenInfo().height());
            GameView.drawChoice(graphics2D,choice,context);
        });
    }}
    // verifie un ensemble de condition pour que le joueur puisse placer sa carte
    public void placeCard(PointerEvent pointerEvent, ApplicationContext context){
        Objects.requireNonNull(pointerEvent);
        Objects.requireNonNull(context);
        Coordinates c = new Coordinates(pointerEvent.location().x(), pointerEvent.location().y());
        int sizeBefore = area.sizelist();
        if(area.isCard(c)) {
            var corner = whichCorner(c);
            if (corner != null) {
                var coo= area.lastCoor();
                if(otherCard(c,corner)){
                    if (canPlace(corner)) {
                        context.renderFrame(graphics -> {
                            if (choice.card() != null  && rightCost(choice.card())) {
                                int sizeAfter = area.sizelist();
                                int differenceSize = sizeAfter - sizeBefore;
                                Graphics2D g3 = graphics;
                                drawPlacement(g3,corner,coo);
                                choice=choice.changeScore(differenceSize,count);
                                addScore(choice.card());
                                choice = new Choice(null,3);

                            }
                        });
                    }}       }
        }
    }
    
    //verifie si le cout de la gold card est correct
    public boolean rightCost(Card c){
        if(!c.isGold()){
            return true;
        }
        if(c.canPlaceGoldCard("Animal", count.get("A")) && c.canPlaceGoldCard("Plant", count.get("P")) && c.canPlaceGoldCard("Insect", count.get("S")) && c.canPlaceGoldCard("Fungi", count.get("F"))){
            return true;
        }
        return false;
    }


    //return le coin où on a appuyer
    public Corner whichCorner(Coordinates c){
        Objects.requireNonNull(c);
        if(area.isBottomLeft(c)){
            return area.clickCard().bottomleft();
        }
        if(area.isBottomRight(c)){
            return area.clickCard().bottomright();
        }
        if(area.isTopRight(c)){
            return area.clickCard().topright();
        }
        if(area.isTopLeft(c)) {
            return area.clickCard().topleft();
        }
        return null;
    }
    //verifie si les coins des autres cartes sont dispo (pour poser des cartes dans les coins)
    public boolean otherCard(Coordinates c,Corner co){
        Objects.requireNonNull(c);
        Objects.requireNonNull(co);
        if(co.position().equals("BR")){
            if(area.isCard(new Coordinates(c.x()+Card.width(),c.y()))){
                var corner = whichCorner(new Coordinates(c.x()+Card.width()-Corner.size(),c.y())); //topright
                if (corner != null) {
                if(!canPlace(corner)){
                    return false;}}}
            if(area.isCard(new Coordinates(c.x()+Card.width(),c.y()+Card.height()))){  //bottomright
                var corner = whichCorner(new Coordinates(c.x()+Card.width()-Corner.size(),c.y()+Card.height()-Corner.size()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;}}
}
            if(area.isCard(new Coordinates(c.x(),c.y()+Card.height()))){   //bottomleft
                var corner = whichCorner(new Coordinates(c.x(),c.y()+Card.height()-Corner.size()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;
                    }
                }
            }

        }
        if(co.position().equals("BL")){
            if(area.isCard(new Coordinates(c.x()-Card.width(),c.y()))){ //topleft
                var corner = whichCorner(new Coordinates(c.x()-Card.width()+Corner.size(),c.y()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;
                    }
                }
            }
            if(area.isCard(new Coordinates(c.x()-Card.width(),c.y()+Card.height()))){  //bottomleft
                var corner = whichCorner(new Coordinates(c.x()-Card.width()+Corner.size(),c.y()+Card.height()-Corner.size()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;
                    }
                }
            }
            if(area.isCard(new Coordinates(c.x(),c.y()+Card.height()))){   //bottomright
                var corner = whichCorner(new Coordinates(c.x(),c.y()+Card.height()-Corner.size()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;
                    }
                }
            }

        }
        if(co.position().equals("TL")){
            if(area.isCard(new Coordinates(c.x()-Card.width(),c.y()))){   //bottomright
                var corner = whichCorner(new Coordinates(c.x()-Card.width()+Corner.size(),c.y()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;
                    }
                }
            }
            if(area.isCard(new Coordinates(c.x()-Card.width(),c.y()-Card.height()))){
                var corner = whichCorner(new Coordinates(c.x()-Card.width()+Corner.size(),c.y()-Card.height()+Corner.size()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;
                    }
                }
            }
            if(area.isCard(new Coordinates(c.x(),c.y()-Card.height()))){
                var corner = whichCorner(new Coordinates(c.x(),c.y()-Card.height()+Corner.size()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;
                    }
                }
            }

        }
        if(co.position().equals("TR")){
            if(area.isCard(new Coordinates(c.x()+Card.width(),c.y()))){
                var corner = whichCorner(new Coordinates(c.x()+Card.width()-Corner.size(),c.y()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;
                    }
                }
            }
            if(area.isCard(new Coordinates(c.x()+Card.width(),c.y()-Card.height()))){
                var corner = whichCorner(new Coordinates(c.x()+Card.width()-Corner.size(),c.y()-Card.height()+Corner.size()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;
                    }
                }
            }
            if(area.isCard(new Coordinates(c.x(),c.y()-Card.height()))){
                var corner = whichCorner(new Coordinates(c.x(),c.y()-Card.height()+Corner.size()));
                if (corner != null) {
                    if(!canPlace(corner)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    // renvoi les coordonnees du coin de la carte
    private Coordinates cornerCoor(Corner corner,Coordinates coo){
        Objects.requireNonNull(corner);
        Objects.requireNonNull(coo);
        if(corner.position().equals("BL")){
            choice.card().topright().changeCorner();
            return new Coordinates(coo.x()-(Card.width()-Corner.size()),coo.y()+(Card.height()-Corner.size()));
        }
        if(corner.position().equals("BR")){
            choice.card().topleft().changeCorner();
            return new Coordinates(coo.x()+(Card.width()-Corner.size()),coo.y()+(Card.height()-Corner.size()));
        }
        if(corner.position().equals("TL")){
            choice.card().bottomright().changeCorner();
            return new Coordinates(coo.x()-(Card.width()-Corner.size()),coo.y()-(Card.height()-Corner.size()));
        }
        choice.card().bottomleft().changeCorner();
        return new Coordinates(coo.x()+(Card.width()-Corner.size()),coo.y()-(Card.height()-Corner.size()));
    }
    
    //compte toute les ressources permettant de réaliser le system de point associe et pour le cout des goldcard
    public void countressource(Card card){
        Objects.requireNonNull(card);
        count.put("A",count.get("A")+card.countAnimal());
        count.put("F",count.get("F")+card.countFungi());
        count.put("S",count.get("S")+card.countInsect());
        count.put("P",count.get("P")+card.countPlant());
        count.put("I",count.get("I")+card.countInkwell());
        count.put("Q",count.get("Q")+card.countQuill());
        count.put("M",count.get("M")+card.countManuscript());
    }
    // reconnaitre la carte que l'on place
    private void drawPlacement(Graphics2D g3,Corner corner,Coordinates co){
        Objects.requireNonNull(g3);
        Objects.requireNonNull(co);
        var coo=cornerCoor(corner,co);
        area.add(choice.card(), coo);
        showArea(g3);
        countressource(choice.card());
        corner.changeCorner(); //eeeeee
        hand.remove(choice.place());
        }

    //zone d'aire de jeu d'un joueur
    public void showArea(Graphics2D g3){
        Objects.requireNonNull(g3);
        area.zone(g3);
    }


 // a chaque tour on clear la zone de jeu et on remontre la nouvelle main
    public void newHand(ApplicationContext context){
        Objects.requireNonNull(context);
        context.renderFrame(graphics->{
            GameView.drawHand(graphics,hand,context.getScreenInfo().width(),context.getScreenInfo().height());
            //GameView.drawScore(graphics,totalScore());

        });
    }


 // renvoi true pour savoir si on peut placer une carte dans le coin
    private boolean canPlace(Corner corner ){
        Objects.requireNonNull(corner);
        return corner.cornerEmpty();
    }

 // renvoi une version non mutable pour eviter de modifier le score
    public Map<String,Integer> count(){
        return Map.copyOf(count);
    }
    
    // renvoi un boolean pour vérifier si on a gagne
    public boolean win() {
        return totalScore() >= 20;
    }

    @Override
    public String toString() {
        return player ;
    }
}
