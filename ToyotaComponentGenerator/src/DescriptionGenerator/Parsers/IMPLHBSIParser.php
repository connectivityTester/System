<?php

class IMPLHBSIParser extends HBSIParser
{

	public function getInformations()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Information")
			{
				$methodDescriptions[]  = self::createInformationMethodDescription($method);
			}
		}
		return $methodDescriptions;
	}

	public function getResponses()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Response")
			{
				$methodDescriptions[]  = self::createResponseMethodDescription($method);
			}
		}
		return $methodDescriptions;
	}

	public function getRequests()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Request")
			{
				$methodDescriptions[]  = self::createRequestMethodDescription($method);
			}
		}
		return $methodDescriptions;
	}

	private function createInformationMethodDescription($data)
	{
		foreach ($data->Parameters as $key => $parameter) 
		{
			$params[] = array(
					"IsConstant" => 1,
					"Type" => $parameter->Parameter->Type->__toString(),
					"Name" => $parameter->Parameter->Name->__toString(),
					"Value"=> self::STR_TO_REPLACE
				);
			$callParams[] = $parameter->Parameter->Name->__toString();
		}
		$nameList = "( " . implode(",", $callParams) . " );";
		$infoCall = substr($this->mScriptParameters->getEntities()['impl'], 0, strlen($this->mScriptParameters->getEntities()['impl'])-4 );
        $description = array(
        	"Visibility" =>"public",
        	 "Name" => "information".$data->Name,
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
        	 "Parameters" => $params,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array('"'. $infoCall . "Stub::information" . $data->Name . $nameList . '"'),
	        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
	        	 "OperationCode" => self::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => self::STR_TO_REPLACE
	        	),
        );

        return $description;
	}

	private function createResponseMethodDescription($data)
	{
		foreach ($data->Parameters as $key => $parameter) 
		{
			$params[] = array(
					"IsConstant" => 1,
					"Type" => $parameter->Parameter->Type->__toString(),
					"Name" => $parameter->Parameter->Name->__toString(),
					"Value"=> self::STR_TO_REPLACE
				);
			$callParams[] = $parameter->Parameter->Name->__toString();
		}
		$nameList = "( " . implode(",", $callParams) . " );";
		$infoCall = substr($this->mScriptParameters->getEntities()['impl'], 0, strlen($this->mScriptParameters->getEntities()['impl'])-4 );
		$description = array(
        	"Visibility" =>"public",
        	 "Name" => "response".$data->Name,
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
        	 "Parameters" => $params,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" =>  array('"'. $infoCall . "Stub::response" . $data->Name . $nameList . '"'),
	        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
	        	 "OperationCode" => self::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => self::STR_TO_REPLACE
	        	),
        );

        return $description;
	}

	private function createRequestMethodDescription($data)
	{
		foreach ($data->Parameters as $key => $parameter) 
		{
			$params[] = array(
					"IsConstant" => 1,
					"Type" => $this->mScriptParameters->getHBSIFileName(). "::" . $parameter->Parameter->Type->__toString(),
					"Name" => $parameter->Parameter->Name->__toString(),
					"Value"=> self::STR_TO_REPLACE
				);
			$callParams[] = $parameter->Parameter->Name->__toString();
		}
		$nameList = "( " . implode(",", $callParams) . " );";
        $description = array(
        	"Visibility" =>"public",
        	 "Name" => "request".$data->Name,
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
        	 "Parameters" => $params,
        	 "InitList" => [],
        	 "Content" => array(
	        	 "Text" => array (
	        	 	'"ASSERTMSG ( ' . "'" . 'mpLogic is 0' . "'". ', 0 != mpLogic );"',
	        	 	'"mpLogic->' . "request".$data->Name . $nameList. '"'
	        	 	),
	        	 "SourseRoutingInfo" => self::STR_TO_REPLACE,
	        	 "OperationCode" => self::STR_TO_REPLACE,
	        	 "Pack" => [],
	        	 "Path" => self::STR_TO_REPLACE
	        	),
        );

        return $description;
	}

}