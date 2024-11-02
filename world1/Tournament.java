package world1;

import world1.TournamentFight.Lopez2;
import world1.TournamentFight.Ramirez2;
import world1.TournamentFight.Tetteh2;

public class Tournament {
    static StreetFighter opponent;
    static Player player = GameLogic.player;
    static String[] opponents = {"El Tigre", "El Jablo", "El Taeh"};
    static PlayerProgress playerProgress = GameLogic.playerProgress;

    public void setOpponent(StreetFighter enemy){
        opponent = enemy;
    }

    public StreetFighter getOpponent(){
        return opponent;
    }

    public static void attemptTournament(int playerStage) {
        GameLogic.clearConsole();
        if (playerStage < 3) {
            System.out.println("⚔️  Tournament Entry Attempt  ⚔️");
            GameLogic.printSeparator(40);
            System.out.println("You step forward, ready to face the toughest challengers...");
            System.out.println();
            GameLogic.printSeparator(40);
            System.out.println("[ But the tournament official stops with a grin ]");
            System.out.println("Gatekeeper: \"Hold it right there! You're not ready for this arena yet. Step into the ring now, and one solid hit");
            System.out.println("\t\t\tHead back to the Train in Gym, build your skills and get stronger will have you on the floor before you even know it!");
            System.out.println("\t\t\tGo back to Coach Fred at the gym, work those muscles, and test your mettle in sparring. No pulling punches—this is where the real training begins!\"");
            System.out.println();
            System.out.println("🏋️ Tip: Train hard, rank up, and grow stronger to unlock the tournament! 🏆");
            GameLogic.pressAnything();
        } else {
            startTournament();
        }
    }

    public static void startTournament() {
        GameLogic.clearConsole();
        GameLogic.printHeading("\t🏆 Champ Arena Tournament 🏆");

        if(player.getStage() < 6){
            System.out.println("Welcome, " + player.getName() + "! Prepare to fight your way to the top!");
            playerProgress.setDone(1);
            printTournament();

            while(true){
                if (playerProgress.getOpponentWins() == 3) {
                    if(offerRematch()){
                        resetMatchScores();
                        continue;
                    } else {
                        GameLogic.gymTraining();
                        return;
                    }
                }
    
                // Before each major stage, encourage checking the shop or inventory
                if (player.getStage() == 3) {
                    if (visitShopOrInventory()) {
                        startMatch(0);
                        if (playerProgress.getPlayerWins() == 3) {
                            player.setStage(4);
                            resetMatchScores();
                            continue;
                        }
                    }
                } else if (player.getStage() == 4) {
                    if (visitShopOrInventory()) {
                        startMatch(1);
                        if (playerProgress.getPlayerWins() == 3) {
                            player.setStage(5);
                            resetMatchScores();
                            continue;
                        }
                    }
                } else if (player.getStage() == 5) {
                    if (visitShopOrInventory()) {
                        startMatch(2);
                        if (playerProgress.getPlayerWins() == 3) {
                            player.setStage(6);
                            resetMatchScores();
                            break;
                        }
                    }
                }
            }
        }
        concludeTournament();
    }

    private static void startMatch(int opponentIndex) {
        System.out.println();
        System.out.println();
    
        String opponentName = opponents[opponentIndex];
        System.out.print(opponentIndex == 2 ? "FINAL OPPONENT: " : "You will face: ");
        System.out.print(opponentName);
    
        UrbanStory.tournaOpponentBackstory(opponentName);
    
        // Initialize opponent based on the index
        switch (opponentIndex) {
            case 0 -> {
                opponent = new StreetFighter("Rico Ramirez", 150, 80, 0.2, 2, 0.30);
                opponent.setPlayerOpponent(player);
                fightWithOpponent(new Ramirez2(player, opponent));
            }
            case 1 -> {
                opponent = new StreetFighter("Oscar Lopez", 170, 100, 0.2, 2, 0.30);
                opponent.setPlayerOpponent(player);
                fightWithOpponent(new Lopez2(player, opponent));
            }
            case 2 -> {
                opponent = new StreetFighter("Ishmael Tetteh", 200, 120, 0.3, 2.5, 0.40);
                opponent.setPlayerOpponent(player);
                fightWithOpponent(new Tetteh2(player, opponent));
            }
            default -> System.out.println("Invalid opponent index.");
        }
    }
    
