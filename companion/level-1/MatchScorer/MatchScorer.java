// Level 1 capstone — pure Java, no hardware required.
// See: docs/source/guide-to-java-level-1.md — Capstone: MatchScorer

public class MatchScorer {

    // Point values for each reef level (teleop scoring, 2025 Reefscape rules).
    // Keep constants together — when the game changes, there is exactly one place to update.
    private static final int L1_POINTS = 2;
    private static final int L2_POINTS = 3;
    private static final int L3_POINTS = 4;
    private static final int L4_POINTS = 5;
    private static final int CLIMB_POINTS = 12;

    private String teamName;
    private int[] coralPerLevel;  // index 0 = Level 1 reef, index 3 = Level 4 reef
    private boolean climbed;

    // Constructor — one MatchScorer per team per match
    public MatchScorer(String teamName) {
        this.teamName = teamName;
        coralPerLevel = new int[4];  // all zeros by default
    }

    // Record coral scored at a reef level (valid levels: 1–4)
    public void recordCoral(int level, int count) {
        if (level >= 1 && level <= 4) {
            coralPerLevel[level - 1] += count;  // convert 1-based level to 0-based array index
        }
    }

    public void setClimbed(boolean didClimb) {
        climbed = didClimb;
    }

    // Loop through each level, multiply coral count by that level's point value, sum them up
    public int coralScore() {
        int[] pointsPerLevel = {L1_POINTS, L2_POINTS, L3_POINTS, L4_POINTS};
        int total = 0;
        for (int i = 0; i < coralPerLevel.length; i++) {
            total += coralPerLevel[i] * pointsPerLevel[i];
        }
        return total;
    }

    public int endgameScore() {
        return climbed ? CLIMB_POINTS : 0;
    }

    public int totalScore() {
        return coralScore() + endgameScore();
    }

    public void printReport() {
        System.out.println("=== " + teamName + " Match Report ===");
        System.out.println("Coral score:   " + coralScore()   + " pts");
        System.out.println("Endgame score: " + endgameScore() + " pts");
        System.out.println("TOTAL:         " + totalScore()   + " pts");
    }

    // Quick smoke-test — run with: javac MatchScorer.java && java MatchScorer
    public static void main(String[] args) {
        MatchScorer team3603 = new MatchScorer("Team 3603");
        team3603.recordCoral(3, 2);
        team3603.recordCoral(4, 1);
        team3603.setClimbed(true);
        team3603.printReport();
        // Expected:
        // === Team 3603 Match Report ===
        // Coral score:   13 pts
        // Endgame score: 12 pts
        // TOTAL:         25 pts
    }
}
