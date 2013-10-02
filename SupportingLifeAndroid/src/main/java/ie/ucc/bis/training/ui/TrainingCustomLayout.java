package ie.ucc.bis.training.ui;

import ie.ucc.bis.activity.TrainingActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TrainingCustomLayout extends LinearLayout {
	
	private float scale = TrainingActivity.BIG_SCALE;
	
	public TrainingCustomLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public TrainingCustomLayout(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		// If you override onDraw(Canvas canvas) you should clear
		// this flag
		setWillNotDraw(false);
	}
	
	public void setPageScale(float scale) {
		setScale(scale);
			
		// if you want to see the scale every time, invokw
		// the invalidate() function which will call 
		// onDraw(Canvas) to redraw the view
		this.invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// main mechanism to display scale animation
		int width = this.getWidth();
		int height = this.getHeight();
		canvas.scale(getScale(), getScale(), width/2, height/2);

		super.onDraw(canvas);
	}
		
	/**
	 * Getter Method: getScale()
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * Setter Method: setScale()
	 */ 
	public void setScale(float scale) {
		this.scale = scale;
	}
}
