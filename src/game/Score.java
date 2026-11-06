package game;
import java.util.HashMap;
import java.util.Objects;

public class Score{

    private final String nameScore;

    private final int score;


    private Score(String word,int num){
        this.nameScore=Objects.requireNonNull(word);
        this.score=num;
    }
    public Score(String word){
        Objects.requireNonNull(word);
        if(word.contains(":")){
            var sc=word.split(":");
            nameScore=sc[0];
            score=Integer.valueOf(sc[1]);
        }else{
            nameScore=word;
            score=0;
        }
    }

    // renvoi un boolean pour savoir si la carte possede un score direct
    public boolean directscoring(){
        return nameScore.equals("D");
    }

    // renvoi un boolean pour savoir si la carte possede un score par recouvrement de coin
    public boolean cScore(){
        return nameScore.equals("C");
    }

    // renvoi un boolean pour savoir si la carte possede un score par point associe de plume
    public boolean inkwellScore(){
        return nameScore.equals("I");
    }

    // renvoi un boolean pour savoir si la carte possede un score par point associe d'encrier
    public boolean quillScore(){
        return nameScore.equals("Q");
    }

    // renvoi un boolean pour savoir si la carte possede un score par point associe de manuscript
    public boolean manuscirptScore(){
        return nameScore.equals("M");
    }


    public Score couvrement(int a, HashMap<String,Integer> b){
        int result=score;
        // prend le score de la carte et mulitplie avec le nbr de cvoin
        if(cScore()){
            result=a*score;
        }
        // prend le score de la carte et multiplie par le nbr de plume
        if(inkwellScore()){
            result=b.get("I")*score;
        }
        // prend le score de la carte et multiplie par le nbr d'encrier
        if(quillScore()){
            result=b.get("Q")*score;
        }
        // prend le score de la carte et multiplie par le nbr de manuscript
        if(manuscirptScore()){
            result=b.get("M")*score;
        }
        return new Score(nameScore,result);
    }

    //accesseur
    public int score(){
        return score;
    }



    @Override
    public String toString() {
        return nameScore + " : " + score;
    }
}
