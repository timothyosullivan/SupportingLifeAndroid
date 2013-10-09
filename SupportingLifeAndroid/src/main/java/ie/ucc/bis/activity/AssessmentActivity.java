package ie.ucc.bis.activity;

import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.model.AbstractModel;
import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.AssessmentPagerAdapter;
import ie.ucc.bis.assessment.model.ModelCallbacks;
import ie.ucc.bis.imci.ui.PageFragmentCallbacks;
import ie.ucc.bis.imci.ui.ReviewFragmentCallbacks;
import ie.ucc.bis.imci.ui.StepPagerStrip;

public class AssessmentActivity extends SupportingLifeBaseActivity implements
		PageFragmentCallbacks, ReviewFragmentCallbacks, ModelCallbacks {

	protected static final String ASSESSMENT_REVIEW_ITEMS = "ASSESSMENT_REVIEW_ITEMS";
	
	private ViewPager viewPager;
    private AssessmentPagerAdapter assessmentPagerAdapter;
    private List<AbstractPage> pageSequence;
	private AbstractModel assessmentModel;
	private StepPagerStrip stepPagerStrip;
	
    private boolean editingAfterReview;
    private boolean consumePageSelectedEvent;

    private Button nextButton;
	private Button prevButton;
	
	@Override
    public void onPageTreeChanged() {
        setPageSequence(getAssessmentModel().getPageSequence());
        recalculateCutOffPage();
        getStepPagerStrip().setPageCount(getPageSequence().size() + 1); // + 1 = review step
        getAssessmentPagerAdapter().notifyDataSetChanged();
        updateBottomBar();
    }

    public void onEditScreenAfterReview(String key) {
        for (int i = getPageSequence().size() - 1; i >= 0; i--) {
            if (getPageSequence().get(i).getKey().equals(key)) {
                setConsumePageSelectedEvent(true);
                setEditingAfterReview(true);
                getViewPager().setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(AbstractPage page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
            	getAssessmentPagerAdapter().notifyDataSetChanged();
                updateBottomBar();
            }
        }
    }
        
    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = getPageSequence().size() + 1;
        for (int i = 0; i < getPageSequence().size(); i++) {
            AbstractPage page = getPageSequence().get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (getAssessmentPagerAdapter().getCutOffPage() != cutOffPage) {
        	getAssessmentPagerAdapter().setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }
    

	/**
	 * Method: updateBottomBar
	 * 
	 * Responsible for configuring the display of buttons (i.e. previous;
	 * next; review;) on the Assessment wizard screens
	 * 
	 */      
    protected void updateBottomBar() {
        int position = getViewPager().getCurrentItem();
        if (position == getPageSequence().size()) {
            // change text on the next button to indicate
        	// assessment data entry is complete
            getNextButton().setText(R.string.assessment_wizard_finish_button);
        } else {
        	getNextButton().setText(isEditingAfterReview()
                    ? R.string.assessment_wizard_review_button
                    : R.string.assessment_wizard_next_button);
        	getNextButton().setBackgroundResource(R.drawable.breadcrumb_next_button);
        	getNextButton().setTextColor(getResources().getColor(R.color.White));

            getNextButton().setEnabled(position != getAssessmentPagerAdapter().getCutOffPage());
        }

        getPrevButton().setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }
    
	/**
	 * Method: getPage
	 * 
	 * Implementation support for 'PageFragmentCallbacks' Interface
	 * 
	 * Retrieve a bread-crumb UI Wizard page based on the key
	 * 
	 * @param key : String
	 */    
    public AbstractPage getPage(String key) {
        return getAssessmentModel().findPageByKey(key);
    }   

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAssessmentModel().unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", getAssessmentModel().save());
    }
    
	/**
	 * Method: getWizardModel
	 * 
	 * Implementation support for 'PageFragmentCallbacks' Interface
	 * 
	 * @return AbstractWizardModel
	 */
    public AbstractModel getWizardModel() {
    	return getAssessmentModel();
    }

    
    /**
     * Inner Class: AssessmentDialogListener
     * 
     * Provides OnClick handler functionality for Submit Button
     * on the Assessment Submission Dialog
     * 
     * @author TOSullivan
     *
     */
    protected final class AssessmentDialogListener implements DialogInterface.OnClickListener {
    	
		public void onClick(DialogInterface dialog, int which) {
			
			Intent intent = new Intent(getApplicationContext(), ImciAssessmentResultsActivity.class);
			intent.putExtra(ASSESSMENT_REVIEW_ITEMS, getAssessmentModel().gatherAssessmentReviewItems());
			startActivity(intent);
			
			// configure the activity animation transition effect
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}
    } // end of inner class
    
    
	/**
	 * Getter Method: getAssessmentPagerAdapter()
	 */			
	public AssessmentPagerAdapter getAssessmentPagerAdapter() {
		return assessmentPagerAdapter;
	}

	/**
	 * Setter Method: setAssessmentPagerAdapter()
	 */   	
	public void setAssessmentPagerAdapter(AssessmentPagerAdapter assessmentPagerAdapter) {
		this.assessmentPagerAdapter = assessmentPagerAdapter;
	}
	
	/**
	 * Getter Method: getViewPager()
	 */		
	public ViewPager getViewPager() {
		return viewPager;
	}

	/**
	 * Setter Method: setViewPager()
	 */  
	public void setViewPager(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	/**
	 * Getter Method: getPageSequence()
	 */	
	public List<AbstractPage> getPageSequence() {
		return pageSequence;
	}
	
	/**
	 * Setter Method: setPageSequence()
	 */  
	public void setPageSequence(List<AbstractPage> pageSequence) {
		this.pageSequence = pageSequence;
	}

	/**
	 * Getter Method: getStepPagerStrip()
	 */	
	public StepPagerStrip getStepPagerStrip() {
		return stepPagerStrip;
	}

	/**
	 * Setter Method: setStepPagerStrip()
	 */  
	public void setStepPagerStrip(StepPagerStrip stepPagerStrip) {
		this.stepPagerStrip = stepPagerStrip;
	}

	/**
	 * Getter Method: getAssessmentModel()
	 */
	public AbstractModel getAssessmentModel() {
		return assessmentModel;
	}

	/**
	 * Setter Method: setAssessmentModel()
	 */
	public void setAssessmentModel(AbstractModel assessmentModel) {
		this.assessmentModel = assessmentModel;
	}

	/**
	 * Getter Method: getNextButton()
	 */		
    public Button getNextButton() {
		return nextButton;
	}

	/**
	 * Setter Method: setNextButton()
	 */  
	public void setNextButton(Button nextButton) {
		this.nextButton = nextButton;
	}

	/**
	 * Getter Method: getPrevButton()
	 */	
	public Button getPrevButton() {
		return prevButton;
	}

	/**
	 * Setter Method: setPrevButton()
	 */  
	public void setPrevButton(Button prevButton) {
		this.prevButton = prevButton;
	}

	/**
	 * Getter Method: isEditingAfterReview()
	 */
	public boolean isEditingAfterReview() {
		return editingAfterReview;
	}

	/**
	 * Setter Method: setEditingAfterReview()
	 */
	public void setEditingAfterReview(boolean editingAfterReview) {
		this.editingAfterReview = editingAfterReview;
	}

	/**
	 * Getter Method: isConsumePageSelectedEvent()
	 */
	public boolean isConsumePageSelectedEvent() {
		return consumePageSelectedEvent;
	}

	/**
	 * Setter Method: setConsumePageSelectedEvent()
	 */
	public void setConsumePageSelectedEvent(boolean consumePageSelectedEvent) {
		this.consumePageSelectedEvent = consumePageSelectedEvent;
	}
}
