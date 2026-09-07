package com.drsi.njmsc.util;

/**
 * Mechanical remaining-life calculation for the circuit breaker.
 * Redis values may arrive as Integer, Long, or String depending on serializer.
 */
public final class RemainingLifeUtil {
   private RemainingLifeUtil() {
   }

   public static int asInt(Object value) {
      if (value == null) {
         return 0;
      }
      if (value instanceof Number) {
         return ((Number)value).intValue();
      }
      try {
         return Integer.parseInt(value.toString().trim());
      } catch (NumberFormatException ignored) {
         return 0;
      }
   }

   /**
    * Remaining operations = rated minus the larger of close/open counts, never negative.
    */
   public static int remainingNum(Object rated, Object closeOperateNum, Object openOperateNum) {
      int ratedNum = asInt(rated);
      if (ratedNum <= 0) {
         return 0;
      }
      int used = Math.max(asInt(closeOperateNum), asInt(openOperateNum));
      return Math.max(0, ratedNum - used);
   }

   public static double remainingLifePercent(Object rated, Object closeOperateNum, Object openOperateNum) {
      int ratedNum = asInt(rated);
      if (ratedNum <= 0) {
         return 0.0;
      }
      return remainingNum(rated, closeOperateNum, openOperateNum) * 100.0 / ratedNum;
   }
}
