<?php

class MappingGenerator
{
	const HEADER_FILE = "Header";
	const SOURCE_FILE = "SOURCE";

	private $mScriptPatameters;

	public function __construct($iScriptsParameters)
	{
		$this->mScriptPatameters = $iScriptsParameters;
	}

	public function getIMPLMappings($name, $headerPathPrefix, $sourcePathPrefix, $isNeedSorceFile, $iMappingType)
	{
			/*
			Configuration_Mapping:     
			   Mappings:
			      Generic:
			         EntityTypes:
			            Class: cpp\Class.twig
			            Interface: cpp\Class.twig
			            Variable: cpp\Variable.twig
			         OutputTypes:
			            Source: cpp\Source.twig
			            Header: cpp\Header.twig
		          Output:
		            - Path: .
		              Name: CDAVCLanSettingsImpl.hpp
		              Type: Header
		              Entities: [ CDAVCLanSettingsImpl ]
		              Imports: []
		            - Path: .
		              Name: CDAVCLanSettingsImpl.cpp
		              Type: Header
		              Entities: [CDAVCLanSettingsImpl]
		              Imports: []
			*/
      	$genEntityTypes = array(
      		"Class" => "cpp\Class.twig",
      		"Interface" => "cpp\Class.twig",
      		"Variable" => "cpp\Variable.twig",
      		"Enum" => "cpp\Enum.twig",
      		"TemplateMethods" => "cpp\TemplateMethods.twig"
      		); 
      	$genOutputTypes = array(
      		"Source" => "cpp\Source.twig",
      		"Header" => "cpp\Header.twig"
      		);

      	$generic = array(
      		"EntityTypes" => $genEntityTypes,
      		"OutputTypes" => $genOutputTypes
      		);
		$genOutput[] = array(
			"Path" => ".". $headerPathPrefix,
            "Name" => $name . ".hpp",
            "Type" => "Header",
            "Entities" => "[ " . $name . " ]",
            "Imports" => self::getImports($iMappingType, self::HEADER_FILE)
			);
		if($isNeedSorceFile == true)
		{
			$genOutput[] = array(
				"Path" => "." . $sourcePathPrefix,
	            "Name" => $name . ".cpp",
	            "Type" => "Source",
	            "Entities" => '[' . $name . ']',
	            "Imports" => self::getImports($iMappingType, self::SOURCE_FILE)
				);
		}
		$mappings = array(
			"Generic" => $generic,
			"Output" => $genOutput
			);
		$config_mapp = array(
			"Mappings" => $mappings
			);
		$root = array(
			"Configuration_Mapping" => $config_mapp
			);	

		$description = yaml_emit($root);
		$description = substr($description, 4, strlen($description) - 9);
		$description = str_replace("'[", "[", $description);
		$description = str_replace("]'", "]", $description);
		return $description;
	}

	private function getImports ($iMappingType, $iFileType)
	{
		$imports = null;
		switch ($iFileType) {
			case self::HEADER_FILE :
				$imports = self::getHeaderImports($iMappingType);
				break;
			case self::SOURCE_FILE :
				$imports = self::getSourceImports($iMappingType);
				break;
		}
		return $imports;
	}

