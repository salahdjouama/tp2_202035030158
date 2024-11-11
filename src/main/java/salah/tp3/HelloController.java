package salah.tp3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class HelloController {



    @FXML
    public TextField namefld;
    @FXML
    private TextField portfld;
    @FXML
    private TextField serverfld;

    @FXML
    private TextField msgfld;
    @FXML
    protected   TextArea logsarea;

    private static String logs;


    Client ca;

    @FXML
     Button sendbtn;


    private List<TextField> textFields = new ArrayList<>(); // Declare here
    public static String[] infos = new String[4]; //each field is gonna be in a cell



    @FXML
    public void initialize() {
        // Initialize the list of text fields after FXML has injected them
        textFields.add(namefld);
        textFields.add(portfld);
        textFields.add(serverfld);
        textFields.add(msgfld);
    }

    boolean client_created = false;
    @FXML
    protected void onSendBtnClick() {
        boolean emp = checkFieldsEmpty(textFields);
        boolean regex = checkregex(textFields);


        if (emp && regex ) {
            if(client_created){
                ca.sendmsg(infos[3]);
            }else{
                ca = new Client(serverfld.getText(),Integer.parseInt(infos[1]),this);
                client_created = true;
            }
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
                        infos[0] = text;
                    } else {
                        field.setStyle("-fx-border-color: red;");
                        flag = false;
                    }
                } else if (id.equals("portfld")) {
                    if (text.matches("^([1-5]?[0-9]{4}|60000)$")) {
                        field.setStyle("");
                        infos[1] = text;
                    } else {
                        field.setStyle("-fx-border-color: red;");
                        flag = false;
                    }
                } else if (id.equals("serverfld")) {
                    if (text.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
                        field.setStyle("");
                        infos[2] = text;
                    } else {
                        field.setStyle("-fx-border-color: red;");
                        flag = false;
                    }
                } else if (id.equals("msgfld")) {
                    infos[3] = text;
                }
            } else {
                flag = false; // Handle null field case
            }
        }
        return flag;
    }


}