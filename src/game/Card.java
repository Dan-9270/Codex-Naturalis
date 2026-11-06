package game;

import java.util.ArrayList;
import java.util.HashMap;

public interface Card {

    static public int height() {
        return 100;
    }

    static public int width() {
        return 200;
    }


    public boolean isRessource();

    public boolean isGold();

    public Corner bottomright();

    public Corner bottomleft();

    public Corner topright();

    public Corner topleft();
    public String kingdom();
    public Score score();

    public ArrayList<String> cost();

    public boolean canPlaceGoldCard(String ressource, int count);

    public int countAnimal();
    public int countFungi();
    public int countInsect();
    public int countPlant();

    public int countQuill();
    public int countManuscript();
    public int countInkwell();
    public boolean isVerso();

    public Card changeScore(int a, HashMap<String,Integer> count);
}
