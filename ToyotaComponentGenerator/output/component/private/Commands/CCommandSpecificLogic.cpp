
#include "imp/AVCLan/DESettings/src/private/Commands/CCommandSpecificLogic.hpp"
#include "api/sys/mocca/pf/trace/src/HBTrace.h"
#include "imp/AVCLan/common/src/private/AVCLanDataPacker.hpp"
#include "imp/AVCLan/common/src/private/include/CommonAvcLanTypes.h"
#include "imp/AVCLan/common/src/private/AVCLanDataExtractor.hpp"



namespace AVCLanDESettingsCommands
{

TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerSettingInformationNotification_02A1 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerSettingStatusUpdateNotification_02A0 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerHomeSettingStatusNotification_0288 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerBeepNotification_60 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerForcedBeepNotification_64 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerAllAtOnceInitializationCompletionNotification_02BB );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerGPSTimeDataNotification_029D );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerDSTStatusNotification_0298 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerTZStatusNotification_0299 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, unpackerSettingLinkedNotification_01B9 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, unpackerSettingOperationNotification_02A2 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, unpackerMeterMMUnitLinkedNotification_03FD );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, unpackerMonitorUnitStatusNotification_00F1 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, unpackerExtendedBeepUnitNotification_0061 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugSettingLinkedNotification_01B9 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugSettingInformationNotification_02A1 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugSettingOperationNotification_02A2 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugSettingStatusUpdateNotification_02A0 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugHomeSettingStatusNotification_0288 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugMeterMMUnitLinkedNotification_03FD );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugBeepNotification_60 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugForcedBeepNotification_64 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugAllAtOnceInitializationCompletionNotification_02BB );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugGPSTimeDataNotification_029D );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugDSTStatusNotification_0298 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugTZStatusNotification_0299 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugMonitorUnitStatusNotification_00F1 );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugExtendedBeepUnitNotification_0061 );


AVCLanProtocolMessagingTypes::eMessagingResult packerSettingInformationNotification_02A1(const DAVCLanSettings::SettingInformationNotification_02A1 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerSettingInformationNotification_02A1 );
   DBG_MSG(("%s pack: DstLogAddr(%d), SrcLogAddr(%d), SrcDevAddr(%d)", fullPrefix.getBuffer(), data.routingInfo.DestinationLogicalAddress, data.routingInfo.SourceLogicalAddress, data.routingInfo.SourceDeviceAddress));
   return true;
}


AVCLanProtocolMessagingTypes::eMessagingResult packerSettingStatusUpdateNotification_02A0(const DAVCLanSettings::SettingStatusUpdateNotification_02A0 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerSettingStatusUpdateNotification_02A0 );
   DBG_MSG(("%s pack: DstLogAddr(%d), SrcLogAddr(%d), SrcDevAddr(%d)", fullPrefix.getBuffer(), data.routingInfo.DestinationLogicalAddress, data.routingInfo.SourceLogicalAddress, data.routingInfo.SourceDeviceAddress));
   return true;
}


AVCLanProtocolMessagingTypes::eMessagingResult packerHomeSettingStatusNotification_0288(const DAVCLanSettings::HomeSettingStatusNotification_0288 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerHomeSettingStatusNotification_0288 );
   DBG_MSG(("%s pack: DstLogAddr(%d), SrcLogAddr(%d), SrcDevAddr(%d)", fullPrefix.getBuffer(), data.routingInfo.DestinationLogicalAddress, data.routingInfo.SourceLogicalAddress, data.routingInfo.SourceDeviceAddress));
   return true;
}


AVCLanProtocolMessagingTypes::eMessagingResult packerBeepNotification_60(const DAVCLanSettings::BeepNotification_60 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerBeepNotification_60 );
   DBG_MSG(("%s pack: DstLogAddr(%d), SrcLogAddr(%d), SrcDevAddr(%d)", fullPrefix.getBuffer(), data.routingInfo.DestinationLogicalAddress, data.routingInfo.SourceLogicalAddress, data.routingInfo.SourceDeviceAddress));
   return true;
}


AVCLanProtocolMessagingTypes::eMessagingResult packerForcedBeepNotification_64(const DAVCLanSettings::ForcedBeepNotification_64 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerForcedBeepNotification_64 );
   DBG_MSG(("%s pack: DstLogAddr(%d), SrcLogAddr(%d), SrcDevAddr(%d)", fullPrefix.getBuffer(), data.routingInfo.DestinationLogicalAddress, data.routingInfo.SourceLogicalAddress, data.routingInfo.SourceDeviceAddress));
   return true;
}


AVCLanProtocolMessagingTypes::eMessagingResult packerAllAtOnceInitializationCompletionNotification_02BB(const DAVCLanSettings::AllAtOnceInitializationCompletionNotification_02BB &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerAllAtOnceInitializationCompletionNotification_02BB );
   DBG_MSG(("%s pack: DstLogAddr(%d), SrcLogAddr(%d), SrcDevAddr(%d)", fullPrefix.getBuffer(), data.routingInfo.DestinationLogicalAddress, data.routingInfo.SourceLogicalAddress, data.routingInfo.SourceDeviceAddress));
   return true;
}


