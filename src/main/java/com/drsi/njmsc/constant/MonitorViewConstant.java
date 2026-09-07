package com.drsi.njmsc.constant;

public interface MonitorViewConstant {
   interface MonitorViewPartailType {
      String PRPD = "prpd";
      String PRPS = "prps";
      String PSTA = "psta";
   }

   interface MonitorViewQueryType {
      String MONITOR_VIEW_ARRESTER = "monitor_view_arrester";
      String MONITOR_VIEW_TEMPANDHUM = "monitor_view_tempAndHum";
      String MONITOR_VIEW_PARTAIL = "monitor_view_partail";
      String MONITOR_VIEW_SF = "monitor_view_sf";
      String MONITOR_VIEW_TPS = "monitor_view_tps";
      String MONITOR_VIEW_CB = "monitor_view_cb";
      String MONITOR_VIEW_TH = "monitor_view_th";
      String MONITOR_VIEW_DI = "monitor_view_di";
   }

   interface MonitorViewThermographyType {
      String BASIC = "basic";
      String STATUS = "status";
   }

   interface MonitorViewThrPositionSwitchgearType {
      String OPENTPS = "openTps";
      String CLOSETPS = "closeTps";
   }
}
