#ifndef IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_CDATAEXTRACTORSETTINGSCOMPONENT_HPP
#define IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_CDATAEXTRACTORSETTINGSCOMPONENT_HPP

//--------------------------------------------------------------------
//                              Includes
//--------------------------------------------------------------------
#include "api/sys/mocca/pf/comm/component/src/CHBComponent.hpp"
#include "private/CDAVCLanSettingsImpl.hpp"
#include "private/CDataExtractorSettingsLogic.hpp"
#include "private/CAVCLanSettingsProtocolMessagingClient.hpp"


//--------------------------------------------------------------------
//                         Class Declaration
//--------------------------------------------------------------------

class CDataExtractorSettingsComponent : public CHBComponent {



private:

CDataExtractorSettingsComponent& self();

public:

CDataExtractorSettingsComponent();
virtual ~CDataExtractorSettingsComponent();
CSettingsSPOnOffClientImpl& getSettingsSPOnOffClientImpl();
CDAVCLanSettingsImpl& getDAVCLanSettings();
CAVCLanSettingsProtocolMessagingClient& getAVCLanSettingsProtocolMessagingClient();
CDataExtractorSettingsLogic& getLogic();


private:

CSettingsSPOnOffClientImpl mSettingsSPOnOffClientImpl;
CDAVCLanSettingsImpl mCDAVCLanSettingsImpl;
CDataExtractorSettingsLogic mDataExtractorSettingsLogic;
CAVCLanSettingsProtocolMessagingClient mAVCLanSettingsProtocolMessagingClient;
};


#endif // IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_DataExtractorSettingsComponent_HPP

