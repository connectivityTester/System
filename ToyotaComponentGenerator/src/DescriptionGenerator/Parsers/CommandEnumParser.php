<?php


class CommandEnumParser extends HBSIParser
{

	public function getItems()
	{
		foreach ($this->mFileContent->xpath('//Method') as $method)
		{
			if($method->Type == "Request")
			{
				$items[]  = "CMD_CLSID_" . strtoupper($method->Name) . "_SERIALIZATION";
			}
			if($method->Type == "Information")
			{
				$items[]  = "CMD_CLSID_" . strtoupper($method->Name) . "_DESERIALIZATION";
			}
		}
		return $items;
	}
} 