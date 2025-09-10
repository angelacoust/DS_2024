package e2;

import java.util.ArrayList;
import java.util.List;

public class DetailedClient implements Observer {
    private List<String> notifications = new ArrayList<>();

    @Override
    public void update(Stock stock) {
        notifications.add("Detailed: Symbol=" + stock.getSymbol() +
                ", Close=" + stock.getClosePrice() +
                ", Max=" + stock.getMaxPrice() +
                ", Min=" + stock.getMinPrice() +
                ", Volume=" + stock.getVolume());
    }

    public List<String> getNotifications() {
        return notifications;
    }
}
