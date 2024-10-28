package aydin.firebasedemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;

import com.google.firebase.auth.*;
import com.google.cloud.firestore.*;
import com.google.api.core.ApiFuture;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DemoApp extends Application {
    public static Scene scene;

    // Firestore instance for connecting with Firestore database
    public static Firestore fstore;
    // FirebaseAuth instance for managing Firebase authentication
    public static FirebaseAuth fauth;
    // FirestoreContext object used for initializing Firestore connection
    private final FirestoreContext contxtFirebase = new FirestoreContext();

    // Overriding the start method of Application to initialize the application UI and Firebase
    @Override
    public void start(Stage stage) throws IOException {
        // Initialize Firestore and FirebaseAuth instances
        fstore = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();

        // Load the primary FXML view and create a scene with dimensions 640x480
        scene = new Scene(loadFXML("primary"), 640, 480);
        // Set the scene on the stage and show it
        stage.setScene(scene);
        stage.show();
    }

    // Method to set the root of the current scene to a new FXML view
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Method to load an FXML file and return its root element
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DemoApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // Main method that launches the JavaFX application
    public static void main(String[] args) {
        launch();
    }
}
