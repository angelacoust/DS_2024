package e2;

import java.util.ArrayList;
import java.util.List;

public class SimpleClient implements Observer {
    private List<String> notifications = new ArrayList<>();

    @Override
    public void update(Stock stock) {
        notifications.add("Simple: Symbol=" + stock.getSymbol() + ", Close=" + stock.getClosePrice());
    }

    public List<String> getNotifications() {
        return notifications;
    }
}


