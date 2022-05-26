import java.util.Arrays;
import java.util.Scanner;
public class Player extends Human {
    // Class Variables
    private final Inventory ie = new Inventory();
    private final Scanner input = Main.input;
    
    public Player() {
        super(EntityType.PLAYER, 1);
    }
    
    private boolean runAway() { return randPercentage() > .70; }
    
    @Override
    public int attack() {
        String str;
        
        loop:
        while (true) {
            try {
                System.out.print("Press 1 to attack, 0 to try to run away » " + Colors.YELLOW);
                str = input.nextLine().toLowerCase();
                System.out.print(Colors.RESET);
    
                if (!Arrays.asList('1', '0').contains(str.charAt(0))) {
                    System.out.println("Invalid Input");
                    continue;
                }
                
                switch (str.charAt(0)) {
                    case '1':
                        break loop;
                    case '2':
                        if (runAway())
                            return -1;
                        break;
                }
            } catch (Exception e) {
                System.out.println("You must input a character!");
            }
        }
        
        return super.attack();
    }
    
}
