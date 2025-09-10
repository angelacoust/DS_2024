package e2;

import java.util.ArrayList;
import java.util.List;

public class FocusedClient implements Observer {
    private String symbol;
    private List<String> notifications = new ArrayList<>();

    public FocusedClient(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void update(Stock stock) {
        if (stock.getSymbol().equals(symbol)) {
            notifications.add("Focused: Symbol=" + stock.getSymbol() + ", Close=" + stock.getClosePrice());
        }
    }

    public List<String> getNotifications() {
        return notifications;
    }
}
