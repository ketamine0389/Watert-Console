public enum Difficulty {
    EASY(32,32, 1, 7, 1, (short)4),
    NORMAL(64,64, 7, 15, 4, (short)5),
    HARD(128,128, 15, 25, 13, (short)7);

    private final int r;
    private final int c;
    private final int ll;
    private final int hl;
    private final int pl;
    private final short v;
    Difficulty(int r, int c, int ll, int hl, int pl, short v) {
        this.c = c;
        this.r = r;
        this.ll = ll;
        this.hl = hl;
        this.pl = pl;
        this.v = v;
    }

    int getRows() { return r; }
    int getCols() { return c; }
    int getLowestLevel() { return ll; }
    int getHighestLevel() { return hl; }
    int getPlayerLevel() { return pl; }
    short getVision() { return v; }

}
