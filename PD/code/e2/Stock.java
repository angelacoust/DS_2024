package e2;
import java.util.ArrayList;
import java.util.List;

public class Stock {
    private final String symbol;
    private double closePrice;
    private double maxPrice;
    private double minPrice;
    private int volume;
    private final List<Observer> observers = new ArrayList<>();

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void setStockData(double closePrice, double maxPrice, double minPrice, int volume) {
        this.closePrice = closePrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.volume = volume;
        notifyObservers();
    }

    public String getSymbol() { return symbol; }
    public double getClosePrice() { return closePrice; }
    public double getMaxPrice() { return maxPrice; }
    public double getMinPrice() { return minPrice; }
    public int getVolume() { return volume; }
}

