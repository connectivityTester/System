
#include "imp/AVCLan/DESettings/src/private/CDAVCLanSettingsImpl.hpp"
#include "imp/AVCLan/DESettings/src/CDataExtractorSettingsComponent.hpp"
#include "imp/AVCLan/DESettings/src/private/CDataExtractorSettingsLogic.hpp"




inline void CDAVCLanSettingsImpl::setLogic(CDataExtractorSettingsLogic * pLogic)
{
}


CDAVCLanSettingsImpl::CDAVCLanSettingsImpl(CDataExtractorSettingsComponent& parent)
   : CDAVCLanSettingsStub()

   , mpLogic( 0 )
{
   mpLogic = &(parent.getLogic() );
}


CDAVCLanSettingsImpl::~CDAVCLanSettingsImpl()
{
   mpLogic = 0;
}


void CDAVCLanSettingsImpl::informationSettingLinkedNotification_01B9(const SettingLinkedNotificationType tSettingLinkedNotification)
{
   CDAVCLanSettingsStub::informationSettingLinkedNotification_01B9( tSettingLinkedNotification );
}


void CDAVCLanSettingsImpl::informationSettingOperationNotification_02A2(const SettingOperationNotificationType tSettingsOperationNotification)
{
   CDAVCLanSettingsStub::informationSettingOperationNotification_02A2( tSettingsOperationNotification );
}


void CDAVCLanSettingsImpl::informationMeterMMUnitLinkedNotification_03FD(const MeterMMUnitLinkedNotificationType tMeterMMUnitLinkedNotification)
{
   CDAVCLanSettingsStub::informationMeterMMUnitLinkedNotification_03FD( tMeterMMUnitLinkedNotification );
}


void CDAVCLanSettingsImpl::informationMonitorUnitStatusNotification_00F1(const MonitorUnitStatusNotificationType tMonitorUnitStatusNotification)
{
   CDAVCLanSettingsStub::informationMonitorUnitStatusNotification_00F1( tMonitorUnitStatusNotification );
}


void CDAVCLanSettingsImpl::informationExtendedBeepUnitNotification_0061(const ExtendedBeepUnitNotificationType tExtendedBeepUnitNotification)
{
   CDAVCLanSettingsStub::informationExtendedBeepUnitNotification_0061( tExtendedBeepUnitNotification );
}


void CDAVCLanSettingsImpl::informationSettingLinkedNotification_01B9(const SettingLinkedNotificationType tSettingLinkedNotification)
{
   CDAVCLanSettingsStub::informationSettingLinkedNotification_01B9( tSettingLinkedNotification );
}


void CDAVCLanSettingsImpl::informationSettingOperationNotification_02A2(const SettingOperationNotificationType tSettingsOperationNotification)
{
   CDAVCLanSettingsStub::informationSettingOperationNotification_02A2( tSettingsOperationNotification );
}


void CDAVCLanSettingsImpl::informationMeterMMUnitLinkedNotification_03FD(const MeterMMUnitLinkedNotificationType tMeterMMUnitLinkedNotification)
{
   CDAVCLanSettingsStub::informationMeterMMUnitLinkedNotification_03FD( tMeterMMUnitLinkedNotification );
}


void CDAVCLanSettingsImpl::informationMonitorUnitStatusNotification_00F1(const MonitorUnitStatusNotificationType tMonitorUnitStatusNotification)
{
   CDAVCLanSettingsStub::informationMonitorUnitStatusNotification_00F1( tMonitorUnitStatusNotification );
}


void CDAVCLanSettingsImpl::informationExtendedBeepUnitNotification_0061(const ExtendedBeepUnitNotificationType tExtendedBeepUnitNotification)
{
   CDAVCLanSettingsStub::informationExtendedBeepUnitNotification_0061( tExtendedBeepUnitNotification );
}


void CDAVCLanSettingsImpl::requestSettingInformationNotification_02A1(const DAVCLanSettings::SettingInformationNotificationType tSettingInformationNotification)
{
   ASSERTMSG ( "mpLogic is 0", 0 != mpLogic );
   mpLogic->requestSettingInformationNotification_02A1( tSettingInformationNotification );
}


void CDAVCLanSettingsImpl::requestSettingStatusUpdateNotification_02A0(const DAVCLanSettings::SettingStatusUpdateNotificationType eSettingStatusUpdateNotification)
{
   ASSERTMSG ( "mpLogic is 0", 0 != mpLogic );
   mpLogic->requestSettingStatusUpdateNotification_02A0( eSettingStatusUpdateNotification );
}


