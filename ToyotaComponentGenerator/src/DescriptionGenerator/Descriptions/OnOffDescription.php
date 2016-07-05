<?php

class OnOffDescription
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
                "Name" => $iScriptParams->getEntities()['onoffStub'],
                "Parameters" => [],
                "Visibility" => "public"
            );
        $classDescription = array(
        	"Type" => "Class",
        	"Namespace" => [],
        	"Name" => $iScriptParams->getEntities()['onoff'],
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
        					$iScriptParams->getEntities()['onoff'] => $classDescription
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
            "Type" => $iScriptParams->getEntities()['component'] . " &",
			);
		return $fields;
	}

	private function getAdditionalMethods($iScriptParams)
	{
		$constructorParams = array(
        	"IsConstant" => 0,
        	"Type" => $iScriptParams->getEntities()['component'] . " &",
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
        	 "Parameters" => array(
        	 	array(
        	 		"IsConstant" => 0,
		        	"Type" => $iScriptParams->getEntities()['component'] . " &",
		            "Name" => "parent",
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
		$startUpDone = array(
        	"Visibility" =>"public",
        	 "Name" => "StartupDone",
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
        	 "Parameters" => array(array(
        	 	"IsConstant" => 0,
	        	"Type" => "bool",
	            "Name" => "ok",
	            "Value" => HBSIParser::STR_TO_REPLACE
        	 	)),
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" =>  array("responseStartup( ok );"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$shutDownDone = array(
        	"Visibility" =>"public",
        	 "Name" => "ShutdownDone",
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
        	 "Parameters" => array(array(
        	 	"IsConstant" => 0,
	        	"Type" => "bool",
	            "Name" => "ok",
	            "Value" => HBSIParser::STR_TO_REPLACE
        	 	)),
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" =>  array(" responseShutdown( ok );"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$reqStart = array(
        	"Visibility" =>"protected",
        	 "Name" => "requestStartup",
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
        	 "Parameters" =>[],
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" =>  array("mParent.getLogic().InitiateStartup();"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);
		$reqShutDown = array(
        	"Visibility" =>"protected",
        	 "Name" => "requestShutdown",
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
        	 "Parameters" =>[],
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" =>  array("mParent.getLogic().InitiateShutdown();"),
	        	 "SourseRoutingInfo" => HBSIParser::STR_TO_REPLACE,
	        	 "OperationCode" => HBSIParser::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => HBSIParser::STR_TO_REPLACE
	        	)        	
        	);

		$methods[] = $constructor;
		$methods[] = $descrtuctor;
		$methods[] = $startUpDone;
		$methods[] = $shutDownDone;
		$methods[] = $reqStart;
		$methods[] = $reqShutDown;

		return $methods;
	}
}