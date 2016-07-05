<?php

include "Pack.php";
include "CommandInfo.php";

class MethodContent
{
	protected $mText = null;
	//protected $mSourseRoutingInfo = null;
	protected $mOperationCode = null;
	protected $mPack = null;
	protected $mCommandInfo = null;

	public function __construct($iMethodArray)
	{
		$this->mText = $iMethodArray['Text'];
		//$this->mSourseRoutingInfo = $iMethodArray['SourseRoutingInfo'];
		$this->mOperationCode = $iMethodArray['OperationCode'];
		$packArray = $iMethodArray['Pack'];
		if($packArray != null)
		{
			$vars = array();
			foreach ( $packArray as $value )
			{
				$vars[] = new Pack($value);
			}
	    $this->mPack = $vars;
	    }
	    else
	    {
	    	$this->mPack = null;
	    }
	    $commandsFolder = $iMethodArray['Path'];
	    if($commandsFolder != null)
	    {

	    	if ( !file_exists($commandsFolder) || !is_dir($commandsFolder) ) throw new \InvalidArgumentException("Folder \"" . $commandsFolder. "\" for commands does not exist!");
	    	Logger::log("MethodContent", "Creating command handlers.....");
	    	foreach (scandir($commandsFolder) as $key => $file) 
	    	{
	    		//Logger::log("MethodContent", "File " . $file);
	    		if( strtolower(pathinfo($file)['extension']) == "cpp")
				{
					$fileContent = file_get_contents($commandsFolder . $file);
					$fileContent = str_replace("\n"," ", $fileContent);
					if(stripos($fileContent, "getCommandClassId") !== false)
					{
						Logger::log("MethodContent", "File path ". $commandsFolder . $file);
    					$this->mCommandInfo[] = CommandInfo::create($fileContent);
    				}
				}
	    	}
		}
	}

	public function getText ()
	{
		return $this->mText;
	}

	// public function getSourseRoutingInfo ()
	// {
	// 	return $this->mSourseRoutingInfo;
	// }

	public function getOperationCode ()
	{
		return $this->mOperationCode;
	}

	public function getPack ()
	{
		return $this->mPack ;
	}

	public function getCommandsInfo()
	{
		return $this->mCommandInfo;
	}
}