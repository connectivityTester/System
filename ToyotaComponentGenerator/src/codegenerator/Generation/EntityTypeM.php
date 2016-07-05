<?php

class EntityTypeM
{
   const UNKNOWN = "";
   const CLASSM = "Class";
   const INTERFACEM = "Interface";
   const VARIABLEM = "Variable";
   const ENUMM = "Enum";
   const UNIONM = "Union";
   const STRUCTM = "Struct";
   const TEMPLATEMETHODS = "TemplateMethods";
   
   protected $mValue = null;
   
   public function __construct( $iValue )
   {
      switch ( $iValue )
      {
         case self::UNKNOWN:
         case self::CLASSM:
         case self::TEMPLATEMETHODS:
         case self::INTERFACEM:
         case self::VARIABLEM:
         case self::ENUMM:
         case self::UNIONM:
         case self::STRUCTM:
            $this->mValue = $iValue;
            break;
         default:
            throw new \InvalidArgumentException("Wrong input value for Entity Type");
      }
   }
   
   public function getValue()
   {
      return $this->mValue;
   }
   
   public function isInterface()
   {
      return $this->mValue == self::INTERFACEM;
   }

   public function isEnum()
   {
      return $this->mValue == self::ENUMM;
   }

   public function isUnion()
   {
      return $this->mValue == self::INTERFACEM;
   }

   public function isStruct()
   {
      return $this->mValue == self::INTERFACEM;
   }
}