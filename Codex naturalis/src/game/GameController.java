package game;
import com.github.forax.zen.*;
import com.github.forax.zen.Event;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;


public class GameController {

    // boucle permettant de lancer le jeu
        static private int gameloop(Stack stack, GameBoard gameBoard,ApplicationContext context,int i){
            updateData(context,gameBoard,stack);
            Objects.requireNonNull(stack);
            Objects.requireNonNull(gameBoard);
            Objects.requireNonNull(context);
            int j = i;
            while (i==j) {
                Event event = context.pollEvent();
                if(gameBoard.win()){
                	win(context,gameBoard);
                	//context.dispose();
                }
                if (event != null) {
                    switch (event) {
                        case PointerEvent pointerEvent -> {
                            if (pointerEvent.action() == PointerEvent.Action.POINTER_UP) {
                                if(isClickInStack(pointerEvent,new Coordinates(context.getScreenInfo().width()-Card.width(),20))){
                                    if(gameBoard.giveRessourceCard(stack,context)){
                                    i++;}
                                }else if(isClickInStack(pointerEvent,new Coordinates(context.getScreenInfo().width()-(Card.width()*2+30),20))){
                                    if(gameBoard.giveGoldCard(stack,context)){
                                    i++;}
                                }else if(isClickInStack(pointerEvent,new Coordinates(context.getScreenInfo().width()-(Card.width()*2+30),20+Card.height()+30))){
                                    if(gameBoard.nextGold(stack,context,1)){
                                    i++;}
                                }else if(isClickInStack(pointerEvent,new Coordinates(context.getScreenInfo().width()-(Card.width()*2+30),20+Card.height()*2+60))){
                                    if(gameBoard.nextGold(stack,context,2)){
                                    i++;}
                                }else if(isClickInStack(pointerEvent,new Coordinates(context.getScreenInfo().width()-Card.width(),20+Card.height()+30))){
                                    if(gameBoard.nextRessource(stack,context,1)){
                                    i++;}
                                }else if(isClickInStack(pointerEvent,new Coordinates(context.getScreenInfo().width()-Card.width(),20+Card.height()*2+60))){
                                    if(gameBoard.nextRessource(stack,context,2)){
                                    i++;}}
                                else{
                                    gameBoard.placeCard(pointerEvent,context);
                                    updateData(context,gameBoard,stack);
                                    gameBoard.newHand(context);}}}
                        case KeyboardEvent keyboardEvent -> {
                            move(keyboardEvent,gameBoard,context,stack);
                            if (keyboardEvent.key() == KeyboardEvent.Key.ESCAPE) {
                            	System.exit(0);
                                context.dispose();}
                            gameBoard.whichCard(keyboardEvent);
                            gameBoard.whichFace(keyboardEvent);
                            gameBoard.selectedCard(context);}}}}
           return i;
        }

    static private boolean isClickInStack(PointerEvent pointerEvent, Coordinates stackCoordinates) {
        Objects.requireNonNull(pointerEvent);
        Objects.requireNonNull(stackCoordinates);
        int clickX = pointerEvent.location().x();
        int clickY = pointerEvent.location().y();
        float stackX = stackCoordinates.x();
        float stackY = stackCoordinates.y();
        int stackWidth = Card.width();
        int stackHeight = Card.height();

        // Vérifie si les coordonnées du clic se trouvent à l'intérieur du rectangle de la pioche
        boolean insideX = clickX >= stackX && clickX <= stackX + stackWidth;
        boolean insideY = clickY >= stackY && clickY <= stackY + stackHeight;
        return insideX && insideY;
    }
    
