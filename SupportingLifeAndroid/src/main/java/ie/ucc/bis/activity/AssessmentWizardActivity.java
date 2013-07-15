package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.model.AbstractPage;
import ie.ucc.bis.wizard.model.AbstractWizardModel;
import ie.ucc.bis.wizard.model.ModelCallbacks;
import ie.ucc.bis.wizard.model.SupportingLifeBreadcrumbWizardModel;
import ie.ucc.bis.wizard.model.SupportingLifeWizardPagerAdapter;
import ie.ucc.bis.wizard.ui.PageFragmentCallbacks;
import ie.ucc.bis.wizard.ui.PageSelectedListener;
import ie.ucc.bis.wizard.ui.ReviewFragmentCallbacks;
import ie.ucc.bis.wizard.ui.StepPagerStrip;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

public class AssessmentWizardActivity extends SupportingLifeBaseActivity implements PageFragmentCallbacks, ReviewFragmentCallbacks, ModelCallbacks {

    private ViewPager viewPager;
    private SupportingLifeWizardPagerAdapter suppLifeWizardPagerAdapter;
    private List<AbstractPage> currentPageSequence;
    private AbstractWizardModel supportingLifeWizardModel = new SupportingLifeBreadcrumbWizardModel(this);    
    private StepPagerStrip stepPagerStrip;
    
    private boolean mEditingAfterReview;
    private boolean mConsumePageSelectedEvent;

    private Button mNextButton;
    private Button mPrevButton;
    
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
        setTitleFromActivityLabel(R.id.title_text);

        if (savedInstanceState != null) {
        	getSupportingLifeWizardModel().load(savedInstanceState.getBundle("model"));
        }

        getSupportingLifeWizardModel().registerListener(this);

        setSuppLifeWizardPagerAdapter(new SupportingLifeWizardPagerAdapter(this, getSupportFragmentManager()));
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

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

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

        mNextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (getViewPager().getCurrentItem() == getCurrentPageSequence().size()) {
                    DialogFragment dg = new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.submit_confirm_message)
                                    .setPositiveButton(R.string.submit_confirm_button, null)
                                    .setNegativeButton(android.R.string.cancel, null)
                                    .create();
                        }
                    };
                    dg.show(getSupportFragmentManager(), "place_order_dialog");
                } else {
                    if (mEditingAfterReview) {
                    	getViewPager().setCurrentItem(getSuppLifeWizardPagerAdapter().getCount() - 1);
                    } else {
                    	getViewPager().setCurrentItem(getViewPager().getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	getViewPager().setCurrentItem(getViewPager().getCurrentItem() - 1);
            }
        });

        onPageTreeChanged();
        updateBottomBar();        
    }


    public void onPageTreeChanged() {
        setCurrentPageSequence(getSupportingLifeWizardModel().getCurrentPageSequence());
        recalculateCutOffPage();
        getStepPagerStrip().setPageCount(getCurrentPageSequence().size() + 1); // + 1 = review step
        getSuppLifeWizardPagerAdapter().notifyDataSetChanged();
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = getViewPager().getCurrentItem();
        if (position == getCurrentPageSequence().size()) {
            // change text and colour on the next button to indicate
        	// assessment data entry is complete
            mNextButton.setText(R.string.assessment_wizard_finish_button);
            mNextButton.setBackgroundResource(R.drawable.blue_button);
            mNextButton.setTextAppearance(this, R.style.BreadcrumbTextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.assessment_wizard_review_button
                    : R.string.assessment_wizard_next_button);
            mNextButton.setBackgroundResource(R.drawable.breadcrumb_next_button);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != getSuppLifeWizardPagerAdapter().getCutOffPage());
        }

        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
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

    public AbstractWizardModel getWizardModel() {
        return getSupportingLifeWizardModel();
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
	 * Getter Method: getSupportingLifeWizardModel()
	 * 
	 */	    
	public AbstractWizardModel getSupportingLifeWizardModel() {
		return supportingLifeWizardModel;
	}
	
	/**
	 * Setter Method: setSupportingLifeWizardModel()
	 * 
	 */   	
	public void setSupportingLifeWizardModel(AbstractWizardModel supportingLifeWizardModel) {
		this.supportingLifeWizardModel = supportingLifeWizardModel;
	}

	/**
	 * Getter Method: getSuppLifeWizardPagerAdapter()
	 * 
	 */			
	public SupportingLifeWizardPagerAdapter getSuppLifeWizardPagerAdapter() {
		return suppLifeWizardPagerAdapter;
	}

	/**
	 * Setter Method: setSuppLifeWizardPagerAdapter()
	 * 
	 */   	
	public void setSuppLifeWizardPagerAdapter(SupportingLifeWizardPagerAdapter suppLifeWizardPagerAdapter) {
		this.suppLifeWizardPagerAdapter = suppLifeWizardPagerAdapter;
	}

	/**
	 * Getter Method: getViewPager()
	 * 
	 */		
	public ViewPager getViewPager() {
		return viewPager;
	}

	/**
	 * Setter Method: setViewPager()
	 * 
	 */  
	public void setViewPager(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	/**
	 * Getter Method: getCurrentPageSequence()
	 * 
	 */	
	public List<AbstractPage> getCurrentPageSequence() {
		return currentPageSequence;
	}

	/**
	 * Setter Method: setCurrentPageSequence()
	 * 
	 */  
	public void setCurrentPageSequence(List<AbstractPage> currentPageSequence) {
		this.currentPageSequence = currentPageSequence;
	}

	/**
	 * Getter Method: getStepPagerStrip()
	 * 
	 */	
	public StepPagerStrip getStepPagerStrip() {
		return stepPagerStrip;
	}

	/**
	 * Setter Method: setStepPagerStrip()
	 * 
	 */  
	public void setStepPagerStrip(StepPagerStrip stepPagerStrip) {
		this.stepPagerStrip = stepPagerStrip;
	}    
}