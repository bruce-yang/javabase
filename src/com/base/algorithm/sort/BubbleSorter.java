package com.base.algorithm.sort;


/**
 * 这可能是最简单的排序算法了，算法思想是每次从数组末端开始比较相邻两元素，把第i小的冒泡到数组的第i个位置。
 * i从0一直到N-1从而完成排序。
 *（当然也可以从数组开始端开始比较相邻两元素，把第i大的冒泡到数组的第N-i个位置。i从0一直到N-1从而完成排序。)
 * @author Administrator
 *
 * @param <E>
 */
public class BubbleSorter<E extends Comparable<E>> extends Sorter<E> {

    private static  boolean DWON=true;
    
    public final void bubble_down(E[] array, int from, int len)
    {
        for(int i=from;i<from+len;i++)
        {
            for(int j=from+len-1;j>i;j--)
            {
                if(array[j].compareTo(array[j-1])<0)
                {
                    swap(array,j-1,j);
                }
            }
        }
        this.printArray(array);
    }
    public final void printArray(E[] array){
    	for (E e : array) {
			System.out.println(e);
		}
    }
    public final void bubble_up(E[] array, int from, int len)
    {
        for(int i=from+len-1;i>=from;i--)
        {
            for(int j=from;j<i;j++)
            {
                if(array[j].compareTo(array[j+1])>0)
                {
                    swap(array,j,j+1);
                }
            }
        }
    }
    @Override
    public void sort(E[] array, int from, int len) {
        
        if(DWON)
        {
            bubble_down(array,from,len);
        }
        else
        {
            bubble_up(array,from,len);
        }
    }
    public static void main(String[] args) {
		BubbleSorter<Integer> data=new BubbleSorter<Integer>();
		Integer[] intgArr = { 7, 2, 4, 3, 12, 1, 9, 6, 8, 5, 11, 10 };
		data.sort(intgArr);
	}
}