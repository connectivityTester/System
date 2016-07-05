<?php

class CommandSpecificDescription
{
	public static function getDescription($iScriptParams, $hbsiParser)
        {
                $typedefs = $hbsiParser->getTypedefs();                
                $methods = array();
                foreach ($hbsiParser->packers() as $key => $method) 
                {
                        array_push($methods, $method);
                }
                foreach ($hbsiParser->unpackers() as $key => $method) 
                {
                        array_push($methods, $method);
                }
                foreach ($hbsiParser->debugs() as $key => $method) 
                {
                        array_push($methods, $method);
                }
                $description = array(
                	"Type" => "TemplateMethods",
                	"Namespace" => [$iScriptParams->getEntities()['command_specific_logic']['namespace']],
                	"Name" => $iScriptParams->getEntities()['command_specific_logic']['file_name'],
                	"Visibility" => "public",
                        "Typedefs" => $typedefs,
                	"Methods" => $methods
                	);
                $generalDescription = array(
                	"Configuration_Classes" => array(
                			"Entities" => array(
                					$iScriptParams->getEntities()['command_specific_logic']['file_name'] => $description
                				) 
                		)
                	);
                return $generalDescription; 
        }
}