void CDAVCLanSettingsImpl::requestHomeSettingStatusNotification_0288(const DAVCLanSettings::HomeSettingStatusNotificationType tHomeSettingStatusNotification)
{
   ASSERTMSG ( "mpLogic is 0", 0 != mpLogic );
   mpLogic->requestHomeSettingStatusNotification_0288( tHomeSettingStatusNotification );
}


void CDAVCLanSettingsImpl::requestBeepNotification_60(const DAVCLanSettings::BeepNotificationType tBeepNotification)
{
   ASSERTMSG ( "mpLogic is 0", 0 != mpLogic );
   mpLogic->requestBeepNotification_60( tBeepNotification );
}


void CDAVCLanSettingsImpl::requestForcedBeepNotification_64(const DAVCLanSettings::ForcedBeepNotificationType tForcedBeepNotification)
{
   ASSERTMSG ( "mpLogic is 0", 0 != mpLogic );
   mpLogic->requestForcedBeepNotification_64( tForcedBeepNotification );
}


void CDAVCLanSettingsImpl::requestAllAtOnceInitializationCompletionNotification_02BB(const DAVCLanSettings::AllAtOnceInitializationCompletionNotificationType tAllAtOnceInitializationCompletionNotification)
{
   ASSERTMSG ( "mpLogic is 0", 0 != mpLogic );
   mpLogic->requestAllAtOnceInitializationCompletionNotification_02BB( tAllAtOnceInitializationCompletionNotification );
}


void CDAVCLanSettingsImpl::requestGPSTimeDataNotification_029D(const DAVCLanSettings::GPSTimeDataNotificationType tGPSTimeDataNotification)
{
   ASSERTMSG ( "mpLogic is 0", 0 != mpLogic );
   mpLogic->requestGPSTimeDataNotification_029D( tGPSTimeDataNotification );
}


void CDAVCLanSettingsImpl::requestDSTStatusNotification_0298(const DAVCLanSettings::DSTStatusNotificationType tDSTStatusNotification)
{
   ASSERTMSG ( "mpLogic is 0", 0 != mpLogic );
   mpLogic->requestDSTStatusNotification_0298( tDSTStatusNotification );
}


void CDAVCLanSettingsImpl::requestTZStatusNotification_0299(const DAVCLanSettings::TZStatusNotificationType tTZStatusNotification)
{
   ASSERTMSG ( "mpLogic is 0", 0 != mpLogic );
   mpLogic->requestTZStatusNotification_0299( tTZStatusNotification );
}


void CDAVCLanSettingsImpl::responseSettingInformationNotification_02A1(const bool bResult)
{
   CDAVCLanSettingsStub::responseSettingInformationNotification_02A1( bResult );
}


void CDAVCLanSettingsImpl::responseSettingStatusUpdateNotification_02A0(const bool bResult)
{
   CDAVCLanSettingsStub::responseSettingStatusUpdateNotification_02A0( bResult );
}


void CDAVCLanSettingsImpl::responseHomeSettingStatusNotification_0288(const bool bResult)
{
   CDAVCLanSettingsStub::responseHomeSettingStatusNotification_0288( bResult );
}


void CDAVCLanSettingsImpl::responseBeepNotification_60(const bool bResult)
{
   CDAVCLanSettingsStub::responseBeepNotification_60( bResult );
}


void CDAVCLanSettingsImpl::responseForcedBeepNotification_64(const bool bResult)
{
   CDAVCLanSettingsStub::responseForcedBeepNotification_64( bResult );
}


void CDAVCLanSettingsImpl::responseAllAtOnceInitializationCompletionNotification_02BB(const bool bResult)
{
   CDAVCLanSettingsStub::responseAllAtOnceInitializationCompletionNotification_02BB( bResult );
}


void CDAVCLanSettingsImpl::responseGPSTimeDataNotification_029D(const bool bResult)
{
   CDAVCLanSettingsStub::responseGPSTimeDataNotification_029D( bResult );
}


void CDAVCLanSettingsImpl::responseDSTStatusNotification_0298(const bool bResult)
{
   CDAVCLanSettingsStub::responseDSTStatusNotification_0298( bResult );
}


void CDAVCLanSettingsImpl::responseTZStatusNotification_0299(const bool bResult)
{
   CDAVCLanSettingsStub::responseTZStatusNotification_0299( bResult );
}


