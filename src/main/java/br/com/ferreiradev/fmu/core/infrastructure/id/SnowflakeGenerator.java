package br.com.ferreiradev.fmu.core.infrastructure.id;

import java.time.Instant;

public class SnowflakeGenerator {

    private final long datacenterId;
    private final long workerId;
    private final long sequenceBits = 12L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;

    private final long maxWorkerId = ~(-1L << workerIdBits);
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);

    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final long sequenceMask = ~(-1L << sequenceBits);

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    private final long twepoch = 1757462400000L; // Epoch em 10/09/2025 - 00h00

    public SnowflakeGenerator(long datacenterId, long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id deve estar entre 0 e %d", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("Datacenter Id deve estar entre 0 e %d", maxDatacenterId));
        }
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = Instant.now().toEpochMilli();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Relógio voltou no tempo. Não é possível gerar ID.");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = Instant.now().toEpochMilli();
        while (timestamp <= lastTimestamp) {
            timestamp = Instant.now().toEpochMilli();
        }
        return timestamp;
    }
}
