package e3;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ColeccionMonedas3 implements Iterable<Moneda3> {
    private final List<Moneda3> monedas;
    private String paisParaIterar;
    private int modCount; // Contador de modificaciones

    public ColeccionMonedas3() {
        this.monedas = new ArrayList<>();
        this.paisParaIterar = null;
        this.modCount = 0;
    }

    public void addMoneda(Moneda3 moneda) {
        monedas.add(moneda);
        modCount++;
    }

    public void setPaisParaIterar(String pais) {
        this.paisParaIterar = pais;
    }

    @Override
    public Iterator<Moneda3> iterator() {
        return new MonedaIterator();
    }

    private class MonedaIterator implements Iterator<Moneda3> {
        private int currentIndex = 0;
        private int expectedModCount = modCount;
        private Moneda3 lastReturned = null;

        @Override
        public boolean hasNext() {
            checkForConcurrentModification();
            while (currentIndex < monedas.size()) {
                Moneda3 moneda = monedas.get(currentIndex);
                if (paisParaIterar == null || moneda.getPais().equals(paisParaIterar)) {
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        @Override
        public Moneda3 next() {
            checkForConcurrentModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = monedas.get(currentIndex++);
            return lastReturned;
        }

        @Override
        public void remove() {
            checkForConcurrentModification();
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            monedas.remove(--currentIndex);
            lastReturned = null;
            expectedModCount++;
            modCount++;
        }

        private void checkForConcurrentModification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
