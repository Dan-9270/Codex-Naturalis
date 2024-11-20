package game;
import java.util.Objects;

public class Corner {
    private final String nameCorner;
    private boolean cornerEmpty;

    private final String position;

    // constructeur d'un coin
    public Corner(String nameCorner,String position){
        Objects.requireNonNull(nameCorner);
        Objects.requireNonNull(position);
        this.nameCorner=nameCorner;
        this.cornerEmpty= !nameCorner.equals("Invisible");
        this.position=position;
    }
    public boolean cornerEmpty() {
        return cornerEmpty;
    }

    public String position(){
        return position;
    }



    @Override
    public boolean equals(Object o) {
        return o instanceof Corner other
                && other.nameCorner.equals(nameCorner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameCorner);
    }
    // renvoi un boolean si le coin d'une carte possede une ressource animal

    public boolean animal(){
        return nameCorner.contains("Animal");
    }
 // renvoi un boolean si le coin d'une carte possede une ressource insect
    public boolean insect(){
        return nameCorner.contains("Insect");
    }

    // renvoi un boolean si le coin d'une carte possede une ressource champignon
    public boolean fungi(){
        return nameCorner.contains("Fungi");
    }
    // renvoi un boolean si le coin d'une carte possede une ressource plant
    public boolean plant(){
        return nameCorner.contains("Plant");
    }

    // renvoi un boolean si le coin d'une carte possede une ressource plume
    public boolean quill(){
        return nameCorner.contains("Quill");
    }
    // renvoi un boolean si le coin d'une carte possede une ressource encrier
    public boolean inkwell(){
        return nameCorner.contains("Inkwell");
    }
    // renvoi un boolean si le coin d'une carte possede une ressource manuscript
    public boolean manuscript(){
        return nameCorner.contains("Manuscript");
    }
    //taille du coin
    static public int size(){
        return 25;
    }

    //modifie la valeur du coin ( cornerEmpty est un boolean qui determine si le coin est libre ou pas )
    public void changeCorner(){
        if(cornerEmpty){
            cornerEmpty=false;
        }
    }

    @Override
    public String toString() {
        return nameCorner + " : " + cornerEmpty +" "+ position;
    }
}
