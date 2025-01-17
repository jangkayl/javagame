package world3;

import world1.GameLogic;
import GlobalClasses.Player;
import GlobalClasses.PlayerProgress;
import Skill.SkillsRegistry;
import GlobalClasses.StreetFighter;
import world2.BoxerHints;
import world3.PassiveSkill.PassiveSkillRegistry;

import java.util.Random;

public abstract class SparFightLogic{
    private Random rand = new Random();
    private PlayerProgress playerProgress = GameLogic.playerProgress;
    private Player player;
    private StreetFighter opponent;
    private SkillsRegistry skills = new SkillsRegistry();
    private PassiveSkillRegistry playerPassiveSkills = new PassiveSkillRegistry();
    private PassiveSkillRegistry opponentPassiveSkills = new PassiveSkillRegistry();
    private boolean playerLowHp = false;
    private boolean oppoenentLowHp = false;
    private String playerPassive = "";
    private String opponentPassive = "";
    private boolean playerHasPassive = false;
    private boolean opponentHasPassive = false;
    private boolean playerDodged = false;
    private boolean opponentDodged = false;
    private static BoxerHints boxerHints;
    private int[] opponentChoices = new int[3];
    private static String[][] attackOption = {{"Jab", "Damage: 10 | Stamina: -5      "}, 
                                              {"Hook", "Damage: 15 | Stamina: -7     "}, 
                                              {"Block", "Stamina: +5                 "}, 
                                              {"Uppercut", "Damage: 20 | Stamina: -10"},
                                              {"The Body Breaker", ""}};
    private static String[][] comboOption = {   {"Lead Body Shot", "Damage: 15 | Stamina: -7     "},
                                                {"Cross to the Ribs", "Damage: 20 | Stamina: -9  "},
                                                {"Finishing Uppercut", "Damage: 25 | Stamina: -14"}};
    private static String[][] passiveSkill = {  {"Flow State", "100% Dodge Chance, blocks all damage next 3 turns.    "},
                                                {"Adrenaline Rush", "Boost your Crit Chance by 100% next 3 turns.     "},
                                                {"Sixth Sense", "Reveals the opponent's next 3 moves for next 2 turns."}};
    public static String[] playerAttacks = {"Jab", "Hook", "Block", "Uppercut", "Lead Body Shot", "Cross to the Ribs", "Finishing Uppercut", "Flow State", "Adrenaline Rush", "Sixth Sense"};
    private String[] opponentAttacks;

    public SparFightLogic(Player player, String[] opponentAttacks, StreetFighter opponent) {
        this.player = player;
        this.opponentAttacks = opponentAttacks;
        this.opponent = opponent;
    }

    public abstract String getOpponentName();
    protected abstract void handleLoss();
    protected abstract void handleWin();

    public Player getPlayer(){
        return player;
    }

    public StreetFighter getOpponent(){
        return opponent;
    }

