#ifndef IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_CDAVCLANSETTINGSIMPL_HPP
#define IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_CDAVCLANSETTINGSIMPL_HPP

//--------------------------------------------------------------------
//                              Includes
//--------------------------------------------------------------------
#include "api/AVCLan/CCommandSpecificLogic.hpp"


//--------------------------------------------------------------------
//                         Class Declaration
//--------------------------------------------------------------------

class CDAVCLanSettingsImpl : public CDAVCLanSettingsStub {



public:

void setLogic(CDataExtractorSettingsLogic * pLogic);
CDAVCLanSettingsImpl(CDataExtractorSettingsComponent& parent);
~CDAVCLanSettingsImpl();
virtual void informationSettingLinkedNotification_01B9(const SettingLinkedNotificationType tSettingLinkedNotification);
virtual void informationSettingOperationNotification_02A2(const SettingOperationNotificationType tSettingsOperationNotification);
virtual void informationMeterMMUnitLinkedNotification_03FD(const MeterMMUnitLinkedNotificationType tMeterMMUnitLinkedNotification);
virtual void informationMonitorUnitStatusNotification_00F1(const MonitorUnitStatusNotificationType tMonitorUnitStatusNotification);
virtual void informationExtendedBeepUnitNotification_0061(const ExtendedBeepUnitNotificationType tExtendedBeepUnitNotification);
virtual void informationSettingLinkedNotification_01B9(const SettingLinkedNotificationType tSettingLinkedNotification);
virtual void informationSettingOperationNotification_02A2(const SettingOperationNotificationType tSettingsOperationNotification);
virtual void informationMeterMMUnitLinkedNotification_03FD(const MeterMMUnitLinkedNotificationType tMeterMMUnitLinkedNotification);
virtual void informationMonitorUnitStatusNotification_00F1(const MonitorUnitStatusNotificationType tMonitorUnitStatusNotification);
virtual void informationExtendedBeepUnitNotification_0061(const ExtendedBeepUnitNotificationType tExtendedBeepUnitNotification);
virtual void requestSettingInformationNotification_02A1(const DAVCLanSettings::SettingInformationNotificationType tSettingInformationNotification);
virtual void requestSettingStatusUpdateNotification_02A0(const DAVCLanSettings::SettingStatusUpdateNotificationType eSettingStatusUpdateNotification);
virtual void requestHomeSettingStatusNotification_0288(const DAVCLanSettings::HomeSettingStatusNotificationType tHomeSettingStatusNotification);
virtual void requestBeepNotification_60(const DAVCLanSettings::BeepNotificationType tBeepNotification);
virtual void requestForcedBeepNotification_64(const DAVCLanSettings::ForcedBeepNotificationType tForcedBeepNotification);
virtual void requestAllAtOnceInitializationCompletionNotification_02BB(const DAVCLanSettings::AllAtOnceInitializationCompletionNotificationType tAllAtOnceInitializationCompletionNotification);
virtual void requestGPSTimeDataNotification_029D(const DAVCLanSettings::GPSTimeDataNotificationType tGPSTimeDataNotification);
virtual void requestDSTStatusNotification_0298(const DAVCLanSettings::DSTStatusNotificationType tDSTStatusNotification);
virtual void requestTZStatusNotification_0299(const DAVCLanSettings::TZStatusNotificationType tTZStatusNotification);
virtual void responseSettingInformationNotification_02A1(const bool bResult);
virtual void responseSettingStatusUpdateNotification_02A0(const bool bResult);
virtual void responseHomeSettingStatusNotification_0288(const bool bResult);
virtual void responseBeepNotification_60(const bool bResult);
virtual void responseForcedBeepNotification_64(const bool bResult);
virtual void responseAllAtOnceInitializationCompletionNotification_02BB(const bool bResult);
virtual void responseGPSTimeDataNotification_029D(const bool bResult);
virtual void responseDSTStatusNotification_0298(const bool bResult);
virtual void responseTZStatusNotification_0299(const bool bResult);


private:

CDataExtractorSettingsLogic * mpLogic;
};


#endif // IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_DAVCLanSettingsImpl_HPP

