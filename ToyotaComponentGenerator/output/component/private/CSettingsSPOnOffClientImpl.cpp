
#include "imp/AVCLan/DESettings/src/private/CDAVCLanSettingsImpl.hpp"
#include "imp/AVCLan/DESettings/src/CDataExtractorSettingsComponent.hpp"




CSettingsSPOnOffClientImpl::CSettingsSPOnOffClientImpl(CDataExtractorSettingsComponent & parent)
   : CSPOnOffClientStub()

   , mParent( parent )
{
}


CSettingsSPOnOffClientImpl::~CSettingsSPOnOffClientImpl()
{
}


void CSettingsSPOnOffClientImpl::StartupDone(bool ok)
{
   responseStartup( ok );
}


void CSettingsSPOnOffClientImpl::ShutdownDone(bool ok)
{
   responseShutdown( ok );
}


void CSettingsSPOnOffClientImpl::requestStartup()
{
   mParent.getLogic().InitiateStartup();
}


void CSettingsSPOnOffClientImpl::requestShutdown()
{
   mParent.getLogic().InitiateShutdown();
}


