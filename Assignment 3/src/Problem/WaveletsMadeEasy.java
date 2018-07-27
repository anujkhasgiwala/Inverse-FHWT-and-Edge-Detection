package Problem;

public class WaveletsMadeEasy {

	public static void main(String[] args) {
		//Test cases
		orderedFastInverseHWTTest01();
		inPlaceFastInverseHWTTest01();
	}

	public static void displayArray(double[] ary) {
		for (int i = 0; i < ary.length; i++) {
			System.out.print(ary[i] + " ");
		}
		System.out.println();
	}

	public static void orderedFastInverseHWTTest01() {
		final double[] signal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
		System.out.print("Original signal: ");
		displayArray(signal);
		final int num_iters = 4;
		OneDHWT.orderedFastInverseHaarWaveletTransformForNumIters(signal, num_iters);
		System.out.print("Signal after " + num_iters + " ordered inverse iters: ");
		displayArray(signal);
		System.out.println();
	}
	
	public static void inPlaceFastInverseHWTTest01() {
		final double[] signal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
		System.out.print("Original signal: ");
		displayArray(signal);
		final int num_iters = 4;
		OneDHWT.inPlaceFastInverseHaarWaveletTransformForNumIters(signal, 4);
		System.out.print("Signal after " + num_iters + " inPlace inverse iters: ");
		displayArray(signal);
		System.out.println();
	} 
}