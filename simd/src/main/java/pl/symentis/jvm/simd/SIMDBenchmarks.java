package pl.symentis.jvm.simd;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

public class SIMDBenchmarks
{

    @State( Scope.Benchmark )
    public static class IncubatingVector
    {

        private final int streamSize = 100_000_000;
        private int[] xs;
        private int[] ys;
        private VectorSpecies<Integer> speciesPreferred;
        private int[] zs;

        @Setup( Level.Iteration )
        public void setUp()
        {
            xs = RandomGenerator.getDefault().ints( streamSize ).toArray();
            ys = RandomGenerator.getDefault().ints( streamSize ).toArray();
            zs = new int[streamSize];
            speciesPreferred = IntVector.SPECIES_PREFERRED;
        }
    }

    @Benchmark
    public void incubatingVector( IncubatingVector iv )
    {
        int loopBound = iv.speciesPreferred.loopBound( iv.streamSize );
        int i = 0;
        for ( ; i < loopBound; i += iv.speciesPreferred.length() )
        {
            IntVector xv = IntVector.fromArray( iv.speciesPreferred, iv.xs, i );
            IntVector yv = IntVector.fromArray( iv.speciesPreferred, iv.ys, i );
            xv.mul( yv ).add( 1 ).neg().intoArray( iv.zs, i );
        }
    }

    @Benchmark
    public void noSimd( IncubatingVector iv )
    {
        for ( int i = 0; i < iv.streamSize; i++ )
        {
            iv.zs[i] = ((iv.xs[i] * iv.ys[i]) + 1) * -1;
        }
    }
}
