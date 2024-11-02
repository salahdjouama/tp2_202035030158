package salah.tp3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HelloController {

    @FXML
    private TextField namefld;
    @FXML
    private TextField portfld;
    @FXML
    private TextField serverfld;

    @FXML
    private TextField msgfld;
    @FXML
    private TextArea logsarea;

    @FXML
     Button sendbtn;


    private List<TextField> textFields = new ArrayList<>(); // Declare here



    @FXML
    public void initialize() {
        // Initialize the list of text fields after FXML has injected them
        textFields.add(namefld);
        textFields.add(portfld);
        textFields.add(serverfld);
        textFields.add(msgfld);
    }

    @FXML
    protected void onSendBtnClick() {
        boolean emp = checkFieldsEmpty(textFields)
                ,regex = checkregex(textFields);
        if (emp && regex) {
            logsarea.setText("everything is ok");
        }
    }

    private boolean checkFieldsEmpty(List<TextField> fields) {
        boolean flag = true;
        for (TextField field : fields) {
            if (field != null) {
                String text = field.getText();
                if (text.isBlank()) {
                    field.setStyle("-fx-border-color: red;");
                    flag = false;
                } else {
                    field.setStyle("");
                }
            } else {
                flag = false;
            }
        }
        return flag;
    }

    private boolean checkregex(List<TextField> fields) {
        boolean flag = true;
        for (TextField field : fields) {
            if (field != null) { // Check for null here
                String text = field.getText();
                String id = field.getId();
                if (id.equals("namefld")) {
                    if (text.matches("^[a-zA-Z0-9_]{1,15}$")) {
                        field.setStyle("");
                    } else {
                        field.setStyle("-fx-border-color: red;");
                        flag = false;
                    }
                } else if (id.equals("portfld")) {
                    if (text.matches("^([1-5]?[0-9]{4}|60000)$")) {
                        field.setStyle("");
                    } else {
                        field.setStyle("-fx-border-color: red;");
                        flag = false;
                    }
                } else if (id.equals("serverfld")) {
                    if (text.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
                        field.setStyle("");
                    } else {
                        field.setStyle("-fx-border-color: red;");
                        flag = false;
                    }
                }
            } else {
                flag = false; // Handle null field case
            }
        }
        return flag;
    }

}