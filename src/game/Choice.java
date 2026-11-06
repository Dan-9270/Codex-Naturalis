package game;
import java.util.HashMap;
import java.util.Objects;

public record Choice(Card card, int place) {

// change le score de la carte (par le recouvrement)	
public Choice changeScore(int a, HashMap<String,Integer> count){
    Objects.requireNonNull(count);
    return new Choice(card.changeScore(a,count),place);
}
}


