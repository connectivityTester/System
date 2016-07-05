<?php

require_once '/../../lib/Twig/Autoloader.php';
include "HelpersExtension.php";

class TwigLoader
{
   public static function Load( &$iTemplatesPath )
   {
      if ( $iTemplatesPath == null ) throw new \InvalidArgumentException("Empty templates path given");
      if ( !is_array($iTemplatesPath) ) $iTemplatesPath = array (iTemplatesPath);

      foreach ( $iTemplatesPath as $path )
         if ( !file_exists($path) || !is_dir($path) ) throw new \InvalidArgumentException("Templates Folder '$path' cannot be found or is not a directory");
      
      Logger::log("TwigLoader","Loading TWIG");
      Twig_Autoloader::register();
      $loader = new Twig_Loader_Filesystem($iTemplatesPath);
      if ( $loader == null ) throw new \RuntimeException("Cannot create Twig loader");
      $twig = new Twig_Environment($loader);
      if ( $twig == null ) throw new \RuntimeException("Cannot create Twig engine");
      
      Logger::log("TwigLoader","TWIG Engine created. Adding extensions");
      $twig->addFilter('var_dump', new Twig_Filter_Function('var_dump'));
      $filter = new Twig_SimpleFilter('hasPublic', function ($items) {
            foreach ($items as $key => $item) 
            {
               if($item->getVisibility()->getValue() == VisibilityM::PUBLICM)
               {
                  return true;
               }
            }
            return false;
      });
      $twig->addFilter($filter);
      $filter = new Twig_SimpleFilter('hasPrivate', function ($items) {
            foreach ($items as $key => $item) 
            {
               if($item->getVisibility()->getValue() == VisibilityM::PRIVATEM)
               {
                  return true;
               }
            }
            return false;
      });
      $twig->addFilter($filter);
      $filter = new Twig_SimpleFilter('hasProtected', function ($items) {
            foreach ($items as $key => $item) 
            {
               if($item->getVisibility()->getValue() == VisibilityM::PROTECTEDM)
               {
                  return true;
               }
            }
            return false;
      });
      $twig->addFilter($filter);
      $filter = new Twig_SimpleFilter('getTRC_SCOPE_DEF', function ($methods) {
         $lines = array();
         foreach ($methods as $key => $method) 
         {
            $text = $method->getContent()->getText();
            if($text != null)
            {
               foreach ($text as $key => $line) 
               {
                  if(strpos($line, "TRC_SCOPE") !== false )
                  {
                     $line = str_replace("TRC_SCOPE", "TRC_SCOPE_DEF", $line);
                     array_push($lines, $line);
                  }
               }
            }
         }
         $lines = array_unique($lines);
         return $lines;
      });
      $twig->addFilter($filter);
      $twig->addExtension( new HelpersExtension() );
      
      Logger::log("TwigLoader","TWIG Engine loaded successfully");
      return $twig;
   }
}