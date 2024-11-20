package game;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;
import java.nio.file.Path;
import java.util.Random;


public class Stack {

    private final ArrayList<Card> ressourceStack;
    private final ArrayList<Card> goldCardStack;

    private Card hiddenGold;

    private Card hiddenRessource;
    private Card nextgoldCard;
    private Card nextgoldCard2;
    private Card nextressourceCard;
    private Card nextressourceCard2;
    
    // constructeur du stack
    public Stack(Path path) throws IOException {
        Objects.requireNonNull(path);
        ressourceStack = new ArrayList<Card>();
        goldCardStack = new ArrayList<Card>();
        completeStack(path);
        this.nextgoldCard=giveGold();
        this.nextgoldCard2=giveGold();
        this.nextressourceCard=giveRessource();
        this.nextressourceCard2=giveRessource();
        this.hiddenGold=giveGold();
        this.hiddenRessource=giveRessource();
    }

//Complete le deck grace au fichier txt =methode factory
    public void completeStack(Path path) throws IOException {
        Objects.requireNonNull(path);
        try(var reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.startsWith("ResourceCard")){
                    ressourceStack.add(RessourceCard.newCard(line));
                }
                if(line.startsWith("GoldCard")){
                    goldCardStack.add(GoldCard.newCard(line));
                }
            }

        }
    }

    // donne la gold card
    public Card giveGold(){
        Random ran = new Random();
        var index=ran.nextInt(goldSize());
        Card card=goldCardStack.get(index);
        goldCardStack.remove(index);
        return card;
    }

    // donne la ressource card
    public Card giveRessource(){
        Random ran = new Random();
        var index=ran.nextInt(ressourceSize());
        Card card=ressourceStack.get(index);
        ressourceStack.remove(index);
        return card;
    }

    // donne la carte cache gold quand on clique
    public Card giveHiddenRessource(){
        Card card=hiddenRessource;
        hiddenRessource=giveRessource();
        return card;
    }

    //donne la carte cache ressource quandon clique
    public Card giveHiddenGold(){
        Card card=hiddenGold;
        hiddenGold=giveGold();
        return card;
    }

    //donne la prochaine gold carte
    public Card givenextgold(int i){
        Card card;
        if(i==1) {
           card = nextgoldCard;
           nextgoldCard = hiddenGold;
        }
        else{
            card=nextgoldCard2;
            nextgoldCard2=hiddenGold;
        }
        hiddenGold=giveGold();
        return card;
    }
    //donne la prochaine carte ressource
    public Card givenextRessource(int i){
        Card card;
        if(i==1) {
            card = nextressourceCard;
            nextressourceCard=hiddenRessource;
        }
        else{
            card=nextressourceCard2;
            nextressourceCard2=hiddenRessource;
        }
        hiddenRessource=giveRessource();
        return card;
    }

    //accesseur
    public Card nextgoldCard() {
        return nextgoldCard;
    }

    public Card nextgoldCard2() {
        return nextgoldCard2;
    }

    public Card nextressourceCard() {
        return nextressourceCard;
    }

    public Card nextressourceCard2() {
        return nextressourceCard2;
    }

    public Card hiddenRessource() {
        return hiddenRessource;
    }

    public Card hiddenGold() {
        return hiddenGold;
    }

    public int ressourceSize(){
        return ressourceStack.size();
    }
    public int goldSize(){
        return goldCardStack.size();
    }

    @Override
    public String toString() {
        return ressourceStack.toString()+goldCardStack.toString();

    }
}