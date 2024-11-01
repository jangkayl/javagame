package world1.TournamentFight;
import java.util.Random;

import world1.GameLogic;
import world1.Player;
import world1.PlayerProgress;
import world1.StreetFighter;
import world1.Tournament;
import world1.FightingLogic.VsTetteh;

public class TettehTourna {
    static Random rand = new Random();
    static int[] opponentChoices = new int[3];
    static PlayerProgress playerProgress = GameLogic.playerProgress;
    static Player player;
    static String[][] attackOption = {{"Jab", "Damage: 10 | Stamina: -5"}, 
                                    {"Hook", "Damage: 15 | Stamina: -7"}, 
                                    {"Block", "Stamina: +5"}, 
                                    {"Uppercut", "Damage: 20 | Stamina: -10"},
                                    {"The Body Breaker", ""}};
    static String[][] comboOption = {{"Lead Body Shot", "Damage: 15 | Stamina: -7"},
                                    {"Cross to the Ribs", "Damage: 20 | Stamina: -9"},
                                    {"Finishing Uppercut", "Damage: 25 | Stamina: -14"}};
    public static String[] playerAttacks = {"Jab", "Hook", "Block", "Uppercut", "Lead Body Shot", "Cross to the Ribs", "Finishing Uppercut"};
    public static String[] opponentAttacks = {"Jab", "Hook", "Block", "Uppercut", "Right Uppercut", "Left Hook", "Right Cross"};
    static Tournament tourna = new Tournament();
    static StreetFighter opponent = tourna.getOpponent();
    static VsTetteh vsTetteh;
    
    public static void setPlayer(Player p) {
        player = p;
    }

    public static void fightLoop2() {
        player.setStage(5);
        GameLogic.gameData.saveGame();
        GameLogic.clearConsole();
        GameLogic.printSeparator(40);
        System.out.println(GameLogic.centerText("Round " + playerProgress.getRound(), 40));
        GameLogic.printSeparator(40);
        System.out.println(GameLogic.centerText("You are fighting " + opponent.getName() + "!", 40));
        System.out.println();
        GameLogic.printSeparator(40);
        vsTetteh = new VsTetteh(player, opponent);
        player.setOpponent(opponent);
        printStats();
        while (player.getHp() > 0 && opponent.getHp() > 0) {
            selectAttack();
            printStats();
            if (player.getHp() <= 0) {
                System.out.println();
                System.out.println(player.getName() + " is knocked out! " + opponent.getName() + " wins!");
                player.setIsLose(true);
                playerProgress.setRound(playerProgress.getRound() + 1);
                opponent.setHp(opponent.getMaxHp());
                opponent.setStamina(opponent.getMaxStamina());
                player.setHp(player.getMaxHp());
                player.setStamina(player.getMaxStamina());
                if(playerProgress.getOpponentWins() != 3){
                    playerProgress.setOpponentWins(playerProgress.getOpponentWins() + 1);
                }
                tourna.printStanding();
                GameLogic.gameData.saveGame();
                return;
            } else if(opponent.getHp() <= 0){
                System.out.println();
                System.out.println(opponent.getName() + " is knocked out! " + player.getName() + " wins!");
                player.setIsLose(false);
                winnerReward();
                player.setHp(player.getMaxHp());
                player.setStamina(player.getMaxStamina());
                opponent.setHp(opponent.getMaxHp());
                opponent.setStamina(opponent.getMaxStamina());
                playerProgress.setRound(playerProgress.getRound() + 1);
                if(playerProgress.getPlayerWins() != 3){
                    playerProgress.setPlayerWins(playerProgress.getPlayerWins() + 1);
                } else {
                    player.setStage(6);
                }
                tourna.printStanding();
                GameLogic.gameData.saveGame();
                return;
            }
        }
        GameLogic.pressAnything();
    }

