<?php

class CommandEnumDescrtiption
{
        public static function getDescription($iScriptParams, $hbsiParser)
        {
        $items = $hbsiParser->getItems();
        $enumDescription = array(
        	"Type" => "Enum",
        	"Namespace" => [$iScriptParams->getEntities()['commands']['namespace']],
        	"Name" => $iScriptParams->getEntities()['commands']['file_name'],
        	"Visibility" => "public",
        	"EnumItems" => $items
        	);
        $generalDescription = array(
        	"Configuration_Classes" => array(
        			"Entities" => array(
        					$iScriptParams->getEntities()['commands']['file_name'] => $enumDescription
        				) 
        		)
        	);
        return $generalDescription; 
        }                                               
}