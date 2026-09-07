package com.drsi.njmsc.handle;

import com.alibaba.fastjson2.JSON;
import com.drsi.njmsc.dto.dto.FileDto;
import com.drsi.njmsc.scheduled.Iec104ClientConnectionEventListener;
import com.drsi.njmsc.util.ByteArrayUtil;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.openmuc.j60870.ASdu;
import org.openmuc.j60870.ASduType;
import org.openmuc.j60870.CauseOfTransmission;
import org.openmuc.j60870.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NjmscIec104ComtradeOccurHandle {
   private static final Logger log = LoggerFactory.getLogger(NjmscIec104ComtradeOccurHandle.class);
   private final int maxTryNum = 60;

   public List<FileDto> getComtradeFileDto(
      Iec104ClientConnectionEventListener iec104ClientConnectionEventListener, Connection connection, Integer commonAddress
   ) throws Exception {
      log.info(
         "iec104ClientConnectionEventListener:{},connection:{},commonAddress:{}",
         new Object[]{JSON.toJSONString(iec104ClientConnectionEventListener), connection, commonAddress}
      );
      iec104ClientConnectionEventListener.getFileDirs().clear();
      iec104ClientConnectionEventListener.setFileDirsReadFlag(true);
      this.callDir("COMTRADE", true, new Date(), new Date(), connection, commonAddress);
      int i = 0;

      while (iec104ClientConnectionEventListener.getFileDirsReadFlag()) {
         try {
            log.info("[{}] wait FileDirsReadFinish", Thread.currentThread().getName());
            if (i >= 60) {
               log.error("[{}] wait FileDirsReadFinish fail", Thread.currentThread().getName());
               return Lists.newArrayList();
            }

            i++;
            TimeUnit.SECONDS.sleep(1L);
         } catch (Exception e) {
            log.error("getComtradeFileDto", e);
         }
      }

      List<FileDto> fileDirs = iec104ClientConnectionEventListener.getFileDirs();
      log.info("fileDirs:{}", JSON.toJSONString(fileDirs).substring(0, 50));
      String s = String.valueOf(fileDirs.stream().mapToInt(f -> Integer.parseInt(f.getFileName().substring(0, 4))).max().getAsInt());

      while (s.length() < 4) {
         s = String.format("0%s", s);
      }

      String starW = s;
      List<FileDto> collect = fileDirs.stream().filter(f -> f.getFileName().startsWith(starW)).collect(Collectors.toList());
      log.info("collect:{}", JSON.toJSONString(collect).substring(0, 50));

      for (FileDto fileDto : collect) {
         fileDto.setFileBytes(new byte[fileDto.getFileSize()]);
         fileDto.setFileReadFinFlag(false);
         iec104ClientConnectionEventListener.setReadSelectFile(fileDto);
         this.readSelectFile(fileDto.getFileName(), connection, commonAddress);
         i = 0;

         while (!fileDto.getFileReadFinFlag()) {
            if (i >= 60) {
               log.error("[{}] wait FileReadFinish fail", Thread.currentThread().getName());
               return Lists.newArrayList();
            }

            i++;
            log.info("[{}] wait FileReadFinish:{}", Thread.currentThread().getName(), JSON.toJSONString(collect).substring(0, 50));
            TimeUnit.SECONDS.sleep(1L);
         }
      }

      return collect;
   }

   public void callDir(String dir, Boolean isAll, Date startTime, Date endTime, Connection connection, Integer commonAddress) throws Exception {
      byte[] privateInformation = new byte[]{
         0, 0, 0, 2, 1, 0, 0, 0, 0, 8, 67, 79, 77, 84, 82, 65, 68, 69, 0, 0, 0, 0, 0, 15, 10, 24, -120, 19, 50, 14, 15, 10, 24
      };
      ASdu aSdu = new ASdu(ASduType.PRIVATE_210, false, 0, CauseOfTransmission.REQUEST, false, false, 0, commonAddress, privateInformation);
      log.info("callDir:[{}]", JSON.toJSONString(ByteArrayUtil.byteArray2HexStringArray(privateInformation)).substring(0, 50));
      connection.send(aSdu);
   }

   public void readSelectFile(String fileName, Connection connection, Integer commonAddress) throws Exception {
      byte[] bytes = fileName.getBytes();
      byte[] privateInformation = new byte[6 + bytes.length];
      byte[] pre = new byte[]{0, 0, 0, 2, 3, ByteArrayUtil.int2ByteArray_Little_Endian(bytes.length)[0]};
      System.arraycopy(pre, 0, privateInformation, 0, pre.length);
      System.arraycopy(bytes, 0, privateInformation, 6, bytes.length);
      ASdu aSdu = new ASdu(ASduType.PRIVATE_210, false, 0, CauseOfTransmission.ACTIVATION, false, false, 0, commonAddress, privateInformation);
      connection.send(aSdu);
   }
}
