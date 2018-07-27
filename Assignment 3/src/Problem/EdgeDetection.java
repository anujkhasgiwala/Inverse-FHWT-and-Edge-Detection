package Problem;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class EdgeDetection extends ImageManip{
	public static void applyColumnBasedFHWT(String infile, String outfile) {
        Mat orig = Highgui.imread(IMAGE_SOURCE_DIR + infile);
        if (orig.rows() == 0 || orig.cols() == 0) {
            throw new IllegalArgumentException("Failed to read " + IMAGE_SOURCE_DIR + infile);
        }
        final int num_rows = orig.rows();
        final int num_cols = orig.cols();
        Mat grayscale = new Mat(num_rows, num_cols, CvType.CV_8UC1);
        Imgproc.cvtColor(orig, grayscale, Imgproc.COLOR_RGB2GRAY);
        double[] fhwt_col_pix = new double[num_cols];

        for (int col = 0; col < num_cols; col++) {
            for (int row = 0; row < num_rows; row++) {
                fhwt_col_pix[row] = grayscale.get(row, col)[0];
            }
            OneDHWT.inPlaceFastHaarWaveletTransformForNumIters(fhwt_col_pix, 1);
            for (int row = 1; row < num_rows; row += 2) {
                if (Math.abs(fhwt_col_pix[row]) >= WAVELET_COEFF_THRESH) {
                    if (fhwt_col_pix[row] < 0) {
                        orig.put(row, col, YES_EDGE_PIX);
                        orig.put(row - 1, col, NO_EDGE_PIX);
                    } else if (fhwt_col_pix[row] > 0) {
                        orig.put(row, col, NO_EDGE_PIX);
                        orig.put(row - 1, col, YES_EDGE_PIX);
                    }
                } else {
                    orig.put(row, col, NO_EDGE_PIX);
                    orig.put(row - 1, col, NO_EDGE_PIX);
                }
            }
        }
        Highgui.imwrite(IMAGE_SOURCE_DIR + outfile, orig);
        orig.release();
    }
	
	public static void markFHWTEdgeHillTopsInCols(String infile, String outfile) {
    	Mat orig = Highgui.imread(IMAGE_SOURCE_DIR + infile);
        if (orig.rows() == 0 || orig.cols() == 0) {
            throw new IllegalArgumentException("Failed to read " + IMAGE_SOURCE_DIR + infile);
        }
        final int num_rows = orig.rows();
        final int num_cols = orig.cols();
        Mat grayscale = new Mat(num_rows, num_cols, CvType.CV_8UC1);
        Imgproc.cvtColor(orig, grayscale, Imgproc.COLOR_RGB2GRAY);
        double[] fhwt_col_pix = new double[num_cols];
        for (int col = 0; col < num_cols; col++) {
        	for (int row = 0; row < num_rows; row++) {
                fhwt_col_pix[row] = grayscale.get(row, col)[0];
            }
        	OneDHWT.inPlaceFastHaarWaveletTransformForNumIters(fhwt_col_pix, 1);
            for(int row = 1; row < num_rows; row += 2) {
                if (Math.abs(fhwt_col_pix[row]) >= WAVELET_COEFF_THRESH) {
                    if (fhwt_col_pix[row] < 0) {
                        orig.put(row, col, YES_EDGE_PIX);
                        orig.put(row - 1, col, NO_EDGE_PIX);
                    } else if (fhwt_col_pix[row] > 0) {
                        orig.put(row, col, NO_EDGE_PIX);
                        orig.put(row - 1, col, YES_EDGE_PIX);
                    }
                } else {
                    orig.put(row, col, NO_EDGE_PIX);
                    orig.put(row - 1, col, NO_EDGE_PIX);
                }
            }
        }
        Highgui.imwrite(IMAGE_SOURCE_DIR + outfile, orig);
        orig.release();	
    }
	
	public static void markFHWTEdgeHillTopsInRowsandCols(String infile, String outfile){
        Mat orig = Highgui.imread(IMAGE_SOURCE_DIR + infile);
        if (orig.rows() == 0 || orig.cols() == 0) {
            throw new IllegalArgumentException("Failed to read " + IMAGE_SOURCE_DIR + infile);
        }
        int num_rows = orig.rows();
        int num_cols = orig.cols();
        int length = Math.max(num_cols, num_rows);
        length = nearestPowerOfTwo(length);
        Size dimensions = new Size(length,length);
        Imgproc.resize(orig, orig, dimensions);
        num_rows = orig.rows();
        num_cols = orig.cols();
        Mat grayscale = new Mat(num_rows, num_cols, CvType.CV_8UC1);
        Imgproc.cvtColor(orig, grayscale, Imgproc.COLOR_RGB2GRAY);
        for(int row = 0; row < num_rows; row++){
            for(int col = 0; col < num_cols; col ++){
                orig.put(row, col, NO_EDGE_PIX);
            }
        }
        double[] fhwt_row_pix = new double[num_rows];
        for (int row = 0; row < num_rows; row++) {
            for (int col = 0; col < num_cols; col++) {
                fhwt_row_pix[col] = grayscale.get(row, col)[0];
            }
            OneDHWT.inPlaceFastHaarWaveletTransformForNumIters(fhwt_row_pix, 1);
            for (int col = 1; col < num_cols; col += 2) {
                if (Math.abs(fhwt_row_pix[col]) >= WAVELET_COEFF_THRESH) {
                    if (fhwt_row_pix[col] < 0) {
                        orig.put(row, col,     YES_EDGE_PIX);
                    } else if (fhwt_row_pix[col] > 0) {
                        orig.put(row, col - 1, YES_EDGE_PIX);
                    }
                }
            }
        }

        double[] FHWT_col_pix = new double[num_cols];
        for(int col = 0; col < num_cols; col++){
            for(int row = 0; row < num_rows; row++){
            	FHWT_col_pix[row] = grayscale.get(row, col)[0];
            }
            OneDHWT.inPlaceFastHaarWaveletTransformForNumIters(FHWT_col_pix, 1);
            for(int row = 1; row < num_rows; row += 2){
                if(Math.abs(FHWT_col_pix[row]) >= WAVELET_COEFF_THRESH){
                    if(FHWT_col_pix[row] < 0) {
                        orig.put(row, col,     YES_EDGE_PIX);
                    } else if (FHWT_col_pix[row] > 0) {
                        orig.put(row - 1, col, YES_EDGE_PIX);
                    }
                }
            }
        }
           
        Highgui.imwrite(IMAGE_SOURCE_DIR + outfile, orig);
        orig.release();
    }
	
	public static int nearestPowerOfTwo(int size){
        //find next highest power of 2
        int powerOf2 = 1;
        int power = 1;
        while(powerOf2 < size){
        	powerOf2 = (int) Math.pow(2, power);
            power += 1;
        }
        return powerOf2;
    }
}