public enum EntityType {
/*  EXAMPLE (' ', 000, 00, .00, .00, "")  */
    PLAYER  ('H', 100, 10, .90, .01, "Player"),
    HEALTH  ('h', 000, 00, .00, .00, "Health"),
    ZOMBIE  ('Z', 100, 07, .70, .01, "Zombie"),
    OGRE    ('O', 150, 12, .75, .01, "Ogre"),
    TROLL   ('T', 175, 22, .80, .01, "Troll"),
    HUMAN   ('H', 100, 10, .80, .01, "Human");
 
    private final char entChar;
    private final int baseMaxHealth;
    private final int baseDamage;
    private final double baseAcc;
    private final double baseCritChance;
    private final String defaultName;
    EntityType(char entChar, int baseMaxHealth, int baseDamage,
               double baseAcc, double baseCritChance, String defaultName) {
        this.entChar = entChar;
        this.baseMaxHealth = baseMaxHealth;
        this.baseDamage = baseDamage;
        this.baseAcc = baseAcc;
        this.baseCritChance = baseCritChance;
        this.defaultName = defaultName;
    }

    public final char getEntChar() { return entChar; }
    public final int getBaseMaxHealth() { return baseMaxHealth; }
    public final int getBaseDamage() { return baseDamage; }
    public final double getBaseAcc() { return baseAcc; }
    public final double getBaseCritChance() { return baseCritChance; }
    public final String getDefaultName() { return defaultName; }

}
