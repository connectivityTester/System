#ifndef IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_CAVCLANSETTINGSPROTOCOLMESSAGINGCLIENT_HPP
#define IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_CAVCLANSETTINGSPROTOCOLMESSAGINGCLIENT_HPP

//--------------------------------------------------------------------
//                              Includes
//--------------------------------------------------------------------
#include "imp/AVCLan/ProtocolMessaging/interface/CAVCLanProtocolMessagingClientBase.hpp"


//--------------------------------------------------------------------
//                         Class Declaration
//--------------------------------------------------------------------

class CAVCLanSettingsProtocolMessagingClient : public CAVCLanProtocolMessagingClientBase {



public:

CAVCLanSettingsProtocolMessagingClient(CDataExtractorSettingsComponent & parent, char const * const implementationRoleName);
virtual ~CAVCLanSettingsProtocolMessagingClient();

protected:

virtual void informationMessageReceived(const AVCLanProtocolMessagingTypes::sAVCLanProtocolMessage &  Message);
virtual void componentConnected(const CHBProxyBase&  proxy);
virtual void componentDisconnected(const CHBProxyBase&  proxy);


private:

CDataExtractorSettingsComponent & mParent;
};


#endif // IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_AVCLanSettingsProtocolMessagingClient_HPP

