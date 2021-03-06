import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class Interface  extends JFrame implements ActionListener {
    private JButton newCard;
    private JButton showDeck;
    private JButton study;
    private JPanel panel;
    private JButton add;
    private Deck deck;
    private JTextField setFrontField;
    private JTextField setBackField;
    private JTextField showFrontField;
    private JTextField showBackField;
    private JFrame addCardWindow;
    private JFrame studyWindow;
    private JButton showAnswer;
    private JButton remembered;
    private JButton forgotten;
    private String back;
    private int index = 0;
    private JFrame finished;
    private JButton OK;
    private JList cardList;
    private JFrame showDeckWindow;
    public Interface(Deck _deck) {
        deck = _deck;
        newCard = new JButton("Add Card");
        showDeck = new JButton("Show Deck");
//        showDeck.setEnabled(false);
        study = new JButton("Study");
        panel = new JPanel(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.add(newCard, BorderLayout.WEST);
        panel.add(showDeck, BorderLayout.EAST);
        panel.add(study, BorderLayout.SOUTH);
        newCard.addActionListener(this);
        study.addActionListener(this);
        showDeck.addActionListener(this);
        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
    private void studyActions() {
        if (index < deck.getCards().size()) {
            if (Days.daysBetween(deck.getCards().get(index).getLastUpdated(), DateTime.now()).getDays() ==
                    deck.getCards().get(index).getInterval()) {
                if (studyWindow != null) studyWindow.dispose();

                JPanel panel1 = new JPanel(new BorderLayout());
                JPanel panel2 = new JPanel(new BorderLayout());
                JPanel panel3 = new JPanel(new BorderLayout());
                JPanel panel4 = new JPanel(new BorderLayout());

                showFrontField = new JTextField(deck.getCards().get(index).getFront());
                showBackField = new JTextField();
                back = deck.getCards().get(index).getBack();
                showFrontField.setEditable(false);
                showBackField.setEditable(false);
                studyWindow = new JFrame("Studying");
                showAnswer = new JButton("Show Answer");
                showAnswer.addActionListener(this);
                remembered = new JButton("Remembered");
                forgotten = new JButton("Forgotten");
                remembered.setEnabled(false);
                remembered.addActionListener(this);
                forgotten.setEnabled(false);
                forgotten.addActionListener(this);
                panel4.add(forgotten, BorderLayout.EAST);
                panel4.add(remembered, BorderLayout.WEST);
                panel3.add(panel4, BorderLayout.SOUTH);
                panel3.add(showBackField, BorderLayout.NORTH);
                panel2.add(showAnswer, BorderLayout.NORTH);
                panel2.add(panel3, BorderLayout.SOUTH);
                panel1.add(showFrontField, BorderLayout.NORTH);
                panel1.add(panel2, BorderLayout.SOUTH);
                studyWindow.add(panel1);
                studyWindow.pack();
                studyWindow.setVisible(true);
            } else {
                index++;
                studyActions();
            }
        } else {
            if (studyWindow != null) studyWindow.dispose();

            JPanel panel = new JPanel(new BorderLayout());
            JPanel panel2 = new JPanel(new BorderLayout());
            JTextField text = new JTextField("You have finished studying.");
            JTextField text2 = new JTextField("If no cards were shown, you didn't have any to study today.");

            text.setEditable(false);
            text2.setEditable(false);
            finished = new JFrame("Finished");
            OK = new JButton("OK");
            OK.addActionListener(this);
            panel.add(text, BorderLayout.NORTH);
            panel2.add(text2, BorderLayout.NORTH);
            panel2.add(OK, BorderLayout.SOUTH);
            panel.add(panel2, BorderLayout.SOUTH);
            finished.add(panel);
            finished.pack();
            finished.setVisible(true);
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newCard) {
            addCardWindow = new JFrame("Add Card");

            JPanel mainPanel = new JPanel(new BorderLayout());
            JPanel textPanel = new JPanel(new BorderLayout());

            setFrontField = new JTextField("Front");
            setBackField = new JTextField("Back");
            add = new JButton("Add");
            add.addActionListener(this);
            mainPanel.add(textPanel, BorderLayout.NORTH);
            mainPanel.add(add, BorderLayout.SOUTH);
            textPanel.add(setFrontField, BorderLayout.NORTH);
            textPanel.add(setBackField, BorderLayout.SOUTH);
            addCardWindow.add(mainPanel);
            addCardWindow.pack();
            addCardWindow.setVisible(true);
        } else if (e.getSource() == add) {
            deck.addCard(setFrontField.getText(), setBackField.getText());
            addCardWindow.dispose();
            try {
                deck.save(System.getProperty("user.home") + File.separator + "flashCardsDeck");
            } catch (FileNotFoundException e1) {
            }
        } else if (e.getSource() == study) {
            studyActions();
        } else if (e.getSource() == showAnswer) {
            showBackField.setText(back);
            remembered.setEnabled(true);
            forgotten.setEnabled(true);
        } else if (e.getSource() == remembered) {
            deck.updateCard(index, true);
            index++;
            studyActions();
        } else if (e.getSource() == forgotten) {
            deck.updateCard(index, false);
            index++;
            studyActions();
        } else if (e.getSource() == OK) {
            finished.dispose();
            index = 0;
            try {
                deck.save(System.getProperty("user.home") + File.separator + "flashCardsDeck");
            } catch (FileNotFoundException e1) {
            }
        } else if (e.getSource() == showDeck) {
            cardList = new JList(deck.getCards().toArray());
            cardList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            cardList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            cardList.setVisibleRowCount(-1);
            JScrollPane listScroller = new JScrollPane(cardList);
            listScroller.setPreferredSize(new Dimension(250, 80));
            showDeckWindow = new JFrame("Deck");

            JPanel panel = new JPanel(new BorderLayout());

            panel.add(cardList);
            showDeckWindow.add(panel);
            showDeckWindow.pack();
            showDeckWindow.setVisible(true);
        }
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