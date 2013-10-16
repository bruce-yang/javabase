package com.base;
import java.util.Arrays;

public class LessionSystemArraycopy {
  public static void main(String[] args) {
    // 此方位为native方法。
    // public static native void arraycopy(
    // Object src, int srcPos, Object dest,
    // int destPos, int length);
    // 初始化
    int[] ids = { 1, 2, 3, 4, 5 };
    System.out.println(Arrays.toString(ids)); // [1, 2, 3, 4, 5]
    // 测试自我复制
    // 把从索引0开始的2个数字复制到索引为3的位置上
    System.arraycopy(ids, 0, ids, 3, 2);
    System.out.println(Arrays.toString(ids)); // [1, 2, 3, 1, 2]
    // 测试复制到别的数组上
    // 将数据的索引1开始的3个数据复制到目标的索引为0的位置上
    int[] ids2 = new int[6];
    System.arraycopy(ids, 1, ids2, 0, 3);
    System.out.println(Arrays.toString(ids2)); // [2, 3, 1, 0, 0, 0]
    // 此功能要求
    // 源的起始位置+长度不能超过末尾
    // 目标起始位置+长度不能超过末尾
    // 且所有的参数不能为负数
    try {
      System.arraycopy(ids, 0, ids2, 0, ids.length + 1);
    } catch (IndexOutOfBoundsException ex) {
      // 发生越界异常，数据不会改变
      System.out.println("拷贝发生异常：数据越界。");
    }
    System.out.println(Arrays.toString(ids2)); // [2, 3, 1, 0, 0, 0]
    // 如果是类型转换问题
    Object[] o1 = { 1, 2, 3, 4.5, 6.7 };
    Integer[] o2 = new Integer[5];
    System.out.println(Arrays.toString(o2)); // [null, null, null, null, null]
    try {
      System.arraycopy(o1, 0, o2, 0, o1.length);
    } catch (ArrayStoreException ex) {
      // 发生存储转换，部分成功的数据会被复制过去
      System.out.println("拷贝发生异常：数据转换错误，无法存储。");
    }
    // 从结果看，前面3个可以复制的数据已经被存储了。剩下的则没有
    System.out.println(Arrays.toString(o2)); // [1, 2, 3, null, null]
  }
}