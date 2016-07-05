<?php

class NamespaceM
{
   private $mNamespaceArray;
   
   public function __construct ( &$iNamespaceArray )
   {
      if ( !is_array($iNamespaceArray) ) throw new \InvalidArgumentException("Not array given for namespace class");
      
      $this->mNamespaceArray = $iNamespaceArray;
   }
   
   public function asArray()
   {
      return $this->mNamespaceArray;
   }
   
   protected function asString()
   {
   	return implode($this->asArray());
   }
   
   public function isSameAs( NamespaceM $iOtherNamespace )
   {
   	return $this->asString() == $iOtherNamespace->asString() ? true : false;
   }
}