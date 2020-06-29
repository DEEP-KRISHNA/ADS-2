
// import java.awt.Color;
// import java.io.File;
import java.lang.Math;
import edu.princeton.cs.algs4.Picture;
import java.lang.IllegalArgumentException;

// import java.util.Arrays;
// import java.util.Scanner;

public class SeamCarver {

    private Picture pic;
    private int wid;
    private int heig;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        pic = picture;
        wid = pic.width();
        heig = pic.height();
    }

    // current picture
    public Picture picture() {
        return pic;
    }

    // width of current picture
    public int width() {
        return wid;
    }

    // height of current picture
    public int height() {
        return heig;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        // Color temp = pic.get(x, y);
        // System.out.println(temp.getRed());
        // System.out.println(temp.getGreen());
        // System.out.println(temp.getBlue());
        double delXsqrVal = 0;
        double delYsqrVal = 0;
        double temp = 0;
        if (x < 0 || x > wid - 1 || y < 0 || y > heig - 1) {
            throw new IllegalArgumentException();
        } else if (x == 0 || x == wid - 1 || y == 0 || y == heig - 1) {
            return 1000;
        } else {

            delXsqrVal = delXsqr(x, y);
            delYsqrVal = delYsqr(x, y);
            temp = delXsqrVal + delYsqrVal;
            return Math.pow(temp, 0.5);
        }

    }

    private double delXsqr(int x, int y) {
        int geRGB = pic.getRGB(x - 1, y);
        int re1 = (geRGB >> 16 & 0xFF);
        int gr1 = (geRGB >> 8 & 0xFF);
        int bl1 = (geRGB & 0xFF);

        geRGB = pic.getRGB(x + 1, y);
        int re2 = (geRGB >> 16 & 0xFF);
        int gr2 = (geRGB >> 8 & 0xFF);
        int bl2 = (geRGB & 0xFF);

        // return (((re1 - re2) * (re1 - re2)) + ((gr1 - gr2) * (gr1 - gr2)) + ((bl1 -
        // bl2) * (bl1 - bl2)));
        return (Math.pow((re1 - re2), 2) + Math.pow((gr1 - gr2), 2) + Math.pow((bl1 - bl2), 2));
    }

    private double delYsqr(int x, int y) {
        int geRGB = pic.getRGB(x, y - 1);
        int re1 = (geRGB >> 16 & 0xFF);
        int gr1 = (geRGB >> 8 & 0xFF);
        int bl1 = (geRGB & 0xFF);

        geRGB = pic.getRGB(x, y + 1);
        int re2 = (geRGB >> 16 & 0xFF);
        int gr2 = (geRGB >> 8 & 0xFF);
        int bl2 = (geRGB & 0xFF);

        // return (((re1 - re2) * (re1 - re2)) + ((gr1 - gr2) * (gr1 - gr2)) + ((bl1 -
        // bl2) * (bl1 - bl2)));

        return (Math.pow((re1 - re2), 2) + Math.pow((gr1 - gr2), 2) + Math.pow((bl1 - bl2), 2));
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] minVer = new int[wid];
        double[][] energymatrix = new double[wid][heig];
        int min = Math.min(wid, heig);
        if (min == 1) {
            int[] t;
            if (wid == 1 && heig == 1) {
                t = new int[1];
                t[0] = 0;
                return t;
            } else if (heig == 1) {
                t = new int[wid];
                for (int i = 0; i < wid; i++) {
                    t[i] = 0;
                }
                return t;
            } else {
                int minn = 0;
                double ener = Double.MAX_VALUE;
                for (int i = 0; i < wid; i++) {
                    if (energy(i, 0) < ener) {
                        minn = i;
                    }
                }
                t = new int[1];
                t[0] = minn;
                return t;
            }
        }
        for (int i = 0; i < wid; i++) {
            for (int j = 0; j < heig; j++) {
                if (i == 0) {
                    energymatrix[i][j] = 1000;
                } else {
                    if (j == 0) {
                        double smallestener = Math.min(energymatrix[i - 1][j], energymatrix[i - 1][j + 1]);
                        energymatrix[i][j] = energy(i, j) + smallestener;
                    } else if (j == heig - 1) {
                        double smallestener = Math.min(energymatrix[i - 1][j - 1], energymatrix[i - 1][j]);
                        energymatrix[i][j] = energy(i, j) + smallestener;
                    } else {
                        double smallestener = Math.min(energymatrix[i - 1][j],
                                Math.min(energymatrix[i - 1][j - 1], energymatrix[i - 1][j + 1]));
                        energymatrix[i][j] = energy(i, j) + smallestener;
                    }
                }
            }
        }

        // System.out.println();
        // System.out.println("cumilative energys");
        // System.out.println();
        // for (int i = 0; i < heig; i++) {
        // for (int j = 0; j < wid; j++) {
        // System.out.printf("%.2f", energymatrix[j][i]);
        // System.err.print(" ");
        // }
        // System.out.println();
        // }
        // System.out.println();

        int jref = 0;
        for (int i = wid - 1; i >= 0; i--) {
            double min_energy = Double.MAX_VALUE;
            if (i == wid - 1) {
                for (int j = heig - 1; j >= 0; j--) {
                    if (energymatrix[i][j] < min_energy) {
                        min_energy = energymatrix[i][j];
                        jref = j;
                    }
                }
            } else {
                if (jref == 0) {
                    if (energymatrix[i][jref] > energymatrix[i][jref + 1]) {
                        jref = jref + 1;
                    }
                } else if (jref == heig - 1) {
                    if (energymatrix[i][jref] > energymatrix[i][jref - 1]) {
                        jref = jref - 1;
                    }
                } else {
                    if ((energymatrix[i][jref] > energymatrix[i][jref - 1])
                            && (energymatrix[i][jref + 1] > energymatrix[i][jref - 1])) {
                        jref = jref - 1;
                    } else if ((energymatrix[i][jref] > energymatrix[i][jref + 1])
                            && (energymatrix[i][jref - 1] > energymatrix[i][jref + 1])) {
                        jref = jref + 1;
                    }
                }
            }
            // if (i == 0) {
            // if ((jref != 0) && (jref != heig - 1) && (energymatrix[i][jref] ==
            // energymatrix[i][jref - 1])) {
            // jref = jref - 1;
            // }
            // }

            minVer[i] = jref;

        }

        return minVer;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] minVer = new int[heig];
        double[][] energymatrix = new double[wid][heig];
        int min = Math.min(wid, heig);
        if (min == 1) {
            int[] t;
            if (wid == 1 && heig == 1) {
                t = new int[1];
                t[0] = 0;
                return t;
            } else if (wid == 1) {
                t = new int[heig];
                for (int i = 0; i < heig; i++) {
                    t[i] = 0;
                }
                return t;
            } else {
                int minn = 0;
                double ener = Double.MAX_VALUE;
                for (int i = 0; i < heig; i++) {
                    if (energy(0, i) < ener) {
                        minn = i;
                    }
                }
                t = new int[1];
                t[0] = minn;
                return t;
            }
        }
        for (int i = 0; i < heig; i++) {
            for (int j = 0; j < wid; j++) {
                if (i == 0) {
                    energymatrix[j][i] = 1000;
                } else {
                    if (j == 0) {
                        if (energymatrix[j][i - 1] < energymatrix[j + 1][i - 1]) {
                            energymatrix[j][i] = energy(j, i) + energymatrix[j][i - 1];
                        } else {
                            energymatrix[j][i] = energy(j, i) + energymatrix[j + 1][i - 1];
                        }
                    } else if (j == wid - 1) {
                        if (energymatrix[j][i - 1] < energymatrix[j - 1][i - 1]) {
                            energymatrix[j][i] = energy(j, i) + energymatrix[j][i - 1];
                        } else {
                            energymatrix[j][i] = energy(j, i) + energymatrix[j - 1][i - 1];
                        }
                    } else {
                        if ((energymatrix[j - 1][i - 1] < energymatrix[j][i - 1])
                                && (energymatrix[j - 1][i - 1] < energymatrix[j + 1][i - 1])) {
                            energymatrix[j][i] = energy(j, i) + energymatrix[j - 1][i - 1];
                        } else if ((energymatrix[j + 1][i - 1] < energymatrix[j][i - 1])
                                && (energymatrix[j + 1][i - 1] < energymatrix[j - 1][i - 1])) {
                            energymatrix[j][i] = energy(j, i) + energymatrix[j + 1][i - 1];
                        } else {
                            energymatrix[j][i] = energy(j, i) + energymatrix[j][i - 1];
                        }
                    }
                }
            }
        }

        // System.out.println();
        // System.out.println("cumilative energys");
        // System.out.println();
        // for (int i = 0; i < heig; i++) {
        // for (int j = 0; j < wid; j++) {
        // System.out.printf("%.2f", energymatrix[j][i]);
        // System.err.print(" ");
        // }
        // System.out.println();
        // }
        // System.out.println();

        int jref = 0;
        for (int i = heig - 1; i >= 0; i--) {
            double min_energy = Double.MAX_VALUE;
            if (i == heig - 1) {
                for (int j = 0; j < wid; j++) {
                    if (energymatrix[j][i] < min_energy) {
                        min_energy = energymatrix[j][i];
                        jref = j;
                    }
                }
            } else {
                if (jref == 0) {
                    if (energymatrix[jref][i] > energymatrix[jref + 1][i]) {
                        jref = jref + 1;
                    }
                } else if (jref == wid - 1) {
                    if (energymatrix[jref][i] > energymatrix[jref - 1][i]) {
                        jref = jref - 1;
                    }
                } else {
                    if ((energymatrix[jref][i] > energymatrix[jref - 1][i])
                            && (energymatrix[jref + 1][i] > energymatrix[jref - 1][i])) {
                        jref = jref - 1;
                    } else if ((energymatrix[jref][i] > energymatrix[jref + 1][i])
                            && (energymatrix[jref - 1][i] > energymatrix[jref + 1][i])) {
                        jref = jref + 1;
                    }
                }
            }
            if (i == 0) {
                if ((jref != 0) && (jref != wid - 1) && (energymatrix[jref][i] == energymatrix[jref - 1][i])) {
                    jref = jref - 1;
                }
            }

            minVer[i] = jref;
            // System.out.println(jref);

        }

        return minVer;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (heig <= 1) {
            throw new IllegalArgumentException();
        }
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (seam.length != wid) {
            throw new IllegalArgumentException();
        }
        Picture new_pic = new Picture(wid, heig - 1);
        int valtemp = seam[0];
        for (int i = 0; i < wid; i++) {
            if (i != 0) {
                if (seam[i] < 0 || seam[i] >= heig) {
                    throw new IllegalArgumentException();
                }
                if (Math.abs(valtemp - seam[i]) <= 1) {
                    valtemp = seam[i];
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                if (seam[i] < 0 || seam[i] >= heig) {
                    throw new IllegalArgumentException();
                }
            }
            for (int j = 0; j < heig; j++) {
                if (j < seam[i]) {
                    new_pic.setRGB(i, j, pic.getRGB(i, j));
                } else if (j > seam[i]) {
                    new_pic.setRGB(i, j - 1, pic.getRGB(i, j));
                }
            }
        }
        pic = new_pic;
        heig = heig - 1;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (wid <= 1) {
            throw new IllegalArgumentException();
        }
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (seam.length != heig) {
            throw new IllegalArgumentException();
        }
        Picture new_pic = new Picture(wid - 1, heig);
        int valtemp = seam[0];
        for (int i = 0; i < heig; i++) {
            if (i != 0) {
                if (seam[i] < 0 || seam[i] >= wid) {
                    throw new IllegalArgumentException();
                }
                if (Math.abs(valtemp - seam[i]) <= 1) {
                    valtemp = seam[i];
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                if (seam[i] < 0 || seam[i] >= wid) {
                    throw new IllegalArgumentException();
                }
            }
            for (int j = 0; j < wid; j++) {
                if (j < seam[i]) {
                    new_pic.setRGB(j, i, pic.getRGB(j, i));
                } else if (j > seam[i]) {
                    new_pic.setRGB(j - 1, i, pic.getRGB(j, i));
                }
            }
        }
        pic = new_pic;
        wid = wid - 1;

    }

    // unit testing (optional)
    public static void main(String[] args) {
        Picture p = new Picture("C:\\Users\\PINAKA\\Desktop\\seam\\5x6.png");
        SeamCarver w = new SeamCarver(p);
        int[] temp = w.findVerticalSeam();
        w.removeVerticalSeam(temp);

        // String path = "C:\\Users\\PINAKA\\Desktop\\seam";
        // File folder = new File(path);
        // File[] ar = folder.listFiles();
        // path = path + "\\";
        // int count = 0;
        // int inCount = 0;
        // for (int i = 0; i < ar.length; i++) {
        // if (ar[i].isFile() && ar[i].getName().endsWith("png")) {
        // inCount++;
        // Picture p = new Picture(path + ar[i].getName());
        // SeamCarver w = new SeamCarver(p);
        // int[] se = w.findVerticalSeam();
        // File f = null;
        // Scanner scObj = null;
        // try {
        // f = new File(path + ar[i].getName().replace(".png", "") + ".printseams.txt");
        // scObj = new Scanner(f);
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // count++;
        // continue;
        // }

        // for (int j = 0; j < 5; j++) {
        // scObj.nextLine();
        // }
        // // .substring(4);
        // String res = Arrays.toString(se);
        // res = res.replace(",", "").replace("[", "{ ").replace("]", " }");
        // String act = scObj.nextLine().replace("Vertical seam: ", "");
        // if (res.equals(act)) {
        // count++;
        // } else {
        // System.out.println();
        // System.out.println("Input : " + ar[i].getName());
        // System.out.println("Actual : " + act);
        // System.out.println("Obtained : " + res);
        // }
        // scObj.close();

        // }
        // }

        // if (count != inCount) {
        // System.out.println("Test cases passed : " + count + " out of " + inCount);
        // } else {
        // System.out.println("All Test Cases Passed");
        // }

        // System.out.println();
        // System.out.println("Normal Energies");
        // System.out.println();
        // for (int i = 0; i < 5; i++) {
        // for (int j = 0; j < 6; j++) {
        // System.out.printf("%.2f", w.energy(j, i));
        // System.err.print(" ");
        // }
        // System.out.println();
        // }
    }
}

