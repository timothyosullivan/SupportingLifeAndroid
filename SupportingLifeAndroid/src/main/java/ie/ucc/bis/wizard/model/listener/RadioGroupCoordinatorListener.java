package ie.ucc.bis.wizard.model.listener;

import ie.ucc.bis.wizard.model.AbstractPage;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RadioGroupCoordinatorListener implements OnCheckedChangeListener {
	
	public static final String RADIO_BUTTON_TEXT_DATA_KEY = "RadioButtonText";
	
	private AbstractPage page;
	private String dataKey;
	private RadioGroup manipulatedRadioGroup;
	
	public RadioGroupCoordinatorListener(AbstractPage page, String dataKey, RadioGroup manipulatedRadioGroup) {
		setPage(page);
		setDataKey(dataKey);
		setManipulatedRadioGroup(manipulatedRadioGroup);
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
		
		if (radioButton.getText().toString().equals("No")) {
			getManipulatedRadioGroup().clearCheck();
		//	getManipulatedRadioGroup().setVisibility(View.GONE);
		//	slideToRight(getManipulatedRadioGroup());
			fadeOut(getManipulatedRadioGroup());		
		}
		else {
			fadeIn(getManipulatedRadioGroup());		
		}
		
		getManipulatedRadioGroup().invalidate();
    	getPage().notifyDataChanged();
	}

	// To animate view slide out from bottom to top
	public void slideToTop(View view) {
	TranslateAnimation animate = new TranslateAnimation(0,0,0,-view.getHeight());
	animate.setDuration(500);
	animate.setFillAfter(true);
	view.startAnimation(animate);
	view.setVisibility(View.GONE);
	}
	
	public void slideToRight(View view){
	TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
	animate.setDuration(500);
	animate.setFillAfter(true);
	view.startAnimation(animate);
	view.setVisibility(View.GONE);
	}	
	
	public void fadeIn(View view){
		// alpha value = 1.0 means fully opaque and alpha value = 0.0 means fully transparent.
		float fromAlphaLevel = 0.0f;
		float toAlphaLevel = 1.0f;

		
		AlphaAnimation animate = new AlphaAnimation(fromAlphaLevel, toAlphaLevel);
		animate.setDuration(500);
		animate.setFillAfter(true);
		view.startAnimation(animate);
		view.setVisibility(View.VISIBLE);
		}		
	
	public void fadeOut(final View view){
		// alpha value = 1.0 means fully opaque and alpha value = 0.0 means fully transparent.
		float fromAlphaLevel = 1.0f;
		float toAlphaLevel = 0.0f;

		
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
            	view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
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

	public RadioGroup getManipulatedRadioGroup() {
		return manipulatedRadioGroup;
	}

	public void setManipulatedRadioGroup(RadioGroup manipulatedRadioGroup) {
		this.manipulatedRadioGroup = manipulatedRadioGroup;
	}

	


	
}