    private static void fightWithOpponent(FightLogic fightLogic) {
        while (!isMatchConcluded()) {
            fightLogic.fightLoop();
        }
    }

    private static void resetMatchScores() {
        playerProgress.setRound(1);
        playerProgress.setPlayerWins(0);
        playerProgress.setOpponentWins(0);
    }

    private static boolean isMatchConcluded() {
        return playerProgress.getPlayerWins() == 3 || playerProgress.getOpponentWins() == 3;
    }

    private static boolean offerRematch() {
        System.out.println();
        System.out.println("You lost your previous match. Would you like to:");
        System.out.println("1. Try the tournament again?");
        System.out.println("2. Train with Fred or your coach to sharpen your skills and gain more stats!");
        System.out.println();
        System.out.print("Enter your choice (1 or 2): ");
        
        int choice = GameLogic.readInt("", 1, 2);
        if (choice == 1) {
            playerProgress.setPlayerWins(0);
            playerProgress.setOpponentWins(0);
            player.setStage(3);
            return true;  
        } else {
            return false;
        }
    }

    private static void concludeTournament() {
        System.out.println("🥊 Congratulations, Champion of the Ring! 🥊\n");
        System.out.println("You've conquered the tournament, delivering knockout blows and proving you have the heart of a true fighter!");
        System.out.println("The crowd roars, and your name is now legend. But the journey isn't over...");
        player.setCurrentWorld(1);
        GameLogic.pressAnything();
    }

    private static void printTournament() {
        System.out.println();
        GameLogic.printHeading("\t\tTournament Rules");
        System.out.println("1. You will face 3 opponents in this tournament.");
        System.out.println("2. Each opponent match is a best-of-3 rounds.");
        System.out.println("3. Win 3 rounds against each opponent to proceed.");
        System.out.println("4. If you lose a best-of-3 match, you are out of the tournament and must return to training.");
        System.out.println("5. Defeat all 3 opponents to claim victory in the tournament.");
    }

    private static boolean visitShopOrInventory() {
        System.out.println();
        System.out.println("Before continuing, would you like to visit the shop or check your inventory?");
        System.out.println("1. Visit Shop");
        System.out.println("2. Check Inventory");
        System.out.println("0. Continue Tournament");
        System.out.println();
        System.out.print("Enter your choice: ");
        
        int choice = GameLogic.readInt("", 0, 2);
        if (choice == 0) {
            return true;  
        } else if(choice == 1){
            GameLogic.shop.showShop(false);
            GameLogic.gameData.inputInventory(GameLogic.inventory.getInventoryItems());
        } else if(choice == 2){
            GameLogic.gameData.getGameDataManager().getInventory();
            GameLogic.gameData.getGameDataManager().getSlots();
            GameLogic.inventory.inventoryMenu();
            GameLogic.gameData.inputSlots(GameLogic.inventory.getSlots());
        }
        return false;
    }

    public void printStanding(){
        System.out.println();  
        GameLogic.printSeparator(40);
        System.out.println();  
        System.out.println("\t ~ ~ ~ BEST OF 3 ~ ~ ~");
        System.out.println();  
        System.out.println(player.getName() + " - " + playerProgress.getPlayerWins() + "\t\t" + opponent.getName() + " - " + playerProgress.getOpponentWins());
        System.out.println();  
        GameLogic.printSeparator(40);
        System.out.println();  
        GameLogic.pressAnything();
    }
}
