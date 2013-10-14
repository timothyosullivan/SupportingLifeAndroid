package ie.ucc.bis.ui.utilities;

import java.util.ArrayList;

import ie.ucc.bis.assessment.model.review.ReviewItem;

/**
 * 
 * @author timothyosullivan
 */
public class ReviewItemUtilities {

	public static ReviewItem findReviewItemBySymptomId(String symptomId, ArrayList<ReviewItem> reviewItems) {
		for (ReviewItem reviewItem : reviewItems) {
			if (symptomId.equals(reviewItem.getSymptomId())) {
				return reviewItem;
			}
		}
		return null;
	}	
}