    public PlayerProgress getPlayerProgress(){
        return playerProgress;
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
        if(skills.getSkillByName(opponentMove).isEffectiveAgainst(playerMove))
            return 2;
        if(skills.getSkillByName(opponentMove).counters(playerMove))
            return 1;
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
                System.out.println();
                System.out.print(GameLogic.redText);
                System.out.println(GameLogic.centerBox(getPlayer().getName() + " is knocked out! " + opponent.getName() + " wins!", 60));
                System.out.print(GameLogic.reset);
                handleLoss();
                return;
            } else if (opponent.getHp() <= 0) {
                System.out.println();
                System.out.print(GameLogic.greenText);
                System.out.println(GameLogic.centerBox(opponent.getName() + " is knocked out! " + getPlayer().getName() + " wins!", 60));
                System.out.print(GameLogic.reset);
                handleWin();
                return;
            }
        }
        GameLogic.pressAnything();
    }

    private void selectAttack() {
        int[] choices = new int[3];
        String input = "";
        boxerHints = new BoxerHints();

        if(opponentHasPassive){
            opponentActivatePassive();
        }

        // Opponent selects a move
        for (int i = 0; i < 3; i++) {
            // Generate opponentChoices with higher probability for 1 to 10
            int randomValue = rand.nextInt(10); // Generate a random number between 0 and 9
        
            // Higher probability for numbers 1 to 4
            if (randomValue < 8) { // 80% chance
                opponentChoices[i] = rand.nextInt(4); // 0, 1, 2, or 3 (which correspond to 1 to 4)
            } else { // 20% chance
                opponentChoices[i] = 4 + rand.nextInt(3); // 4, 5, or 6
            }
        }
        
        for (int i = 0; i < 3; i++) {
            if (opponentChoices[i] >= 4 && opponentChoices[i] <= 6) {
                opponentChoices = new int[]{4, 5, 6}; 
                break;
            }
        }

        opponentValid(opponentChoices);

        // Player selects a move
        System.out.print(GameLogic.blueText);
        if(playerPassive == "Sixth Sense"){
            System.out.print(GameLogic.blueText);
            System.out.print(GameLogic.centerText(30, ("~ ~ I sense a pattern forming... ~ ~")));
            for(int i = 0; i < 3; i++){
                System.out.print(GameLogic.centerText(20, opponentAttacks[opponentChoices[i]]));
            }
            System.out.print(GameLogic.reset);
            System.out.println();
        } else {
            System.out.println(GameLogic.centerText(30, ("~ ~ " + boxerHints.getRandomHint(opponentAttacks[opponentChoices[0]]) + " ~ ~")));
        }
        System.out.print(GameLogic.reset);

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

        System.out.print(GameLogic.centerText(20, "Passive: " + (playerHasPassive ? "✅" : "❌")));
        for (int i = 0; i < passiveSkill.length; i++) {
            System.out.print(GameLogic.centerText(40, ((i + 6) + ") " + passiveSkill[i][0] + " - " + passiveSkill[i][1])));
        }
        
        System.out.print(GameLogic.centerText(30,"\n(0) Check " + opponent.getName() + "'s combo counters"));

        System.out.print(GameLogic.greenText);
        System.out.print(GameLogic.centerText(30,"\nSelect 3 combos:"));
        System.out.print(GameLogic.reset);
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
            } else if (input.contains("5")) {
                System.out.println();
                String message = "You can use your special combo by entering '5'!\n" +
                        "If you want to proceed with the combo, just enter '5'.";
                System.out.println(GameLogic.centerBox(message, 60));
                System.out.print(GameLogic.reset);
                continue; 
            }
            
            System.out.print(GameLogic.redText);
            if (input.equals("6") || input.equals("7") || input.equals("8")) {
                if(playerHasPassive){
                    System.out.print(GameLogic.blueText);
                    playerActivatePassive(input);
                    if(playerPassive == "Sixth Sense" && opponentPassive != "Sixth Sense"){
                        System.out.print(GameLogic.centerText(30, ("~ ~ I sense a pattern forming... ~ ~")));
                        for(int i = 0; i < 3; i++){
                            System.out.print(GameLogic.centerText(20, opponentAttacks[opponentChoices[i]]));
                        }
                    }
                    System.out.print(GameLogic.reset);
                    continue;
                } else if(playerPassive != "") {
                    String message = "Your passive skill, " + playerPassive + ", is already active! You must attack first before activating it again.";
                    System.out.println(GameLogic.centerBox(message, 110));
                    System.out.print(GameLogic.reset);
                    continue;
                } else {
                    String message = getPlayer().getName() + " needs 3 successful consecutive hits to activate this combo!\n" +
                        "Try to land more hits before using your passive skill.";
                    System.out.println(GameLogic.centerBox(message, 70));
                    System.out.print(GameLogic.reset);
                    continue;
                }
            } else if (input.contains("6") || input.contains("7") || input.contains("8")) {
                System.out.println();
                String message = "You can use your passive skill by entering '6' | '7' | '8'!\n" +
                        "If you want to proceed, just enter '6' | '7' | '8'.";
                System.out.println(GameLogic.centerBox(message, 65));
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
            choices[i] = Character.getNumericValue(input.charAt(i)) - 1; 
        }

        if(opponentPassive == "Sixth Sense"){
            for(int i = 0; i < 3; i++){
                if(choices[i] < 4){
                    opponentChoices[i] = choices[i] + 1;
                }
            }
            opponentValid(opponentChoices);
        }

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
        int playerStreak = 0, opponentStreak = 0;

        System.out.print(GameLogic.centerText(50, GameLogic.printCenteredSeparator(50)));

        for(int i = 0; i < 3; i++){
            if(playerPassive == "Flow State") playerDodged = true;
            else playerDodged = false;

            if(opponentPassive == "Flow State") opponentDodged = true;
            else opponentDodged = false;

            int countered = isCounter(opponentAttacks[opponentChoices[i]], playerAttacks[choices[i]]);
            System.out.print(GameLogic.greenText);
            String playerAttack = getPlayer().getName() + " throws a " + playerAttacks[choices[i]] + " to " + opponent.getName();
            System.out.print(GameLogic.reset);

            if(countered == 1){
                if(playerPassive != "Sixth Sense"){
                    playerStreak++;
                }
                System.out.print(GameLogic.greenText);
                System.out.print(GameLogic.centerText(50, playerAttack));
                System.out.print(GameLogic.reset);
                playerSuccessAction(choices[i], opponentChoices[i], false);
                opponentFailedAction(opponentAttacks[opponentChoices[i]]);
            } else if(countered == 2){
                opponentStreak++;
                System.out.print(GameLogic.greenText);
                System.out.print(GameLogic.centerText(50, playerAttack));
                System.out.print(GameLogic.reset);
                opponentSuccessAction(opponentChoices[i], choices[i], false);
                playerFailedAction(playerAttacks[choices[i]]);
            } else {
                System.out.print(GameLogic.greenText);
                System.out.print(GameLogic.centerText(50, playerAttack));
                System.out.print(GameLogic.reset);
                String opponentAttack = opponent.getName() + " draws " + getPlayer().getName() + " with " + opponentAttacks[opponentChoices[i]];
                System.out.print(GameLogic.redText);
                System.out.print(GameLogic.centerText(50, opponentAttack));
                System.out.print(GameLogic.reset);
                drawAction(choices[i], opponentChoices[i]);
            }

            if(getPlayer().getHp() <= 0 || getOpponent().getHp() <= 0){
                return;
            }

            System.out.print(GameLogic.centerText(50, GameLogic.printCenteredSeparator(50)));
        }

        if(playerStreak == 3 && playerPassive == ""){
            playerHasPassive = true;
        }

        if(opponentStreak == 3 && opponentPassive == ""){
            opponentHasPassive = true;
        }
        
        if(playerPassive != ""){
            playerDeactivatePassive();
        }

        if(opponentPassive != ""){
            opponentDeactivatePassive();
        }

        checkHps();
    }

    private void playerActivatePassive(String input){
        int num = Character.getNumericValue(input.charAt(0)); 
        playerPassive = playerAttacks[num + 1];
        System.out.print(GameLogic.greenText);
        playerPassiveSkills.getSkillByName(playerPassive).activatePassive(player.getName());
        System.out.print(GameLogic.reset);
        playerHasPassive = false;
    }

    private void playerDeactivatePassive(){
        if(!playerHasPassive){
            System.out.print(GameLogic.greenText);
            playerPassiveSkills.getSkillByName(playerPassive).deactivatePassive(player.getName());
            System.out.print(GameLogic.reset);
            if(playerPassiveSkills.getSkillByName(playerPassive).getRoundActive() == 0){
                playerPassiveSkills.getSkillByName(playerPassive).setRoundActive(3);
                playerPassive = "";
            }
        }
    }

    private void opponentActivatePassive(){
        int randomNumber = rand.nextInt(3) + 7;
        opponentPassive = opponentAttacks[randomNumber];
        System.out.print(GameLogic.redText);
        opponentPassiveSkills.getSkillByName(opponentPassive).activatePassive(opponent.getName());
        System.out.print(GameLogic.reset);
        opponentHasPassive = false;
    }

    private void opponentDeactivatePassive(){
        if(!opponentHasPassive){
        System.out.print(GameLogic.redText);
            System.out.print(GameLogic.redText);
            opponentPassiveSkills.getSkillByName(opponentPassive).deactivatePassive(opponent.getName());
            System.out.print(GameLogic.reset);
            if(opponentPassiveSkills.getSkillByName(opponentPassive).getRoundActive() == 0){
                opponentPassiveSkills.getSkillByName(opponentPassive).setRoundActive(3);
                opponentPassive = "";
            }
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
            if (!Character.isDigit(c) || c < '1' || c > '9') {
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

        if(playerPassive == "Adrenaline Rush") critChance = 0;
        else critChance = rand.nextDouble();

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

        if(opponentPassive == "Adrenaline Rush") critChance = 0;
        else critChance = rand.nextDouble();

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
        String prompt = GameLogic.formatColumns("*"+ getPlayer().getName() +"*" , "*"+ opponent.getName()+"*", 30)
                        + "\n" + GameLogic.formatColumns("HP       " + getPlayer().getHp() + "/" + getPlayer().getMaxHp(), "HP       " + opponent.getHp() + "/" + opponent.getMaxHp(), 30)
                         + "\n" + GameLogic.formatColumns("Stamina   " + getPlayer().getStamina() + "/" + getPlayer().getMaxStamina(), "Stamina   " + opponent.getStamina() + "/" + opponent.getMaxStamina(), 30);
        System.out.print(GameLogic.centerBox(prompt, 55));
        System.out.println("\n");
    }

    private void checkHps(){
        if(getPlayer().getHp() <= getPlayer().getMaxHp() * .2 && !playerLowHp && playerPassive == ""){
            playerLowHp = true;
            playerHasPassive = true;
        } 
        if(getOpponent().getHp() <= getOpponent().getMaxHp() * .2 && !oppoenentLowHp && opponentPassive == ""){
            oppoenentLowHp = true;
            opponentHasPassive = true;
        }
    }
    
    protected void winnerReward(){
        if(playerProgress.getPlayerWins() != 3){
            System.out.println();
            System.out.print(GameLogic.greenText);
            System.out.print(GameLogic.centerBox("Congratulations! You've won the match!", 50));
            System.out.print(GameLogic.reset);
        }
    }

    protected void winnerRewardPoints(){
        System.out.println();
        player.setPlayerPoints(player.getPlayerPoints() + 300);
        String message = "Congratulations! You've won the match!\n\n" +
            "You have been given 300 points.\n\n" +
            "You now have " + player.getPlayerPoints() + " points.\n\n" +
            "Visit the shop and use your points to buy items.\n";

        System.out.print(GameLogic.greenText);
        System.out.println(GameLogic.centerBox(message, 90));
        System.out.print(GameLogic.reset);
    }

    protected void addStats(int choice){
        if(choice == 1){
            double hpMultiplier = 1 + 0.15;
            int maxHp = (int)Math.ceil(player.getMaxHp() * hpMultiplier);
            player.setHp(maxHp);
            player.setMaxHp(maxHp);
        } else if(choice == 2){
            double staminaMultiplier = 1 + 0.15;
            int maxStamina = (int)Math.ceil(player.getMaxStamina() * staminaMultiplier);
            player.setStamina(maxStamina);
            player.setMaxStamina(maxStamina);
        } else if(choice == 3){
            double newCrit = player.getCritChance() + 0.05; 
            player.setCritChance(newCrit);
        } else if(choice == 4){
            double newDodge = player.getDodgeChance() + 0.05;
            player.setDodgeChance(newDodge);
        } else if(choice == 5){
            double newMulti = player.getCritMultiplier() + 0.05;
            player.setCritMultiplier(newMulti);
        }
    }

    private void counterInfos(String name){
        GameLogic.clearConsole();
        System.out.print(GameLogic.blueText);
        if(opponent.getName() == "Junjun Arcega"){
            System.out.print(GameLogic.centerBox("Junjun Arcega Combo Counter:",50));
            System.out.println();
            System.out.print(GameLogic.centerText(50,"(1) Right Uppercut < Block"));
            System.out.print(GameLogic.centerText(50,"(2) Left Hook < Jab"));
            System.out.print(GameLogic.centerText(50,"(3) Right Cross < Uppercut"));
        } else if(opponent.getName() == "Kargado Magsilos"){
            System.out.print(GameLogic.centerBox("Kargado Magsilos Combo Counter:",50));
            System.out.println();
            System.out.print(GameLogic.centerText(50,"(1) Cross < Uppercut"));
            System.out.print(GameLogic.centerText(50,"(2) Rear Uppercut < Block"));
            System.out.print(GameLogic.centerText(50,"(3) Lead Hook < Jab"));
        } else if(opponent.getName() == "Manual Macuez"){
            System.out.print(GameLogic.centerBox("Manual Macuez Combo Counter:",50));
            System.out.println();
            System.out.print(GameLogic.centerText(50,"(1) Cross < Uppercut"));
            System.out.print(GameLogic.centerText(50,"(2) Rear Uppercut < Block"));
            System.out.print(GameLogic.centerText(50,"(3) Lead Hook < Jab"));
        } else if(opponent.getName() == "Mani Pakyaw"){
            System.out.print(GameLogic.centerBox("Mani Pakyaw Combo Counter:",50));
            System.out.println();
            System.out.print(GameLogic.centerText(50,"(1) Quick Jab < Uppercut"));
            System.out.print(GameLogic.centerText(50,"(2) Cross < Uppercut"));
            System.out.print(GameLogic.centerText(50,"(3) Power Punch < Hook"));
        } else if(opponent.getName() == "May Welder"){
            System.out.print(GameLogic.centerBox("May Welder Combo Counter:",50));
            System.out.println();
            System.out.print(GameLogic.centerText(50,"(1) Right Uppercut < Block"));
            System.out.print(GameLogic.centerText(50,"(2) Left Hook < Jab"));
            System.out.print(GameLogic.centerText(50,"(3) Right Cross < Uppercut"));
        }
        System.out.print(GameLogic.reset);
    }
}
