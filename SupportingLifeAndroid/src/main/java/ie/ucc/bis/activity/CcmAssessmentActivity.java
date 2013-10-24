package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import ie.ucc.bis.assessment.ccm.model.CcmAssessmentModel;
import ie.ucc.bis.assessment.imci.ui.PageSelectedListener;
import ie.ucc.bis.assessment.imci.ui.StepPagerStrip;
import ie.ucc.bis.assessment.model.AssessmentPagerAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

/**
 * 
 * @author timothyosullivan
 */

public class CcmAssessmentActivity extends AssessmentActivity {
    
	/**
	 * OnCreate method is called when the activity is first created.
	 * 
	 * This is where all of the normal static set up should occur
	 * e.g. create views, bind data to lists, etc.
	 * 
	 * The method also provides a Bundle parameter containing the activity's
	 * previously frozen state (if there was one).
	 * 
	 * This method is always followed by onStart().
	 * 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setAssessmentModel(new CcmAssessmentModel(this));
        
        setContentView(R.layout.activity_ccm_assessment);
        
        setTitleFromActivityLabel(R.id.action_bar_title_text);
        
        if (savedInstanceState != null) {
        	getAssessmentModel().load(savedInstanceState.getBundle("model"));
        }

        getAssessmentModel().registerListener(this);

        setAssessmentPagerAdapter(new AssessmentPagerAdapter(this, getSupportFragmentManager()));
        setViewPager((ViewPager) findViewById(R.id.pager));
        getViewPager().setAdapter(getAssessmentPagerAdapter());
        setStepPagerStrip((StepPagerStrip) findViewById(R.id.strip));
        
        // configure listener on StepPagerStrip UI component
        getStepPagerStrip().setPageSelectedListener(new PageSelectedListener() {
            public void onPageStripSelected(int position) {
                position = Math.min(getAssessmentPagerAdapter().getCount() - 1, position);
                if (getViewPager().getCurrentItem() != position) {
                	getViewPager().setCurrentItem(position);
                }
            }
        });

        setNextButton((Button) findViewById(R.id.next_button));
        setPrevButton((Button) findViewById(R.id.prev_button));

        getViewPager().setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	getStepPagerStrip().setCurrentPage(position);

                if (isConsumePageSelectedEvent()) {
                    setConsumePageSelectedEvent(false);
                    return;
                }

                setEditingAfterReview(false);
                updateBottomBar();
            }
        });

        // configure click listener on Next Button       
        getNextButton().setOnClickListener(new NextButtonListener());

        getPrevButton().setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	getViewPager().setCurrentItem(getViewPager().getCurrentItem() - 1);
            }
        });

        onPageTreeChanged();
        updateBottomBar();
        
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		 addSoftKeyboardHandling(findViewById(R.id.assessment_wizard));
    }
    
    /**
     * Inner Class: NextButtonListener
     * 
     * Provides OnClick handler functionality for Next Button
     * on the Wizard bread-crumb UI 
     * 
     * @author TOSullivan
     *
     */
    private final class NextButtonListener implements View.OnClickListener {
		public void onClick(View view) {
		    if (getViewPager().getCurrentItem() == getPageSequence().size()) {
		    	// we're currently on the review pane so display confirmation dialog
		        DialogFragment dg = new DialogFragment() {
		            @Override
		            public Dialog onCreateDialog(Bundle savedInstanceState) {
		                return new AlertDialog.Builder(getActivity())
		                        .setMessage(R.string.submit_confirm_message)
		                        .setPositiveButton(R.string.submit_confirm_button, new AssessmentDialogListener())
		                        .setNegativeButton(android.R.string.cancel, null)
		                        .create();
		            }
		        };
		        dg.show(getSupportFragmentManager(), "Submit Assessment");
		    } else {
		        if (isEditingAfterReview()) {
		        	getViewPager().setCurrentItem(getAssessmentPagerAdapter().getCount() - 1);
		        } else {
		        	getViewPager().setCurrentItem(getViewPager().getCurrentItem() + 1);
		        }
		    }
		}
	} // end of inner class
}