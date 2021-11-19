package numbergame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NumberGame extends Application {
	private final Text text = new Text();
	private final TextField txtGuess = new TextField();
	private int target = 0, n = -1;

	private boolean flag = false;
	Scene scene;

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Number Game");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("higherLower cursor.png")));

		primaryStage.setWidth(800);
		primaryStage.setHeight(600);

		Image image = new Image(getClass().getResourceAsStream("higherLower.png"));
		Label label = new Label("\n", new ImageView(image));
		scene = new Scene(txtGuess, 500, 500);
		scene.setCursor(new ImageCursor(image));

		text.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
		text.setText("Let's start the game! \nHow many time do you want to play ?");

		txtGuess.setPromptText("Enter A Number from 0-100");
		txtGuess.setPrefColumnCount(16);

		Button submit = new Button("Submit");
		Button restart = new Button("Restart");
		Button exitBtn = new Button("Exit");
		exitBtn.setOnAction(e -> Platform.exit());

		submit.setCursor(Cursor.HAND);
		restart.setCursor(Cursor.HAND);
		exitBtn.setCursor(Cursor.HAND);

		VBox pane = new VBox();
		pane.setSpacing(10);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(25, 25, 25, 25));

		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(10);
		txtGuess.setPrefWidth(270);

		hBox.getChildren().addAll(txtGuess, submit, restart, exitBtn);

		pane.getChildren().addAll(label, text, hBox);

		primaryStage.setScene(new Scene(pane, 300, 250));

		primaryStage.show();

		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					if (flag == false) {

						String str = txtGuess.getText();
						n = Integer.parseInt(str);
						if (n > 0 && !str.isEmpty()) {
							text.setText("Guess the number");
							txtGuess.clear();
							target = (int) Math.floor(Math.random() * 100) + 1;

							System.out.println(target);
							flag = true;
						} else {
							text.setText("Please enter a possitive number!");
							txtGuess.clear();
						}

					} else {
						guess();
					}
				} catch (Exception e) {
					text.setText("Invalid Input.\nPlease enter a possitive number!");
					txtGuess.clear();
					return;
				}

			}
		});

		restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				restart();
				flag = false;
			}
		});

	}

	public void guess() {
		try {
			String str = txtGuess.getText();

			int guess = Integer.parseInt(str);
			if (n >= 0 && !str.isEmpty()) {
				if (guess < 0 || guess > 100) {
					text.setText("Out of the range and try again.");
					txtGuess.clear();
					return;
				} else if (guess > target) {
					text.setText("The guess is higher than target. Remaining attempts are: " + (n - 1));
					txtGuess.clear();
				} else if (guess < target) {
					text.setText("The guess is lower than the target. Remaining attempts are: " + (n - 1));
					txtGuess.clear();
				} else if (guess == target) {
					text.setText("That was correct, you win!\nRestart for a new game!");
					txtGuess.clear();
					return;
				}
				n--;
				if (n == 0) {
					text.setText(
							"Game over!You lose!\nThe number was " + target + "." + "\nPlease restart and try again!");
					txtGuess.clear();
					return;

				}

			} else {
				text.setText("Invalid Input.\nTry Again!");
				txtGuess.clear();
				return;
			}
		}

		catch (Exception e) {
			text.setText("Invalid Input.\nPlease Restart!");
			txtGuess.clear();
			return;
		}

	}

	public void restart() {
		txtGuess.clear();

		text.setText("Let's start the game! \nHow many time do you want to play ?");

		n = -1;

	}

	public static void main(String[] args) {
		launch(args);

	}

}