// int oldHor[] = new int[wid];
// int newHor[] = new int[wid];
// int iref;
// double minenrgy = Double.MAX_VALUE;
// double enrgy;
// for (int i = 0; i < heig; i++) {
// iref = i;
// enrgy = 0;
// for (int j = 0; j < wid - 1; j++) {
// oldHor[j] = iref;
// enrgy = enrgy + energy(j, iref);
// if (iref == 0) {
// if ((energy(iref, j + 1)) > (energy(iref + 1, j + 1))) {
// iref = iref + 1;
// }
// } else if (iref == heig - 1) {
// if ((energy(iref, j + 1)) > (energy(iref - 1, j + 1))) {
// iref = iref - 1;
// }
// } else {
// if (((energy(iref, j + 1)) > (energy(iref - 1, j + 1)))
// && ((energy(iref + 1, j + 1)) > (energy(iref - 1, j + 1)))) {
// iref = iref - 1;
// } else if (((energy(iref, j + 1)) > (energy(iref + 1, j + 1)))
// && ((energy(iref - 1, j + 1)) > (energy(iref + 1, j + 1)))) {
// iref = iref + 1;
// }
// }
// }
// oldHor[wid - 1] = iref;
// enrgy = enrgy + energy(wid - 1, iref);
// if (enrgy < minenrgy) {
// minenrgy = enrgy;
// newHor = oldHor.clone();
// }
// }
// for (int k = 0; k < wid; k++) {
// System.out.println(newHor[k]);
// }
// return newHor;