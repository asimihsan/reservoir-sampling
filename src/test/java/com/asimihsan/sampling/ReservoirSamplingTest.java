package com.asimihsan.sampling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class ReservoirSamplingTest {
    @Test
    public void testFairness() {
        int rounds = 3_000;
        int iteratorSize = 100_000;
        int reservoirSize = 10_000;
        Random random = new Random(42);
        int expectedCount = rounds * reservoirSize / iteratorSize;

        Map<Integer, Integer> counts = getCounts(rounds, iteratorSize, reservoirSize, random);
        for (Map.Entry<Integer, Integer> count : counts.entrySet()) {
            Assertions.assertTrue(count.getValue() < expectedCount * 1.3);
            Assertions.assertTrue(count.getValue() > expectedCount * 0.7);
        }
    }


    private Map<Integer, Integer> getCounts(int rounds, int iteratorSize, int reservoirSize, Random random) {
        Map<Integer, Integer> counts = new HashMap<>(iteratorSize);
        for (int round = 0; round < rounds; round++) {
            ReservoirSampling<Integer> sampler = new ReservoirSampling<>(reservoirSize, random);
            for (int i = 0; i < iteratorSize; i++) {
                sampler.update(i);
            }
            for (Integer sample : sampler.getSamples()) {
                counts.put(sample, counts.getOrDefault(sample, 0) + 1);
            }
        }
        return counts;
    }
}