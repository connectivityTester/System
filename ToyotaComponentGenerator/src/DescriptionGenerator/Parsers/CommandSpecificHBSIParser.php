<?php

class CommandSpecificHBSIParser extends HBSIParser
{
	public function getTypedefs()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Information")
			{
				$typedefs[] = "typedef ".
							   $this->mScriptParameters->getEntities()['templ_class']['deser'] . 
							   "<".
							   $this->mScriptParameters->getEntities()['commands']['namespace'] . 
							   "::CommandClassID, " .
							   $this->mScriptParameters->getHBSIFileName() . 
							   "::" . 
							   $method->Parameters[0]->Parameter->Type->__toString(). 
							   "> C" .
							   $method->Name .
							   "DeserializationCommand;"; 
			}
			if($method->Type == "Request")
			{
				$typedefs[] = "typedef ".
							   $this->mScriptParameters->getEntities()['templ_class']['ser'] . 
							   "<".
							   $this->mScriptParameters->getEntities()['commands']['namespace'] . 
							   "::CommandClassID, " .
							   $this->mScriptParameters->getHBSIFileName() . 
							   "::" . 
							   $method->Parameters[0]->Parameter->Type->__toString() . 
							   "> C" .
							   $method->Name .
							   "SerializationCommand;";
			}
		}

		return $typedefs;
	}

	public function packers()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Request")
			{
				$packersParams = null;
				$packersParams[] = array(
					"IsConstant" => 1,
					"Type" => $this->mScriptParameters->getHBSIFileName() . "::" . $method->Name . " & " ,
					"Name" => "data",
					"Value"=> self::STR_TO_REPLACE
				);
				$packersParams[] = array(
					"IsConstant" => 0,
					"Type" => $this->mScriptParameters->getEntities()['types'] . "::" . "sAVCLanProtocolMessage &" ,
					"Name" => "outMessage",
					"Value"=> self::STR_TO_REPLACE
				);
				$packersParams[] = array(
					"IsConstant" => 1,
					"Type" => "CHBString & " ,
					"Name" => "fullPrefix",
					"Value"=> self::STR_TO_REPLACE
				);
				$packers[] = array(
					"Visibility" =>"public",
		        	"Name" => "packer" . $method->Name,
		        	"IsAbstract" => 0,
	        	 	"IsStatic" => 0,
	        	 	"IsInline" => 0,
	        	 	"IsVirtual" => 0,
	        	 	"IsConstant" => 0,
	        	 	"IsConstructor" => 0,
	        	 	"IsDestructor" => 0,
		        	 "Return" => array(
		        	 		"IsConstant" => 0,
		        	 		"Type" => "AVCLanProtocolMessagingTypes::eMessagingResult"
		        	 	),
		        	 "Parameters" => $packersParams,
		        	 "InitList" => [],
		        	 "Content" => array(
			        	 "Text" => array(
			        	 	"TRC_SCOPE( imp_meu_swsystems_avclan," . $this->mScriptParameters->getEntities()[ScriptParameters::COMMAND_SPECIFIC_LOGIC]['namespace'] .", " . "packer" . $method->Name . " );",
							"DBG_MSG(( \"%s pack: DstLogAddr(%d), SrcLogAddr(%d), SrcDevAddr(%d)\"" . ", fullPrefix.getBuffer(), data.routingInfo.DestinationLogicalAddress, data.routingInfo.SourceLogicalAddress, data.routingInfo.SourceDeviceAddress ));",
			        	 	"return null;"),
			        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
			        	 "OperationCode" => self::STR_TO_REPLACE,
			        	 "Pack" => [],
			        	 "Path" => self::STR_TO_REPLACE
			        	)
		        );
			}
		}
		return $packers;
	}

	public function unpackers()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Information")
			{
				$unpackersParams = null;
				$unpackersParams[] = array(
					"IsConstant" => 1,
					"Type" => $this->mScriptParameters->getEntities()['types'] . "::sAVCLanProtocolMessage &" ,
					"Name" => "inputMessage",
					"Value"=> self::STR_TO_REPLACE
				);
				$unpackersParams[] = array(
					"IsConstant" => 1,
					"Type" => "CHBString & " ,
					"Name" => "fullPrefix",
					"Value"=> self::STR_TO_REPLACE
				);
				$unpackersParams[] = array(
					"IsConstant" => 0,
					"Type" => $this->mScriptParameters->getHBSIFileName() . "::" . $method->Parameters[0]->Parameter->Type->__toString() . " & ",
					"Name" => "outData ",
					"Value"=> self::STR_TO_REPLACE
				);
				$unpackers[] = array(
					"Visibility" =>"public",
		        	 "Name" => "unpacker" . $method->Name,
		        	 "Return" => array(
		        	 		"IsConstant" => 0,
		        	 		"Type" => "AVCLanProtocolMessagingTypes::eMessagingResult"
		        	 	),
		        	 "IsAbstract" => 0,
		        	 "IsStatic" => 0,
		        	 "IsInline" => 0,
		        	 "IsVirtual" => 0,
		        	 "IsConstant" => 0,
		        	 "IsConstructor" => 0,
		        	 "IsDestructor" => 0,
		        	 "Parameters" => $unpackersParams,
		        	 "InitList" => [],
		        	 "Content" => array(
			        	 "Text" => array(
			        	 	"TRC_SCOPE( imp_meu_swsystems_avclan," . $this->mScriptParameters->getEntities()[ScriptParameters::COMMAND_SPECIFIC_LOGIC]['namespace'] .", " . "unpacker" . $method->Name . " );",
							"DBG_MSG(( \"%s unpack()\"" . ", fullPrefix.getBuffer() ));",
			        	 	"return null;"
			        	 	),
			        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
			        	 "OperationCode" => self::STR_TO_REPLACE,
			        	 "Pack" => [],
			        	 "Path" => self::STR_TO_REPLACE
			        	)
		        );
			}
		}
		return $unpackers;
	}

	public function debugs()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Request" or $method->Type == "Information")
			{
				$debugsParams = null;
				$debugsParams[] = array(
					"IsConstant" => 1,
					"Type" => "CHBString & " ,
					"Name" => "fullPrefix",
					"Value"=> self::STR_TO_REPLACE
				);
				$debugsParams[] = array(
					"IsConstant" => 1,
					"Type" => "CHBString & " ,
					"Name" => "message",
					"Value"=> self::STR_TO_REPLACE
				);
				$debugs[] = array(
					"Visibility" =>"public",
		        	 "Name" => "debug" . $method->Name,
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
		        	 "Parameters" => $debugsParams,
		        	 "InitList" => [],
		        	 "Content" => array(
			        	 "Text" => array(
			        	 	"TRC_SCOPE( imp_meu_swsystems_avclan," . $this->mScriptParameters->getEntities()[ScriptParameters::COMMAND_SPECIFIC_LOGIC]['namespace'] .", " . "debug" . $method->Name . " );",
							"DBG_MSG((". " \"%s %s\"" .",  fullPrefix.getBuffer(), message.getBuffer() ));"
			        	 	),
			        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
			        	 "OperationCode" => self::STR_TO_REPLACE,
			        	 "Pack" => [],
			        	 "Path" => self::STR_TO_REPLACE
			        	)
		        );
			}
		}

		return $debugs;
	}

}