    static private void win(ApplicationContext context, GameBoard g) {
    	context.renderFrame(g2->{
    		GameView.drawWinner(g2, g, context);
    	});
    }
    static private void updateData(ApplicationContext context,GameBoard gameBoard,Stack stack) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(gameBoard);
        Objects.requireNonNull(stack);
        int y=context.getScreenInfo().height();
        int x=context.getScreenInfo().width();
        context.renderFrame(graphics -> {
            graphics.clearRect(0,0,x,y);
            GameView.drawPlayer(gameBoard.toString(),graphics,context);
            gameBoard.showArea(graphics);
            GameView.drawHand(graphics, gameBoard.hand(),x,y);
            GameView.drawRCount(graphics,gameBoard.count(),context);
            GameView.drawScore(graphics, gameBoard.totalScore(),context);
            GameView.drawStack(graphics, context, stack, 1);
            GameView.drawStack(graphics, context, stack, 2);

        });
    }
        private static boolean clickOnplayer(PointerEvent event,int x,int y){
            Objects.requireNonNull(event);
            int clickX = event.location().x();
            int clickY = event.location().y();
            if(x<=clickX && clickX <= x+200 & y<=clickY && clickY<= y+40){
                return true;
            }
            return false;
        }
    static private void move(KeyboardEvent keyboardEvent,GameBoard area,ApplicationContext context,Stack stack) {
        Objects.requireNonNull(keyboardEvent);Objects.requireNonNull(area);Objects.requireNonNull(context);Objects.requireNonNull(stack);
        int tr=0;
        if (keyboardEvent.key() == KeyboardEvent.Key.UP) {
            area.moveArea("U");
            tr=1;}
        if (keyboardEvent.key() == KeyboardEvent.Key.DOWN) {
            area.moveArea("D");
            tr=1;}
        if (keyboardEvent.key() == KeyboardEvent.Key.LEFT) {
            area.moveArea("L");
            tr=1;}
        if (keyboardEvent.key() == KeyboardEvent.Key.RIGHT) {
            area.moveArea("R");
            tr=1;}
        if(tr==1){
        updateData(context,area,stack);}
        }
        private static int numberOfplayer(ApplicationContext context) {
            Objects.requireNonNull(context);
            context.renderFrame(graphics -> {
                GameView.drawStartGame(graphics,context.getScreenInfo().width(),context.getScreenInfo().height());
            });
            int x=(int) (context.getScreenInfo().width()-1.2*(context.getScreenInfo().width()/2));
            int y=(int) (context.getScreenInfo().height()-1.2*(context.getScreenInfo().height()/2));
            while (true) {
                Event event = context.pollEvent();
                if (event != null) {
                    switch (event) {
                        case PointerEvent pointerEvent -> {
                            if (clickOnplayer(pointerEvent, x - 20, y - 30)) {
                                return 1;}
                            if (clickOnplayer(pointerEvent, x - 20, y + 60 - 30)) {
                                return 2;}
                            if (clickOnplayer(pointerEvent, x - 20, y + 120 - 30)) {
                                return 3;}
                            if (clickOnplayer(pointerEvent, x - 20, y + 180 - 30)) {
                                return 4;}
                        }
                        case KeyboardEvent keyboardEvent -> {
                            context.dispose();}}
                }
            }
        }


        public static void main(String[] args) throws IOException {
           Application.run(new Color(224, 176, 138), context -> {
               Stack stack;
               try {
                  stack = new Stack(Path.of("data","deck.txt"));
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               var gameBoard = new GameBoard("Joueur 1");
               var gameBoard2 = new GameBoard("Joueur 2");
               var gameBoard3 = new GameBoard("Joueur 3");
               var gameBoard4 = new GameBoard("Joueur 4");
               int variable=numberOfplayer(context);
               if(variable==1){
                   gameBoard.startHand(stack);
                   while (true){
                       int i = 0;
                       i=gameloop(stack,gameBoard,context,0);}
               }
               if(variable==2){
                   gameBoard.startHand(stack);
                   gameBoard2.startHand(stack);
                   while (true){
                       int i = 0;
                       i=gameloop(stack,gameBoard,context,0);
                       i = gameloop(stack, gameBoard2, context, 1);}
               }
               if(variable==3){
                   gameBoard.startHand(stack);
                   gameBoard2.startHand(stack);
                   gameBoard3.startHand(stack);
                   while (true){
                       int i = 0;
                       i=gameloop(stack,gameBoard,context,0);
                       i = gameloop(stack, gameBoard2, context, 1);
                       i = gameloop(stack, gameBoard3, context, 2);}
               }
               if(variable==4){
                   gameBoard.startHand(stack);
                   gameBoard2.startHand(stack);
                   gameBoard3.startHand(stack);
                   gameBoard4.startHand(stack);
                   while (true){
                       int i = 0;
                       i=gameloop(stack,gameBoard,context,0);
                       i= gameloop(stack, gameBoard2, context, 1);
                       i= gameloop(stack, gameBoard3, context, 2);
                       i= gameloop(stack, gameBoard4, context, 3);}
               }
            });
        }
    }
