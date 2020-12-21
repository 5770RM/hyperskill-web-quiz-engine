package engine.model;

public class Answer {
    private int[] answer;

    public Answer() {
    }

    public Answer(int[] answer) {
        this.answer = answer;
    }

    public Answer(String answer) {
        this.answer = new int[]{};
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
