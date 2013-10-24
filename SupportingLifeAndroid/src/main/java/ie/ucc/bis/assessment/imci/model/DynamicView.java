package ie.ucc.bis.assessment.imci.model;

import android.view.View;

/**
 * 
 * @author timothyosullivan
 */

public class DynamicView {
	private View wrappedView;
	private View controlledElement;
	
	public DynamicView(View wrappedView, View controlledElement) {
		setWrappedView(wrappedView);
		setControlledElement(controlledElement);
	}
	
	/**
	 * Getter Method: getWrappedView()
	 */
	public View getWrappedView() {
		return wrappedView;
	}

	/**
	 * Setter Method: setWrappedView()
	 */
	private void setWrappedView(View wrappedView) {
		this.wrappedView = wrappedView;
	}

	/**
	 * Getter Method: getControlledElement()
	 */
	public View getControlledElement() {
		return controlledElement;
	}

	/**
	 * Setter Method: setControlledElement()
	 */
	private void setControlledElement(View controlledElement) {
		this.controlledElement = controlledElement;
	}
}
