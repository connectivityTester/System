
#include "CDataExtractorSettingsLogic.hpp"
#include "api/sys/mocca/pf/trace/src/HBTrace.h"
#include "imp/AVCLan/DESettings/src/private/CDAVCLanSettingsImpl.hpp"
#include "imp/AVCLan/DESettings/src/private/CAVCLanSettingsProtocolMessagingClient.hpp"
#include "imp/AVCLan/DESettings/src/private/DESettingsCommandTypes.hpp"
#include "imp/AVCLan/common/src/private/TAVCLanSerializationCmd.hpp"
#include "imp/AVCLan/common/src/private/TAVCLanDeserializationCmd.hpp"
#include "imp/AVCLan/DESettings/src/private/Commands/CCommandSpecificLogic.hpp"


TRC_SCOPE_DEF( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
TRC_SCOPE_DEF( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );


CDataExtractorSettingsLogic::CDataExtractorSettingsLogic(CSettingsSPOnOffClientImpl * pSettingsSPOnOffClientImpl, CDAVCLanSettingsImpl * pDAVCLanSettingsImpl, CAVCLanSettingsProtocolMessagingClient * pAVCLanSettingsProtocolMessagingClient, CHBString RoleName)
   : CHBComponent()
   , CPCommandClientBase()

   , mpSPOnOffClientImpl( pSettingsSPOnOffClientImpl )
   , mpDAVCLanSettingsImpl( pDAVCLanSettingsImpl )
   , mpAVCLanSettingsProtocolMessagingClient( pAVCLanSettingsProtocolMessagingClient )
   , mRoleName( RoleName )
   , mCommandsQueue(  )
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   mCommandsQueue.unblock();
}


CDataExtractorSettingsLogic::CDataExtractorSettingsLogic(CDataExtractorSettingsLogic& mpLogic)
   : CHBComponent()
   , CPCommandClientBase()
{
}


CDataExtractorSettingsLogic::~CDataExtractorSettingsLogic()
{
   mpSPOnOffClientImpl = 0;
   mpDAVCLanSettingsImpl = 0;
}


void CDataExtractorSettingsLogic::InitiateShutdown()
{
   mpSPOnOffClientImpl->ShutdownDone( true );
}


void CDataExtractorSettingsLogic::InitiateStartup()
{
   mpOnOffClientImpl->StartupDone( true );
}


