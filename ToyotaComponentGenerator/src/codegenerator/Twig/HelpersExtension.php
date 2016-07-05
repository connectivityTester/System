<?php

include "/../../lib/Twig/Extension.php";

class HelpersExtension extends Twig_Extension
{
   public function getName()
   {
      return 'HelpersF';
   }
   
   public function getFunctions()
   {
      return array(
         'get_single_namespace' => new \Twig_Function_Function(array($this,'getSingleNamespace')),
      );
   }

   public static function getSingleNamespace( $output, $entities )
   {
      $namespaceM = null;
      $single = true;
      
      foreach ( $output["Entities"] as /* EntityM */ $entity )
      {
         if ( isset($entities[$entity]) )
         {
            $currentNS = $entities[$entity]->getNamespace();
            
            if ( $namespaceM == null )
            {
               $namespaceM = $currentNS;
            }
            elseif ( !$currentNS->isSameAs( $namespaceM ) )
            {
               $single = false;
               break;
            }
         }
      }
      
      if ( !$single ) $namespaceM = null;
         
      return $namespaceM;
   }
}