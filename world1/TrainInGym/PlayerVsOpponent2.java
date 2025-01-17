package world1.TrainInGym;

import java.util.Random;

import world1.GameLogic;
import GlobalClasses.Player;
import GlobalClasses.PlayerProgress;
import GlobalClasses.StreetFighter;
import Skill.SkillsRegistry;

public abstract class PlayerVsOpponent2 {
    protected Random rand = new Random();
    private PlayerProgress playerProgress = GameLogic.playerProgress;
    private Player player;
    private SkillsRegistry skills = new SkillsRegistry();
    private StreetFighter opponent;
    private boolean playerDodged = false;
    private boolean opponentDodged = false;
    private int[] opponentChoices = new int[3];
    private String[][] attackOption = { {"Jab", "Damage: 10 | Stamina: -5      "}, 
                                        {"Hook", "Damage: 15 | Stamina: -7     "}, 
                                        {"Block", "Stamina: +5                 "}, 
                                        {"Uppercut", "Damage: 20 | Stamina: -10"},
                                        {"The Body Breaker", ""}};
    private String[][] comboOption = {  {"Lead Body Shot", "Damage: 15 | Stamina: -7     "},
                                        {"Cross to the Ribs", "Damage: 20 | Stamina: -9  "},
                                        {"Finishing Uppercut", "Damage: 25 | Stamina: -14"}};
    private String[] playerAttacks = {"Jab", "Hook", "Block", "Uppercut", "Lead Body Shot", "Cross to the Ribs", "Finishing Uppercut"};
    private String[] opponentAttacks;

    public PlayerVsOpponent2(Player player, String[] opponentAttacks, StreetFighter opponent) {
        this.player = player;
        this.opponentAttacks = opponentAttacks;
        this.opponent = opponent;
    }

    public abstract String getOpponentName();
    protected abstract void handleWin();
    protected abstract void handleLoss();
    protected abstract void winnerReward();

    public Player getPlayer(){
        return player;
    }

    public StreetFighter getOpponent(){
        return opponent;
    }

    public int[] getOpponentChoices(){
        return opponentChoices;
    }

    public void setOpponentChoices(int[] opponentChoices){
        this.opponentChoices = opponentChoices;
    }

    public void setOpponent(StreetFighter opponent) {
        this.opponent = opponent;
    }

    public String getPlayerAttackByChoice(int choice){
        return playerAttacks[choice];
    }

    public SkillsRegistry getSkills(){
        return skills;
    }

    private int isCounter(String opponentMove, String playerMove) {
        if(skills.getSkillByName(opponentMove).counters(playerMove))
            return 1;
        if(skills.getSkillByName(opponentMove).isEffectiveAgainst(playerMove))
            return 2;
        return 0;
    }
    
    public void fightLoop() {
        player.setStage(player.getStage());
        GameLogic.gameData.saveGame();
        player.setOpponent(opponent);
        GameLogic.clearConsole();
        System.out.print(GameLogic.greenText);
        System.out.println(GameLogic.centerBox("Round " + playerProgress.getRound(), 40));
        System.out.print(GameLogic.reset);
        System.out.print(GameLogic.redText);
        System.out.println(GameLogic.centerBox("You are fighting " + opponent.getName() + "!", 40));
        System.out.print(GameLogic.reset);
        printStats();
        while (player.getHp() > 0 && opponent.getHp() > 0) {
            selectAttack();
            printStats();
            if (player.getHp() <= 0) {
                handleLoss();
                return;
            } else if (opponent.getHp() <= 0) {
                handleWin();
                return;
            }
        }
        GameLogic.pressAnything();
    }

