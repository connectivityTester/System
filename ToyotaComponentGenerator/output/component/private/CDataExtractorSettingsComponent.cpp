
#include "imp/AVCLan/DESettings/src/CDataExtractorSettingsComponent.hpp"
#include "imp/AVCLan/DESettings/src/private/InternalVersion.hpp"


TRC_SCOPE_DEF( imp_meu_swsystems_avclan, CDataExtractorSettingsComponent, all );


CDataExtractorSettingsComponent::CDataExtractorSettingsComponent()
   : CHBComponent()

   , mSettingsSPOnOffClientImpl( self() )
   , mCDAVCLanSettingsImpl( self() )
   , mDataExtractorSettingsLogic( mSettingsSPOnOffClientImpl, &mCDAVCLanSettingsImpl, &mAVCLanSettingsProtocolMessagingClient, "ProtocolMessaging" )
   , mAVCLanSettingsProtocolMessagingClient( self(), "ProtocolMessaging" )
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CDataExtractorSettingsComponent, all );
   DBG_MSG(("CDataExtractorSettingsComponent internal version: <%s>", AVCLAN_DESETTINGS_INTERNAL_VERSION.getBuffer() ));
}


CDataExtractorSettingsComponent::~CDataExtractorSettingsComponent()
{
}


inline CSettingsSPOnOffClientImpl& CDataExtractorSettingsComponent::getSettingsSPOnOffClientImpl()
{
   return mSettingsSPOnOffClientImpl;
}


inline CDAVCLanSettingsImpl& CDataExtractorSettingsComponent::getDAVCLanSettings()
{
   return mCDAVCLanSettingsImpl;
}


inline CAVCLanSettingsProtocolMessagingClient& CDataExtractorSettingsComponent::getAVCLanSettingsProtocolMessagingClient()
{
   return mAVCLanSettingsProtocolMessagingClient;
}


inline CDataExtractorSettingsLogic& CDataExtractorSettingsComponent::getLogic()
{
   return mDataExtractorSettingsLogic;
}


inline CDataExtractorSettingsComponent& CDataExtractorSettingsComponent::self()
{
   return *this;
}


