package com.drsi.njmsc.util;

public class ByteArrayUtilTest {
   public static void main(String[] args) {
      if (ByteArrayUtil.byteArray2Int_Little_Endian(null) != 0) {
         throw new AssertionError("null little-endian int");
      }
      if (ByteArrayUtil.byteArray2Float_Big_Endian(new byte[]{1, 2, 3}) != 0.0F) {
         throw new AssertionError("short big-endian float should be 0");
      }
      byte[] four = ByteArrayUtil.int2ByteArray_Little_Endian(0x01020304);
      if (ByteArrayUtil.byteArray2Int_Little_Endian(four) != 0x01020304) {
         throw new AssertionError("round-trip little-endian int");
      }
      if (!"".equals(ByteArrayUtil.byteArray2HexString(null))) {
         throw new AssertionError("null hex string");
      }
      if (ByteArrayUtil.hexString2ByteArray(null) != null) {
         throw new AssertionError("null hex parse");
      }
      System.out.println("ByteArrayUtilTest OK");
   }
}
