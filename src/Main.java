import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Deck deck = new Deck();

        try {
            deck.open(System.getProperty("user.home") + File.separator + "flashCardsDeck");
        } catch (FileNotFoundException e) {
        }
        Interface _interface = new Interface(deck);

    }
}