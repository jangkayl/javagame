package world3.SparringOpponents;

import world1.GameLogic;
import world1.Player;
import world1.StreetFighter;
import world3.SparFightLogic;

public class MagsilosSparring extends SparFightLogic{
    private static String[] opponentAttacks = {"Jab", "Hook", "Block", "Uppercut", "Cross", "Rear Uppercut", "Lead Hook", "Flow State", "Adrenaline Rush", "Sixth Sense"};
        
        public MagsilosSparring(Player player, StreetFighter opponent) {
            super(player, opponentAttacks, opponent);
    }

    @Override
    public String getOpponentName() {
        return "Magsilos";
    }

    @Override
    protected void handleWin() {
        winnerReward();
        System.out.println(); 
        
        if(getPlayerProgress().getAddStats() < 5){
            GameLogic.printSeparator(40);
            System.out.println(); 
            getPlayerProgress().setAddStats(getPlayerProgress().getAddStats() + 1);
            System.out.println("Congratulations! You've won " + getPlayerProgress().getAddStats() + " / 5 matches");
            System.out.println("\nHere are your choices: ( Select one only )");
            System.out.println("1. HP - Increase by +15% ");
            System.out.println("2. Stamina - Increase by +15%");
            System.out.println("3. Crit Chance - Increase by +5%");
            System.out.println("4. Dodge Chance - Increase by +5%");
            System.out.println("5. Crit Multiplier - Increase by +5%");
            System.out.print("\nEnter the number of the stat you'd like to upgrade: ");
            int choice = GameLogic.readInt("", 1, 5);
            addStats(choice);
            System.out.println();
            System.out.println("Stats added! Remember, you can gain stats up to 5 times!");
            GameLogic.printCenteredSeparator(50);
        }

        resetFighterStats();
        getPlayerProgress().setRound(getPlayerProgress().getRound() + 1);
        GameLogic.pressAnything();
        GameLogic.gameData.saveGame();
    }

    @Override
    protected void handleLoss() {
        resetFighterStats();
        getPlayerProgress().setRound(getPlayerProgress().getRound() + 1);
        // GameLogic.printSeparator(40);
        // System.out.println(); 
        // System.out.println("You have been defeated!");
        // System.out.println("You lost 150 points");
        // getPlayer().setPlayerPoints(getPlayer().getPlayerPoints() - 150);
        // System.out.println("You now have " + getPlayer().getPlayerPoints() + " points.");
        // System.out.println();
        GameLogic.pressAnything();
        GameLogic.gameData.saveGame();
    }

}