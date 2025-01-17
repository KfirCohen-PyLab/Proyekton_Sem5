package GUI_Handler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class switchScreen {

	/**
	 * @param event The action event triggered by the user (e.g., button click).
	 * @param Frame The name of the FXML file (without the `.fxml` extension) for
	 *              the target screen.
	 * @param Title The title of the new window.
	 * @throws Exception if there is an error loading the FXML or CSS files.
	 */
	public static void switchTo(javafx.event.ActionEvent event, String Frame, String Title) throws Exception {
		// Construct the FXML and CSS file paths based on the provided frame name
		String FrameFxml = Frame + ".fxml";
		String FrameCss = Frame + ".css";
		((Node) event.getSource()).getScene().getWindow().hide();

		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(switchScreen.class.getResource(FrameFxml));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(switchScreen.class.getResource(FrameCss).toExternalForm());

		primaryStage.setTitle(Title);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
