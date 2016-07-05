<?php

class VisibilityM
{
   const UNKNOWN = "unknown";
   const PUBLICM = "public";
   const PROTECTEDM = "protected";
   const PRIVATEM = "private";
   const PACKAGEM = "package";
   
   protected $mValue = null;
   
   public function __construct( $iValue )
   {
      switch ( $iValue )
      {
         case self::UNKNOWN:
         case self::PUBLICM:
         case self::PROTECTEDM:
         case self::PRIVATEM:
         case self::PACKAGEM:
            $this->mValue = $iValue;
            break;
         default:
            throw new \InvalidArgumentException("Wrong input value for Visibility");
      }
   }
   
   public function getValue()
   {
      return $this->mValue;
   }
}