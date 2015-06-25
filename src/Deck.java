import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.*;
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
        private Card(String front, String back, int interval, DateTime lastUpdated) {
            this.front = front;
            this.back = back;
            this.interval = interval;
            this.lastUpdated = lastUpdated;
        }
        private String writeToString() {
            return front + " FLIPPING_SIDE_SEPARATOR " + back + " AGE_SEPARATOR " + interval + " UPDATE_SEPARATOR "
                    + lastUpdated.toString(DateTimeFormat.forPattern("dd, MM, yyyy, HH:mm:ss"));
        }
        public String toString() {
            return front;
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
    public void deleteCard(int index) throws ArrayIndexOutOfBoundsException {
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
    public void save(String fileName) throws FileNotFoundException {

        BufferedWriter bufferedWriter = null;
        File file = null;

        try {

            file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            for (int i = 0; i< cards.size(); i++) {
                bufferedWriter.write(cards.get(i).writeToString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void open(String fileName) throws FileNotFoundException {
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(fileName));
            cards =  new ArrayList<Card>();

            int indexFlipSide;
            int indexAge;
            int indexUpdated;
            String line;
            String front;
            String back;
            int interval;
            DateTime lastUpdated;

            while ((line = reader.readLine()) != null) {
                indexFlipSide = line.indexOf(" FLIPPING_SIDE_SEPARATOR ");
                indexAge = line.indexOf(" AGE_SEPARATOR ");
                indexUpdated = line.indexOf(" UPDATE_SEPARATOR ");
                front = line.substring(0, indexFlipSide);
                back = line.substring(indexFlipSide + " FLIPPING_SIDE_SEPARATOR ".length(), indexAge);
                interval = Integer.parseInt(line.substring(indexAge + " AGE_SEPARATOR ".length(), indexUpdated));
                lastUpdated = DateTime.parse(line.substring(indexUpdated + " UPDATE_SEPARATOR ".length(),
                        line.length() - 1), DateTimeFormat.forPattern("dd, MM, yyyy, HH:mm:ss"));
                cards.add(new Card(front, back, interval, lastUpdated));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}