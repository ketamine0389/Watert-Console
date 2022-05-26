public class Cell {
    // Class Variables
    private Entity occupant;
    private boolean visited;

    public Cell() { visited = false; }

    public char getChar(boolean revealed) {
        if (!revealed && !isOccupied()) {
            return ' ';
        } else if (!revealed && isOccupied()) {
            return '?';
        } else if (revealed && !isOccupied()) {
            return '-';
        } else if (revealed && isOccupied()) {
            return occupant.getEntChar();
        } else {
            return '¿';
        }
    }

    public boolean isOccupied() { return occupant != null; }

    public Entity getOccupant() { return occupant; }
    public boolean getVisited() { return visited; }

    public void setOccupant(Entity occupant) { this.occupant = occupant; }
    public void setVisited(boolean visited) { this.visited = visited; }

}
