<?php

abstract class Validator
{

   const ROOT_ELEMENT = "Configuration";

   
   public static function ValidateOrThrow( $configuration_data )
   {
      if ( $configuration_data != null )
      {
         if ( is_array($configuration_data) )
         {
            if ( isset($configuration_data[static::ROOT_ELEMENT]) )
            {
               return;
            }
            else
            {
               throw new \InvalidArgumentException("Root element of configuration has to be \"" . static::ROOT_ELEMENT . "\".");
            }
         }
         else
         {
            throw new \InvalidArgumentException("Parsed configuration is not of array type. Probably YAML parser error.");
         }
      }
      else
      {
         throw new \InvalidArgumentException("Configuration data is empty.");
      }
   }
}
