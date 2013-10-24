package ie.ucc.bis.ui.custom;

import java.util.Arrays;
import java.util.List;

import ie.ucc.bis.assessment.imci.model.DynamicView;
import ie.ucc.bis.assessment.model.AbstractPage;
import ie.ucc.bis.assessment.model.listener.RadioGroupCoordinatorListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * 
 * @author timothyosullivan
 */
public class ToggleButtonGroupTableLayout extends TableLayout implements OnClickListener {
	
	private RadioButton activeRadioButton;
	private AbstractPage page;
	private String dataKey;
	private List<DynamicView> dynamicViews;
	private ViewGroup parentView;
	private int indexPosition;
	
	/** 
	 * @param context
	 */
	public ToggleButtonGroupTableLayout(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ToggleButtonGroupTableLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onClick(View view) {
		final RadioButton radioButton = (RadioButton) view;

		// firstly uncheck all radio buttons
		uncheckAll();
		
		// secondly check click button
		radioButton.setChecked(true);
		setActiveRadioButton(radioButton);
		
		// firstly add text label of radio button
		// needed for displaying review UI screen
    	getPage().getPageData().putString(dataKey + RadioGroupCoordinatorListener.RADIO_BUTTON_TEXT_DATA_KEY, getActiveRadioButton().getText().toString());
    	
    	// secondly add the id of the radio button
    	// needed for onCreateView() in relevant Fragment to re-display
    	// a view from page data
		getPage().getPageData().putInt(dataKey, getActiveRadioButton().getId());
		
		/*
		 * 	In some cases this custom layout will also be handling a dynamic view
		 */
		if (getDynamicViews() != null) {
			if (radioButton.getText().toString().equals("No")) {
				for (DynamicView dynamicView : getDynamicViews()) {
					if (dynamicView.getControlledElement() instanceof RadioGroup) {
						((RadioGroup) dynamicView.getControlledElement()).clearCheck();
					}
					else if (dynamicView.getControlledElement() instanceof EditText) {
						((EditText) dynamicView.getControlledElement()).setText(null);
					}
				
					int index = getParentView().indexOfChild(dynamicView.getWrappedView());
					if (index != -1) {				
						getParentView().removeViewAt(index);
					}
				}
			}	
			else { // user has selected 'YES' (or its equivalent)
				for (int counter = 0; counter < getDynamicViews().size(); counter++) {
					if (getParentView().indexOfChild(getDynamicViews().get(counter).getWrappedView()) == -1) {
						getParentView().addView(getDynamicViews().get(counter).getWrappedView(), getIndexPosition() + counter);
					}
				}
			}
		}
    	getPage().notifyDataChanged();
	}
	
	public void configureDynamicView(DynamicView dynamicView, ViewGroup parentView, int indexPosition) {
		setDynamicViews(Arrays.asList(dynamicView));
		setParentView(parentView);
		this.indexPosition = indexPosition;
	}
	

	/* (non-Javadoc)
	 * @see android.widget.TableLayout#addView(android.view.View, int, android.view.ViewGroup.LayoutParams)
	 */
	@Override
	public void addView(View child, int index,
			android.view.ViewGroup.LayoutParams params) {
		super.addView(child, index, params);
		setChildrenOnClickListener((TableRow)child);
	}


	/* (non-Javadoc)
	 * @see android.widget.TableLayout#addView(android.view.View, android.view.ViewGroup.LayoutParams)
	 */
	@Override
	public void addView(View child, android.view.ViewGroup.LayoutParams params) {
		super.addView(child, params);
		setChildrenOnClickListener((TableRow)child);
	}


	private void setChildrenOnClickListener(TableRow tableRow) {
		final int tableRowChildCount = tableRow.getChildCount();
		
		for (int counter=0; counter < tableRowChildCount; counter++) {
			final View view = tableRow.getChildAt(counter);
			
			if ( view instanceof RadioButton ) {
				view.setOnClickListener(this);
			}
		}
	}
	
	/**
	 * Utility method to check appropriate radio button in 
	 * custom table/radio group
	 */
	public void check(int radioButtonId) {
		// firstly iterate over rows
		for (int rowCounter = 0; rowCounter < getChildCount(); rowCounter++) {
			TableRow tableRow = (TableRow) getChildAt(rowCounter);
			// secondly iterate over columns in each row
			for (int columnCounter = 0; columnCounter < tableRow.getChildCount(); columnCounter++) {
				RadioButton radButton = (RadioButton) tableRow.getChildAt(columnCounter);
				if (radButton.getId() == radioButtonId) {
					radButton.setChecked(true);
					break;
				}
			} // end of inner for
		} // end of outer for
	}
	
	/**
	 * Utility method to uncheck all radio buttons in 
	 * custom table/radio group
	 */
	public void uncheckAll() {
		// firstly iterate over rows
		for (int rowCounter = 0; rowCounter < getChildCount(); rowCounter++) {
			TableRow tableRow = (TableRow) getChildAt(rowCounter);
			// secondly iterate over columns in each row
			for (int columnCounter = 0; columnCounter < tableRow.getChildCount(); columnCounter++) {
				RadioButton radButton = (RadioButton) tableRow.getChildAt(columnCounter);
				radButton.setChecked(false);
			} // end of inner for
		} // end of outer for
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
	 * Getter Method: getActiveRadioButton()
	 */	
	public RadioButton getActiveRadioButton() {
		return activeRadioButton;
	}

	/**
	 * Setter Method: setActiveRadioButton()
	 */	
	public void setActiveRadioButton(RadioButton activeRadioButton) {
		this.activeRadioButton = activeRadioButton;
	}

	/**
	 * Getter Method: getDynamicViews()
	 */	
	public List<DynamicView> getDynamicViews() {
		return dynamicViews;
	}

	/**
	 * Setter Method: setDynamicViews()
	 */	
	public void setDynamicViews(List<DynamicView> dynamicViews) {
		this.dynamicViews = dynamicViews;
	}

	/**
	 * Getter Method: getParentView()
	 */	
	public ViewGroup getParentView() {
		return parentView;
	}

	/**
	 * Setter Method: setParentView()
	 */	
	public void setParentView(ViewGroup parentView) {
		this.parentView = parentView;
	}

	/**
	 * Getter Method: getIndexPosition()
	 */	
	public int getIndexPosition() {
		return indexPosition;
	}

}
