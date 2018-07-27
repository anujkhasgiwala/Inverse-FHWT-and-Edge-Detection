package Problem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *************************************************************** 
 * @author vladimir kulyukin
 ***************************************************************
 */

public class Utils {
    
    public static void displaySample(double[] sample) {
        System.out.print("Sample: ");
        for (int i = 0; i < sample.length; i++) {
            System.out.printf("%5.2f ", sample[i]);
        }
        System.out.println();
    }
    
    public static void displaySample(int[] sample) {
        System.out.print("Sample: ");
        for (int i = 0; i < sample.length; i++) {
            System.out.print(sample[i] + " ");
        }
        System.out.println();
    }
    
    public static void displaySampleLineByLine(double[] sample) {
        System.out.print("Sample: ");
        for (int i = 0; i < sample.length; i++) {
            System.out.println(sample[i] + " ");
        }
    }
    
    public static void displaySampleLineByLine(int[] sample) {
        System.out.print("Sample: ");
        for (int i = 0; i < sample.length; i++) {
            System.out.println(sample[i] + " ");
        }
    }
    
    public static void displaySignalRange(double[] sample, int start, int end) {
        for(int i = start; i <= end; i++) {
            System.out.println(sample[i]);
        }
        System.out.println();
    }

    public static boolean isPowerOf2(int n) {
        if (n < 1) {
            return false;
        } else {
            double p_of_2 = (Math.log(n) / Math.log(2));
            return Math.abs(p_of_2 - (int) p_of_2) == 0;
        }
    }
    
    // if n = 2^x; this method returns x
    public static int wholeLog2(int n) {
        return (int)(Math.log(n)/Math.log(2));
    }
    
    // if n = 2^x; this method returns x
    public static double log2(int n) {
        return (Math.log(n)/Math.log(2));
    }
    
    public static int largestPowerOf2NoGreaterThan(int i) {
        if ( isPowerOf2(i) )
            return i;
        else {
            int curr = i-1;
            while ( curr > 0 ) {
                if ( isPowerOf2(curr) ) {
                    return curr;
                }
                else {
                    --curr;
                }
            }
            return 0;
        }
    }
    
    public static double[] largestSubsignalOfPowerOf2(double[] signal) {
        if ( isPowerOf2(signal.length) ) {
            return signal;
        }
        else {
            int i = largestPowerOf2NoGreaterThan(signal.length);
            if ( i == 0 ) return null;
            double[] subsignal = new double[i];
            System.arraycopy(signal, 0, subsignal, 0, i);
            return subsignal;
        }
    }
    
