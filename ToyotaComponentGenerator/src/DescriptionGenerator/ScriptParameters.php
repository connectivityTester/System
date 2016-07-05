<?php

class ScriptParameters
{
   const LOGIC = "logic";
   const IMPL = "impl";
   const COMPONENT = "component";
   const ONOFF = "onoff";
   const CLIENT = "client";
   const TYPES = "types";
   const COMMANDS = "commands";
   const COMMAND_SPECIFIC_LOGIC = "command_specific_logic";
   const ONOFFSTUB = "onoffStub"; 
   const CLIENTBASE = "clientBase";
   const TEMPLATES = "templ_class";

   private $mHBSIFilePath;
   private $mOutConfigFolder;
   private $mEntities;
   private $mBaseDirectory;
   private $mComponentName;
   
   public function __construct( $iHBSIFilePath, $iComponentName, $iOutConfigFolder, $iEntities, $iBaseDirectory)
   {
      $this->mHBSIFilePath = $iHBSIFilePath;
      $this->mComponentName = $iComponentName;
      $this->mOutConfigFolder = $iOutConfigFolder;
      $this->mEntities = $iEntities;
      $this->mBaseDirectory = $iBaseDirectory;
   }

   public function getHBSIFileName()
   {
      return pathinfo($this->mHBSIFilePath)['filename'];
   }

   public function getComponentName()
   {
      return $this->mComponentName;
   }

   public function getHBSIFilePath()
   {
      return $this->mHBSIFilePath;
   }

   public function getOutConfigFolder()
   {
      return $this->mOutConfigFolder;
   }
   
   public function getEntities()
   {
      return $this->mEntities;
   }

   public static function fromArgv($iBaseDirectory)
   {
      $shortopts  = "";
      $longopts  = array(
         "config_file:"
      );

      $opts = getopt($shortopts,$longopts);

      if (!file_exists($iBaseDirectory.$opts["config_file"]) || filesize ($iBaseDirectory.$opts["config_file"]) == 0) 
      {
         throw new \InvalidArgumentException("Parameter 'config_file'does not exists or empty");
      }

      $params = yaml_parse(file_get_contents($opts['config_file']));

      if ( !isset($params["hbsi_file_path"]) || $params["hbsi_file_path"] == null )
      {
         throw new \InvalidArgumentException("Parameter 'hbsi_file_path' was not specified or incorrect");
      }
      if (!file_exists($iBaseDirectory.$params["hbsi_file_path"]) || filesize ($iBaseDirectory.$params["hbsi_file_path"]) == 0) 
      {
         throw new \InvalidArgumentException("File 'hbsi_file_path' does not exists or empty. Path:" . $iBaseDirectory. $opts["hbsi_file_path"]);
      }
      $path_parts = pathinfo($params["hbsi_file_path"]);
      if ( strtolower($path_parts['extension']) != "hbsi")
      {
         throw new \InvalidArgumentException("HBSI filename extension shall be 'hbsi'");
      }
      ///////////////////////////////////////////////////////////////////////////
      if ( !isset($params["output_config_folder"]) || $params["output_config_folder"] == null )
      {
         throw new \InvalidArgumentException("Parameter " . $iBaseDirectory ."'output_config_folder' was not specified or incorrect");
      }
      if (!file_exists($iBaseDirectory.$params["output_config_folder"])) 
      {
         if (!mkdir($iBaseDirectory.$params["output_config_folder"], 0777, true)) 
         {
            throw new \InvalidArgumentException("Folder " . $iBaseDirectory . "'output_config_folder' can not be created");
         }
      }
      ///////////////////////////////////////////////////////////////////////////
      if ( !isset($params["entities"]) || $params["entities"] == null )
      {
         throw new \InvalidArgumentException("Parameter 'hbsi_path' was not specified or incorrect");
      }



      $entities = $params["entities"];
      foreach ($entities as $key => $entity) 
      {
            switch($key)
            {
               case self::COMPONENT:
               case self::CLIENTBASE:
               case self::COMMANDS:
               case self::TEMPLATES:
               case self::COMMAND_SPECIFIC_LOGIC:
               case self::TYPES:
               case self::CLIENT:
               case self::ONOFF:
               case self::ONOFFSTUB:
               case self::IMPL : 
               case self::LOGIC: break;
               default:
                  throw new \InvalidArgumentException("Some of 'entities' parameters is wrong or empty");
            }
      }
      /////////////////////////////////////////////////////////////////////////// 

      $sp = new ScriptParameters($params["hbsi_file_path"], $params["component_name"], $params["output_config_folder"], $entities, $iBaseDirectory);
      return $sp;
   }
   
   
}