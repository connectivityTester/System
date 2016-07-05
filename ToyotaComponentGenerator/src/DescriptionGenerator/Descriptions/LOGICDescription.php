<?php

class LOGICDescription
{

	public static function getDescription($iHbsiParser, $iScriptParams)
	{
		$methods = array();
        foreach (self::getAdditionalMethods($iScriptParams, $iHbsiParser) as $key => $method)
        {
            array_push($methods, $method);
        }
        foreach ($iHbsiParser->getOnNotifications() as $key => $method) 
        {
        	array_push($methods, $method);
        }
        foreach ($iHbsiParser->getNotificationsRequests() as $key => $method) 
        {
        	array_push($methods, $method);
        }
        foreach ($iHbsiParser->getQueues() as $key => $method) 
        {
        	array_push($methods, $method);
        }
        foreach ($iHbsiParser->getHandles() as $key => $method) 
        {
        	array_push($methods, $method);
        }
		$fields = self::getClassFields($iScriptParams);
        $parentsLogic[] = array(
                "Name" => "CHBComponent",
                "Parameters" => [],
                "Visibility" => "public"
            );
        $parentsLogic[] = array(
                "Name" => "CPCommandClientBase",
                "Parameters" => [],
                "Visibility" => "public"
            );
        $classDescription = array(
        	"Type" => "Class",
        	"Namespace" => [],
        	"Name" => $iScriptParams->getEntities()['logic'],
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
        					$iScriptParams->getEntities()['logic'] => $classDescription
        				) 
        		)
        	);
        return $generalDescription; 
	}

	private function getAdditionalMethods($iScriptParams, $iHbsiParser)
	{
		$destructor = array(
        	"Visibility" =>"public",
        	 "Name" => HBSIParser::STR_TO_REPLACE,
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 0,
        	 "IsVirtual" => 0,
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
	        	 "Text" => array(
                    "mpSPOnOffClientImpl = 0;",
                    "mp" . substr($iScriptParams->getEntities()['impl'], 1) . "=0;"
                    ),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$constructorParams[] = array(
        	"IsConstant" => 0,
        	"Type" => $iScriptParams->getEntities()['onoff'] . "*",
            "Name" => "p". substr($iScriptParams->getEntities()['onoff'], 1),
            "Value" => HBSIParser::STR_TO_REPLACE
        	);
		$constructorParams[] = array(
        	"IsConstant" => 0,
        	"Type" => $iScriptParams->getEntities()['impl'] . "*",
            "Name" => "p". substr($iScriptParams->getEntities()['impl'], 1),
            "Value" => HBSIParser::STR_TO_REPLACE
        	);
		$constructorParams[] = array(
        	"IsConstant" => 0,
        	"Type" => $iScriptParams->getEntities()['client'] . "*",
            "Name" => "p". substr($iScriptParams->getEntities()['client'], 1),
            "Value" => HBSIParser::STR_TO_REPLACE
        	);
		$constructorParams[] = array(
        	"IsConstant" => 0,
        	"Type" => "CHBString",
            "Name" => "RoleName",
            "Value" => HBSIParser::STR_TO_REPLACE
        	);
		$publicConstructor = array(
        	"Visibility" =>"public",
        	 "Name" => HBSIParser::STR_TO_REPLACE,
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
        	 "Parameters" => $constructorParams,
        	 "InitList" => array(
                array(
                "Name" => "mpSPOnOffClientImpl",
                "Value" => "p". substr($iScriptParams->getEntities()['onoff'], 1)
                ),
                array(
                "Name" => "mp" . substr($iScriptParams->getEntities()['impl'], 1),
                "Value" => "p". substr($iScriptParams->getEntities()['impl'], 1)
                ),
                array(
                "Name" => "mp" . substr($iScriptParams->getEntities()['client'], 1),
                "Value" => "p". substr($iScriptParams->getEntities()['client'], 1)
                ),
                array(
                "Name" => "mRoleName",
                "Value" => "RoleName"
                ),
                array(
                "Name" => "mCommandsQueue",
                "Value" => HBSIParser::STR_TO_REPLACE
                )
                ),
        	 "Content" => array(
	        	 "Text" => array(
                    "TRC_SCOPE( imp_meu_swsystems_avclan, " . $iScriptParams->getEntities()[ScriptParameters::LOGIC] . ", all );",
                    "mCommandsQueue.unblock();"
                    ),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$InitiateStartup = array(
        	"Visibility" =>"public",
        	 "Name" => "InitiateStartup",
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 0,
        	 "IsVirtual" => 0,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => "void"
        	 	),
        	 "Parameters" => [],
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array("mpOnOffClientImpl->StartupDone( true );"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$InitiateShutdown = array(
        	"Visibility" =>"public",
        	 "Name" => "InitiateShutdown",
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 0,
        	 "IsVirtual" => 0,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => "void"
        	 	),
        	 "Parameters" => [],
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array("mpSPOnOffClientImpl->ShutdownDone( true );"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$recNotParams[] = array(
			"IsConstant" => 1,
        	"Type" => "CPCommandNotificationEvent&",
            "Name" => "ev",
            "Value" => HBSIParser::STR_TO_REPLACE
			);
        $receiveNotificationText = array(
            "TRC_SCOPE( imp_meu_swsystems_avclan, " . $iScriptParams->getEntities()[ScriptParameters::LOGIC] . ", all );",
            "CPCommandPtr cmd = ev.data().getCommand();",
            "CommandTypes::eCommandNotificationType notificationType = ev.data().getNotificationType();",
            "ASSERT_MSG( \"Received notification from command with 0 pointer\", cmd != 0 );",
            "using namespace ". $iScriptParams->getEntities()["commands"]["namespace"] . ";",
            "switch ( cmd->getCommandClassId() ) {",
            $iHbsiParser->getCases(),
            "default: {",
            " break; }   }",
            );
		$receiveNotification = array(
        	"Visibility" =>"public",
        	 "Name" => "receiveNotification",
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
        	 "Parameters" => $recNotParams,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => $receiveNotificationText,
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$privateConstructorParams[] = array(
        	"IsConstant" => 0,
        	"Type" => $iScriptParams->getEntities()['logic'] . "&",
            "Name" => "mpLogic",
            "Value" => HBSIParser::STR_TO_REPLACE
        	);
		$privateConstructor = array(
        	"Visibility" =>"private",
        	 "Name" => HBSIParser::STR_TO_REPLACE,
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
        	 "Parameters" => $privateConstructorParams,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => HBSIParser::STR_TO_REPLACE,
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$operatorParams[] = array(
        	"IsConstant" => 0,
        	"Type" => $iScriptParams->getEntities()['logic'] . "&",
            "Name" => "object",
            "Value" => HBSIParser::STR_TO_REPLACE
        	);
		$operator = array(
        	"Visibility" =>"private",
        	 "Name" => "operator=",
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 0,
        	 "IsVirtual" => 0,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => $iScriptParams->getEntities()['logic'] . "&"
        	 	),
        	 "Parameters" => $operatorParams,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => HBSIParser::STR_TO_REPLACE,
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$methods[] = $publicConstructor;
		$methods[] = $privateConstructor;
		$methods[] = $destructor;
		$methods[] = $InitiateShutdown;
		$methods[] = $InitiateStartup;
		$methods[] = $receiveNotification;
		$methods[] = $operator;

		return $methods;
	}

	private function getClassFields($iScriptParams)
	{
		$fields[] = array(
				"Name" => "mpSPOnOffClientImpl",
				"Visibility" => "private",
				"IsStatic" => 0,
				"IsConstant" => 0,
				"Value" =>  HBSIParser::STR_TO_REPLACE,
                "Type" => $iScriptParams->getEntities()['onoff']."*",
			);
		$fields[] = array(
				"Name" => "mp" . substr($iScriptParams->getEntities()['impl'], 1),
				"Visibility" => "private",
				"IsStatic" => 0,
				"IsConstant" => 0,
				"Value" =>  HBSIParser::STR_TO_REPLACE,
                "Type" => $iScriptParams->getEntities()['impl']."*",
			);
		$fields[] = array(
				"Name" => "mp" . substr($iScriptParams->getEntities()['client'], 1),
				"Visibility" => "private",
				"IsStatic" => 0,
				"IsConstant" => 0,
				"Value" =>  HBSIParser::STR_TO_REPLACE,
                "Type" => $iScriptParams->getEntities()['client']."*",
			);
		$fields[] = array(
				"Name" => "mRoleName",
				"Visibility" => "private",
				"IsStatic" => 0,
				"IsConstant" => 0,
				"Value" =>  HBSIParser::STR_TO_REPLACE,
                "Type" => "CHBString",
			);
		$fields[] = array(
				"Name" => "mCommandsQueue",
				"Visibility" => "private",
				"IsStatic" => 0,
				"IsConstant" => 0,
				"Value" =>  HBSIParser::STR_TO_REPLACE,
                "Type" => "CPCommandsQueue",
			);
		return $fields;
	}
}