package elins.org.aktvtas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SensorDataSequence {
    protected List<Sensor> buffer = new ArrayList<>();
    private List<List<Sensor>> sequence = new ArrayList<>();
    protected HashMap<Sensor, Integer> sensorOrder = new HashMap<>();
    private int registeredSensor = 0;

    public SensorDataSequence registerSensor(Sensor sensor) {
        sensorOrder.put(sensor, registeredSensor++);
        buffer.add(sensor);

        return this;
    }

    public SensorDataSequence setData(Sensor sensor) {
        int index = sensorOrder.get(sensor);
        buffer.set(index, sensor);

        return this;
    }

    public void commit() {
        sequence.add(buffer);

        resetBuffer();
    }

    private void resetBuffer() {
        for (int i = 0; i < buffer.size(); i++) {
            Sensor sensor = buffer.get(i);
            sensor.resetValues();
            buffer.set(i, sensor);
        }
    }

    public Sensor getLastData(Sensor sensor) {
        int index = sensorOrder.get(sensor);

        List<Sensor> lastData = sequence.get(sequence.size() - 1);
        return lastData.get(index);
    }

    public List<Sensor> getDataByIndex(int index) {
        return sequence.get(index);
    }

    public List<List<Sensor>> getAll() {
        return sequence;
    }

    public void clear() {
        sequence.clear();
    }
}
