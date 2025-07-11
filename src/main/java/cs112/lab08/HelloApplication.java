package cs112.lab08;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;             //class for getting positions for alignment
import javafx.scene.image.ImageView;    //class for image viewing
import javafx.stage.Stage;              //class for GUI window
import javafx.scene.Scene;              //class for specific view in GUI window
import javafx.scene.layout.VBox;        //class for layout pane, organized top-to-bottom
import javafx.scene.control.Label;      //class for label component
import javafx.scene.control.Button;     //class for button component
import javafx.event.EventHandler;       //interface for handling events
import javafx.event.ActionEvent;        //class for type of event for action (like button or key pressed)

import java.util.Random;

import java.io.IOException;

public class HelloApplication extends Application {

    //CONSTANTS
    private Random rng = new Random();



    //array of LoteriaCards to use for game:
    private static final LoteriaCard[] LOTERIA_CARDS = {
            new LoteriaCard("Las matemáticas", "1.png", 1),
            new LoteriaCard("Las ciencias", "2.png", 2),
            new LoteriaCard("La Tecnología", "8.png", 8),
            new LoteriaCard("La ingeniería", "9.png", 9),
    };
    private static final int NUMBER_OF_CARDS = LOTERIA_CARDS.length; // create a constant for the number of cards in the deck


    @Override
    public void start(Stage stage) throws IOException {
        //removed FXML code, fill this in with components, scene, stage, etc.
        // set up components here
        Label titleLabel = new Label("Welcome to EChALE STEM Loteria!");



        // create a default card, call its getImage method, use that image as the image for the imageView:
        ImageView cardImageView = new ImageView(new LoteriaCard().getImage());
        cardImageView.setFitWidth(281.5);  // w = 50% of image width
        cardImageView.setFitHeight(356.5); // h = 50% of image height


        Label messageLabel = new Label("Click the button below to draw a random card from the deck.");
        messageLabel.setWrapText(true);

        Button drawCardButton = new Button("Draw Random Card");
        // when this button is clicked, it should set the image and the text to be a random card from the deck (in the LOTERIA_CARDS array)
        // so let's do that:
        drawCardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int randomInt = rng.nextInt(NUMBER_OF_CARDS); // will generate a random number up to, but not including, NUMBER_OF_CARDS
                cardImageView.setImage(LOTERIA_CARDS[randomInt].getImage());
                messageLabel.setText(LOTERIA_CARDS[randomInt].getCardName());
            }
        });



        // add components here, set up the single VBox layout, as specified in the README
        VBox layout = new VBox(titleLabel, cardImageView, messageLabel, drawCardButton);
        layout.setSpacing(20); // create some space between all the elements
        layout.setAlignment(Pos.CENTER); // center the elements in the VBox

        // set up scene and show it
        Scene primaryScene = new Scene(layout, 350, 500);
        stage.setScene(primaryScene);
        stage.setTitle("EChALE STEM Loteria"); //set title of main window
        stage.setResizable(false); // set resizable attribute to window
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}