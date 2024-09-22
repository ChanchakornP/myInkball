package inkball.state;

public enum Color {
    GREY(0, 0),
    ORANGE(0, 0),
    BLUE(0, 0),
    GREEN(0, 0),
    YELLOW(0, 0);

    public int reward;
    public int penalty;

    private Color(int reward, int penalty) {
        this.reward = reward;
        this.penalty = penalty;
    }

    private Color() {

    }

    public int getReward() {
        return reward;
    }

    public int getPenalty() {
        return penalty;
    }

    public static void setReward(String s, int reward) {
        if (s.equals("grey")) {
            GREY.reward = reward;
        } else if (s.equals("orange")) {
            ORANGE.reward = reward;
        } else if (s.equals("blue")) {
            BLUE.reward = reward;
        } else if (s.equals("green")) {
            GREEN.reward = reward;
        } else if (s.equals("yellow")) {
            YELLOW.reward = reward;
        }
    }

    public static void setPenalty(String s, int penalty) {
        if (s.equals("grey")) {
            GREY.penalty = penalty;
        } else if (s.equals("orange")) {
            ORANGE.penalty = penalty;
        } else if (s.equals("blue")) {
            BLUE.penalty = penalty;
        } else if (s.equals("green")) {
            GREEN.penalty = penalty;
        } else if (s.equals("yellow")) {
            YELLOW.penalty = penalty;
        }
    }
}