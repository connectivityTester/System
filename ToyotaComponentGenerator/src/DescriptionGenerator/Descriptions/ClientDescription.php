<?php

class ClientDescription 
{
	public static function getDescription($iScriptParams)
	{
		$methods = array();
        foreach (self::getAdditionalMethods($iScriptParams) as $key => $method)
        {
            array_push($methods, $method);
        }
		$fields = self::getClassFields($iScriptParams);
        $parentsLogic[] = array(
                "Name" => $iScriptParams->getEntities()['clientBase'],
                "Parameters" => ["implementationRoleName"],
                "Visibility" => "public"
            );
        $classDescription = array(
        	"Type" => "Class",
        	"Namespace" => [],
        	"Name" => $iScriptParams->getEntities()['client'],
        	"Visibility" => "public",
        	"TypeParameters" => [],
        	"IsAbstract" => 0,
        	"IsConstant" => 0,
        	"Parents" => array(
        		"Interfaces" => [],
        		"Logic" => $parentsLogic
        		),
        	"Variables" => $fields,
        	"Methods" => $methods
        	);
        $generalDescription = array(
        	"Configuration_Classes" => array(
        			"Entities" => array(
        					$iScriptParams->getEntities()['client'] => $classDescription
        				) 
        		)
        	);
        return $generalDescription; 
	}

	private function getClassFields($iScriptParams)
	{
        $fields[] = array(
				"Name" => "mParent",
				"Visibility" => "private",
				"IsStatic" => 0,
				"IsConstant" => 0,
				"Value" =>  HBSIParser::STR_TO_REPLACE,
                "Type" => $iScriptParams->getEntities()['component']." &",
			);

		return $fields;
	}

	private function getAdditionalMethods($iScriptParams)
	{
		$constructor = array(
        	"Visibility" =>"public",
        	 "Name" => "",
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 0,
        	 "IsVirtual" => 0,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 1,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => HBSIParser::STR_TO_REPLACE
        	 	),
        	 "Parameters" => array(
	        	 array(
	        	 	"IsConstant" => 0,
			    	"Type" => $iScriptParams->getEntities()['component'] . " &",
			        "Name" => "parent",
			        "Value" => HBSIParser::STR_TO_REPLACE
	        	 	),
	        	 array(
	        	 	"IsConstant" => 0,
			    	"Type" => "char const* const",
			        "Name" => "implementationRoleName",
			        "Value" => HBSIParser::STR_TO_REPLACE
	        	 	)
	        	 ),
        	 "InitList" => array(
        	 	array(
        	 		"Name" => "mParent",
	                "Value" => "parent"
        	 		)
        	 	),
        	 "Content" => array(
	        	 "Text" => HBSIParser::STR_TO_REPLACE,
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$destructor = array(
        	"Visibility" =>"public",
        	 "Name" => HBSIParser::STR_TO_REPLACE,
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 0,
        	 "IsVirtual" => 1,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 1,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => HBSIParser::STR_TO_REPLACE
        	 	),
        	 "Parameters" => [],
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array("notifyOnInformationMessageReceived( false );"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$informationMessageReceived = array(
        	"Visibility" =>"protected",
        	 "Name" => "informationMessageReceived",
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 0,
        	 "IsVirtual" => 1,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => "void"
        	 	),
        	 "Parameters" => array(
        	 	array(
        	 		"IsConstant" => 1,
		        	"Type" => $iScriptParams->getEntities()['types'] . "::sAVCLanProtocolMessage & ",
		            "Name" => "Message",
		            "Value" => HBSIParser::STR_TO_REPLACE
        	 		)
        	 	),
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array(
                    "TRC_SCOPE( imp_meu_swsystems_avclan, " . $iScriptParams->getEntities()[ScriptParameters::CLIENT] . ", all );",
                    "DBG_MSG (( \"informationMessageReceived. opcode=%d, src=%d, dst=%d\"" . ", Message.RoutingInfo.OperationCode, Message.RoutingInfo.SourceLogicalAddress, Message.RoutingInfo.DestinationLogicalAddress ));"
                    ),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$componentConnected = array(
        	"Visibility" =>"protected",
        	 "Name" => "componentConnected",
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 0,
        	 "IsVirtual" => 1,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => "void"
        	 	),
        	 "Parameters" => array(
        	 	array(
        	 		"IsConstant" => 1,
		        	"Type" => "CHBProxyBase& ",
		            "Name" => "proxy",
		            "Value" => HBSIParser::STR_TO_REPLACE
        	 		)
        	 	),
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array (
                    "TRC_SCOPE( imp_meu_swsystems_avclan, " . $iScriptParams->getEntities()[ScriptParameters::CLIENT] . ", all );",
                    "DBG_MSG((\"" . $iScriptParams->getEntities()[ScriptParameters::CLIENT] . "::componentConnected()\"));",
                    "notifyOnInformationMessageReceived( true );"
                    ),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$componentDisconnected = array(
        	"Visibility" =>"protected",
        	 "Name" => "componentDisconnected",
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 0,
        	 "IsVirtual" => 1,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => "void"
        	 	),
        	 "Parameters" => array(
        	 	array(
        	 		"IsConstant" => 1,
		        	"Type" => "CHBProxyBase& ",
		            "Name" => "proxy",
		            "Value" => HBSIParser::STR_TO_REPLACE
        	 		)
        	 	),
        	 "InitList" => [],
        	 "Content" => array(
	        	"Text" => array(
                    "TRC_SCOPE( imp_meu_swsystems_avclan, " . $iScriptParams->getEntities()[ScriptParameters::CLIENT] . ", all );",
                    "DBG_MSG((\"" . $iScriptParams->getEntities()[ScriptParameters::CLIENT] . "::componentDisconnected()\"));",
                    "notifyOnInformationMessageReceived( false );"
                    ),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$methods[] = $constructor;
		$methods[] = $destructor;
		$methods[] = $informationMessageReceived;
		$methods[] = $componentConnected;
		$methods[] = $componentDisconnected;

		return $methods; 
	}
}