    private void selectAttack() {
        int[] choices = new int[3];

        System.out.print(GameLogic.orangeText);
        System.out.print(GameLogic.centerText(30,"You're the first one to attack!"));
    
        for (int i = 0; i < attackOption.length; i++) {
            String attackInfo;
            if(i < attackOption.length - 1){
                attackInfo = (i + 1) + ") " + attackOption[i][0] + " - " + attackOption[i][1];
            } else {
                attackInfo = (i + 1) + ") " + attackOption[i][0];
            }
            System.out.print(GameLogic.centerText(40, attackInfo));
        }

        for (int i = 0; i < comboOption.length; i++) {
            String attackInfo = (comboOption[i][0] + " - " + comboOption[i][1]);
            System.out.print(GameLogic.centerText(40, attackInfo));
        }
        System.out.print(GameLogic.centerText(30,"\n(0) Check " + opponent.getName() + "'s combo counters"));

        System.out.print(GameLogic.greenText);
        System.out.print(GameLogic.centerText(30,"\nSelect 3 combos:"));
        String input;
        
        System.out.print(GameLogic.orangeText);
        while (true) {
            input = GameLogic.readString(GameLogic.centerText("", 97) + "-> ");

            if(input.equals("0")){
                counterInfos(opponent.getName());
                GameLogic.pressAnything();
                GameLogic.clearConsole();
                System.out.print(GameLogic.greenText);
                System.out.println(GameLogic.centerBox("Round " + playerProgress.getRound(), 40));
                System.out.print(GameLogic.reset);
                System.out.print(GameLogic.redText);
                System.out.println(GameLogic.centerBox("You are fighting " + opponent.getName() + "!", 40));
                System.out.print(GameLogic.reset);
                return;
            }

            System.out.print(GameLogic.redText);
            if (input.equals("5")) {
                if(player.getStamina() - 30 >= 0){
                    input = "567";
                    choices = new int[]{5, 6, 7}; 
                    System.out.print(GameLogic.reset);
                    break;
                } else {
                    String message = getPlayer().getName() + " doesn't have enough stamina for this combo!\n" +
                        "You may use 3 Blocks as your combo to regain stamina";
                    System.out.println(GameLogic.centerBox(message, 60));
                    System.out.print(GameLogic.reset);
                    continue;
                }
            } else if (input.contains("5") || input.contains("6") || input.contains("7")) {
                System.out.println();
                String message = "You can use your special combo by entering '5'!\n" +
                        "If you want to proceed with the combo, just enter '5'.";
                System.out.println(GameLogic.centerBox(message, 60));
                System.out.print(GameLogic.reset);
                continue; 
            }

            if(isValidCombo(input, player.getStamina()) == 1){
                System.out.println(GameLogic.centerBox("Please enter a valid combo (e.g., 123):", 50));
                System.out.print(GameLogic.reset);
                continue;
            } else if(isValidCombo(input, player.getStamina()) == 2) {
                String message = player.getName() + " doesn't have enough stamina for this combo!\n" +
                                                "You may use 3 Blocks as your combo to regain stamina";
                System.out.println(GameLogic.centerBox(message, 60));
                System.out.print(GameLogic.reset);
                continue;
            }
            break;
        }

        for (int i = 0; i < 3; i++) {
            // Adjust the character input value correctly
            choices[i] = Character.getNumericValue(input.charAt(i)) - 1; // Use input directly
        
            // Generate opponentChoices with higher probability for 1 to 4
            int randomValue = rand.nextInt(10); // Generate a random number between 0 and 9
        
            // Higher probability for numbers 1 to 4
            if (randomValue < 8) { // 80% chance
                opponentChoices[i] = rand.nextInt(4); // 0, 1, 2, or 3 (which correspond to 1 to 4)
            } else { // 20% chance
                opponentChoices[i] = 4 + rand.nextInt(3); // 4, 5, or 6
            }
        }
        
        // Check for opponentChoices being >= 4
        for (int i = 0; i < 3; i++) {
            if (opponentChoices[i] >= 4) {
                opponentChoices = new int[]{4, 5, 6}; 
                break;
            }
        }

        opponentValid(opponentChoices);

        System.out.print(GameLogic.reset);
        System.out.println();
        System.out.println(GameLogic.centerText(50, GameLogic.printCenteredSeparator(50)));
        System.out.print(GameLogic.centerText(30, GameLogic.formatColumns("You've selected:", "Opponent selected:", 30)));

        for(int i = 0; i < 3; i++){
            String playerAttack = playerAttacks[choices[i]];
            String opponentAttack = opponentAttacks[opponentChoices[i]];

            String line =  GameLogic.formatColumns(playerAttack, opponentAttack, 30);
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + line);
        }

