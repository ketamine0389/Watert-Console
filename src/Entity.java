public class Entity {
    /* Class Variables */
    private final EntityType entType;
    private final char entChar;
    private final int expToLevelUp = 50;
    private final int CRIT = 2;
    private int level;
    private int exp;
    private int health;
    private int maxHealth;
    private double maxHealthMultiplier;
    private int damage;
    private double damageMultiplier;
    private double acc;
    private double accMultiplier;
    private double critChance;
    private double critChanceMultiplier;
    private String name;
    
    /*
     * Constructors
     */
    
    public Entity(EntityType entType, int level) {
        this.entType = entType;
        this.entChar = entType.getEntChar();
        this.level = level;
        
        init();
    }
    
    public Entity(EntityType entType) {
        this(entType, 1);
    }
    
    public Entity() {
        EntityType[] types = EntityType.values();
        int randNum = rand(0, types.length*3) % types.length;
        
        this.entType = randNum == 0? types[1]:types[randNum];
        this.entChar = entType.getEntChar();
        this.level = rand(1, 10);
        
        init();
    }
    
    /*
     * Methods
     */
    
    protected static int rand(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
    
    protected static double randPercentage() { return Math.random(); }
    
    private void init() {
        maxHealth = entType.getBaseMaxHealth();
        damage = entType.getBaseDamage();
        acc = entType.getBaseAcc();
        critChance = entType.getBaseCritChance();
        name = entType.getDefaultName();
        
        updateMultipliers();
        updateStats();
        
        if (level-1 > 0)
            exp = (level-1)*expToLevelUp;
        
        health = maxHealth;
    }
    
    private void updateStats() {
        calculateMaxHealth();
        calculateDamage();
        calculateAcc();
        calculateCritChance();
    }
    
    private void updateMultipliers() {
        double add = (level-1) * .1;
        
        maxHealthMultiplier = 1.0 + add;
        damageMultiplier = 1.0 + add;
        accMultiplier = 1.0 + add;
        critChanceMultiplier = 1.0 + add;
    }
    
    public final void updateLevel() {
        if (exp/expToLevelUp > level-1) {
            level = (int) Math.floor((double) exp / expToLevelUp);
            updateMultipliers();
            updateStats();
        }
    }
    
    public final void nextLevel() {
        exp += 50;
        updateLevel();
    }
    
    private void calculateMaxHealth() { maxHealth *= maxHealthMultiplier; }
    private void calculateDamage() { damage *= damageMultiplier; }
    private void calculateAcc() { acc *= accMultiplier; }
    private void calculateCritChance() { critChance *= critChanceMultiplier; }
    
    public void takeDamage(int incoming) { health -= incoming; }
    
    public boolean heal(int amt) {
        if (health >= maxHealth) {
            return false;
        } else if (health + amt == maxHealth) {
            health = maxHealth;
        } else {
            health += amt;
        }
        return true;
    }
    
    public int attack() {
        int dmg = damage;
        double rpa = randPercentage() / acc;
        double rpcc = randPercentage();
        
        if (rpa > 10) {
            double reduction = (1 - rpa) * (acc * 10);
            dmg -= Math.floor(reduction);
            
            if (1 - rpcc <= critChance) dmg *= CRIT;
        } else {
            dmg = 0;
        }
        
        return dmg;
    }
    
    public final boolean isDefeated() { return health == 0; }
    
    /*
     * Getters / Setters
     */
    
    public final void setLevel(int level) { this.level = level; }
    public final void setName(String name) { this.name = name; }
    
    public final EntityType getEntType() { return entType; }
    public final char getEntChar() { return entChar; }
    public final int getExpToLevelUp() { return expToLevelUp; }
    public final int getLevel() { return level; }
    public final int getExp() { return exp; }
    public final int getHealth() { return health; }
    public final int getMaxHealth() { return maxHealth; }
    public final int getDamage() { return damage; }
    public final double getAcc() { return acc; }
    public final double getCritChance() { return critChance; }
    public final String getName() { return name; }
    
}
