// note for later: possibly call System.gc() after combat is finished
public class Combat {
    /* Class Variables */
    private boolean inCombat;
    private final short first;
    private final Player p;
    private final Monster m;
    /**
     * Possible fight codes: <br>
     * -1: Fight is illegal <br>
     *  0: Fight is ongoing <br>
     *  1: Player won the fight <br>
     *  2: Entity won the fight <br>
     * <br>
     * Any other code acts as a 0 as it is invalid.
     */
    private short fightCode;
    private short turn;
    /** 0 = Player; 1 = Monster; */
    private final short[] CODES = { 0, 1 };
    
    public Combat(Entity initiate, Entity opponent) {
        if (!(initiate instanceof Player) || !(opponent instanceof Player)) {
            p = null;
            m = null;
            first = 0;
            endFight((short) -1);
            return;
        }
        
        fightCode = 0;
        
        p = initiate instanceof Player? (Player)initiate : (Player)opponent;
        m = initiate instanceof Player? (Monster)opponent : (Monster)initiate;
        first = initiate instanceof Player? CODES[0] : CODES[1];
        
        combatLoop();
    }
    
    private void swapTurn() { turn = turn == 1? CODES[0] : CODES[1]; }
    private boolean isFightOver() { return p.isDefeated() || m.isDefeated() || fightCode != 0; }
    private void endFight(short fightCode) { this.fightCode = fightCode; }
    private Entity getEntityFromTurn() { return turn == 1? m:p; }
    private Entity getWinner() { return p.isDefeated()? m:p; }
    
    private void combatLoop() {
        System.out.println("A fight has broken out!");
        do {
            System.out.println(getStats());
            
            attack();
            
            System.out.println("<Press 'Enter' to Continue>");
            Main.input.nextLine();
        } while(!isFightOver());
        
        endFight(getWinner() instanceof Player? CODES[0] : CODES[1]);
    }
    
    private void attack() {
        int dmg = 0;
        
        if (getEntityFromTurn() instanceof Player) {
            dmg = p.attack();
            
            if (dmg == -1) {
                System.out.println(p.getName() + " has ran away!");
            }
            
            m.takeDamage(dmg);
        } else {
            dmg = m.attack();
    
            if (dmg == -1) {
                System.out.println(m.getName() + " has ran away!");
            }
            
            p.takeDamage(dmg);
        }
    }
    
    public String getStats() {
        StringBuilder str = new StringBuilder();
        str.append("placeholder\n");
        return str.toString();
    }
    
    public short getFightCode() { return fightCode; }
    
}
