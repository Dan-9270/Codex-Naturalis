package game;
import com.github.forax.zen.ApplicationContext;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;

public record GameView(int xOrigin, int yOrigin, int height, int width ) {

    public static void drawImage(Graphics2D graphics, BufferedImage image, float x, float y, float dimX, float dimY) {
        var width = image.getWidth();
        var height = image.getHeight();
        var scale = Math.min(dimX / width, dimY / height);
        var transform = new AffineTransform(scale, 0, 0, scale, x + (dimX - scale * width) / 2,
                y + (dimY - scale * height) / 2);
        graphics.setComposite(AlphaComposite.SrcOver);
        graphics.drawImage(image, transform, null);
    }
    // dessine la couleur de la carte en fonction des ressources
    private static Color cardColor(Card card){
        Objects.requireNonNull(card);
        if (card.kingdom().equals("Fungi")) {
            return new Color(200,20,40);
        }
        if (card.kingdom().equals("Insect")) {
            return new Color(152, 76, 185);
        }
        if (card.kingdom().equals("Plant")) {
            return new Color(117, 197, 115);
        }
        return new Color(42, 111, 211);
    }
    // dessine la carte ressource
    public static void drawRessourceCard(Graphics2D graphics, Coordinates coor, Card card ){
        Objects.requireNonNull(card);
        Objects.requireNonNull(coor);
        Objects.requireNonNull(graphics);
        graphics.setColor(cardColor(card));
        graphics.fill(new Rectangle2D.Float(coor.x(),coor.y(),Card.width(),Card.height()));
        drawCorner(graphics,coor,card.topleft());
        drawCorner(graphics,new Coordinates(coor.x()+Card.width()-Corner.size(),coor.y()),card.topright());
        drawCorner(graphics,new Coordinates(coor.x(),coor.y()+Card.height()-Corner.size()),card.bottomleft());
        drawCorner(graphics,new Coordinates(coor.x()+Card.width()-Corner.size(),coor.y()+Card.height()-Corner.size()),card.bottomright());
        drawCardScore(graphics,new Coordinates(coor.x(),coor.y()),card.score());
        if(card.isVerso()){
            graphics.setColor(new Color(0, 0, 0));
            int circleSize = 20;
            graphics.fillOval((int) (coor.x() + (Card.width() - circleSize) / 2), (int) (coor.y() + (Card.height() - circleSize) / 2), circleSize, circleSize);
        }
    }
    // dessine le score sur les cartes
    public static void drawCardScore(Graphics2D graphics2D,Coordinates coor,Score score){
        Objects.requireNonNull(graphics2D);
        Objects.requireNonNull(coor);
        Objects.requireNonNull(score);
        drawDirectScoring(graphics2D,new Coordinates(coor.x()+100,coor.y()+5),score);
        drawCornerScoring(graphics2D,new Coordinates(coor.x()+100,coor.y()+5),score);
        drawInkwellScoring(graphics2D,new Coordinates(coor.x()+100,coor.y()+5),score);
        drawQuillScoring(graphics2D, new Coordinates(coor.x()+100,coor.y()+5),score);
        drawManuscriptScoring(graphics2D, new Coordinates(coor.x()+100,coor.y()),score);
    }
    // dessine "D :"
    public static void drawDirectScoring(Graphics2D graphics, Coordinates coor, Score score) {
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(coor);
        Objects.requireNonNull(score);
        if (score.directscoring()) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Serif", Font.PLAIN, 20));
            String scoreText = " D  :" + score.score();
            int textWidth = graphics.getFontMetrics().stringWidth(scoreText);
            int textHeight = graphics.getFontMetrics().getHeight();
            int centerX = (int) (coor.x() - (textWidth) / 2);
            int centerY = (int) (coor.y() + (textHeight) / 2);
            graphics.drawString(scoreText, centerX, centerY);
        }
    }
    // dessine "C :"
    public static void drawCornerScoring(Graphics2D graphics, Coordinates coor, Score score) {
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(coor);
        Objects.requireNonNull(score);
        if (score.cScore()) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Serif", Font.PLAIN, 20));
            String scoreText = "C :" + score.score();
            int textWidth = graphics.getFontMetrics().stringWidth(scoreText);
            int textHeight = graphics.getFontMetrics().getHeight();
            int centerX = (int) (coor.x() - (textWidth) / 2);
            int centerY = (int) (coor.y() + (textHeight) / 2);
            graphics.drawString(scoreText, centerX, centerY);
        }
    }
    //dessine "I :"
    public static void drawInkwellScoring(Graphics2D graphics, Coordinates coor, Score score) {
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(coor);
        Objects.requireNonNull(score);
        if (score.inkwellScore()) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Serif", Font.PLAIN, 20));
            String scoreText = "I :" + score.score();
            int textWidth = graphics.getFontMetrics().stringWidth(scoreText);
            int textHeight = graphics.getFontMetrics().getHeight();
            int centerX = (int) (coor.x() - (textWidth) / 2);
            int centerY = (int) (coor.y() + (textHeight) / 2);
            graphics.drawString(scoreText, centerX, centerY);
        }
    }
    //dessine "Q : "
    public static void drawQuillScoring(Graphics2D graphics, Coordinates coor, Score score) {
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(coor);
        Objects.requireNonNull(score);
        if (score.quillScore()) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Serif", Font.PLAIN, 20));
            String scoreText = "Q :" + score.score();
            int textWidth = graphics.getFontMetrics().stringWidth(scoreText);
            int textHeight = graphics.getFontMetrics().getHeight();
            int centerX = (int) (coor.x() - (textWidth) / 2);
            int centerY = (int) (coor.y() + (textHeight) / 2);
            graphics.drawString(scoreText, centerX, centerY);
        }
    }
    //dessine "M :"
    public static void drawManuscriptScoring(Graphics2D graphics, Coordinates coor, Score score) {
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(coor);
        Objects.requireNonNull(score);
        if (score.manuscirptScore()) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Serif", Font.PLAIN, 20));
            String scoreText = "M :" + score.score();
            int textWidth = graphics.getFontMetrics().stringWidth(scoreText);
            int textHeight = graphics.getFontMetrics().getHeight();
            int centerX = (int) (coor.x() - (textWidth) / 2);
            int centerY = (int) (coor.y() + (textHeight) / 2);
            graphics.drawString(scoreText, centerX, centerY);
        }
    }
    // dessine les coins de la carte
    private static void drawCorner(Graphics2D graphics, Coordinates  coor, Corner corner){
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(coor);
        Objects.requireNonNull(corner);
        if(corner.cornerEmpty()){
        graphics.setColor(Color.BLACK);
        graphics.draw(new Rectangle2D.Float(coor.x(),coor.y(),25,25));
        graphics.setColor(new Color(248, 215, 230));
        graphics.fill(new Rectangle2D.Float(coor.x(),coor.y(),Corner.size(),Corner.size()));}
        if(corner.animal()){
            graphics.setColor(Color.BLUE);
            graphics.setFont(new Font("Serif", Font.PLAIN, 25));
            graphics.drawString("A",coor.x()+5,coor.y()+22);
        }
        if(corner.fungi()){
            graphics.setColor(Color.BLUE);
            graphics.setFont(new Font("Serif", Font.PLAIN, 25));
            graphics.drawString("F",coor.x()+5,coor.y()+22);
        }
        if(corner.insect()){
            graphics.setColor(Color.BLUE);
            graphics.setFont(new Font("Serif", Font.PLAIN, 25));
            graphics.drawString("S",coor.x()+5,coor.y()+22);
        }
        if(corner.plant()){
            graphics.setColor(Color.BLUE);
            graphics.setFont(new Font("Serif", Font.PLAIN, 25));
            graphics.drawString("P",coor.x()+5,coor.y()+22);
        }
        if (corner.quill()){
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Serif", Font.PLAIN, 25));
            graphics.drawString("Q",coor.x()+5,coor.y()+24);
        } else if (corner.inkwell()){
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Serif", Font.PLAIN, 25));
            graphics.drawString("I",coor.x()+5,coor.y()+22);
        } else if (corner.manuscript()){
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Serif", Font.PLAIN, 25));
            graphics.drawString("M",coor.x()+2,coor.y()+22);
        }

    }
    // dessine les gold card
    public static void drawGoldCard(Graphics2D graphics, Coordinates coor, Card card ){
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(coor);
        Objects.requireNonNull(card);
        graphics.setColor(cardColor(card));
        graphics.fill(new Rectangle2D.Float(coor.x(),coor.y(),Card.width(),Card.height()));
        drawCorner(graphics,coor,card.topleft());
        drawCorner(graphics,new Coordinates(coor.x()+Card.width()-Corner.size(),coor.y()),card.topright());
        drawCorner(graphics,new Coordinates(coor.x(),coor.y()+Card.height()-Corner.size()),card.bottomleft());
        drawCorner(graphics,new Coordinates(coor.x()+Card.width()-Corner.size(),coor.y()+Card.height()-Corner.size()),card.bottomright());
        drawCardScore(graphics,new Coordinates(coor.x(),coor.y()),card.score());
        drawCost(graphics,card.cost(),coor.x(),coor.y());
        if(card.isVerso()){
            graphics.setColor(new Color(0, 0, 0));
            int circleSize = 20;
            graphics.fillOval((int) (coor.x() + (Card.width() - circleSize) / 2), (int) (coor.y() + (Card.height() - circleSize) / 2), circleSize, circleSize);
        }
    }
    // dessine la carte de depart
    public static void drawStarterCard(Graphics2D graphics, Coordinates coor, Card card ){
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(card);
        Objects.requireNonNull(coor);
        graphics.setColor(new Color(232, 220, 201));
        graphics.fill(new Rectangle2D.Float(coor.x(),coor.y(),Card.width(),Card.height()));
        drawCorner(graphics,coor,card.topleft());
        drawCorner(graphics,new Coordinates(coor.x()+Card.width()-Corner.size(),coor.y()),card.topright());
        drawCorner(graphics,new Coordinates(coor.x(),coor.y()+Card.height()-Corner.size()),card.bottomleft());
        drawCorner(graphics,new Coordinates(coor.x()+Card.width()-Corner.size(),coor.y()+Card.height()-Corner.size()),card.bottomright());
    }


    // dessine la main avec les 3 cartes ou moins
    public static void drawHand(Graphics2D graphics, ArrayList<Card> hand,int x,int y) {
        Objects.requireNonNull(hand);
        Objects.requireNonNull(graphics);
        int spacing = 275;
        int hand_x=0;int hand_y=y-(Card.height()+10);
        graphics.clearRect(hand_x,hand_y-40,275*3,Card.height()+10);
        drawKey(graphics,hand_x,hand_y);
        for (Card card : hand) {
            if (card.isRessource()) {
                GameView.drawRessourceCard(graphics, new Coordinates(hand_x, hand_y), card);
            } else {
                GameView.drawGoldCard(graphics, new Coordinates(hand_x, hand_y), card);
            }
            hand_x += spacing;}
    }
    //Dessine les touches a selectionner
    private static void drawKey(Graphics2D g, int x,int y) {
    	y=y-10;
    	x=x+100;
    	 int spacing = 275;
    	var lst = new ArrayList<String>();
    	lst.add("W");lst.add("X");lst.add("C");
    	g.setColor(Color.black);      g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 30));
    	for(var letter : lst) {
    		
    		g.drawString(letter, x, y);
    		x+=spacing;
    	}
    }
    //dessine le contour de la carte quand on la selectionne
    static public void drawChoice(Graphics2D graphics2D,Choice choice,ApplicationContext context){
        Objects.requireNonNull(context);
        Objects.requireNonNull(graphics2D);
        Objects.requireNonNull(choice);
        if(choice.card().isRessource()){
            drawRessourceCard(graphics2D,new Coordinates(275* choice.place(),context.getScreenInfo().height()-(Card.height()+10)),choice.card());
        }else{
            drawGoldCard(graphics2D,new Coordinates(275* choice.place(),context.getScreenInfo().height()-(Card.height()+10)),choice.card());
        }
        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.drawRect(275* choice.place(),context.getScreenInfo().height()-(Card.height()+10),Card.width(),Card.height());
    }
    //dessine les 6 cartes en haut a droite
    public static void drawStack(Graphics2D graphics, ApplicationContext context,Stack stack, int number) {
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(context);
        Objects.requireNonNull(stack);

        int x=context.getScreenInfo().width();
        int y=20;
        if(number==1){
            graphics.clearRect(x-Card.width(),y,Card.width(),y+Card.height()*2+60);
            drawRessourceCard(graphics,new Coordinates(x-Card.width(), y+Card.height()+30),stack.nextressourceCard());
            drawRessourceCard(graphics,new Coordinates(x-Card.width(), y+Card.height()*2+60),stack.nextressourceCard2());
            drawRessourceCard(graphics,new Coordinates(x-Card.width(),y),RessourceCard.switchVerso(stack.hiddenRessource()));
        }
        if(number==2){
            graphics.clearRect(x-(Card.width()*2+30),y,Card.width(),y+Card.height()*2+60);
            drawGoldCard(graphics,new Coordinates(x-(Card.width()*2+30), y+Card.height()+30),stack.nextgoldCard());
            drawGoldCard(graphics,new Coordinates(x-(Card.width()*2+30), y+Card.height()*2+60),stack.nextgoldCard2());
            drawGoldCard(graphics,new Coordinates(x-(Card.width()*2+30), y),GoldCard.switchVerso(stack.hiddenGold()));
        }
    }

    // dessine le score total
    public static void drawScore(Graphics2D graphics, int score,ApplicationContext context){
        Objects.requireNonNull(graphics);
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Serif", Font.PLAIN, 25));
        graphics.drawString("Score : "+String.valueOf(score),context.getScreenInfo().width()-150 ,context.getScreenInfo().height()-40);
    }
    //recupere les coordonnee d'une carte est permet de determiner si on dessine une gold ou une ressource
    private static void ressourceOrGold(Graphics2D g3,Coordinates coo,Card card){
        Objects.requireNonNull(g3);
        Objects.requireNonNull(coo);
        Objects.requireNonNull(card);
        if (card.isRessource()) {
            GameView.drawRessourceCard(g3, coo, card);
        } else if(card.isGold()) {
            GameView.drawGoldCard(g3, coo, card);
        }else{
            GameView.drawStarterCard(g3,coo,card);
        }
    }
    // dessine la zone de jeu
    public static void drawArea(Graphics2D g, HashMap<Coordinates,Card> area){
        Objects.requireNonNull(g);
        Objects.requireNonNull(area);
        for(Coordinates coo : area.keySet()){
            ressourceOrGold(g,coo,area.get(coo));
        }
    }

    //dessine le menu
    public static void drawStartGame(Graphics2D g,int x,int y){
        Objects.requireNonNull(g);
            int new_x= (int) (x-1.2*(x/2));
            int new_y=(int) (y-1.2*(y/2));
            drawRectStart(g,new_x,new_y);
            g.setFont(new Font("Monospaced", Font.BOLD, 25));
            g.setColor(new Color(200,75,150) );
            g.drawString("1 Joueur",new_x,new_y);
            g.drawString("2 Joueurs",new_x,new_y+60);
            g.drawString("3 Joueurs",new_x,new_y+120);
            g.drawString("4 Joueurs",new_x,new_y+180);

    }

    // dessine les rectangles du menu
    private static void drawRectStart(Graphics2D g, int x, int y){
        Objects.requireNonNull(g);
        g.fill(new Rectangle2D.Float(x - 20, y - 30, 200, 40));
        g.fill(new Rectangle2D.Float(x - 20, y+60 - 30, 200, 40));
        g.fill(new Rectangle2D.Float(x - 20, y+120 - 30, 200, 40));
        g.fill(new Rectangle2D.Float(x - 20, y+180 - 30, 200, 40));
    }

    //dessine les compteurs
    public static void drawRCount(Graphics2D g, Map<String,Integer> count,ApplicationContext context){
        Objects.requireNonNull(g);
        Objects.requireNonNull(count);
        int x=context.getScreenInfo().width()-150;int y=context.getScreenInfo().height()-250;
        for (String r : count.keySet()){
            drawRessource(g,count.get(r),x,y,r);
            y+=30;
        }
    }

    //dessine un compteur
    public static void drawRessource(Graphics2D graphics, int countAnimal,int x,int y,String ressource){
        Objects.requireNonNull(graphics);
        Objects.requireNonNull(ressource);
        graphics.clearRect(x,y,100,40);
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Serif", Font.PLAIN, 20));
        graphics.drawString(ressource +" : "+String.valueOf(countAnimal),x ,y);
    }

    // dessine le cout sur la gold card
    public static void drawCost(Graphics2D g,ArrayList<String> cost,float x,float y){
        Objects.requireNonNull(cost);
        Objects.requireNonNull(g);
        var str="";
        for(String ligne : cost){
            if(ligne.equals("Animal")){
                str+= " A";}
            if(ligne.equals("Plant")){
                str+= " P";}
            if(ligne.equals("Fungi")){
                str+= " F";}
            if(ligne.equals("Insect")){
                str+= " S";}
        }
        g.drawString(str,x+(Card.width()/2)-40,y+(Card.height()-5));
    }
    
    //ecris quelle joueur est en train de jouer au dessus de l'ecran
    static public void drawPlayer(String line,Graphics2D g,ApplicationContext context){
        Objects.requireNonNull(line);Objects.requireNonNull(g);Objects.requireNonNull(context);
        g.clearRect(context.getScreenInfo().width(),25,30,30);
        g.setColor(Color.black);
        g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 30));
        g.drawString(line,context.getScreenInfo().width()/2,25);
    }
    
    //Affiche le joueur qui a gagner
    static public void drawWinner(Graphics2D g,GameBoard player,ApplicationContext context) {
    	Objects.requireNonNull(player);Objects.requireNonNull(context);
    	g.clearRect(0, 0, context.getScreenInfo().width(), context.getScreenInfo().height());
    	g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 50));
    	g.drawString("Félicitation , "+player.toString()+" vous avez Gagné ! ", 0, context.getScreenInfo().height()/2);
    }
    }
