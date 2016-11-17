package com.tower.common.util;

import java.util.Random;

public class DataValidation {
	
	
	public static String getValidateCode(int count){
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		StringBuilder sb=new StringBuilder();
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
		    int index = rand.nextInt(i);
		    int tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		int result = 0;
		for(int i = 0; i < count; i++)
			  sb.append(String.valueOf( array[i]));
		return sb.toString();
	}

}