void CDataExtractorSettingsLogic::receiveNotification(const CPCommandNotificationEvent& ev)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   CPCommandPtr cmd = ev.data().getCommand();
   CommandTypes::eCommandNotificationType notificationType = ev.data().getNotificationType();
   ASSERT_MSG( "Received notification from command with 0 pointer", cmd != 0 );
   using namespace AVCLanDESettingsCommandTypes;
   switch (cmd->getCommandClassId() ) {
   case CMD_CLSID_SETTINGLINKEDNOTIFICATION_01B9_DESERIALIZATION:
   {
      handleSettingLinkedNotification_01B9DeserializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_SETTINGINFORMATIONNOTIFICATION_02A1_SERIALIZATION:
   {
      handleSettingInformationNotification_02A1SerializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_SETTINGOPERATIONNOTIFICATION_02A2_DESERIALIZATION:
   {
      handleSettingOperationNotification_02A2DeserializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_SETTINGSTATUSUPDATENOTIFICATION_02A0_SERIALIZATION:
   {
      handleSettingStatusUpdateNotification_02A0SerializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_HOMESETTINGSTATUSNOTIFICATION_0288_SERIALIZATION:
   {
      handleHomeSettingStatusNotification_0288SerializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_METERMMUNITLINKEDNOTIFICATION_03FD_DESERIALIZATION:
   {
      handleMeterMMUnitLinkedNotification_03FDDeserializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_BEEPNOTIFICATION_60_SERIALIZATION:
   {
      handleBeepNotification_60SerializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_FORCEDBEEPNOTIFICATION_64_SERIALIZATION:
   {
      handleForcedBeepNotification_64SerializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_ALLATONCEINITIALIZATIONCOMPLETIONNOTIFICATION_02BB_SERIALIZATION:
   {
      handleAllAtOnceInitializationCompletionNotification_02BBSerializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_GPSTIMEDATANOTIFICATION_029D_SERIALIZATION:
   {
      handleGPSTimeDataNotification_029DSerializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_DSTSTATUSNOTIFICATION_0298_SERIALIZATION:
   {
      handleDSTStatusNotification_0298SerializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_TZSTATUSNOTIFICATION_0299_SERIALIZATION:
   {
      handleTZStatusNotification_0299SerializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_MONITORUNITSTATUSNOTIFICATION_00F1_DESERIALIZATION:
   {
      handleMonitorUnitStatusNotification_00F1DeserializationCommandNotification ( cmd, notificationType );
      break;
   }
   case CMD_CLSID_EXTENDEDBEEPUNITNOTIFICATION_0061_DESERIALIZATION:
   {
      handleExtendedBeepUnitNotification_0061DeserializationCommandNotification ( cmd, notificationType );
      break;
   }
   default:
   {
      break;
   }
   }
}


CDataExtractorSettingsLogic& CDataExtractorSettingsLogic::operator=(CDataExtractorSettingsLogic& object)
{
}


void CDataExtractorSettingsLogic::onSettingLinkedNotification_01B9(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & iMessage)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("onSettingLinkedNotification_01B9"));
   queueSettingLinkedNotification_01B9DeserializationCommand ( iMessage );
}


void CDataExtractorSettingsLogic::onSettingOperationNotification_02A2(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & iMessage)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("onSettingOperationNotification_02A2"));
   queueSettingOperationNotification_02A2DeserializationCommand ( iMessage );
}


void CDataExtractorSettingsLogic::onMeterMMUnitLinkedNotification_03FD(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & iMessage)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("onMeterMMUnitLinkedNotification_03FD"));
   queueMeterMMUnitLinkedNotification_03FDDeserializationCommand ( iMessage );
}


void CDataExtractorSettingsLogic::onMonitorUnitStatusNotification_00F1(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & iMessage)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("onMonitorUnitStatusNotification_00F1"));
   queueMonitorUnitStatusNotification_00F1DeserializationCommand ( iMessage );
}


void CDataExtractorSettingsLogic::onExtendedBeepUnitNotification_0061(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage & iMessage)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("onExtendedBeepUnitNotification_0061"));
   queueExtendedBeepUnitNotification_0061DeserializationCommand ( iMessage );
}


void CDataExtractorSettingsLogic::requestSettingInformationNotification_02A1(const DAVCLanSettings::SettingInformationNotificationType & tSettingInformationNotification)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("requestSettingInformationNotification_02A1"));
   queueSettingInformationNotification_02A1SerializationCommand( tSettingInformationNotification_02A1 );
}


void CDataExtractorSettingsLogic::requestSettingStatusUpdateNotification_02A0(const DAVCLanSettings::SettingStatusUpdateNotificationType & eSettingStatusUpdateNotification)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("requestSettingStatusUpdateNotification_02A0"));
   queueSettingStatusUpdateNotification_02A0SerializationCommand( tSettingStatusUpdateNotification_02A0 );
}


void CDataExtractorSettingsLogic::requestHomeSettingStatusNotification_0288(const DAVCLanSettings::HomeSettingStatusNotificationType & tHomeSettingStatusNotification)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("requestHomeSettingStatusNotification_0288"));
   queueHomeSettingStatusNotification_0288SerializationCommand( tHomeSettingStatusNotification_0288 );
}


void CDataExtractorSettingsLogic::requestBeepNotification_60(const DAVCLanSettings::BeepNotificationType & tBeepNotification)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("requestBeepNotification_60"));
   queueBeepNotification_60SerializationCommand( tBeepNotification_60 );
}


