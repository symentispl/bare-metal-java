package pl.symentis.jvm.simd;

import jdk.incubator.vector.IntVector;

public class Main
{
    public static void main( String[] args )
    {
        var intVector = IntVector.fromArray( IntVector.SPECIES_512, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 0 );
        var negVector = intVector.neg();
        System.out.println(negVector);
    }
}
