package com.drsi.njmsc.constant;

public enum ThresholdEnums {
   close_ironCoreStartingCurrent("close_ironCoreStartingCurrent", "铁芯启动电流", "合闸", 8, 16409),
   close_ironCoreStoppingCurrent("close_ironCoreStoppingCurrent", "铁芯停止电流", "合闸", 9, 16410),
   close_workingCurrentOfCoil("close_workingCurrentOfCoil", "线圈工作电流", "合闸", 10, 16411),
   close_ironCoreStartingTime("close_ironCoreStartingTime", "铁芯启动时间", "合闸", 11, 16412),
   close_ironCoreStoppingTime("close_ironCoreStoppingTime", "铁芯停止时间", "合闸", 12, 16413),
   close_ironCoreWorkingTime("close_ironCoreWorkingTime", "合闸", "线圈工作时间", 13, 16414),
   close_actionTime("close_actionTime", "动作时间", "合闸", 14, 16415),
   open_ironCoreStartingCurrent("open_ironCoreStartingCurrent", "铁芯启动电流", "分闸", 1, 16402),
   open_ironCoreStoppingCurrent("open_ironCoreStoppingCurrent", "铁芯停止电流", "分闸", 2, 16403),
   open_workingCurrentOfCoil("open_workingCurrentOfCoil", "线圈工作电流", "分闸", 3, 16404),
   open_ironCoreStartingTime("open_ironCoreStartingTime", "铁芯启动时间", "分闸", 4, 16405),
   open_ironCoreStoppingTime("open_ironCoreStoppingTime", "铁芯停止时间", "分闸", 5, 16406),
   open_ironCoreWorkingTime("open_ironCoreWorkingTime", "线圈工作时间", "分闸", 6, 16407),
   open_actionTime("open_actionTime", "动作时间", "分闸", 7, 16408),
   energy_startingCurrent("energy_startingCurrent", "启动电流", "储能电机", 15, 16416),
   energy_idleElectricCurrent("energy_idleElectricCurrent", "空转电流", "储能电机", 16, 16417),
   energy_outputCurrent("energy_outputCurrent", "出力电流", "储能电机", 17, 16418),
   energy_startingTime("energy_startingTime", "启动时间", "储能电机", 18, 16419),
   energy_idleElectricTime("energy_idleElectricTime", "空转时间", "储能电机", 19, 16420),
   energy_outputTime("energy_outputTime", "出力时间", "储能电机", 20, 16421),
   energy_actionTime("energy_actionTime", "动作时间", "储能电机", 21, 16422),
   threeStation_peakValue("threeStation_peakValue", "电流峰值", "三工位电机", 22, 16423),
   threeStation_valleyValue("threeStation_valleyValue", "电流谷值", "三工位电机", 23, 16424),
   threeStation_actionTime("threeStation_actionTime", "动作时间", "三工位电机", 24, 16425);

   private String code;
   private String desc;
   private String partDesc;
   private Integer aiAlarmAddress;
   private Integer diAddress;

   ThresholdEnums(String code, String desc, String partDesc, Integer aiAlarmAddress, Integer diAddress) {
      this.code = code;
      this.desc = desc;
      this.partDesc = partDesc;
      this.aiAlarmAddress = aiAlarmAddress;
      this.diAddress = diAddress;
   }

   public static String getDescByAiAlarmAddress(Integer aiAlarmAddress) {
      for (ThresholdEnums value : values()) {
         if (value.getAiAlarmAddress() == aiAlarmAddress) {
            return value.getDesc();
         }
      }

      return "";
   }

   public static String getPartDescBydiAddress(Integer aiAlarmAddress) {
      for (ThresholdEnums value : values()) {
         if (value.getAiAlarmAddress() == aiAlarmAddress) {
            return value.getPartDesc();
         }
      }

      return "";
   }

   public String getCode() {
      return this.code;
   }

   public String getDesc() {
      return this.desc;
   }

   public String getPartDesc() {
      return this.partDesc;
   }

   public Integer getAiAlarmAddress() {
      return this.aiAlarmAddress;
   }

   public Integer getDiAddress() {
      return this.diAddress;
   }
}
