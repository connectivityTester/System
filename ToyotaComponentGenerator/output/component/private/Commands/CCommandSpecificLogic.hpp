
namespace AVCLanDESettingsCommands
{

typedef TAVCLanDeserializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::SettingLinkedNotificationType> CSettingLinkedNotification_01B9DeserializationCommand;
typedef TAVCLanSerializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::SettingInformationNotificationType> CSettingInformationNotification_02A1SerializationCommand;
typedef TAVCLanDeserializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::SettingOperationNotificationType> CSettingOperationNotification_02A2DeserializationCommand;
typedef TAVCLanSerializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::SettingStatusUpdateNotificationType> CSettingStatusUpdateNotification_02A0SerializationCommand;
typedef TAVCLanSerializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::HomeSettingStatusNotificationType> CHomeSettingStatusNotification_0288SerializationCommand;
typedef TAVCLanDeserializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::MeterMMUnitLinkedNotificationType> CMeterMMUnitLinkedNotification_03FDDeserializationCommand;
typedef TAVCLanSerializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::BeepNotificationType> CBeepNotification_60SerializationCommand;
typedef TAVCLanSerializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::ForcedBeepNotificationType> CForcedBeepNotification_64SerializationCommand;
typedef TAVCLanSerializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::AllAtOnceInitializationCompletionNotificationType> CAllAtOnceInitializationCompletionNotification_02BBSerializationCommand;
typedef TAVCLanSerializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::GPSTimeDataNotificationType> CGPSTimeDataNotification_029DSerializationCommand;
typedef TAVCLanSerializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::DSTStatusNotificationType> CDSTStatusNotification_0298SerializationCommand;
typedef TAVCLanSerializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::TZStatusNotificationType> CTZStatusNotification_0299SerializationCommand;
typedef TAVCLanDeserializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::MonitorUnitStatusNotificationType> CMonitorUnitStatusNotification_00F1DeserializationCommand;
typedef TAVCLanDeserializationCmd<AVCLanDESettingsCommandTypes::CommandClassID, DAVCLanSettings::ExtendedBeepUnitNotificationType> CExtendedBeepUnitNotification_0061DeserializationCommand;

AVCLanProtocolMessagingTypes::eMessagingResult packerSettingInformationNotification_02A1(const DAVCLanSettings::SettingInformationNotification_02A1 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix);
AVCLanProtocolMessagingTypes::eMessagingResult packerSettingStatusUpdateNotification_02A0(const DAVCLanSettings::SettingStatusUpdateNotification_02A0 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix);
AVCLanProtocolMessagingTypes::eMessagingResult packerHomeSettingStatusNotification_0288(const DAVCLanSettings::HomeSettingStatusNotification_0288 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix);
AVCLanProtocolMessagingTypes::eMessagingResult packerBeepNotification_60(const DAVCLanSettings::BeepNotification_60 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix);
AVCLanProtocolMessagingTypes::eMessagingResult packerForcedBeepNotification_64(const DAVCLanSettings::ForcedBeepNotification_64 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix);
AVCLanProtocolMessagingTypes::eMessagingResult packerAllAtOnceInitializationCompletionNotification_02BB(const DAVCLanSettings::AllAtOnceInitializationCompletionNotification_02BB &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix);
AVCLanProtocolMessagingTypes::eMessagingResult packerGPSTimeDataNotification_029D(const DAVCLanSettings::GPSTimeDataNotification_029D &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix);
AVCLanProtocolMessagingTypes::eMessagingResult packerDSTStatusNotification_0298(const DAVCLanSettings::DSTStatusNotification_0298 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix);
AVCLanProtocolMessagingTypes::eMessagingResult packerTZStatusNotification_0299(const DAVCLanSettings::TZStatusNotification_0299 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix);
AVCLanProtocolMessagingTypes::eMessagingResult unpackerSettingLinkedNotification_01B9(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & inputMessage, const CHBString &  fullPrefix, DAVCLanSettings::SettingLinkedNotificationType &  outData );
AVCLanProtocolMessagingTypes::eMessagingResult unpackerSettingOperationNotification_02A2(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & inputMessage, const CHBString &  fullPrefix, DAVCLanSettings::SettingOperationNotificationType &  outData );
AVCLanProtocolMessagingTypes::eMessagingResult unpackerMeterMMUnitLinkedNotification_03FD(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & inputMessage, const CHBString &  fullPrefix, DAVCLanSettings::MeterMMUnitLinkedNotificationType &  outData );
AVCLanProtocolMessagingTypes::eMessagingResult unpackerMonitorUnitStatusNotification_00F1(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & inputMessage, const CHBString &  fullPrefix, DAVCLanSettings::MonitorUnitStatusNotificationType &  outData );
AVCLanProtocolMessagingTypes::eMessagingResult unpackerExtendedBeepUnitNotification_0061(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & inputMessage, const CHBString &  fullPrefix, DAVCLanSettings::ExtendedBeepUnitNotificationType &  outData );
void debugSettingLinkedNotification_01B9(const CHBString &  fullPrefix, const CHBString &  message);
void debugSettingInformationNotification_02A1(const CHBString &  fullPrefix, const CHBString &  message);
void debugSettingOperationNotification_02A2(const CHBString &  fullPrefix, const CHBString &  message);
void debugSettingStatusUpdateNotification_02A0(const CHBString &  fullPrefix, const CHBString &  message);
void debugHomeSettingStatusNotification_0288(const CHBString &  fullPrefix, const CHBString &  message);
void debugMeterMMUnitLinkedNotification_03FD(const CHBString &  fullPrefix, const CHBString &  message);
void debugBeepNotification_60(const CHBString &  fullPrefix, const CHBString &  message);
void debugForcedBeepNotification_64(const CHBString &  fullPrefix, const CHBString &  message);
void debugAllAtOnceInitializationCompletionNotification_02BB(const CHBString &  fullPrefix, const CHBString &  message);
void debugGPSTimeDataNotification_029D(const CHBString &  fullPrefix, const CHBString &  message);
void debugDSTStatusNotification_0298(const CHBString &  fullPrefix, const CHBString &  message);
void debugTZStatusNotification_0299(const CHBString &  fullPrefix, const CHBString &  message);
void debugMonitorUnitStatusNotification_00F1(const CHBString &  fullPrefix, const CHBString &  message);
void debugExtendedBeepUnitNotification_0061(const CHBString &  fullPrefix, const CHBString &  message);
}

