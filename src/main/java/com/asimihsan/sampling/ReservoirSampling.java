package com.asimihsan.sampling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ReservoirSampling maintains a fair uniform sample over a large stream of undefined size.
 *
 * @param <T> Any item type, no bounds.
 */
public class ReservoirSampling<T> {
    private final ArrayList<T> data;
    private final int reservoirSize;
    private long itemsSeen = 0;
    private final Random random;

    /**
     * Create a reservoir sampler.
     *
     * @param reservoirSize The size of the fair sample you want to return.
     * @param random Some instance of {@link Random}.
     */
    public ReservoirSampling(int reservoirSize, Random random) {
        this.data = new ArrayList<>();
        this.reservoirSize = reservoirSize;
        this.random = random;
    }

    /**
     * Call this method when you see an item in your stream. This updates the sampler
     * to maintain an internal fair sample of the stream.
     *
     * k = reservoir size
     * n = items seen
     *
     * prob(keep_item) < k / n
     *                 < reservoir size / items seen
     */
    public void update(T item) {
        // Initial phase, fill the reservoir
        if (itemsSeen < reservoirSize) {
            data.add(item);
            itemsSeen++;
            return;
        }

        // Steady state where we sample randomly
        itemsSeen++;
        if (random.nextDouble() * itemsSeen < reservoirSize) {
            int newSlot = random.nextInt(reservoirSize);
            data.set(newSlot, item);
        }
    }

    public List<T> getSamples() {
        return List.copyOf(data);
    }

}
