#ifndef IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_CSETTINGSSPONOFFCLIENTIMPL_HPP
#define IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_CSETTINGSSPONOFFCLIENTIMPL_HPP

//--------------------------------------------------------------------
//                              Includes
//--------------------------------------------------------------------
#include "api/OnOff/CSPOnOffClientStub.hpp"


//--------------------------------------------------------------------
//                         Class Declaration
//--------------------------------------------------------------------

class CSettingsSPOnOffClientImpl : public CSPOnOffClientStub {



public:

CSettingsSPOnOffClientImpl(CDataExtractorSettingsComponent & parent);
virtual ~CSettingsSPOnOffClientImpl();
void StartupDone(bool ok);
void ShutdownDone(bool ok);

protected:

virtual void requestStartup();
virtual void requestShutdown();


private:

CDataExtractorSettingsComponent & mParent;
};


#endif // IMP_MEU_SWSYSTEMS_SRC_AVCLAN_AVCLANLOGICCTRL_SRC_SettingsSPOnOffClientImpl_HPP

