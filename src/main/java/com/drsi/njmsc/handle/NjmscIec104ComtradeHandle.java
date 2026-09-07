package com.drsi.njmsc.handle;

import com.alibaba.fastjson2.JSON;
import com.drsi.njmsc.dto.dto.DeviceRawIecTelemtryDto;
import com.drsi.njmsc.dto.dto.FileDto;
import com.drsi.njmsc.dto.model.AnalogChannelInfoModel;
import com.drsi.njmsc.dto.model.ChannelDataModel;
import com.drsi.njmsc.dto.model.ComtradeCfgModel;
import com.drsi.njmsc.dto.model.ComtradeChannelDataModel;
import com.drsi.njmsc.dto.model.DigitalChannelInfoModel;
import com.drsi.njmsc.util.ByteArrayUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NjmscIec104ComtradeHandle {
   private static final Logger log = LoggerFactory.getLogger(NjmscIec104ComtradeHandle.class);

   public List<ComtradeChannelDataModel> handleWave(DeviceRawIecTelemtryDto deviceRawIecTelemtryDto, Integer commonAddress, List<FileDto> fileDtos) throws Exception {
      long start = System.currentTimeMillis();
      ComtradeCfgModel cfg = new ComtradeCfgModel();
      this.handleWaveCfg(fileDtos, cfg);
      log.info("handleWaveCfg：{}", JSON.toJSONString(cfg).substring(0, 50));
      List<ComtradeChannelDataModel> waveData = new ArrayList<>();
      handleWaveDat(fileDtos, cfg, waveData);
      log.info("loadWave consume: {} ms", System.currentTimeMillis() - start);
      deviceRawIecTelemtryDto.getMapComtradeCfgModels().put(String.valueOf(commonAddress), cfg);
      return waveData;
   }

   public void handleWaveCfg(List<FileDto> fileDtos, ComtradeCfgModel cfg) throws Exception {
      for (FileDto fileDto : fileDtos) {
         if (fileDto.getFileName().endsWith(".cfg")) {
            InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(fileDto.getFileBytes()), "GB2312");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp = bufferedReader.readLine();
            String[] l1 = temp.split(",");
            cfg.setStationName(l1[0]);
            cfg.setRecDevId(l1[1]);
            cfg.setRevYear(l1[2]);
            temp = bufferedReader.readLine();
            String[] l2 = temp.split(",");
            cfg.setChannelTotalNum(Integer.valueOf(l2[0].replaceAll(" ", "")));
            cfg.setAnalogChannelTotalNum(Integer.valueOf(l2[1].replaceAll(" ", "").substring(0, l2[1].length() - 2)));
            cfg.setDigitalChannelTotalNum(Integer.valueOf(l2[2].replaceAll(" ", "").substring(0, l2[2].length() - 2)));
            if (cfg.getChannelTotalNum() != cfg.getAnalogChannelTotalNum() + cfg.getDigitalChannelTotalNum()) {
               throw new Exception("comtrade .cfg 文件无效");
            }

            List<AnalogChannelInfoModel> ais = new ArrayList<>();

            for (int i = 0; i < cfg.getAnalogChannelTotalNum(); i++) {
               temp = bufferedReader.readLine();
               String[] l3 = temp.split(",");
               if (l3.length != 13) {
                  throw new Exception("comtrade .cfg 文件无效");
               }

               AnalogChannelInfoModel ai = new AnalogChannelInfoModel();
               ai.setAn(Integer.valueOf(l3[0].replaceAll(" ", "")));
               ai.setChId(l3[1]);
               ai.setPh(l3[2]);
               ai.setCcbm(l3[3]);
               ai.setUu(l3[4]);
               ai.setA(Float.valueOf(l3[5]));
               ai.setB(Float.valueOf(l3[6]));
               ai.setSkew(Float.valueOf(l3[7]));
               ai.setMin(Integer.valueOf(l3[8]));
               ai.setMax(Integer.valueOf(l3[9]));
               ai.setPrimary(Float.valueOf(l3[10]));
               ai.setSecondary(Float.valueOf(l3[11]));
               ai.setPs(l3[12]);
               ais.add(ai);
            }

            cfg.setAnalogChannelInfos(ais);
            List<DigitalChannelInfoModel> dis = new ArrayList<>();

            for (int j = 0; j < cfg.getDigitalChannelTotalNum(); j++) {
               temp = bufferedReader.readLine();
               String[] l3 = temp.split(",");
               if (l3.length != 5) {
                  throw new Exception("comtrade .cfg 文件无效");
               }

               DigitalChannelInfoModel di = new DigitalChannelInfoModel();
               di.setDn(Integer.valueOf(l3[0].replaceAll(" ", "")));
               di.setChId(l3[1]);
               di.setPh(l3[2]);
               di.setCcbm(l3[3]);
               di.setY(Integer.valueOf(l3[4].replaceAll(" ", "")));
               dis.add(di);
            }

            cfg.setDigitalChannelInfos(dis);
            temp = bufferedReader.readLine();
            cfg.setChannelFrequency(Integer.valueOf(temp.replaceAll(" ", "")));
            temp = bufferedReader.readLine();
            cfg.setNartes(Integer.valueOf(temp.replaceAll(" ", "")));
            if (cfg.getNartes() != 1) {
               throw new Exception("parsing is not supported at present");
            }

            temp = bufferedReader.readLine();
            String[] l4 = temp.split(",");
            cfg.setSamp(Integer.valueOf(l4[0].replaceAll(" ", "")));
            cfg.setEndSamp(Integer.valueOf(l4[1].replaceAll(" ", "")));
            temp = bufferedReader.readLine();
            cfg.setTfdDate(LocalDateTime.parse(temp, DateTimeFormatter.ofPattern("dd/MM/yyyy,HH:mm:ss.SSSSSS")));
            temp = bufferedReader.readLine();
            cfg.setTpDate(LocalDateTime.parse(temp, DateTimeFormatter.ofPattern("dd/MM/yyyy,HH:mm:ss.SSSSSS")));
            temp = bufferedReader.readLine();
            cfg.setFt(temp);
            temp = bufferedReader.readLine();
            cfg.setTimemult(Integer.valueOf(temp.replaceAll(" ", "")));
            bufferedReader.close();
            inputStreamReader.close();
         }
      }
   }

   public static void handleWaveDat(List<FileDto> fileDtos, ComtradeCfgModel cfg, List<ComtradeChannelDataModel> waveData) throws Exception {
      boolean handleFlag = false;

      for (FileDto fileDto : fileDtos) {
         if (fileDto.getFileName().endsWith(".dat")) {
            if ("BINARY".equalsIgnoreCase(cfg.getFt())) {
               onloadDat(fileDto, cfg, waveData);
            }

            handleFlag = true;
            break;
         }
      }

      if (!handleFlag) {
         log.info("comtrade .dat 文件不存在");
         throw new FileNotFoundException();
      }
   }

   public static void onloadDat(FileDto fileDto, ComtradeCfgModel cfg, List<ComtradeChannelDataModel> waveData) throws Exception {
      int dl = cfg.getDigitalChannelTotalNum() % 16 == 0
         ? (cfg.getDigitalChannelTotalNum() == 0 ? 0 : cfg.getDigitalChannelTotalNum() / 16)
         : cfg.getDigitalChannelTotalNum() / 16 + 1;
      int len = 8 + cfg.getAnalogChannelTotalNum() * 2 + dl * 2;
      if (fileDto.getFileSize() != cfg.getEndSamp() * len) {
         log.info("comtrade .dat 文件无效，文件大小不符");
         throw new Exception("comtrade .dat 文件无效，文件大小不符");
      }

      ZonedDateTime tfdz = cfg.getTfdDate().atZone(ZoneId.systemDefault());
      long tfdTimestamp = tfdz.toEpochSecond() * 1000000L + tfdz.getNano() / 1000;
      ZonedDateTime tpz = cfg.getTpDate().atZone(ZoneId.systemDefault());
      long tpTimestamp = tpz.toEpochSecond() * 1000000L + tpz.getNano() / 1000;
      Integer tpOffset = (int)(cfg.getSamp().intValue() * (tpTimestamp - tfdTimestamp)) / 1000000;

      for (int i = 0; i < cfg.getChannelTotalNum(); i++) {
         waveData.add(
            new ComtradeChannelDataModel()
               .setN(i + 1)
               .setChId(
                  i < cfg.getAnalogChannelTotalNum()
                     ? cfg.getAnalogChannelInfos().get(i).getChId()
                     : cfg.getDigitalChannelInfos().get(i - cfg.getAnalogChannelTotalNum()).getChId()
               )
               .setPh(
                  i < cfg.getAnalogChannelTotalNum()
                     ? cfg.getAnalogChannelInfos().get(i).getPh()
                     : cfg.getDigitalChannelInfos().get(i - cfg.getAnalogChannelTotalNum()).getPh()
               )
               .setCcbm(
                  i < cfg.getAnalogChannelTotalNum()
                     ? cfg.getAnalogChannelInfos().get(i).getCcbm()
                     : cfg.getDigitalChannelInfos().get(i - cfg.getAnalogChannelTotalNum()).getCcbm()
               )
               .setUu(i < cfg.getAnalogChannelTotalNum() ? cfg.getAnalogChannelInfos().get(i).getUu() : null)
               .setY(i < cfg.getAnalogChannelTotalNum() ? null : cfg.getDigitalChannelInfos().get(i - cfg.getAnalogChannelTotalNum()).getY())
               .setWaveRawData(new ArrayList<>())
               .setTpOffset(tpOffset)
         );
      }

      log.info("init waveData fir suc...");
      ByteArrayInputStream in = new ByteArrayInputStream(fileDto.getFileBytes());
      byte[] buffer = new byte[len];

      int dc;
      for (dc = 0; in.read(buffer) != -1; dc++) {
         int n = ByteArrayUtil.byteArray2Int_Little_Endian(new byte[]{buffer[0], buffer[1], buffer[2], buffer[3]});

         for (int i = 0; i < cfg.getAnalogChannelTotalNum(); i++) {
            ChannelDataModel channelData = new ChannelDataModel().setOffset(n);
            long usIntervalOfTfd = n * 1000000L / cfg.getSamp().intValue();
            channelData.setTimestamp(tfdTimestamp + usIntervalOfTfd);
            int v = ByteArrayUtil.byteArray2Short_Little_Endian(new byte[]{buffer[8 + i * 2], buffer[9 + i * 2]});
            float tt = v * cfg.getAnalogChannelInfos().get(i).getA() + cfg.getAnalogChannelInfos().get(i).getB();
            if ("P".equals(cfg.getAnalogChannelInfos().get(i).getPs())) {
               channelData.setPValue(tt);
               channelData.setSValue(tt / cfg.getAnalogChannelInfos().get(i).getPrimary() * cfg.getAnalogChannelInfos().get(i).getSecondary());
            } else if ("S".equals(cfg.getAnalogChannelInfos().get(i).getPs())) {
               channelData.setSValue(tt);
               channelData.setPValue(tt * cfg.getAnalogChannelInfos().get(i).getPrimary() / cfg.getAnalogChannelInfos().get(i).getSecondary());
            }

            waveData.get(i).getWaveRawData().add(channelData);
         }

         for (int i = 0; i < cfg.getDigitalChannelTotalNum(); i++) {
            ChannelDataModel channelData = new ChannelDataModel().setOffset(n);
            long usIntervalOfTfd = n * 1000000L / cfg.getSamp().intValue();
            channelData.setTimestamp(tfdTimestamp + usIntervalOfTfd);
            int diStartOfBuf = (i + 1) % 16 == 0 ? cfg.getDigitalChannelTotalNum() / 16 : cfg.getDigitalChannelTotalNum() / 16 + 1;
            byte[] bs = new byte[]{
               buffer[8 + cfg.getAnalogChannelTotalNum() * diStartOfBuf * 2], buffer[9 + cfg.getAnalogChannelTotalNum() * diStartOfBuf * 2]
            };
            short bsi = ByteArrayUtil.byteArray2Short_Little_Endian(bs);
            int v = bsi << 15 - i >>> 15;
            channelData.setValue(v);
            waveData.get(cfg.getAnalogChannelTotalNum() + i).getWaveRawData().add(channelData);
         }
      }

      in.close();
      log.info("load waveData num:{} suc...", dc);
   }
}
