import org.joda.time.DateTime;
import org.joda.time.Days;

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
    public void updateCard(int index, boolean remembered) {
        if (remembered) {
            if (cards.get(index).interval == 0) {
                Card _Card = new Card(cards.get(index).front, cards.get(index).back);
                _Card.interval = 1;
                cards.set(index, _Card);
            } else {
                
            }
        }
    }
}