import java.util.*;

public class Test {
    public static void main(String[] args) {
        Test test=new Test();
//        System.out.println(test+" "+test.toString());
        int[] a=new int[5];
        int b=a.length;
        int[] sort={1,3,5,7,2,4,8,6,0,101,10,9};
        bubble(sort);
        System.out.println(Arrays.toString(sort));
    }
    private static void bubble(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    arr[j]=arr[j]^arr[j+1];
                    arr[j+1]=arr[j]^arr[j+1];
                    arr[j]=arr[j]^arr[j+1];
                }
            }
        }
    }
}
