import java.awt.*;
import java.util.*;

public class EntityContainer<T extends ChartEntity> {
    private Map<T, Color> entities;
    private Random random;

    public EntityContainer() {
        entities = new HashMap<>();
        random = new Random();
    }

    public void add(T ce) {
        entities.put(ce, getRandomColor());
    }

    public double getChartValuesSum() {
        return entities.keySet().stream().mapToDouble(ChartEntity::getValue).sum();
    }

    public T getByTitle(String title) {
        return entities.keySet().stream().filter(x -> x.getTitle().equals(title)).findFirst().get();
    }

    public Color getColorFor(T e) {
        return entities.entrySet().stream().filter(x -> x.getKey().equals(e)).findFirst().get().getValue();
    }

    private Color getRandomColor() {
        return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }
}
