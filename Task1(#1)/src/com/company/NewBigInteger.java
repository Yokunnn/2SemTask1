package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NewBigInteger {

    private int[] number;
    private boolean positive = true;

    public NewBigInteger(){

    }
    public NewBigInteger(int[] number, boolean positive){
        this.number = number;
        this.positive = positive;
    }
    public NewBigInteger(int[] number){
        this.number = number;
        positive = true;
    }
    public NewBigInteger(String strNumber){
        if(strNumber.startsWith("-")){
            positive = false;
            strNumber = strNumber.substring(1);
            if(strNumber.matches("0")){
                positive = true;
            }
        }
        number = new int[strNumber.length()];
        for(int i = 0; i<strNumber.length(); i++){
            number[i] = Integer.parseInt(strNumber.substring(i, i+1));
        }
        number = deleteFirstZeros(number);
    }
    public static NewBigInteger createDefaultRandom(){
        return createRandom(20, 10, true);
    }
    private static NewBigInteger createRandom(int size, int numBound, boolean positive){
        Random RND = new Random();
        int[] number = new int[size];
        for(int i = 0; i < size; i++){
            number[i] = RND.nextInt(numBound);
        }
        return new NewBigInteger(number, positive);
    }
    private int[] deleteFirstZeros(int[] number){
        List<Integer> num = new ArrayList<>();
        boolean real = false;
        for(int i = 0; i< number.length; i++){
            if(!real && number[i]!=0){
                real = true;
                num.add(number[i]);
            } else if(real){
                num.add(number[i]);
            }
        }
        if(num.isEmpty()){
            num.add(0);
        }
        int[] res = new int[num.size()];
        int i = 0;
        for(Integer a : num){
            res[i] = a;
            i++;
        }
        return res;
    }//O(n)
    public NewBigInteger add(NewBigInteger value){
        if(positive==value.positive){
            NewBigInteger solution = new NewBigInteger();
            int[] result = new int[max(number.length, value.number.length)+1];
            int k = 1;
            for(int i = result.length-1; i>=0; i--){
                int a = 0;
                int b = 0;
                if(number.length-k>=0){
                    a = number[number.length-k];
                }
                if(value.number.length-k>=0){
                    b = value.number[value.number.length-k];
                }
                int sumNum = a + b;
                k++;
                result[i] += sumNum;
                if(result[i]>=10){
                    result[i-1] += result[i]/10;
                    result[i] = result[i]%10;
                }
            }
            result = deleteFirstZeros(result);
            solution.number = result;
            solution.positive = positive;
            return solution;
        } else if(!positive){
            NewBigInteger nw = new NewBigInteger();
            nw.number = number;
            return value.subtract(nw);
        } else {
            NewBigInteger nw = new NewBigInteger();
            nw.number = value.number;
            return subtract(nw);
        }
    }//O(n)
    private int max(int a, int b){
        if(a>b){
            return a;
        } else return b;
    }
    public NewBigInteger subtract(NewBigInteger value){
        if(positive==value.positive){
            if(!positive){
                NewBigInteger nw1 = new NewBigInteger();
                nw1.number = number;
                NewBigInteger nw2 = new NewBigInteger();
                nw2.number = value.number;
                return nw2.subtract(nw1);
            } else {
                NewBigInteger solution = new NewBigInteger();
                int[] result = new int[max(number.length, value.number.length)];
                int[] tmp1 = number;
                int[] tmp2 = value.number;
                if(!exceed(value)){
                    solution.positive = false;
                    tmp2 = number;
                    tmp1 = value.number;
                } else {
                    solution.positive = true;
                }
                for(int i = 0; i<result.length; i++){
                    if(tmp2.length-i>0){
                        if(tmp1[tmp1.length-i-1]<tmp2[tmp2.length-i-1]){
                            tmp1[tmp1.length-i-1] += 10;
                            tmp1[tmp1.length-i-2]--;
                        }
                        result[result.length-i-1] = tmp1[tmp1.length-i-1] - tmp2[tmp2.length-i-1];
                    } else {
                        if(tmp1[tmp1.length-i-1]==-1 && tmp1.length-i-2>=0){
                            tmp1[tmp1.length-i-1] += 10;
                            tmp1[tmp1.length-i-2]--;
                        }
                        result[result.length-i-1] = tmp1[tmp1.length-i-1];
                    }
                }
                result = deleteFirstZeros(result);
                solution.number = result;
                return solution;
            }
        } else {
            NewBigInteger nw = new NewBigInteger();
            nw.positive = positive;
            nw.number = value.number;
            return add(nw);
        }
    }//O(n)
    private boolean exceed(NewBigInteger another){
        if(number.length>another.number.length){
            return true;
        } else if(number.length<another.number.length){
            return false;
        } else {
            for(int i = 0; i<number.length; i++){
                if(number[i]>another.number[i]){
                    return true;
                } else if(number[i]<another.number[i]){
                    return false;
                }
            }
            return true;
        }
    }//O(n)
    private boolean exceed(List<Integer> another){
        int[] tmpNumber = new int[another.size()];
        int i = 0;
        for(int num : another){
            tmpNumber[i] = num;
            i++;
        }
        NewBigInteger another1 = new NewBigInteger();
        another1.number = tmpNumber;
        return this.exceed(another1);
    }
    public NewBigInteger multiply(NewBigInteger value){
        NewBigInteger solution = new NewBigInteger();
        int[] result = new int[number.length*value.number.length+1];
        if(positive==value.positive){
            solution.positive = true;
        } else {
            solution.positive = false;
        }
        for(int i = 0; i<value.number.length; i++){
            for(int j = 0; j<number.length; j++){
                result[result.length-j-i-1] += number[number.length-j-1] * value.number[value.number.length-i-1];
                result[result.length-j-i-2] += result[result.length-j-i-1]/10;
                result[result.length-j-i-1] = result[result.length-j-i-1]%10;
            }
        }
        result = deleteFirstZeros(result);
        solution.number = result;
        return solution;
    }//O(n^2)

    public NewBigInteger multiplyKaratsuba(NewBigInteger value){
        if(number.length<=2 || value.number.length<=2){ //какие то условные числа
            return multiply(value);
        }
        int maxLen = Math.max(number.length, value.number.length);
        int half = (maxLen + 1) / 2;
        NewBigInteger a = getUpper(half);
        NewBigInteger b = getLower(half);
        NewBigInteger c = value.getUpper(half);
        NewBigInteger d = value.getLower(half);
        //System.out.println(a.toStr() + " a; " + b.toStr() + " b; " + c.toStr() + " c; " + d.toStr() + " d");
        NewBigInteger p1 = a.multiplyKaratsuba(c);
        NewBigInteger p2 = b.multiplyKaratsuba(d);
        NewBigInteger p3 = a.add(b).multiplyKaratsuba(c.add(d));
        /*if(maxLen % 2 == 1){
            half--;
        }*/
        NewBigInteger result = p1.shiftLeft(half).add(p3.subtract(p1).subtract(p2)).shiftLeft(half).add(p2);
        return result;
    }

    private NewBigInteger shiftLeft(int points){
        NewBigInteger solution = new NewBigInteger();
        int[] result = new int[number.length+points];
        for(int i = 0; i<result.length; i++){
            result[i] = i<number.length ? number[i] : 0;
        }
        solution.number = result;
        return solution;
    }

    private NewBigInteger getLower(int point){
        if(number.length<=point){
            return this;
        }
        NewBigInteger solution = new NewBigInteger();
        int[] result = new int[point];
        for(int i = 0; i<point; i++){
            result[result.length-i-1] = number[number.length-i-1];
        }
        solution.number = result;
        return solution;
    }

    private NewBigInteger getUpper(int point){
        if(number.length<=point){
            return new NewBigInteger(new int[]{0});
        }
        NewBigInteger solution = new NewBigInteger();
        int[] result = new int[number.length-point];
        for(int i = 0; i<number.length-point; i++){
            result[i] = number[i];
        }
        solution.number = result;
        return solution;
    }

    public NewBigInteger sortNumbers(){
        int[] copy = new int[number.length];
        for(int i = 0; i < number.length; i++){
            copy[i] = number[i];
        }
        Arrays.sort(copy);
        return new NewBigInteger(copy);
    }

    public NewBigInteger mergeSortNumbers(){
        int[] copy = new int[number.length];
        for(int i = 0; i < number.length; i++){
            copy[i] = number[i];
        }
        mergeSort(copy, copy.length);
        return new NewBigInteger(copy);
    }

    private void merge(int[] left_arr,int[] right_arr, int[] arr,int left_size, int right_size){
        int i = 0, l = 0, r = 0;
        while(l<left_size && r<right_size){
            if(left_arr[l]<right_arr[r]){
                arr[i++] = left_arr[l++];
            }
            else{
                arr[i++] = right_arr[r++];
            }
        }
        while(l<left_size){
            arr[i++] = left_arr[l++];
        }
        while(r<right_size){
            arr[i++] = right_arr[r++];
        }
    }

    private void mergeSort(int [] arr, int len){
        if (len < 2){
            return;
        }
        int mid = len / 2;
        int [] left_arr = new int[mid];
        int [] right_arr = new int[len-mid];
        int k = 0;
        for(int i = 0;i<len;++i){
            if(i<mid){
                left_arr[i] = arr[i];
            }
            else{
                right_arr[k] = arr[i];
                k = k+1;
            }
        }
        mergeSort(left_arr,mid);
        mergeSort(right_arr,len-mid);
        merge(left_arr,right_arr,arr,mid,len-mid);
    }

    public NewBigInteger divide(NewBigInteger value)throws ArithmeticException{
        if(value.number[0]==0){
            throw new ArithmeticException();
        }
        NewBigInteger solution = new NewBigInteger();
        if(!this.exceed(value)){
            solution.number = new int[]{0};
            return solution;
        }
        if(this.positive==value.positive){
            solution.positive = true;
        } else {
            solution.positive = false;
        }
        List<Integer> resList = new ArrayList<>();
        int[] tmp;
        NewBigInteger t = new NewBigInteger();
        NewBigInteger val = new NewBigInteger();
        val.number = value.number;
        List<Integer> tmpList = new ArrayList<>();
        for(int i = 0; i<number.length; i++){
            tmpList.add(number[i]);
            if(!val.exceed(tmpList)){
                tmp = new int[tmpList.size()];
                int j = 0;
                for(int num : tmpList){
                    tmp[j] = num;
                    j++;
                }
                t.number = tmp;
                int count = 0;
                while(t.exceed(val)){
                    t = t.subtract(val);
                    t.number = deleteFirstZeros(t.number);
                    count++;
                }
                resList.add(count);
                tmpList.clear();
                for(int num : t.number){
                    tmpList.add(num);
                }
            }
        }
        int[] result = new int[resList.size()];
        int i = 0;
        for(int a : resList){
            result[i] = a;
            i++;
        }
        solution.number = result;
        return solution;
    }//O(n^2)
    public NewBigInteger mod(NewBigInteger value){
        if(value.number[0]==0){
            throw new ArithmeticException();
        }
        NewBigInteger solution = new NewBigInteger();
        if(!this.exceed(value)){
            solution.number = new int[]{0};
            return solution;
        }
        if(this.positive==value.positive){
            solution.positive = true;
        } else {
            solution.positive = false;
        }
        int[] result;
        int[] tmp;
        NewBigInteger t = new NewBigInteger();
        NewBigInteger val = new NewBigInteger();
        val.number = value.number;
        List<Integer> tmpList = new ArrayList<>();
        for(int i = 0; i<number.length; i++){
            tmpList.add(number[i]);
            if(!val.exceed(tmpList)){
                tmp = new int[tmpList.size()];
                int j = 0;
                for(int num : tmpList){
                    tmp[j] = num;
                    j++;
                }
                t.number = tmp;
                while(t.exceed(val)){
                    t = t.subtract(val);
                    t.number = deleteFirstZeros(t.number);
                }
                tmpList.clear();
                for(int num : t.number){
                    tmpList.add(num);
                }
            }
        }
        result = new int[tmpList.size()];
        int i = 0;
        for(int a : tmpList){
            result[i] = a;
            i++;
        }
        solution.number = result;
        return solution;
    }//O(n^2)
    public String toStr(){
        StringBuilder str = new StringBuilder();
        if(!positive){
            str.append("-");
        }
        for(int i = 0; i<number.length; i++){
            str.append(number[i]);
        }
        if(str.isEmpty()){
            str.append("0");
        }
        return str.toString();
    }
}