        System.out.println();
        printFight(choices, opponentChoices);
    }

    private void printFight(int[] choices, int[] opponentChoices) {
        System.out.print(GameLogic.centerText(50, GameLogic.printCenteredSeparator(50)));
        for(int i = 0; i < 3; i++){
            int countered = isCounter(opponentAttacks[opponentChoices[i]], playerAttacks[choices[i]]);
            String playerAttack = getPlayer().getName() + " throws a " + playerAttacks[choices[i]] + " to " + opponent.getName();

            if(countered == 1){
                System.out.print(GameLogic.greenText);
                System.out.print(GameLogic.centerText(50, playerAttack));
                System.out.print(GameLogic.reset);
                playerSuccessAction(choices[i], opponentChoices[i], false);
                opponentFailedAction(opponentAttacks[opponentChoices[i]]);
            } else if(countered == 2){
                System.out.print(GameLogic.greenText);
                System.out.print(GameLogic.centerText(50, playerAttack));
                System.out.print(GameLogic.reset);
                opponentSuccessAction(opponentChoices[i], choices[i], false);
                playerFailedAction(playerAttacks[choices[i]]);
            } else {
                System.out.print(GameLogic.greenText);
                System.out.print(GameLogic.centerText(50, playerAttack));
                System.out.print(GameLogic.redText);
                String opponentAttack = opponent.getName() + " draws " + getPlayer().getName() + " with " + opponentAttacks[opponentChoices[i]];
                System.out.print(GameLogic.centerText(50, opponentAttack));
                System.out.print(GameLogic.reset);
                drawAction(choices[i], opponentChoices[i]);
            }
            if(getPlayer().getHp() <= 0 || getOpponent().getHp() <= 0){
                return;
            }
            System.out.print(GameLogic.centerText(50, GameLogic.printCenteredSeparator(50)));
        }
    }

    private void opponentValid(int[] opponentChoice) {
        int tempStamina = getOpponent().getStamina();
        
        for (int i = 0; i < opponentChoice.length; i++) {
            int staminaCost = 0;
            boolean validChoice = false;

            while (!validChoice) {
                staminaCost = getSkills().getSkillByName(opponentAttacks[opponentChoice[i]]).getStaminaCost();
                if (tempStamina - staminaCost >= 0) {
                    validChoice = true;
                } else {
                    if(rand.nextDouble() > 0.3)
                        opponentChoice[i] = rand.nextInt(7);
                        if (opponentChoice[i] >= 4) {
                            if(tempStamina - 30 < 0){
                                int[] opChoices = {2, 2, 2};
                                setOpponentChoices(opChoices); 
                                return;
                            } else {
                                int[] opChoices = {4, 5, 6};
                                setOpponentChoices(opChoices);
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

    private int isValidCombo(String input, int stamina) {
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
            
            staminaCost = getSkills().getSkillByName(opponentAttacks[move-1]).getStaminaCost();

            if (tempStamina - staminaCost < 0) {
                return 2;
            }
            tempStamina -= staminaCost;
        }
        return 0;
    }

    private void playerSuccessAction(int choice, int opponentChoice, boolean isDraw) {
        double critChance = rand.nextDouble();
        double dodgeChance = rand.nextDouble();

        if (critChance < player.getCritChance() && choice != 2 && !isDraw && !opponentDodged) {
            player.setDamageSetter(player.getCritMultiplier());
            System.out.print(GameLogic.greenText);
            System.out.print(GameLogic.centerText(40,player.getName() + "'s " + playerAttacks[choice] + " hit the weak spot! CRITICAL HIT!"));
            System.out.print(GameLogic.reset);
        }

        if (dodgeChance < player.getDodgeChance() && opponentChoice != 2 && !isDraw) {
            playerDodged = true;
        }

        if (opponentDodged) {
            player.setDamageSetter(0);
            System.out.print(GameLogic.redText);
            System.out.print(GameLogic.centerText(40,opponent.getName() + " dodges " + player.getName() + "'s punch!"));
            System.out.print(GameLogic.reset);
        }

        player.useSkill(playerAttacks[choice]);

        player.setDamageSetter(1);
        opponentDodged = false;
    }

    private void playerFailedAction(String attack) {
        player.setStamina(player.getStamina() - skills.getSkillByName(attack).getStaminaCost());
    }

    private void opponentSuccessAction(int choice, int playerChoice, boolean isDraw) {
        double critChance = rand.nextDouble();
        double dodgeChance = rand.nextDouble();

        if (critChance < opponent.getCritChance() && choice != 2 && !isDraw) {
            opponent.setDamageSetter(opponent.getCritMultiplier());
            System.out.print(GameLogic.redText);
            System.out.print(GameLogic.centerText(40,opponent.getName() + "'s " + opponentAttacks[choice] + " hit the weak spot! CRITICAL HIT!"));
            System.out.print(GameLogic.reset);
        }

        if (dodgeChance < opponent.getDodgeChance() && playerChoice != 2 && !isDraw) {
            opponentDodged = true;
        }

        if (playerDodged) {
            opponent.setDamageSetter(0);
            System.out.print(GameLogic.greenText);
            System.out.print(GameLogic.centerText(40,player.getName() + " dodges " + opponent.getName() + "'s punch!"));
            System.out.print(GameLogic.reset);
        }

        opponentPerformAction(opponentAttacks[choice]);

        opponent.setDamageSetter(1);
        playerDodged = false;
    }

    private void opponentFailedAction(String attack) {
        opponent.setStamina(opponent.getStamina() - skills.getSkillByName(attack).getStaminaCost());
    }

    private void opponentPerformAction(String attack) {
        opponent.useSkill(attack);
        opponent.setDamageSetter(1);
        playerDodged = false;
    }

    private void drawAction(int choice, int opponentChoice) {
        player.setDamageSetter(0.5);
        playerSuccessAction(choice, opponentChoice, false);
        player.setDamageSetter(1);

        opponent.setDamageSetter(0.5);
        opponentSuccessAction(opponentChoice, choice, false);
        opponent.setDamageSetter(1);
    }

    protected void resetFighterStats() {
        player.setHp(player.getMaxHp());
        player.setStamina(player.getMaxStamina());
        opponent.setHp(opponent.getMaxHp());
        opponent.setStamina(opponent.getMaxStamina());
    }

    private void printStats(){
        System.out.println();        
        System.out.print(GameLogic.reset);
        String prompt = GameLogic.formatColumns("*"+ getPlayer().getName() +"*" , "*"+ opponent.getName()+"*", 30)
                        + "\n" + GameLogic.formatColumns("HP       " + getPlayer().getHp() + "/" + getPlayer().getMaxHp(), "HP       " + opponent.getHp() + "/" + opponent.getMaxHp(), 30)
                         + "\n" + GameLogic.formatColumns("Stamina    " + getPlayer().getStamina() + "/" + getPlayer().getMaxStamina(), "Stamina    " + opponent.getStamina() + "/" + opponent.getMaxStamina(), 30);
        System.out.print(GameLogic.centerBox(prompt, 55));
        System.out.println("\n");
    }

    private void counterInfos(String name){
        GameLogic.clearConsole();
        System.out.print(GameLogic.blueText);
        if(opponent.getName() == "Pablo Martinez"){
            System.out.print(GameLogic.centerBox("Pablo Martinez Combo Counter:",50));
            System.out.println();
            System.out.print(GameLogic.centerText(50,"(1) Jab to the Body < Uppercut"));
            System.out.print(GameLogic.centerText(50,"(2) Lead Hook < Jab"));
            System.out.print(GameLogic.centerText(50,"(3) Rear Uppercut < Block"));
        } else if(opponent.getName() == "Fred"){
            System.out.print(GameLogic.centerBox("Fred Combo Counter:",50));
            System.out.println();
            System.out.print(GameLogic.centerText(50,"(1) Jab to the Body < Uppercut"));
            System.out.print(GameLogic.centerText(50,"(2) Lead Hook < Jab"));
            System.out.print(GameLogic.centerText(50,"(3) Rear Uppercut < Block"));
        }
        System.out.print(GameLogic.reset);
    }
}