    static void printStats(){
        System.out.println();
        System.out.println(GameLogic.formatColumns(player.getName(), opponent.getName(), 30));
        System.out.println(GameLogic.formatColumns("HP        " + player.getHp() + "/" + player.getMaxHp(), "HP        " + opponent.getHp() + "/" + opponent.getMaxHp(), 30));
        System.out.println(GameLogic.formatColumns("Stamina   " + player.getStamina() + "/" + player.getMaxStamina(), "Stamina   " + opponent.getStamina() + "/" + opponent.getMaxStamina(), 30));
        System.out.println();
    }

    static void selectAttack() {
        int[] choices = new int[3];
        String input = "";

        System.out.println();
        System.out.println("You're the first one to attack!");
        System.out.println("Select 3 combos:");
    
        for (int i = 0; i < attackOption.length; i++) {
            if(i < attackOption.length - 1){
                System.out.println((i + 1) + ") " + attackOption[i][0] + " - " + attackOption[i][1]);
            } else {
                System.out.println((i + 1) + ") " + attackOption[i][0]);
            }
        }

        for (int i = 0; i < comboOption.length; i++) {
            System.out.println("\t\t- " + comboOption[i][0] + " - " + comboOption[i][1]);
        }
    
        System.out.print("-> ");
        while (true) {
            input = GameLogic.scan.nextLine();

            if (input.equals("5")) {
                if(player.getStamina() - 30 >= 0){
                    input = "567";
                    choices = new int[]{5, 6, 7}; 
                    break;
                } else {
                    System.out.println(player.getName() + " doesn't have enough stamina for this combo!");
                    System.out.println("You may use 3 Blocks as your combo to regain stamina");
                    System.out.println();
                    continue;
                }
            } else if (input.contains("5") || input.contains("6") || input.contains("7")) {
                System.out.println();
                System.out.println("You can use your special combo by entering '5'!");
                System.out.println("If you want to proceed with the combo, just enter '5'.");
                System.out.println();
                continue; 
            }

            if(isValidCombo(input, player.getStamina()) == 1){
                System.out.println("Please enter a valid combo (e.g., 123):");
                continue;
            } else if(isValidCombo(input, player.getStamina()) == 2) {
                System.out.println(player.getName() + " doesn't have enough stamina for this combo!");
                System.out.println("You may use 3 Blocks as your combo to regain stamina");
                System.out.println();
                continue;
            }
            break;
        }

        for (int i = 0; i < 3; i++) {
            choices[i] = Character.getNumericValue(input.charAt(i) - 1);
            opponentChoices[i] = rand.nextInt(7);
        }

        for (int i = 0; i < 3; i++) {
            if (opponentChoices[i] >= 4) {
                opponentChoices = new int[]{4, 5, 6}; 
                break;
            }
        }

        opponentValid(opponentChoices);

        System.out.println();
        System.out.println("You've selected:\t\tOpponent selected:");
        for(int i = 0; i < 3; i++){
            System.out.println(playerAttacks[choices[i]] + "     \t\t\t" + opponentAttacks[opponentChoices[i]]);
        }

        System.out.println();
        printFight(choices, opponentChoices);
    }

