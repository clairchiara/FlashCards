import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface  extends JFrame implements ActionListener {
    private JButton newCard;
    private JButton showDeck;
    private JButton study;
    private JPanel panel;
    private JButton add;
    private Deck deck;
    private JTextField front;
    private JTextField back;
    private JFrame addCardWindow;
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newCard) {

            addCardWindow = new JFrame("Add Card");
            JPanel mainPanel = new JPanel(new BorderLayout());
            JPanel textPanel = new JPanel(new BorderLayout());
            front = new JTextField("Front");
            back = new JTextField("Back");

            add = new JButton("Add");
            add.addActionListener(this);
            mainPanel.add(textPanel, BorderLayout.NORTH);
            mainPanel.add(add, BorderLayout.SOUTH);
            textPanel.add(front, BorderLayout.NORTH);
            textPanel.add(back, BorderLayout.SOUTH);
            addCardWindow.add(mainPanel);
            addCardWindow.pack();
            addCardWindow.setVisible(true);
        } else if (e.getSource() == add) {
            deck.addCard(front.getText(), back.getText());
            addCardWindow.dispose();
        }
    }
    public Interface(Deck _deck) {
        deck = _deck;
        newCard = new JButton("Add Card");
        showDeck = new JButton("Show Deck");
        study = new JButton("Study");
        panel = new JPanel(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.add(newCard, BorderLayout.WEST);
        panel.add(showDeck, BorderLayout.EAST);
        panel.add(study, BorderLayout.SOUTH);
        newCard.addActionListener(this);
        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
    public static void error(String message) {

        JFrame frame = new JFrame("FlashCards");
        JTextField messageField = new JTextField(message);

        messageField.setEditable(false);
        frame.add(messageField);
        frame.pack();
        frame.setVisible(true);
    }
}