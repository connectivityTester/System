<?php

class ComponentDescription
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
                "Name" => "CHBComponent",
                "Parameters" => [],
                "Visibility" => "public"
            );
        $classDescription = array(
        	"Type" => "Class",
        	"Namespace" => [],
        	"Name" => $iScriptParams->getEntities()['component'],
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
        					$iScriptParams->getEntities()['component'] => $classDescription
        				) 
        		)
        	);
        return $generalDescription; 
	}

	private function getClassFields($iScriptParams)
	{
		$fields[] = array(
			"Name" => "m" . substr($iScriptParams->getEntities()['onoff'], 1),
			"Visibility" => "private",
			"IsStatic" => 0,
			"IsConstant" => 0,
			"Value" =>  HBSIParser::STR_TO_REPLACE,
            "Type" => $iScriptParams->getEntities()['onoff'],
			);
		$fields[] = array(
			"Name" => "m" . $iScriptParams->getEntities()['impl'],
			"Visibility" => "private",
			"IsStatic" => 0,
			"IsConstant" => 0,
			"Value" =>  HBSIParser::STR_TO_REPLACE,
            "Type" => $iScriptParams->getEntities()['impl'],
			);
		$fields[] = array(
			"Name" => "m" . substr($iScriptParams->getEntities()['logic'], 1),
			"Visibility" => "private",
			"IsStatic" => 0,
			"IsConstant" => 0,
			"Value" =>  HBSIParser::STR_TO_REPLACE,
            "Type" => $iScriptParams->getEntities()['logic'],
			);
		$fields[] = array(
			"Name" => "m" . substr($iScriptParams->getEntities()['client'], 1),
			"Visibility" => "private",
			"IsStatic" => 0,
			"IsConstant" => 0,
			"Value" =>  HBSIParser::STR_TO_REPLACE,
            "Type" => $iScriptParams->getEntities()['client'],
			);
		return $fields;
	}

	private function getAdditionalMethods($iScriptParams)
	{
		// $constructorParams = array(
		// 	"//m" . substr($iScriptParams->getEntities()['onoff'], 1) . ".setLogic ( &" . "m" . substr($iScriptParams->getEntities()['logic'], 1) . ");",  
		// 	"m" . $iScriptParams->getEntities()['impl'] . ".setLogic ( &" . "m" . substr($iScriptParams->getEntities()['logic'], 1) . ");",  
		// 	"//m" . substr($iScriptParams->getEntities()['client'], 1) . ".setLogic ( &" . "m" . substr($iScriptParams->getEntities()['logic'], 1) . ");",  
		// 	);
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
        	 "Parameters" => [],
        	 "InitList" => array(
        	 	array(
	                "Name" => "m" . substr($iScriptParams->getEntities()['onoff'], 1),
	                "Value" => "self()"
                ),
                array(
	                "Name" => "m" . $iScriptParams->getEntities()['impl'],
	                "Value" => "self()"
                ),
                 array(
	                "Name" => "m" . substr($iScriptParams->getEntities()['logic'], 1),
	                "Value" => substr("&m" . substr($iScriptParams->getEntities()['onoff'], 1) . ", &m" . $iScriptParams->getEntities()['impl'] . ", &m" . substr($iScriptParams->getEntities()['client'], 1) . ", \"ProtocolMessaging\"", 1) 
                ),
                array(
	                "Name" => "m" . substr($iScriptParams->getEntities()['client'], 1),
	                "Value" => "self(), \"ProtocolMessaging\""
                )
                ),
        	 "Content" => array(
	        	 "Text" => array(
                    "TRC_SCOPE( imp_meu_swsystems_avclan, " . $iScriptParams->getEntities()[ScriptParameters::COMPONENT] . ", all );",
                    "DBG_MSG((\"" . $iScriptParams->getEntities()[ScriptParameters::COMPONENT] . " internal version: <%s>\", AVCLAN_DESETTINGS_INTERNAL_VERSION.getBuffer() ));"
                    ),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$descrtuctor = array(
        	"Visibility" =>"public",
        	 "Name" => "",
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
	        	 "Text" => HBSIParser::STR_TO_REPLACE,
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$getOnOff = array(
        	"Visibility" =>"public",
        	 "Name" => "get" . substr($iScriptParams->getEntities()['onoff'], 1),
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 1,
        	 "IsVirtual" => 0,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => $iScriptParams->getEntities()['onoff']. "&"
        	 	),
        	 "Parameters" => [],
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array("return " . "m" . substr($iScriptParams->getEntities()['onoff'], 1) . ";"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$getIMPL = array(
        	"Visibility" =>"public",
        	 "Name" => "get" . substr($iScriptParams->getEntities()['impl'], 1, strlen($iScriptParams->getEntities()['impl']) - 5),
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 1,
        	 "IsVirtual" => 0,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => $iScriptParams->getEntities()['impl']. "&"
        	 	),
        	 "Parameters" => [],
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array ("return m" . $iScriptParams->getEntities()['impl'] . ";"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$getClient = array(
        	"Visibility" =>"public",
        	 "Name" => "get" . substr($iScriptParams->getEntities()['client'], 1),
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 1,
        	 "IsVirtual" => 0,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => $iScriptParams->getEntities()['client']. "&"
        	 	),
        	 "Parameters" => [],
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array ("return m" . substr($iScriptParams->getEntities()['client'], 1) . ";"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$getLogic = array(
        	"Visibility" =>"public",
        	 "Name" => "getLogic",
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 1,
        	 "IsVirtual" => 0,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => $iScriptParams->getEntities()['logic']. "&"
        	 	),
        	 "Parameters" => [],
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array ("return m" . substr($iScriptParams->getEntities()['logic'], 1). ";"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$getSelf = array(
        	"Visibility" =>"private",
        	 "Name" => "self",
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 1,
        	 "IsVirtual" => 0,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => $iScriptParams->getEntities()['component']. "&"
        	 	),
        	 "Parameters" => [],
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array("return *this;"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$methods[] = $constructor;
		$methods[] = $descrtuctor;
		$methods[] = $getOnOff;
		$methods[] = $getIMPL;
		$methods[] = $getClient;
		$methods[] = $getLogic;
		$methods[] = $getSelf;

		return $methods;
	}
}