#ifndef IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_CDATAEXTRACTORSETTINGSLOGIC_HPP
#define IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_CDATAEXTRACTORSETTINGSLOGIC_HPP

//--------------------------------------------------------------------
//                              Includes
//--------------------------------------------------------------------
#include "api/sys/mocca/pf/comm/component/src/CHBComponent.hpp"
#include "imp/common/DebugMessages/CPDebugMessages.hpp"
#include "api/AVCLan/DAVCLanSettingsStub.hpp"
#include "imp/AVCLan/ProtocolMessaging/interface/CAVCLanProtocolMessagingClientBase.hpp"
#include "imp/common/CommandsQueue/CPCommandsQueue.hpp"


//--------------------------------------------------------------------
//                         Class Declaration
//--------------------------------------------------------------------

class CDataExtractorSettingsLogic : public CHBComponent, public CPCommandClientBase {



private:

CDataExtractorSettingsLogic(CDataExtractorSettingsLogic& mpLogic);
CDataExtractorSettingsLogic& operator=(CDataExtractorSettingsLogic& object);

public:

CDataExtractorSettingsLogic(CSettingsSPOnOffClientImpl * pSettingsSPOnOffClientImpl, CDAVCLanSettingsImpl * pDAVCLanSettingsImpl, CAVCLanSettingsProtocolMessagingClient * pAVCLanSettingsProtocolMessagingClient, CHBString RoleName);
~CDataExtractorSettingsLogic();
void InitiateShutdown();
void InitiateStartup();
virtual void receiveNotification(const CPCommandNotificationEvent& ev);
void onSettingLinkedNotification_01B9(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & iMessage);
void onSettingOperationNotification_02A2(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & iMessage);
void onMeterMMUnitLinkedNotification_03FD(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & iMessage);
void onMonitorUnitStatusNotification_00F1(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & iMessage);
void onExtendedBeepUnitNotification_0061(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & iMessage);
void requestSettingInformationNotification_02A1(const DAVCLanSettings::SettingInformationNotificationType & tSettingInformationNotification);
void requestSettingStatusUpdateNotification_02A0(const DAVCLanSettings::SettingStatusUpdateNotificationType & eSettingStatusUpdateNotification);
void requestHomeSettingStatusNotification_0288(const DAVCLanSettings::HomeSettingStatusNotificationType & tHomeSettingStatusNotification);
void requestBeepNotification_60(const DAVCLanSettings::BeepNotificationType & tBeepNotification);
void requestForcedBeepNotification_64(const DAVCLanSettings::ForcedBeepNotificationType & tForcedBeepNotification);
void requestAllAtOnceInitializationCompletionNotification_02BB(const DAVCLanSettings::AllAtOnceInitializationCompletionNotificationType & tAllAtOnceInitializationCompletionNotification);
void requestGPSTimeDataNotification_029D(const DAVCLanSettings::GPSTimeDataNotificationType & tGPSTimeDataNotification);
void requestDSTStatusNotification_0298(const DAVCLanSettings::DSTStatusNotificationType & tDSTStatusNotification);
void requestTZStatusNotification_0299(const DAVCLanSettings::TZStatusNotificationType & tTZStatusNotification);
void queueSettingLinkedNotification_01B9DeserializationCommand(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage& iMessage);
void queueSettingInformationNotification_02A1SerializationCommand(const DAVCLanSettings::SettingInformationNotificationType & tSettingInformationNotification);
void queueSettingOperationNotification_02A2DeserializationCommand(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage& iMessage);
void queueSettingStatusUpdateNotification_02A0SerializationCommand(const DAVCLanSettings::SettingStatusUpdateNotificationType & eSettingStatusUpdateNotification);
void queueHomeSettingStatusNotification_0288SerializationCommand(const DAVCLanSettings::HomeSettingStatusNotificationType & tHomeSettingStatusNotification);
void queueMeterMMUnitLinkedNotification_03FDDeserializationCommand(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage& iMessage);
void queueBeepNotification_60SerializationCommand(const DAVCLanSettings::BeepNotificationType & tBeepNotification);
void queueForcedBeepNotification_64SerializationCommand(const DAVCLanSettings::ForcedBeepNotificationType & tForcedBeepNotification);
void queueAllAtOnceInitializationCompletionNotification_02BBSerializationCommand(const DAVCLanSettings::AllAtOnceInitializationCompletionNotificationType & tAllAtOnceInitializationCompletionNotification);
void queueGPSTimeDataNotification_029DSerializationCommand(const DAVCLanSettings::GPSTimeDataNotificationType & tGPSTimeDataNotification);
void queueDSTStatusNotification_0298SerializationCommand(const DAVCLanSettings::DSTStatusNotificationType & tDSTStatusNotification);
void queueTZStatusNotification_0299SerializationCommand(const DAVCLanSettings::TZStatusNotificationType & tTZStatusNotification);
void queueMonitorUnitStatusNotification_00F1DeserializationCommand(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage& iMessage);
void queueExtendedBeepUnitNotification_0061DeserializationCommand(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage& iMessage);
void handleSettingLinkedNotification_01B9DeserializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleSettingInformationNotification_02A1SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleSettingOperationNotification_02A2DeserializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleSettingStatusUpdateNotification_02A0SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleHomeSettingStatusNotification_0288SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleMeterMMUnitLinkedNotification_03FDDeserializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleBeepNotification_60SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleForcedBeepNotification_64SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleAllAtOnceInitializationCompletionNotification_02BBSerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleGPSTimeDataNotification_029DSerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleDSTStatusNotification_0298SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleTZStatusNotification_0299SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleMonitorUnitStatusNotification_00F1DeserializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);
void handleExtendedBeepUnitNotification_0061DeserializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType);


private:

CSettingsSPOnOffClientImpl * mpSPOnOffClientImpl;
CDAVCLanSettingsImpl * mpDAVCLanSettingsImpl;
CAVCLanSettingsProtocolMessagingClient * mpAVCLanSettingsProtocolMessagingClient;
CHBString mRoleName;
CPCommandsQueue mCommandsQueue;
};


#endif // IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_DataExtractorSettingsLogic_HPP

