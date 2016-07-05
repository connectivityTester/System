<?php

class OutputsProcessor
{
   protected $mTwig;
   protected $mBaseDirectory;
   protected $mGenericMappings;
   
   public function __construct( Twig_Environment &$twig, &$baseDirectory, &$genericMappings )
   {
      if ( !file_exists($baseDirectory) || !is_dir($baseDirectory) ) 
      {
        if (!mkdir($baseDirectory, 0777))
        {
            throw new \InvalidArgumentException("Output folder '$baseDirectory' cannot be found, is not a directory or can not be created");
        }
      }      
      $this->mTwig = $twig;
      $this->mBaseDirectory = $baseDirectory;
      $this->mGenericMappings = $genericMappings;
   }
   
   public function GenerateOutputs( &$outputs, &$entities )
   {
      if ( $outputs === null ) throw new \InvalidArgumentException("List of outputs is null");
      if ( !is_array($outputs) ) throw new \InvalidArgumentException("Outputs must be a list");
      if ( $entities === null ) throw new \InvalidArgumentException("Entities is null");


      Logger::log("OutputsProcessor","Generation of outputs is started");
      foreach ( $outputs as $k => $output )
      {
         Logger::log("OutputsProcessor","Processing output \"$k\"");
         
         if(!file_exists($this->mBaseDirectory . DIRECTORY_SEPARATOR . $output["Path"]))
         {
            if (!mkdir($this->mBaseDirectory . DIRECTORY_SEPARATOR . $output["Path"], 0777, true)) 
            {
                throw new \Exception("Can not create output directory ". $this->mBaseDirectory . DIRECTORY_SEPARATOR . $output["Path"]);
            }
         }
         $output_path = $this->mBaseDirectory . DIRECTORY_SEPARATOR . $output["Path"] . DIRECTORY_SEPARATOR . $output["Name"];
         $template = $this->mGenericMappings["OutputTypes"][$output["Type"]];
         Logger::log("OutputsProcessor","Output path: \"$output_path\". Template to use: \"$template\"");
         
         $content = $this->mTwig->render( $template, array(
            "output" => $output,
            "entities" => $entities,
            "generic_mappings" =>$this->mGenericMappings
         ) );
         
         ////specific functionality
         $content = str_replace("''", "\"", $content);
         //////////////////////////

         file_put_contents( $output_path, $content );
      }
   }
}