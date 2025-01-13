package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GUI_Handler.switchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
public class LoanController {

	@FXML
	private Button btnExit; // Exit button

	@FXML
	private Button btnExit1; // Back button
	
	@FXML
	private Button BtnSaveLoaner; // Save loaner details Book, ID, Dates
	
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
        
        
        // Limit barcode input to 13 characters
        BarcodeText.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().length() <= 13) {
                return change; // Allow the input if it's 13 characters or less
            }
            return null; // Reject input if it's more than 13 characters
        }));
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
		// Implement this method if required
		@FXML
		private void BarcodeScan(ActionEvent event) {
			String enteredBarcode = BarcodeText.getText().trim();
			if (enteredBarcode.length() == 13 && bookMap.containsKey(enteredBarcode)) {
	            String bookName = bookMap.get(enteredBarcode);
	            BooksList.getSelectionModel().select(bookName);
	            BooksList.scrollTo(bookName);
			
			//if (bookMap.containsKey(enteredBarcode)) {
	         //   String bookName = bookMap.get(enteredBarcode);
	        //    BooksList.getSelectionModel().select(bookName);
	         //   BooksList.scrollTo(bookName);  // Ensure the selected book is visible
	        } else {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "No book found for the entered barcode.", ButtonType.OK);
	            alert.showAndWait();
	        }
    }

	// Method called when the "Exit" button is clicked
	// Switches back to the Exit screen
	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/ExitFrame", "Bye");
	}

	// Method called when the "Back" button is clicked
	// Implement this method if required
	@FXML
	public void getBackBtn(ActionEvent event) throws Exception {
		// Implement logic to switch back to the previous screen
		switchScreen.switchTo(event, "/gui/OptionFrame", "Back");
	}
}