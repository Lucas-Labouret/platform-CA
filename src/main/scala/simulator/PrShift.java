package simulator;

import compiler.Locus;

/**
 * an object implementing PrShift can do the prior computation needed for horizontal communication
 */
public interface PrShift {

    /**
     * preprocessin bit level communication within the CA memory, so that << and >>> need only a shift instead of a rotation
     *
     * @param h int32 CA memory
     */
    public void prepareBit(int[] h);

    public void prepareBit(int[][] h);

    /** does a miror on the border */
    public void mirror(int[][] h, Locus l);

    public void mirror(int[] h, Locus l);

    /** does a torus on the border */
    public void torusify(int[][] h, Locus l);

    public void torusify(int[] h, Locus l);

}



