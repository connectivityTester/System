<?php

class CommandInfo
{
	protected $mCommandName = null;
	protected $mCommandType = null;
	protected $mParameters = null;

	protected function __construct($iCommandName, $iCommandType, $iParameters)
	{
		$this->mCommandType = $iCommandType;
		$this->mCommandName = $iCommandName;
		$this->mParameters = $iParameters;
	}

	public static function create($fileContent)
	{
		//get parameters from method "create"
		preg_match_all("(::create( )*\((.*)\))", $fileContent, $out, PREG_SET_ORDER);
		$position = strpos($out[0][2], ")");
		if($position !== false)
		{
			$parametersString = substr($out[0][2], 0, $position); 
		}
		else
		{
			$parametersString = $out[0][2];
		}
		//split parameters string
		$parametersStrings = explode(",", $parametersString);
		$methodParameters = array();
		foreach ($parametersStrings as $key => $str) 
		{
			$str = trim($str);
			$isConstant = false;
			if (strpos($str, "const") !== false)
			{
				$str = trim(str_replace("const", " ", $str));
				$isConstant = true;
			}
			$parameters = explode(" ", $str);
			$type = $parameters[0];
			$name = array_splice($parameters, 1, count($parameters))[0];
			$visibility = new VisibilityM(VisibilityM::UNKNOWN);
		    $value = null;
		    if( $type != "CHBString" && strpos(strtolower($name), "rolename") === false )
		    {
		    	$methodParameters [] = new VariableM($type, $name, $visibility, $isConstant, $value, false);
		    }
		}
		////get command name and type from file content
		//get name
		preg_match_all("(([_a-zA-Z0-9]*)::getCommandClassId.*([_a-zA-Z0-9]*::[_a-zA-Z0-9]*))", $fileContent, $out, PREG_SET_ORDER);
		//command name
		$commandName = substr($out[0][1], 1, strlen($out[0][1]));
		$position = strpos($out[0][0], "}");
		if($position !== false)
		{
			$getCommandClassIdContent = substr($out[0][0], 0, $position); 
		}
		else
		{
			$getCommandClassIdContent = $out[0][0];
		}
		preg_match_all("(.*return(| .* | .*)( [_a-zA-Z0-9]*::[_a-zA-Z0-9]*))", $getCommandClassIdContent, $out, PREG_SET_ORDER);
		//command type
		$commandType = $out[0][2];

		return new CommandInfo($commandName, $commandType, $methodParameters);
	}

	public function getCommandName()
	{
		return $this->mCommandName;
	}

	public function getCommandType()
	{
		return $this->mCommandType;
	}

	public function getParameters()
	{
		return $this->mParameters;
	}
}