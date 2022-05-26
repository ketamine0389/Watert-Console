public class Monster extends Entity {
    // Class Variables
    private int expHeld;
    private boolean isInCombat;
    
    public Monster(){
        super(null);
    }

    public Monster(EntityType e, int lvl) {
        super(e, lvl);
    }
    
}
