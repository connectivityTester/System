<?php

class LOGICHBSIParser extends HBSIParser
{
	public function getCases()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Information")
			{
				$lines[]  = "case CMD_CLSID_" . strtoupper($method->Name) . "_DESERIALIZATION:{";
				$lines[]  = "handle" . $method->Name . "DeserializationCommandNotification ( cmd, notificationType );";
				$lines[] = "break;}";
			}
			if($method->Type == "Request")
			{
				$lines[]  = "case CMD_CLSID_" . strtoupper($method->Name) . "_SERIALIZATION:{";
				$lines[]  = "handle" . $method->Name . "SerializationCommandNotification ( cmd, notificationType );";
				$lines[] = "break;}";
			}
		}

		return implode("\n", $lines);
	}
	public function getOnNotifications()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Information")
			{
				$methodDescriptions[]  = self::createNotificationDescription($method);
			}
		}
		return $methodDescriptions;
	}

	public function getNotificationsRequests()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Request")
			{
				$methodDescriptions[]  = self::createNotificationRequestDescription($method);
			}
		}
		return $methodDescriptions;
	}

	public function getQueues()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Information")
			{
				$methodDescriptions[]  = self::createQueueDeserMethodDescription($method);
			}
			if($method->Type == "Request")
			{
				$methodDescriptions[]  = self::createQueueSerMethodDescription($method);
			}
		}

		return $methodDescriptions;
	}

	public function getHandles()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Information")
			{
				$methodDescriptions[]  = self::createHandleDeserMethodDescription($method);
			}
			if($method->Type == "Request")
			{
				$methodDescriptions[]  = self::createHandleSerMethodDescription($method);
			}
		}

		return $methodDescriptions;
	}

	private function createNotificationDescription($data)
	{
		$params[] = array(
				"IsConstant" => 1,
				"Type" => $this->mScriptParameters->getEntities()['types'] . "::sAVCLanProtocolMessage &",
				"Name" => "iMessage",
				"Value"=> self::STR_TO_REPLACE
			);
		$description = array(
        	"Visibility" =>"public",
        	 "Name" => "on" . $data->Name,
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
        	 "Parameters" => $params,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array(
	        	 	"TRC_SCOPE( imp_meu_swsystems_avclan," . $this->mScriptParameters->getEntities()[ScriptParameters::LOGIC] . ", all );",
   					"DBG_MSG(( \"on" . $data->Name . "\" ));",
	        	 	"queue" . $data->Name . "DeserializationCommand ( iMessage );"
	        	 	),
	        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
	        	 "OperationCode" => self::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => self::STR_TO_REPLACE
	        	),
        );

        return $description;
	}

	private function createNotificationRequestDescription($data)
	{
		foreach ($data->Parameters as $key => $parameter) 
		{
			$params[] = array(
					"IsConstant" => 1,
					"Type" => $this->mScriptParameters->getHBSIFileName() . "::" . $parameter->Parameter->Type->__toString(). " &",
					"Name" => $parameter->Parameter->Name->__toString(),
					"Value"=> self::STR_TO_REPLACE
				);
		}
		/*$params[] = array(
				"IsConstant" => 1,
				"Type" => $this->mScriptParameters->getHBSIFileName(). "::" . $data->Name . "Type &" ,
				"Name" => "t" . $data->Name,
				"Value"=> self::STR_TO_REPLACE
			);*/
		$description = array(
        	"Visibility" =>"public",
        	 "Name" => "request".$data->Name,
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
        	 "Parameters" => $params,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array(
	        	 	"TRC_SCOPE( imp_meu_swsystems_avclan," . $this->mScriptParameters->getEntities()[ScriptParameters::LOGIC] . ", all );",
   					"DBG_MSG(( \"request" . $data->Name . "\" ));",
	        	 	"queue" . $data->Name . "SerializationCommand( t" . $data->Name ." );"),
	        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
	        	 "OperationCode" => self::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => self::STR_TO_REPLACE
	        	),
        );

        return $description;
	}

	private function createQueueDeserMethodDescription($data)
	{
		$params[] = array(
				"IsConstant" => 1,
				"Type" => $this->mScriptParameters->getEntities()['types'] . "::" . "sAVCLanProtocolMessage&" ,
				"Name" => "iMessage",
				"Value"=> self::STR_TO_REPLACE
			);
		$text = array(
			"TRC_SCOPE( imp_meu_swsystems_avclan," . $this->mScriptParameters->getEntities()[ScriptParameters::LOGIC] . ", all );",
			"CHBString fullPrefix = this->getPrefix() + CHBString( \"" . $data->Name .":\" );",
  			$this->mScriptParameters->getEntities()['command_specific_logic']['namespace'] . "::C". $data->Name . "DeserializationCommand * pCmd = " . $this->mScriptParameters->getEntities()['commands']['namespace'] . "::C". $data->Name . "DeserializationCommand::create",
            "( iMessage,",
            $this->mScriptParameters->getEntities()['commands']['namespace'] . "::CMD_CLSID_" . strtoupper($data->Name) . "_DESERIALIZATION,",
            $this->mScriptParameters->getEntities()['command_specific_logic']['namespace'] . "::unpacker" . $data->Name . ",",
            $this->mScriptParameters->getEntities()['command_specific_logic']['namespace'] . "::debug"  . $data->Name . ",",
            "fullPrefix );",
			"ASSERT_MSG( \"pCmd is null\", 0 != pCmd);",
   			"subscribeOn( pCmd , CommandTypes::ON_FINISH );",
   			"static_cast<void>( mCommandsQueue.push_back( pCmd ) );"
			);
		$description = array(
        	"Visibility" =>"public",
        	 "Name" => "queue" . $data->Name . "DeserializationCommand",
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
        	 "Parameters" => $params,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => $text,
	        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
	        	 "OperationCode" => self::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => self::STR_TO_REPLACE
	        	),
        );

        return $description;
	} 

	private function createQueueSerMethodDescription($data)
	{
		/*$params[] = array(
				"IsConstant" => 1,
				"Type" => $this->mScriptParameters->getHBSIFileName() . "::" . $data->Name . "&" ,
				"Name" => "t" . $data->Name,
				"Value"=> self::STR_TO_REPLACE
			);*/
		foreach ($data->Parameters as $key => $parameter) 
		{
			$params[] = array(
					"IsConstant" => 1,
					"Type" => $this->mScriptParameters->getHBSIFileName() . "::" . $parameter->Parameter->Type->__toString(). " &",
					"Name" => $parameter->Parameter->Name->__toString(),
					"Value"=> self::STR_TO_REPLACE
				);
		}
   		$text = array(
   			"CHBString fullPrefix = this->getPrefix() + CHBString( \"" . $data->Name .":\" );",
  			$this->mScriptParameters->getEntities()['command_specific_logic']['namespace'] . "::C". $data->Name . "SerializationCommand * pCmd = " . $this->mScriptParameters->getEntities()['commands']['namespace'] . "::C". $data->Name . "SerializationCommand::create",
            "( t" . $data->Name . ",",
            $this->mScriptParameters->getEntities()['commands']['namespace'] . "::CMD_CLSID_" . strtoupper($data->Name) . "_SERIALIZATION,",
            $this->mScriptParameters->getEntities()['command_specific_logic']['namespace'] . "::packer" . $data->Name . ",",
            $this->mScriptParameters->getEntities()['command_specific_logic']['namespace'] . "::debug"  . $data->Name . ",",
            "fullPrefix );",
   			"ASSERT_MSG( \"pCmd is null\", 0 != pCmd);",
   			"subscribeOn( pCmd , CommandTypes::ON_FINISH );",
   			"static_cast<void>( mCommandsQueue.push_back( pCmd ) );"
   			);
		$description = array(
        	"Visibility" =>"public",
        	 "Name" => "queue" . $data->Name . "SerializationCommand",
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
        	 "Parameters" => $params,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => $text,
	        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
	        	 "OperationCode" => self::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => self::STR_TO_REPLACE
	        	),
        	);
		return $description;
	}

	private function createHandleDeserMethodDescription($data)
	{
		$params[] = array(
				"IsConstant" => 1,
				"Type" => "CPCommandPtr&" ,
				"Name" => "cmd",
				"Value"=> self::STR_TO_REPLACE
			);
		$params[] = array(
				"IsConstant" => 1,
				"Type" => "CommandTypes::eCommandNotificationType&" ,
				"Name" => "notificationType",
				"Value"=> self::STR_TO_REPLACE
			);
   		$text = array(
   			"TRC_SCOPE( imp_meu_swsystems_avclan," . $this->mScriptParameters->getEntities()[ScriptParameters::LOGIC] . ", all );",
			"ASSERT_MSG( \"mpDAVCLanSettingsImpl is null\", 0 != mpDAVCLanSettingsImpl );",
   			"if ( CommandTypes::ON_FINISH == notificationType ){",
   			"CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();",
      		"switch( cmdResult ){",
      		"case CommandTypes::RUNRESULT_SUCCESS:{",
      		"DBG_MSG( \"" . $data->Name . "Deserialization command finished (successfully: Yes). Responding to consumer\" );",            
      		$this->mScriptParameters->getEntities()['command_specific_logic']['namespace'] . "::C" . $data->Name . "DeserializationCommand* pCommand = static_cast<" . $this->mScriptParameters->getEntities()['command_specific_logic']['namespace'] . "::C" . $data->Name . "DeserializationCommand* >( cmd.getPointer( ) );",
   			"mp" . substr($this->mScriptParameters->getEntities()['impl'], 1) . "->information" . $data->Name . "( pCommand->getOutData() );",
   			"break;}",
   			"case CommandTypes::RUNRESULT_ERROR: {",
   			"DBG_MSG( \"" . $data->Name . "Deserialization command finished (successfully: No). Not responding to consumer\" );",            
   			"break;}",
   			"case CommandTypes::RUNRESULT_ABORTED: {",
   			"DBG_MSG( \"" . $data->Name . "Deserialization command finished (was aborted). Not responding to consumer\" );",            
   			"break;}",
   			"default:{",
   			"DBG_MSG( \"" . $data->Name . "Deserialization  command finished with unexpected result %d. Not responding to consumer\", cmdResult );",
   			"break;}}}"
   			);
		$description = array(
        	"Visibility" =>"public",
        	 "Name" => "handle" . $data->Name . "DeserializationCommandNotification",
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
        	 "Parameters" => $params,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => $text,
	        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
	        	 "OperationCode" => self::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => self::STR_TO_REPLACE
	        	),
        );
        return $description;
	}

	private function createHandleSerMethodDescription($data)
	{
		$params[] = array(
				"IsConstant" => 1,
				"Type" => "CPCommandPtr&" ,
				"Name" => "cmd",
				"Value"=> self::STR_TO_REPLACE
			);
		$params[] = array(
				"IsConstant" => 1,
				"Type" => "CommandTypes::eCommandNotificationType&" ,
				"Name" => "notificationType",
				"Value"=> self::STR_TO_REPLACE
			);
		$text = array(
			"TRC_SCOPE( imp_meu_swsystems_avclan," . $this->mScriptParameters->getEntities()[ScriptParameters::LOGIC] . ", all );",
			"ASSERT_MSG( \"mpDAVCLanSettingsImpl is null\", 0 != mpDAVCLanSettingsImpl );",
   			"if ( CommandTypes::ON_FINISH == notificationType ){",
   			"CommandTypes::eCommandResult cmdResult = cmd->getCommandResult();",
      		"switch( cmdResult ){",
      		"case CommandTypes::RUNRESULT_SUCCESS:{",
      		"DBG_MSG( \"" . $data->Name . "Serialization command finished (successfully: Yes). Responding to consumer\" );",            
      		$this->mScriptParameters->getEntities()['command_specific_logic']['namespace'] . "::C" . $data->Name . "SerializationCommand* pCommand = static_cast<" . $this->mScriptParameters->getEntities()['command_specific_logic']['namespace'] . "::C" . $data->Name . "SerializationCommand * >( cmd.getPointer( ) );",
   			"mp" . substr($this->mScriptParameters->getEntities()['impl'], 1) . "->response" . $data->Name . "( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );",
   			"break;}",
   			"case CommandTypes::RUNRESULT_ERROR: {",
   			"DBG_MSG( \"" . $data->Name . "Serialization command finished (successfully: No). Not responding to consumer\" );",  
   			"mp" . substr($this->mScriptParameters->getEntities()['impl'], 1) . "->response" . $data->Name . "( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );",          
   			"break;}",
   			"case CommandTypes::RUNRESULT_ABORTED: {",
   			"DBG_MSG( \"" . $data->Name . "Serialization command finished (was aborted). Not responding to consumer\" );",
   			"mp" . substr($this->mScriptParameters->getEntities()['impl'], 1) . "->response" . $data->Name . "( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );",            
   			"break;}",
   			"default:{",
   			"DBG_MSG( \"" . $data->Name . "Serialization  command finished with unexpected result %d. Not responding to consumer\", cmdResult );",
   			"mp" . substr($this->mScriptParameters->getEntities()['impl'], 1) . "->response" . $data->Name . "( (CommandTypes::RUNRESULT_SUCCESS == cmdResult) );",
   			"break;}}}"
   			);
		$description = array(
        	"Visibility" =>"public",
        	 "Name" => "handle" . $data->Name . "SerializationCommandNotification",
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
        	 "Parameters" => $params,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => $text,
	        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
	        	 "OperationCode" => self::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => self::STR_TO_REPLACE
	        	),
        );

        return $description;
	}
}