void CDataExtractorSettingsLogic::requestForcedBeepNotification_64(const DAVCLanSettings::ForcedBeepNotificationType & tForcedBeepNotification)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("requestForcedBeepNotification_64"));
   queueForcedBeepNotification_64SerializationCommand( tForcedBeepNotification_64 );
}


void CDataExtractorSettingsLogic::requestAllAtOnceInitializationCompletionNotification_02BB(const DAVCLanSettings::AllAtOnceInitializationCompletionNotificationType & tAllAtOnceInitializationCompletionNotification)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("requestAllAtOnceInitializationCompletionNotification_02BB"));
   queueAllAtOnceInitializationCompletionNotification_02BBSerializationCommand( tAllAtOnceInitializationCompletionNotification_02BB );
}


void CDataExtractorSettingsLogic::requestGPSTimeDataNotification_029D(const DAVCLanSettings::GPSTimeDataNotificationType & tGPSTimeDataNotification)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("requestGPSTimeDataNotification_029D"));
   queueGPSTimeDataNotification_029DSerializationCommand( tGPSTimeDataNotification_029D );
}


void CDataExtractorSettingsLogic::requestDSTStatusNotification_0298(const DAVCLanSettings::DSTStatusNotificationType & tDSTStatusNotification)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("requestDSTStatusNotification_0298"));
   queueDSTStatusNotification_0298SerializationCommand( tDSTStatusNotification_0298 );
}


void CDataExtractorSettingsLogic::requestTZStatusNotification_0299(const DAVCLanSettings::TZStatusNotificationType & tTZStatusNotification)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   DBG_MSG(("requestTZStatusNotification_0299"));
   queueTZStatusNotification_0299SerializationCommand( tTZStatusNotification_0299 );
}


