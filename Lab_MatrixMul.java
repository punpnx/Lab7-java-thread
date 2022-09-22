/* 
1.6 หาก uncomment บรรทัดที่ 19 o.getValue()จากบรรทัดที่ 20 ตอบค่าอะไรได้บ้าง
From worker1 thread 15 x value is 3
From worker2 thread 16 x value is 7
from main x value is 7
*/
import java.util.ArrayList;
import java.util.Arrays;

public class Lab_MatrixMul {
    public static void main(String[] args) {
        int[][] inputA = { { 5, 6, 7 }, { 4, 8, 9 } };
        int[][] inputB = { { 6, 4 }, { 5, 7 }, { 1, 1 } };
        MyData matA = new MyData(inputA);
        MyData matB = new MyData(inputB);

        int matC_r = matA.data.length;
        int matC_c = matB.data[0].length;

        MyData matC = new MyData(matC_r, matC_c);

        /* Q4 construct 2D array for each "thread" 
        with respected to each cell in matC,
        then start each thread */
        ArrayList<Thread> t = new ArrayList<Thread>();
        for(int i = 0; i < matC_r; i++) {
            Thread mat = new Thread(new MatrixMulThread(i, i, matA, matB, matC));
            mat.start();
            t.add(mat);
        }

        // Q5 join each thread
        for(Thread thread : t) {
            try {
                thread.join();
            } catch (Exception e) { System.out.println(e); }
        }
        
        matC.show();
    }
}

class MatrixMulThread implements Runnable {
    int processing_row; 
    int processing_col;
    MyData datA; 
    MyData datB; 
    MyData datC;

    MatrixMulThread(int tRow, int tCol, MyData a, MyData b, MyData c) {

        // Q1 code here
        processing_row = tRow;
        processing_col = tCol;
        datA = a;
        datB = b;
        datC = c;
    }
    /* Q2 */ public void run() {
        // Q3
        for(int i = 0; i < datA.data.length; i++) {
            for(int j = 0; j < datB.data.length; j++) {
                datC.data[processing_row][i] += datA.data[processing_row][j] * datB.data[j][i];
            }
        }
        /* System.out.println("perform sum of
        multiplication of assigned row and col"); */
    }
} //class

class MyData {
    int[][] data;
    MyData(int[][] m) { data = m; }
    MyData(int r, int c) {
        data = new int[r][c];
        for (int[] aRow : data)
            Arrays.fill(aRow, 0);
            // 0 will be overwritten anyway
    }

    void show() {
        System.out.println(Arrays.deepToString(data));
    }
} //class