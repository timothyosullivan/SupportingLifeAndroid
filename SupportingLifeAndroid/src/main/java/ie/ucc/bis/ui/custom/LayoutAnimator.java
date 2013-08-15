package ie.ucc.bis.ui.custom;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.view.View;

public class LayoutAnimator {
	
	public static void flipInAnimation(LayoutTransition transition) {
		Animator appearAnim = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f);
		appearAnim.setDuration(transition.getDuration(LayoutTransition.APPEARING));
		transition.setAnimator(LayoutTransition.APPEARING, appearAnim);
	}

	public static void flipOutAnimation(LayoutTransition transition, View view) {
		Animator disappearAnim = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f);
		disappearAnim.setDuration(transition.getDuration(LayoutTransition.DISAPPEARING));
		transition.setAnimator(LayoutTransition.DISAPPEARING, disappearAnim);
	}
	
}
