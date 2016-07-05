<?php

class Logger
{

	public static function log($className, $msg)
	{
	  echo $className . "::" .  $msg . "\n";
	}

}