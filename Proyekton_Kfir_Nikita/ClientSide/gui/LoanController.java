package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import GUI_Handler.switchScreen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import client.ClientUI; // Connect with ClientUI for communication with the server

public class LoanController {

	@FXML
	private Button btnExit; // Exit button

	@FXML
	private Button btnExit1; // Back button

	@FXML
	private Button BtnSaveLoaner; // Save loaner details (Book, ID, Dates)

	@FXML
	private DatePicker loandate; // Loan date picker

	@FXML
	private DatePicker retunrdate; // Return date picker

	@FXML
	private Button BtnBarcode;

	@FXML
	private ListView<String> BooksList;

	@FXML
	private TextField BarcodeText;

	@FXML
	private TextField subscriberIDText;

	@FXML
	// Map to store book barcodes and their corresponding names
	private final Map<String, String> bookMap = new HashMap<>();

	@FXML
	public void initialize() {
		// Set today's date in the loan date picker and configure its formatter
		loandate.setValue(LocalDate.now());
		loandate.setConverter(createDateConverter());
		loandate.setDisable(true); // Make the loan date picker non-editable

		// Set the return date as two weeks from today and configure its formatter
		retunrdate.setValue(LocalDate.now().plusWeeks(2));
		retunrdate.setConverter(createDateConverter());

		// Populate the book map with book barcodes and names
		bookMap.put("9780061120084", "To Kill a Mockingbird – Harper Lee");
		bookMap.put("9780141439518", "Pride and Prejudice – Jane Austen");
		bookMap.put("9780451524935", "1984 – George Orwell");
		bookMap.put("9780743273565", "The Great Gatsby – F. Scott Fitzgerald");
		bookMap.put("9781503280786", "Moby-Dick – Herman Melville");
		bookMap.put("9780141441146", "Jane Eyre – Charlotte Brontë");
		bookMap.put("9780141439556", "Wuthering Heights – Emily Brontë");
		bookMap.put("9780316769488", "The Catcher in the Rye – J.D. Salinger");
		bookMap.put("9780486415871", "Crime and Punishment – Fyodor Dostoevsky");
		bookMap.put("9780060850524", "Brave New World – Aldous Huxley");

		// Populate the ListView with book names
		BooksList.getItems().addAll(bookMap.values());

		// Limit subscriber ID input to 9 numeric characters only
		subscriberIDText.setTextFormatter(new TextFormatter<String>(change -> {
			String newText = change.getControlNewText();
			// Allow input only if it's numeric and 9 characters or less
			if (newText.matches("\\d{0,9}")) {
				return change; // Allow valid input
			}
			return null; // Reject invalid input
		}));

		// Limit barcode input to 13 characters
		BarcodeText.setTextFormatter(new TextFormatter<String>(change -> {
			if (change.getControlNewText().matches("\\d{0,13}")) {
				return change; // Allow the input if it's 13 characters or less
			}
			return null; // Reject input if it's more than 13 characters
		}));

		// Change the screen loan icon to be group 6 icon from Internet URL
		Platform.runLater(() -> {
			Stage stage = (Stage) loandate.getScene().getWindow();
			stage.getIcons().add(new Image(
					"https://spinninrecords.com/uploads/profile/images/1e/c4/74/b1/1ec474b14cf442989a3b30f3835865ff.png?1607347164"));
		});
	}

	// Creates a StringConverter for DatePicker
	private StringConverter<LocalDate> createDateConverter() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return new StringConverter<LocalDate>() {
			@Override
			public String toString(LocalDate date) {
				return (date != null) ? formatter.format(date) : "";
			}

			@Override
			public LocalDate fromString(String string) {
				return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
			}
		};
	}

	// Method called when the "Barcode" button is clicked
	@FXML
	private void BarcodeScan(ActionEvent event) {
		String enteredBarcode = BarcodeText.getText().trim();
		
		if (enteredBarcode.length() < 13) {
	        // Show error message if the barcode is incomplete
	        showError("Book's barcode must be 13 digits long.");
	    } else {
	    	if (enteredBarcode.length() == 13 && bookMap.containsKey(enteredBarcode)) {
				String bookName = bookMap.get(enteredBarcode);
				BooksList.getSelectionModel().select(bookName);
				BooksList.scrollTo(bookName);
				// Perform save action here
		        System.out.println("Book's barcode is: " + enteredBarcode);
		        // Additional logic for saving the barcode...
			}
	    	else {
				Alert alert = new Alert(Alert.AlertType.WARNING, "No book found for the entered barcode.", ButtonType.OK);
				alert.showAndWait();
			}
	    }
	}

	// Method called when the "Exit" button is clicked
	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/ExitFrame", "Bye");
	}

	// Method called when the "Back" button is clicked
	@FXML
	public void getBackBtn(ActionEvent event) throws Exception {
		// Implement logic to switch back to the previous screen
		switchScreen.switchTo(event, "/gui/OptionFrame", "Back");
	}
	
	@FXML
	public void SaveLoanerID(ActionEvent event) throws Exception {
	    // Check if the subscriber ID is less than 9 characters
	    String subscriberID = subscriberIDText.getText();
	    String barcode = BarcodeText.getText();
	    LocalDate loanDate = loandate.getValue();
	    LocalDate returnDate = retunrdate.getValue();

	    if (subscriberID.length() < 9) {
	        // Show error message if the ID is incomplete
	        showError("Subscriber ID must be 9 digits long.");
	    } else if (barcode.isEmpty() || !bookMap.containsKey(barcode)) {
	        // Show error message if barcode is empty or not found
	        showError("Please scan a valid barcode.");
	    } else {
	        // Run network communication on a separate thread to avoid blocking the UI
	        new Thread(() -> {
	            try {
	                // Send loan data to the server
	                ClientUI.chat.sendLoanRequest(barcode, subscriberID, loanDate.toString(), returnDate.toString());

	                // Handle server response asynchronously
	                ClientUI.chat.setResponseHandler(response -> {
	                    Platform.runLater(() -> {
	                        if ("success".equalsIgnoreCase(response)) {
	                            showSuccess("Loan request sent successfully!");
	                        } else {
	                            showError("Failed to process the loan request. Please try again.");
	                        }
	                    });
	                });

	            } catch (Exception e) {
	                Platform.runLater(() -> {
	                    showError("An error occurred while processing the loan request: " + e.getMessage());
	                });
	            }
	        }).start();
	    }
	}


	// Helper method to show error messages
	private void showError(String message) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}

	// Helper method to show success messages
	private void showSuccess(String message) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Success");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
}
