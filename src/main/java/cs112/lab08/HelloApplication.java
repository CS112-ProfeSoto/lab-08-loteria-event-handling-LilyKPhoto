package cs112.lab08;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;             //class for getting positions for alignment
import javafx.scene.image.ImageView;    //class for image viewing
import javafx.scene.text.TextAlignment; //needed to re-align text
import javafx.scene.text.Font;          //class for creating Font objects
import javafx.scene.text.FontPosture;   //class for font italic or non-italic
import javafx.scene.text.FontWeight;    //class for font bold or non-bold
import javafx.stage.Stage;              //class for GUI window
import javafx.scene.Scene;              //class for specific view in GUI window
import javafx.scene.layout.VBox;        //class for layout pane, organized top-to-bottom
import javafx.scene.control.Label;      //class for label component
import javafx.scene.control.Button;     //class for button component
import javafx.scene.control.ProgressBar; //class for progress bar
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
    private static int numPicked = 0;
    private static final LoteriaCard[] PICKED_CARDS = new LoteriaCard[NUMBER_OF_CARDS]; // HC - create an empty array to store the cards we've already picked


    @Override
    public void start(Stage stage) throws IOException {
        //removed FXML code, fill this in with components, scene, stage, etc.
        // set up components here
        Label titleLabel = new Label("Welcome to EChALE STEM Loteria!");
        titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, FontPosture.REGULAR, 20)); // oh hell yeah you can set the font to comic sans!


        // create a default card, call its getImage method, use that image as the image for the imageView:
        ImageView cardImageView = new ImageView(new LoteriaCard().getImage());
        cardImageView.setFitWidth(281.5);  // w = 50% of image width
        cardImageView.setFitHeight(356.5); // h = 50% of image height


        Label messageLabel = new Label("Click the button below to draw a random card from the deck.");
        messageLabel.setWrapText(true);

        Button drawCardButton = new Button("Draw Random Card");

        ProgressBar gameProgressBar = new ProgressBar(); // i think this might be the only Hacker Challenge i've done so far lol
        gameProgressBar.setMinWidth(200);
        // so i'm not sure why, but on my laptop, when the program starts up, the progress bar goes from left to right (not complaining though, it looks neat)
        // this doesn't happen when i set it to 0, so maybe it's a default behavior?

        // when this button is clicked, it should set the image and the text to be a random un-picked card from the deck (in the LOTERIA_CARDS array)
        // BUT, if all cards have been picked, we should disable the program
        // so let's do that:
        drawCardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(numPicked != NUMBER_OF_CARDS) {
                    int randomInt = rng.nextInt(NUMBER_OF_CARDS); // will generate a random number up to, but not including, NUMBER_OF_CARDS
                    // check if the card at rng's index has been picked already (is in the picked cards array):
                    for (int i = 0; i < PICKED_CARDS.length; i++) {
                        if(LOTERIA_CARDS[randomInt].equals(PICKED_CARDS[i])) {
                            // if it has been picked already...
                            randomInt = rng.nextInt(NUMBER_OF_CARDS);
                            i = 0; // is this allowed? forcing the for loop to start over like this? (edit: yes it is!)
                        }
                    }
                    PICKED_CARDS[numPicked] = LOTERIA_CARDS[randomInt];
                    numPicked++;
                    gameProgressBar.setProgress((double) numPicked / NUMBER_OF_CARDS); // increment the progress bar
                    cardImageView.setImage(LOTERIA_CARDS[randomInt].getImage());
                    messageLabel.setText(LOTERIA_CARDS[randomInt].getCardName());
                }
                else {
                    cardImageView.setImage(new LoteriaCard().getImage()); // set cardImageView to default LoteriaCard's image
                    messageLabel.setText("GAME OVER. No more cards!\nExit and run program again to reset ^_^");
                    messageLabel.setTextAlignment(TextAlignment.CENTER);
                    drawCardButton.setDisable(true); // disables the button
                    gameProgressBar.setStyle("-fx-accent: red"); // set the color to red
                }
            }
        });

        // add components here, set up the single VBox layout, as specified in the README
        VBox layout = new VBox(titleLabel, cardImageView, messageLabel, drawCardButton, gameProgressBar);
        layout.setSpacing(5); // create some space between all the elements

        layout.setAlignment(Pos.CENTER); // center the elements in the VBox

        // set up scene and show it
        Scene primaryScene = new Scene(layout, 350, 500);
        stage.setScene(primaryScene);
        stage.setTitle("EChALE STEM Loteria"); //set title of main window
        stage.setResizable(false); // set resizable attribute of window
        stage.show(); // show the stage!
    }

    public static void main(String[] args) {
        launch();
    }
}