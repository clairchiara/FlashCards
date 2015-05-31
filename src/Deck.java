import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;

public class Deck {
    public class Card {
        public String getFront() {
            return front;
        }
        public String getBack() {
            return back;
        }
        private String front;
        private String back;
        private int interval = 0;
        public int getInterval() {
            return interval;
        }
        public DateTime getLastUpdated() {
            return lastUpdated;
        }
        private DateTime lastUpdated;
        public Card(String front, String back) {
            this.front = front;
            this.back = back;
            this.lastUpdated = new DateTime();
        }
    }
    private ArrayList<Card> cards;
    public Deck() {
        this.cards = new ArrayList<Card>();
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
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
                Card _Card = new Card(cards.get(index).front, cards.get(index).back);
                _Card.interval = cards.get(index).interval * 2;
                cards.set(index, _Card);
            }
        } else {
            if (cards.get(index).interval != 0) {
                Card _Card = new Card(cards.get(index).front, cards.get(index).back);
                _Card.interval = 0;
                cards.set(index, _Card);
            }
        }
    }
}