AVCLanProtocolMessagingTypes::eMessagingResult packerGPSTimeDataNotification_029D(const DAVCLanSettings::GPSTimeDataNotification_029D &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerGPSTimeDataNotification_029D );
   DBG_MSG(("%s pack: DstLogAddr(%d), SrcLogAddr(%d), SrcDevAddr(%d)", fullPrefix.getBuffer(), data.routingInfo.DestinationLogicalAddress, data.routingInfo.SourceLogicalAddress, data.routingInfo.SourceDeviceAddress));
   return true;
}


AVCLanProtocolMessagingTypes::eMessagingResult packerDSTStatusNotification_0298(const DAVCLanSettings::DSTStatusNotification_0298 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerDSTStatusNotification_0298 );
   DBG_MSG(("%s pack: DstLogAddr(%d), SrcLogAddr(%d), SrcDevAddr(%d)", fullPrefix.getBuffer(), data.routingInfo.DestinationLogicalAddress, data.routingInfo.SourceLogicalAddress, data.routingInfo.SourceDeviceAddress));
   return true;
}


AVCLanProtocolMessagingTypes::eMessagingResult packerTZStatusNotification_0299(const DAVCLanSettings::TZStatusNotification_0299 &  data, AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & outMessage, const CHBString &  fullPrefix)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, packerTZStatusNotification_0299 );
   DBG_MSG(("%s pack: DstLogAddr(%d), SrcLogAddr(%d), SrcDevAddr(%d)", fullPrefix.getBuffer(), data.routingInfo.DestinationLogicalAddress, data.routingInfo.SourceLogicalAddress, data.routingInfo.SourceDeviceAddress));
   return true;
}


AVCLanProtocolMessagingTypes::eMessagingResult unpackerSettingLinkedNotification_01B9(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & inputMessage, const CHBString &  fullPrefix, DAVCLanSettings::SettingLinkedNotificationType &  outData )
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, unpackerSettingLinkedNotification_01B9 );
   DBG_MSG(("%s unpack()", fullPrefix.getBuffer() ));
   return null;
}


AVCLanProtocolMessagingTypes::eMessagingResult unpackerSettingOperationNotification_02A2(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & inputMessage, const CHBString &  fullPrefix, DAVCLanSettings::SettingOperationNotificationType &  outData )
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, unpackerSettingOperationNotification_02A2 );
   DBG_MSG(("%s unpack()", fullPrefix.getBuffer() ));
   return null;
}


AVCLanProtocolMessagingTypes::eMessagingResult unpackerMeterMMUnitLinkedNotification_03FD(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & inputMessage, const CHBString &  fullPrefix, DAVCLanSettings::MeterMMUnitLinkedNotificationType &  outData )
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, unpackerMeterMMUnitLinkedNotification_03FD );
   DBG_MSG(("%s unpack()", fullPrefix.getBuffer() ));
   return null;
}


AVCLanProtocolMessagingTypes::eMessagingResult unpackerMonitorUnitStatusNotification_00F1(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & inputMessage, const CHBString &  fullPrefix, DAVCLanSettings::MonitorUnitStatusNotificationType &  outData )
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, unpackerMonitorUnitStatusNotification_00F1 );
   DBG_MSG(("%s unpack()", fullPrefix.getBuffer() ));
   return null;
}


AVCLanProtocolMessagingTypes::eMessagingResult unpackerExtendedBeepUnitNotification_0061(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & inputMessage, const CHBString &  fullPrefix, DAVCLanSettings::ExtendedBeepUnitNotificationType &  outData )
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, unpackerExtendedBeepUnitNotification_0061 );
   DBG_MSG(("%s unpack()", fullPrefix.getBuffer() ));
   return null;
}


void debugSettingLinkedNotification_01B9(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugSettingLinkedNotification_01B9 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugSettingInformationNotification_02A1(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugSettingInformationNotification_02A1 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugSettingOperationNotification_02A2(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugSettingOperationNotification_02A2 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugSettingStatusUpdateNotification_02A0(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugSettingStatusUpdateNotification_02A0 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugHomeSettingStatusNotification_0288(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugHomeSettingStatusNotification_0288 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugMeterMMUnitLinkedNotification_03FD(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugMeterMMUnitLinkedNotification_03FD );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugBeepNotification_60(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugBeepNotification_60 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugForcedBeepNotification_64(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugForcedBeepNotification_64 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugAllAtOnceInitializationCompletionNotification_02BB(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugAllAtOnceInitializationCompletionNotification_02BB );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugGPSTimeDataNotification_029D(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugGPSTimeDataNotification_029D );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugDSTStatusNotification_0298(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugDSTStatusNotification_0298 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugTZStatusNotification_0299(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugTZStatusNotification_0299 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugMonitorUnitStatusNotification_00F1(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugMonitorUnitStatusNotification_00F1 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}


void debugExtendedBeepUnitNotification_0061(const CHBString &  fullPrefix, const CHBString &  message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, AVCLanDESettingsCommands, debugExtendedBeepUnitNotification_0061 );
   DBG_MSG(("%s %s", fullPrefix.getBuffer(), message.getBuffer() ));
}
}

