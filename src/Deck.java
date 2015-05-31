import org.joda.time.DateTime;
import java.util.ArrayList;

public class Deck {
    public class Card {
        private String front;
        private String back;
        private int interval = 0;
        private DateTime lastUpdated;
        public Card(String front, String back) {
            this.front = front;
            this.back = back;
            this.lastUpdated = new DateTime();
        }
    }
    public Deck() {
        this.cards = new ArrayList<Card>();
    }
    private ArrayList<Card> cards;
    public void addCard(String front, String back) {
        cards.add(new Card(front, back));
    }
    public void deleteCard(int index) {
        try {
            cards.remove(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            Interface.error("No card found at index " + index);
        }
    }
    public ArrayList<Card> cardsToStudy() {
        ArrayList<Card> _cards = new ArrayList<Card>();
        for (Card c : cards) {
            if (c.lastUpdated.)
        }
    }
}