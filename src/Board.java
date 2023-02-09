import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Board {
    /*
     * Class Variables
     */
    private final boolean revealed;
    private final ArrayList<List<Cell>> BOARD;
    private final ArrayList<Entity> ENTITIES;
    private final Difficulty DIF;
    private final short VISION;
    private final int ENTITY_AMT;
    private final int AVG_ENTITY_LVL;
    private final int[] DIMEN = new int[2];
    private final int[] playerLoc = new int[2];

    /*
     * Constructors
     */

    public Board(Difficulty DIF, boolean revealed) {
        this.DIF = DIF;
        this.revealed = revealed;
        VISION = DIF.getVision();
        DIMEN[0] = DIF.getRows();
        DIMEN[1] = DIF.getCols();

        ENTITY_AMT = (DIMEN[0]*DIMEN[1] + 16) / (rand((DIMEN[0]/4 + DIMEN[1]/4)/2, DIMEN[0]/4 + DIMEN[1]/4)/2);

        BOARD = new ArrayList<>();
        ENTITIES = new ArrayList<>();

        init();

        int tmp = 0;
        for (Entity e: ENTITIES)
            tmp += e.getLevel();
        AVG_ENTITY_LVL = tmp / ENTITIES.size();
    }

    public Board() { this(Difficulty.NORMAL, false); }

    /*
     * Methods
     */

    private static int rand(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }

    private void init() {
        for(int i = 0; i < DIMEN[0]; i++){
            BOARD.add(new ArrayList<>());
            for (int j = 0; j < DIMEN[1]; j++) {
                Cell c = new Cell();
                BOARD.get(i).add(c);
            }
        }

        placeEntities();
        placePlayer();
    }

    private void placeEntities() {
        for (int i = 0; i < ENTITY_AMT; i++) {
            int x,y;
            int lvl = 1;
//            int lvl = rand(DIF.getLowestLevel(), DIF.getHighestLevel());
            EntityType[] types = EntityType.values();
            int randNum = rand(0, types.length*3) % types.length;
            Entity e;

            switch (types[randNum]) {
                default: case PLAYER:
                    e = new Monster(EntityType.ZOMBIE, lvl);
                    break;
                case ZOMBIE: case OGRE: case TROLL:
                    e = new Monster(types[randNum], lvl);
                    break;
                case HUMAN:
                    e = new Human(EntityType.HUMAN, lvl);
                    break;
                case HEALTH:
                    e = new Entity(EntityType.HEALTH, lvl);
                    break;
            }

            do {
                x = rand(0, DIMEN[0]-1);
                y = rand(0, DIMEN[1]-1);
            } while(isCellOccupied(x, y));
            
            getCell(x, y).setOccupant(e);
            ENTITIES.add(e);
        }
    }

    private void placePlayer() {
        int x,y;
        Player p = new Player();

        do {
            x = rand(0, DIMEN[0]-1);
            y = rand(0, DIMEN[1]-1);
        } while(isCellOccupied(x, y) || isSurroundingOccupied(x, y));

        getCell(x, y).setOccupant(p);
        playerLoc[0] = x;
        playerLoc[1] = y;
    }

    public boolean movePlayer(char dir) {
        /* NOTE: dont touch this, it hurts */
        int x = playerLoc[0];
        int y = playerLoc[1];

        switch (dir) {
            case 'w':
                x--;
                break;
            case 'a':
                y--;
                break;
            case 's':
                x++;
                break;
            case 'd':
                y++;
                break;
        }

        if (isCellOutOfBounds(x, y)) return false;
        
        if (isCellOccupied(x, y)) {
            startCombat(getCell(playerLoc).getOccupant(), getCell(x, y).getOccupant());
            return true;
        }

        getCell(x, y).setOccupant(getCell(playerLoc).getOccupant());
        getCell(playerLoc).setOccupant(null);

        playerLoc[0] = x;
        playerLoc[1] = y;
        return true;
    }

    private void monsterDefeated(Entity e) {
        if (e instanceof Monster) {
//            getCell();
        }
    }

    private void humanDefeated(Entity e) {
        if (e instanceof Human) {
            // Replace with an inventory item
        }
    }
    
    private void startCombat(Entity in, Entity op) {
        Combat c = new Combat(in, op);

        switch (c.getFightCode()) {
            default:
            case (short)-1:
            case 0:
                System.out.println("A combat error has occurred.");
                break;
            case (short)1:
            case (short)2:
                System.out.println(c.getDefeatString());
                break;
            case (short)3:
                /* Do nothing as Combat class handles it */
                break;
        }

        Main.enterToContinue();
    }

    public boolean isGameOver() { return getCell(playerLoc).getOccupant().isDefeated(); }

    public boolean isGameComplete() {
        for (Entity e: ENTITIES)
            if (e.getHealth() != 0)
                return false;

        return true;
    }

    private boolean isCellOccupied(int x, int y) { return getCell(x, y).isOccupied(); }

    private boolean isSurroundingOccupied(int x, int y) {
        if (isCellOutOfBounds(x-1, y-1) || isCellOutOfBounds(x+1, y+1))
            return true;

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(getCell(x-1, y+1));
        cells.add(getCell(x, y+1));
        cells.add(getCell(x+1, y+1));
        cells.add(getCell(x-1, y));
        cells.add(getCell(x+1, y));
        cells.add(getCell(x-1, y-1));
        cells.add(getCell(x, y-1));
        cells.add(getCell(x+1, y-1));

        for (Cell c : cells)
            if (c.isOccupied()) return true;
        
        return false;
    }

    private boolean isCellOutOfBounds(int x, int y) {
        return x < 0 || x >= DIMEN[0] || y < 0 || y >= DIMEN[1];
    }

    public String getPlayerStats() {
        StringBuilder str = new StringBuilder();
        Player p = (Player) getCell(playerLoc).getOccupant();

        str.append(Colors.GREEN).append("Health: ").append(Colors.BLUE).append(p.getHealth()).append(Colors.GREEN).append(" / ").append(Colors.BLUE).append(p.getMaxHealth()).append("\n");
        str.append(Colors.GREEN).append("Level: ").append(Colors.BLUE).append(p.getLevel()).append("\n");
        str.append(Colors.GREEN).append("Exp: ").append(Colors.BLUE).append(p.getExp()).append(Colors.GREEN).append(" / ").append(Colors.BLUE).append(p.getExpToLevelUp() * p.getLevel() + 50).append("\n");
        str.append(Colors.GREEN).append("Damage: ").append(Colors.BLUE).append(p.getDamage()).append("\n");
        //str.append(Colors.GREEN).append(": ").append(Colors.BLUE).append().append(Colors.GREEN).append(" / ").append(Colors.BLUE).append().append("\n");

        str.append(Colors.RESET);
        return str.toString();
    }

    public String getBoardInfo() {
        StringBuilder str = new StringBuilder();

        str.append(Colors.GREEN).append("Difficulty: ").append(Colors.BLUE).append(DIF).append('\n').append(Colors.GREEN);
        str.append("Entities: ").append(Colors.BLUE).append(ENTITY_AMT).append('\n').append(Colors.GREEN);
        str.append("Avg Entity lvl: ").append(Colors.BLUE).append(AVG_ENTITY_LVL).append('\n').append(Colors.GREEN);
        str.append("Player Loc: ").append(Colors.BLUE).append(Arrays.toString(playerLoc)).append("\n").append(Colors.GREEN);
        str.append("X, Y: ").append(Colors.BLUE).append(Arrays.toString(DIMEN)).append(Colors.RESET);

        return str.toString();
    }

    public String getBoardAsString() {
        StringBuilder str = new StringBuilder();

        str.append(" /");
        for (int i=0; i < DIMEN[0]; i++)
                str.append("---");
        str.append("\\");
        str.append("\n");

        for (int i=0; i < DIMEN[0]; i++) {
            str.append(" | ");
            for (int j=0; j < DIMEN[1]; j++) {
                Cell c = getCell(i, j);

                if (c.isOccupied()) {
                    if (c.getOccupant().getEntType() == EntityType.PLAYER) {
                        str.append(Colors.RED).append(c.getChar(true)).append(Colors.RESET).append("  ");
                        continue;
                    }
                }

                if (!revealed && j >= playerLoc[1] - VISION && j <= playerLoc[1] + VISION && i >= playerLoc[0] - VISION && i <= playerLoc[0] + VISION) {
                    str.append(c.getChar(true)).append("  ");
                    continue;
                }
                
                str.append(c.getChar(revealed)).append("  ");
            }
            str.append("\b\b | ");
            str.append("\n");
        }

        str.append(" \\");
        for (int i=0; i < DIMEN[0]; i++)
            str.append("---");
        str.append("/");
        str.append("\n");

        return str.toString();
    }

    public Cell getCell(int[] loc) { return BOARD.get(loc[0]).get(loc[1]); }
    public Cell getCell(int row, int col) { return BOARD.get(row).get(col); }
    
}