    public static boolean areSignalsEqual(double[] output1, double[] output2) {
        final int n = output1.length;
        if ( output2.length != n ) return false;
        for(int i = 0; i < n; i++) {
            if ( output1[i] != output2[i] ) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean areSignalsEqual(double[] output1, double[] output2, double thresh) {
        final int n = output1.length;
        if ( output2.length != n ) return false;
        for(int i = 0; i < n; i++) {
            if ( Math.abs(output1[i] - output2[i]) > thresh ) {
                return false;
            }
        }
        return true;
    }
    
    public static double[] copySignal(double[] sig) {
        double[] sigCopy = new double[sig.length];
        System.arraycopy(sig, 0, sigCopy, 0, sig.length);
        return sigCopy;
    }
    
    public static void display2DArray(double[][] ary, int num_rows, int num_cols) {
        for(int r = 0; r < num_rows; r++) {
            for(int c = 0; c < num_cols; c++) {
                System.out.print(ary[r][c] + " ");
            }
            System.out.println();
        }
    }
    
    public static double[] readInPrimDoublesFromLineFile(String inpath) {
        ArrayList<Double> nonPrimDoubles = new ArrayList<>();
        double[] primDoubles = null;
        
        try {
            BufferedReader bufRdr = new BufferedReader(new FileReader(new File(inpath) ) );
            String line = null;
            while ( (line = bufRdr.readLine() ) != null ) {
                nonPrimDoubles.add(Double.valueOf(line));
            }
            
            primDoubles = new double[nonPrimDoubles.size()];
            int i = 0;
            for(Double d: nonPrimDoubles) {
                primDoubles[i++] = d;
            }
            nonPrimDoubles.clear();
            nonPrimDoubles = null;
            bufRdr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return primDoubles;
    }
    
    public static double computeMean(double[] signal) {
        double mean = 0;
        for(double x: signal) { mean += x; }
        return mean/signal.length;
    }
    
    public static double computeMean(double[][] signal) {
        double mean = 0;
        int num_rows = signal.length;
        int num_cols = signal[0].length;
        for(int r = 0; r < num_rows; r++) {
            for(int c = 0; c < num_cols; c++) {
                mean += signal[r][c];
            }
        }
        return mean/(num_rows*num_cols);
    }
    
    public static double computeMean(double[][] signal, 
            int from_row, int upto_row, int from_col, int upto_col) {
        //System.out.println("from_row = " + from_row + " upto_row = " + upto_row);
        //System.out.println("from_col = " + from_col + " upto_col = " + upto_col);
        double mean = 0;
        for(int r = from_row; r <= upto_row; r++) {
            for(int c = from_col; c <= upto_col; c++)
            mean += signal[r][c];
        }
        return mean/((upto_row - from_row + 1)*(upto_col - from_col + 1));
    }
    
    public static double computeVariance(double[] signal) {
        double mean = computeMean(signal);
        double var = 0;
        for(double x: signal) {
            var += Math.pow(x - mean, 2.0);
        }
        return var/signal.length;
    }
    
    public static double computeCorrectedVariance(double[] signal) {
        double mean = computeMean(signal);
        double var = 0;
        for(double x: signal) {
            var += Math.pow(x - mean, 2.0);
        }
        return var/(signal.length-1);
    }
    
    public static double computeSTD(double[] signal) {
        return Math.sqrt(computeVariance(signal));
    }
    
    public static double computeCorrectedSTD(double[] signal) {
        return Math.sqrt(computeCorrectedVariance(signal));
    }
    
    public static double computeMeanInRange(double[] signal, int start, int end) {
        double mean = 0;
        for(int i = start; i <= end; i++) { mean += signal[i]; }
        return mean/(end - start + 1);
    }
    
    public static double computeVarianceInRange(double[] signal, int start, int end) {
        double mean = computeMeanInRange(signal, start, end);
        double var = 0;
        for(int i = start; i <= end; i++) {
            var += Math.pow(signal[i] - mean, 2.0);
        }
        return var/(end - start + 1);
    }
    
    public static double computeCorrectedVarianceInRange(double[] signal, int start, int end) {
        double mean = computeMeanInRange(signal, start, end);
        double var = 0;
        for(int i = start; i <= end; i++) {
            var += Math.pow(signal[i] - mean, 2.0);
        }
        return var/(end - start);
    }
    
    public static double computeSTDInRange(double[] signal, int start, int end) {
        return Math.sqrt(computeVarianceInRange(signal, start, end));
    }
    
    public static double computeCorrectedSTDInRange(double[] signal, int start, int end) {
        return Math.sqrt(computeCorrectedVarianceInRange(signal, start, end));
    }
    
    public static String getFileNameExtension(String file_path) {
        int i = file_path.lastIndexOf(".");
        if ( i > 0 ) {
            return file_path.substring(i+1);
        }
        else {
            return null;
        }
    }
    
    public static int getFileNameExtensionPos(String file_path) {
        int i = file_path.lastIndexOf(".");
        if ( i > 0 ) {
            return i;
        }
        else {
            return -1;
        }
    }

    public static String getFileName(String file_path) {
        final int i = file_path.lastIndexOf("/");
        final int j = getFileNameExtensionPos(file_path);
        if ( i > 0 ) {
            if ( j > 0 ) {
                return file_path.substring(i+1, j);
            }
            else {
                return null;
            }
        }
        else if ( j > 0 ) {
                return file_path.substring(0, j);
            }
            else {
                return null;
            }
        }
    
    public static List<String> getListOfAbsoluteFilePathsInDirectory(String dir) {
        List<String> list = new ArrayList<>();
        
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        for(File f: listOfFiles) {
            if ( f.isFile() ) {
                list.add(f.getAbsolutePath());
            }
        }
        
        return list;
    }
    
    //https://en.wikipedia.org/wiki/Gaussian_elimination
    public double[][] invMat(double[][] mat) {
        int nrows = mat.length;
        int ncols = mat[0].length;
        if ( nrows != ncols ) {
            return null;
        }
        
        //double[][] inv_mat = new double[nrows][ncols];
        
        
        return null;
    }

    public static void main(String[] args) {
            String fp1 = "test.png";
            System.out.println(Utils.getFileName(fp1));
            String fp2 = "/home/ubuntu/test.png";
            System.out.println(Utils.getFileNameExtension(fp1));
            System.out.println(getFileName(fp2));
            System.out.println(getFileNameExtension(fp2));
    }
    
    
}

