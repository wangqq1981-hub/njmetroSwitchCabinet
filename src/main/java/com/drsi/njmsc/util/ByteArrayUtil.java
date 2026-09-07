package com.drsi.njmsc.util;

public class ByteArrayUtil {
   public static short byteArray2Short_Little_Endian(byte[] array) {
      if (array == null || array.length == 0 || array.length > 2) {
         return 0;
      }

      short value = 0;

      for (int i = 0; i < array.length; i++) {
         value = (short)(value | (array[i] & 255) << i * 8);
      }

      return value;
   }

   public static short byteArray2Short_Big_Endian(byte[] array) {
      if (array == null || array.length == 0 || array.length > 2) {
         return 0;
      }

      short value = 0;

      for (int i = 0; i < array.length; i++) {
         value = (short)(value | (array[i] & 255) << (array.length - i - 1) * 8);
      }

      return value;
   }

   public static int byteArray2Int_Little_Endian(byte[] array) {
      if (array == null || array.length == 0 || array.length > 4) {
         return 0;
      }

      int value = 0;

      for (int i = 0; i < array.length; i++) {
         value |= (array[i] & 255) << i * 8;
      }

      return value;
   }

   public static int byteArray2Int_Big_Endian(byte[] array) {
      if (array == null || array.length == 0 || array.length > 4) {
         return 0;
      }

      int value = 0;

      for (int i = 0; i < array.length; i++) {
         value |= (array[i] & 255) << (array.length - i - 1) * 8;
      }

      return value;
   }

   public static float byteArray2Float_Little_Endian(byte[] array) {
      return array == null || array.length != 4 ? 0.0F : Float.intBitsToFloat(byteArray2Int_Little_Endian(array));
   }

   public static float byteArray2Float_Big_Endian(byte[] array) {
      return array == null || array.length != 4 ? 0.0F : Float.intBitsToFloat(byteArray2Int_Big_Endian(array));
   }

   public static long byteArray2Long_Little_Endian(byte[] array) {
      if (array == null || array.length != 8) {
         return 0L;
      }

      long value = 0L;

      for (int i = 0; i < array.length; i++) {
         value |= (long)(array[i] & 0xFF) << i * 8;
      }

      return value;
   }

   public static long byteArray2Long_Big_Endian(byte[] array) {
      if (array == null || array.length != 8) {
         return 0L;
      }

      long value = 0L;

      for (int i = 0; i < array.length; i++) {
         value |= (long)(array[i] & 0xFF) << (array.length - i - 1) * 8;
      }

      return value;
   }

   public static double byteArray2Double_Little_Endian(byte[] array) {
      return array == null || array.length != 8 ? 0.0 : Double.longBitsToDouble(byteArray2Long_Little_Endian(array));
   }

   public static double byteArray2Double_Big_Endian(byte[] array) {
      return array == null || array.length != 8 ? 0.0 : Double.longBitsToDouble(byteArray2Long_Big_Endian(array));
   }

   public static String byteArray2HexString(byte[] array) {
      if (array == null) {
         return "";
      }

      StringBuilder builder = new StringBuilder();

      for (byte b : array) {
         String s = Integer.toHexString(b & 255);
         if (s.length() < 2) {
            builder.append("0");
         }

         builder.append(s);
      }

      return builder.toString().toUpperCase();
   }

   public static String[] byteArray2HexStringArray(byte[] array) {
      if (array == null) {
         return new String[0];
      }

      String[] strs = new String[array.length];

      for (int i = 0; i < array.length; i++) {
         String s = Integer.toHexString(array[i] & 255);
         if (s.length() < 2) {
            s = '0' + s;
         }

         strs[i] = s;
      }

      return strs;
   }

   public static byte[] short2ByteArray_Little_Endian(short s) {
      byte[] array = new byte[2];

      for (int i = 0; i < array.length; i++) {
         array[i] = (byte)(s >> i * 8);
      }

      return array;
   }

   public static byte[] short2ByteArray_Big_Endian(short s) {
      byte[] array = new byte[2];

      for (int i = 0; i < array.length; i++) {
         array[array.length - 1 - i] = (byte)(s >> i * 8);
      }

      return array;
   }

   public static byte[] int2ByteArray_Little_Endian(int s) {
      byte[] array = new byte[4];

      for (int i = 0; i < array.length; i++) {
         array[i] = (byte)(s >> i * 8);
      }

      return array;
   }

   public static byte[] int2ByteArray_Big_Endian(int s) {
      byte[] array = new byte[4];

      for (int i = 0; i < array.length; i++) {
         array[array.length - 1 - i] = (byte)(s >> i * 8);
      }

      return array;
   }

   public static byte[] float2ByteArray_Little_Endian(float f) {
      return int2ByteArray_Little_Endian(Float.floatToIntBits(f));
   }

   public static byte[] float2ByteArray_Big_Endian(float f) {
      return int2ByteArray_Big_Endian(Float.floatToIntBits(f));
   }

   public static byte[] long2ByteArray_Little_Endian(long l) {
      byte[] array = new byte[8];

      for (int i = 0; i < array.length; i++) {
         array[i] = (byte)(l >> i * 8);
      }

      return array;
   }

   public static byte[] long2ByteArray_Big_Endian(long l) {
      byte[] array = new byte[8];

      for (int i = 0; i < array.length; i++) {
         array[array.length - 1 - i] = (byte)(l >> i * 8);
      }

      return array;
   }

   public static byte[] double2ByteArray_Little_Endian(double d) {
      return long2ByteArray_Little_Endian(Double.doubleToLongBits(d));
   }

   public static byte[] double2ByteArray_Big_Endian(double d) {
      return long2ByteArray_Big_Endian(Double.doubleToLongBits(d));
   }

   public static byte[] hexString2ByteArray(String hexString) {
      if (hexString == null || hexString.length() % 2 != 0) {
         return null;
      }

      byte[] array = new byte[hexString.length() / 2];
      int value = 0;

      for (int i = 0; i < hexString.length(); i++) {
         char s = hexString.charAt(i);
         if (i % 2 == 0) {
            value = Integer.parseInt(String.valueOf(s), 16) * 16;
         } else {
            value += Integer.parseInt(String.valueOf(s), 16);
            array[i / 2] = (byte)value;
            value = 0;
         }
      }

      return array;
   }

   public static byte[] hexStringArray2ByteArray(String[] hexString) {
      byte[] array = new byte[hexString.length];

      for (int i = 0; i < hexString.length; i++) {
         int value = Integer.parseInt(hexString[i], 16);
         array[i] = (byte)value;
      }

      return array;
   }
}