    static int isValidCombo(String input, int stamina) {
        if (input.length() != 3) {
            return 1;
        }

        int tempStamina = stamina;
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c) || c < '1' || c > '7') {
                return 1;
            }
            
            int move = Character.getNumericValue(c);
            int staminaCost;
            
            switch (move) {
                case 1: // Jab
                    staminaCost = 5;
                    break;
                case 2: // Hook
                    staminaCost = 7;
                    break;
                case 3: // Block (assuming no stamina cost)
                    staminaCost = 0;
                    break;
                case 4:  // Uppercut 
                    staminaCost = 10;
                    break;
                case 5:
                    staminaCost = 9;
                    break;
                case 6:
                    staminaCost = 10;
                    break;
                case 7:
                    staminaCost = 14;
                    break;
                default:
                    return 1;
            }

            if (tempStamina - staminaCost < 0) {
                return 2;
            }
            tempStamina -= staminaCost;
        }
        return 0;
    }

    static void opponentValid(int[] opponentChoice) {
        int tempStamina = opponent.getStamina();
        
        for (int i = 0; i < opponentChoice.length; i++) {
            int staminaCost = 0;
            boolean validChoice = false;

            while (!validChoice) {
                switch (opponentChoice[i]) {
                        case 1: // Jab
                        staminaCost = 5;
                        break;
                    case 2: // Hook
                        staminaCost = 7;
                        break;
                    case 3: // Block (assuming no stamina cost)
                        staminaCost = 0;
                        break;
                    case 4:  // Uppercut 
                        staminaCost = 10;
                        break;
                    case 5:
                        staminaCost = 9;
                        break;
                    case 6:
                        staminaCost = 10;
                        break;
                    case 7:
                        staminaCost = 14;
                        break;
                }
    
                if (tempStamina - staminaCost >= 0) {
                    validChoice = true;
                } else {
                    if(rand.nextDouble() > 0.6)
                        opponentChoice[i] = rand.nextInt(7);
                        if (opponentChoice[i] >= 4) {
                            if(tempStamina - 30 < 0){
                                opponentChoices = new int[]{2, 2, 2}; 
                                return;
                            } else {
                                opponentChoices = new int[]{4, 5, 6}; 
                                return;
                            }
                        }
                    else 
                        opponentChoice[i] = 2;
                }
            }
    
            tempStamina -= staminaCost;
        }
    }

    static void printFight(int[] choices, int[] opponentChoices){
        for(int i = 0; i < 3; i++){
            int countered = isCounter(opponentChoices[i], choices[i]);
            if(countered == 1){
                System.out.println(player.getName() + " throws a " + playerAttacks[choices[i]] + " to " + opponent.getName());
                vsTetteh.playerSuccessAction(choices[i], opponentChoices[i], false);
                vsTetteh.opponentFailedAction(opponentChoices[i]);
            } else if(countered == 2){
                System.out.println(player.getName() + " throws a " + playerAttacks[choices[i]] + " to " + opponent.getName());
                vsTetteh.opponentSuccessAction(opponentChoices[i], choices[i], false);
                vsTetteh.playerFailedAction(choices[i]);
            } else {
                System.out.println(player.getName() + " throws a " + playerAttacks[choices[i]] + " to " + opponent.getName());
                System.out.println(opponent.getName() + " draws " + player.getName() + " with " + opponentAttacks[choices[i]]);
                vsTetteh.drawAction(choices[i], opponentChoices[i]);
            }
            if(player.getHp() <= 0 || opponent.getHp() <= 0){
                return;
            }
            GameLogic.printSeparator(50);
        }
    }

    static int isCounter(int opponentMove, int playerMove) {
        switch (opponentMove) {
            case 0:
                if(playerMove == 1 || playerMove == 5 || playerMove == 4) return 1;
                if(playerMove == 3 || playerMove == 6) return 2;
                break;
            case 1:
                if(playerMove == 2 || playerMove == 6 || playerMove == 5) return 1;
                if(playerMove == 0 || playerMove == 4) return 2;
                break;
            case 2:
                if(playerMove == 3 || playerMove == 4) return 1;
                if(playerMove == 1 || playerMove == 6 || playerMove == 5) return 2;
                break;
            case 3:
                if(playerMove == 0 || playerMove == 4 || playerMove == 6) return 1;
                if(playerMove == 2) return 2;
                break;
            case 4:
                if(playerMove == 2) return 1;
                if(playerMove == 0 || playerMove == 3) return 2;
                break;
            case 5:
                if(playerMove == 0) return 1;
                if(playerMove == 1 || playerMove == 2) return 2;
                break;
            case 6:
                if(playerMove == 3) return 1;
                if(playerMove == 1 || playerMove == 0) return 2;
                break;
            default:
                break;
        }
        return 0;
    }

    private static void winnerReward() {
        if(playerProgress.getPlayerWins() != 3){
            System.out.println(); 
            GameLogic.printSeparator(40);
            System.out.println(); 
            System.out.println("Congratulations! You've won the match!");
        }
    }
}