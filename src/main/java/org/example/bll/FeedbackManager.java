package org.example.bll;

import org.example.dal.FeedbackDal;
import org.example.dto.Feedback;

public class FeedbackManager {
    private final FeedbackDal feedbackDal;

    public FeedbackManager() {
        this.feedbackDal = new FeedbackDal();
    }

    public boolean saveFeedback(Feedback feedback) {
        return feedbackDal.saveFeedback(feedback);
    }
}