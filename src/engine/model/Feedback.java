package engine.model;


public class Feedback {
    private boolean success;
    private String feedback;

    public Feedback() {
    }

    public Feedback(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }


    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
