package ie.ucc.bis.imci.model.review;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;

import java.io.Serializable;

import android.content.res.Resources;
import android.text.TextUtils;

/**
 * Represents the 'Skin Pinch' Radio Group review item.
 * 
 * Responsible for applying the correct Symptom value to this review item 
 * during the assessSymptom review stage
 * 
 */
public class SkinPinchReviewItem extends ReviewItem implements Serializable {

	/**
	 *  Generated Serial ID
	 */
	private static final long serialVersionUID = 2867350261119694095L;
	
	/** These symptom values will be cross-referenced 
	*   against the 'classification_rules.xml' by the rule engine
	*/
	private static final String VERY_SLOWLY = "very slowly";
	private static final String SLOWLY = "slowly";
	private static final String IMMEDIATE = "immediate";

    /**
     * Constructor for non-header, symptom review items
     * 
     * @param title
     * @param displayValue
     * @param symptomId
     * @param pageKey
     * @param weight
     */
    public SkinPinchReviewItem(String title, String displayValue, String symptomId, String pageKey, int weight) {
    	super(title, displayValue, symptomId, pageKey, weight, false);
    }
 
    /**
     * Method: assessSymptom()
     * 
     * Responsible for applying the correct Symptom value to 
     * this review item 
     * 
     * @param supportingLifeBaseActivity 
     * 
     */
    public void assessSymptom(SupportingLifeBaseActivity supportingLifeBaseActivity) {
    	if (!TextUtils.isEmpty(getDisplayValue())) {
    		Resources resources = supportingLifeBaseActivity.getResources();
    	
    		if (getDisplayValue().equals(resources.getString(R.string.diarrhoea_assessment_radio_skin_pinch_very_slowly))) {
    			// Very Slowly
    			setSymptomValue(VERY_SLOWLY);
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.diarrhoea_assessment_radio_skin_pinch_slowly))) {
    			// Slowly
    			setSymptomValue(SLOWLY);
    		}
    		else if (getDisplayValue().equals(resources.getString(R.string.diarrhoea_assessment_radio_skin_pinch_immediate))) {
    			// Immediate
    			setSymptomValue(IMMEDIATE);
    		}
    		
    	}
    }
	
}