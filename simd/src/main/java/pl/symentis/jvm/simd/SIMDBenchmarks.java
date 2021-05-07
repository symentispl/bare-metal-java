package pl.symentis.jvm.simd;

import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorSpecies;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.random.RandomGenerator;


@Fork(
        jvmArgsAppend = {
                "--add-modules", "jdk.incubator.vector"
        }
)
public class SIMDBenchmarks {

    @State(Scope.Benchmark)
    public static class IncubatingVector {

        @Param({"1000", "100000", "100000000"})
        public int streamSize = 100_000_000;
        private double[] xs;
        private double[] ys;
        private VectorSpecies<Double> speciesPreferred;
        private double[] zs;

        @Setup(Level.Iteration)
        public void setUp() {
            xs = RandomGenerator.getDefault().doubles(streamSize).toArray();
            ys = RandomGenerator.getDefault().doubles(streamSize).toArray();
            zs = new double[streamSize];
            speciesPreferred = DoubleVector.SPECIES_PREFERRED;
        }
    }

    @Benchmark
    public void incubatingVector(IncubatingVector iv, Blackhole bh) {
        int loopBound = iv.speciesPreferred.loopBound(iv.streamSize);
        int i = 0;
        for (; i < loopBound; i += iv.speciesPreferred.length()) {
            DoubleVector xv = DoubleVector.fromArray(iv.speciesPreferred, iv.xs, i);
            DoubleVector yv = DoubleVector.fromArray(iv.speciesPreferred, iv.ys, i);
            xv.mul(yv).add(1).neg().intoArray(iv.zs, i);
        }
        bh.consume(iv.zs);
    }

    @Benchmark
    public void autoVector(IncubatingVector iv, Blackhole bh) {
        for (int i = 0; i < iv.streamSize; i++) {
            iv.zs[i] = ((iv.xs[i] * iv.ys[i]) + 1) * -1;
        }
        bh.consume(iv.zs);
    }

    @Fork(
            jvmArgsAppend = {
                    "-XX:-UseSuperWord",
                    "--add-modules", "jdk.incubator.vector"
            }
    )
    @Benchmark
    public void noVector(IncubatingVector iv, Blackhole bh) {
        for (int i = 0; i < iv.streamSize; i++) {
            iv.zs[i] = ((iv.xs[i] * iv.ys[i]) + 1) * -1;
        }
        bh.consume(iv.zs);
    }
}
