<?php

include "/Configuration/impl/MappingConfiguration.php";
include "/Configuration/impl/ClassesConfiguration.php";

class Loader
{
   const TYPE_CLASSES = "Classes";
   const TYPE_MAPPING = "Mappings";

   public static function LoadData( &$entitiesPath, $type )
   {
      if ( !file_exists($entitiesPath) || filesize ($entitiesPath) == 0 ) throw new \InvalidArgumentException("Configuration '$entitiesPath' cannot be found or empty");
      if ( $type != self::TYPE_CLASSES && $type != self::TYPE_MAPPING ) throw new \InvalidArgumentException("Unknown configuration type '$type'.");
      
      if ( $type == self::TYPE_CLASSES )
         Logger::log("Loader","Reading entities...");
      else
         Logger::Log("Loader","Reading mappings...");
      $entities_file_content = file_get_contents( $entitiesPath );
      Logger::log("Loader","Read " . strlen($entities_file_content) . " symbols from file");
      
      
      Logger::log("Loader","Parsing read data...");
      $entities_data = yaml_parse( $entities_file_content );
      $yaml = null;

      Logger::log("Loader","YAML parsers' work done. Creating configuration model");

      $configuration = self::createConfiguration( $type, $entities_data);
      
      if ( $configuration == null ) throw new \RuntimeException("Cannot create Configuration instance");
      
      Logger::log("Loader","Configuration model successfully created");
      
      return $configuration;
   }
   
   protected static function createConfiguration( $type, $data )
   {
      switch ( $type )
      {
         case self::TYPE_CLASSES:
            return new ClassesConfiguration($data, true);
         case self::TYPE_MAPPING:
            return new MappingConfiguration($data);
         default:
            throw new \InvalidArgumentException("Unknown configuration type '$type'.");
      }
   }
}