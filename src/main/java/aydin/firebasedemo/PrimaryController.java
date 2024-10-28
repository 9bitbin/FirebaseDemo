package aydin.firebasedemo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController {
    @FXML
    private TextField ageTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button readButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button switchSecondaryViewButton;

    @FXML
    private Button writeButton;

    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    // Getter method to retrieve the list of users
    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    // Method to initialize bindings and UI elements
    void initialize() {
        // Set up a data view model to bind nameTextField and enable/disable the write button accordingly
        AccessDataView accessDataViewModel = new AccessDataView();
        nameTextField.textProperty().bindBidirectional(accessDataViewModel.personNameProperty());
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());
    }

    // Method called when the read button is clicked
    @FXML
    void readButtonClicked(ActionEvent event) {
        readFirebase();
    }

    // Method called when the register button is clicked
    @FXML
    void registerButtonClicked(ActionEvent event) {
        registerUser();
    }

    // Method called when the write button is clicked
    @FXML
    void writeButtonClicked(ActionEvent event) {
        addData();
    }

    // Method to switch to the secondary view
    @FXML
    private void switchToSecondary() throws IOException {
        DemoApp.setRoot("secondary");
    }

    // Method to read data from Firebase Firestore
    public boolean readFirebase() {
        key = false;

        // Asynchronously retrieve all documents from the "Persons" collection
        ApiFuture<QuerySnapshot> future = DemoApp.fstore.collection("Persons").get();
        // future.get() blocks on response to retrieve the documents
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if (documents.size() > 0) {
                System.out.println("Getting (reading) data from Firebase database...");
                listOfUsers.clear();
                // Iterate through each document and extract user data
                for (QueryDocumentSnapshot document : documents) {
                    outputTextArea.setText(outputTextArea.getText() + document.getData().get("Name") + " , Age: " +
                            document.getData().get("Age") + " \n ");
                    System.out.println(document.getId() + " => " + document.getData().get("Name"));
                    // Create a new Person object and add it to the list of users
                    person = new Person(String.valueOf(document.getData().get("Name")),
                            Integer.parseInt(document.getData().get("Age").toString()));
                    listOfUsers.add(person);
                }
            } else {
                System.out.println("No data");
            }
            key = true;
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return key;
    }

    // Method to register a new user in Firebase Authentication
    public boolean registerUser() {
        // Create a request object to register a new user with specified attributes
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user222@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);

        UserRecord userRecord;
        try {
            // Create a new user in Firebase Authentication
            userRecord = DemoApp.fauth.createUser(request);
            System.out.println("Successfully created new user with Firebase Uid: " + userRecord.getUid()
                    + " check Firebase > Authentication > Users tab");
            return true;
        } catch (FirebaseAuthException ex) {
            System.out.println("Error creating a new user in the Firebase");
            return false;
        }
    }

    // Method to add data to Firebase Firestore
    public void addData() {
        // Create a new document in the "Persons" collection with a unique identifier
        DocumentReference docRef = DemoApp.fstore.collection("Persons").document(UUID.randomUUID().toString());

        // Create a map to store user data (name and age)
        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameTextField.getText());
        data.put("Age", Integer.parseInt(ageTextField.getText()));

        // Asynchronously write data to Firestore
        ApiFuture<WriteResult> result = docRef.set(data);
    }
}