void CDataExtractorSettingsLogic::queueSettingLinkedNotification_01B9DeserializationCommand(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage& iMessage)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   CHBString fullPrefix = this->getPrefix() + CHBString( "SettingLinkedNotification_01B9:" );
   AVCLanDESettingsCommands::CSettingLinkedNotification_01B9DeserializationCommand * pCmd = AVCLanDESettingsCommandTypes::CSettingLinkedNotification_01B9DeserializationCommand::create
         ( iMessage,
         AVCLanDESettingsCommandTypes::CMD_CLSID_SETTINGLINKEDNOTIFICATION_01B9_DESERIALIZATION,
         AVCLanDESettingsCommands::unpackerSettingLinkedNotification_01B9,
         AVCLanDESettingsCommands::debugSettingLinkedNotification_01B9,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueSettingInformationNotification_02A1SerializationCommand(const DAVCLanSettings::SettingInformationNotificationType & tSettingInformationNotification)
{
   CHBString fullPrefix = this->getPrefix() + CHBString( "SettingInformationNotification_02A1:" );
   AVCLanDESettingsCommands::CSettingInformationNotification_02A1SerializationCommand * pCmd = AVCLanDESettingsCommandTypes::CSettingInformationNotification_02A1SerializationCommand::create
         ( tSettingInformationNotification_02A1,
         AVCLanDESettingsCommandTypes::CMD_CLSID_SETTINGINFORMATIONNOTIFICATION_02A1_SERIALIZATION,
         AVCLanDESettingsCommands::packerSettingInformationNotification_02A1,
         AVCLanDESettingsCommands::debugSettingInformationNotification_02A1,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueSettingOperationNotification_02A2DeserializationCommand(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage& iMessage)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   CHBString fullPrefix = this->getPrefix() + CHBString( "SettingOperationNotification_02A2:" );
   AVCLanDESettingsCommands::CSettingOperationNotification_02A2DeserializationCommand * pCmd = AVCLanDESettingsCommandTypes::CSettingOperationNotification_02A2DeserializationCommand::create
         ( iMessage,
         AVCLanDESettingsCommandTypes::CMD_CLSID_SETTINGOPERATIONNOTIFICATION_02A2_DESERIALIZATION,
         AVCLanDESettingsCommands::unpackerSettingOperationNotification_02A2,
         AVCLanDESettingsCommands::debugSettingOperationNotification_02A2,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueSettingStatusUpdateNotification_02A0SerializationCommand(const DAVCLanSettings::SettingStatusUpdateNotificationType & eSettingStatusUpdateNotification)
{
   CHBString fullPrefix = this->getPrefix() + CHBString( "SettingStatusUpdateNotification_02A0:" );
   AVCLanDESettingsCommands::CSettingStatusUpdateNotification_02A0SerializationCommand * pCmd = AVCLanDESettingsCommandTypes::CSettingStatusUpdateNotification_02A0SerializationCommand::create
         ( tSettingStatusUpdateNotification_02A0,
         AVCLanDESettingsCommandTypes::CMD_CLSID_SETTINGSTATUSUPDATENOTIFICATION_02A0_SERIALIZATION,
         AVCLanDESettingsCommands::packerSettingStatusUpdateNotification_02A0,
         AVCLanDESettingsCommands::debugSettingStatusUpdateNotification_02A0,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueHomeSettingStatusNotification_0288SerializationCommand(const DAVCLanSettings::HomeSettingStatusNotificationType & tHomeSettingStatusNotification)
{
   CHBString fullPrefix = this->getPrefix() + CHBString( "HomeSettingStatusNotification_0288:" );
   AVCLanDESettingsCommands::CHomeSettingStatusNotification_0288SerializationCommand * pCmd = AVCLanDESettingsCommandTypes::CHomeSettingStatusNotification_0288SerializationCommand::create
         ( tHomeSettingStatusNotification_0288,
         AVCLanDESettingsCommandTypes::CMD_CLSID_HOMESETTINGSTATUSNOTIFICATION_0288_SERIALIZATION,
         AVCLanDESettingsCommands::packerHomeSettingStatusNotification_0288,
         AVCLanDESettingsCommands::debugHomeSettingStatusNotification_0288,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueMeterMMUnitLinkedNotification_03FDDeserializationCommand(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage& iMessage)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   CHBString fullPrefix = this->getPrefix() + CHBString( "MeterMMUnitLinkedNotification_03FD:" );
   AVCLanDESettingsCommands::CMeterMMUnitLinkedNotification_03FDDeserializationCommand * pCmd = AVCLanDESettingsCommandTypes::CMeterMMUnitLinkedNotification_03FDDeserializationCommand::create
         ( iMessage,
         AVCLanDESettingsCommandTypes::CMD_CLSID_METERMMUNITLINKEDNOTIFICATION_03FD_DESERIALIZATION,
         AVCLanDESettingsCommands::unpackerMeterMMUnitLinkedNotification_03FD,
         AVCLanDESettingsCommands::debugMeterMMUnitLinkedNotification_03FD,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueBeepNotification_60SerializationCommand(const DAVCLanSettings::BeepNotificationType & tBeepNotification)
{
   CHBString fullPrefix = this->getPrefix() + CHBString( "BeepNotification_60:" );
   AVCLanDESettingsCommands::CBeepNotification_60SerializationCommand * pCmd = AVCLanDESettingsCommandTypes::CBeepNotification_60SerializationCommand::create
         ( tBeepNotification_60,
         AVCLanDESettingsCommandTypes::CMD_CLSID_BEEPNOTIFICATION_60_SERIALIZATION,
         AVCLanDESettingsCommands::packerBeepNotification_60,
         AVCLanDESettingsCommands::debugBeepNotification_60,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueForcedBeepNotification_64SerializationCommand(const DAVCLanSettings::ForcedBeepNotificationType & tForcedBeepNotification)
{
   CHBString fullPrefix = this->getPrefix() + CHBString( "ForcedBeepNotification_64:" );
   AVCLanDESettingsCommands::CForcedBeepNotification_64SerializationCommand * pCmd = AVCLanDESettingsCommandTypes::CForcedBeepNotification_64SerializationCommand::create
         ( tForcedBeepNotification_64,
         AVCLanDESettingsCommandTypes::CMD_CLSID_FORCEDBEEPNOTIFICATION_64_SERIALIZATION,
         AVCLanDESettingsCommands::packerForcedBeepNotification_64,
         AVCLanDESettingsCommands::debugForcedBeepNotification_64,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueAllAtOnceInitializationCompletionNotification_02BBSerializationCommand(const DAVCLanSettings::AllAtOnceInitializationCompletionNotificationType & tAllAtOnceInitializationCompletionNotification)
{
   CHBString fullPrefix = this->getPrefix() + CHBString( "AllAtOnceInitializationCompletionNotification_02BB:" );
   AVCLanDESettingsCommands::CAllAtOnceInitializationCompletionNotification_02BBSerializationCommand * pCmd = AVCLanDESettingsCommandTypes::CAllAtOnceInitializationCompletionNotification_02BBSerializationCommand::create
         ( tAllAtOnceInitializationCompletionNotification_02BB,
         AVCLanDESettingsCommandTypes::CMD_CLSID_ALLATONCEINITIALIZATIONCOMPLETIONNOTIFICATION_02BB_SERIALIZATION,
         AVCLanDESettingsCommands::packerAllAtOnceInitializationCompletionNotification_02BB,
         AVCLanDESettingsCommands::debugAllAtOnceInitializationCompletionNotification_02BB,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueGPSTimeDataNotification_029DSerializationCommand(const DAVCLanSettings::GPSTimeDataNotificationType & tGPSTimeDataNotification)
{
   CHBString fullPrefix = this->getPrefix() + CHBString( "GPSTimeDataNotification_029D:" );
   AVCLanDESettingsCommands::CGPSTimeDataNotification_029DSerializationCommand * pCmd = AVCLanDESettingsCommandTypes::CGPSTimeDataNotification_029DSerializationCommand::create
         ( tGPSTimeDataNotification_029D,
         AVCLanDESettingsCommandTypes::CMD_CLSID_GPSTIMEDATANOTIFICATION_029D_SERIALIZATION,
         AVCLanDESettingsCommands::packerGPSTimeDataNotification_029D,
         AVCLanDESettingsCommands::debugGPSTimeDataNotification_029D,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueDSTStatusNotification_0298SerializationCommand(const DAVCLanSettings::DSTStatusNotificationType & tDSTStatusNotification)
{
   CHBString fullPrefix = this->getPrefix() + CHBString( "DSTStatusNotification_0298:" );
   AVCLanDESettingsCommands::CDSTStatusNotification_0298SerializationCommand * pCmd = AVCLanDESettingsCommandTypes::CDSTStatusNotification_0298SerializationCommand::create
         ( tDSTStatusNotification_0298,
         AVCLanDESettingsCommandTypes::CMD_CLSID_DSTSTATUSNOTIFICATION_0298_SERIALIZATION,
         AVCLanDESettingsCommands::packerDSTStatusNotification_0298,
         AVCLanDESettingsCommands::debugDSTStatusNotification_0298,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueTZStatusNotification_0299SerializationCommand(const DAVCLanSettings::TZStatusNotificationType & tTZStatusNotification)
{
   CHBString fullPrefix = this->getPrefix() + CHBString( "TZStatusNotification_0299:" );
   AVCLanDESettingsCommands::CTZStatusNotification_0299SerializationCommand * pCmd = AVCLanDESettingsCommandTypes::CTZStatusNotification_0299SerializationCommand::create
         ( tTZStatusNotification_0299,
         AVCLanDESettingsCommandTypes::CMD_CLSID_TZSTATUSNOTIFICATION_0299_SERIALIZATION,
         AVCLanDESettingsCommands::packerTZStatusNotification_0299,
         AVCLanDESettingsCommands::debugTZStatusNotification_0299,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueMonitorUnitStatusNotification_00F1DeserializationCommand(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage& iMessage)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   CHBString fullPrefix = this->getPrefix() + CHBString( "MonitorUnitStatusNotification_00F1:" );
   AVCLanDESettingsCommands::CMonitorUnitStatusNotification_00F1DeserializationCommand * pCmd = AVCLanDESettingsCommandTypes::CMonitorUnitStatusNotification_00F1DeserializationCommand::create
         ( iMessage,
         AVCLanDESettingsCommandTypes::CMD_CLSID_MONITORUNITSTATUSNOTIFICATION_00F1_DESERIALIZATION,
         AVCLanDESettingsCommands::unpackerMonitorUnitStatusNotification_00F1,
         AVCLanDESettingsCommands::debugMonitorUnitStatusNotification_00F1,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::queueExtendedBeepUnitNotification_0061DeserializationCommand(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage& iMessage)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   CHBString fullPrefix = this->getPrefix() + CHBString( "ExtendedBeepUnitNotification_0061:" );
   AVCLanDESettingsCommands::CExtendedBeepUnitNotification_0061DeserializationCommand * pCmd = AVCLanDESettingsCommandTypes::CExtendedBeepUnitNotification_0061DeserializationCommand::create
         ( iMessage,
         AVCLanDESettingsCommandTypes::CMD_CLSID_EXTENDEDBEEPUNITNOTIFICATION_0061_DESERIALIZATION,
         AVCLanDESettingsCommands::unpackerExtendedBeepUnitNotification_0061,
         AVCLanDESettingsCommands::debugExtendedBeepUnitNotification_0061,
         fullPrefix );
   ASSERT_MSG( "pCmd is null", 0 != pCmd);
   subscribeOn( pCmd, CommandTypes::ON_FINISH );
   static_cast<void>(mCommandsQueue.push_back( pCmd ) );
}


void CDataExtractorSettingsLogic::handleSettingLinkedNotification_01B9DeserializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "SettingLinkedNotification_01B9Deserialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CSettingLinkedNotification_01B9DeserializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CSettingLinkedNotification_01B9DeserializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->informationSettingLinkedNotification_01B9( pCommand->getOutData() );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "SettingLinkedNotification_01B9Deserialization command finished (successfully: No). Not responding to consumer" );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "SettingLinkedNotification_01B9Deserialization command finished (was aborted). Not responding to consumer" );
         break;
      }
      default:
      {
         DBG_MSG( "SettingLinkedNotification_01B9Deserialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleSettingInformationNotification_02A1SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "SettingInformationNotification_02A1Serialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CSettingInformationNotification_02A1SerializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CSettingInformationNotification_02A1SerializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->responseSettingInformationNotification_02A1( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "SettingInformationNotification_02A1Serialization command finished (successfully: No). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseSettingInformationNotification_02A1( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "SettingInformationNotification_02A1Serialization command finished (was aborted). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseSettingInformationNotification_02A1( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      default:
      {
         DBG_MSG( "SettingInformationNotification_02A1Serialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         mpDAVCLanSettingsImpl->responseSettingInformationNotification_02A1( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleSettingOperationNotification_02A2DeserializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "SettingOperationNotification_02A2Deserialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CSettingOperationNotification_02A2DeserializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CSettingOperationNotification_02A2DeserializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->informationSettingOperationNotification_02A2( pCommand->getOutData() );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "SettingOperationNotification_02A2Deserialization command finished (successfully: No). Not responding to consumer" );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "SettingOperationNotification_02A2Deserialization command finished (was aborted). Not responding to consumer" );
         break;
      }
      default:
      {
         DBG_MSG( "SettingOperationNotification_02A2Deserialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleSettingStatusUpdateNotification_02A0SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "SettingStatusUpdateNotification_02A0Serialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CSettingStatusUpdateNotification_02A0SerializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CSettingStatusUpdateNotification_02A0SerializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->responseSettingStatusUpdateNotification_02A0( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "SettingStatusUpdateNotification_02A0Serialization command finished (successfully: No). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseSettingStatusUpdateNotification_02A0( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "SettingStatusUpdateNotification_02A0Serialization command finished (was aborted). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseSettingStatusUpdateNotification_02A0( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      default:
      {
         DBG_MSG( "SettingStatusUpdateNotification_02A0Serialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         mpDAVCLanSettingsImpl->responseSettingStatusUpdateNotification_02A0( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleHomeSettingStatusNotification_0288SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "HomeSettingStatusNotification_0288Serialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CHomeSettingStatusNotification_0288SerializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CHomeSettingStatusNotification_0288SerializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->responseHomeSettingStatusNotification_0288( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "HomeSettingStatusNotification_0288Serialization command finished (successfully: No). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseHomeSettingStatusNotification_0288( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "HomeSettingStatusNotification_0288Serialization command finished (was aborted). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseHomeSettingStatusNotification_0288( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      default:
      {
         DBG_MSG( "HomeSettingStatusNotification_0288Serialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         mpDAVCLanSettingsImpl->responseHomeSettingStatusNotification_0288( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleMeterMMUnitLinkedNotification_03FDDeserializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "MeterMMUnitLinkedNotification_03FDDeserialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CMeterMMUnitLinkedNotification_03FDDeserializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CMeterMMUnitLinkedNotification_03FDDeserializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->informationMeterMMUnitLinkedNotification_03FD( pCommand->getOutData() );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "MeterMMUnitLinkedNotification_03FDDeserialization command finished (successfully: No). Not responding to consumer" );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "MeterMMUnitLinkedNotification_03FDDeserialization command finished (was aborted). Not responding to consumer" );
         break;
      }
      default:
      {
         DBG_MSG( "MeterMMUnitLinkedNotification_03FDDeserialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleBeepNotification_60SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "BeepNotification_60Serialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CBeepNotification_60SerializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CBeepNotification_60SerializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->responseBeepNotification_60( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "BeepNotification_60Serialization command finished (successfully: No). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseBeepNotification_60( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "BeepNotification_60Serialization command finished (was aborted). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseBeepNotification_60( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      default:
      {
         DBG_MSG( "BeepNotification_60Serialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         mpDAVCLanSettingsImpl->responseBeepNotification_60( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleForcedBeepNotification_64SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "ForcedBeepNotification_64Serialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CForcedBeepNotification_64SerializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CForcedBeepNotification_64SerializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->responseForcedBeepNotification_64( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "ForcedBeepNotification_64Serialization command finished (successfully: No). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseForcedBeepNotification_64( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "ForcedBeepNotification_64Serialization command finished (was aborted). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseForcedBeepNotification_64( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      default:
      {
         DBG_MSG( "ForcedBeepNotification_64Serialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         mpDAVCLanSettingsImpl->responseForcedBeepNotification_64( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleAllAtOnceInitializationCompletionNotification_02BBSerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "AllAtOnceInitializationCompletionNotification_02BBSerialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CAllAtOnceInitializationCompletionNotification_02BBSerializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CAllAtOnceInitializationCompletionNotification_02BBSerializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->responseAllAtOnceInitializationCompletionNotification_02BB( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "AllAtOnceInitializationCompletionNotification_02BBSerialization command finished (successfully: No). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseAllAtOnceInitializationCompletionNotification_02BB( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "AllAtOnceInitializationCompletionNotification_02BBSerialization command finished (was aborted). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseAllAtOnceInitializationCompletionNotification_02BB( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      default:
      {
         DBG_MSG( "AllAtOnceInitializationCompletionNotification_02BBSerialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         mpDAVCLanSettingsImpl->responseAllAtOnceInitializationCompletionNotification_02BB( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleGPSTimeDataNotification_029DSerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "GPSTimeDataNotification_029DSerialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CGPSTimeDataNotification_029DSerializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CGPSTimeDataNotification_029DSerializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->responseGPSTimeDataNotification_029D( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "GPSTimeDataNotification_029DSerialization command finished (successfully: No). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseGPSTimeDataNotification_029D( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "GPSTimeDataNotification_029DSerialization command finished (was aborted). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseGPSTimeDataNotification_029D( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      default:
      {
         DBG_MSG( "GPSTimeDataNotification_029DSerialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         mpDAVCLanSettingsImpl->responseGPSTimeDataNotification_029D( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleDSTStatusNotification_0298SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "DSTStatusNotification_0298Serialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CDSTStatusNotification_0298SerializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CDSTStatusNotification_0298SerializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->responseDSTStatusNotification_0298( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "DSTStatusNotification_0298Serialization command finished (successfully: No). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseDSTStatusNotification_0298( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "DSTStatusNotification_0298Serialization command finished (was aborted). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseDSTStatusNotification_0298( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      default:
      {
         DBG_MSG( "DSTStatusNotification_0298Serialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         mpDAVCLanSettingsImpl->responseDSTStatusNotification_0298( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleTZStatusNotification_0299SerializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "TZStatusNotification_0299Serialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CTZStatusNotification_0299SerializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CTZStatusNotification_0299SerializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->responseTZStatusNotification_0299( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "TZStatusNotification_0299Serialization command finished (successfully: No). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseTZStatusNotification_0299( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "TZStatusNotification_0299Serialization command finished (was aborted). Not responding to consumer" );
         mpDAVCLanSettingsImpl->responseTZStatusNotification_0299( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      default:
      {
         DBG_MSG( "TZStatusNotification_0299Serialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         mpDAVCLanSettingsImpl->responseTZStatusNotification_0299( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleMonitorUnitStatusNotification_00F1DeserializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "MonitorUnitStatusNotification_00F1Deserialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CMonitorUnitStatusNotification_00F1DeserializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CMonitorUnitStatusNotification_00F1DeserializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->informationMonitorUnitStatusNotification_00F1( pCommand->getOutData() );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "MonitorUnitStatusNotification_00F1Deserialization command finished (successfully: No). Not responding to consumer" );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "MonitorUnitStatusNotification_00F1Deserialization command finished (was aborted). Not responding to consumer" );
         break;
      }
      default:
      {
         DBG_MSG( "MonitorUnitStatusNotification_00F1Deserialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         break;
      }
      }
   }
}


void CDataExtractorSettingsLogic::handleExtendedBeepUnitNotification_0061DeserializationCommandNotification(const CPCommandPtr& cmd, const CommandTypes::eCommandNotificationType& notificationType)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsLogic, all );
   ASSERT_MSG( "mpDAVCLanSettingsImpl is null", 0 != mpDAVCLanSettingsImpl );
   if (CommandTypes::ON_FINISH == notificationType) {
      CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();
      switch (cmdResult) {
      case CommandTypes::RUNRESULT_SUCCESS:
      {
         DBG_MSG( "ExtendedBeepUnitNotification_0061Deserialization command finished (successfully: Yes). Responding to consumer" );
         AVCLanDESettingsCommands::CExtendedBeepUnitNotification_0061DeserializationCommand * pCommand = static_cast<AVCLanDESettingsCommands::CExtendedBeepUnitNotification_0061DeserializationCommand * >(cmd.getPointer( ) );
         mpDAVCLanSettingsImpl->informationExtendedBeepUnitNotification_0061( pCommand->getOutData() );
         break;
      }
      case CommandTypes::RUNRESULT_ERROR:
      {
         DBG_MSG( "ExtendedBeepUnitNotification_0061Deserialization command finished (successfully: No). Not responding to consumer" );
         break;
      }
      case CommandTypes::RUNRESULT_ABORTED:
      {
         DBG_MSG( "ExtendedBeepUnitNotification_0061Deserialization command finished (was aborted). Not responding to consumer" );
         break;
      }
      default:
      {
         DBG_MSG( "ExtendedBeepUnitNotification_0061Deserialization  command finished with unexpected result %d. Not responding to consumer", cmdResult );
         break;
      }
      }
   }
}


