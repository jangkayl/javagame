package world1;

import GlobalClasses.Player;
import GlobalClasses.PlayerProgress;
import GlobalClasses.Shop;
import GlobalClasses.StreetFighter;
import world1.TrainInGym.PabloUrbanGym;

public class UrbanStory {
    private static Player player = GameLogic.player;
    private static PlayerProgress playerProgress = GameLogic.playerProgress;
    private static Shop shop = GameLogic.shop;
    private static String[] array = {"Jab", "Hook", "Block", "Uppercut"};

    static void reply(int num, String reply){
        System.out.print(GameLogic.centerText(50, "Select " + num + " to " + reply));
        System.out.print(GameLogic.centerText(50, "(" + num + ") - " + reply));
    }

    static void reply(String reply1, String reply2){
        System.out.print(GameLogic.centerText(20,"Select what to reply:"));
        System.out.print(GameLogic.centerText(20,"(1) \"" + reply1 + "\""));
        System.out.print(GameLogic.centerText(20,"(2) \"" + reply2 + "\""));
    }

    static void space(int separator){
        System.out.println();
        GameLogic.printSeparator(separator);
        System.out.println();
    }

    static int isCounter(int fredMove, int playerMove) {
        switch (fredMove) {
            case 1: 
                if (playerMove == 2) {
                    return 1;
                } else if (playerMove == 4) {
                    return 2; 
                } else {
                    return 0; 
                }
            case 2:
                if (playerMove == 3) {
                    return 1;
                } else if (playerMove == 1) {
                    return 2; 
                } else {
                    return 0; 
                }
            case 3: 
                if (playerMove == 4) {
                    return 1;
                } else if (playerMove == 2) {
                    return 2; 
                } else {
                    return 0; 
                }
            case 4:
                if (playerMove == 1) {
                    return 1;
                } else if (playerMove == 3) {
                    return 2; 
                } else {
                    return 0; 
                }
            default:
                return -1;
        }
    }

