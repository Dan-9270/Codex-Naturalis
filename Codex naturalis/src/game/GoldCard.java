package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GoldCard implements Card{

    private final String face;
    private final Corner topleft;
    private final Corner topright;
    private final Corner bottomleft;
    private final Corner bottomright;
    private final String kingdom;

    private final ArrayList<String> cost;
    private final Score score;

    public GoldCard(String face, Corner topleft, Corner topright, Corner bottomleft, Corner bottomright, String kingdom, ArrayList<String> cost, Score score) {
        this.face = face;
        this.topleft = topleft;
        this.topright = topright;
        this.bottomleft = bottomleft;
        this.bottomright = bottomright;
        this.kingdom = kingdom;
        this.cost = cost;
        this.score = score;
    }
    static public  GoldCard newCard(String line) {
        Objects.requireNonNull(line);
        String[] parts = line.split(" ");
        return new GoldCard(parts[1],
                new Corner(parts[2],"TL"),
                new Corner(parts[3],"TR"),
                new Corner(parts[4],"BL"),
                new Corner(parts[5],"BR"),
                parts[7], cost(parts), new Score(parts[parts.length-1]));
    }

    static public GoldCard switchVerso(Card c){
        return new GoldCard("Verso",
                new Corner("Empty","TL"),
                new Corner("Empty","TR"),
                new Corner("Empty","BL"),
                new Corner("Empty","BR"),c.kingdom(),vCost(),new Score("None")
        );
    }


    static private  ArrayList<String> vCost(){
        var cout = new ArrayList<String>();
        cout.add("None");
        return cout;
    }

    static private ArrayList<String> cost(String[] parts){ //Permet de d'ajouter les score;
        Objects.requireNonNull(parts);
        var cout = new ArrayList<String>();
        boolean possible=false;
        for(String part : parts){
            if(part.equals("Scoring")){
                possible = false;
            }
            if(possible){
                cout.add(part);
            }
            if(part.equals("Cost")){
                possible = true;
            }

        }
        return cout;
    }

    @Override
    public boolean isVerso() {
        return face.equals("Verso");
    }


    @Override
    public boolean isRessource() {
        return false;
    }

    @Override
    public boolean isGold() {
        return true;
    }

    @Override
    public Corner bottomright() {
        return bottomright;
    }

    @Override
    public Corner bottomleft() {
        return bottomleft;
    }

    @Override
    public Corner topleft() {
        return topleft;
    }

    @Override
    public Corner topright() {
        return topright;
    }

    @Override
    public String kingdom() {
        return kingdom;
    }




    @Override
    public boolean equals(Object obj) {
        return obj instanceof GoldCard other &&
                other.bottomright== bottomright && other.bottomleft==bottomleft && other.topright==topright && other.topleft==topleft;
    }
    @Override
    public Card changeScore(int a, HashMap<String,Integer> count) {
        Objects.requireNonNull(count);
        return new GoldCard(face,topleft,topright,bottomleft,bottomright,kingdom,cost,score.couvrement(a,count));
    }

    @Override
    public boolean canPlaceGoldCard(String ressource, int count){
        Objects.requireNonNull(ressource);
        int newCount = 0;
        for (String a : cost){
            if (ressource.equals(a)){
                newCount +=1;
            }
        }
        return count>=newCount;
    }

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

    @Override
    public Score score() {
        return score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bottomright,bottomleft,topright,topleft);
    }

    @Override
    public ArrayList<String> cost() {
        return cost;
    }

    @Override
    public String toString() {
        return "GoldCard{" +
                "face='" + face + '\'' +
                ", topleft=" + topleft +
                ", topright=" + topright +
                ", bottomleft=" + bottomleft +
                ", bottomright=" + bottomright +
                ", kingdom=" + kingdom +
                ", cost=" + cost +
                ", score=" + score +
                '}';
    }
}
