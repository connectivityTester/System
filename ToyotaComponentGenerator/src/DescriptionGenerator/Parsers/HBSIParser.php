<?php

class HBSIParser
{
	const STR_TO_REPLACE = "__________";

	protected $mFileContent;
	protected $mScriptParameters;

	public function __construct($iFileContent,$iScriptParameters)
	{
		$this->mScriptParameters = $iScriptParameters;
		$this->mFileContent = simplexml_load_string($iFileContent);
	} 
}