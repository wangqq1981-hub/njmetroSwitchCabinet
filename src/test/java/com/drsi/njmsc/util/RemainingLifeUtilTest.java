package com.drsi.njmsc.util;

public class RemainingLifeUtilTest {
   public static void main(String[] args) {
      assertEqual(0, RemainingLifeUtil.remainingNum(null, 1, 2));
      assertEqual(0, RemainingLifeUtil.remainingNum(0, 1, 2));
      assertEqual(7000, RemainingLifeUtil.remainingNum(10000, 3000, 2000));
      assertEqual(0, RemainingLifeUtil.remainingNum(100, 150, 80));
      assertEqual(7000, RemainingLifeUtil.remainingNum(10000L, 3000, 2000));
      assertEqual(70.0, RemainingLifeUtil.remainingLifePercent(10000, 3000, 2000), 0.0001);
      assertEqual(0, RemainingLifeUtil.asInt(null));
      assertEqual(9, RemainingLifeUtil.asInt("9"));
      System.out.println("RemainingLifeUtilTest OK");
   }

   private static void assertEqual(int expected, int actual) {
      if (expected != actual) {
         throw new AssertionError("expected " + expected + " but was " + actual);
      }
   }

   private static void assertEqual(double expected, double actual, double eps) {
      if (Math.abs(expected - actual) > eps) {
         throw new AssertionError("expected " + expected + " but was " + actual);
      }
   }
}
