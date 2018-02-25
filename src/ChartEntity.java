public class ChartEntity {
    private String title;
    private double value;

    public ChartEntity(String title, double value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (3 * title.hashCode() + 7 * value);
    }
}
