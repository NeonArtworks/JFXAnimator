package at.neonartworks.jfxanimator.examples;

import at.neonartworks.jfxanimator.anim.JFXAnimator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class example extends Application {

	private double width = 600;
	private double height = 430;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// create root and setup stage....
		primaryStage.setTitle("JFXAnimator example");
		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();

		// create example object.....
		Rectangle rect = new Rectangle(100, 100);
		rect.setFill(new Color(0, 0, 0, 1));
		rect.setTranslateX(width / 2 - 50);
		rect.setTranslateY(height / 2 - 50);
		root.getChildren().add(rect);

		// animate
		// static access
		JFXAnimator.animateProperty(rect.opacityProperty(), 1000, 500, 10, 0, 1);

		// normal access
		JFXAnimator animator = new JFXAnimator(rect.rotateProperty());
		animator.start(0, 500, 0, 0, 360);

	}

	public static void main(String[] args) {
		launch(args);
	}

}
