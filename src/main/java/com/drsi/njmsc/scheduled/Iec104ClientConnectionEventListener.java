package com.drsi.njmsc.scheduled;

import com.drsi.njmsc.dto.dto.ComtradeFileDto;
import com.drsi.njmsc.dto.dto.DeviceRawIecTelemtryDto;
import com.drsi.njmsc.dto.dto.FileDto;
import com.drsi.njmsc.util.ByteArrayUtil;
import com.dsri.iec104.ies.SunStation;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.openmuc.j60870.ASdu;
import org.openmuc.j60870.ASduType;
import org.openmuc.j60870.CauseOfTransmission;
import org.openmuc.j60870.ConnectionEventListener;
import org.openmuc.j60870.ie.IeQuality;
import org.openmuc.j60870.ie.IeShortFloat;
import org.openmuc.j60870.ie.IeSinglePointWithQuality;
import org.openmuc.j60870.ie.IeTime56;
import org.openmuc.j60870.ie.InformationElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Iec104ClientConnectionEventListener implements ConnectionEventListener {
   private static final Logger log = LoggerFactory.getLogger(Iec104ClientConnectionEventListener.class);
   private DeviceRawIecTelemtryDto deviceRawIecTelemtryDto;
   private AtomicReference<CountDownLatch> endOfInitializationCD;
   private Map<String, CountDownLatch> endOfInterrogationCD;
   private Map<String, SunStation> endOfInterrogationSS;
   private AtomicBoolean connectionClosedFlag;
   private List<ComtradeFileDto> comtradeFiles = new ArrayList<>();
   private List<FileDto> fileDirs = new ArrayList<>();
   private FileDto readSelectFile;
   private boolean fileDirsReadFlag = false;

   public Iec104ClientConnectionEventListener(
      DeviceRawIecTelemtryDto deviceRawIecTelemtryDto,
      Map<String, CountDownLatch> endOfInterrogationCD,
      Map<String, SunStation> endOfInterrogationSS,
      AtomicBoolean connectionClosedFlag
   ) {
      this.deviceRawIecTelemtryDto = deviceRawIecTelemtryDto;
      this.endOfInterrogationCD = endOfInterrogationCD;
      this.endOfInterrogationSS = endOfInterrogationSS;
      this.connectionClosedFlag = connectionClosedFlag;
   }

   public void newASdu(ASdu aSdu) {
      log.info("accept aSdu");

      try {
         if (aSdu.getTypeIdentification() == ASduType.M_EI_NA_1) {
            log.info("iec104 初始化结束");
         } else if (aSdu.getTypeIdentification() == ASduType.C_IC_NA_1) {
            if (aSdu.getCauseOfTransmission() == CauseOfTransmission.ACTIVATION_CON) {
            }

            if (aSdu.getCauseOfTransmission() == CauseOfTransmission.ACTIVATION_TERMINATION) {
               CountDownLatch latch = this.endOfInterrogationCD.get(String.valueOf(aSdu.getCommonAddress()));
               if (latch != null) {
                  latch.countDown();
               }
               log.info("iec104 召唤命令激活终止");
            }
         } else if (aSdu.getTypeIdentification() == ASduType.M_SP_NA_1) {
            if (aSdu.getCauseOfTransmission() == CauseOfTransmission.INTERROGATED_BY_STATION) {
               for (int bi = 0; bi < aSdu.getInformationObjects().length; bi++) {
                  int informationObjectAddress = aSdu.getInformationObjects()[bi].getInformationObjectAddress();

                  for (int i = 0; i < aSdu.getInformationObjects()[bi].getInformationElements().length; i++) {
                     InformationElement[] informationElement = aSdu.getInformationObjects()[bi].getInformationElements()[i];
                     int infoAddress = informationObjectAddress + i;
                     Integer value = 0;

                     for (int i1 = 0; i1 < informationElement.length; i1++) {
                        InformationElement informationElement1 = informationElement[i1];
                        if (informationElement1 instanceof IeSinglePointWithQuality) {
                           value = ((IeSinglePointWithQuality)informationElement1).isOn() ? 1 : 0;
                        }
                     }

                     setInfoValue(this.deviceRawIecTelemtryDto, infoAddress, value);
                  }
               }
            } else if (aSdu.getCauseOfTransmission() == CauseOfTransmission.SPONTANEOUS) {
               for (int bi = 0; bi < aSdu.getInformationObjects().length; bi++) {
                  int informationObjectAddress = aSdu.getInformationObjects()[bi].getInformationObjectAddress();

                  for (int i = 0; i < aSdu.getInformationObjects()[bi].getInformationElements().length; i++) {
                     InformationElement[] informationElement = aSdu.getInformationObjects()[bi].getInformationElements()[i];
                     int infoAddress = informationObjectAddress + i;
                     Integer value = 0;

                     for (int i1 = 0; i1 < informationElement.length; i1++) {
                        InformationElement informationElement1 = informationElement[i1];
                        if (informationElement1 instanceof IeSinglePointWithQuality) {
                           value = ((IeSinglePointWithQuality)informationElement1).isOn() ? 1 : 0;
                        }
                     }

                     setInfoValue(this.deviceRawIecTelemtryDto, infoAddress, value);
                  }
               }

               this.deviceRawIecTelemtryDto.getNjmscIec104MutationHandle().mutationHandle(aSdu, this.deviceRawIecTelemtryDto);
            }
         } else if (aSdu.getTypeIdentification() == ASduType.M_ME_NC_1) {
            if (aSdu.getCauseOfTransmission() == CauseOfTransmission.INTERROGATED_BY_STATION) {
               for (int bi = 0; bi < aSdu.getInformationObjects().length; bi++) {
                  int informationObjectAddress = aSdu.getInformationObjects()[bi].getInformationObjectAddress();

                  for (int i = 0; i < aSdu.getInformationObjects()[bi].getInformationElements().length; i++) {
                     InformationElement[] informationElement = aSdu.getInformationObjects()[bi].getInformationElements()[i];
                     int infoAddress = informationObjectAddress + i;
                     float value = 0.0F;

                     for (int i1 = 0; i1 < informationElement.length; i1++) {
                        InformationElement informationElement1 = informationElement[i1];
                        if (informationElement1 instanceof IeShortFloat) {
                           value = ((IeShortFloat)informationElement1).getValue();
                        } else if (informationElement1 instanceof IeQuality) {
                        }
                     }

                     setInfoValue(this.deviceRawIecTelemtryDto, infoAddress, value);
                  }
               }
            } else if (aSdu.getCauseOfTransmission() == CauseOfTransmission.SPONTANEOUS) {
               for (int bi = 0; bi < aSdu.getInformationObjects().length; bi++) {
                  int informationObjectAddress = aSdu.getInformationObjects()[bi].getInformationObjectAddress();

                  for (int i = 0; i < aSdu.getInformationObjects()[bi].getInformationElements().length; i++) {
                     InformationElement[] informationElement = aSdu.getInformationObjects()[bi].getInformationElements()[i];
                     int infoAddress = informationObjectAddress + i;
                     float value = 0.0F;

                     for (int i1 = 0; i1 < informationElement.length; i1++) {
                        InformationElement informationElement1 = informationElement[i1];
                        if (informationElement1 instanceof IeShortFloat) {
                           value = ((IeShortFloat)informationElement1).getValue();
                        } else if (informationElement1 instanceof IeQuality) {
                        }
                     }

                     setInfoValue(this.deviceRawIecTelemtryDto, infoAddress, value);
                  }
               }

               this.deviceRawIecTelemtryDto.getNjmscIec104MutationHandle().mutationHandle(aSdu, this.deviceRawIecTelemtryDto);
            }
         }

         if (aSdu.getTypeIdentification() == ASduType.PRIVATE_210) {
            log.info("PRIVATE_210.....");
            byte[] privateInformation = aSdu.getPrivateInformation();
            int operateFlag = ByteArrayUtil.byteArray2Int_Little_Endian(new byte[]{privateInformation[4]});
            if (operateFlag == 2) {
               log.info("PRIVATE_210 operateFlag 2.....");
               int isSuccess = ByteArrayUtil.byteArray2Int_Little_Endian(new byte[]{privateInformation[5]});
               int haveFollowing = ByteArrayUtil.byteArray2Int_Little_Endian(new byte[]{privateInformation[10]});
               int numOfFile = ByteArrayUtil.byteArray2Int_Little_Endian(new byte[]{privateInformation[11]});
               if (isSuccess == 0 && numOfFile > 0) {
                  int x = 12;
                  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

                  for (int i = 0; i < numOfFile; i++) {
                     FileDto fileDto = new FileDto();
                     int l = ByteArrayUtil.byteArray2Int_Little_Endian(new byte[]{privateInformation[x]});
                     byte[] fileNameB = new byte[l];

                     for (int i1 = 0; i1 < l; i1++) {
                        fileNameB[i1] = privateInformation[x + 1 + i1];
                     }

                     String fileName = new String(fileNameB);
                     String attribute = new String(new byte[]{privateInformation[x + l + 1]});
                     int fileSize = ByteArrayUtil.byteArray2Int_Little_Endian(
                        new byte[]{privateInformation[x + l + 2], privateInformation[x + l + 3], privateInformation[x + l + 4], privateInformation[x + l + 5]}
                     );
                     IeTime56 ieTime56 = new IeTime56(
                        new byte[]{
                           privateInformation[x + l + 6],
                           privateInformation[x + l + 7],
                           privateInformation[x + l + 8],
                           privateInformation[x + l + 9],
                           privateInformation[x + l + 10],
                           privateInformation[x + l + 11],
                           privateInformation[x + l + 12]
                        }
                     );
                     Date date = new Date(ieTime56.getTimestamp());
                     fileDto.setFileName(fileName);
                     fileDto.setAttribute(attribute);
                     fileDto.setFileSize(fileSize);
                     fileDto.setFileTimeFormat(format.format(date));
                     x = x + l + 13;
                     this.fileDirs.add(fileDto);
                  }
               }

               if (haveFollowing == 0) {
                  try {
                     TimeUnit.MILLISECONDS.sleep(500L);
                  } catch (InterruptedException e) {
                     log.error("", e);
                  }

                  log.info("PRIVATE_210 operateFlag 2 fin.....");
                  this.fileDirsReadFlag = false;
               }
            } else if (operateFlag == 4) {
               log.info("PRIVATE_210 operateFlag 4.....");
               int isSuccess = ByteArrayUtil.byteArray2Int_Little_Endian(new byte[]{privateInformation[5]});
               int l = ByteArrayUtil.byteArray2Int_Little_Endian(new byte[]{privateInformation[6]});
               byte[] fileNameB = new byte[l];

               for (int i1 = 0; i1 < l; i1++) {
                  fileNameB[i1] = privateInformation[7 + i1];
               }

               String fileName = new String(fileNameB);
               int fileId = ByteArrayUtil.byteArray2Int_Little_Endian(
                  new byte[]{privateInformation[7 + l], privateInformation[8 + l], privateInformation[9 + l], privateInformation[10 + l]}
               );
               int fileSize = ByteArrayUtil.byteArray2Int_Little_Endian(
                  new byte[]{privateInformation[11 + l], privateInformation[12 + l], privateInformation[13 + l], privateInformation[14 + l]}
               );
               log.info(
                  "operateFlag:readFile activation ack isSuccess:{} fileName:{} fileId:{} fileSize:{}", new Object[]{isSuccess, fileName, fileId, fileSize}
               );
            } else if (operateFlag == 5) {
               log.info("PRIVATE_210 operateFlag 5.....");
               int fileId = ByteArrayUtil.byteArray2Int_Little_Endian(
                  new byte[]{privateInformation[5], privateInformation[6], privateInformation[7], privateInformation[8]}
               );
               int indexOfContent = ByteArrayUtil.byteArray2Int_Little_Endian(
                  new byte[]{privateInformation[9], privateInformation[10], privateInformation[11], privateInformation[12]}
               );
               int haveFollowing = ByteArrayUtil.byteArray2Int_Little_Endian(new byte[]{privateInformation[13]});
               int checkCode = ByteArrayUtil.byteArray2Int_Little_Endian(new byte[]{privateInformation[privateInformation.length - 1]});
               if (this.readSelectFile != null && this.readSelectFile.getFileBytes() != null) {
                  System.arraycopy(privateInformation, 14, this.readSelectFile.getFileBytes(), indexOfContent, privateInformation.length - 15);
               }
               log.info(
                  "operateFlag:readFileData fileId:{} indexOfContent:{} haveFollowing:{} checkCode:{}",
                  new Object[]{fileId, indexOfContent, haveFollowing, checkCode}
               );
               if (haveFollowing == 0) {
                  try {
                     TimeUnit.MILLISECONDS.sleep(500L);
                  } catch (InterruptedException e) {
                     log.error("", e);
                  }

                  log.info("PRIVATE_210 operateFlag 5 fin.....");
                  this.readSelectFile.setFileReadFinFlag(true);
               }
            }
         }
      } catch (Exception e) {
         log.error("", e);
      }
   }

   public static void setInfoValue(DeviceRawIecTelemtryDto deviceRawIecTelemtryDto, Integer infoAddress, Object value) {
      log.info("deviceCode:{} infoAddress:{} value:{} for hcs", new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), infoAddress, value.toString()});
      if (infoAddress == 1) {
         deviceRawIecTelemtryDto.setSwitchClosedPosition((Integer)value);
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setSwitchClosedPosition((Integer)value);
      } else if (infoAddress == 2) {
         deviceRawIecTelemtryDto.setSwitchDivision((Integer)value);
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setSwitchDivision((Integer)value);
      } else if (infoAddress == 3) {
         deviceRawIecTelemtryDto.setThreePositionKnifeClosingPosition((Integer)value);
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setThreePositionKnifeClosingPosition((Integer)value);
      } else if (infoAddress == 4) {
         deviceRawIecTelemtryDto.setThreeStationKnifeClosingPosition((Integer)value);
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setThreeStationKnifeClosingPosition((Integer)value);
      } else if (infoAddress == 5) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setOpeningCoilCurrentStart((Integer)value);
      } else if (infoAddress == 6) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setStartOfClosingCoilCurrent((Integer)value);
      } else if (infoAddress == 7) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setEnergyStorageMotorCurrentStarting((Integer)value);
      } else if (infoAddress == 8) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setThreeStationOneCurrentStart((Integer)value);
      } else if (infoAddress == 9) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setThreeStationTwoCurrentStart((Integer)value);
      } else if (infoAddress == 10) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setWaveRecordingStart((Integer)value);
      } else if (infoAddress == 11) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setModuleSelfTestAbnormality((Integer)value);
      } else if (infoAddress == 12) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setAbnormalSamplingData((Integer)value);
      } else if (infoAddress == 13) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setRamSelfTestAbnormality((Integer)value);
      } else if (infoAddress == 14) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setOpenSelfTestAbnormality((Integer)value);
      } else if (infoAddress == 15) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setFixedValueSelfTestAbnormality((Integer)value);
      } else if (infoAddress == 16) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setPtDisconnection((Integer)value);
      } else if (infoAddress == 17) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setDeviceLocking((Integer)value);
      } else if (infoAddress == 18) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setGroundingAlarm((Integer)value);
      } else if (infoAddress == 19) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setCommunicationInterruption((Integer)value);
      } else if (infoAddress == 20) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setCbInterruption((Integer)value);
      } else if (infoAddress == 21) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setBInterruption((Integer)value);
      } else if (infoAddress == 22) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setEnvInterruption((Integer)value);
      } else if (infoAddress == 23) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setArresterInterruption((Integer)value);
      } else if (infoAddress == 24) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setIsolationPressureInterruption((Integer)value);
      } else if (infoAddress == 25) {
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setIsolationRemoteSignalingInterruption((Integer)value);
      } else if (infoAddress == 26) {
         deviceRawIecTelemtryDto.setSubsectionIsolationThQuarantineClosing((Integer)value);
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setSubsectionIsolationThQuarantineClosing((Integer)value);
      } else if (infoAddress == 27) {
         deviceRawIecTelemtryDto.setSubsectionIsolationThQuarantineMiddle((Integer)value);
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setSubsectionIsolationThQuarantineMiddle((Integer)value);
      } else if (infoAddress == 28) {
         deviceRawIecTelemtryDto.setSubsectionIsolationThQuarantineOpening((Integer)value);
         deviceRawIecTelemtryDto.getRawIecRemoteSignalingModel().setSubsectionIsolationThQuarantineOpening((Integer)value);
      } else if (infoAddress == 16385) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setOpeningCoilCurrent((Float)value);
      } else if (infoAddress == 16386) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setClosingCoilCurrent((Float)value);
      } else if (infoAddress == 16387) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setEnergyStorageMotorCurrent((Float)value);
      } else if (infoAddress == 16388) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setThreeStationsOneCurrent((Float)value);
      } else if (infoAddress == 16389) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setThreeStationsTwoCurrent((Float)value);
      } else if (infoAddress == 16390) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setTempOfEnv((Float)value);
      } else if (infoAddress == 16391) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setHumOfEnv((Float)value);
      } else if (infoAddress == 16392) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setDischargeCurrentMeterA((Float)value);
      } else if (infoAddress == 16393) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setDischargeCurrentMeterB((Float)value);
      } else if (infoAddress == 16394) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setDischargeCurrentMeterC((Float)value);
      } else if (infoAddress == 16395) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setLeakageCurrentMeterA((Float)value);
      } else if (infoAddress == 16396) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setLeakageCurrentMeterB((Float)value);
      } else if (infoAddress == 16397) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setLeakageCurrentMeterC((Float)value);
      } else if (infoAddress == 16398) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setTempOfCbr((Float)value);
      } else if (infoAddress == 16399) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setPressureOfCbr((Float)value);
      } else if (infoAddress == 16400) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setDensityOfCbr((Float)value);
      } else if (infoAddress == 16401) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setTempOfBr((Float)value);
      } else if (infoAddress == 16402) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setPressureOfBr((Float)value);
      } else if (infoAddress == 16403) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setDensityOfBr((Float)value);
      } else if (infoAddress == 16404) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setTempOfMui((Float)value);
      } else if (infoAddress == 16405) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setPressureOfMui((Float)value);
      } else if (infoAddress == 16406) {
         deviceRawIecTelemtryDto.getRawIecTelemetryModel().setDensityOfMui((Float)value);
      } else {
         log.warn("deviceCode:{} no infoAddress:{} value:{} for hcs", new Object[]{deviceRawIecTelemtryDto.getDeviceCode(), infoAddress, value.toString()});
      }
   }

   public void connectionClosed(IOException e) {
      this.connectionClosedFlag.set(true);
   }

   public void setReadSelectFile(FileDto readSelectFile) {
      this.readSelectFile = readSelectFile;
   }

   public List<FileDto> getFileDirs() {
      return this.fileDirs;
   }

   public boolean getFileDirsReadFlag() {
      return this.fileDirsReadFlag;
   }

   public void setFileDirsReadFlag(boolean fileDirsReadFlag) {
      this.fileDirsReadFlag = fileDirsReadFlag;
   }
}
