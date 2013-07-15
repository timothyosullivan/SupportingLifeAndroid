package ie.ucc.bis.wizard.model;

import ie.ucc.bis.activity.AssessmentWizardActivity;
import ie.ucc.bis.wizard.ui.ReviewFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class SupportingLifeWizardPagerAdapter extends FragmentStatePagerAdapter {
    private int mCutOffPage;
    private Fragment mPrimaryItem;
    private AssessmentWizardActivity assessmentWizardActivity;

    public SupportingLifeWizardPagerAdapter(AssessmentWizardActivity assessmentWizardActivity, FragmentManager fm) {
        super(fm);
        setAssessmentWizardActivity(assessmentWizardActivity);
    }

    @Override
    public Fragment getItem(int i) {
        if (i >= getAssessmentWizardActivity().getCurrentPageSequence().size()) {
            return new ReviewFragment();
        }

        return getAssessmentWizardActivity().getCurrentPageSequence().get(i).createFragment();
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO: be smarter about this
        if (object == mPrimaryItem) {
            // Re-use the current fragment (its position never changes)
            return POSITION_UNCHANGED;
        }

        return POSITION_NONE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mPrimaryItem = (Fragment) object;
    }

    @Override
    public int getCount() {
        return Math.min(mCutOffPage + 1, getAssessmentWizardActivity().getCurrentPageSequence().size() + 1);
    }

    public void setCutOffPage(int cutOffPage) {
        if (cutOffPage < 0) {
            cutOffPage = Integer.MAX_VALUE;
        }
        mCutOffPage = cutOffPage;
    }

    public int getCutOffPage() {
        return mCutOffPage;
    }

	/**
	 * Getter Method: getAssessmentWizardActivity()
	 * 
	 */	    
	public AssessmentWizardActivity getAssessmentWizardActivity() {
		return assessmentWizardActivity;
	}

	/**
	 * Setter Method: setAssessmentWizardActivity()
	 * 
	 */  	
	public void setAssessmentWizardActivity(AssessmentWizardActivity assessmentWizardActivity) {
		this.assessmentWizardActivity = assessmentWizardActivity;
	}
}