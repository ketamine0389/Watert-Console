public class Human extends Entity {
    // Class Variables
    private int expHeld;
    private boolean isInteracting;
    private boolean isInCombat;

    public Human(EntityType e, int lvl) {
        super(e, lvl);
        expHeld = 16;
    }

    public Human(){
        super(EntityType.HUMAN);
        expHeld = 20;
        isInteracting = false;
    }

}
