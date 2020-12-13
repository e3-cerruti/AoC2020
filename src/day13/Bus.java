package day13;

class Bus {
    private final int number;
    private final int order;
    private final boolean valid;

    public Bus(String name, int order) {
        this.order = order;
        this.valid = !name.equals("x");
        number = valid ? Integer.parseInt(name) : -1;
    }

    public long firstDepartureAfter(long t) {
        assert valid;
        return (number - t % number) % number;
    }

    public boolean departsInOrderAt(long t) {
        assert valid;
        return (t + order) % number == 0;
    }

    public int getNumber() {
        return number;
    }

    public int getOrder() {
        return order;
    }

    public boolean isValid() {
        return valid;
    }

    public static int latestInitialDeparture(Bus bus, Bus bus1) {
        return ((bus.number - bus.order) - (bus1.number - bus1.order));
    }

    public long getInitialDeparture() {
        return number - order;
    }
}
