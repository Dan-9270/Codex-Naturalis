package game;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class StarterCard implements Card{
    private final String face;
    private final Corner topleft;
    private final Corner topright;
    private final Corner bottomleft;
    private final Corner bottomright;


    public StarterCard(String face, Corner topleft, Corner topright, Corner bottomleft, Corner bottomright) {
        this.face = face;
        this.topleft = topleft;
        this.topright = topright;
        this.bottomleft = bottomleft;
        this.bottomright = bottomright;
    }

    static private  StarterCard newCard(String line) {
        Objects.requireNonNull(line);
        String[] parts = line.split(" ");
        return new StarterCard(parts[1],
                new Corner(parts[2],"TL"),
                new Corner(parts[3],"TR"),
                new Corner(parts[4],"BL"),
                new Corner(parts[5],"BR"));
                //new Animal(parts[7]));
    }
    private static List<StarterCard> giveStater(Path path) throws IOException {
        Objects.requireNonNull(path);
        ArrayList<StarterCard> stackS = new ArrayList<>();
        try (var reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("StarterCard")) {
                    stackS.add(StarterCard.newCard(line));
                }
            }
        }
        return List.copyOf(stackS);
    }
    static public StarterCard firstCard(){
        try {
            var stack = giveStater(Path.of("data","deck.txt"));
            Random ran = new Random();
            return stack.get(ran.nextInt(stack.size()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Corner topleft() {
        return topleft;
    }

    @Override
    public Corner bottomright() {
        return bottomright;
    }

    @Override
    public Corner topright() {
        return topright;
    }

    @Override
    public Corner bottomleft() {
        return bottomleft;
    }

    @Override
    public boolean isVerso() {
        return face.equals("Verso");
    }

    @Override//A CHANGER
    public Score score() {
        return null;
    }

    @Override
    public boolean isRessource() {
        return false;
    }

    @Override
    public boolean isGold() {
        return false;
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
    public String kingdom() {
        return "A changer";
    }

    @Override
    public Card changeScore(int a, HashMap<String,Integer> count) {
        return new StarterCard(face,topleft,topright,bottomleft,bottomright);
    }



    @Override
    public boolean canPlaceGoldCard(String ressource, int count) {
        return false;
    }

    @Override
    public ArrayList<String> cost() {
        return null;
    }

    @Override
    public String toString() {
        return "StarterCard{" +
                "face='" + face + '\'' +
                ", topleft=" + topleft +
                ", topright=" + topright +
                ", bottomleft=" + bottomleft +
                ", bottomright=" + bottomright +
                '}';
    }
}
