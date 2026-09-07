package com.drsi.njmsc.constant;

public interface ThresholdStants {
   String Alam = "Alam";
   String close_ironCoreStartingCurrent = "close_ironCoreStartingCurrent";
   String close_ironCoreStoppingCurrent = "close_ironCoreStoppingCurrent";
   String close_workingCurrentOfCoil = "close_workingCurrentOfCoil";
   String close_ironCoreStartingTime = "close_ironCoreStartingTime";
   String close_ironCoreStoppingTime = "close_ironCoreStoppingTime";
   String close_ironCoreWorkingTime = "close_ironCoreWorkingTime";
   String close_actionTime = "close_actionTime";
   String open_ironCoreStartingCurrent = "open_ironCoreStartingCurrent";
   String open_ironCoreStoppingCurrent = "open_ironCoreStoppingCurrent";
   String open_workingCurrentOfCoil = "open_workingCurrentOfCoil";
   String open_ironCoreStartingTime = "open_ironCoreStartingTime";
   String open_ironCoreStoppingTime = "open_ironCoreStoppingTime";
   String open_ironCoreWorkingTime = "open_ironCoreWorkingTime";
   String open_actionTime = "open_actionTime";
   String energy_startingCurrent = "energy_startingCurrent";
   String energy_idleElectricCurrent = "energy_idleElectricCurrent";
   String energy_outputCurrent = "energy_outputCurrent";
   String energy_startingTime = "energy_startingTime";
   String energy_idleElectricTime = "energy_idleElectricTime";
   String energy_outputTime = "energy_outputTime";
   String energy_actionTime = "energy_actionTime";
   String threeStation_peakValue = "threeStation_peakValue";
   String threeStation_valleyValue = "threeStation_valleyValue";
   String threeStation_actionTime = "threeStation_actionTime";

   static String getDeviceAlarmRedisKey(String deviceCode, String alarmType) {
      return deviceCode + ":" + alarmType + ":" + "Alam";
   }

   static String getDeviceRedisKey(String deviceCode, String alarmType) {
      return deviceCode + ":" + alarmType;
   }

   interface PartDesc {
      String close = "合闸";
      String open = "分闸";
      String energy = "储能电机";
      String threeStation = "三工位电机";
   }
}