    static int punch(){
        System.out.print(GameLogic.orangeText);
        String prompt = "Select the counter punch:";
        System.out.print(GameLogic.centerText(20, prompt));
        for(int i = 1; i <= 4; i++){
            String option = " (" + i + ")\"" + array[i-1] + "\"";
            System.out.print(GameLogic.centerText(20, option));
        }
        int choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 4);
        return choice;
    }

    public static void printUrban(){
        System.out.println();
        String content ="Welcome to the Urban Gym, a tough, inner-city training ground.\n" +
                        "Here, local fighters sharpen their skills and compete in underground matches.\n\n" +
                        "Under the watchful eye of Coach Fred, you train rigorously to hone your skills.\n" +
                        "Every punch, every dodge, and every strategy counts as you prepare for the battles ahead.\n" +
                        "Once you're ready, prove your strength by winning a tournament.\n" +
                        "Victory will grant you the opportunity to advance to the next world. Keep striving for greatness!\n";

        String centeredContent = GameLogic.centerText(100, content);
        System.out.println(centeredContent);
        System.out.println();
    }

    public static void printTraining(String name){
        int choice = 0;

        urbanTraining1(name);
        choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 2);
        if(choice == 1) response(name);
        else if(choice == 2) response2(name);
        GameLogic.pressAnything();

        urbanTraining2();
        choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 2);
        if(choice == 1) response3();
        else response4();

        GameLogic.pressAnything();
        urbanTraining3(name);
        urbanTraining4();
        choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 1);
        if(choice == 1) response5();

        choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 2, 2);
        if(choice == 2) response6();

        choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 3, 3);
        if(choice == 3) response7();

        choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 4, 4);
        if(choice == 4){
            response8(name);
        }

        urbanTraining5(name);
        train(name);
    }

    static void urbanTraining1(String name) {
        String message = "Fred: \"Welcome to the Urban Gym, " + name + ". It may not look like much, but this place has\n"
                + "made champions. Today, it's gonna make you better. But first...\"\n\n"
                + "( He pauses, giving you a serious look. )\n\n"
                + "Fred: \"Training here isn't just about hitting harder. It's about discipline, control,\n"
                + "and endurance. Think you've got it?\"\n\n";

        System.out.print(GameLogic.centerBox(message, 90));

        System.out.println("\n");
        System.out.print(GameLogic.orangeText);
        reply("I'm ready. Let's do this!", "Not sure... Never trained in a real gym.");
        System.out.print(GameLogic.reset);
        System.out.println();
    }

    static void response(String name) {
        System.out.println("\n");
        String responseText = "( Fred nods with approval. )\n\n"
                + "Fred: \"That's the spirit. Confidence is good, \nbut keep your head in the game.\"\n\n";

        System.out.print(GameLogic.redText);
        System.out.println(GameLogic.leftBox(responseText, 50));
        System.out.print(GameLogic.reset);

        System.out.println("\n");

        System.out.print(GameLogic.blueText);
        String playerResponse = name + ": \"Let's go!\"";
        System.out.println(GameLogic.rightBox(playerResponse, 40));
        System.out.print(GameLogic.reset);

        System.out.println();
    }

    static void response2(String name) {
        System.out.println("\n");
        String message = "( Fred claps a hand on your shoulder, his grip firm but reassuring. )\n\n"
                + "Fred: \"Don't worry. Everyone starts somewhere, kid. You've got the heart for this,\n and that's what counts."
                + "The rest? I'll teach you. \nKeep your head in the game, and let's see what you've got.\"\n\n";

        System.out.print(GameLogic.redText);
        System.out.println(GameLogic.leftBox(message, 100));
        System.out.print(GameLogic.reset);

        System.out.println("\n");
        
        System.out.print(GameLogic.blueText);
        String playerResponse = name + ": \"Thanks, Fred. I'll give it my all.\"\n";
        System.out.println(GameLogic.rightBox(playerResponse, 60));
        System.out.print(GameLogic.reset);

        System.out.println();
    }

    static void urbanTraining2(){
        System.out.println("\n\n");
        String message = "[ Inside the gym, you take in the smell of sweat and leather. Fred hands you old gloves. ]\n\n" +
                        "Fred: \"First things first. Let's see your stance. You've got power, \nbut power's no good without balance.\"" +
                        "\n\n ( Fred demonstrates the proper stance. )";

        System.out.print(GameLogic.centerBox(message, 95));

        System.out.println("\n");
        System.out.print(GameLogic.orangeText);
        reply("Got it. Let's do this!", "This feels a little awkward...");
        System.out.print(GameLogic.reset);
        System.out.println();
    }

    static void response3() {
        System.out.println("\n");
        String message = "Fred: \"Good. Now, let's see those jabs—quick, sharp, back to guard. Ready?\"\n";
        System.out.print(GameLogic.centerBox(message, 80));
        System.out.println("\n");
    }
    
    static void response4() {
        System.out.println("\n");
        String message = "Fred: \"It always feels weird at first, but trust me, it'll become second nature.\n"
                        + "Just keep practicing, and it'll click.\"\n";
        System.out.print(GameLogic.centerBox(message, 85));
        System.out.println("\n");
    }

    static void urbanTraining3(String name) {
        System.out.println("\n");
        String message = "[ Fred takes you through a series of drills: jabs, hooks, uppercuts, and footwork. ]\n\n"
                + "Fred: \"You've got potential, " + name + ". But remember, boxing isn't just about strength. \nIt's about outsmarting your"
                + "opponent. When you're in the ring, \nyou have to think two steps ahead. Now, let's see what you've really got.\"\n\n"
                + "[ " + name + " nods, ready for the challenge. ]\n\n"
                + "Fred: \"Alright, let's start with the basics. Boxing is a dance of timing, precision, \nand knowing when to use the right move."
                + " Every move has a counter, \nand knowing that will give you the edge. Here's how it works...\"\n";
        System.out.print(GameLogic.centerBox(message, 95));
        System.out.println("\n");
        GameLogic.pressAnything();
        System.out.println("\n");
    }

    static void urbanTraining4() {
        System.out.println("\n");
        String message = "( Fred throws a quick Jab )\n\n"
                + "Fred: \"This is your Jab. It's fast and hits first. If your opponent tries a big Uppercut, \nyou can beat them to the punch."
                + " So remember, a Jab will always stop an Uppercut in its tracks.\""
                + "\n\n[ Jab > Uppercut ]\n\n"
                + "Fred: \"Now, after I demonstrate, I want you to follow my lead.\"\n";
        System.out.print(GameLogic.centerBox(message, 100));
        System.out.println("\n");
        System.out.print(GameLogic.orangeText);

        reply(1, "Jab");
        System.out.print(GameLogic.reset);
        System.out.println();
    }
    
    static void response5() {
        String message = "( Fred swings a wide Hook )\n\n"
                + "Fred: \"But watch out! A Hook is stronger than a Jab. When someone throws a Jab at you,\n"
                + "answer with a Hook to overpower them.\""
                + "\n\n[ Hook > Jab ]\n\n";
        System.out.print(GameLogic.centerBox(message, 100));
        System.out.println("\n");
        System.out.print(GameLogic.orangeText);
        reply(2, "Hook");
        System.out.print(GameLogic.reset);
        System.out.println();
    }

    static void response6() {
        String message = "( Fred pulls back and guards with a Block )\n\n"
                + "Fred: \"Now, a good Block can stop a lot of punches, especially a heavy Hook. If you see that coming,\n"
                + "get ready to Block. It'll protect you from a nasty hit.\""
                + "\n\n[ Block > Hook ]\n\n";
        System.out.print(GameLogic.centerBox(message, 105));
        System.out.println("\n");
        System.out.print(GameLogic.orangeText);
        reply(3, "Block");
        System.out.print(GameLogic.reset);
        System.out.println();
    }

    static void response7() {
        String message = "( Fred drops his guard and launches an Uppercut )\n\n"
                + "Fred: \"But don't get too comfortable behind that guard! A well-placed Uppercut can break through a Block.\n"
                + "If they're just standing there guarding, hit them hard with an Uppercut.\""
                + "\n\n[ Uppercut > Block ]\n\n";
        System.out.print(GameLogic.centerBox(message, 110));
        System.out.println("\n");
        System.out.print(GameLogic.orangeText);
        reply(4, "Uppercut");
        System.out.print(GameLogic.reset);
        System.out.println();
    }

    static void response8(String name) {
        String message = "( Fred takes a step back, his eyes sharp )\n\n"
                + "Fred: \"Remember, " + name + ", it's all about knowing what's coming and how to counter.\n"
                + "JAB stops UPPERCUT, HOOK overpowers JAB, BLOCK defends against HOOK, and UPPERCUT breaks through BLOCK.\n"
                + "Get these counters down, and you'll be ready for anything!\"\n";
        System.out.print(GameLogic.centerBox(message, 110));
        System.out.println("\n");
        GameLogic.pressAnything();
        System.out.println("\n");
    }

    static void urbanTraining5(String name) {
        String message = "( " + name + " smiles, absorbing the lesson )\n\n"
                + "Fred: \"Think before striking. Outsmart your opponent. Ready to test it out?\"\n";
        System.out.print(GameLogic.centerBox(message, 80));
        System.out.println("\n");
        GameLogic.pressAnything();
    }

    static void train(String name){
        int success = 0;
        int choice;

        String message = "Fred: \"Alright, let's put your skills to the test. Try to counter my next punch.\"\n";
        System.out.print(GameLogic.centerBox(message, 90));

        do {
            int randomNum = 0 + (int)(Math.random() * ((3 - 0) + 1));
            System.out.println("\n");

            String punchMessage = "";
            System.out.print(GameLogic.redText);
            if(randomNum == 2){
                punchMessage = "( Fred " + array[randomNum] + "s )";
            } else if(randomNum == 3){
                punchMessage = "( Fred throws an " + array[randomNum] + " )";
            } else {
                punchMessage = "( Fred throws a " + array[randomNum] + " ) ";
            }
            System.out.print(GameLogic.centerBox(punchMessage, 50));
            System.out.print(GameLogic.reset);

            System.out.println("\n");
            String successMessage = "Success: " + success + " / 5";
            
            System.out.print(GameLogic.greenText);
            System.out.print(GameLogic.centerText(20, successMessage));
            System.out.print(GameLogic.reset);
            System.out.println();
            choice = punch();
            int countered = isCounter(randomNum + 1, choice); // Check if the punch was countered

            System.out.println();
            GameLogic.printCenteredSeparator(30);
            System.out.println();

            System.out.println("\n");
            String responseMessage = "";
            System.out.print(GameLogic.greenText);
            if(countered == 1){
                responseMessage = "Great job!";
                success++;
            } else if(countered == 2){
                responseMessage = "No, you should try to counter punch it!";
            } else {
                responseMessage = "Not bad, but I wanna see some counter punches!";
            }
            System.out.print(GameLogic.centerBox(responseMessage, 50));

        } while(success < 5);

        System.out.println("\n");
        String finalMessage = "Fred: \"Good work, " + name + "! You've got some real potential. Remember, practice makes perfect.\n"
                + "I hope to see you again tomorrow for another session. Keep your guard up and stay sharp!\"";
        System.out.print(GameLogic.centerBox(finalMessage, 100));
        System.out.println("\n");

        System.out.print(GameLogic.reset);
        GameLogic.pressAnything();
    }

    public static void urbanTraining6(String name) {
        String message = "Fred: \"Good to see you back, " + name + ". You've made some progress, but there's still work to do.\"\n"
                + "I want you to know that training is important, but so is preparation. I've opened up a shop here\n"
                + "in the gym where you can buy gear and supplements. This will help boost your stats whether it's\n"
                + "your strength, speed, or resilience. Spend your winnings wisely.\"\n\n";

        System.out.print(GameLogic.centerBox(message, 110));
        System.out.println("\n");
        System.out.print(GameLogic.orangeText);
        String shopMessage = "Select 1 to go to Shop\n"
                            + "\t(1) - Shop\n";
        System.out.print(GameLogic.centerText(20, shopMessage));
    }

    public static void urbanTraining7() {
        String dialogue = "Fred: \"But before you start spending your hard-earned cash, I've got a task for you. \nConsider it a "
                + "challenge to prove you're ready to take the next step.\"\n"
                + "\n"
                + "( Fred leans in with a serious expression )\n"
                + "\n"
                + "Fred: \"Head over to the ringside and spar with one of our top fighters, Carlito 'The Phantom' Cortez. \n"
                + "Beat him, and I'll let you purchase some exclusive items in the shop only the best get access to. \n"
                + "It's not gonna be easy, but I know you've got it in you.\"\n"
                + "\n"
                + "( He pats the player on the shoulder and steps back )\n"
                + "\n"
                + "Fred: \"Go on, show me what you've got. And remember \"train hard, shop smart\".\n";

        System.out.println(GameLogic.centerBox(dialogue, 110));

        GameLogic.pressAnything();
        shop.showMenu();
    }

    public static boolean urbanTrainingLose(String name, String opponent) {
        System.out.println("\n");
        String message =
                "Fred: \"Ahhh, tough break there, " + name + ". " + opponent + " is a beast in the ring.\"\n\n" +
                        "( Fred steps forward, his expression calm yet encouraging )\n\n" +
                        "Fred: \"Listen, losses aren't the end. They're the stepping stones to real progress.\n" +
                        "Every time you fall, you get back up stronger.\"\n\n" +
                        "( He places a reassuring hand on your shoulder, a knowing smile on his face )\n\n" +
                        "Fred: \"You've got what it takes, I see that. So, how about another go? \nThe road to victory isn't easy," +
                        "but it's worth every fight. Remember this, \nStrength isn't in never falling, but in rising every single time.\"\n\n" +
                        "Fred: \"Do you want a rematch?\"\n";

        System.out.println(GameLogic.centerBox(message, 110));
        System.out.print(GameLogic.orangeText);
        System.out.print(GameLogic.centerText(20,"Select what to reply:"));
        System.out.print(GameLogic.centerText(20,"(1) Sure, im just warming up! "));
        System.out.print(GameLogic.centerText(20,"(2) Let me take a break first."));
        int choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 2);
        System.out.print(GameLogic.reset);
        if(choice == 1){
            if(playerProgress.getShopStage() < 1){
                GameLogic.shop.showMenu();
            } else {
                PabloUrbanGym pablo = new PabloUrbanGym(player);
                pablo.setPlayer(GameLogic.player);
                pablo.fightLoop();
            }
            return true;
        } else {
            return false;
        }
    }

    public static void urbanTraining8(String name){
        System.out.println("\n");
        String message = " [ You step out of the ring, sweat dripping from your brow.\n" +
                "The crowd of gym-goers cheer as you emerge victorious. ]\n" +
                "\n" +
                "[ Fred, your coach, claps you on the back with a big grin on his face. ]\n" +
                "\n" +
                "Fred: \"Well done, " + name + "! You showed some real grit out there, you're improving fast. But remember,\n" +
                "winning one sparring match is just the beginning. If you want to get stronger, you'll need to keep pushing.\"\n" +
                "\n" +
                "Fred: \"You can take a break, or if you're ready for more, we can keep training. What do you say?\"";
        System.out.print(GameLogic.centerBox(message, 110));
        System.out.println();
        System.out.print(GameLogic.orangeText);
        System.out.print(GameLogic.centerText(20,"Select what to do:"));
        System.out.print(GameLogic.centerText(20,"1) Yes, I wanna keep training!"));
        System.out.print(GameLogic.centerText(20,"2) I'll take a break first and check the Shop."));
        int choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 2);
        System.out.print(GameLogic.reset);
        if(choice == 1) {
            urbanTrainingCombo(name);
        } else {
            return;
        }
    }

    public static void urbanTrainingCombo(String name) {
        System.out.println("\n");
        String message = "Fred: \"Alright " + name + ", let's master 'The Body Breaker' combo!\"\n" +
                "\n" +
                "Fred: \"Step 1: Lead Body Shot to counter a Jab.\"\n" +
                "[ Fred demonstrates the Lead Body Shot. ]\n" +
                "\n" +
                "Fred: \"Step 2: Cross to the Ribs to counter a Hook.\"\n" +
                "[ Fred executes a precise Cross to the Ribs. ]\n" +
                "\n" +
                "Fred: \"Step 3: Finishing Uppercut to break through a Block.\"\n" +
                "[ Fred showcases the Finishing Uppercut. ]\n" +
                "\n" +
                "Fred: \"Got it? Now, give it a try! Afterward, let's spar and put those moves to the test!\"\n" +
                "\n";

        System.out.print(GameLogic.blueText);
        System.out.println(GameLogic.centerBox(message, 95));
        System.out.print(GameLogic.reset);
        System.out.println("\n");
        GameLogic.pressAnything();
        train2(name);
    }

    private static void train2(String name){
        String[] skill = {"Jab", "Hook", "Block"};
        String[] combo = {"Lead Body Shot", "Cross to the Ribs", "Finishing Uppercut"};
        int success = 0;
        int choice;

        System.out.println("\n");
        String message = "Fred: \"Alright, let's put your skills to the test. Try to counter my next punch.\"\n";
        System.out.print(GameLogic.centerBox(message, 90));
        do {
            int randomNum = 0 + (int)(Math.random() * ((2 - 0) + 1));
            System.out.println();

            String skillMessage = "";
            if(randomNum == 2){
                skillMessage = "( Fred " + skill[randomNum] + "s )";
            } else if(randomNum == 3){
                skillMessage = "( Fred throws an " + skill[randomNum] + " )";
            } else {
                skillMessage = "( Fred throws a " + skill[randomNum] + " ) ";
            }

            System.out.print(GameLogic.redText);
            System.out.println(GameLogic.centerBox(skillMessage, 50));
            System.out.print(GameLogic.reset);
            System.out.println();

            System.out.print(GameLogic.greenText);
            System.out.println(GameLogic.centerText(20, "Success: " + success + " / 5"));
            System.out.print(GameLogic.reset);

            System.out.print(GameLogic.orangeText);
            System.out.print(GameLogic.centerText(20, "Select the counter punch:"));
            for (int i = 1; i <= 3; i++) {
                System.out.print(GameLogic.centerText(20, "(" + i + ") \"" + combo[i-1] + "\""));
            }

            choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 3);

            System.out.println();
            GameLogic.printCenteredSeparator(30);
            System.out.println();

            String message2;
            System.out.print(GameLogic.greenText);
            if(isCounter2(randomNum + 1, choice)) {
                message2 = "Great job!\n";
                success++;
            } else {
                message2 = "No, you should try to counter punch it!\n";
            }
            System.out.println(GameLogic.centerBox(message2, 50));

        } while(success < 5);

        System.out.print(GameLogic.reset);
        System.out.println("\n");
        String message1 = "Fred:  \"Great job with the 'The Body Breaker' combo, " + name + "! Now it's time to put those skills to the test.\n" +
                "I've arranged a sparring match for you against one of the best, Pablo 'El Tigre' Martínez!\n" +
                "Pablo's fast and skilled, so this will be a true test of what you've learned. \nRemember to stay focused and use your combo!\"\n" +
                "\n" +
                "[ Fred gestures toward the sparring ring, where Pablo stands ready, his eyes sharp and confident. ]\n" +
                "\n" +
                "Fred: \"Step into the ring, and show Pablo what you've got!\"\n" +
                "\n" +
                "[ You take a deep breath, stepping into the ring to face Pablo 'El Tigre' Martínez, \neager to prove your skills... ]";

        System.out.println(GameLogic.centerBox(message1, 120));
        System.out.println("\n");
        GameLogic.pressAnything();

        PabloUrbanGym pablo = new PabloUrbanGym(player);
        pablo.setPlayer(GameLogic.player);
        pablo.fightLoop();
    }

    static boolean isCounter2(int fredMove, int playerMove) {
        switch (fredMove) {
            case 1: 
                if (playerMove == 1) return true;
                return false; 
            case 2:
                if (playerMove == 2) return true;
                return false; 
            case 3: 
                if (playerMove == 3) return true;
                return false; 
            default:
                return false;
        }
    }

    public static boolean inviteToTournament(String name) {
        GameLogic.clearConsole();
        System.out.println(GameLogic.centerBox(
                "Fred: \"Great work, " + name + "! Your progress is impressive, but there's a big tournament coming up.\n" +
                        "It's a tough challenge, but it could be your time to shine. If you're not ready, you can\n" +
                        "train with me some more to sharpen your skills and build your stats before deciding.\""
        ,100));
        System.out.print(GameLogic.reset);
        System.out.print(GameLogic.orangeText);
        reply("I'm ready for the tournament.", "I want to train more first.");
        int choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 2);
        System.out.print(GameLogic.reset);
        if (choice == 1) {
            GameLogic.enterTournament();
            return true;
        } else {
            GameLogic.clearConsole();
            System.out.print(GameLogic.centerBox("Fred: \"No sweat! Let's keep working on your skills.\"",60));
            playerProgress.setOpponentWins(3);
            System.out.print(GameLogic.reset);
            GameLogic.pressAnything();
            return false;
        }
    }

    public static boolean tournaLoseTraining(String name) {
        System.out.println("\n");
        String message = "Fred: \"The tournament was a tough test. Losing happens, but it's how we get stronger.\"\n" +
                "Let's train harder, gain more stats, and you'll be ready for the next challenge.\n" +
                "Ready to get back to work?\"";

        System.out.println(GameLogic.centerBox(message, 100));
        System.out.print(GameLogic.orangeText);
        reply("I'm in! Let's do this.", "I'll think about it, Fred. Not sure if I'm ready yet.");
        System.out.print(GameLogic.reset);
        int choice = GameLogic.readInt(GameLogic.centerText("", 97) + "-> ", 1, 2);
        if(choice == 1){
            return true;
        } 
        return false;
    }
    
    public static void tournaOpponentBackstory(StreetFighter opponent) {
        Tournament.showOpStats(opponent);
        System.out.print(GameLogic.orangeText);
        switch (opponent.getName()) {
            case "Rico Ramirez":
                System.out.println(GameLogic.centerText(20, "\n🔥Backstory: Introducing Rico \"El Tigre\" Ramirez, a fierce contender from Manila, inspired by his father's legacy as a former champion." +
                        "\nKnown for his explosive style and powerful uppercuts, Rico has quickly climbed the ranks of boxing, embodying resilience and determination." +
                        "\nAs he steps into the ring for this tournament, he carries the hopes of his community, ready to unleash the spirit of the tiger and claim" +
                        "\nhis place among the greats! Now, they seek to prove their strength in the tournament!"));
                System.out.print(GameLogic.reset);
                GameLogic.pressAnything();
                break;
            case "Oscar Lopez":
                System.out.println(GameLogic.centerText(20, "\n💪Backstory: Oscar \"El Jablo\" Lopez, from Cebu City, grew up in a hardworking family as the youngest of five. Inspired by local boxing matches," +
                        "\nhe honed his skills in underground fights, earning a reputation for his lightning-fast jabs and explosive combos. Driven by a desire to uplift" +
                        "\n his community and motivated by a friend's injury in the ring, Oscar turned pro. Now, as he enters the tournament, he's determined to prove" +
                        "\nhimself as a champion, ready to unleash his quick jab, powerful cross, and knockout power punch."));
                    System.out.print(GameLogic.reset);
                    GameLogic.pressAnything();
                break;
            case "Ishmael Tetteh":
                System.out.println();
                System.out.print(GameLogic.centerText(20, " - The Thunderous Finisher"));
                System.out.print(GameLogic.centerText(20, "🌟Backstory: Ishmael Tetteh, \"The Thunderous Finisher,\" is a 28-year-old Ghanaian-American middleweight boxer with 28 wins (24 by KO)." +
                        "\nInspired by Canelo Alvarez, he has mastered a signature combo—Right Uppercut, Left Hook, Right Cross—reflecting his journey from Accra to the boxing ring." +
                        "\n After a pivotal early loss, he trained under a retired champion, transforming into a powerful and agile fighter." +
                        "\nNow, as he prepares for a major tournament, Ishmael fights for victory and his community, ready to make his mark in the boxing world."));
                        System.out.print(GameLogic.reset);
                        GameLogic.pressAnything();
                break;
            default:
                System.out.println("No backstory available for this opponent.");
        }
    }

}

