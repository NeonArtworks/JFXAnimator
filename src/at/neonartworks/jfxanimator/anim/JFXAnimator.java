package at.neonartworks.jfxanimator.anim;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.util.Duration;

/**
 * This is a very simple helper class that allows the user to animate properties
 * of JavaFX nodes.
 * 
 * @author Florian Wagner
 *
 */
public class JFXAnimator {

	private Timeline _intTimeline = new Timeline();
	private Timeline _outTImeline = new Timeline();
	private static boolean isFinished = false;

	private void reset_Timeline() {
		_intTimeline = new Timeline();
		_outTImeline = new Timeline();
		isFinished = false;
	}

	private WritableValue<Number> _animateProp;

	/**
	 * Creates a new JFXAnimator instance and sets the property to animate.
	 * 
	 * @param property
	 *            the property to animate
	 */
	public JFXAnimator(WritableValue<Number> property) {
		this._animateProp = property;

	}

	/**
	 * Sets the property you want to animate.
	 * 
	 * @param property
	 *            the property to animate
	 */
	public void setProperty(WritableValue<Number> property) {
		this._animateProp = property;
	}

	public static boolean isFinished() {
		return isFinished;
	}

	/**
	 * <p>
	 * This method animates a property from 'startDuration' to 'stopDuration' with
	 * an hold time of 'holdTime'.
	 * </p>
	 * 
	 * @param startDuration
	 *            the duration to reach the first value (fade-in)
	 * 
	 * @param endDuration
	 *            the duration after the holdTime to reach the second value
	 *            (fade-out)
	 * 
	 * @param holdTime
	 *            the hold time of the animation
	 * 
	 * @param firstVal
	 *            the first keyframe value
	 * 
	 * @param secondVal
	 *            the second keyframe value
	 */
	public void start(int startDuration, int endDuration, int holdTime, double firstVal, double secondVal) {

		KeyFrame inKey = new KeyFrame(Duration.millis(startDuration), new KeyValue(_animateProp, firstVal));
		KeyFrame outKey = new KeyFrame(Duration.millis(endDuration), new KeyValue(_animateProp, secondVal));
		reset_Timeline();

		_intTimeline.getKeyFrames().add(inKey);
		_outTImeline.getKeyFrames().add(outKey);
		_intTimeline.setOnFinished((actionEvent) -> {
			new Thread(() -> {
				try {
					Thread.sleep(holdTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				_outTImeline.play();
				_outTImeline.setOnFinished((actionEvent2) -> {
					isFinished = true;
				});
			}).start();
		});
		_intTimeline.play();
	}

	/**
	 * <p>
	 * This method animates a property from 'startDuration' to 'stopDuration' with
	 * an hold time of 'holdTime'.
	 * </p>
	 * 
	 * @param prop
	 *            the property you want to animate
	 * 
	 * @param startDuration
	 *            the duration to reach the first value (fade-in)
	 * 
	 * @param endDuration
	 *            the duration after the holdTime to reach the second value
	 *            (fade-out)
	 * 
	 * @param holdTime
	 *            the hold time of the animation
	 * 
	 * @param firstVal
	 *            the first keyframe value
	 * 
	 * @param secondVal
	 *            the second keyframe value
	 */
	public static synchronized void animateProperty(WritableValue<Number> property, int startDuration, int endDuration,
			int holdTime, double firstVal, double secondVal) {

		isFinished = false;

		Timeline intTimeline = new Timeline();
		Timeline outTImeline = new Timeline();

		KeyFrame inKey = new KeyFrame(Duration.millis(startDuration), new KeyValue(property, firstVal));
		KeyFrame outKey = new KeyFrame(Duration.millis(endDuration), new KeyValue(property, secondVal));

		intTimeline.getKeyFrames().add(inKey);
		outTImeline.getKeyFrames().add(outKey);
		intTimeline.setOnFinished((actionEvent) -> {
			new Thread(() -> {
				try {
					Thread.sleep(holdTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				outTImeline.play();
				outTImeline.setOnFinished((actionEvent2) -> {
					isFinished = true;
				});
			}).start();
		});
		intTimeline.play();

	}
}