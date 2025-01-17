package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import GUI_Handler.switchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

public class LoanController {

	@FXML
	private Button btnExit; // Exit button

	@FXML
	private Button btnExit1; // Back button

	@FXML
	private DatePicker loandate; // Loan date picker

	@FXML
	private DatePicker retunrdate; // Return date picker

	@FXML
	public void initialize() {
		// Set today's date in the loan date picker and configure its formatter
		loandate.setValue(LocalDate.now());
		loandate.setConverter(createDateConverter());
		loandate.setDisable(true); // Make the loan date picker non-editable

		// Set the return date as two weeks from today and configure its formatter
		retunrdate.setValue(LocalDate.now().plusWeeks(2));
		retunrdate.setConverter(createDateConverter());
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