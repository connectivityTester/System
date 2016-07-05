<?php

class IMPLDescription
{
	public static function getDescription($iHbsiParser, $iScriptParams)
	{
		$methods = array();
        foreach (self::getAdditionalMethods($iScriptParams) as $key => $method)
        {
            array_push($methods, $method);
        }
        foreach ($iHbsiParser->getInformations() as $key => $method) 
        {
           array_push($methods, $method);
        }
        foreach ($iHbsiParser->getInformations() as $key => $method) 
        {
           array_push($methods, $method);
        }
        foreach ($iHbsiParser->getRequests() as $key => $method) 
        {
           array_push($methods, $method);
        }
        foreach ($iHbsiParser->getResponses() as $key => $method) 
        {
           array_push($methods, $method);
        }
		$fields[] = self::getClassFields($iScriptParams);
        $parentsLogic[] = array(
                "Name" => substr($iScriptParams->getEntities()['impl'], 0, strlen($iScriptParams->getEntities()['impl'])-4) . "Stub",
                "Parameters" => [],
                "Visibility" => "public"
            );
        $classDescription = array(
        	"Type" => "Class",
        	"Namespace" => [],
        	"Name" => $iScriptParams->getEntities()['impl'],
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
        					$iScriptParams->getEntities()['impl'] => $classDescription
        				) 
        		)
        	);
        return $generalDescription; 
	}


	private function getClassFields($iScriptParams)
	{
        $fields = array(
				"Name" => "mpLogic",
				"Visibility" => "private",
				"IsStatic" => 0,
				"IsConstant" => 0,
				"Value" =>  HBSIParser::STR_TO_REPLACE,
                "Type" => $iScriptParams->getEntities()['logic']."*",
			);

		return $fields;
	}

	private function getAdditionalMethods($iScriptParams)
	{
        $setLogicParams[] = array(
        	"IsConstant" => 0,
        	"Type" => $iScriptParams->getEntities()['logic'] . "*",
            "Name" => "pLogic",
            "Value" => HBSIParser::STR_TO_REPLACE
        	);
        $setLogic = array(
        	"Visibility" =>"public",
        	 "Name" => "setLogic",
        	 "IsAbstract" => 0,
        	 "IsStatic" => 0,
        	 "IsInline" => 1,
        	 "IsVirtual" => 0,
        	 "IsConstant" => 0,
        	 "IsConstructor" => 0,
        	 "IsDestructor" => 0,
        	 "Return" => array(
        	 		"IsConstant" => 0,
        	 		"Type" => "void"
        	 	),
        	 "Parameters" => $setLogicParams,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => [],
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
        $condtructorParams[] = array(
        	"IsConstant" => 0,
        	"Type" => $iScriptParams->getEntities()['component'] . "&",
            "Name" => "parent",
            "Value" => HBSIParser::STR_TO_REPLACE
        	);
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
        	 "Parameters" => $condtructorParams,
        	 "InitList" => array(array(
                "Name" => "mpLogic",
                "Value" => 0
                )),
        	 "Content" => array(
	        	 "Text" => array((string)'"mpLogic = &( parent.getLogic() );"'),
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
	        	 "Text" => array((string)'"mpLogic = 0;"'),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);

        $methods[] = $setLogic;
        $methods[] = $constructor;
        $methods[] = $destructor;

        return $methods;
	}
}