	private function getHeaderImports($iMappingType)
	{
		$imports = null;
		switch ($iMappingType) {
			case ScriptParameters::CLIENT 		: 
				$imports = array(
					"imp/AVCLan/ProtocolMessaging/interface/" . $this->mScriptPatameters->getEntities()[ScriptParameters::CLIENTBASE] . ".hpp"
					);
				break;	
			case ScriptParameters::LOGIC 		: 
				$imports = array(
					"api/sys/mocca/pf/comm/component/src/CHBComponent.hpp",
					"imp/common/DebugMessages/CPDebugMessages.hpp",
					"api/AVCLan/" . $this->mScriptPatameters->getHBSIFileName() . "Stub.hpp",
					"imp/AVCLan/ProtocolMessaging/interface/CAVCLanProtocolMessagingClientBase.hpp",
					"imp/common/CommandsQueue/CPCommandsQueue.hpp"
					);
				break;
		 	case ScriptParameters::COMPONENT 	: 
			 	$imports = array(
			 		"api/sys/mocca/pf/comm/component/src/CHBComponent.hpp",
			 		"private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::IMPL] . ".hpp",
			 		"private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::LOGIC] . ".hpp",
			 		"private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::CLIENT] . ".hpp"
			 		);
			 	break;
			case ScriptParameters::IMPL 		: 
				$imports = array(
					"api/AVCLan/" . $this->mScriptPatameters->getEntities()[ScriptParameters::COMMAND_SPECIFIC_LOGIC]['file_name'] . ".hpp"
					);
				break;	
			case ScriptParameters::ONOFF 		:
				$imports = array(
					"api/OnOff/" . $this->mScriptPatameters->getEntities()[ScriptParameters::ONOFFSTUB] . ".hpp"
					);
				break;
			case ScriptParameters::COMMANDS:
				$imports = array(
					"api/sys/colibry/pf/base/src/CHBString.hpp"
					);
				break;
			case ScriptParameters::COMMAND_SPECIFIC_LOGIC:
				$imports = array(
					"imp/AVCLan/common/src/private/TAVCLanSerializationCmd.hpp",
					"imp/AVCLan/common/src/private/TAVCLanDeserializationCmd.hpp",
					"api/AVCLan/" . $this->mScriptPatameters->getHBSIFileName() . ".hpp",
					"imp/AVCLan/" . $this->mScriptPatameters->getComponentName() . "/src/private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::COMMANDS]['file_name'] . ".hpp"
					);
				break;
		 } 
		return $imports;
	}

	private function getSourceImports($iMappingType)
	{
		$imports = null;
		switch ($iMappingType) {
			case ScriptParameters::CLIENT 		: 
				$imports = array(
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::CLIENT] . ".hpp",
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/" . $this->mScriptPatameters->getEntities()[ScriptParameters::COMPONENT] . ".hpp",
					"imp/AVCLan/common/src/private/include/CommonAvcLanTypes.h"
					);
				break;	
			case ScriptParameters::LOGIC 		: 
				$imports = array(
					$this->mScriptPatameters->getEntities()[ScriptParameters::LOGIC] . ".hpp",
					"api/sys/mocca/pf/trace/src/HBTrace.h",
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::IMPL] . ".hpp",
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::CLIENT] . ".hpp",
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::COMMANDS]['file_name'] . ".hpp",
					"imp/AVCLan/common/src/private/TAVCLanSerializationCmd.hpp",
					"imp/AVCLan/common/src/private/TAVCLanDeserializationCmd.hpp",
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/private/Commands/" . $this->mScriptPatameters->getEntities()[ScriptParameters::COMMAND_SPECIFIC_LOGIC]['file_name'] . ".hpp"
					);
				break;
		 	case ScriptParameters::COMPONENT 	: 
			 	$imports = array(
			 		"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/CDataExtractorSettingsComponent.hpp",
			 		"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/private/InternalVersion.hpp"
			 		);
		 		break;
			case ScriptParameters::IMPL 		: 
				$imports = array(
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::IMPL] . ".hpp",
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/" . $this->mScriptPatameters->getEntities()[ScriptParameters::COMPONENT] . ".hpp",
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::LOGIC] . ".hpp"
					);
				break;	
			case ScriptParameters::ONOFF 		: 
				$imports = array(
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/private/" . $this->mScriptPatameters->getEntities()[ScriptParameters::IMPL] . ".hpp",
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/" . $this->mScriptPatameters->getEntities()[ScriptParameters::COMPONENT] . ".hpp"
					);
				break;
			case ScriptParameters::COMMAND_SPECIFIC_LOGIC:
				$imports = array(
					"imp/AVCLan/". $this->mScriptPatameters->getComponentName() . "/src/private/Commands/" . $this->mScriptPatameters->getEntities()[ScriptParameters::COMMAND_SPECIFIC_LOGIC]['file_name'] . ".hpp",
					"api/sys/mocca/pf/trace/src/HBTrace.h",
					"imp/AVCLan/common/src/private/AVCLanDataPacker.hpp",
					"imp/AVCLan/common/src/private/include/CommonAvcLanTypes.h",
					"imp/AVCLan/common/src/private/AVCLanDataExtractor.hpp"
					);
				break;
		 } 
		return $imports;
	}
}