
#include "imp/AVCLan/DESettings/src/private/CAVCLanSettingsProtocolMessagingClient.hpp"
#include "imp/AVCLan/DESettings/src/CDataExtractorSettingsComponent.hpp"
#include "imp/AVCLan/common/src/private/include/CommonAvcLanTypes.h"


TRC_SCOPE_DEF( imp_meu_swsystems_avclan, CAVCLanSettingsProtocolMessagingClient, all );


CAVCLanSettingsProtocolMessagingClient::CAVCLanSettingsProtocolMessagingClient(CDataExtractorSettingsComponent & parent, char const * const implementationRoleName)
   : CAVCLanProtocolMessagingClientBase(implementationRoleName)

   , mParent( parent )
{
}


CAVCLanSettingsProtocolMessagingClient::~CAVCLanSettingsProtocolMessagingClient()
{
   notifyOnInformationMessageReceived( false );
}


void CAVCLanSettingsProtocolMessagingClient::informationMessageReceived(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage &  Message)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CAVCLanSettingsProtocolMessagingClient, all );
   DBG_MSG (("informationMessageReceived. opcode=%d, src=%d, dst=%d", Message.RoutingInfo.OperationCode, Message.RoutingInfo.SourceLogicalAddress, Message.RoutingInfo.DestinationLogicalAddress));
}


void CAVCLanSettingsProtocolMessagingClient::componentConnected(const CHBProxyBase&  proxy)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CAVCLanSettingsProtocolMessagingClient, all );
   DBG_MSG(("CAVCLanSettingsProtocolMessagingClient::componentConnected()"));
   notifyOnInformationMessageReceived( true );
}


void CAVCLanSettingsProtocolMessagingClient::componentDisconnected(const CHBProxyBase&  proxy)
{
   TRC_SCOPE( imp_meu_swsystems_avclan, CAVCLanSettingsProtocolMessagingClient, all );
   DBG_MSG(("CAVCLanSettingsProtocolMessagingClient::componentDisconnected()"));
   notifyOnInformationMessageReceived( false );
}


