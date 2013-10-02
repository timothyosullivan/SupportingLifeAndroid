package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import ie.ucc.bis.imci.model.AbstractPage;
import ie.ucc.bis.imci.model.AbstractWizardModel;
import ie.ucc.bis.imci.model.AssessmentWizardModel;
import ie.ucc.bis.imci.model.AssessmentWizardPagerAdapter;
import ie.ucc.bis.imci.model.ModelCallbacks;
import ie.ucc.bis.imci.ui.PageFragmentCallbacks;
import ie.ucc.bis.imci.ui.PageSelectedListener;
import ie.ucc.bis.imci.ui.ReviewFragmentCallbacks;
import ie.ucc.bis.imci.ui.StepPagerStrip;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

public class AssessmentWizardActivity extends SupportingLifeBaseActivity implements PageFragmentCallbacks, ReviewFragmentCallbacks, ModelCallbacks {

	public static final String ASSESSMENT_REVIEW_ITEMS = "ASSESSMENT_REVIEW_ITEMS";
	
	private ViewPager viewPager;
    private AssessmentWizardPagerAdapter suppLifeWizardPagerAdapter;
    private List<AbstractPage> currentPageSequence;
    private AbstractWizardModel supportingLifeWizardModel = new AssessmentWizardModel(this);;
    private StepPagerStrip stepPagerStrip;
    
    private boolean mEditingAfterReview;
    private boolean mConsumePageSelectedEvent;

    private Button nextButton;
	private Button prevButton;
    
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
        setContentView(R.layout.activity_assessment_wizard);
        
        setTitleFromActivityLabel(R.id.action_bar_title_text);
        
        if (savedInstanceState != null) {
        	getSupportingLifeWizardModel().load(savedInstanceState.getBundle("model"));
        }

        getSupportingLifeWizardModel().registerListener(this);

        setSuppLifeWizardPagerAdapter(new AssessmentWizardPagerAdapter(this, getSupportFragmentManager()));
        setViewPager((ViewPager) findViewById(R.id.pager));
        getViewPager().setAdapter(getSuppLifeWizardPagerAdapter());
        setStepPagerStrip((StepPagerStrip) findViewById(R.id.strip));
        
        // configure listener on StepPagerStrip UI component
        getStepPagerStrip().setPageSelectedListener(new PageSelectedListener() {
            public void onPageStripSelected(int position) {
                position = Math.min(getSuppLifeWizardPagerAdapter().getCount() - 1, position);
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

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return;
                }

                mEditingAfterReview = false;
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


    public void onPageTreeChanged() {
        setCurrentPageSequence(getSupportingLifeWizardModel().getCurrentPageSequence());
        recalculateCutOffPage();
        getStepPagerStrip().setPageCount(getCurrentPageSequence().size() + 1); // + 1 = review step
        getSuppLifeWizardPagerAdapter().notifyDataSetChanged();
        updateBottomBar();
    }

	/**
	 * Method: updateBottomBar
	 * 
	 * Responsible for configuring the display of buttons (i.e. previous;
	 * next; review;) on the Assessment wizard screens
	 * 
	 */      
    private void updateBottomBar() {
        int position = getViewPager().getCurrentItem();
        if (position == getCurrentPageSequence().size()) {
            // change text on the next button to indicate
        	// assessment data entry is complete
            getNextButton().setText(R.string.assessment_wizard_finish_button);
        } else {
        	getNextButton().setText(mEditingAfterReview
                    ? R.string.assessment_wizard_review_button
                    : R.string.assessment_wizard_next_button);
        	getNextButton().setBackgroundResource(R.drawable.breadcrumb_next_button);
        	getNextButton().setTextColor(getResources().getColor(R.color.White));

            getNextButton().setEnabled(position != getSuppLifeWizardPagerAdapter().getCutOffPage());
        }

        getPrevButton().setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportingLifeWizardModel().unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", getSupportingLifeWizardModel().save());
    }

    public void onEditScreenAfterReview(String key) {
        for (int i = getCurrentPageSequence().size() - 1; i >= 0; i--) {
            if (getCurrentPageSequence().get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                getViewPager().setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    public void onPageDataChanged(AbstractPage page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
            	getSuppLifeWizardPagerAdapter().notifyDataSetChanged();
                updateBottomBar();
            }
        }
    }
        
    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = getCurrentPageSequence().size() + 1;
        for (int i = 0; i < getCurrentPageSequence().size(); i++) {
            AbstractPage page = getCurrentPageSequence().get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (getSuppLifeWizardPagerAdapter().getCutOffPage() != cutOffPage) {
        	getSuppLifeWizardPagerAdapter().setCutOffPage(cutOffPage);
            return true;
        }

        return false;
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
		    if (getViewPager().getCurrentItem() == getCurrentPageSequence().size()) {
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
		        if (mEditingAfterReview) {
		        	getViewPager().setCurrentItem(getSuppLifeWizardPagerAdapter().getCount() - 1);
		        } else {
		        	getViewPager().setCurrentItem(getViewPager().getCurrentItem() + 1);
		        }
		    }
		}
	} // end of inner class
    
    /**
     * Inner Class: AssessmentDialogListener
     * 
     * Provides OnClick handler functionality for Submit Button
     * on the Assessment Submission Dialog
     * 
     * @author TOSullivan
     *
     */
    private final class AssessmentDialogListener implements DialogInterface.OnClickListener {
    	
		public void onClick(DialogInterface dialog, int which) {
			
			Intent intent = new Intent(getApplicationContext(), AssessmentResultsActivity.class);
			intent.putExtra(ASSESSMENT_REVIEW_ITEMS, getSupportingLifeWizardModel().gatherAssessmentReviewItems());
			startActivity(intent);
			
			// configure the activity animation transition effect
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}
    } // end of inner class

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
        return getSupportingLifeWizardModel().findPageByKey(key);
    }   
  
	/**
	 * Getter Method: getWizardModel()
	 */	 
    public AbstractWizardModel getWizardModel() {
        return getSupportingLifeWizardModel();
    }
    
	/**
	 * Getter Method: getSupportingLifeWizardModel()
	 */	    
	public AbstractWizardModel getSupportingLifeWizardModel() {
		return supportingLifeWizardModel;
	}
	
	/**
	 * Setter Method: setSupportingLifeWizardModel()
	 */   	
	public void setSupportingLifeWizardModel(AbstractWizardModel supportingLifeWizardModel) {
		this.supportingLifeWizardModel = supportingLifeWizardModel;
	}

	/**
	 * Getter Method: getSuppLifeWizardPagerAdapter()
	 */			
	public AssessmentWizardPagerAdapter getSuppLifeWizardPagerAdapter() {
		return suppLifeWizardPagerAdapter;
	}

	/**
	 * Setter Method: setSuppLifeWizardPagerAdapter()
	 */   	
	public void setSuppLifeWizardPagerAdapter(AssessmentWizardPagerAdapter suppLifeWizardPagerAdapter) {
		this.suppLifeWizardPagerAdapter = suppLifeWizardPagerAdapter;
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
	 * Getter Method: getCurrentPageSequence()
	 */	
	public List<AbstractPage> getCurrentPageSequence() {
		return currentPageSequence;
	}

	/**
	 * Setter Method: setCurrentPageSequence()
	 */  
	public void setCurrentPageSequence(List<AbstractPage> currentPageSequence) {
		this.currentPageSequence = currentPageSequence;
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
}