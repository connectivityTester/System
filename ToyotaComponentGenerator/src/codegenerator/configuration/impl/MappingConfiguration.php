<?php

include "MappingValidator.php";
include "/../interfaces/Configuration.php";

class MappingConfiguration extends Configuration
{

   public function __construct($data)
   {
      parent::__construct($data, new MappingValidator() );
      $this->SetMapping($data);
   }
      
   public function GetMapping()
   {
      return $this->mData["Mappings"];
   }
   
   protected function SetMapping ( $mapping )
   {
   	if ( $mapping==null ) throw new \InvalidArgumentException("Cannot set entities. Entities cannot be empty.");

   	$this->mData["Mapping"] = $mapping;
   }
   
   public function GetGenericMappings()
   {
      return $this->mData["Mappings"]["Generic"];
   }
   
   public function GetOutputs()
   {
      return $this->mData["Mappings"]["Output"];
   }

}