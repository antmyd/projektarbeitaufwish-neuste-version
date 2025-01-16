package model;

import java.util.Random;
import java.util.Scanner;

public class Raid {
    private Superhero[] heroes; // Liste der Superhelden
    private int heroCount; // Anzahl der Superhelden

    // Konstruktor, um Superhelden zu initialisieren
    public Raid(Superhero[] heroes) {
        this.heroes = heroes;
        this.heroCount = heroes.length;
    }

    // Raubzug starten
    public void startRaid() {
        if (heroCount == 0) {
            System.out.println("Keine Superhelden verfügbar. Der Raubzug kann nicht starten!");
            return;
        }

        // Superhelden anzeigen
        System.out.println("Der Raubzug beginnt mit den folgenden Helden:");
        for (int i = 0; i < heroCount; i++) {
            System.out.println("- " + heroes[i].getName());
        }

        boolean raidActive = true;
        Scanner sc = new Scanner(System.in);
        while (raidActive) {
            System.out.println("=== RAID MENU ===");
            System.out.println();
            System.out.println("(1) Erforschen");
            System.out.println("(2) Tanzen");
            System.out.println("(3) Raubzug beenden");
            System.out.print("Wähle eine Option: ");
            int choice = sc.nextInt();

            // Option auswählen
            switch (choice) {
                case 1:
                    explore();
                    break;
                case 2:
                    dance(); // Tanzmethode verwenden
                    break;
                case 3:
                    raidActive = false;
                    System.out.println("Raubzug beendet.");
                    break;
                default:
                    System.out.println("Ungültige Wahl. Versuche es nochmal.");
            }
        }
    }

    // Erforschen der Gegend
    private void explore() {
        Random rand = new Random();
        Villain encounteredVillain = createRandomVillain(); // Zufälliger Gegner

        System.out.println("\nDu erforschst die feindliche Zentrale...");
        if (rand.nextBoolean()) {
            System.out.println("Du hast einen " + encounteredVillain.getName() + " getroffen!");
            if (encounteredVillain.isArtefactPresent()) {
                Superhero hero = heroes[0].selectHero(heroes); // Wähle Superhelden
                if (hero != null) {
                    fightOrPuzzle(hero, encounteredVillain); // Kampf oder Rätsel
                }
            } else {
                System.out.println("Du hast diesen Gegner schon besiegt.");
            }
        } else {
            System.out.println("Kein Gegner gefunden. Es ist sicher.");
        }
    }

    // Gegner treffen (Kampf oder Rätsel)
    private void fightOrPuzzle(Superhero hero, Villain villain) {
        if (villain instanceof Employee) {
            System.out.println("Du kämpfst gegen einen Employee!");
            fightEmployee(hero, villain);
        } else {
            System.out.println("Du spielst ein Rätsel gegen " + villain.getName());
            playPuzzle(villain); // Rätsel lösen
        }
    }

    // Kampf gegen einen Employee
    private void fightEmployee(Superhero hero, Villain villain) {
        Random rand = new Random();
        int damageToVillain = rand.nextInt(50) + 1; // Zufälliger Schaden an Gegner
        int damageToHero = rand.nextInt(50) + 1; // Zufälliger Schaden am Superhelden

        System.out.println("Kampf: " + hero.getName() + " vs " + villain.getName());
        System.out.println("Schaden an Gegner: " + damageToVillain);
        System.out.println("Schaden am Superhelden: " + damageToHero);

        villain.takeDamage(damageToVillain);
        hero.takeDamage(damageToHero);

        if (!villain.isAlive()) {
            System.out.println("Gegner besiegt!");
        } else {
            System.out.println("Du hast den Kampf verloren.");
            loseLife(); // Leben verlieren
        }
    }

    // Rätsel lösen
    private void playPuzzle(Villain villain) {
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);

        System.out.println("Rätsel gegen " + villain.getName());
        System.out.print("Rate eine Zahl zwischen 1 und 10: ");
        int userGuess = sc.nextInt();
        int correctAnswer = rand.nextInt(10) + 1;

        if (userGuess == correctAnswer) {
            System.out.println("Du hast das Rätsel gelöst!");
            villain.setArtefactPresent(false); // Artefakt erhalten
        } else {
            System.out.println("Rätsel verloren.");
            loseLife(); // Leben verlieren
        }
    }

    // Ein Leben des Superhelden verlieren
    private void loseLife() {
        Random rand = new Random();
        int heroIndex = rand.nextInt(heroCount); // Zufällig einen Superhelden auswählen
        Superhero hero = heroes[heroIndex];
        hero.takeDamage(20); // Energiepunkte verlieren
        System.out.println(hero.getName() + " hat ein Leben verloren!");
    }

    // Zufälligen Gegner erstellen
    private Villain createRandomVillain() {
        Random rand = new Random();
        int villainType = rand.nextInt(4);
        switch (villainType) {
            case 0: return new Employee();
            case 1: return new Admin();
            case 2: return new Manager();
            case 3: return new TheBigBoss();
            default: return new Employee();
        }
    }

    // Tanz-Option
    private void dance() {
        Scanner sc = new Scanner(System.in);
        
        // Alle Superhelden ausgeben und eine Auswahl treffen
        System.out.println("Wähle einen Superhelden zum Tanzen:");
        for (int i = 0; i < heroes.length; i++) {
            System.out.println((i + 1) + ". " + heroes[i].getName());
        }
        
        // Benutzereingabe für Auswahl
        int choice = sc.nextInt();
        
        if (choice > 0 && choice <= heroes.length) {
            Superhero selectedHero = heroes[choice - 1];
            System.out.println(selectedHero.getName() + " tanzt!");
            selectedHero.dance();  // Aufruf der dance-Methode des Superhelden
        } else {
            System.out.println("Ungültige Wahl.");
        }
    }
}
