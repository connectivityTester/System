<?php

include "MethodContent.php";

class MethodM
{
   protected $mVisibility = null;
   protected $mName = null;
   protected $mIsConstructor = null;
   protected $mIsDestructor = null;
   protected $mIsAbstract = null;
   protected $mIsVirtual = null;
   protected $mIsConstant = null;
   protected $mReturnType = null;
   protected $mParameters = null;
   protected $mContent = null;
   protected $mIsStatic = null;
   protected $mIsInline = null;
   protected $mInitList = null;

   static public function createAsEntityMethods(&$iMethodsArray )
   {
      if ( $iMethodsArray === null ) throw new \InvalidArgumentException("Methods description for entity is null");
      if ( !is_array($iMethodsArray) ) throw new \InvalidArgumentException("Methods description for entity is not array");

      $methods = array();
      foreach ( $iMethodsArray as $pkey => &$param )
      {
         //Logger::log("MethodM","Processing entity method '$pkey'");
         $methods[] = self::createAsEntityMethod($param );
      }
      return $methods;
   }
   static public function createAsEntityMethod(&$iMethodArray )
   {
      //Logger::log("MethodM", "Method name - " . $iMethodArray['Name']);
   	self::validateMethodArray( $iMethodArray, true, true, true, true, true, true, true, true, true, true, true, true  );

      $visibility = new VisibilityM ( $iMethodArray['Visibility'] );
      $name = $iMethodArray['Name'];
      $isConstructor = $iMethodArray['IsConstructor'] ? true : false;
      $isDestructor = $iMethodArray['IsDestructor'] ? true : false;
      $isAbstract = $iMethodArray['IsAbstract'] ? true : false;
      $isVirtual = $iMethodArray['IsVirtual'] ? true : false;
      $isConstant = $iMethodArray['IsConstant'] ? true : false;
      $returnType = VariableM::createAsMethodReturnValue($iMethodArray['Return']);
      $parameters = VariableM::createAsMethodParameters($iMethodArray['Parameters']);
      $content = new MethodContent($iMethodArray['Content']);
      $isStatic = $iMethodArray['IsStatic'] ? true : false;
      $isInline = $iMethodArray['IsInline'] ? true : false;
      $initList = Variablem::createAsInitList($iMethodArray['InitList']);
   	
   	return new MethodM($visibility, $name, $isConstructor, $isDestructor, $isAbstract, $isVirtual, $isConstant, $returnType, $parameters, $content, $isStatic, $isInline, $initList);
   }
   
   static private function validateMethodArray(
      &$iMethodArray, $iValidateVisibility, $iValidateName, $iValidateIsConstructor, $iValidateIsDestructor,
      $iValidateIsAbstract, $iValidateIsVirtual, $iValidateIsConstant, $iValidateReturn,
      $iValidateParameters, $iValidateReturn, $iValidateIsStatic, $iValidateIsInline )
   {
   	if ( $iMethodArray == null ) throw new \InvalidArgumentException("Method description is null");
   	if ( !is_array($iMethodArray) ) throw new \InvalidArgumentException("Method description is not array");

      if ( $iValidateVisibility && (!key_exists('Visibility', $iMethodArray) || $iMethodArray['Visibility'] == null) ) throw new \InvalidArgumentException("Method visibility is not defined");
      if ( $iValidateName && (!key_exists('Name', $iMethodArray)) ) throw new \InvalidArgumentException("Method name is not defined");
      if ( $iValidateIsConstructor && (!key_exists('IsConstructor', $iMethodArray) || $iMethodArray['IsConstructor'] === null) ) throw new \InvalidArgumentException("Method IsConstructor value is not defined");
      if ( $iValidateIsDestructor && (!key_exists('IsDestructor', $iMethodArray) || $iMethodArray['IsDestructor'] === null) ) throw new \InvalidArgumentException("Method IsDestructor value is not defined");
      if ( $iValidateIsAbstract && (!key_exists('IsAbstract', $iMethodArray) || $iMethodArray['IsAbstract'] === null) ) throw new \InvalidArgumentException("Method abstract value is not defined");
      if ( $iValidateIsVirtual && (!key_exists('IsVirtual', $iMethodArray) || $iMethodArray['IsVirtual'] === null) ) throw new \InvalidArgumentException("Method virtual value is not defined");
      if ( $iValidateIsStatic && (!key_exists('IsStatic', $iMethodArray) || $iMethodArray['IsStatic'] === null) ) throw new \InvalidArgumentException("Method static value is not defined");
      if ( $iValidateIsInline && (!key_exists('IsInline', $iMethodArray) || $iMethodArray['IsInline'] === null) ) throw new \InvalidArgumentException("Method inline value is not defined");
      if ( $iValidateIsConstant && (!key_exists('IsConstant', $iMethodArray) || $iMethodArray['IsConstant'] === null) ) throw new \InvalidArgumentException("Method constant value is not defined");
      if ( $iValidateReturn && (!key_exists('Return', $iMethodArray) || $iMethodArray['Return'] === null) ) throw new \InvalidArgumentException("Method return value is not defined");
      if ( $iValidateParameters && (!key_exists('Parameters', $iMethodArray) || $iMethodArray['Parameters'] === null || !is_array($iMethodArray['Parameters'])) ) throw new \InvalidArgumentException("Method parameters are not defined");
      if ( $iValidateReturn && (!key_exists('Content', $iMethodArray)) ) throw new \InvalidArgumentException("Method content value is not defined");
   }
   
   public function __construct(VisibilityM $iVisibility, $iName, $iIsConstructor, $iIsDestructor, $iIsAbstract, $iIsVirtual, $iIsConstant, VariableM $iReturnType, $iParameters, $iContent, $iIsStatic, $iIsInline, $iInitList )
   {
      $this->mVisibility = $iVisibility;
      $this->mName = $iName;
      $this->mIsConstructor = $iIsConstructor;
      $this->mIsDestructor = $iIsDestructor;
      $this->mIsAbstract = $iIsAbstract;
      $this->mIsVirtual = $iIsVirtual;
      $this->mIsConstant = $iIsConstant;
      $this->mReturnType = $iReturnType;
      $this->mParameters = $iParameters;
      $this->mContent = $iContent;
      $this->mIsStatic = $iIsStatic;
      $this->mIsInline = $iIsInline;
      $this->mInitList = $iInitList;
   }

   public function getVisibility()
   {
      return $this->mVisibility;
   }

   public function getName()
   {
      return $this->mName;
   }

   public function getIsConstructor()
   {
      return $this->mIsConstructor;
   }

   public function getIsAbstract()
   {
      return $this->mIsAbstract;
   }

   public function getIsVirtual()
   {
      return $this->mIsVirtual;
   }

   public function getIsDestructor()
   {
      return $this->mIsDestructor;
   }

   public function getIsConstant()
   {
      return $this->mIsConstant;
   }

   public function getReturnType()
   {
      return $this->mReturnType;
   }

   public function getParameters()
   {
      return $this->mParameters;
   }

   public function getContent()
   {
      return $this->mContent;
   }

   public function getIsStatic()
   {
      return $this->mIsStatic;
   }

   public function getIsInline()
   {
      return $this->mIsInline;
   }

   public function getInitList()
   {
      return $this->mInitList;
   }
}