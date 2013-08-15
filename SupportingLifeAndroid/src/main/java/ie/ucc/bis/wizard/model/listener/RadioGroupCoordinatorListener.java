package ie.ucc.bis.wizard.model.listener;

import ie.ucc.bis.wizard.model.AbstractPage;
import ie.ucc.bis.wizard.model.DynamicView;

import java.util.Arrays;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RadioGroupCoordinatorListener implements OnCheckedChangeListener {
	
	public static final String RADIO_BUTTON_TEXT_DATA_KEY = "RadioButtonText";
	
	private AbstractPage page;
	private String dataKey;
	private List<DynamicView> dynamicViews;
	private ViewGroup parentView;

	public RadioGroupCoordinatorListener(AbstractPage page, String dataKey, DynamicView dynamicView, ViewGroup parentView) {
		setPage(page);
		setDataKey(dataKey);
		setDynamicViews(Arrays.asList(dynamicView));
		setParentView(parentView);
	}
	
	public RadioGroupCoordinatorListener(AbstractPage page, String dataKey, List<DynamicView> dynamicViews, ViewGroup parentView) {
		setPage(page);
		setDataKey(dataKey);
		setDynamicViews(dynamicViews);
		setParentView(parentView);
	}
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
		// firstly add text label of radio button
		// needed for displaying review UI screen
    	getPage().getPageData().putString(dataKey + RADIO_BUTTON_TEXT_DATA_KEY, radioButton.getText().toString());
    	
    	// secondly add the id of the radio button
    	// needed for onCreateView() in relevant Fragment to re-display
    	// a view from page data
		getPage().getPageData().putInt(dataKey, checkedId);
		
		// Thirdly, need to handle the View(s) that this RadioButton is controlling
		for (DynamicView dynamicView : getDynamicViews()) {
			if (radioButton.getText().toString().equals("No")) {
				if (dynamicView.getControlledElement() instanceof RadioGroup) {
					((RadioGroup) dynamicView.getControlledElement()).clearCheck();
				}
				else if (dynamicView.getControlledElement() instanceof EditText) {
					((EditText) dynamicView.getControlledElement()).setText(null);
				}
				
		//		if (dynamicView.getWrappedView().getVisibility() == View.VISIBLE) {
			//		slideToRight(dynamicView.getWrappedView());
				//	dynamicView.getWrappedView().setVisibility(View.GONE);
					getParentView().removeView(dynamicView.getWrappedView());
		//		}
			}
			else {
	//			if (dynamicView.getWrappedView().getVisibility() == View.GONE) {
				//	fadeIn(dynamicView.getWrappedView());
				//	dynamicView.getWrappedView().setVisibility(View.VISIBLE);
				getParentView().addView(dynamicView.getWrappedView());
	//			}
			}
	//		dynamicView.getWrappedView().invalidate();
		}
	    getPage().notifyDataChanged();
	}

	private void fadeIn(final View view){
		// alpha value = 1.0 means fully opaque and alpha value = 0.0 means fully transparent.
		float fromAlphaLevel = 0.0f;
		float toAlphaLevel = 1.0f;
		
		AlphaAnimation animate = new AlphaAnimation(fromAlphaLevel, toAlphaLevel);
		animate.setDuration(500);
		animate.setFillAfter(true);
		view.startAnimation(animate);
		
		animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            	
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            	view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
	}		
	
	private void fadeOut(final View view){
		// alpha value = 1.0 means fully opaque and alpha value = 0.0 means fully transparent.
		float fromAlphaLevel = 1.0f;
		float toAlphaLevel = 0.0f;
		
		AlphaAnimation animate = new AlphaAnimation(fromAlphaLevel, toAlphaLevel);
		animate.setDuration(500);
		animate.setFillAfter(true);
		view.startAnimation(animate);
		
		animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
            	view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
	}
	
	
	private void slideToRight(final View view) {
		TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
		animate.setDuration(1000);
		animate.setFillAfter(true);
		view.startAnimation(animate);
		
		animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
            	view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
	}
	
	/**
	 * Getter Method: getPage()
	 */
	public AbstractPage getPage() {
		return page;
	}

	/**
	 * Setter Method: setPage()
	 */
	public void setPage(AbstractPage page) {
		this.page = page;
	}

	/**
	 * Getter Method: getDataKey()
	 */
	public String getDataKey() {
		return dataKey;
	}

	/**
	 * Setter Method: setDataKey()
	 */
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	/**
	 * Getter Method: getDynamicViews()
	 */
	private List<DynamicView> getDynamicViews() {
		return dynamicViews;
	}

	/**
	 * Setter Method: setDynamicViews()
	 */
	private void setDynamicViews(List<DynamicView> dynamicViews) {
		this.dynamicViews = dynamicViews;
	}

	/**
	 * Getter Method: getParentView()
	 */
	private ViewGroup getParentView() {
		return parentView;
	}

	/**
	 * Setter Method: setParentView()
	 */
	private void setParentView(ViewGroup parentView) {
		this.parentView = parentView;
	}

}
