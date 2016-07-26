package com.augmentis.ayp.criminalintention;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.UUID;

import static junit.framework.Assert.assertNull;

/**
 * Created by Chayanit on 7/26/2016.
 */
public class CrimeLabTest {

    @Test
    public void test_create_crimeLab_no_error_and_size_100(){
        CrimeLab crimeLab = CrimeLab.getInstance(null);
        for(int i = 0;i<200;i++){
            Crime crime = crimeLab.getCrimeById(UUID.randomUUID());
            assertNull(crime);
        }
    }

    @Test
    public void testArray(){
        int[] arr = new int[20];

        for(int i = 0;i<200;i++){
            try{
                arr[i] = i;
            } catch (ArrayIndexOutOfBoundsException aoe){
                System.out.println("Hello : " + arr.length);
                arr = Arrays.copyOf(arr, i + 80);
                arr[i] = i;

            }
        }
        System.out.println(arr.length);
    }
}
