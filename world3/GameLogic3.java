package world3;

import world1.GameLogic;
import GlobalClasses.Inventory;
import GlobalClasses.Player;
import GlobalClasses.PlayerProgress;
import GlobalClasses.Shop;

public class GameLogic3 {
    public static Player player = GameLogic.player;
    public static PlayerProgress playerProgress = GameLogic.playerProgress;
    public static Shop shop = GameLogic.shop;
    public static Inventory inventory = new Inventory();
    
    public static void gameLoop(){
        int input;

        while(GameLogic.isRunning){
            shop = new Shop(player, playerProgress);
            printMenu();
            if(playerProgress.getShopStage() > 3){
                input = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 0, 4);
            } else {
                input = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 0, 3);
            }
            if(input == 0){
                GameLogic.isRunning = false;
            } else if(input == 1){
                continueJourney();
            } else if(input == 2){
                GameLogic.printStats();
            } else if(input == 3){
                GameLogic.gameData.getGameDataManager().getInventory();
                GameLogic.gameData.getGameDataManager().getSlots();
                inventory.inventoryMenu();
                GameLogic.gameData.inputSlots(inventory.getSlots());
            }
        }
    }

    // Prints the menu options
    static void printMenu(){
        GameLogic.gameData.saveGame();
        GameLogic.clearConsole();
        System.out.print(GameLogic.greenText);
        System.out.print(GameLogic.centerBox("MENU", 30));
        System.out.print(GameLogic.orangeText);
        System.out.println();
        System.out.print(GameLogic.centerText(50,"Choose an action:"));
        System.out.print(GameLogic.centerText(50,"(0) Exit Game"));
        System.out.print(GameLogic.centerText(50,"(1) Continue on your journey"));
        System.out.print(GameLogic.centerText(50,"(2) Check your Stats"));
        System.out.print(GameLogic.centerText(50,"(3) Inventory"));
        player.setHp(player.getMaxHp());
        player.setStamina(player.getMaxStamina());
        System.out.print(GameLogic.reset);
    }

    // Continues players journey
    private static void continueJourney() {
        TrainWithFred trainWithFred = new TrainWithFred();
        SparringRing sparringRing = new SparringRing();

        GameLogic.clearConsole();
        if(player.getCurrentWorld() == 2) {
            if(player.getStage() == 12){
                String[] worlds = player.getWorlds();
                System.out.print(GameLogic.greenText);
                System.out.print(GameLogic.centerBox("Welcome to the " + worlds[player.getCurrentWorld()],100));
                System.out.print(GameLogic.reset);
                StoryChampsArena.printChampsArena();
                System.out.print(GameLogic.orangeText);
                System.out.println("\n");
                System.out.print(GameLogic.centerText(50,"Are you ready to start your journey?"));
                System.out.print(GameLogic.centerText(50,"(1) Yes\n(2) No "));
                System.out.print(GameLogic.reset);
                int choice2 = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 2);
                if(choice2 == 2){
                    return;
                }
                StoryChampsArena.printIntroduction(player.getName());
                player.setStage(13);
                trainWithFred.teachPassiveSkills();
            } else {
                while (true) {
                    GameLogic.clearConsole();
                    System.out.print(GameLogic.greenText);
                    System.out.print(GameLogic.centerBox("MENU", 30));
                    System.out.println();
                    System.out.print(GameLogic.orangeText);
                    System.out.print(GameLogic.centerText(50,"(1) Sparring Ring"));
                    System.out.print(GameLogic.centerText(50,"(2) Boxing Arsenal"));
                    System.out.print(GameLogic.centerText(50,"(3) Enter Tournament"));
                    System.out.print(GameLogic.centerText(50,"(4) Go back"));
                    int choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 4);
                    System.out.print(GameLogic.reset);
                    if(choice == 1){
                        if(player.getStage() == 13)
                            trainWithFred.teachPassiveSkills();
                        else 
                            sparringRing.start();
                    } else if(choice == 2){
                        shop.showShop(false);
                        GameLogic.gameData.inputInventory(inventory.getInventoryItems());
                        GameLogic.gameData.saveGame();
                    } else if(choice == 3){
                        ChampTournament champTourna = new ChampTournament();
                        champTourna.attemptTournament(player.getStage());
                    } else if(choice == 4){
                        break;
                    } 
                }
            }
        }
    }
}
