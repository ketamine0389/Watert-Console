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
     *  2: Monster won the fight <br>
     *  3: Something successfully ran away <br>
     * <br>
     * Any other code acts as a 0 as it is invalid.
     */
    private short fightCode;
    private short turn;
    /** 0 = Player; 1 = Monster; */
    private final short[] CODES = { 0, 1 };
    
    public Combat(Entity initiate, Entity opponent) {
        if (!(initiate instanceof Player) && !(opponent instanceof Player) || 
            !(initiate instanceof Monster) && !(opponent instanceof Monster)) {
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
    public Entity getWinner() { return p.isDefeated()? m:p; }
    private Entity getEntityFromTurn() { return turn == 1? m:p; }

    private void runAwayFromTurn() {
        Entity e = turn == CODES[0]? p : m;
        
        endFight((short)3);

        Main.clear();
        System.out.println(e.getName() + " has ran away!");
    }
    
    private void combatLoop() {
        Main.clear();
        System.out.println("A fight has broken out between " + p.getName() + " and " + m.getName() + "!");
        while(!isFightOver()) {
            System.out.println(getStats());
            System.out.println();

            int dmg = attack();

            if (dmg == -2) {
                swapTurn();
            } else if (dmg != -1) {
                System.out.println(getLastAttack(dmg));
                swapTurn();
            } else {
                runAwayFromTurn();
                continue;
            }

            Main.enterToContinue();
        }
        
        if (fightCode == 0) endFight((short) (getWinner() instanceof Player? CODES[0] + 1 : CODES[1] + 1));
    }
    
    private int attack() {
        int dmg = 0;
        
        if (getEntityFromTurn() instanceof Player) {
            dmg = p.attack();
            
            if (dmg == -1) return -1;

            if (dmg == -2) {
                System.out.println(Colors.BOLD + Colors.RED + "Failed to run away!" + Colors.RESET + "\n");
                return dmg;
            }
            
            m.takeDamage(dmg);
        } else {
            dmg = m.attack();
    
            if (dmg == -1) return -1;
            
            p.takeDamage(dmg);
        }

        return dmg;
    }
    
    private String getLastAttack(int lastDmg) {
        Entity e = turn == CODES[0]? p : m;
        Entity e2 = turn == CODES[0]? m : p;

        return Colors.BLUE + e.getName() + Colors.GREEN + " has attacked " + Colors.BLUE + e2.getName() + Colors.GREEN + " for " + Colors.RED + lastDmg + Colors.GREEN + " damage!" + Colors.RESET;
    }

    private String getStats() {
        StringBuilder str = new StringBuilder();

		str.append(Colors.GREEN).append(p.getName() + " Health: ").append(Colors.BLUE).append(p.getHealth()).append(Colors.GREEN).append(" / ").append(Colors.BLUE).append(p.getMaxHealth()).append("\n");
		str.append(Colors.GREEN).append(p.getName() + " Level: ").append(Colors.BLUE).append(p.getLevel()).append("\n\n");
		str.append(Colors.RED).append(m.getName() + " Health: ").append(Colors.BLUE).append(m.getHealth()).append(Colors.RED).append(" / ").append(Colors.BLUE).append(m.getMaxHealth()).append("\n");
		str.append(Colors.RED).append(m.getName() + " Level: ").append(Colors.BLUE).append(m.getLevel()).append("\n");
		str.append(Colors.RESET);

        return str.toString();
    }
    
    public String getDefeatString() {
        if (p.isDefeated()) {
            return Colors.BLUE + p.getName() + Colors.GREEN + " has been " + Colors.RED + "defeated " + Colors.GREEN + "by " + Colors.BLUE + m.getName() + Colors.GREEN + "!" + Colors.RESET;
        } else if (m.isDefeated()) {
            return Colors.BLUE + p.getName() + Colors.GREEN + " has " + Colors.RED + "defeated " + Colors.BLUE + m.getName() + Colors.GREEN + "!" + Colors.RESET;
        } else {
            return "The fight never ended but here we are... (error)";
        }
    }

    public short getFightCode() { return fightCode; }
    
}
