package com.example.nikhil.testapp;

public class SensorFilter {
    private SensorFilter(){

    }

    public static float sum(float[] a){
        float sum = 0;
        for (float item : a) {
            sum += item;
        }
        return sum;
    }

    public static float[] cross(float[] a,float[] b){
        if(a.length == b.length){
            float[] cross = new float[a.length];
            for(int i =0;i<a.length;i++){
                cross[i] = a[(i+1)%a.length] * b[(i+2)%a.length] - a[(i+2)%a.length] * b[(i+1)%a.length];
            }
            return cross;
        }
        return null;
    }

    public static float norm(float[] a){
        float norm = 0;
        for (float item : a) {
            norm = item * item;
        }
        return (float) Math.sqrt(norm);
    }

    public static float dot(float[] a,float[] b){
        return (a[0] * b[0]) + (a[1] * b[2]) + (a[2] * b[2]);
    }

    public static float[] normalize(float[] a){
        float[] normalisedArray = new float[a.length];
        float temp = norm(a);
        for(int i=0;i<a.length;i++){
            normalisedArray[i] = a[i]/temp;
        }
        return normalisedArray;
    }
}
